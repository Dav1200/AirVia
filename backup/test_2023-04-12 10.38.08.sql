-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: smcse-stuproj00.city.ac.uk    Database: in2018g04
-- ------------------------------------------------------
-- Server version	5.5.68-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Admin`
--

DROP TABLE IF EXISTS `Admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Admin` (
  `StaffID` int(3) DEFAULT NULL,
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `Admin_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Admin`
--

LOCK TABLES `Admin` WRITE;
/*!40000 ALTER TABLE `Admin` DISABLE KEYS */;
INSERT INTO `Admin` VALUES (320);
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CommissionRate`
--

DROP TABLE IF EXISTS `CommissionRate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CommissionRate` (
  `BlankType` int(3) NOT NULL DEFAULT '0',
  `CommissionRate` float DEFAULT NULL,
  PRIMARY KEY (`BlankType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommissionRate`
--

LOCK TABLES `CommissionRate` WRITE;
/*!40000 ALTER TABLE `CommissionRate` DISABLE KEYS */;
INSERT INTO `CommissionRate` VALUES (101,1),(201,5),(420,2),(440,4),(444,9);
/*!40000 ALTER TABLE `CommissionRate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `ID` bigint(10) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `CustomerType` varchar(10) DEFAULT NULL,
  `DiscountType` varchar(10) DEFAULT NULL,
  `DiscountAmount` decimal(3,2) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `TotalTickets` int(4) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  `Alias` varchar(255) DEFAULT NULL,
  `TotalPayment` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `Customer_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `TravelAdvisor` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES (1,'Chris','Smart','ChrisSmart@Gmail.com','Valued','Fixed',0.01,'12 Drent Street, London',2,250,'Chris',571.4),(2,'David','Dodson','DavidDodson@Gmail.com','Valued','Flexible',0.00,'145 Drent Street, London',1,250,'DaveD',358),(3,'Sarah','Broklehurst','SarahBroklehurst@Gmail.com','Valued','Fixed',0.02,'12 Water Street, London',2,250,'SarahB',578.6),(4,'Dominic','Beatty','DominicBeatty@Gmail.com','Regular','Fixed',0.00,'159 Key Street, London',0,250,'Dom',0);
/*!40000 ALTER TABLE `Customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OfficeManager`
--

DROP TABLE IF EXISTS `OfficeManager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `OfficeManager` (
  `StaffID` int(3) DEFAULT NULL,
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `OfficeManager_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OfficeManager`
--

LOCK TABLES `OfficeManager` WRITE;
/*!40000 ALTER TABLE `OfficeManager` DISABLE KEYS */;
INSERT INTO `OfficeManager` VALUES (220);
/*!40000 ALTER TABLE `OfficeManager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SalesReport`
--

DROP TABLE IF EXISTS `SalesReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SalesReport` (
  `TicketID` bigint(11) NOT NULL AUTO_INCREMENT,
  `TicketType` int(3) DEFAULT NULL,
  `PaymentType` varchar(4) DEFAULT NULL,
  `ReportType` varchar(10) DEFAULT NULL,
  `Departure` varchar(30) DEFAULT NULL,
  `Destination` varchar(30) DEFAULT NULL,
  `CommissionAmount` decimal(3,2) DEFAULT NULL,
  `TicketQuantity` int(4) DEFAULT NULL,
  `TicketPrice` decimal(5,2) DEFAULT NULL,
  `SubTotal` int(10) DEFAULT NULL,
  `GrandTotal` int(10) DEFAULT NULL,
  `LatePayment` varchar(3) DEFAULT NULL,
  `ExchangeRate` decimal(10,2) DEFAULT NULL,
  `TicketDate` int(8) DEFAULT NULL,
  `ID` bigint(10) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  PRIMARY KEY (`TicketID`),
  KEY `ID` (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `SalesReport_ibfk_1` FOREIGN KEY (`ID`) REFERENCES `Customer` (`ID`),
  CONSTRAINT `SalesReport_ibfk_2` FOREIGN KEY (`StaffID`) REFERENCES `OfficeManager` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SalesReport`
--

LOCK TABLES `SalesReport` WRITE;
/*!40000 ALTER TABLE `SalesReport` DISABLE KEYS */;
/*!40000 ALTER TABLE `SalesReport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Staff`
--

DROP TABLE IF EXISTS `Staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Staff` (
  `StaffID` int(3) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Role` varchar(20) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Staff`
--

LOCK TABLES `Staff` WRITE;
/*!40000 ALTER TABLE `Staff` DISABLE KEYS */;
INSERT INTO `Staff` VALUES (211,'Dennis','Menace','DennisMenace@Gmail.com','13 Drent Street, London','Travel Advisor','c134547585f40bf1516f5f1d01039f7e275dd10db503d891f292f9cd246b0263'),(220,'Minnie','Minx',NULL,NULL,'Office Manager','ce7d42cbd11296802b162cf3cf916768055ba66702e78d6decbb3c3b86ebd469'),(250,'Penelope','Pitstop','PenelopePitstop@Gmail.com','12 Drent Street, London','Travel Advisor','56dffc22bd487cf69c74da553acc60de6c60cab2ab41a0bccc4ad1ae6e614583'),(320,'Arthur','Daley',NULL,NULL,'Admin','86b62504c14be20bafc995a425a5c49e371de41989ba2bffbda546598f660814');
/*!40000 ALTER TABLE `Staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TicketTurnoverReport`
--

DROP TABLE IF EXISTS `TicketTurnoverReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TicketTurnoverReport` (
  `ReportID` bigint(10) NOT NULL AUTO_INCREMENT,
  `BlankType` int(3) DEFAULT NULL,
  `ReceivedBlanks` int(10) DEFAULT NULL,
  `BlanksAssigned` int(10) DEFAULT NULL,
  `TotalBlanks` int(10) DEFAULT NULL,
  `StartDate` int(8) DEFAULT NULL,
  `AdvisorID` bigint(10) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  PRIMARY KEY (`ReportID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `TicketTurnoverReport_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `OfficeManager` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TicketTurnoverReport`
--

LOCK TABLES `TicketTurnoverReport` WRITE;
/*!40000 ALTER TABLE `TicketTurnoverReport` DISABLE KEYS */;
/*!40000 ALTER TABLE `TicketTurnoverReport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TravelAdvisor`
--

DROP TABLE IF EXISTS `TravelAdvisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TravelAdvisor` (
  `StaffID` int(3) DEFAULT NULL,
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `TravelAdvisor_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TravelAdvisor`
--

LOCK TABLES `TravelAdvisor` WRITE;
/*!40000 ALTER TABLE `TravelAdvisor` DISABLE KEYS */;
INSERT INTO `TravelAdvisor` VALUES (211),(250);
/*!40000 ALTER TABLE `TravelAdvisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advisor_blanks`
--

DROP TABLE IF EXISTS `advisor_blanks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `advisor_blanks` (
  `ad_blank_id` int(10) NOT NULL AUTO_INCREMENT,
  `advisor_id` int(3) DEFAULT NULL,
  `advisor_name` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `blanks` bigint(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ad_blank_id`),
  KEY `advisor_id` (`advisor_id`),
  CONSTRAINT `advisor_blanks_ibfk_1` FOREIGN KEY (`advisor_id`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisor_blanks`
--

LOCK TABLES `advisor_blanks` WRITE;
/*!40000 ALTER TABLE `advisor_blanks` DISABLE KEYS */;
INSERT INTO `advisor_blanks` VALUES (280,250,'Penelope Pitstop','01/04/2019',44400000001,'Used'),(281,250,'Penelope Pitstop','01/04/2019',44400000002,'Unused'),(282,250,'Penelope Pitstop','01/04/2019',44400000003,'Used'),(283,250,'Penelope Pitstop','01/04/2019',44400000004,'Used'),(284,250,'Penelope Pitstop','01/04/2019',44400000005,'Unused'),(285,250,'Penelope Pitstop','01/04/2019',44400000006,'Unused'),(286,250,'Penelope Pitstop','01/04/2019',44400000007,'Unused'),(287,250,'Penelope Pitstop','01/04/2019',44400000008,'Unused'),(288,250,'Penelope Pitstop','01/04/2019',44400000009,'Unused'),(289,250,'Penelope Pitstop','01/04/2019',44400000010,'Unused'),(290,250,'Penelope Pitstop','01/04/2019',44400000011,'Unused'),(291,250,'Penelope Pitstop','01/04/2019',44400000012,'Unused'),(292,250,'Penelope Pitstop','01/04/2019',44400000013,'Unused'),(293,250,'Penelope Pitstop','01/04/2019',44400000014,'Unused'),(294,250,'Penelope Pitstop','01/04/2019',44400000015,'Unused'),(295,250,'Penelope Pitstop','01/04/2019',44400000016,'Unused'),(296,250,'Penelope Pitstop','01/04/2019',44400000017,'Unused'),(297,250,'Penelope Pitstop','01/04/2019',44400000018,'Unused'),(298,250,'Penelope Pitstop','01/04/2019',44400000019,'Unused'),(299,250,'Penelope Pitstop','01/04/2019',44400000020,'Unused'),(300,250,'Penelope Pitstop','08/05/2019',42000000001,'Unused'),(301,250,'Penelope Pitstop','08/05/2019',42000000002,'Unused'),(302,250,'Penelope Pitstop','08/05/2019',42000000003,'Unused'),(303,250,'Penelope Pitstop','08/05/2019',42000000004,'Unused'),(304,250,'Penelope Pitstop','08/05/2019',42000000005,'Unused'),(305,250,'Penelope Pitstop','08/05/2019',42000000006,'Unused'),(306,250,'Penelope Pitstop','08/05/2019',42000000007,'Unused'),(307,250,'Penelope Pitstop','08/05/2019',42000000008,'Unused'),(308,250,'Penelope Pitstop','08/05/2019',42000000009,'Unused'),(309,250,'Penelope Pitstop','08/05/2019',42000000010,'Unused'),(310,250,'Penelope Pitstop','08/05/2019',42000000011,'Unused'),(311,250,'Penelope Pitstop','08/05/2019',42000000012,'Unused'),(312,250,'Penelope Pitstop','08/05/2019',42000000013,'Unused'),(313,250,'Penelope Pitstop','08/05/2019',42000000014,'Unused'),(314,250,'Penelope Pitstop','08/05/2019',42000000015,'Unused'),(315,250,'Penelope Pitstop','08/05/2019',42000000016,'Unused'),(316,250,'Penelope Pitstop','08/05/2019',42000000017,'Unused'),(317,250,'Penelope Pitstop','08/05/2019',42000000018,'Unused'),(318,250,'Penelope Pitstop','08/05/2019',42000000019,'Unused'),(319,250,'Penelope Pitstop','08/05/2019',42000000020,'Unused'),(320,250,'Penelope Pitstop','08/05/2019',42000000021,'Unused'),(321,250,'Penelope Pitstop','08/05/2019',42000000022,'Unused'),(322,250,'Penelope Pitstop','08/05/2019',42000000023,'Unused'),(323,250,'Penelope Pitstop','08/05/2019',42000000024,'Unused'),(324,250,'Penelope Pitstop','08/05/2019',42000000025,'Unused'),(325,250,'Penelope Pitstop','08/05/2019',42000000026,'Unused'),(326,250,'Penelope Pitstop','08/05/2019',42000000027,'Unused'),(327,250,'Penelope Pitstop','08/05/2019',42000000028,'Unused'),(328,250,'Penelope Pitstop','08/05/2019',42000000029,'Unused'),(329,250,'Penelope Pitstop','08/05/2019',42000000030,'Unused'),(330,250,'Penelope Pitstop','03/06/2019',20100000001,'Used'),(331,250,'Penelope Pitstop','03/06/2019',20100000002,'Used'),(332,250,'Penelope Pitstop','03/06/2019',20100000003,'Unused'),(333,250,'Penelope Pitstop','03/06/2019',20100000004,'Unused'),(334,250,'Penelope Pitstop','03/06/2019',20100000005,'Unused'),(335,250,'Penelope Pitstop','03/06/2019',20100000006,'Unused'),(336,250,'Penelope Pitstop','03/06/2019',20100000007,'Unused'),(337,250,'Penelope Pitstop','03/06/2019',20100000008,'Unused'),(338,250,'Penelope Pitstop','03/06/2019',20100000009,'Unused'),(339,250,'Penelope Pitstop','03/06/2019',20100000010,'Unused'),(340,211,'Dennis Menace','05/04/2019',44400000021,'Used'),(341,211,'Dennis Menace','05/04/2019',44400000022,'Used'),(342,211,'Dennis Menace','05/04/2019',44400000023,'Unused'),(343,211,'Dennis Menace','05/04/2019',44400000024,'Unused'),(344,211,'Dennis Menace','05/04/2019',44400000025,'Unused'),(345,211,'Dennis Menace','05/04/2019',44400000026,'Unused'),(346,211,'Dennis Menace','05/04/2019',44400000027,'Unused'),(347,211,'Dennis Menace','05/04/2019',44400000028,'Unused'),(348,211,'Dennis Menace','05/04/2019',44400000029,'Unused'),(349,211,'Dennis Menace','05/04/2019',44400000030,'Unused'),(350,211,'Dennis Menace','05/04/2019',44400000031,'Unused'),(351,211,'Dennis Menace','05/04/2019',44400000032,'Unused'),(352,211,'Dennis Menace','05/04/2019',44400000033,'Unused'),(353,211,'Dennis Menace','05/04/2019',44400000034,'Unused'),(354,211,'Dennis Menace','05/04/2019',44400000035,'Unused'),(355,211,'Dennis Menace','05/04/2019',44400000036,'Unused'),(356,211,'Dennis Menace','05/04/2019',44400000037,'Unused'),(357,211,'Dennis Menace','05/04/2019',44400000038,'Unused'),(358,211,'Dennis Menace','05/04/2019',44400000039,'Unused'),(359,211,'Dennis Menace','05/04/2019',44400000040,'Unused'),(360,211,'Dennis Menace','10/05/2019',42000000031,'Unused'),(361,211,'Dennis Menace','10/05/2019',42000000032,'Unused'),(362,211,'Dennis Menace','10/05/2019',42000000033,'Unused'),(363,211,'Dennis Menace','10/05/2019',42000000034,'Unused'),(364,211,'Dennis Menace','10/05/2019',42000000035,'Unused'),(365,211,'Dennis Menace','10/05/2019',42000000036,'Unused'),(366,211,'Dennis Menace','10/05/2019',42000000037,'Unused'),(367,211,'Dennis Menace','10/05/2019',42000000038,'Unused'),(368,211,'Dennis Menace','10/05/2019',42000000039,'Unused'),(369,211,'Dennis Menace','10/05/2019',42000000040,'Unused'),(370,211,'Dennis Menace','10/05/2019',42000000041,'Unused'),(371,211,'Dennis Menace','10/05/2019',42000000042,'Unused'),(372,211,'Dennis Menace','10/05/2019',42000000043,'Unused'),(373,211,'Dennis Menace','10/05/2019',42000000044,'Unused'),(374,211,'Dennis Menace','10/05/2019',42000000045,'Unused'),(375,211,'Dennis Menace','10/05/2019',42000000046,'Unused'),(376,211,'Dennis Menace','10/05/2019',42000000047,'Unused'),(377,211,'Dennis Menace','10/05/2019',42000000048,'Unused'),(378,211,'Dennis Menace','10/05/2019',42000000049,'Unused'),(379,211,'Dennis Menace','10/05/2019',42000000050,'Unused'),(380,211,'Dennis Menace','15/06/2019',20100000011,'Unused'),(381,211,'Dennis Menace','15/06/2019',20100000012,'Unused'),(382,211,'Dennis Menace','15/06/2019',20100000013,'Unused'),(383,211,'Dennis Menace','15/06/2019',20100000014,'Unused'),(384,211,'Dennis Menace','15/06/2019',20100000015,'Unused'),(385,211,'Dennis Menace','15/06/2019',20100000016,'Unused'),(386,211,'Dennis Menace','15/06/2019',20100000017,'Unused'),(387,211,'Dennis Menace','15/06/2019',20100000018,'Unused'),(388,211,'Dennis Menace','15/06/2019',20100000019,'Unused'),(389,211,'Dennis Menace','15/06/2019',20100000020,'Unused'),(390,211,'Dennis Menace','15/06/2019',20100000021,'Unused'),(391,211,'Dennis Menace','15/06/2019',20100000022,'Unused'),(392,211,'Dennis Menace','15/06/2019',20100000023,'Unused'),(393,211,'Dennis Menace','15/06/2019',20100000024,'Unused'),(394,211,'Dennis Menace','15/06/2019',20100000025,'Unused'),(395,211,'Dennis Menace','11/07/2019',10100000001,'Unused'),(396,211,'Dennis Menace','11/07/2019',10100000002,'Unused'),(397,211,'Dennis Menace','11/07/2019',10100000003,'Unused'),(398,211,'Dennis Menace','11/07/2019',10100000004,'Unused'),(399,211,'Dennis Menace','11/07/2019',10100000005,'Unused'),(400,211,'Dennis Menace','11/07/2019',10100000006,'Unused'),(401,211,'Dennis Menace','11/07/2019',10100000007,'Unused'),(402,211,'Dennis Menace','11/07/2019',10100000008,'Unused'),(403,211,'Dennis Menace','11/07/2019',10100000009,'Unused'),(404,211,'Dennis Menace','11/07/2019',10100000010,'Unused'),(405,211,'Dennis Menace','11/07/2019',10100000011,'Unused'),(406,211,'Dennis Menace','11/07/2019',10100000012,'Unused'),(407,211,'Dennis Menace','11/07/2019',10100000013,'Unused'),(408,211,'Dennis Menace','11/07/2019',10100000014,'Unused'),(409,211,'Dennis Menace','11/07/2019',10100000015,'Unused'),(410,211,'Dennis Menace','11/07/2019',10100000016,'Unused'),(411,211,'Dennis Menace','11/07/2019',10100000017,'Unused'),(412,211,'Dennis Menace','11/07/2019',10100000018,'Unused'),(413,211,'Dennis Menace','11/07/2019',10100000019,'Unused'),(414,211,'Dennis Menace','11/07/2019',10100000020,'Unused'),(415,211,'Dennis Menace','11/07/2019',10100000021,'Unused'),(416,211,'Dennis Menace','11/07/2019',10100000022,'Unused'),(417,211,'Dennis Menace','11/07/2019',10100000023,'Unused'),(418,211,'Dennis Menace','11/07/2019',10100000024,'Unused'),(419,211,'Dennis Menace','11/07/2019',10100000025,'Unused'),(420,211,'Dennis Menace','11/07/2019',10100000026,'Unused'),(421,211,'Dennis Menace','11/07/2019',10100000027,'Unused'),(422,211,'Dennis Menace','11/07/2019',10100000028,'Unused'),(423,211,'Dennis Menace','11/07/2019',10100000029,'Unused'),(424,211,'Dennis Menace','11/07/2019',10100000030,'Unused'),(425,211,'Dennis Menace','11/07/2019',10100000031,'Unused'),(426,211,'Dennis Menace','11/07/2019',10100000032,'Unused'),(427,211,'Dennis Menace','11/07/2019',10100000033,'Unused'),(428,211,'Dennis Menace','11/07/2019',10100000034,'Unused'),(429,211,'Dennis Menace','11/07/2019',10100000035,'Unused'),(430,211,'Dennis Menace','11/07/2019',10100000036,'Unused'),(431,211,'Dennis Menace','11/07/2019',10100000037,'Unused'),(432,211,'Dennis Menace','11/07/2019',10100000038,'Unused'),(433,211,'Dennis Menace','11/07/2019',10100000039,'Unused'),(434,211,'Dennis Menace','11/07/2019',10100000040,'Unused'),(435,211,'Dennis Menace','11/07/2019',10100000041,'Unused'),(436,211,'Dennis Menace','11/07/2019',10100000042,'Unused'),(437,211,'Dennis Menace','11/07/2019',10100000043,'Unused'),(438,211,'Dennis Menace','11/07/2019',10100000044,'Unused'),(439,211,'Dennis Menace','11/07/2019',10100000045,'Unused'),(440,211,'Dennis Menace','11/07/2019',10100000046,'Unused'),(441,211,'Dennis Menace','11/07/2019',10100000047,'Unused'),(442,211,'Dennis Menace','11/07/2019',10100000048,'Unused'),(443,211,'Dennis Menace','11/07/2019',10100000049,'Unused'),(444,211,'Dennis Menace','11/07/2019',10100000050,'Unused');
/*!40000 ALTER TABLE `advisor_blanks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blank_stock`
--

DROP TABLE IF EXISTS `blank_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blank_stock` (
  `date` varchar(255) DEFAULT NULL,
  `blanks_received` bigint(11) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blank_stock`
--

LOCK TABLES `blank_stock` WRITE;
/*!40000 ALTER TABLE `blank_stock` DISABLE KEYS */;
INSERT INTO `blank_stock` VALUES ('12/04/2023',44400000001,'assigned'),('12/04/2023',44400000002,'assigned'),('12/04/2023',44400000003,'assigned'),('12/04/2023',44400000004,'assigned'),('12/04/2023',44400000005,'assigned'),('12/04/2023',44400000006,'assigned'),('12/04/2023',44400000007,'assigned'),('12/04/2023',44400000008,'assigned'),('12/04/2023',44400000009,'assigned'),('12/04/2023',44400000010,'assigned'),('12/04/2023',44400000011,'assigned'),('12/04/2023',44400000012,'assigned'),('12/04/2023',44400000013,'assigned'),('12/04/2023',44400000014,'assigned'),('12/04/2023',44400000015,'assigned'),('12/04/2023',44400000016,'assigned'),('12/04/2023',44400000017,'assigned'),('12/04/2023',44400000018,'assigned'),('12/04/2023',44400000019,'assigned'),('12/04/2023',44400000020,'assigned'),('12/04/2023',44400000021,'assigned'),('12/04/2023',44400000022,'assigned'),('12/04/2023',44400000023,'assigned'),('12/04/2023',44400000024,'assigned'),('12/04/2023',44400000025,'assigned'),('12/04/2023',44400000026,'assigned'),('12/04/2023',44400000027,'assigned'),('12/04/2023',44400000028,'assigned'),('12/04/2023',44400000029,'assigned'),('12/04/2023',44400000030,'assigned'),('12/04/2023',44400000031,'assigned'),('12/04/2023',44400000032,'assigned'),('12/04/2023',44400000033,'assigned'),('12/04/2023',44400000034,'assigned'),('12/04/2023',44400000035,'assigned'),('12/04/2023',44400000036,'assigned'),('12/04/2023',44400000037,'assigned'),('12/04/2023',44400000038,'assigned'),('12/04/2023',44400000039,'assigned'),('12/04/2023',44400000040,'assigned'),('12/04/2023',44400000041,'unassigned'),('12/04/2023',44400000042,'unassigned'),('12/04/2023',44400000043,'unassigned'),('12/04/2023',44400000044,'unassigned'),('12/04/2023',44400000045,'unassigned'),('12/04/2023',44400000046,'unassigned'),('12/04/2023',44400000047,'unassigned'),('12/04/2023',44400000048,'unassigned'),('12/04/2023',44400000049,'unassigned'),('12/04/2023',44400000050,'unassigned'),('12/04/2023',44400000051,'unassigned'),('12/04/2023',44400000052,'unassigned'),('12/04/2023',44400000053,'unassigned'),('12/04/2023',44400000054,'unassigned'),('12/04/2023',44400000055,'unassigned'),('12/04/2023',44400000056,'unassigned'),('12/04/2023',44400000057,'unassigned'),('12/04/2023',44400000058,'unassigned'),('12/04/2023',44400000059,'unassigned'),('12/04/2023',44400000060,'unassigned'),('12/04/2023',44400000061,'unassigned'),('12/04/2023',44400000062,'unassigned'),('12/04/2023',44400000063,'unassigned'),('12/04/2023',44400000064,'unassigned'),('12/04/2023',44400000065,'unassigned'),('12/04/2023',44400000066,'unassigned'),('12/04/2023',44400000067,'unassigned'),('12/04/2023',44400000068,'unassigned'),('12/04/2023',44400000069,'unassigned'),('12/04/2023',44400000070,'unassigned'),('12/04/2023',44400000071,'unassigned'),('12/04/2023',44400000072,'unassigned'),('12/04/2023',44400000073,'unassigned'),('12/04/2023',44400000074,'unassigned'),('12/04/2023',44400000075,'unassigned'),('12/04/2023',44400000076,'unassigned'),('12/04/2023',44400000077,'unassigned'),('12/04/2023',44400000078,'unassigned'),('12/04/2023',44400000079,'unassigned'),('12/04/2023',44400000080,'unassigned'),('12/04/2023',44400000081,'unassigned'),('12/04/2023',44400000082,'unassigned'),('12/04/2023',44400000083,'unassigned'),('12/04/2023',44400000084,'unassigned'),('12/04/2023',44400000085,'unassigned'),('12/04/2023',44400000086,'unassigned'),('12/04/2023',44400000087,'unassigned'),('12/04/2023',44400000088,'unassigned'),('12/04/2023',44400000089,'unassigned'),('12/04/2023',44400000090,'unassigned'),('12/04/2023',44400000091,'unassigned'),('12/04/2023',44400000092,'unassigned'),('12/04/2023',44400000093,'unassigned'),('12/04/2023',44400000094,'unassigned'),('12/04/2023',44400000095,'unassigned'),('12/04/2023',44400000096,'unassigned'),('12/04/2023',44400000097,'unassigned'),('12/04/2023',44400000098,'unassigned'),('12/04/2023',44400000099,'unassigned'),('12/04/2023',44400000100,'unassigned'),('12/04/2023',42000000001,'assigned'),('12/04/2023',42000000002,'assigned'),('12/04/2023',42000000003,'assigned'),('12/04/2023',42000000004,'assigned'),('12/04/2023',42000000005,'assigned'),('12/04/2023',42000000006,'assigned'),('12/04/2023',42000000007,'assigned'),('12/04/2023',42000000008,'assigned'),('12/04/2023',42000000009,'assigned'),('12/04/2023',42000000010,'assigned'),('12/04/2023',42000000011,'assigned'),('12/04/2023',42000000012,'assigned'),('12/04/2023',42000000013,'assigned'),('12/04/2023',42000000014,'assigned'),('12/04/2023',42000000015,'assigned'),('12/04/2023',42000000016,'assigned'),('12/04/2023',42000000017,'assigned'),('12/04/2023',42000000018,'assigned'),('12/04/2023',42000000019,'assigned'),('12/04/2023',42000000020,'assigned'),('12/04/2023',42000000021,'assigned'),('12/04/2023',42000000022,'assigned'),('12/04/2023',42000000023,'assigned'),('12/04/2023',42000000024,'assigned'),('12/04/2023',42000000025,'assigned'),('12/04/2023',42000000026,'assigned'),('12/04/2023',42000000027,'assigned'),('12/04/2023',42000000028,'assigned'),('12/04/2023',42000000029,'assigned'),('12/04/2023',42000000030,'assigned'),('12/04/2023',42000000031,'assigned'),('12/04/2023',42000000032,'assigned'),('12/04/2023',42000000033,'assigned'),('12/04/2023',42000000034,'assigned'),('12/04/2023',42000000035,'assigned'),('12/04/2023',42000000036,'assigned'),('12/04/2023',42000000037,'assigned'),('12/04/2023',42000000038,'assigned'),('12/04/2023',42000000039,'assigned'),('12/04/2023',42000000040,'assigned'),('12/04/2023',42000000041,'assigned'),('12/04/2023',42000000042,'assigned'),('12/04/2023',42000000043,'assigned'),('12/04/2023',42000000044,'assigned'),('12/04/2023',42000000045,'assigned'),('12/04/2023',42000000046,'assigned'),('12/04/2023',42000000047,'assigned'),('12/04/2023',42000000048,'assigned'),('12/04/2023',42000000049,'assigned'),('12/04/2023',42000000050,'assigned'),('12/04/2023',42000000051,'unassigned'),('12/04/2023',42000000052,'unassigned'),('12/04/2023',42000000053,'unassigned'),('12/04/2023',42000000054,'unassigned'),('12/04/2023',42000000055,'unassigned'),('12/04/2023',42000000056,'unassigned'),('12/04/2023',42000000057,'unassigned'),('12/04/2023',42000000058,'unassigned'),('12/04/2023',42000000059,'unassigned'),('12/04/2023',42000000060,'unassigned'),('12/04/2023',42000000061,'unassigned'),('12/04/2023',42000000062,'unassigned'),('12/04/2023',42000000063,'unassigned'),('12/04/2023',42000000064,'unassigned'),('12/04/2023',42000000065,'unassigned'),('12/04/2023',42000000066,'unassigned'),('12/04/2023',42000000067,'unassigned'),('12/04/2023',42000000068,'unassigned'),('12/04/2023',42000000069,'unassigned'),('12/04/2023',42000000070,'unassigned'),('12/04/2023',42000000071,'unassigned'),('12/04/2023',42000000072,'unassigned'),('12/04/2023',42000000073,'unassigned'),('12/04/2023',42000000074,'unassigned'),('12/04/2023',42000000075,'unassigned'),('12/04/2023',42000000076,'unassigned'),('12/04/2023',42000000077,'unassigned'),('12/04/2023',42000000078,'unassigned'),('12/04/2023',42000000079,'unassigned'),('12/04/2023',42000000080,'unassigned'),('12/04/2023',42000000081,'unassigned'),('12/04/2023',42000000082,'unassigned'),('12/04/2023',42000000083,'unassigned'),('12/04/2023',42000000084,'unassigned'),('12/04/2023',42000000085,'unassigned'),('12/04/2023',42000000086,'unassigned'),('12/04/2023',42000000087,'unassigned'),('12/04/2023',42000000088,'unassigned'),('12/04/2023',42000000089,'unassigned'),('12/04/2023',42000000090,'unassigned'),('12/04/2023',42000000091,'unassigned'),('12/04/2023',42000000092,'unassigned'),('12/04/2023',42000000093,'unassigned'),('12/04/2023',42000000094,'unassigned'),('12/04/2023',42000000095,'unassigned'),('12/04/2023',42000000096,'unassigned'),('12/04/2023',42000000097,'unassigned'),('12/04/2023',42000000098,'unassigned'),('12/04/2023',42000000099,'unassigned'),('12/04/2023',42000000100,'unassigned'),('12/04/2023',20100000001,'assigned'),('12/04/2023',20100000002,'assigned'),('12/04/2023',20100000003,'assigned'),('12/04/2023',20100000004,'assigned'),('12/04/2023',20100000005,'assigned'),('12/04/2023',20100000006,'assigned'),('12/04/2023',20100000007,'assigned'),('12/04/2023',20100000008,'assigned'),('12/04/2023',20100000009,'assigned'),('12/04/2023',20100000010,'assigned'),('12/04/2023',20100000011,'assigned'),('12/04/2023',20100000012,'assigned'),('12/04/2023',20100000013,'assigned'),('12/04/2023',20100000014,'assigned'),('12/04/2023',20100000015,'assigned'),('12/04/2023',20100000016,'assigned'),('12/04/2023',20100000017,'assigned'),('12/04/2023',20100000018,'assigned'),('12/04/2023',20100000019,'assigned'),('12/04/2023',20100000020,'assigned'),('12/04/2023',20100000021,'assigned'),('12/04/2023',20100000022,'assigned'),('12/04/2023',20100000023,'assigned'),('12/04/2023',20100000024,'assigned'),('12/04/2023',20100000025,'assigned'),('12/04/2023',20100000026,'unassigned'),('12/04/2023',20100000027,'unassigned'),('12/04/2023',20100000028,'unassigned'),('12/04/2023',20100000029,'unassigned'),('12/04/2023',20100000030,'unassigned'),('12/04/2023',20100000031,'unassigned'),('12/04/2023',20100000032,'unassigned'),('12/04/2023',20100000033,'unassigned'),('12/04/2023',20100000034,'unassigned'),('12/04/2023',20100000035,'unassigned'),('12/04/2023',20100000036,'unassigned'),('12/04/2023',20100000037,'unassigned'),('12/04/2023',20100000038,'unassigned'),('12/04/2023',20100000039,'unassigned'),('12/04/2023',20100000040,'unassigned'),('12/04/2023',20100000041,'unassigned'),('12/04/2023',20100000042,'unassigned'),('12/04/2023',20100000043,'unassigned'),('12/04/2023',20100000044,'unassigned'),('12/04/2023',20100000045,'unassigned'),('12/04/2023',20100000046,'unassigned'),('12/04/2023',20100000047,'unassigned'),('12/04/2023',20100000048,'unassigned'),('12/04/2023',20100000049,'unassigned'),('12/04/2023',20100000050,'unassigned'),('12/04/2023',20100000051,'unassigned'),('12/04/2023',20100000052,'unassigned'),('12/04/2023',20100000053,'unassigned'),('12/04/2023',20100000054,'unassigned'),('12/04/2023',20100000055,'unassigned'),('12/04/2023',20100000056,'unassigned'),('12/04/2023',20100000057,'unassigned'),('12/04/2023',20100000058,'unassigned'),('12/04/2023',20100000059,'unassigned'),('12/04/2023',20100000060,'unassigned'),('12/04/2023',20100000061,'unassigned'),('12/04/2023',20100000062,'unassigned'),('12/04/2023',20100000063,'unassigned'),('12/04/2023',20100000064,'unassigned'),('12/04/2023',20100000065,'unassigned'),('12/04/2023',20100000066,'unassigned'),('12/04/2023',20100000067,'unassigned'),('12/04/2023',20100000068,'unassigned'),('12/04/2023',20100000069,'unassigned'),('12/04/2023',20100000070,'unassigned'),('12/04/2023',20100000071,'unassigned'),('12/04/2023',20100000072,'unassigned'),('12/04/2023',20100000073,'unassigned'),('12/04/2023',20100000074,'unassigned'),('12/04/2023',20100000075,'unassigned'),('12/04/2023',20100000076,'unassigned'),('12/04/2023',20100000077,'unassigned'),('12/04/2023',20100000078,'unassigned'),('12/04/2023',20100000079,'unassigned'),('12/04/2023',20100000080,'unassigned'),('12/04/2023',20100000081,'unassigned'),('12/04/2023',20100000082,'unassigned'),('12/04/2023',20100000083,'unassigned'),('12/04/2023',20100000084,'unassigned'),('12/04/2023',20100000085,'unassigned'),('12/04/2023',20100000086,'unassigned'),('12/04/2023',20100000087,'unassigned'),('12/04/2023',20100000088,'unassigned'),('12/04/2023',20100000089,'unassigned'),('12/04/2023',20100000090,'unassigned'),('12/04/2023',20100000091,'unassigned'),('12/04/2023',20100000092,'unassigned'),('12/04/2023',20100000093,'unassigned'),('12/04/2023',20100000094,'unassigned'),('12/04/2023',20100000095,'unassigned'),('12/04/2023',20100000096,'unassigned'),('12/04/2023',20100000097,'unassigned'),('12/04/2023',20100000098,'unassigned'),('12/04/2023',20100000099,'unassigned'),('12/04/2023',20100000100,'unassigned'),('12/04/2023',10100000001,'assigned'),('12/04/2023',10100000002,'assigned'),('12/04/2023',10100000003,'assigned'),('12/04/2023',10100000004,'assigned'),('12/04/2023',10100000005,'assigned'),('12/04/2023',10100000006,'assigned'),('12/04/2023',10100000007,'assigned'),('12/04/2023',10100000008,'assigned'),('12/04/2023',10100000009,'assigned'),('12/04/2023',10100000010,'assigned'),('12/04/2023',10100000011,'assigned'),('12/04/2023',10100000012,'assigned'),('12/04/2023',10100000013,'assigned'),('12/04/2023',10100000014,'assigned'),('12/04/2023',10100000015,'assigned'),('12/04/2023',10100000016,'assigned'),('12/04/2023',10100000017,'assigned'),('12/04/2023',10100000018,'assigned'),('12/04/2023',10100000019,'assigned'),('12/04/2023',10100000020,'assigned'),('12/04/2023',10100000021,'assigned'),('12/04/2023',10100000022,'assigned'),('12/04/2023',10100000023,'assigned'),('12/04/2023',10100000024,'assigned'),('12/04/2023',10100000025,'assigned'),('12/04/2023',10100000026,'assigned'),('12/04/2023',10100000027,'assigned'),('12/04/2023',10100000028,'assigned'),('12/04/2023',10100000029,'assigned'),('12/04/2023',10100000030,'assigned'),('12/04/2023',10100000031,'assigned'),('12/04/2023',10100000032,'assigned'),('12/04/2023',10100000033,'assigned'),('12/04/2023',10100000034,'assigned'),('12/04/2023',10100000035,'assigned'),('12/04/2023',10100000036,'assigned'),('12/04/2023',10100000037,'assigned'),('12/04/2023',10100000038,'assigned'),('12/04/2023',10100000039,'assigned'),('12/04/2023',10100000040,'assigned'),('12/04/2023',10100000041,'assigned'),('12/04/2023',10100000042,'assigned'),('12/04/2023',10100000043,'assigned'),('12/04/2023',10100000044,'assigned'),('12/04/2023',10100000045,'assigned'),('12/04/2023',10100000046,'assigned'),('12/04/2023',10100000047,'assigned'),('12/04/2023',10100000048,'assigned'),('12/04/2023',10100000049,'assigned'),('12/04/2023',10100000050,'assigned');
/*!40000 ALTER TABLE `blank_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `domestic_sales_report`
--

DROP TABLE IF EXISTS `domestic_sales_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `domestic_sales_report` (
  `StaffID` int(3) DEFAULT NULL,
  `StaffName` varchar(255) DEFAULT NULL,
  `TicketID` bigint(11) NOT NULL,
  `ReportType` varchar(255) DEFAULT NULL,
  `Taxes` double DEFAULT NULL,
  `CommissionRate` int(10) DEFAULT NULL,
  `PaymentType` varchar(6) DEFAULT NULL,
  `PaymentDetails` varchar(255) DEFAULT NULL,
  `CustomerInformation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TicketID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `domestic_sales_report_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `domestic_sales_report`
--

LOCK TABLES `domestic_sales_report` WRITE;
/*!40000 ALTER TABLE `domestic_sales_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `domestic_sales_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interline_sales_report`
--

DROP TABLE IF EXISTS `interline_sales_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interline_sales_report` (
  `StaffID` int(3) DEFAULT NULL,
  `StaffName` varchar(255) DEFAULT NULL,
  `TicketID` bigint(11) NOT NULL,
  `ReportType` varchar(255) DEFAULT NULL,
  `LocalCurrency` double DEFAULT NULL,
  `LocalTax` double DEFAULT NULL,
  `OtherTax` double DEFAULT NULL,
  `PaymentType` varchar(6) DEFAULT NULL,
  `PaymentDetails` varchar(255) DEFAULT NULL,
  `CommissionRate` int(10) DEFAULT NULL,
  `CustomerInformation` varchar(255) DEFAULT NULL,
  `Date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TicketID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `interline_sales_report_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interline_sales_report`
--

LOCK TABLES `interline_sales_report` WRITE;
/*!40000 ALTER TABLE `interline_sales_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `interline_sales_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket_sales`
--

DROP TABLE IF EXISTS `ticket_sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket_sales` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `ticket_type` varchar(50) DEFAULT NULL,
  `blank_id` varchar(50) DEFAULT NULL,
  `payment_type` varchar(50) DEFAULT NULL,
  `report_type` varchar(50) DEFAULT NULL,
  `departure` varchar(50) DEFAULT NULL,
  `destination` varchar(50) DEFAULT NULL,
  `commission_amount` decimal(10,2) DEFAULT NULL,
  `customer` varchar(50) DEFAULT NULL,
  `discount` decimal(5,2) DEFAULT NULL,
  `refund` varchar(10) DEFAULT NULL,
  `ticket_price` decimal(10,2) DEFAULT NULL,
  `tax_total` decimal(10,2) DEFAULT NULL,
  `grand_total` decimal(10,2) DEFAULT NULL,
  `exchange_rate` decimal(10,2) DEFAULT NULL,
  `ticket_date` varchar(12) DEFAULT NULL,
  `StaffID` int(11) DEFAULT NULL,
  `card_detail` varchar(255) DEFAULT NULL,
  `payment_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `ticket_sales_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_sales`
--

LOCK TABLES `ticket_sales` WRITE;
/*!40000 ALTER TABLE `ticket_sales` DISABLE KEYS */;
INSERT INTO `ticket_sales` VALUES (58,'444','44400000001','Cash','Interline','London','Rome',9.00,'SarahB 3',0.02,'No',220.00,58.00,273.60,0.54,'01/01/2023',250,'','01/01/2023'),(59,'444','44400000002','Card','Interline','London','Rome',9.00,'Casual',0.00,'Yes',230.00,98.00,328.00,0.54,'01/01/2023',250,'4901000223453456','01/01/2023'),(60,'201','20100000001','Cash','Domestic','London','Scotland',5.00,'Casual',0.00,'No',86.00,15.60,101.60,0.54,'01/01/2023',250,'','01/01/2023'),(61,'444','44400000003','Card','Interline','London','Rome',9.00,'DaveD 2',0.00,'No',220.00,138.00,358.00,0.43,'02/01/2023',250,'5899455432655121','12/04/2023'),(62,'444','44400000004','LatePayment','Interline','London','Rome',9.00,'Chris 1',0.01,'No',230.00,58.00,285.70,0.43,'02/01/2023',250,'',''),(63,'201','20100000002','Card','Domestic','London','Scotland',5.00,'Chris 1',0.01,'No',230.00,58.00,285.70,0.43,'02/01/2023',250,'6454986387338876','02/01/2023'),(64,'444','44400000021','LatePayment','Interline','London','Rome',9.00,'SarahB 3',0.02,'No',250.00,60.00,305.00,0.43,'02/01/2023',211,'',''),(65,'444','44400000022','Card','Interline','London','Rome',9.00,'Casual',0.00,'No',300.00,65.00,365.00,0.43,'02/01/2023',211,'7449155545893456','02/01/2023'),(66,'201','20100000011','Cash','Domestic','London','Scotland',5.00,'Casual',0.00,'Yes',75.00,13.80,88.80,0.43,'02/01/2023',211,'','02/01/2023');
/*!40000 ALTER TABLE `ticket_sales` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-12 22:38:11
