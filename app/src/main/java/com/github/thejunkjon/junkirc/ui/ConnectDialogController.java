package com.github.thejunkjon.junkirc.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectDialogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationEntry.class);

    @FXML
    protected void handleConnect(final ActionEvent actionEvent) {
        LOGGER.debug("handleConnect");
    }

    @FXML
    protected void handleCancel(final ActionEvent actionEvent) {
        LOGGER.debug("handleCancel");
    }
}
