package com.junkirc.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Connection implements AutoCloseable {

    private final String mUser;
    private final int mPort;
    private final String mHost;

    private final Socket mSocket;
    private final OutputStream mWriter;
    private final BufferedReader mReader;

    private final Thread mReaderThread;
    private final ReadRunner mReadRunner = new ReadRunner();

    private volatile OnDataAvailableListener mListener = new OnDataAvailableListener() {
        @Override
        public void onDataAvailable(byte[] data) {
            // do nothing.
        }
    };

    public interface OnDataAvailableListener {
        void onDataAvailable(final byte[] data);
    }

    public Connection(String host, int port, String user) throws IOException {
        mHost = host;
        mUser = user;
        mPort = port;

        mSocket = new Socket(mHost, port);
        mWriter = mSocket.getOutputStream();
        mReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

        mReaderThread = new Thread(mReadRunner);
        mReaderThread.start();
    }

    public String getHost() {
        return mHost;
    }

    public int getPort() {
        return mPort;
    }

    public String getUser() {
        return mUser;
    }

    public void setOnDataAvailableListener(final OnDataAvailableListener listener) {
        mListener = listener;
    }

    @Override
    public void close() throws Exception {
        mReadRunner.stop();
        closeConnectionObjects();
    }

    private void closeConnectionObjects() throws IOException {
        mWriter.close();
        mReader.close();
        mSocket.close();
    }

    public void write(final byte[] data) throws IOException {
        mWriter.write(data);
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
                    final String line = mReader.readLine();
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
