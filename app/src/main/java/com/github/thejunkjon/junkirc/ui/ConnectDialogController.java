package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.Connection;
import com.github.thejunkjon.junkirc.network.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ConnectDialogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectDialogController.class);

    @FXML
    private TextField nickname;

    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @FXML
    protected void handleConnect(final ActionEvent actionEvent) {
        LOGGER.trace("handleConnect");
        getStageFromActionEvent(actionEvent).close();
        final MainWindow mainWindow = new MainWindow();
        mainWindow.show();

        try {
            final Connection connection = ConnectionManager.INSTANCE.createConnection(host.getText(),
                    Integer.parseInt(port.getText()), nickname.getText());
            mainWindow.addServerCommunicationPane(connection);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
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
