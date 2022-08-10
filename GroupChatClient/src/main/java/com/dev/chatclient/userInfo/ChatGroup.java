package com.dev.chatclient.userInfo;

import com.dev.chatclient.utils.AES;
import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChatGroup
{
    private int chatGroupId;
    private String groupName;
    private char status;
    private String creationDate;
    private ArrayList<String> messages;
    private ArrayList<String> users;

    public ChatGroup(int chatGroupId, String groupName, char status, String creationDate) {
        this.chatGroupId = chatGroupId;
        this.groupName = groupName;
        this.status = status;
        this.creationDate = creationDate;
        this.messages = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public int getChatGroupId() {
        return chatGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public char getStatus() {
        return status;
    }

    public ArrayList<String> getMessages() {
        return this.messages;
    }

    public void setMessagesAndUsers() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        this.messages.clear();
        this.users.clear();

        if(conn == null)
        {
            this.messages.add("Error fetching messages from database!");
            this.users.add("Error fetching users from database!");
        }

        else
        {
            String query = QueryStrings.getAllMessagesQuery(this.chatGroupId);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                String decMessage = AES.decrypt(rs.getString("message"),
                        Integer.toString(this.getChatGroupId()));

                String message = String.format("%s   -   %s   -   %s%s",
                        rs.getString("username"),
                        decMessage,
                        "sent: ",
                        rs.getString("time_sent"));
                this.messages.add(message);
            }

            query = QueryStrings.getAllUsersInAGroupQuery(this.chatGroupId);
            rs = stmt.executeQuery(query);

            while(rs.next())
            {
                String temp = "";
                if(rs.getString("status").equals("+"))
                {
                    temp = " - Online";
                }
                else
                {
                    temp = " - Offline";
                }
                this.users.add(rs.getString("username") + temp);
            }
        }
        conn.close();
    }

    public ArrayList<String> getUsers() {
        return this.users;
    }

    @Override
    public String toString() {
        String temp = "";
        if(this.status == '+')
        {
            temp = "public";
        }
        else
        {
            temp = "private";
        }

        return String.format("%s   -   %s   -   Creation Date: %s\n",
                this.groupName, temp, this.creationDate);
    }
}
