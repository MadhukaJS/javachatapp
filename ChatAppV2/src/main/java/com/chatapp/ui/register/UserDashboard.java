package com.chatapp.ui.register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserDashboard extends JFrame {
    private JButton profileButton;
    private JPanel dashpanel;

    //view chats
    private JButton SubBtn;
    private JPanel SubPanel;
    private JTable table1;


    public UserDashboard() {

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(dashpanel);
        this.pack();

    
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDashboard.super.dispose();
                JFrame frame6 = new UserProfile();
                frame6.setVisible(true);
                frame6.setSize(400, 500);
            }
        });


    }

}
