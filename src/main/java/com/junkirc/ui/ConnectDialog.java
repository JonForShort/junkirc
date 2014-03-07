package com.junkirc.ui;

import javax.swing.*;
import java.awt.event.*;

public class ConnectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField hostTextField;
    private JTextField portTextField;
    private JTextField userTextField;
    private JTextField sleeperTextField;
    private boolean mIsClosedByCancel = false;

    public ConnectDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        mIsClosedByCancel = true;
        dispose();
    }

    public String getHost() {
        return hostTextField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portTextField.getText());
    }

    public String getUser() {
        return userTextField.getText();
    }

    public boolean isClosedByCancel() {
        return mIsClosedByCancel;
    }
}
