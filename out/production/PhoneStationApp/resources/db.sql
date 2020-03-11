-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: proverka
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `accountnumber` int(11) NOT NULL AUTO_INCREMENT,
  `idsubscriber` int(11) NOT NULL,
  `cost_per_month` int(11) NOT NULL,
  `account_state` int(11) NOT NULL,
  PRIMARY KEY (`accountnumber`,`idsubscriber`),
  UNIQUE KEY `idsubscriber_UNIQUE` (`idsubscriber`),
  KEY `subscriberid_idx` (`idsubscriber`),
  KEY `subid_idx` (`idsubscriber`),
  CONSTRAINT `subid` FOREIGN KEY (`idsubscriber`) REFERENCES `subscriber` (`idsubscriber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (53,51,5,5),(54,52,12,-9),(55,53,15,-8),(56,54,27,76),(57,55,22,8),(58,56,27,58),(59,57,10,123),(60,58,20,-15),(61,59,12,25),(62,60,17,3);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `idAdministrator` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`idAdministrator`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (1,'Ivan Ivanov');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password`
--

DROP TABLE IF EXISTS `password`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `password` (
  `name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password`
--

LOCK TABLES `password` WRITE;
/*!40000 ALTER TABLE `password` DISABLE KEYS */;
INSERT INTO `password` VALUES ('Alexandre Dumas','password',2),('Ivan Ivanov','pass',1),('Victor Hugo','password',2),('William Shakespeare','password',2),('Александр Пушкин','Пушкин',2),('Антон Чехов','password',2),('Владимир Набоков','password',2),('Лев Толстой','voinaimir',2),('Михаил Булгаков','password',2),('Николай Гоголь','password',2),('Фёдор Достоевский','password',2);
/*!40000 ALTER TABLE `password` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serveces`
--

DROP TABLE IF EXISTS `serveces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serveces` (
  `idservece` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cost` int(11) NOT NULL,
  PRIMARY KEY (`idservece`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serveces`
--

LOCK TABLES `serveces` WRITE;
/*!40000 ALTER TABLE `serveces` DISABLE KEYS */;
INSERT INTO `serveces` VALUES (1,'clip',5),(2,'vip_number',10),(3,'virtual_number',7);
/*!40000 ALTER TABLE `serveces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriber`
--

DROP TABLE IF EXISTS `subscriber`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriber` (
  `idsubscriber` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `lock_status` varchar(3) NOT NULL,
  PRIMARY KEY (`idsubscriber`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber`
--

LOCK TABLES `subscriber` WRITE;
/*!40000 ALTER TABLE `subscriber` DISABLE KEYS */;
INSERT INTO `subscriber` VALUES (51,'Лев Толстой','Кирова,15-56','no'),(52,'Фёдор Достоевский','Советская,97-35','no'),(53,'Николай Гоголь','Кожара,1-356','yes'),(54,'Александр Пушкин','Петруся Бровки,45-1','no'),(55,'Антон Чехов','Мазурова,8-21','no'),(56,'Михаил Булгаков','Ефремова,19-75','no'),(57,'Владимир Набоков','Свиридова,5-9','no'),(58,'Victor Hugo','Kirova,23-98','no'),(59,'William Shakespeare','Артема,5-12','no'),(60,'Alexandre Dumas','Mira,76-89','no');
/*!40000 ALTER TABLE `subscriber` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriber_has_serveces`
--

DROP TABLE IF EXISTS `subscriber_has_serveces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriber_has_serveces` (
  `subscriber_idsubscriber` int(11) NOT NULL,
  `serveces_idservece` int(11) NOT NULL,
  PRIMARY KEY (`subscriber_idsubscriber`,`serveces_idservece`),
  KEY `fk_subscriber_has_serveces_serveces1_idx` (`serveces_idservece`),
  KEY `fk_subscriber_has_serveces_subscriber1_idx` (`subscriber_idsubscriber`),
  CONSTRAINT `fk_subscriber_has_serveces_serveces1` FOREIGN KEY (`serveces_idservece`) REFERENCES `serveces` (`idservece`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_subscriber_has_serveces_subscriber1` FOREIGN KEY (`subscriber_idsubscriber`) REFERENCES `subscriber` (`idsubscriber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriber_has_serveces`
--

LOCK TABLES `subscriber_has_serveces` WRITE;
/*!40000 ALTER TABLE `subscriber_has_serveces` DISABLE KEYS */;
INSERT INTO `subscriber_has_serveces` VALUES (54,1),(56,1),(57,1),(58,1),(60,1),(53,2),(54,2),(55,2),(56,2),(58,2),(52,3),(54,3),(55,3),(56,3),(59,3),(60,3);
/*!40000 ALTER TABLE `subscriber_has_serveces` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-19 21:06:22
