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
  `BlankType` int(3) DEFAULT NULL,
  `CommissionRate` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CommissionRate`
--

LOCK TABLES `CommissionRate` WRITE;
/*!40000 ALTER TABLE `CommissionRate` DISABLE KEYS */;
/*!40000 ALTER TABLE `CommissionRate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Customer`
--

DROP TABLE IF EXISTS `Customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Customer` (
  `ID` bigint(10) NOT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `CustomerType` varchar(10) DEFAULT NULL,
  `DiscountType` varchar(10) DEFAULT NULL,
  `DiscountAmount` decimal(3,2) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `TotalTickets` int(4) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `Customer_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `TravelAdvisor` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
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
/*!40000 ALTER TABLE `OfficeManager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SalesReport`
--

DROP TABLE IF EXISTS `SalesReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SalesReport` (
  `TicketID` bigint(11) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=322 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Staff`
--

LOCK TABLES `Staff` WRITE;
/*!40000 ALTER TABLE `Staff` DISABLE KEYS */;
INSERT INTO `Staff` VALUES (250,'Penelope','Pitstop','Penelope.p@gmail.com',NULL,'Travel Advisor','56dffc22bd487cf69c74da553acc60de6c60cab2ab41a0bccc4ad1ae6e614583'),(320,'Arthur','Daley',NULL,NULL,'Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(321,'Penelope','Pitstop','Penelope@gmail.com','56 Penlop Road London','Travel Advisor','56dffc22bd487cf69c74da553acc60de6c60cab2ab41a0bccc4ad1ae6e614583');
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
INSERT INTO `TravelAdvisor` VALUES (250),(321);
/*!40000 ALTER TABLE `TravelAdvisor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-03 16:18:21
