package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.InternetRelayChatConnection;
import com.github.thejunkjon.junkirc.network.InternetRelayChatConnection.OnMessageReceivedListener;
import com.github.thejunkjon.junkirc.ui.UserInputParser.OnUserInputCompletedListener;
import com.github.thejunkjon.junkirc.ui.UserInputParser.OnUserInputListener;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class MainWindowController implements OnUserInputListener,
        OnUserInputCompletedListener,
        OnMessageReceivedListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindowController.class);

    @FXML
    private TextField userInput;

    @FXML
    private TextArea content;

    private final UserInputParser userInputParser = new UserInputParser(this, this);
    private InternetRelayChatConnection connection;

    @FXML
    void onKeyPressed(final KeyEvent keyEvent) {
        userInputParser.onKeyPressed(keyEvent);
    }

    public void openConnection(final String server, final int port) throws IOException {
        connection = new InternetRelayChatConnection.Builder(server, port, this).build();
        connection.open();
    }

    @Override
    public String getUserInput() {
        return userInput.getText();
    }

    @Override
    public void setUserInput(final String newInput) {
        userInput.setText(newInput);
    }

    @Override
    public void clearUserInput() {
        userInput.clear();
    }

    @Override
    public void onUserInputCompleted(String userInput) {
        try {
            connection.writeCommand("%s", userInput);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void onMessageReceived(final String message) {
        javafx.application.Platform.runLater(() -> content.appendText(message + "\n"));
    }
}
