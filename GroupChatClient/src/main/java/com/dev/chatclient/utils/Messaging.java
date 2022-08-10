package com.dev.chatclient.utils;

import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;
import com.dev.chatclient.userInfo.ChatGroup;
import com.dev.chatclient.userInfo.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Messaging {
    public static void sendMessage(String message, Person person, ChatGroup chatGroup) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();

        if(conn == null)
        {
            System.out.println("Could not establish connection to database!");
        }

        else
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();

            String query = QueryStrings.getPersonsInChatGroupIdQuery(chatGroup.getChatGroupId(), person.getPersonId());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int temp = rs.getInt("persons_in_chatgroup_id");
            String encMessage = AES.encrypt(message, Integer.toString(chatGroup.getChatGroupId()));
            stmt.executeUpdate(QueryStrings.insertMessageQuery(temp, encMessage, dtf.format(now)));
        }

        conn.close();
    }
}
