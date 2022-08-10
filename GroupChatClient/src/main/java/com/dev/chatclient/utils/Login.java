package com.dev.chatclient.utils;

import com.dev.chatclient.jdbcTools.DBConnection;
import com.dev.chatclient.jdbcTools.QueryStrings;
import com.dev.chatclient.userInfo.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login
{
    public static Person loginAuthentication(String username, String password) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        Person person = null;

        if(conn == null)
        {
            System.out.println("Connection to Database Failed");
        }
        else
        {
            String query = QueryStrings.loginVerifcationQuery(username, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if(rs.getString(1).equals("1"))
            {
                System.out.println("Login successfull");
                query = QueryStrings.personInformation(username);
                rs = stmt.executeQuery(query);
                rs.next();
                person = new Person(rs.getInt("person_id"), rs.getString("username"),
                        rs.getString("status"));
                query = QueryStrings.updatePersonsStatus(person.getPersonId(), "+");
                stmt.executeUpdate(query);
            }
            else
            {
                System.out.println("Login failed! Username and password don't match.");
            }
        }

        conn.close();
        return person;
    }

    public static String registrationAuthentication(String username, String password) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        String message = "";
        username = username.replaceAll("\\s+", "");

        if(conn == null)
        {
            message = "Issue connecting to database!";
        }

        else
        {
            String query = QueryStrings.checkIfUsernameUsedQuery(username);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            if(rs.getString(1).equals("1"))
            {
                message = "Error! Username is already used.";
            }
            else
            {
                query = QueryStrings.insertPersonQuery(username, password);
                stmt.executeUpdate(query);
                message = "Registration complete! You can login!";
            }
        }

        conn.close();
        return message;
    }

    public static String logout(Person person) throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        String message = "";

        if(conn == null)
        {
            message = "Error! Couldn't Log out";
        }
        else
        {
            Statement stmt = conn.createStatement();
            String query = QueryStrings.updatePersonsStatus(person.getPersonId(), "-");
            stmt.executeUpdate(query);
            message = "logout successful";
        }
        conn.close();
        return message;
    }
}
