package com.sezgin.mywsclient;

import com.sezgin.mywsclient.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import javax.xml.datatype.DatatypeFactory;
import java.math.BigInteger;
import java.util.Scanner;

public class UserMessageClient extends WebServiceGatewaySupport {

    private static final Logger log = LoggerFactory.getLogger(UserMessageClient.class);
    private static final ClientSoapHeaders clientSoapHeaders = new ClientSoapHeaders();

    public void setClientSoapHeaders(ClientObject clientObject) {
        clientSoapHeaders.setClient(clientObject);
    }

    public ClientSoapHeaders getClientSoapHeaders() {
        return clientSoapHeaders;
    }

    public LoginResponse login() {

        Scanner scanner = new Scanner(System.in);
        LoginRequest request = new LoginRequest();
        System.out.print("Username: ");
        request.setUsername(scanner.nextLine());
        System.out.print("Password: ");
        request.setPassword(scanner.nextLine());

        log.info("Requesting: login");
        return (LoginResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SecurityHeader(clientSoapHeaders));
    }

    public CreateUserResponse createUser() {

        Scanner scanner = new Scanner(System.in);
        CreateUserRequest request = new CreateUserRequest();
        UserObject newUser = new UserObject();

        System.out.print("Admin?:    "); // newUser.setAdmin(false);
        newUser.setAdmin(scanner.nextLine().equalsIgnoreCase("true"));
        System.out.print("Username:  "); // newUser.setUsername("javauser");
        newUser.setUsername(scanner.nextLine());
        System.out.print("Password:  "); // newUser.setPassword("qwerty");
        newUser.setPassword(scanner.nextLine());
        System.out.print("E-mail:    "); // newUser.setEmail("user@java.com");
        newUser.setEmail(scanner.nextLine());
        System.out.print("Firstname: "); // newUser.setFirstname("John");
        newUser.setFirstname(scanner.nextLine());
        System.out.print("Lastname:  "); // newUser.setLastname("Smith");
        newUser.setLastname(scanner.nextLine());
        System.out.print("Gender:    "); // newUser.setGender("male");
        newUser.setGender(scanner.nextLine());
        try {
            System.out.print("Birth:     "); // "1975-04-17"
            newUser.setBirth(DatatypeFactory.newInstance().newXMLGregorianCalendar(scanner.nextLine()));
        } catch (Exception e) {
            newUser.setBirth(null);
        }
        request.setUser(newUser);

        log.info("Requesting: createUser");
        return (CreateUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SecurityHeader(clientSoapHeaders));
    }

    public GetUserIDByUsernameResponse getUserIDByUsername() {

        Scanner scanner = new Scanner(System.in);
        GetUserIDByUsernameRequest request = new GetUserIDByUsernameRequest();
        System.out.print("Username: ");
        request.setUsername(scanner.nextLine());

        log.info("Requesting: getUserIDByUsername");
        return (GetUserIDByUsernameResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SecurityHeader(clientSoapHeaders));
    }

    public SendMessageResponse sendMessage() {

        Scanner scanner = new Scanner(System.in);
        SendMessageRequest request = new SendMessageRequest();
        MessageObject newMsg = new MessageObject();

        System.out.println("From:     " + clientSoapHeaders.getClient().getUserid());
        newMsg.setFrom(clientSoapHeaders.getClient().getUserid());
        System.out.print("To:       ");
        newMsg.setTo(new BigInteger(scanner.nextLine()));
        System.out.print("Subject:  ");
        newMsg.setSubject(scanner.nextLine());
        System.out.print("Body:     ");
        newMsg.setBody(scanner.nextLine());
        request.setMessage(newMsg);

        log.info("Requesting: sendMessage");
        return (SendMessageResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SecurityHeader(clientSoapHeaders));
    }

    public LogoutResponse logout() {

        LogoutRequest request = new LogoutRequest();
        request.setUserid(clientSoapHeaders.getClient().getUserid());

        log.info("Requesting: logout");
        return (LogoutResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SecurityHeader(clientSoapHeaders));
    }
}
