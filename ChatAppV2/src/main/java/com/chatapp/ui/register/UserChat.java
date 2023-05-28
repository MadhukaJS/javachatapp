package com.chatapp.ui.register;

import com.chatapp.chat.*;
import com.chatapp.chat.Server.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class UserChat {
    private JPanel uchat;

    Client client = new Client();
    Chat chatClient = null;

    private String lastms = "";

    JFrame frame = new JFrame("uchat");
    JList<Message> list = new JList<>();
    DefaultListModel<Message> model = new DefaultListModel<>();

    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JTextField textField = new JTextField(20);
    JButton button = new JButton("Send");

    public UserChat() {
        client.runClient();

        try {
            chatClient = client.chatClient;

            list.setModel(model);
            list.setCellRenderer(new MessageListRenderer());

            panel.setLayout(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(textField, BorderLayout.CENTER);
            panel.add(button, BorderLayout.EAST);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String messagebox = textField.getText();
                    Message message = new Message();
                    message.setMessage(textField.getText());
                    message.setUserId(10);
                    message.setNickname(User.getNickname());
                    message.setAvatar(User.getAvatar());
                    try {
                        chatClient.sendMessage(message);
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                    textField.setText("");
                }
            });

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new JScrollPane(list), BorderLayout.CENTER);
            frame.add(panel, BorderLayout.SOUTH);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);


            Thread updateMessagesThread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Message message = chatClient.broadcast();
                            if (lastms.equals(message.getMessage())){
                                continue;
                            }else {
                                lastms = message.getMessage();
                                SwingUtilities.invokeLater(() -> model.addElement(message));
                            }
                            Thread.sleep(100);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            updateMessagesThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMessages(){
        try {
            Observer remoteUser1 = new RemoteUser("User1");
            chatClient.registerObserver(remoteUser1);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserChat::new);
    }

    private class MessageListRenderer extends JPanel implements ListCellRenderer<Message> {
        private JLabel nicknameLabel;
        private JLabel messageLabel;
        private JLabel avatarLabel;

        public MessageListRenderer() {
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            setLayout(new BorderLayout());

            avatarLabel = new JLabel();
            nicknameLabel = new JLabel();
            messageLabel = new JLabel();

            add(avatarLabel, BorderLayout.WEST);
            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.add(nicknameLabel, BorderLayout.NORTH);
            textPanel.add(messageLabel, BorderLayout.CENTER);
            add(textPanel, BorderLayout.CENTER);
        }

        public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nicknameLabel.setText(message.getNickname());
            messageLabel.setText(message.getMessage());

            if (message.getAvatar() != null) {
                ImageIcon avatarIcon = new ImageIcon(message.getAvatar());
                Image scaledAvatar = avatarIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                avatarIcon = new ImageIcon(scaledAvatar);
                avatarLabel.setIcon(avatarIcon);
            } else {
                // Set a default avatar image if no avatar is available
                ImageIcon defaultAvatarIcon = new ImageIcon("com/chatapp/image/user.png");
                avatarLabel.setIcon(defaultAvatarIcon);
            }

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }
    }
}