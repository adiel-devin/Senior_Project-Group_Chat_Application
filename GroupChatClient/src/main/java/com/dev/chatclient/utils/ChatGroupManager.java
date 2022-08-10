package com.dev.chatclient.utils;

import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;
import com.dev.chatclient.userInfo.ChatGroup;
import com.dev.chatclient.userInfo.Person;
import com.dev.chatclient.userInfo.PersonsChatGroups;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatGroupManager
{
    public static String createChatgroup(Person person, String groupName, String password) throws SQLException {
        String message = "";
        Connection conn = DBConnection.getInstance().getConnection();
        groupName = groupName.replaceAll("\\s+", "");

        if(conn == null)
        {
            message = "Error! Connection to Database not established!";
        }

        else
        {
            String query = QueryStrings.checkIfGroupnameIsUsed(groupName);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();

            if(rs.getString(1).equals("1"))
            {
                message = "Error. Group name is already used!";
            }

            else
            {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
                LocalDateTime now = LocalDateTime.now();
                int groupId = -1;

                // for public groups
                if(password.equals(""))
                {
                    query = QueryStrings.insertChatGroupWithoutPassword(groupName, dtf.format(now));
                    stmt.executeUpdate(query);

                }

                // for private groups
                else
                {
                    query = QueryStrings.insertChatGroupWithPassword(groupName, password, dtf.format(now));
                    stmt.executeUpdate(query);
                }
                query = QueryStrings.getChatgroupId(groupName);
                rs = stmt.executeQuery(query);  // create chat group
                // get new chatgroups id
                while(rs.next())
                {
                    groupId = rs.getInt("chatgroup_id");
                }
                // add person to chatgroup and set as admin
                query = QueryStrings.insertPersonInChatGroupAsAdmin(person.getPersonId(), groupId);
                stmt.executeUpdate(query);
                message = "Your chat group '" + groupName + "' has been created!";
            }
        }

        conn.close();
        return message;
    }

    public static ChatGroup joinChatgroup(Person person, PersonsChatGroups personsChatGroups, String selected,
                                          String selectedH, String password) throws SQLException {
        ChatGroup chatGroup = null;
        Connection conn = DBConnection.getInstance().getConnection();

        if(conn == null)
        {
            System.out.println("Error! Connection to database failed!");
        }

        else
        {
            Statement stmt = conn.createStatement();

            if(selectedH.equals("[]") && selected.equals("[]"))
            {
                System.out.println("Error! User didn't select anything from the list");
            }

            // user did NOT select from their group chat history list
            else if(selectedH.equals("[]"))
            {
                // parse the selected so that only the username is left from the list string
                String[] splited = selected.split("\\s+");

                String query = QueryStrings.getChatgroupInfoUsingGroupName(splited[0].substring(1));
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                chatGroup = new ChatGroup(rs.getInt("chatgroup_id"), rs.getString("groupname"),
                        rs.getString("status").charAt(0), rs.getString("creation_date"));

                if(chatGroup.getStatus() == '-')
                {

                    query = QueryStrings.chatgroupVerificationQuery(chatGroup.getChatGroupId(), password);
                    rs = stmt.executeQuery(query);
                    rs.next();
                    // if the password matches the chatgroup
                    if(rs.getString(1).equals("1"))
                    {
                        query = QueryStrings.checkIfPersonHasAPersonsInChatgroupIdQuery(person.getPersonId(),
                                chatGroup.getChatGroupId());
                        rs = stmt.executeQuery(query);
                        rs.next();

                        // if the user already has history with chatgroup, just update from '-' to '+'
                        if(rs.getString(1).equals("1"))
                        {
                            query = QueryStrings.updatePersonInChatGroupJoining(person.getPersonId(), chatGroup.getChatGroupId());
                        }

                        else
                        {
                            query = QueryStrings.insertPersonInChatGroup(person.getPersonId(),
                                    chatGroup.getChatGroupId());
                        }
                        stmt.executeUpdate(query);
                    }
                    // invalid password
                    else
                    {
                        chatGroup = null;
                    }

                }

                else
                {
                        query = QueryStrings.checkIfPersonHasAPersonsInChatgroupIdQuery(person.getPersonId(),
                                chatGroup.getChatGroupId());
                        rs = stmt.executeQuery(query);
                        rs.next();

                        // if the user already has history with chatgroup, just update from '-' to '+'
                        if(rs.getString(1).equals("1"))
                        {
                            query = QueryStrings.updatePersonInChatGroupJoining(person.getPersonId(), chatGroup.getChatGroupId());
                        }

                        else
                        {
                            query = QueryStrings.insertPersonInChatGroup(person.getPersonId(),
                                    chatGroup.getChatGroupId());
                        }
                        stmt.executeUpdate(query);

                }
            }

            // user selected from history list
            else
            {
                String[] splitedH = selectedH.split("\\s+");
                String query = QueryStrings.getChatgroupInfoUsingGroupName(splitedH[0].substring(1));
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                chatGroup = new ChatGroup(rs.getInt("chatgroup_id"), rs.getString("groupname"),
                        rs.getString("status").charAt(0), rs.getString("creation_date"));
            }
        }

        conn.close();
        return chatGroup;
    }

    public static String deleteChatgroup(Person person, PersonsChatGroups personsChatGroups, String selectedH) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        String message = "";
        ChatGroup chatGroup = null;

        if(conn == null)
        {
            message = "Error! Connection to Database not established!";
        }

        else
        {
            if(selectedH.equals("[]"))
            {
                message = "Error! A group must be selected from history list in order to delete";
            }
            else
            {
                String[] splited = selectedH.split("\\s+");

                String query = QueryStrings.getChatgroupInfoUsingGroupName(splited[0].substring(1));
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                chatGroup = new ChatGroup(rs.getInt("chatgroup_id"), rs.getString("groupname"),
                        rs.getString("status").charAt(0), rs.getString("creation_date"));


                query = QueryStrings.checkIfPersonIsAdminOfChatgroup(person.getPersonId(),
                        chatGroup.getChatGroupId());
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                rs.next();

                // if person is admin delete the chatgroup
                if(rs.getString(1).equals("1"))
                {
                    query = QueryStrings.deleteChatgroupQuery(chatGroup.getChatGroupId());
                    stmt.executeUpdate(query);
                    message = "Chatgroup has been successfully deleted";
                }

                else
                {
                    message = "Error! Cannot delete! Must be an admin!";
                }
            }
        }

        conn.close();
        return message;
    }

    public static String removeChatgroup(Person person, PersonsChatGroups personsChatGroups, String selectedH) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String message = "";
        ChatGroup chatGroup = null;

        if(conn == null)
        {
            message = "Error! Connection to Database not established!";
        }

        else
        {
            if(selectedH.equals("[]"))
            {
                message = "Error! A group must be selected from history list in order to remove from history!";
            }
            else
            {
                String[] splited = selectedH.split("\\s+");

                String query = QueryStrings.getChatgroupInfoUsingGroupName(splited[0].substring(1));
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                chatGroup = new ChatGroup(rs.getInt("chatgroup_id"), rs.getString("groupname"),
                        rs.getString("status").charAt(0), rs.getString("creation_date"));


                query = QueryStrings.getPersonsInChatGroupIdQuery(chatGroup.getChatGroupId(), person.getPersonId());
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                rs.next();
                int id = rs.getInt(1);
                query = QueryStrings.updatePersonInChatGroupLeaving(id);
                stmt.executeUpdate(query);
                message = "'" + chatGroup.getGroupName() + "' has been removed.";
            }
        }

        conn.close();
        return message;
    }

    public static ArrayList<String> getAllChannels() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        ArrayList<String> list = new ArrayList<>();

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

                list.add(String.format("%s   -   %s   -   Creation Date: %s\n",
                        rs.getString("groupname"), temp, rs.getString("creation_date")));
            }

        }
        conn.close();
        return list;
    }
}
