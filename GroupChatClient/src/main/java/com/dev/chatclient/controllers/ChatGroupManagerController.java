package com.dev.chatclient.controllers;

import com.dev.chatclient.userInfo.ChatGroup;
import com.dev.chatclient.userInfo.Person;
import com.dev.chatclient.userInfo.PersonsChatGroups;
import com.dev.chatclient.utils.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

public class ChatGroupManagerController
{
    private Person person;
    PersonsChatGroups personsChatGroups;

    @FXML
    private ListView<String> allChatGroupsList;

    @FXML
    private ListView<String> chatGroupHistoryList;

    @FXML
    private Text redFlagText;

    @FXML
    private Text userWelcomeText;

    @FXML
    private TextField passwordForJoin;

    public ChatGroupManagerController(Person person) {
        this.person = person;
        this.personsChatGroups = new PersonsChatGroups(this.person.getPersonId());
    }

    @FXML
    public void initialize() throws SQLException {
        this.personsChatGroups.setBothGroupLists();
        userWelcomeText.setText(String.format("Welcome %s!", this.person.getUsername()));

        chatGroupHistoryList.getItems().addAll(this.personsChatGroups.getChatGroupHistory());

        allChatGroupsList.getItems().addAll(this.personsChatGroups.getAllChatGroups());

        Collections.sort(chatGroupHistoryList.getItems());
        Collections.sort(allChatGroupsList.getItems());

    }

    @FXML
    void createChatGroupButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToChannelCreation(event, this.person);
    }

    @FXML
    void deleteChatGroupButton(ActionEvent event) throws SQLException, IOException {
        String selectedH = String.valueOf(chatGroupHistoryList.getSelectionModel().getSelectedItems());
        redFlagText.setText(ChatGroupManager.deleteChatgroup(this.person, this.personsChatGroups, selectedH));
        if(redFlagText.getText().charAt(0) == 'E')
        {
            // an error has occured
        }
        else
        {
            SceneChanger.changeToGroupChatManager(event, this.person);
        }
    }

    @FXML
    void joinChatGroupButton(ActionEvent event) throws IOException, SQLException {
        String selectedH = String.valueOf(chatGroupHistoryList.getSelectionModel().getSelectedItems());
        String selected = String.valueOf(allChatGroupsList.getSelectionModel().getSelectedItems());
        String password = passwordForJoin.getText();
        ChatGroup chatGroup = ChatGroupManager.joinChatgroup(this.person, this.personsChatGroups, selected, selectedH, password);

        if(chatGroup == null)
        {
            redFlagText.setText("Error! Could not join the chatgroup!");
        }
        else
        {
            SceneChanger.changeToMessaging(event, chatGroup, this.person);
        }

    }

    @FXML
    void removeChatGroupButton(ActionEvent event) throws SQLException, IOException {
        String selectedH = String.valueOf(chatGroupHistoryList.getSelectionModel().getSelectedItems());
        redFlagText.setText(ChatGroupManager.removeChatgroup(this.person, personsChatGroups, selectedH));
        if(redFlagText.getText().charAt(0) == 'E')
        {
            // an error has occured
        }
        else
        {
            SceneChanger.changeToGroupChatManager(event, this.person);
        }
    }

    @FXML
    void logoutButtonPress(ActionEvent event) throws IOException, SQLException {
        Login.logout(this.person);
        SceneChanger.changeToLogin(event);
    }

    @FXML
    void refreshButtonPress(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToGroupChatManager(event, this.person);
    }

    @FXML
    void dmButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToDmManager(event, this.person);
    }

}
