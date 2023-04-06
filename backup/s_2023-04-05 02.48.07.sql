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
INSERT INTO `CommissionRate` VALUES (101,4),(201,5),(420,3),(440,2),(444,9);
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
INSERT INTO `Customer` VALUES (1,'Chris','Smart','ChrisSmart@Gmail.com','Valued','Fixed',0.01,'11 Bricklane',2,250,'Chris'),(2,'David','Dodson','DavidDodson@Gmail.com','Valued','Flexible',0.00,'12 BrickLane',0,250,'DaveD'),(3,'Sarah','Broklehurst','SarahBroklehurst@Gmail.com','Valued','Fixed',0.02,'13 Bricklane ',0,250,'SarahB'),(4,'Dominic','Beatty','DominicBeatty','Regular','None',0.00,'14 BrickLane',0,250,'Dom');
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
) ENGINE=InnoDB AUTO_INCREMENT=322 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Staff`
--

LOCK TABLES `Staff` WRITE;
/*!40000 ALTER TABLE `Staff` DISABLE KEYS */;
INSERT INTO `Staff` VALUES (211,'Dennis','Menace',NULL,NULL,'Travel Advisor','c134547585f40bf1516f5f1d01039f7e275dd10db503d891f292f9cd246b0263'),(220,'Minnie','Minx',NULL,NULL,'Office Manager','ce7d42cbd11296802b162cf3cf916768055ba66702e78d6decbb3c3b86ebd469'),(250,'Penelope','Pitstop','Penelope.p@gmail.com',NULL,'Travel Advisor','56dffc22bd487cf69c74da553acc60de6c60cab2ab41a0bccc4ad1ae6e614583'),(320,'Arthur','Daley',NULL,NULL,'Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(321,'Penelope','Pitstop','Penelope@gmail.com','56 Penlop Road London','Travel Advisor','56dffc22bd487cf69c74da553acc60de6c60cab2ab41a0bccc4ad1ae6e614583');
/*!40000 ALTER TABLE `Staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TicketTurnoverReport`
--

DROP TABLE IF EXISTS `TicketTurnoverReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TicketTurnoverReport` (
  `ReportID` bigint(10) NOT NULL,
  `BlankType` int(3) DEFAULT NULL,
  `ReceivedBlanks` int(10) DEFAULT NULL,
  `BlanksAssigned` int(10) DEFAULT NULL,
  `TotalBlanks` int(10) DEFAULT NULL,
  `Date` int(8) DEFAULT NULL,
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
INSERT INTO `TravelAdvisor` VALUES (211),(250),(321);
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
  PRIMARY KEY (`ad_blank_id`),
  KEY `advisor_id` (`advisor_id`),
  CONSTRAINT `advisor_blanks_ibfk_1` FOREIGN KEY (`advisor_id`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisor_blanks`
--

