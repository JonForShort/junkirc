package com.github.thejunkjon.junkirc;

import com.github.thejunkjon.junkirc.network.Connection;
import com.github.thejunkjon.junkirc.network.ConnectionManager;
import com.github.thejunkjon.junkirc.ui.ChatWindowDialog;
import com.github.thejunkjon.junkirc.ui.ConnectDialog;

import java.io.IOException;

public class Entry {

    public static void main(String[] args) {

        ConnectDialog dialog = new ConnectDialog();
        dialog.pack();
        dialog.setVisible(true);

        if (!dialog.isClosedByCancel()) {
            try {
                final Connection connection = ConnectionManager.INSTANCE.createConnection(
                        dialog.getHost(), dialog.getPort(), dialog.getUser());
                ChatWindowDialog chatWindowDialog = new ChatWindowDialog(connection);
                chatWindowDialog.pack();
                chatWindowDialog.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
