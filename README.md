# MySql Database 

This applications requires a MySql database to work, with no database you cannot get passed the login screen. I left the url, username, and password hard coded for my Azure cloud database so that the application might be able to access it through your PC but there might be issues because of the firewall. So I included the GroupChatSQLDump.sql file that if ran in MySql workbench will creates the Sql database which includes six tables: chatgroup, person, persons_in_chatgroup, chatlog, dm, directmessaging. Then under src/main/java/com/dev/chatclient/jdbcTools/DBConnection.java, you can modify the url, username, password to your database. 

# GroupChatClient

GroupChatClient is the client-only software, I was NOT able to make a web application or server software in time, but this application acts as the GUI, single user manager, and runs queries on the database. So this application act more like a server application since it directly accesses the database but also manages the users GUI. This was tested on two separate desktops in with different IP's I just opened the Azure's cloud server to them and both where able to create groups and message one another simultaneously. Although some disconnection issues where found. 

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






