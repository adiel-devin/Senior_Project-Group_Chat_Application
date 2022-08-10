package com.dev.chatclient.utils;

import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;
import com.dev.chatclient.userInfo.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DirectMessaging
{
    public static int[] establishDirectMessage(Person person, String selected) throws SQLException {
        int[] uFId = {-1, -1};
        int friendId = -1;

        Connection conn = DBConnection.getInstance().getConnection();
        if(conn == null)
        {
            System.out.println("Connection with database was not established");
        }
        else
        {
            if(selected.equals("[]"))
            {
                System.out.println("Nothing was selected");
            }

            else
            {
                String[] splited = selected.split("\\s+");
                String username = splited[0].substring(1);

                String query = QueryStrings.personInformation(username);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                friendId = rs.getInt("person_id");

                if (person.getPersonId() == friendId) {
                    // Cannot dm yourself
                    System.out.println("You cannot DM yourself");
                } else {
                    query = QueryStrings.checkIfThereIsaDM(person.getPersonId(), friendId);
                    rs = stmt.executeQuery(query);
                    rs.next();
                    if (rs.getString(1).equals("1")) {
                        // they have already established dm, get the dm id

                    } else {
                        // create new dm link
                        query = QueryStrings.insertDm(person.getPersonId(), friendId);
                        stmt.executeUpdate(query);
                        // create new dm link
                        query = QueryStrings.insertDm(friendId, person.getPersonId());
                        stmt.executeUpdate(query);

                    }
                    query = QueryStrings.getTheDmId(person.getPersonId(), friendId);
                    rs = stmt.executeQuery(query);
                    rs.next();
                    uFId[0] = rs.getInt("dm_id");

                    query = QueryStrings.getTheDmId(friendId, person.getPersonId());
                    rs = stmt.executeQuery(query);
                    rs.next();
                    uFId[1] = rs.getInt("dm_id");
                }
            }



        }

        conn.close();
        return uFId;
    }

    public static ArrayList<String> getAllDirectMessages(int id1, int id2) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        ArrayList<String> messages = new ArrayList<>();
        int key = id1 + id2;

        if(conn == null)
        {
            messages.add("Error connecting to database");
        }

        else
        {
            String query = QueryStrings.getAllDirectMessages(id1, id2);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                String decMessage = AES.decrypt(rs.getString("message"),
                        Integer.toString(key));
                String message = String.format("%s   -   %s   -   %s%s",
                        rs.getString("username"),
                        decMessage,
                        "sent: ",
                        rs.getString("time_sent"));
                messages.add(message);
            }
        }
        return messages;
    }

    public static void sendMessage(String message, int id1, int id2) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();

        if(conn == null)
        {
            System.out.println("Could not establish connection to database!");
        }

        else
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            int key = id1 + id2;

            String encMessage = AES.encrypt(message, Integer.toString(key));

            String query = QueryStrings.sendDirectMessage(id1, encMessage, dtf.format(now));
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }

        conn.close();
    }

    public static ArrayList<String> getFriendsUsername(int dmId) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        if(conn == null)
        {
            System.out.println("Could not establish connection to database!");
        }

        else
        {
            String query = QueryStrings.getFriendsUsernameQuery(dmId);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            list.add(rs.getString("username"));
            list.add(rs.getString("status"));


        }

        conn.close();
        return list;
    }

    public static ArrayList<String> getAllUsersInTheDm(int mainId) throws SQLException {
        ArrayList<String> list = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        if(conn == null)
        {
            System.out.println("Could not establish connection to database!");
        }
        else
        {
            String query = QueryStrings.getAllOfUsersDm(mainId);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
                list.add(rs.getString("username") + temp);
            }
        }
        conn.close();
        return list;
    }

    public static String deleteTheDirectMessage(Person person, String selected) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String temp = "";

        if(conn == null)
        {
            temp = "Error connecting to database";
        }

        else
        {
            if(selected.equals("[]"))
            {
                temp = "Error. Nothing was selected";
            }
            else
            {
                int[] ufId = new int[2];
                String[] splited = selected.split("\\s+");
                String username = splited[0].substring(1);

                String query = QueryStrings.personInformation(username);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                rs.next();
                int friendId = rs.getInt("person_id");

                query = QueryStrings.getTheDmId(person.getPersonId(), friendId);
                rs = stmt.executeQuery(query);
                rs.next();
                ufId[0] = rs.getInt("dm_id");

                query = QueryStrings.getTheDmId(friendId, person.getPersonId());
                rs = stmt.executeQuery(query);
                rs.next();
                ufId[1] = rs.getInt("dm_id");

                query = QueryStrings.deleteDms(ufId[0]);
                stmt.executeUpdate(query);

                query = QueryStrings.deleteDms(ufId[1]);
                stmt.executeUpdate(query);

                temp = "Successful deletion";
            }
        }

        conn.close();
        return temp;
    }

}
