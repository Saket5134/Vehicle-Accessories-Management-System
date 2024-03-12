-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 12, 2024 at 06:59 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vam`
--

-- --------------------------------------------------------

--
-- Table structure for table `accessories`
--

CREATE TABLE `accessories` (
  `aid` int(11) NOT NULL,
  `aname` varchar(200) DEFAULT NULL,
  `cname` varchar(200) DEFAULT NULL,
  `aqty` int(11) DEFAULT NULL,
  `aprice` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accessories`
--

INSERT INTO `accessories` (`aid`, `aname`, `cname`, `aqty`, `aprice`) VALUES
(1, 'Bike Cover', '2 Wheeler', 2, 199),
(2, 'Side Bag', '2 Wheeler', 12, 682),
(3, 'Car Cover', '4 Wheeler', 92, 450),
(4, 'Phone Charger For Car', '4 Wheeler', 197, 200),
(5, 'Phone Holder', '2 Wheeler', 89, 200),
(6, 'Phone Holder', '4 Wheeler', 98, 600),
(7, 'Headlights', '4 Wheeler', 200, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `email`, `password`) VALUES
(1, 'admin@gmail.com', 'Admin@1234');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `cid` int(11) NOT NULL,
  `cname` varchar(200) NOT NULL,
  `cdesc` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`cid`, `cname`, `cdesc`) VALUES
(1, '2 Wheeler', 'Lightweight'),
(3, '3 Wheeler', 'Medium Weight'),
(2, '4 Wheeler', 'Heavyweight');

-- --------------------------------------------------------

--
-- Table structure for table `purchase`
--

CREATE TABLE `purchase` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `uname` varchar(15) DEFAULT NULL,
  `umobile` varchar(15) NOT NULL,
  `aid` int(11) DEFAULT NULL,
  `accessory_name` varchar(200) DEFAULT NULL,
  `qty` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `payment` varchar(200) NOT NULL,
  `p_date` varchar(20) DEFAULT NULL,
  `uaddress` text DEFAULT NULL,
  `receive_date` varchar(20) DEFAULT NULL,
  `supplier` varchar(200) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `purchase`
--

INSERT INTO `purchase` (`id`, `uid`, `uname`, `umobile`, `aid`, `accessory_name`, `qty`, `price`, `total`, `payment`, `p_date`, `uaddress`, `receive_date`, `supplier`, `status`) VALUES
(1, 4, 'neha', '8888888888', 3, 'Car Cover', 3, 450, 1350, 'Cash On Delivery', '15-06-2023', 'rohini', NULL, NULL, 'Pending'),
(3, 5, 'Anjali Jha', '9599275416', 2, 'Side Bag', 1, 682, 682, 'Cash On Delivery', '16-06-2023', 'Rohini', '16-06-2023', 'Ishant', 'Received'),
(4, 1, 'Saket', '7982304799', 5, 'Phone Holder', 3, 200, 600, 'Cash On Delivery', '31-07-2023', 'Rohini', '31-07-2023', 'Ishant', 'Received');

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `sid` int(11) NOT NULL,
  `sname` varchar(100) NOT NULL,
  `semail` varchar(100) DEFAULT NULL,
  `spassword` varchar(100) DEFAULT NULL,
  `sphone` varchar(15) DEFAULT NULL,
  `saddress` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`sid`, `sname`, `semail`, `spassword`, `sphone`, `saddress`) VALUES
(2, 'Ashish', 'ashish@gmail.com', 'Ashish@1234', '9311545461', 'Palam'),
(3, 'Ishant', 'ishant@gmail.com', 'Ishant@1234', '8368024167', 'Rohini'),
(1, 'Suhail ', 'suhail@gmail.com', 'Suhail@1234', '8595636196', 'Mukundpur');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uid` int(11) NOT NULL,
  `uname` varchar(200) DEFAULT NULL,
  `uemail` varchar(100) DEFAULT NULL,
  `upassword` varchar(100) DEFAULT NULL,
  `umobile` varchar(15) DEFAULT NULL,
  `uaddress` text NOT NULL,
  `usecqus` text DEFAULT NULL,
  `uans` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `uname`, `uemail`, `upassword`, `umobile`, `uaddress`, `usecqus`, `uans`) VALUES
(1, 'Saket', 'saket03@gmail.com', 'Saket@1234', '7982304799', 'Rohini', 'What is your favourite colour ?', 'Blue'),
(2, 'Daksh Dogra', 'daksh@gmail.com', 'Daksh@1234', '8373949065', 'Pitampura', 'What was your first car ?', 'Wagonr'),
(3, 'Shruti', 'shruti@gmail.com', 'Shruti@1234', '9311545461', 'Rohini', 'What is your family name ?', 'Guddi'),
(4, 'neha', '123@gmail.com', 'Neha@1234', '8888888888', 'rohini', 'What was your first car ?', 'santro'),
(5, 'Anjali Jha', 'anjalijha0124@gmail.com', 'Anjali@1234', '9599275416', 'Rohini', 'What is your favourite colour ?', 'Pink');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accessories`
--
ALTER TABLE `accessories`
  ADD PRIMARY KEY (`aid`),
  ADD KEY `fk_category_name` (`cname`);

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`cname`),
  ADD UNIQUE KEY `cid_UNIQUE` (`cid`);

--
-- Indexes for table `purchase`
--
ALTER TABLE `purchase`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_user_uid` (`uid`),
  ADD KEY `fk_supplier_name` (`supplier`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`sname`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accessories`
--
ALTER TABLE `accessories`
  MODIFY `aid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `purchase`
--
ALTER TABLE `purchase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `accessories`
--
ALTER TABLE `accessories`
  ADD CONSTRAINT `fk_category_name` FOREIGN KEY (`cname`) REFERENCES `category` (`cname`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `purchase`
--
ALTER TABLE `purchase`
  ADD CONSTRAINT `fk_supplier_name` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`sname`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user_uid` FOREIGN KEY (`uid`) REFERENCES `users` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
