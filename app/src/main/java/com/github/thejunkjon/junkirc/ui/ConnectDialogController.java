package com.github.thejunkjon.junkirc.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectDialogController {

    @FXML
    private TextField nickname;

    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @FXML
    protected void handleConnect(final ActionEvent actionEvent) throws IOException {
        getStageFromActionEvent(actionEvent).close();
        new MainWindow().show(host.getText(), Integer.parseInt(port.getText()));
    }

    @FXML
    protected void handleCancel(final ActionEvent actionEvent) {
        getStageFromActionEvent(actionEvent).close();
    }

    private static Stage getStageFromActionEvent(final ActionEvent actionEvent) {
        final Node node = (Node) actionEvent.getSource();
        final Stage stage = (Stage) node.getScene().getWindow();
        return (Stage) stage.getScene().getWindow();
    }
}
