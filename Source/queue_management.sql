-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 18, 2019 at 12:53 AM
-- Server version: 5.7.27-0ubuntu0.19.04.1
-- PHP Version: 7.2.19-0ubuntu0.19.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `queue_management`
--
CREATE DATABASE IF NOT EXISTS `queue_management` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `queue_management`;

-- --------------------------------------------------------

--
-- Table structure for table `Counter`
--

CREATE TABLE IF NOT EXISTS `Counter` (
  `CounterID` int(11) NOT NULL,
  `RequestType` varchar(50) NOT NULL,
  PRIMARY KEY (`CounterID`,`RequestType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RequestType`
--

CREATE TABLE IF NOT EXISTS `RequestType` (
  `RequestName` varchar(50) NOT NULL,
  `ServiceTimeMinutes` int(11) NOT NULL,
  PRIMARY KEY (`RequestName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `RequestType`
--

INSERT INTO `RequestType` (`RequestName`, `ServiceTimeMinutes`) VALUES
('Accounting', 15),
('Package', 10);

-- --------------------------------------------------------

--
-- Table structure for table `Ticket`
--

CREATE TABLE IF NOT EXISTS `Ticket` (
  `TicketID` int(11) NOT NULL,
  `RequestType` varchar(50) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `CounterAssigned` int(11) DEFAULT NULL,
  PRIMARY KEY (`TicketID`, `Date`),
  KEY `CounterAssigned` (`CounterAssigned`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Ticket`
--
ALTER TABLE `Ticket`
  ADD CONSTRAINT `Ticket_ibfk_1` FOREIGN KEY (`CounterAssigned`) REFERENCES `Counter` (`CounterID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
