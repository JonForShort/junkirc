package com.github.thejunkjon.junkirc.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.thejunkjon.junkirc.utils.ResourceUtils.getResource;

final class MainWindow {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private static final String WINDOW_TITLE = "Connected";
    private static final String FXML_WINDOW_MAIN = "fxml/window/MainWindow.fxml";

    void show(final String server, final int port) {
        final Stage stage = new Stage();
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getResource(FXML_WINDOW_MAIN));
            final Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle(WINDOW_TITLE);
            stage.show();

            fxmlLoader.<MainWindowController>getController().openConnection(server, port);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}