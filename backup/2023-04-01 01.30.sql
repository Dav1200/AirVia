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
INSERT INTO `Admin` VALUES (311),(431),(435),(439),(441),(447);
/*!40000 ALTER TABLE `Admin` ENABLE KEYS */;
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
  `DiscountAmount` double DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `TotalTickets` int(4) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `Customer_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `TravelAdvisor` (`StaffID`)
) ENGINE=InnoDB AUTO_INCREMENT=6000000018 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Customer`
--

LOCK TABLES `Customer` WRITE;
/*!40000 ALTER TABLE `Customer` DISABLE KEYS */;
INSERT INTO `Customer` VALUES (6000000001,'dav','singh','davsingh@gmail.com','valued','fixed',2,NULL,4,111),(6000000002,'sam','jain','samjain@gmail.com','valued','fixed',2,'12 high street, liverpool',3,112),(6000000003,'taha','essa','tahaessa@gmail.com','valued','flexible',4,NULL,1,113),(6000000004,'junaid','alam','junaidalam@gmail.com','regular',NULL,NULL,'23 station road, london',1,113),(6000000005,'yusuf','choudary','yusufchoudhury@gmail.com','regular',NULL,NULL,'5 main street, norwich',2,113),(6000000006,'farhana','ali','farhanaali@gmail.com','regular',NULL,NULL,NULL,5,114),(6000000007,'che','law','chelaw@gmail.com','regular',NULL,NULL,'16 park road, brighton',3,115),(6000000009,'a','a','a','valued','fixed',2,'a',2,113),(6000000010,'John','Cena','John.cena@wwe.com','valued','fixed',6,'56 park avenue, london',1,111),(6000000011,'test','test','test@gmail.com','valued','',0,'Testlane123',1,111),(6000000012,'b','b','b','regular','',0,'',2,111),(6000000015,'y','y','y','Regular','None',0,'asdf',2,113),(6000000016,'x','x','x','Regular','None',0,'x',2,111),(6000000017,'tt','tt','tt','Regular','Fixed',1,'tt',1,111);
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
INSERT INTO `OfficeManager` VALUES (411),(438),(445);
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
) ENGINE=InnoDB AUTO_INCREMENT=90000000020 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SalesReport`
--

LOCK TABLES `SalesReport` WRITE;
/*!40000 ALTER TABLE `SalesReport` DISABLE KEYS */;
INSERT INTO `SalesReport` VALUES (90000000001,440,'card','interline','london','new york',3.00,2,55.00,110,345,'0',0.90,20200114,6000000001,411),(90000000002,444,'card','interline','berlin','warsaw',0.80,4,75.00,300,479,'0',1.10,20200305,6000000003,411),(90000000003,440,'cash','interline','london','paris',1.50,1,160.00,160,297,'0',1.60,20200422,6000000004,411),(90000000004,201,'cash','interline','berlin','new york',2.00,2,270.00,540,666,'0',2.10,20200501,6000000005,411),(90000000005,101,'cash','interline','frankfurt','paris',3.00,4,100.00,400,523,'1',0.70,20200604,6000000006,411),(90000000006,420,'card','domestic','california','texas',1.00,3,50.00,150,356,'1',NULL,20200217,6000000002,411),(90000000007,101,'cash','domestic','london','glasgow',1.50,3,40.00,120,160,'0',NULL,20200714,6000000007,411),(90000000008,420,'cash','domestic','barcelona','madrid',2.00,1,50.00,50,80,'0',NULL,20200714,6000000007,411),(90000000011,NULL,NULL,NULL,'London','Rome',3.00,2,140.00,240,400,NULL,0.90,20200716,6000000002,411),(90000000012,NULL,NULL,NULL,'Rome','London',3.00,2,140.00,240,400,NULL,0.90,20200720,6000000002,411),(90000000013,NULL,NULL,NULL,'b','b',2.00,3,140.00,250,400,NULL,8.00,20200824,6000000004,411),(90000000014,NULL,'Card',NULL,'c','c',2.00,2,40.00,80,150,NULL,13.00,20200725,6000000007,411),(90000000015,444,'Card','Interline','y','y',2.00,2,2.00,2,2,'Yes',2.00,2,6000000006,411);
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
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Staff`
--

