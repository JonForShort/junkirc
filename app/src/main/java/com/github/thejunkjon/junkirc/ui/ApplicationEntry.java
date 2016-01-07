package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.model.IrcServerConfiguration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApplicationEntry extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEntry.class);
    private static final String WINDOW_TITLE = "Connect To IRC Server";
    private static final String FXML_CONNECT_DIALOG = "ConnectDialog.fxml";

    public void start(Stage primaryStage) throws Exception {

        final URL connectDialogFxmlUrl = getClass().getResource(FXML_CONNECT_DIALOG);
        final Parent root = FXMLLoader.load(connectDialogFxmlUrl);
        primaryStage.setTitle(WINDOW_TITLE);

        final Scene scene = new Scene(root);
        buildIrcServersList(scene);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void buildIrcServersList(Scene scene) {
        final ListView<String> listView = (ListView<String>) scene.lookup("#ircServersList");
        final List<IrcServerConfiguration> configurationsForIrcServers = getConfigurationsForIrcServers();
        final ObservableList<String> observableList = FXCollections.observableArrayList();
        for (final IrcServerConfiguration ircServerConfiguration : configurationsForIrcServers) {
            observableList.add(ircServerConfiguration.name);
        }

        listView.setItems(observableList);
        listView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(final ObservableValue<? extends String> observableValue,
                                        final String oldValue,
                                        final String newValue) {
                        final TextField host = (TextField) scene.lookup("#host");
                        final TextField port = (TextField) scene.lookup("#port");
                        final IrcServerConfiguration ircServerConfiguration =
                                getMatchingIrcServerConfigurationFor(newValue);
                        host.setText(ircServerConfiguration.host);
                        port.setText(String.valueOf(ircServerConfiguration.port));
                    }

                    private IrcServerConfiguration getMatchingIrcServerConfigurationFor(String newValue) {
                        for (IrcServerConfiguration ircServerConfiguration : configurationsForIrcServers) {
                            if (ircServerConfiguration.name.equals(newValue)) {
                                return ircServerConfiguration;
                            }
                        }
                        return new IrcServerConfiguration();
                    }
                });
    }

    public List<IrcServerConfiguration> getConfigurationsForIrcServers() {
        final List<IrcServerConfiguration> configurationsForIrcServers = new ArrayList<>();
        try (final InputStream defaultIrcServerJsonFileStream =
                     ApplicationEntry.class.getClassLoader().getResourceAsStream("defaultIrcServers.json")) {
            final Type listType = new TypeToken<ArrayList<IrcServerConfiguration>>() {
            }.getType();
            configurationsForIrcServers.addAll(
                    new Gson().fromJson(new InputStreamReader(defaultIrcServerJsonFileStream, "UTF-8"), listType));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return configurationsForIrcServers;
    }
}