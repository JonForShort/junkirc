package com.github.thejunkjon.junkirc.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationEntry extends Application {

    public void start(Stage primaryStage) throws Exception {
        new ConnectDialog(primaryStage).show();
    }
}