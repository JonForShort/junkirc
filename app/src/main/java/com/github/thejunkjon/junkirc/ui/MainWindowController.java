package com.github.thejunkjon.junkirc.ui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public final class MainWindowController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private final List<String> commandHistoryQueue = new ArrayList<>(0);
    private ListIterator<String> commandHistoryIterator = commandHistoryQueue.listIterator();

    @FXML
    private TextArea commandsText;

    @FXML
    private TextArea chatText;

    @FXML
    private ListView userList;

    @FXML
    public void onCommandKeyPressed(final KeyEvent keyEvent) {
        LOGGER.debug(keyEvent.getText());
        if (KEY_PRESSED.equals(keyEvent.getEventType())) {
            if (ENTER.equals(keyEvent.getCode())) {
                addCurrentCommandBufferToHistory();
                resetCommandBuffer();
                keyEvent.consume();
            } else if (UP.equals(keyEvent.getCode()) || KP_UP.equals(keyEvent.getCode())) {
                cycleThroughBufferUp();
            } else if (DOWN.equals(keyEvent.getCode()) || KP_DOWN.equals(keyEvent.getCode())) {
                cycleThroughBufferDown();
            }
        }
    }

    private void cycleThroughBufferUp() {
        if (commandHistoryIterator.hasPrevious()) {
            commandsText.setText(commandHistoryIterator.previous());
        }
    }

    private void cycleThroughBufferDown() {
        if (commandHistoryIterator.hasNext()) {
            commandsText.setText(commandHistoryIterator.next());
        }
    }

    private void resetCommandBuffer() {
        commandsText.clear();
    }

    private void addCurrentCommandBufferToHistory() {
        final String currentCommandText = commandsText.getText();
        commandHistoryQueue.add(currentCommandText);
        commandHistoryIterator = commandHistoryQueue.listIterator(commandHistoryQueue.size() - 1);
    }
}
