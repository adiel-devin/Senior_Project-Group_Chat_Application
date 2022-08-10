package com.dev.chatclient.userInfo;

import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PersonsChatGroups
{
    private int personId;
    private ArrayList<String> chatGroupHistory;
    private ArrayList<String> allChatGroups;

    public PersonsChatGroups(int personId) {
        this.personId = personId;
        this.chatGroupHistory = new ArrayList<>();
        this.allChatGroups = new ArrayList<>();
    }

    public ArrayList<String> getChatGroupHistory() {
        return chatGroupHistory;
    }

    public void setBothGroupLists() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        this.chatGroupHistory.clear();
        this.allChatGroups.clear();

        if(conn == null)
        {
            System.out.println("Error! Connection to Database not established!");
        }
        else
        {
            String query = "SELECT * FROM chatgroup ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                String temp = "";
                if(rs.getString("status").charAt(0) == '+')
                {
                    temp = "public";
                }
                else
                {
                    temp = "private";
                }

                this.allChatGroups.add(String.format("%s   -   %s   -   Creation Date: %s\n",
                        rs.getString("groupname"), temp, rs.getString("creation_date")));
            }

            ArrayList<String> tempList = new ArrayList<>();
            query = QueryStrings.personsGroupsQuery(personId);
            rs = stmt.executeQuery(query);
            while(rs.next())
            {
                tempList.add(rs.getString("groupname"));
            }

            for(int i=0; i<tempList.size(); i++)
            {
                for(int j=0; j<allChatGroups.size(); j++)
                {
                    String[] splited = allChatGroups.get(j).split("\\s+");
                    if(tempList.get(i).equals(splited[0]))
                    {
                        chatGroupHistory.add(allChatGroups.get(j));
                        allChatGroups.remove(j);
                    }
                }
            }
        }
        conn.close();
    }

    public ArrayList<String> getAllChatGroups() {
        return allChatGroups;
    }

}
