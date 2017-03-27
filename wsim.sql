/*
Navicat MySQL Data Transfer

Source Server         : wsim
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : wsim

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2017-03-27 14:04:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) NOT NULL,
  `password` varchar(1024) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `account_username_index` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `oneUserId` int(11) NOT NULL,
  `anotherUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contact_anotherUserId_index` (`anotherUserId`),
  KEY `contact_oneUserId_index` (`oneUserId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
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

-- ----------------------------
-- Table structure for request
-- ----------------------------
DROP TABLE IF EXISTS `request`;
CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requestUserId` int(11) NOT NULL,
  `requestUserName` varchar(12) NOT NULL,
  `acceptUserId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `request_acceptUserId_requestUserId_index` (`acceptUserId`,`requestUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=1;
