package com.dev.chatclient.userInfo;

public class Person
{
    private int personId;
    private String username;
    private String status;

    public Person(int personId, String username, String status)
    {
        this.personId = personId;
        this.username = username;
        this.status = status;
    }

    public int getPersonId()
    {
        return this.personId;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String toString()
    {
        String temp = "";
        if(this.status.equals("+"))
        {
            temp = "Online";
        }
        else
        {
            temp = "Offline";
        }

        return String.format("%s - %s", this.username, temp);
    }

}
