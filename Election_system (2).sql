-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 01, 2021 at 01:30 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Election_system`
--
CREATE DATABASE IF NOT EXISTS `Election_system` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `Election_system`;

-- --------------------------------------------------------

--
-- Table structure for table `Admin`
--

CREATE TABLE `Admin` (
  `id` int(11) NOT NULL,
  `Username` varchar(20) NOT NULL,
  `Password` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Admin`
--

INSERT INTO `Admin` (`id`, `Username`, `Password`) VALUES
(1, 'rootAdmin', 'razvAgbRg2015gt');

-- --------------------------------------------------------

--
-- Table structure for table `Candidates`
--

CREATE TABLE `Candidates` (
  `Id` int(11) NOT NULL,
  `Descriptions` varchar(200) DEFAULT NULL,
  `address` varchar(200) NOT NULL,
  `post_held` varchar(20) NOT NULL,
  `actual_workplace` varchar(30) NOT NULL,
  `office_no` varchar(16) NOT NULL,
  `home_no` varchar(16) NOT NULL,
  `mobile_no` varchar(16) NOT NULL,
  `email` varchar(35) NOT NULL,
  `own_car` tinyint(1) NOT NULL DEFAULT 0,
  `department` int(11) NOT NULL,
  `Admit` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Candidates`
--

INSERT INTO `Candidates` (`Id`, `Descriptions`, `address`, `post_held`, `actual_workplace`, `office_no`, `home_no`, `mobile_no`, `email`, `own_car`, `department`, `Admit`) VALUES
(1, 'For the betterment of tomorrow', '20 Roal Road Port Louis', 'Programmer', 'Port Louis', '6234593', '6984235', '59049437', 'paulSmith@hotmail.com', 1, 1, 1),
(2, 'want to be a candidate', 'RailwayRoad', 'Architectutre', 'Port Louis', '58131213', '15131323', '13513212', 'anyone@gmail.com', 1, 2, 1),
(3, 'need to improve this country', 'Chapel Road', 'Accountant', 'Rose hill', '59506561', '54650154', '65015615', 'James25@gmail.com', 0, 2, 1),
(4, 'hgdcgcfgc', 'Church Road', 'Captian', 'Posrt Louis', '65135135', '65361835', '51513188', 'Jay@hotmail.com', 1, 1, 1),
(5, 'sjbhshbshbdhjs', 'Hospital Road', 'Programmer', 'Eben', '58168153', '531318168', '531515315', 'BrayanSomadoo@gmail.com', 1, 1, 1),
(7, 'shdsh dhjbdfhdb fdjshsd kfhbsd djhbf dhbf', 'Main Street', 'CEO', 'SSSR HOME', '51615132', '516545132', '6516815322', 'Jagnate@gmail.com', 1, 2, 1),
(13, 'shdvsgh dsvdhsj ads adgvsgv svg', 'Railway Road', 'Doctor', 'Moka', '6555136', '5156465', '32135859', 'Chandler@gmail.com', 1, 2, 1),
(14, 'sahdbashbd', 'royal Road', 'Mangaer', 'POrt Louis', '551322', '6515123', '1553525', 'someone@gamilc.com', 0, 1, 0),
(15, 'Want to be a candidate for people in my area', 'Chapel Road, 20', 'Manager', 'Pamplemousse', '58102352', '625648', '632548', 'someone#gmail.com', 1, 1, 1),
(16, 'hfxdc hjgcjh vjh ggg', 'River road', 'Econimist', 'Curepipe', '1531315', '51351352', '59251356', 'peggy@gmail.com', 1, 1, 0),
(24, 'jhh dfdhjbd fdjbf dfbd', 'Temple Road', 'Administrator', 'Youtube', '51681553', '513515351', '5135135153', 'brianawadis@gmail.com', 0, 1, 0),
(25, 'shjbshjbds dsjhbds', 'Jombo road 25', 'Manager', 'Snapchat', '581025', '5135135', '513151312', 'brandnawadis15@gmail.com', 0, 2, 0),
(26, 'sjdshbsh', 'Jundoo Road', 'Captain', 'Emirates', '51531512', '561513123', '543132153', 'gauravtaneja@yahoo.com', 1, 1, 0),
(27, 'sjdbdd djfbdhf d', 'main road', 'Developer', 'Accenture', '5810225', '5812665', '6845453', 'ritutaneja48@gmail.com', 1, 1, 0),
(28, 'sjdh skdjsdh sjbdd', 'Hospital Road', 'Supervisor', 'Facebook', '1513535', '56434268', '98454523', 'karlrock@dr.com', 0, 2, 0),
(29, 'sdhgdv djhf dfj', 'germain street', 'Sale person', 'CompuSpeed', '513512132', '51351312', '35135125', 'sherman@yahoo.com', 0, 1, 0),
(30, 'sasdhsjbd', 'Jordan Road', 'CEO', 'Smarties', '64651123', '68451231', '384513123', 'pjugnaugth@gmail.com', 1, 2, 0),
(31, 'sdsjhbsdf djhbd djhd', 'None', 'Doctor', 'SSRN Hospital', '5531351', '6151312', '6515135', 'navinramgoolam007@gmail.com', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Election`
--

CREATE TABLE `Election` (
  `id` int(11) NOT NULL,
  `Date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `Admin_id` int(11) NOT NULL,
  `Open` tinyint(1) NOT NULL DEFAULT 1,
  `RecentlyClosed` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Election`
--

INSERT INTO `Election` (`id`, `Date_created`, `Admin_id`, `Open`, `RecentlyClosed`) VALUES
(1, '2021-05-22 04:11:31', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `Ministry_Department`
--

CREATE TABLE `Ministry_Department` (
  `id` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `url` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Ministry_Department`
--

INSERT INTO `Ministry_Department` (`id`, `Name`, `url`) VALUES
(1, 'MMM', 'none'),
(2, 'MSM', 'none');

-- --------------------------------------------------------

--
-- Table structure for table `Region`
--

CREATE TABLE `Region` (
  `id` int(11) NOT NULL,
  `region_name` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Region`
--

INSERT INTO `Region` (`id`, `region_name`) VALUES
(1, 'Savanne'),
(2, 'Pamplemousse'),
(3, 'Port-Louis');

-- --------------------------------------------------------

--
-- Table structure for table `Result`
--

CREATE TABLE `Result` (
  `Candidate_id` int(11) NOT NULL,
  `Election_id` int(11) NOT NULL,
  `No_of_Votes` int(11) NOT NULL,
  `DateOfVote` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Voters`
--

CREATE TABLE `Voters` (
  `id` int(11) NOT NULL,
  `Firstname` varchar(20) NOT NULL,
  `Lastname` varchar(20) NOT NULL,
  `Password` longtext DEFAULT NULL,
  `NIC` varchar(30) NOT NULL,
  `Region_id` int(11) DEFAULT NULL,
  `Flag` tinyint(1) NOT NULL DEFAULT 0,
  `gender` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Voters`
--

INSERT INTO `Voters` (`id`, `Firstname`, `Lastname`, `Password`, `NIC`, `Region_id`, `Flag`, `gender`) VALUES
(1, 'Paul', 'Smith', 'password1234', 'S1542453873568998', 1, 0, 'M'),
(2, 'Gavin', 'Kimberlay', 'password1234', 'K123456789245921', 1, 0, 'M'),
(3, 'James', 'Collar', 'password1234', 'C123569874325612', 2, 0, 'M'),
(4, 'Jay', 'Kumar', 'password1234', 'K894561234567839', 2, 0, 'M'),
(5, 'Bryan', 'Soomadoo', 'passsowrd1234', 'S568463157921596', 3, 0, 'M'),
(6, 'Pravin', 'Dodo', 'password1234', 'D632159621785305', 3, 0, 'M'),
(7, 'Jagnate', 'Paullain', 'password1234', 'P569874123654896', 1, 0, 'M'),
(8, 'Ali', 'White', 'password1234', 'W987686423102578', 1, 0, 'M'),
(9, 'Washeem', 'Hanabi', 'password1234', 'H698563201458769', 1, 0, 'M'),
(10, 'Alice', 'Johnson', 'password1234', 'J685347859123024', 1, 0, 'F'),
(11, 'Jordan', 'Pertherson', 'password1234', 'P987512345698756', 1, 0, 'M'),
(12, 'Ibrahim', 'Atlas', 'password1234', 'A987512301230458', 1, 0, 'M'),
(13, 'Chandler', 'Bing', 'Pjv0X13u', 'B6548911326547520', 1, 0, 'F'),
(14, 'Rosa', 'Bing', 'beg3BGVW', 'B032169857425896', 1, 0, 'F'),
(15, 'Joe', 'Tribianny', 'SDMJ7Tbc', 'T548632157963240', 2, 0, 'M'),
(16, 'Peggy', 'Louise', 'password1234', 'L986321054897265', 3, 0, 'F'),
(17, 'Jorge', 'Minor', 'password1234', 'M986314523045786', 3, 0, 'M'),
(18, 'Josh', 'Bong', 'password1234', 'B459863201578426', 3, 0, 'M'),
(19, 'Natasha', 'Jenny', 't694u#7H', 'J459861236059875', 3, 0, 'F'),
(20, 'Neha', 'Balladoo', 'password1234', 'B123694258574569', 3, 0, 'F'),
(21, 'Johan', 'Rehaman', 'password1234', 'R032156475213654', 2, 0, 'M'),
(22, 'John', 'Dishaw', 'password1234', 'D168954236785230', 2, 0, 'M'),
(23, 'Mathew', 'Christin', 'password1234', 'C897634215689325', 2, 0, 'M'),
(24, 'Brian', 'Awadis', 'password1234', 'A1542685429153', 2, 0, 'M'),
(25, 'Brandon', 'Awadis', 'password1234', 'A14526875215945', 3, 0, 'M'),
(26, 'Gaurav', 'Taneja', 'password1234', 'T15264859867542', 1, 0, 'M'),
(27, 'Ritu', 'Taneja', 'password1234', 'T12458975915314', 2, 0, 'F'),
(28, 'Karl', 'Rock', 'password1234', 'R15152626823492', 3, 0, 'M'),
(29, 'Sherman', 'TheVerman', 'password1234', 'S45678912352634', 3, 0, 'M'),
(30, 'Pravind', 'Jugnauth', 'password1234', 'P15978642515784', 3, 0, 'M'),
(31, 'Navin', 'Ramgoolam', 'password1234', 'N56864521865521', 3, 0, 'M');

-- --------------------------------------------------------

--
-- Table structure for table `Votes`
--

CREATE TABLE `Votes` (
  `Voter_id` int(11) NOT NULL,
  `Candidate_id` int(11) NOT NULL,
  `Election_Id` int(11) NOT NULL,
  `time` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Votes`
--

INSERT INTO `Votes` (`Voter_id`, `Candidate_id`, `Election_Id`, `time`) VALUES
(1, 1, 1, '2021-06-01 10:20:36'),
(1, 13, 1, '2021-06-01 10:20:36'),
(2, 2, 1, '2021-06-01 10:21:36'),
(2, 7, 1, '2021-06-01 10:21:36'),
(3, 3, 1, '2021-06-01 10:22:25'),
(3, 15, 1, '2021-06-01 10:22:25'),
(4, 3, 1, '2021-06-01 10:32:41'),
(4, 4, 1, '2021-06-01 10:32:41'),
(5, 5, 1, '2021-06-01 10:33:42'),
(7, 7, 1, '2021-06-01 10:35:41'),
(7, 13, 1, '2021-06-01 10:35:41'),
(8, 1, 1, '2021-06-01 09:52:35'),
(9, 1, 1, '2021-06-01 09:54:13'),
(9, 7, 1, '2021-06-01 09:54:13'),
(10, 1, 1, '2021-06-01 09:55:06'),
(10, 2, 1, '2021-06-01 09:55:06'),
(11, 7, 1, '2021-06-01 09:55:37'),
(11, 13, 1, '2021-06-01 09:55:37'),
(12, 1, 1, '2021-06-01 09:56:06'),
(12, 2, 1, '2021-06-01 09:56:06'),
(13, 2, 1, '2021-06-01 10:38:17'),
(13, 7, 1, '2021-06-01 10:38:17'),
(15, 4, 1, '2021-06-01 10:38:50'),
(15, 15, 1, '2021-06-01 10:38:50'),
(17, 5, 1, '2021-06-01 09:58:11'),
(18, 5, 1, '2021-06-01 10:00:07'),
(19, 5, 1, '2021-06-01 10:00:41'),
(20, 5, 1, '2021-06-01 10:01:09'),
(21, 3, 1, '2021-06-01 10:02:13'),
(21, 4, 1, '2021-06-01 10:02:13'),
(22, 3, 1, '2021-06-01 10:02:49'),
(22, 4, 1, '2021-06-01 10:02:49'),
(23, 4, 1, '2021-06-01 15:20:08'),
(23, 15, 1, '2021-06-01 15:20:08');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Admin`
--
ALTER TABLE `Admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Indexes for table `Candidates`
--
ALTER TABLE `Candidates`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `department` (`department`);

--
-- Indexes for table `Election`
--
ALTER TABLE `Election`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Admin_id` (`Admin_id`);

--
-- Indexes for table `Ministry_Department`
--
ALTER TABLE `Ministry_Department`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Region`
--
ALTER TABLE `Region`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `Result`
--
ALTER TABLE `Result`
  ADD PRIMARY KEY (`Candidate_id`),
  ADD KEY `Election_id` (`Election_id`);

--
-- Indexes for table `Voters`
--
ALTER TABLE `Voters`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Region_id` (`Region_id`);

--
-- Indexes for table `Votes`
--
ALTER TABLE `Votes`
  ADD PRIMARY KEY (`Voter_id`,`Candidate_id`,`Election_Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Admin`
--
ALTER TABLE `Admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Election`
--
ALTER TABLE `Election`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `Ministry_Department`
--
ALTER TABLE `Ministry_Department`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `Region`
--
ALTER TABLE `Region`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `Voters`
--
ALTER TABLE `Voters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Candidates`
--
ALTER TABLE `Candidates`
  ADD CONSTRAINT `Candidates_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `Voters` (`id`),
  ADD CONSTRAINT `Candidates_ibfk_2` FOREIGN KEY (`department`) REFERENCES `Ministry_Department` (`id`);

--
-- Constraints for table `Election`
--
ALTER TABLE `Election`
  ADD CONSTRAINT `Election_ibfk_1` FOREIGN KEY (`Admin_id`) REFERENCES `Admin` (`id`);

--
-- Constraints for table `Result`
--
ALTER TABLE `Result`
  ADD CONSTRAINT `Result_ibfk_1` FOREIGN KEY (`Election_id`) REFERENCES `Election` (`id`);

--
-- Constraints for table `Voters`
--
ALTER TABLE `Voters`
  ADD CONSTRAINT `Voters_ibfk_1` FOREIGN KEY (`Region_id`) REFERENCES `Region` (`id`);

--
-- Constraints for table `Votes`
--
ALTER TABLE `Votes`
  ADD CONSTRAINT `Votes_ibfk_2` FOREIGN KEY (`Voter_id`) REFERENCES `Voters` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
