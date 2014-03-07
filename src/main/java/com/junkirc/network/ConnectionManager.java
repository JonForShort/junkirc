package com.junkirc.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum ConnectionManager {

    INSTANCE;

    private Map<Integer, Connection> mContexts = new HashMap<Integer, Connection>();

    public Connection createConnection(final String host, final int port, String user) throws IOException {
        return new Connection(host, port, user);
    }
}
