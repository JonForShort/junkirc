package com.github.thejunkjon.junkirc.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

class Connection {

    private final String server;
    private final int port;
    private final ReadRunner readRunner = new ReadRunner();

    private Socket socket;
    private OutputStream writer;
    private BufferedReader reader;

    private volatile OnDataAvailableListener mListener = data -> {
    };

    interface OnDataAvailableListener {
        void onDataAvailable(final byte[] data);
    }

    protected void setOnDataAvailableListener(final OnDataAvailableListener listener) {
        mListener = listener;
    }

    Connection(final String server, final int port) {
        this.server = server;
        this.port = port;
    }

    protected void open() throws IOException {
        socket = new Socket(server, port);
        writer = socket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        final Thread readerThread = new Thread(readRunner);
        readerThread.start();
    }

    protected void close() throws IOException {
        readRunner.stop();
        closeConnectionObjects();
    }

    private void closeConnectionObjects() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    protected void write(final byte[] data) throws IOException {
        writer.write(data);
        writer.flush();
    }

    private class ReadRunner implements Runnable {

        private volatile boolean mContinueToRun = true;

        public void stop() {
            mContinueToRun = false;
        }

        @Override
        public void run() {
            while (mContinueToRun) {
                try {
                    final String line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    mListener.onDataAvailable(line.getBytes());
                } catch (IOException e) {
                    mContinueToRun = false;
                }
            }
        }
    }
}
