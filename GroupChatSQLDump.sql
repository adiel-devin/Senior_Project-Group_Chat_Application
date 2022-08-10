CREATE DATABASE  IF NOT EXISTS `groupchat database` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `groupchat database`;
-- MySQL dump 10.13  Distrib 8.0.26, for macos11 (x86_64)
--
-- Host: mysql-chatgroupserver-csc492.mysql.database.azure.com    Database: groupchat database
-- ------------------------------------------------------
-- Server version	5.6.47.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chatgroup`
--

DROP TABLE IF EXISTS `chatgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatgroup` (
  `chatgroup_id` int(11) NOT NULL AUTO_INCREMENT,
  `groupname` varchar(45) NOT NULL,
  `status` char(1) NOT NULL,
  `creation_date` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`chatgroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chatlog`
--

DROP TABLE IF EXISTS `chatlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chatlog` (
  `chatlog_id` int(11) NOT NULL AUTO_INCREMENT,
  `persons_in_chatgroup_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `time_sent` varchar(45) NOT NULL,
  PRIMARY KEY (`chatlog_id`,`persons_in_chatgroup_id`),
  KEY `fk_chatlog_persons_in_chatgroup1_idx` (`persons_in_chatgroup_id`),
  CONSTRAINT `fk_chatlog_persons_in_chatgroup1` FOREIGN KEY (`persons_in_chatgroup_id`) REFERENCES `persons_in_chatgroup` (`persons_in_chatgroup_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `directmessage`
--

DROP TABLE IF EXISTS `directmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `directmessage` (
  `directmessage_id` int(11) NOT NULL AUTO_INCREMENT,
  `dm_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `time_sent` varchar(45) NOT NULL,
  PRIMARY KEY (`directmessage_id`,`dm_id`),
  KEY `dm_id_idx` (`dm_id`),
  CONSTRAINT `dm_id` FOREIGN KEY (`dm_id`) REFERENCES `dm` (`dm_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dm`
--

DROP TABLE IF EXISTS `dm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dm` (
  `dm_id` int(11) NOT NULL AUTO_INCREMENT,
  `main_id` int(11) NOT NULL,
  `friend_id` int(11) NOT NULL,
  PRIMARY KEY (`dm_id`,`main_id`,`friend_id`),
  KEY `main_id_idx` (`main_id`),
  KEY `friend_id_idx` (`friend_id`),
  CONSTRAINT `friend_id` FOREIGN KEY (`friend_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `main_id` FOREIGN KEY (`main_id`) REFERENCES `person` (`person_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `status` char(1) NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `persons_in_chatgroup`
--

DROP TABLE IF EXISTS `persons_in_chatgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persons_in_chatgroup` (
  `persons_in_chatgroup_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `chatgroup_id` int(11) NOT NULL,
  `status` char(1) NOT NULL,
  `admin` char(1) NOT NULL,
  PRIMARY KEY (`persons_in_chatgroup_id`,`person_id`,`chatgroup_id`),
  KEY `fk_persons_in_chatgroup_person1_idx` (`person_id`),
  KEY `fk_persons_in_chatgroup_chatgroup1_idx` (`chatgroup_id`),
  CONSTRAINT `fk_persons_in_chatgroup_chatgroup1` FOREIGN KEY (`chatgroup_id`) REFERENCES `chatgroup` (`chatgroup_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_persons_in_chatgroup_person1` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'groupchat database'
--

--
-- Dumping routines for database 'groupchat database'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-08 22:18:34
