package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class ServerCommunicationPaneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerCommunicationPaneController.class);

    private UserInputParser userInputParser;

    private Connection connection;

    @FXML
    private TextArea content;

    @FXML
    private TextField userInput;

    @FXML
    public void onKeyPressed(final KeyEvent keyEvent) {
        userInputParser.onKeyPressed(keyEvent);
    }

    public void initialize(Connection connection) {
        this.userInputParser = new UserInputParser(userInput);
        this.connection = connection;
        connection.setOnDataAvailableListener(data -> {
            try {
                content.appendText(new String(data, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                LOGGER.error(e.getMessage(), e);
            }
        });
    }
}
