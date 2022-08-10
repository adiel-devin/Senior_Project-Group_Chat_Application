package com.dev.chatclient.jdbcTools;

public class QueryStrings
{
    public static String loginVerifcationQuery(String username, String password)
    {
        String temp = "";
        temp += "SELECT EXISTS ";
        temp += "(SELECT person.username ";
        temp += "FROM person ";
        temp += "WHERE person.username = '" + username + "' AND person.password = '" + password + "' )";
        return temp;
    }

    public static String personInformation(String username)
    {
        String temp = "";
        temp += "SELECT person.person_id, person.username, person.status ";
        temp += "FROM person ";
        temp += "WHERE person.username = '" + username + "'";
        return temp;
    }

    public static String personsGroupsQuery(int personId)
    {
        String temp = "";
        temp += "SELECT chatgroup.groupname ";
        temp += "FROM persons_in_chatgroup JOIN chatgroup ON (chatgroup.chatgroup_id = persons_in_chatgroup.chatgroup_id) ";
        temp += "WHERE persons_in_chatgroup.person_id = " + personId + " AND persons_in_chatgroup.status = '+'; ";
        return temp;
    }

    public static String getAllMessagesQuery(int groupId)
    {
        String temp = "";
        temp += "SELECT person.username, chatlog.message, chatlog.time_sent ";
        temp += "FROM person ";
        temp += "JOIN persons_in_chatgroup USING (person_id) ";
        temp += "JOIN chatlog USING (persons_in_chatgroup_id)";
        temp += "WHERE chatgroup_id = " + groupId + " ";
        temp += "ORDER BY chatlog.time_sent ASC ";
        return temp;
    }

    public static String getPersonsInChatGroupIdQuery(int groupId, int personId)
    {
        String temp = "";
        temp += "SELECT persons_in_chatgroup.persons_in_chatgroup_id ";
        temp += "FROM persons_in_chatgroup ";
        temp += "WHERE persons_in_chatgroup.person_id = " + personId + " ";
        temp += "AND persons_in_chatgroup.chatgroup_id = " + groupId + " ";
        return temp;
    }

