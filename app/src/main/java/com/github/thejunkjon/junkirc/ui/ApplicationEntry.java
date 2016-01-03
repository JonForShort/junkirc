package com.github.thejunkjon.junkirc.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ApplicationEntry extends Application {

    private static final String WINDOW_TITLE = "Connect";
    private static final String FXML_CONNECT_DIALOG = "ConnectDialog.fxml";

    public void start(Stage primaryStage) throws Exception {

        final URL connectDialogFxmlUrl = getClass().getResource(FXML_CONNECT_DIALOG);
        final Parent root = FXMLLoader.load(connectDialogFxmlUrl);
        primaryStage.setTitle(WINDOW_TITLE);

        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
