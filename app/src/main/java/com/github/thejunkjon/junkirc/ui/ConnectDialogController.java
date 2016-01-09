package com.github.thejunkjon.junkirc.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectDialogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectDialogController.class);

    @FXML
    protected void handleConnect(final ActionEvent actionEvent) {
        LOGGER.trace("handleConnect");
        getStageFromActionEvent(actionEvent).close();
        new MainWindow().show();
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
