package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.Connection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

import static com.github.thejunkjon.junkirc.utils.ResourceUtils.getResource;

class ServerCommunicationPane {

    private static final String FXML_PANE_SERVER_COMMUNICATION = "fxml/pane/ServerCommunicationPane.fxml";
    private final FXMLLoader fxmlLoader = new FXMLLoader(getResource(FXML_PANE_SERVER_COMMUNICATION));
    private final Connection connection;

    public ServerCommunicationPane(Connection connection) {
        this.connection = connection;
    }

    Node load() throws IOException {
        final Node node = fxmlLoader.load();
        fxmlLoader.<ServerCommunicationPaneController>getController().initialize(connection);
        return node;
    }
}
