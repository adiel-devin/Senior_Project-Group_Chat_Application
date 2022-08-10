package com.dev.chatclient.controllers;

import com.dev.chatclient.utils.ChatGroupManager;
import com.dev.chatclient.userInfo.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class ChannelCreationController
{
    private Person person;

    public ChannelCreationController(Person person)
    {
        this.person = person;
    }

    @FXML
    public void initialize() throws SQLException {
        allChatGroupsList.getItems().addAll(ChatGroupManager.getAllChannels());
        Collections.sort(allChatGroupsList.getItems());
    }

    @FXML
    private ListView<String> allChatGroupsList;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private Text redFlagText;

    @FXML
    void createChannelButton(ActionEvent event) throws SQLException, IOException {
        String groupName = nameField.getText();
        String password = passwordField.getText();
        redFlagText.setText(ChatGroupManager.createChatgroup(this.person, groupName, password));
        if(redFlagText.getText().charAt(0) == 'E')
        {
            // An error has occured
        }
        else
        {
            SceneChanger.changeToChannelCreation(event, this.person);
        }
    }

    @FXML
    void leaveButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToGroupChatManager(event, this.person);
    }

    @FXML
    void refreshButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToChannelCreation(event, this.person);
    }
}