LOCK TABLES `Staff` WRITE;
/*!40000 ALTER TABLE `Staff` DISABLE KEYS */;
INSERT INTO `Staff` VALUES (111,'frank','furter','frankfurter@ttechttonic.com','17 church road, blackpool','travel advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(112,'holly','harper','hollyharper@ttechttonic.com','44 victoria road, london','travel advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(113,'india','iris','indiairis@ttechttonic.com','51 green lane, birmingham','travel advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(114,'jack','jenny','jackjenny@ttechttonic.com','11 manor road, manchester','travel advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(115,'mia','mason','miamason@ttechttonic.com','32 park crescent, birmingham','travel advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(311,'scarlet','stella','scarletstella@ttechttonic.com','8 chester road, cornwall','admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(411,'thiago','tucker','thiagotucker@ttechttonic.com','30 north road, birmingham','office manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(412,'Elon','Jones','elonjones@ttechttonic.com','12 Jump Street, Bradford','Travel Advisor','adv123'),(416,'a','a','a','a','office Manager','a'),(417,'Marcus ','Rashford ','Marcus.rashford@ttehcttonic.com','83 London Road, London','Travel Advisor','adv123'),(418,'','','','','Travel Advisor',''),(419,'test1','test1','test1','test1','Travel Advisor','test1'),(420,'Test','test','test@gmail.com','testaddress.com','Travel Advisor','123pass'),(421,'','','','','Travel Advisor',''),(422,'encr','encr','encr','encr','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(423,'shush','shush','shush','shush','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(427,'','','','','Travel Advisor','e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'),(428,'dav','dav','dav','dav','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(429,'Chris ','Evan','chrisevan@gmail.com','22 Jump Street','Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(430,'Chris','Pratt','chrispratt@gmail.com','23 Jump Street','Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(431,'Chris','Hemsworth','chrishem@gmail.com','24 Jump Street','Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(432,'Will','Smith','willsmith@gmail.com','25 Jump Street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(433,'Chris','Rock','chrisrock@gmail.com','26 Jump Street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(434,'Chris','Rock','chrisrock@gmail.com','26 Jump Street','Travel Advisor','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(435,'Chris','Rock','chrisrock@gmail.com','26 Jump Street','Admin','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(436,'zoomer','zoomer','zoomer@gmail.com','20 zoomer street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(437,'boomer','boomer','boomer@gmail.com','24 boomer Street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(438,'adam','adam','adam@gmail.com','31 adam street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(439,'tom','smith','toms@gmail.com','tommy street','Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(440,'bob','smith','bbobs@gmail.com','bobby street','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(441,'Bob','Jackson','BobJ@gmail.com','12 bob street ','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(442,'Bob','Jackson','BobJ@gmail.com','12 bob street ','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(443,'john','smith','joth@gmail.com','smithy street','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(444,'bob','smith','bobbs@gmail.com','bobby street','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(445,'Jack','Jones','jackjones@gmail.com','11 Station Square, London','Office Manager','f3a25e124dea6889cd15ccbaf818c4cb7d7c035257b828f173a2384ab9b621ac'),(446,'','','','','Travel Advisor','e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855'),(447,'james','jenson','jamesj@gmail.com','12 Baker Street, London','Admin','fcef631eab0be0f69d940e737b136e0cbcf4f6f1de81f50822862002655af92e'),(448,'Mario','Luigi','mario@gmail.com','Mario World','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(449,'Gwen','Stacey','GwenS@gmail.com','1 Liverpool Street, London','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(450,'Luigi','Mario','Luigi@gmail.com','Mario World','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865'),(451,'tttt','tttt','tttt','atttt','Travel Advisor','b892a25266fd13f4cab70dc40bf331c6fd6a5e625e607d6d135cdde83cbe865');
/*!40000 ALTER TABLE `Staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TicketTurnoverReport`
--

DROP TABLE IF EXISTS `TicketTurnoverReport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `TicketTurnoverReport` (
  `BlankID` bigint(10) NOT NULL,
  `BlankType` int(3) DEFAULT NULL,
  `ReceivedBlanks` int(10) DEFAULT NULL,
  `BlanksAssigned` int(10) DEFAULT NULL,
  `TotalBlanks` int(10) DEFAULT NULL,
  `StartDate` int(8) DEFAULT NULL,
  `AdvisorID` bigint(10) DEFAULT NULL,
  `StaffID` int(3) DEFAULT NULL,
  PRIMARY KEY (`BlankID`),
  KEY `StaffID` (`StaffID`),
  CONSTRAINT `TicketTurnoverReport_ibfk_1` FOREIGN KEY (`StaffID`) REFERENCES `OfficeManager` (`StaffID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TicketTurnoverReport`
--

LOCK TABLES `TicketTurnoverReport` WRITE;
/*!40000 ALTER TABLE `TicketTurnoverReport` DISABLE KEYS */;
INSERT INTO `TicketTurnoverReport` VALUES (10100000005,101,100,80,210,20200501,113,411),(10100000010,101,100,80,200,20201001,114,411),(10100000011,101,100,80,220,20201101,115,411),(10100000012,101,100,80,190,20201201,115,411),(42000000002,440,70,40,120,20200201,111,411),(42000000004,420,150,80,300,20200401,112,411),(42000000006,420,50,30,140,20200601,113,411),(42000000009,420,150,80,250,20200901,114,411),(44000000007,440,70,40,130,20200701,113,411),(44000000008,440,200,140,400,20200801,113,411),(44400000001,444,50,30,100,20200101,111,411),(44400000003,444,200,140,360,20200301,111,411);
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
INSERT INTO `TravelAdvisor` VALUES (111),(112),(113),(114),(115),(444),(446),(448),(449),(450),(451);
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

-- Dump completed on 2023-04-01 13:30:48
