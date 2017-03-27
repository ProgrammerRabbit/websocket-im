-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.16 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 wsim 的数据库结构
CREATE DATABASE IF NOT EXISTS `wsim` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `wsim`;

-- 导出  表 wsim.account 结构
CREATE TABLE IF NOT EXISTS `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) NOT NULL,
  `password` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_username_index` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 wsim.contact 结构
CREATE TABLE IF NOT EXISTS `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oneUserId` int(11) NOT NULL,
  `anotherUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_anotherUserId_index` (`anotherUserId`),
  KEY `contact_oneUserId_index` (`oneUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 wsim.message 结构
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `fromId` int(11) NOT NULL,
  `fromUserName` varchar(12) NOT NULL,
  `toId` int(11) NOT NULL,
  `content` varchar(2048) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `message_toId_fromId_index` (`toId`,`fromId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
-- 导出  表 wsim.request 结构
CREATE TABLE IF NOT EXISTS `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requestUserId` int(11) NOT NULL,
  `requestUserName` varchar(12) NOT NULL,
  `acceptUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `request_acceptUserId_requestUserId_index` (`acceptUserId`,`requestUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
