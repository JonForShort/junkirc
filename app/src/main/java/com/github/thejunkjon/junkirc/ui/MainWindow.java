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
    private static final String FXML_WINDOW_MAIN_WINDOW_FXML = "fxml/window/MainWindow.fxml";

    public void show() {
        final Stage stage = new Stage();
        final FXMLLoader fxmlLoader = new FXMLLoader(getResource(FXML_WINDOW_MAIN_WINDOW_FXML));
        try {
            stage.setTitle(WINDOW_TITLE);
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
            LOGGER.trace("loaded main fxml.window");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}