package com.github.thejunkjon.junkirc.network;

import com.github.thejunkjon.junkirc.network.Connection.OnDataAvailableListener;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public final class InternetRelayChatConnection extends Connection implements AutoCloseable, OnDataAvailableListener {

    private final Builder builder;

    public static class Builder {

        private final OnMessageReceivedListener listener;
        private final String server;
        private final int port;

        public Builder(final String server,
                       final int port,
                       final OnMessageReceivedListener listener) {
            this.server = server;
            this.port = port;
            this.listener = listener;
        }

        public InternetRelayChatConnection build() {
            return new InternetRelayChatConnection(this);
        }
    }

    private InternetRelayChatConnection(final Builder builder) {
        super(builder.server, builder.port);
        this.builder = builder;
        if (builder.listener != null) {
            setOnDataAvailableListener(this);
        }
    }

    public interface OnMessageReceivedListener {
        void onMessageReceived(final String message);
    }

    @Override
    public void open() throws IOException {
        super.open();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    @Override
    public void onDataAvailable(final byte[] data) {
        builder.listener.onMessageReceived(new String(data, StandardCharsets.US_ASCII));
    }

    public void writeCommand(final String format, final Object... objects) throws IOException {
        final String command = String.format(Locale.US, format + "\n", objects);
        write(command.getBytes());
    }

    public void writeAwayCommand(final String awayMessage) throws IOException {
        writeCommand("AWAY %s", awayMessage);
    }

    public void writeNickCommand(final String nickname) throws IOException {
        writeCommand("NICK %s", nickname);
    }

    public void writeUserCommand(final String username,
                                 final int mode,
                                 final String realName) throws IOException {
        writeCommand("USER %s %d * : %s", username, mode, realName);
    }

    public void writePrivMsgCommand(final String target,
                                    final String message) throws IOException {
        writeCommand("PRIVMSG %s %s", target, message);
    }

    public void writeQuitCommand(final String message) throws IOException {
        writeCommand("QUIT %s", message);
    }
}