    public static String insertMessageQuery(int id, String message, String timeSent)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`chatlog` (`persons_in_chatgroup_id`, `message`, `time_sent`) ";
        temp += "VALUES ('" + id + "', '" + message + "', '" + timeSent + "'); " ;
        return temp;
    }

    public static String checkIfUsernameUsedQuery(String username)
    {
        String temp = "";
        temp += "SELECT EXISTS ";
        temp += "(SELECT person.username ";
        temp += "FROM person ";
        temp += "WHERE person.username = '" + username + "' )";
        return temp;
    }

    public static String getAllUsersInAGroupQuery(int groupId)
    {
        String temp = "";
        temp += "SELECT person.username, person.status ";
        temp += "FROM person JOIN persons_in_chatgroup USING (person_id) ";
        temp += "JOIN chatgroup USING (chatgroup_id) ";
        temp += "WHERE chatgroup_id = " + groupId + " ";
        temp += "AND persons_in_chatgroup.status = '+' ";
        return temp;
    }

    public static String insertPersonQuery(String username, String password)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`person` (`username`, `password`, `status`) VALUES ('" +
                username + "', '" + password + "', '-');";
        return temp;
    }

    public static String insertPersonInChatGroup(int personId, int chatgroupId)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`persons_in_chatgroup` (`person_id`, `chatgroup_id`, `status`, `admin`) ";
        temp += "VALUES ('" + personId + "', '" + chatgroupId + "', '+', '-'); ";
        return temp;
    }

    public static String chatgroupVerificationQuery(int chatgroupId, String password)
    {
        String temp = "";
        temp += "SELECT EXISTS ";
        temp += "(SELECT chatgroup.password ";
        temp += "FROM chatgroup ";
        temp += "WHERE chatgroup.chatgroup_id = " + chatgroupId + " AND chatgroup.password = '" + password + "' )";
        return temp;
    }

    public static String updatePersonInChatGroupLeaving(int id)
    {
        String temp = "";
        temp += "UPDATE `groupchat database`.`persons_in_chatgroup` SET `status` = '-' ";
        temp += "WHERE (`persons_in_chatgroup_id` = '" + id + "'); ";
        return temp;
    }

    public static String checkIfPersonHasAPersonsInChatgroupIdQuery(int personId, int chatgroupId)
    {
        String temp = "";
        temp += "SELECT EXISTS (SELECT persons_in_chatgroup.status FROM persons_in_chatgroup ";
        temp += "WHERE persons_in_chatgroup.person_id = " + personId + " ";
        temp += "AND persons_in_chatgroup.chatgroup_id = " + chatgroupId + ") ";
        return temp;
    }

    public static String updatePersonInChatGroupJoining(int personId, int chatgroupId)
    {
        String temp = "";
        temp += "UPDATE `groupchat database`.`persons_in_chatgroup` SET `status` = '+' ";
        temp += "WHERE (`person_id` = '" + personId + "') and (`chatgroup_id` = '" + chatgroupId + "'); ";
        return temp;
    }

    public static String checkIfPersonIsAdminOfChatgroup(int personId, int chatgroupId)
    {
        String temp = "";
        temp += "SELECT EXISTS (SELECT persons_in_chatgroup.status FROM persons_in_chatgroup ";
        temp += "WHERE person_id = " + personId + " AND chatgroup_id = " + chatgroupId + " AND admin = '+'); ";
        return temp;
    }

    public static String deleteChatgroupQuery(int chatgroupId)
    {
        String temp = "";
        temp += "DELETE FROM `groupchat database`.`chatgroup` WHERE (`chatgroup_id` = '" + chatgroupId + "'); ";
        return temp;
    }

    public static String checkIfGroupnameIsUsed(String groupname)
    {
        String temp = "";
        temp += "SELECT EXISTS (SELECT chatgroup.groupname FROM chatgroup ";
        temp += "WHERE chatgroup.groupname = '" + groupname + "'); ";
        return temp;
    }

    public static String insertChatGroupWithoutPassword(String name, String dateTime)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`chatgroup` (`groupname`, `status`, `creation_date`) ";
        temp += "VALUES ('" + name + "', '+', '" + dateTime + "'); ";
        return temp;
    }

    public static String insertPersonInChatGroupAsAdmin(int personId, int chatgroupId)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`persons_in_chatgroup` (`person_id`, `chatgroup_id`, `status`, `admin`) ";
        temp += "VALUES ('" + personId + "', '" + chatgroupId + "', '+', '+'); ";
        return temp;
    }

    public static String getChatgroupId(String name)
    {
        String temp = "";
        temp += "SELECT chatgroup.chatgroup_id FROM chatgroup ";
        temp += "WHERE chatgroup.groupname = '" + name + "'; ";
        return temp;
    }

    public static String insertChatGroupWithPassword(String name, String password, String creationDate)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`chatgroup` (`groupname`, `status`, `creation_date`, `password`) ";
        temp += "VALUES ('" + name + "', '-', '" + creationDate + "', '" + password + "'); ";
        return temp;
    }

    public static String updatePersonsStatus(int personId, String status)
    {
        String temp = "";
        temp += "UPDATE `groupchat database`.`person` SET `status` = '";
        temp += status + "' WHERE (`person_id` = '" + personId + "'); ";
        return temp;
    }

    public static String checkIfThereIsaDM(int mainId, int friendId)
    {
        String temp = "";
        temp += "SELECT EXISTS (SELECT dm.dm_id FROM dm ";
        temp += "WHERE dm.main_id = " + mainId + " AND dm.friend_id = " + friendId + ") ";
        return temp;
    }

    public static String getTheDmId(int mainId, int friendId)
    {
        String temp = "";
        temp += "SELECT dm.dm_id FROM dm ";
        temp += "WHERE dm.main_id = " + mainId + " AND dm.friend_id = " + friendId + " ";
        return temp;
    }

    public static String insertDm(int mainId, int friendId)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`dm` (`main_id`, `friend_id`) ";
        temp += "VALUES ('" + mainId + "', '" + friendId + "'); ";
        return temp;
    }

    public static String getAllDirectMessages(int dmId1, int dmId2)
    {
        String temp = "";
        temp += "SELECT person.username, directmessage.message, directmessage.time_sent ";
        temp += "FROM directmessage JOIN dm ON (directmessage.dm_id = dm.dm_id) ";
        temp += "JOIN person ON (dm.main_id = person.person_id)";
        temp += "WHERE dm.dm_id = " + dmId1 + " OR dm.dm_id = " + dmId2 + " ";
        temp += "ORDER BY directmessage.time_sent ASC";
        return temp;
    }

    public static String sendDirectMessage(int id, String message, String dateTime)
    {
        String temp = "";
        temp += "INSERT INTO `groupchat database`.`directmessage` (`dm_id`, `message`, `time_sent`) ";
        temp += "VALUES ('" + id + "', '" + message + "', '" + dateTime + "'); ";
        return temp;
    }

    public static String getFriendsUsernameQuery(int friendId)
    {
        String temp = "";
        temp += "SELECT person.username, person.status ";
        temp += "FROM person JOIN dm ON (dm.friend_id = person.person_id) ";
        temp += "WHERE dm.dm_id = " + friendId + " ";
        return temp;
    }

    public static String getAllOfUsersDm(int mainId)
    {
        String temp = "";
        temp += "SELECT person.username, person.status ";
        temp += "FROM person JOIN dm ON (dm.friend_id = person.person_id) ";
        temp += "WHERE dm.main_id = " + mainId + " ";
        return temp;
    }

    public static String deleteDms(int id)
    {
        String temp = "";
        temp += "DELETE FROM `groupchat database`.`dm` WHERE (`dm_id` = '" + id + "'); ";
        return temp;
    }

    public static String getChatgroupInfoUsingGroupName(String groupname)
    {
        String temp = "";
        temp += "SELECT * ";
        temp += "FROM chatgroup ";
        temp += "WHERE groupname = '" + groupname + "' ";
        return temp;
    }
}
