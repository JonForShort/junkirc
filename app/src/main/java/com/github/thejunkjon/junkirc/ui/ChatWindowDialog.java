package com.github.thejunkjon.junkirc.ui;

import com.github.thejunkjon.junkirc.network.Connection;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatWindowDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField commandTextField;
    private JTextArea textArea1;
    private final Connection mConnection;

    private final List<String> mCommandHistory = new ArrayList<String>();
    private int mCurrentCommandHistoryEntry = 0;

    public ChatWindowDialog(Connection connection) throws IOException {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        mConnection = connection;
        mConnection.setOnDataAvailableListener(new Connection.OnDataAvailableListener() {
            @Override
            public void onDataAvailable(byte[] data) {
                textArea1.append(new String(data) + "\n");
            }
        });

        registerUserToConnection();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                final String commandText = commandTextField.getText();
                mCommandHistory.add(commandText);

                StringBuffer inputString = new StringBuffer();
                inputString.append(commandText);
                inputString.append('\r');
                inputString.append('\n');
                commandTextField.setText("");
                try {
                    mConnection.write(inputString.toString().getBytes("UTF-8"));
                } catch (IOException e1) {

                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("up pushed");
                if (mCurrentCommandHistoryEntry > 0) {
                    String nextCommand = mCommandHistory.get(mCurrentCommandHistoryEntry);
                    commandTextField.setText(nextCommand);
                    mCurrentCommandHistoryEntry--;
                }
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        contentPane.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("down pushed");
                if (mCurrentCommandHistoryEntry < mCommandHistory.size()) {
                    String nextCommand = mCommandHistory.get(mCurrentCommandHistoryEntry);
                    commandTextField.setText(nextCommand);
                    mCurrentCommandHistoryEntry++;
                }
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void registerUserToConnection() throws IOException {
        final String registerCommand = "USER " + mConnection.getUser() + " " + mConnection.getHost() +
                " " + mConnection.getHost() + " " + mConnection.getUser() + "\r\n";
        mConnection.write(registerCommand.getBytes("UTF-8"));

        final String nickCommand = "NICK sleeper" + "\r\n";
        mConnection.write(nickCommand.getBytes("UTF-8"));
    }

    private void onCancel() {
        try {
            mConnection.close();
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
