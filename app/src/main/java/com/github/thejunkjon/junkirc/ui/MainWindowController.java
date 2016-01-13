package com.github.thejunkjon.junkirc.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public final class MainWindowController {

    private final List<String> commandHistory = new ArrayList<>(0);
    private ListIterator<String> commandHistoryIterator = commandHistory.listIterator();

    @FXML
    private TextField commandsText;

    @FXML
    private TextArea chatText;

    @FXML
    private ListView userList;

    @FXML
    public void onCommandKeyPressed(final KeyEvent keyEvent) {
        if (KEY_PRESSED.equals(keyEvent.getEventType())) {
            if (ENTER.equals(keyEvent.getCode())) {
                addCurrentCommandToHistory();
                resetCommandTextArea();
                keyEvent.consume();
            } else if (UP.equals(keyEvent.getCode()) || KP_UP.equals(keyEvent.getCode())) {
                cycleUpInCommandHistory();
            } else if (DOWN.equals(keyEvent.getCode()) || KP_DOWN.equals(keyEvent.getCode())) {
                cycleDownInCommandHistory();
            }
        }
    }

    private void addCurrentCommandToHistory() {
        final String currentCommandText = commandsText.getText();
        commandHistory.add(currentCommandText);
        commandHistoryIterator = commandHistory.listIterator(commandHistory.size());
    }

    private void cycleUpInCommandHistory() {
        if (commandHistoryIterator.hasPrevious()) {
            commandsText.setText(commandHistoryIterator.previous());
        } else {
            resetCommandTextArea();
        }
    }

    private void cycleDownInCommandHistory() {
        if (commandHistoryIterator.hasNext()) {
            commandsText.setText(commandHistoryIterator.next());
        } else {
            resetCommandTextArea();
        }
    }

    private void resetCommandTextArea() {
        commandsText.clear();
    }
}
