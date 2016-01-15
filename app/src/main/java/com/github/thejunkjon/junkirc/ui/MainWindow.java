package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.Connection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.github.thejunkjon.junkirc.utils.ResourceUtils.getResource;

final class MainWindow {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);
    private static final String WINDOW_TITLE = "Connected";
    private static final String FXML_WINDOW_MAIN = "fxml/window/MainWindow.fxml";
    private final FXMLLoader fxmlLoader = new FXMLLoader(getResource(FXML_WINDOW_MAIN));
    private Scene scene;

    void show() {
        final Stage stage = new Stage();
        try {
            stage.setTitle(WINDOW_TITLE);
            scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
            LOGGER.trace("loaded main fxml.window");
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    void addServerCommunicationPane(final Connection connection) throws IOException {
        final TabPane tabPane = (TabPane) scene.lookup("#tabPane");
        final Tab tab = new Tab(connection.getHost());
        tab.setContent(new ServerCommunicationPane(connection).load());
        tabPane.getTabs().add(tab);
    }
}