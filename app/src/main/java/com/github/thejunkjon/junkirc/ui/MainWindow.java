package com.github.thejunkjon.junkirc.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

final class MainWindow {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private static final String WINDOW_TITLE = "Connected";

    public void show() {
        final Stage stage = new Stage();
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        try {
            stage.setTitle(WINDOW_TITLE);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            LOGGER.trace("loaded main window");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}