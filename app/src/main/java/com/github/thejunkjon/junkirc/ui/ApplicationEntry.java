package com.github.thejunkjon.junkirc.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ApplicationEntry extends Application {

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final URL connectDialogUrl = getClass().getResource("ConnectDialog.fxml");
        final Parent root = FXMLLoader.load(connectDialogUrl);
        primaryStage.setTitle("Connect");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
