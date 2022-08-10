package com.dev.chatclient.controllers;

import com.dev.chatclient.userInfo.ChatGroup;
import com.dev.chatclient.userInfo.Person;
import com.dev.chatclient.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class MessagingController
{
    private ChatGroup chatGroup;
    private Person person;

    public MessagingController(ChatGroup chatGroup, Person person)
    {
        this.chatGroup = chatGroup;
        this.person = person;
    }

    @FXML
    private TextField messageText;

    @FXML
    private Text title;

    @FXML
    private Text redFlagText;

    @FXML
    private ListView<String> messagingList;

    @FXML
    private ListView<String> usersList;

    @FXML
    public void initialize() throws SQLException {
        title.setText(chatGroup.getGroupName());
        chatGroup.setMessagesAndUsers();
        messagingList.getItems().addAll(chatGroup.getMessages());
        usersList.getItems().addAll(chatGroup.getUsers());
    }

    @FXML
    void refreshButton(ActionEvent event) throws IOException {
        SceneChanger.changeToMessaging(event, this.chatGroup, this.person);
    }

    @FXML
    void leaveGroupButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToGroupChatManager(event, this.person);

    }

    @FXML
    void sendMessageButton(ActionEvent event) throws SQLException, IOException {
        Messaging.sendMessage(messageText.getText(), this.person, this.chatGroup);
        SceneChanger.changeToMessaging(event, this.chatGroup, this.person);
    }


    @FXML
    void sendDirectMessage(ActionEvent event) throws SQLException, IOException {
        int ufId[] = DirectMessaging.establishDirectMessage(this.person, String.valueOf(usersList.getSelectionModel().getSelectedItems()));
        if(ufId[0] == -1 || ufId[1] == -1)
        {
            redFlagText.setText("Error! A user must be selected in order to dm.");
        }
        else
        {
            SceneChanger.changeToDirectMessaging(event, this.person, ufId);
        }
    }
}
