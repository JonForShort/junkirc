package com.junkirc;

import com.junkirc.network.Connection;
import com.junkirc.network.ConnectionManager;
import com.junkirc.ui.ChatWindowDialog;
import com.junkirc.ui.ConnectDialog;

import java.io.IOException;

public class Entry {

    public static void main(String[] args) {

        ConnectDialog dialog = new ConnectDialog();
        dialog.pack();
        dialog.setVisible(true);

        if (!dialog.isClosedByCancel()) {
            try {
                final Connection connection = ConnectionManager.INSTANCE.createConnection(dialog.getHost(), dialog.getPort(), dialog.getUser());
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
