package com.dev.chatclient.controllers;

import com.dev.chatclient.utils.DirectMessaging;
import com.dev.chatclient.userInfo.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DirectMessagingController {
    private Person person;
    private int[] ufId;


    public DirectMessagingController(Person person, int[] ufId)
    {
        this.person = person;
        this.ufId = ufId;
    }

    @FXML
    public void initialize() throws SQLException {
        messagesList.getItems().addAll(DirectMessaging.getAllDirectMessages(this.ufId[0], this.ufId[1]));
        ArrayList<String> list = new ArrayList<>(DirectMessaging.getFriendsUsername(this.ufId[0]));
        String temp = "";
        if(list.get(1).equals("+"))
        {
            temp = " - is online";
        }
        else
        {
            temp = " - is offline";
        }
        titleText.setText(list.get(0) + temp);
    }

    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messagesList;

    @FXML
    private Text titleText;

    @FXML
    void leaveButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToDmManager(event, this.person);
    }

    @FXML
    void refreshButton(ActionEvent event) throws IOException {
        SceneChanger.changeToDirectMessaging(event, this.person, this.ufId);
    }

    @FXML
    void sendMessageButton(ActionEvent event) throws SQLException, IOException {
        DirectMessaging.sendMessage(messageField.getText(), this.ufId[0], this.ufId[1]);
        SceneChanger.changeToDirectMessaging(event, this.person, this.ufId);
    }
}
