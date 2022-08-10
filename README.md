# Overview

This project was my submission for my CSUDH senior project. The project is a group chat application where users are able to create, manage, and chat in groups with other users. This project has a back-end database which saves user credentials, chat history, channel information, and private messages. 

# MySql Database 

This applications requires a MySql database to work. I included the GroupChatSQLDump.sql file that if ran as a query in MySql workbench, it will creates the Sql database named "groupchat database" which includes six tables: chatgroup, person, persons_in_chatgroup, chatlog, dm, directmessaging. Then under src/main/java/com/dev/chatclient/jdbcTools/DBConnection.java, you can modify the url, username, password to your database.
Entity Relational Diagram below:

![image](https://user-images.githubusercontent.com/47425401/183830210-4ff9a45d-724c-4991-a7a0-7b3fa40c82c8.png)

# GroupChatClient

GroupChatClient folders is the client application which acts as the GUI, single user manager, and runs queries on the database. This was tested on two separate desktops in with different IP's connected to a Microsoft Azure MySQL cloud database and both where able to create groups and message one another simultaneously. Although some disconnection issues where found which is more notable when multiply queries are ran at once.
Below are screenshot of the GUI:

![image](https://user-images.githubusercontent.com/47425401/183830645-26d3d8aa-1178-44e8-bd9e-82dc45480a0b.png)
![image](https://user-images.githubusercontent.com/47425401/183830711-f55226af-a00a-41f0-8ea5-1670ce2eb5e0.png)
![image](https://user-images.githubusercontent.com/47425401/183830769-43c04ec1-e3ad-49d3-9bcc-5c992793d82d.png)
![image](https://user-images.githubusercontent.com/47425401/183830840-597ce7ed-2597-472a-b10c-d0b0e9654b55.png)
![image](https://user-images.githubusercontent.com/47425401/183830896-d1493cc2-20f9-401c-ac3e-41e9795ce237.png)
![image](https://user-images.githubusercontent.com/47425401/183830969-a02b1a31-491c-4a38-bf47-1ff455d30e5e.png)


# Maven - Build before running

The software is built using Maven so it should include all of the dependencies and libraries for the JAVAFX, JDBC drivers, and jdk-17.0.1 in the pom.xml file so building the software before running is required for it to work. The main method is under the src/main/java/com/dev/chatclient/App.java, for the configuration you can use the java 17 SDK of the 'GroupChatClient' module or Oracle OpenJDK version 17.0.1. 

# JavaFX (GUI) and Controllers

This desktop application uses the JavaFx libraries for the GUI so under src/main/resources/com/dev/chatclient/* contains .fxml files which contain interfaces for all the functions of the program like the chatGroupManager page, login page, etc. Under src/main/java/com/dev/chatclient/controllers/* contain a corresponding *.java controller for each *.fxml file which would handle button listening, Action Events, and instance variables. There is also a SceneChanger.java file which handles the transition from one scene to another by connection a *.java controller to a *.fxml, passing any neccesary objects (like user information) and loading the scene as a GUI for the user. 

# JDBC Tools

Under src/main/java/com/dev/chatclient/jdbcTools/* contain the DBConnection.java file which establishes a connection to the database and returns the connection. This class is called anytime a connection with the database is needed. QueryString.java has all the static methods which build query strings that are used to pass the mysql database to run as queries. 

# User classes

Under src/main/java/com/dev/chatclient/userInfo/* contains classes that store a single users information or single chat groups information so that they can be passed as objects between scene to scene. These classes also execute queries to the database to get data, for example ChatGroup runs a query to get all of the messages in a specific chat group and stores that information in an instance String list. 

# Utils

Under src/main/java/com/dev/chatclient/utils/* contains other utilities classes that mostly contains static methods. They usually perform more complex queries, parse user input, or perform encryption. 
