package com.dev.chatclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class HomeController {
    @FXML
    void enterButton(ActionEvent event) throws IOException {
        SceneChanger.changeToLogin(event);
    }

}
