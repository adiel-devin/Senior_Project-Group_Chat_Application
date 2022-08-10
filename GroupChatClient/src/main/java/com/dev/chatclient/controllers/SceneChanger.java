package com.dev.chatclient.controllers;

import com.dev.chatclient.App;
import com.dev.chatclient.userInfo.ChatGroup;
import com.dev.chatclient.userInfo.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SceneChanger
{
    public static void changeToLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 450, 450);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeToGroupChatManager(ActionEvent event, Person person) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("GroupChatManager.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChatGroupManagerController controller = new ChatGroupManagerController(person);

        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 1000, 667);
        stage.setTitle("Group Chat Manager");
        stage.setScene(scene);

        stage.show();

    }

    public static void changeToMessaging(ActionEvent event, ChatGroup chatGroup, Person person) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Messaging.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MessagingController controller = new MessagingController(chatGroup, person);

        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 880, 566);
        stage.setTitle("Messaging");
        stage.setScene(scene);

        stage.show();

    }

    public static void changeToDirectMessaging(ActionEvent event, Person person, int[] ufId) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("DirectMessaging.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DirectMessagingController controller = new DirectMessagingController(person, ufId);

        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 880, 556);
        stage.setTitle("Direct Messaging");
        stage.setScene(scene);

        stage.show();

    }

    public static void changeToDmManager(ActionEvent event, Person person) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("DmManager.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        DmManagerController controller = new DmManagerController(person);

        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        stage.setTitle("Direct Messaging Manager");
        stage.setScene(scene);

        stage.show();

    }

    public static void changeToChannelCreation(ActionEvent event, Person person) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ChannelCreation.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ChannelCreationController controller = new ChannelCreationController(person);

        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), 700, 450);
        stage.setTitle("Group Chat Creation");
        stage.setScene(scene);

        stage.show();

    }

}
