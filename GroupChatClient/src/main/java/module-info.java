module com.dev.chatclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.dev.chatclient to javafx.fxml;
    exports com.dev.chatclient;
    exports com.dev.chatclient.controllers;
    opens com.dev.chatclient.controllers to javafx.fxml;
}