LOCK TABLES `advisor_blanks` WRITE;
/*!40000 ALTER TABLE `advisor_blanks` DISABLE KEYS */;
INSERT INTO `advisor_blanks` VALUES (255,250,'Penelope Pitstop','01/04/2019',44400000001);
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
  `blanks_received` bigint(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blank_stock`
--

LOCK TABLES `blank_stock` WRITE;
/*!40000 ALTER TABLE `blank_stock` DISABLE KEYS */;
INSERT INTO `blank_stock` VALUES ('01/04/2019',44400000001),('01/04/2019',44400000002),('01/04/2019',44400000003),('01/04/2019',44400000004),('01/04/2019',44400000005),('01/04/2019',44400000006),('01/04/2019',44400000007),('01/04/2019',44400000008),('01/04/2019',44400000009),('01/04/2019',44400000010),('01/04/2019',44400000011),('01/04/2019',44400000012),('01/04/2019',44400000013),('01/04/2019',44400000014),('01/04/2019',44400000015),('01/04/2019',44400000016),('01/04/2019',44400000017),('01/04/2019',44400000018),('01/04/2019',44400000019),('01/04/2019',44400000020),('01/04/2019',44400000021),('01/04/2019',44400000022),('01/04/2019',44400000023),('01/04/2019',44400000024),('01/04/2019',44400000025),('01/04/2019',44400000026),('01/04/2019',44400000027),('01/04/2019',44400000028),('01/04/2019',44400000029),('01/04/2019',44400000030),('01/04/2019',44400000031),('01/04/2019',44400000032),('01/04/2019',44400000033),('01/04/2019',44400000034),('01/04/2019',44400000035),('01/04/2019',44400000036),('01/04/2019',44400000037),('01/04/2019',44400000038),('01/04/2019',44400000039),('01/04/2019',44400000040),('01/04/2019',44400000041),('01/04/2019',44400000042),('01/04/2019',44400000043),('01/04/2019',44400000044),('01/04/2019',44400000045),('01/04/2019',44400000046),('01/04/2019',44400000047),('01/04/2019',44400000048),('01/04/2019',44400000049),('01/04/2019',44400000050),('01/04/2019',44400000051),('01/04/2019',44400000052),('01/04/2019',44400000053),('01/04/2019',44400000054),('01/04/2019',44400000055),('01/04/2019',44400000056),('01/04/2019',44400000057),('01/04/2019',44400000058),('01/04/2019',44400000059),('01/04/2019',44400000060),('01/04/2019',44400000061),('01/04/2019',44400000062),('01/04/2019',44400000063),('01/04/2019',44400000064),('01/04/2019',44400000065),('01/04/2019',44400000066),('01/04/2019',44400000067),('01/04/2019',44400000068),('01/04/2019',44400000069),('01/04/2019',44400000070),('01/04/2019',44400000071),('01/04/2019',44400000072),('01/04/2019',44400000073),('01/04/2019',44400000074),('01/04/2019',44400000075),('01/04/2019',44400000076),('01/04/2019',44400000077),('01/04/2019',44400000078),('01/04/2019',44400000079),('01/04/2019',44400000080),('01/04/2019',44400000081),('01/04/2019',44400000082),('01/04/2019',44400000083),('01/04/2019',44400000084),('01/04/2019',44400000085),('01/04/2019',44400000086),('01/04/2019',44400000087),('01/04/2019',44400000088),('01/04/2019',44400000089),('01/04/2019',44400000090),('01/04/2019',44400000091),('01/04/2019',44400000092),('01/04/2019',44400000093),('01/04/2019',44400000094),('01/04/2019',44400000095),('01/04/2019',44400000096),('01/04/2019',44400000097),('01/04/2019',44400000098),('01/04/2019',44400000099),('01/04/2019',44400000100);
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
INSERT INTO `domestic_sales_report` VALUES (250,'Penelope',20100000002,'Global',13.8,5,'Credit','VISA 6454 9863 8733 8876',NULL),(211,'Dennis',20100000011,'Individual',13.8,5,'Cash',NULL,NULL);
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
INSERT INTO `interline_sales_report` VALUES (250,'Penelope',44400000001,'Individual',230,23,35,NULL,'Valued Customer - can pay later',9,'Chris'),(211,'DennisMenace',44400000021,'Individual',250,25,35,'Valued',NULL,9,'SarahB'),(211,'Dennis',44400000022,'Individual',300,28,37,'Credit','VISA 7449 1555 4589 3456',9,'casual');
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
  `ticket_quantity` int(11) DEFAULT NULL,
  `ticket_price` decimal(10,2) DEFAULT NULL,
  `tax_total` decimal(10,2) DEFAULT NULL,
  `grand_total` decimal(10,2) DEFAULT NULL,
  `late_payment` varchar(5) DEFAULT NULL,
  `exchange_rate` decimal(10,2) DEFAULT NULL,
  `ticket_date` varchar(12) DEFAULT NULL,
  `StaffID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `ticket_sales_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `Staff` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket_sales`
--

LOCK TABLES `ticket_sales` WRITE;
/*!40000 ALTER TABLE `ticket_sales` DISABLE KEYS */;
INSERT INTO `ticket_sales` VALUES (8,'444','NotDoneYet','Card','Interline','London','Rome',9.00,'DaveD 2',0.00,1,100.00,10.00,110.00,'No',0.54,'04-04-2023',250);
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

-- Dump completed on 2023-04-05 14:48:09
