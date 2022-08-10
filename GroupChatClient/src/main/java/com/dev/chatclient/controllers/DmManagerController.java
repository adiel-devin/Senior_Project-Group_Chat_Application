package com.dev.chatclient.controllers;

import com.dev.chatclient.utils.DirectMessaging;
import com.dev.chatclient.userInfo.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class DmManagerController
{
    private Person person;

    public DmManagerController(Person person)
    {
        this.person = person;
    }

    @FXML
    public void initialize() throws SQLException {
        usersList.getItems().addAll(DirectMessaging.getAllUsersInTheDm(this.person.getPersonId()));
    }

    @FXML
    private ListView<String> usersList;

    @FXML
    private Text redFlagText;

    @FXML
    void leaveButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToGroupChatManager(event, this.person);
    }

    @FXML
    void refreshButton(ActionEvent event) throws SQLException, IOException {
        SceneChanger.changeToDmManager(event, this.person);
    }

    @FXML
    void sendMessageButton(ActionEvent event) throws SQLException, IOException {
        int ufId[] = DirectMessaging.establishDirectMessage(this.person, String.valueOf(usersList.getSelectionModel().getSelectedItems()));
        SceneChanger.changeToDirectMessaging(event, this.person, ufId);
    }

    @FXML
    void deleteDmButton(ActionEvent event) throws SQLException, IOException {
        redFlagText.setText(DirectMessaging.deleteTheDirectMessage(this.person,
                String.valueOf(usersList.getSelectionModel().getSelectedItems())));
        if(redFlagText.getText().charAt(0) == 'E')
        {
            // an error has occurred
        }
        else
        {
            SceneChanger.changeToDmManager(event, this.person);
        }
    }

}
