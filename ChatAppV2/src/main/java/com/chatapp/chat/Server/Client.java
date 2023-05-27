package com.chatapp.chat.Server;

import com.chatapp.chat.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public void runClient(){
        String chat = "";

        Chat chatClient = null;
        Scanner input = new Scanner(System.in);

        try {
            chatClient = (Chat) Naming.lookup("rmi://192.168.8.119:3000/chat");

            Observer remoteUser1 = new RemoteUser("User1");
            Observer remoteUser2 = new RemoteUser("User2");

            chatClient.registerObserver(remoteUser1);
            chatClient.registerObserver(remoteUser2);

            Message msg = new Message();

            while (true){
                System.out.println("Enter the message : ");
                String ms = input.nextLine();
                msg.setMessage(ms);
                chatClient.notifyObservers(msg);
//                System.out.println(chat);
            }




        } catch (RemoteException | MalformedURLException | NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client().runClient();
    }

}
