package com.github.thejunkjon.junkirc;

import com.github.thejunkjon.junkirc.network.Connection;
import com.github.thejunkjon.junkirc.network.ConnectionManager;

import java.io.IOException;

import static java.lang.Runtime.getRuntime;

public class Main {

    public static void main(final String[] args) {

        if (args.length < 3) {
            System.out.println("Please specify the host, port and user.");
            return;
        }

        try {
            final Connection connection = ConnectionManager.INSTANCE.createConnection(
                    args[0], Integer.valueOf(args[1]), args[2]);
            connection.setOnDataAvailableListener(data -> System.out.println(new String(data)));

            getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
