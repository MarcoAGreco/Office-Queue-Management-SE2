-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ott 21, 2019 alle 18:53
-- Versione del server: 10.1.40-MariaDB
-- Versione PHP: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `queue_management_test`
--
CREATE DATABASE IF NOT EXISTS `queue_management_test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `queue_management_test`;

-- --------------------------------------------------------

--
-- Struttura della tabella `counter`
--

CREATE TABLE IF NOT EXISTS `Counter` (
  `CounterID` int(11) NOT NULL,
  `RequestType` varchar(50) NOT NULL,
  PRIMARY KEY (`CounterID`,`RequestType`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `counter`
--

INSERT INTO `Counter` (`CounterID`, `RequestType`) VALUES
(1, 'Accounting'),
(1, 'Package'),
(2, 'Accounting');

-- --------------------------------------------------------

--
-- Struttura della tabella `requesttype`
--

CREATE TABLE IF NOT EXISTS `RequestType` (
  `RequestName` varchar(50) NOT NULL,
  `ServiceTimeMinutes` int(11) NOT NULL,
  PRIMARY KEY (`RequestName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `requesttype`
--

INSERT INTO `RequestType` (`RequestName`, `ServiceTimeMinutes`) VALUES
('Accounting', 15),
('Package', 10);

-- --------------------------------------------------------

--
-- Struttura della tabella `ticket`
--

CREATE TABLE IF NOT EXISTS `Ticket` (
  `TicketID` int(11) NOT NULL,
  `RequestType` varchar(50) NOT NULL,
  `Date` date NOT NULL,
  `Time` time NOT NULL,
  `CounterAssigned` int(11) DEFAULT NULL,
  PRIMARY KEY (`TicketID`,`Date`),
  KEY `CounterAssigned` (`CounterAssigned`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `ticket`
--

INSERT INTO `Ticket` (`TicketID`, `RequestType`, `Date`, `Time`, `CounterAssigned`) VALUES
(1, 'Accounting', '2019-10-18', '10:21:33', NULL),
(2, 'Package', '2019-10-18', '10:21:38', NULL),
(3, 'Accounting', '2019-10-18', '10:21:46', NULL),
(4, 'Accounting', '2019-10-18', '10:22:21', NULL);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `Ticket`
--


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
