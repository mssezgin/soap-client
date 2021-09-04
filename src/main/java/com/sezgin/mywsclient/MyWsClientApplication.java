package com.sezgin.mywsclient;

import com.sezgin.mywsclient.wsdl.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class MyWsClientApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UserMessageConfiguration.class);
        UserMessageClient client = context.getBean(UserMessageClient.class);
        Scanner scanner = new Scanner(System.in);

        // login
        System.out.println("Login");
        LoginResponse loginResponse = client.login();
        if (loginResponse.getClient() == null) {
            System.out.println("Could not login.");
            return;
        }
        System.out.println("Logged in.");
        System.out.println("SessionID: " + loginResponse.getClient().getSessionID());
        System.out.println("UserID:    " + loginResponse.getClient().getUserid());
        client.setClientSoapHeaders(loginResponse.getClient());

        // create user
        System.out.println("Create user?");
        if (!scanner.nextLine().equals("q")) {
            CreateUserResponse createUserResponse = client.createUser();
            if (createUserResponse.getUserid() == null) {
                System.out.println("Could not create user.");
            } else {
                System.out.println("User created. UserID: " + createUserResponse.getUserid());
            }
        }

        // get userid
        System.out.println("Get userid?");
        if (!scanner.nextLine().equals("q")) {
            GetUserIDByUsernameResponse getUserIDByUsernameResponse = client.getUserIDByUsername();
            if (getUserIDByUsernameResponse.getUserid() == null) {
                System.out.println("Could not find user.");
            } else {
                System.out.println("UserID:   " + getUserIDByUsernameResponse.getUserid());
            }
        }

        // send message
        System.out.println("Send message?");
        if (!scanner.nextLine().equals("q")) {
            SendMessageResponse sendMessageResponse = client.sendMessage();
            if (sendMessageResponse.getMsgid() == null) {
                System.out.println("Could not send message.");
            } else {
                System.out.println("Message sent. MessageID: " + sendMessageResponse.getMsgid());
            }
        }

        // logout
        System.out.println("Logout");
        scanner.nextLine();
        LogoutResponse logoutResponse = client.logout();
        if (logoutResponse.getUserid() == null) {
            System.out.println("Could not logout.");
            return;
        }
        System.out.println("Logged out.");
        System.out.println("UserID:   " + logoutResponse.getUserid());
    }
}
