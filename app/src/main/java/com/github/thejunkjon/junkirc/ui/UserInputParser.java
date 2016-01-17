package com.github.thejunkjon.junkirc.ui;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

class UserInputParser {

    private final List<String> inputHistory = new ArrayList<>(0);
    private final OnUserInputListener onUserInputListener;
    private final OnUserInputCompletedListener onUserInputCompletedListener;
    private ListIterator<String> inputHistoryIterator = inputHistory.listIterator();

    UserInputParser(final OnUserInputCompletedListener onUserInputCompletedListener,
                    final OnUserInputListener onUserInputListener) {
        this.onUserInputCompletedListener = onUserInputCompletedListener;
        this.onUserInputListener = onUserInputListener;
    }

    interface OnUserInputCompletedListener {
        void onUserInputCompleted(final String userInput);
    }

    interface OnUserInputListener {
        String getUserInput();

        void setUserInput(final String newInput);

        void clearUserInput();
    }

    @FXML
    public void onKeyPressed(final KeyEvent keyEvent) {
        if (KEY_PRESSED.equals(keyEvent.getEventType())) {
            if (ENTER.equals(keyEvent.getCode())) {
                final String completedUserInput = onUserInputListener.getUserInput();
                addInputToHistory(completedUserInput);
                resetCommandTextArea();
                keyEvent.consume();
                onUserInputCompletedListener.onUserInputCompleted(completedUserInput);
            } else if (UP.equals(keyEvent.getCode()) || KP_UP.equals(keyEvent.getCode())) {
                cycleUpInInputHistory();
            } else if (DOWN.equals(keyEvent.getCode()) || KP_DOWN.equals(keyEvent.getCode())) {
                cycleDownInInputHistory();
            }
        }
    }

    private void addInputToHistory(final String completedInput) {
        inputHistory.add(completedInput);
        inputHistoryIterator = inputHistory.listIterator(inputHistory.size());
    }

    private void cycleUpInInputHistory() {
        if (inputHistoryIterator.hasPrevious()) {
            onUserInputListener.setUserInput(inputHistoryIterator.previous());
        } else {
            resetCommandTextArea();
        }
    }

    private void cycleDownInInputHistory() {
        if (inputHistoryIterator.hasNext()) {
            onUserInputListener.setUserInput(inputHistoryIterator.next());
        } else {
            resetCommandTextArea();
        }
    }

    private void resetCommandTextArea() {
        onUserInputListener.clearUserInput();
    }
}
