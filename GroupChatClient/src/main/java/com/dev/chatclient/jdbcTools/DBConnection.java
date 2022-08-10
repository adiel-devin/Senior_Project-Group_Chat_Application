package com.dev.chatclient.jdbcTools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private static DBConnection instance;
    private Connection conn;
    private String url = "jdbc:mysql://localhost:3306/groupchat database";
    private String username = "root";
    private String password = "password";

    private DBConnection() throws SQLException
    {
        try
        {
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println("Database Connection Successful");
        }
        catch (SQLException e)
        {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
        }
    }

    public Connection getConnection()
    {
        return conn;
    }

    public static DBConnection getInstance() throws SQLException
    {
        if (instance == null)
        {
            instance = new DBConnection();
        }
        else if (instance.getConnection().isClosed())
        {
            instance = new DBConnection();
        }
        return instance;
    }
}
