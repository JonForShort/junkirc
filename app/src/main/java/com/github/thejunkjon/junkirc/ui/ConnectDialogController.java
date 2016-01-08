package com.github.thejunkjon.junkirc.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConnectDialogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectDialogController.class);
    private static final String WINDOW_TITLE = "Connected";

    @FXML
    protected void handleConnect(final ActionEvent actionEvent) {
        LOGGER.trace("handleConnect");
        final Stage stage = getStageFromActionEvent(actionEvent);
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        try {
            stage.setTitle(WINDOW_TITLE);
            stage.getScene().setRoot(fxmlLoader.load());
            LOGGER.trace("loaded main window");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @FXML
    protected void handleCancel(final ActionEvent actionEvent) {
        LOGGER.trace("handleCancel");
        getStageFromActionEvent(actionEvent).close();
    }

    private static Stage getStageFromActionEvent(final ActionEvent actionEvent) {
        final Node node = (Node) actionEvent.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        return (Stage) stage.getScene().getWindow();
    }
}
