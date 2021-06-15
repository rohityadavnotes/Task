-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 08, 2021 at 03:07 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id14853975_item`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `category_name` varchar(250) NOT NULL,
  `image` varchar(500) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `category_name`, `image`) VALUES
(1, 'Roti', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(2, 'Rice', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(3, 'Soups', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(4, 'MainCourse', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(5, 'Starter', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(6, 'MC', 'https://backend24.000webhostapp.com/image/photo_place_holder.png');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `rate` varchar(250) NOT NULL,
  `image` varchar(500) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `category_id`, `name`, `rate`, `image`) VALUES
(1, 1, 'rumali roti', '55', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(2, 2, 'Jeera Rice', '180', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(3, 2, 'Jeera Rice', '180', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(4, 3, 'Manchow Soup', '200', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(5, 3, 'Lemon Coriender Soup', '250', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(6, 1, 'Butter Naan', '25', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(7, 4, 'Paneer Kolhapuri', '250', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(8, 5, 'Aloo Chat', '50', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(9, 5, 'Chana Masala', '120', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(10, 6, 'Dal Fry', '140', 'https://backend24.000webhostapp.com/image/photo_place_holder.png'),
(11, 6, 'Dal tadka', '150', 'https://backend24.000webhostapp.com/image/photo_place_holder.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
