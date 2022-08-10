package com.dev.chatclient.controllers;

import com.dev.chatclient.utils.Login;
import com.dev.chatclient.userInfo.Person;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController
{
    @FXML
    private Label blankLabel;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField usernameInput;

    @FXML
    void loginAuthentication(ActionEvent event) throws SQLException, IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        Person person = Login.loginAuthentication(username, password);

        if(person == null)
        {
            blankLabel.setText("Login failed!");
        }
        else
        {
            SceneChanger.changeToGroupChatManager(event, person);
        }

    }

    @FXML
    void registerAuthentication(ActionEvent event) throws SQLException
    {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        blankLabel.setText(Login.registrationAuthentication(username, password));
    }

}