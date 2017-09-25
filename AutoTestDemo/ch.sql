/*
Navicat MySQL Data Transfer

Source Server         : 10.131.0.136
Source Server Version : 50630
Source Host           : 10.131.0.136:3306
Source Database       : ch

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2017-09-25 11:40:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `section` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for autotimer
-- ----------------------------
DROP TABLE IF EXISTS `autotimer`;
CREATE TABLE `autotimer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qcdbid` int(11) DEFAULT NULL,
  `testid` varchar(2000) CHARACTER SET latin1 DEFAULT NULL,
  `execdate` varchar(20) CHARACTER SET latin1 NOT NULL,
  `touserid` varchar(10000) CHARACTER SET latin1 DEFAULT NULL,
  `ccuserid` varchar(10000) CHARACTER SET latin1 DEFAULT NULL,
  `newuserid` int(11) DEFAULT NULL,
  `updateuserid` int(11) DEFAULT NULL,
  `newdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`execdate`),
  KEY `FK_newuser` (`newuserid`),
  KEY `FK_updateuser` (`updateuserid`),
  CONSTRAINT `FK_newuser` FOREIGN KEY (`newuserid`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_updateuser` FOREIGN KEY (`updateuserid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=293 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for description
-- ----------------------------
DROP TABLE IF EXISTS `description`;
CREATE TABLE `description` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET gbk DEFAULT NULL,
  `type` varchar(20) CHARACTER SET gbk DEFAULT NULL,
  `Description` varchar(500) CHARACTER SET gbk DEFAULT NULL,
  `resultDescription` varchar(500) DEFAULT NULL,
  `resultType` varchar(20) DEFAULT NULL,
  `resultName` varchar(50) DEFAULT NULL,
  `testID` int(11) DEFAULT NULL,
  `step` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_testID` (`testID`),
  CONSTRAINT `FK_testID` FOREIGN KEY (`testID`) REFERENCES `test` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9680 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `projectid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qcdb
-- ----------------------------
DROP TABLE IF EXISTS `qcdb`;
CREATE TABLE `qcdb` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dbname` varchar(50) DEFAULT NULL,
  `ip` varchar(50) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `projectid` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for recordexec
-- ----------------------------
DROP TABLE IF EXISTS `recordexec`;
CREATE TABLE `recordexec` (
  `id` int(225) NOT NULL AUTO_INCREMENT,
  `redmineNo` varchar(50) DEFAULT NULL,
  `qcdbID` varchar(100) DEFAULT NULL,
  `userNo` varchar(50) DEFAULT NULL,
  `execdate` datetime DEFAULT NULL,
  `execTime` varchar(50) DEFAULT NULL,
  `testCount` int(50) DEFAULT NULL,
  `success` int(50) DEFAULT NULL,
  `fail` int(50) DEFAULT NULL,
  `succesRate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for servercount
-- ----------------------------
DROP TABLE IF EXISTS `servercount`;
CREATE TABLE `servercount` (
  `id` int(225) NOT NULL AUTO_INCREMENT,
  `countTime` date DEFAULT NULL,
  `ppCount` varchar(5000) DEFAULT NULL,
  `apiCount` int(50) DEFAULT NULL,
  `addTest` int(50) DEFAULT NULL,
  `upTool` int(50) DEFAULT NULL,
  `runTest` int(50) DEFAULT NULL,
  `useTool` int(50) DEFAULT NULL,
  `activeProject` varchar(5000) DEFAULT NULL,
  `updateTest` varchar(5000) DEFAULT NULL,
  `regresscount` int(50) DEFAULT NULL,
  `releasecount` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for switcham
-- ----------------------------
DROP TABLE IF EXISTS `switcham`;
CREATE TABLE `switcham` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `qid` int(10) DEFAULT NULL,
  `amdesc` varchar(255) DEFAULT NULL,
  `amurl` varchar(255) DEFAULT NULL,
  `amtemp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Qcdb_id` (`qid`) USING BTREE,
  CONSTRAINT `FK_Qcdb_id` FOREIGN KEY (`qid`) REFERENCES `qcdb` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for test
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `testName` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `newUserID` int(11) DEFAULT NULL,
  `updateUserID` int(11) DEFAULT NULL,
  `projectID` int(11) DEFAULT NULL,
  `reportURL` varchar(500) DEFAULT NULL,
  `value` int(2) DEFAULT NULL,
  `success` int(11) DEFAULT NULL,
  `fail` int(11) DEFAULT NULL,
  `tid` varchar(11) DEFAULT NULL,
  `flag` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_newUserID` (`newUserID`),
  KEY `FK_updateUserID` (`updateUserID`),
  KEY `FK_projectID` (`projectID`),
  CONSTRAINT `FK_newUserID` FOREIGN KEY (`newUserID`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_projectID` FOREIGN KEY (`projectID`) REFERENCES `project` (`id`),
  CONSTRAINT `FK_updateUserID` FOREIGN KEY (`updateUserID`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=761 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tools
-- ----------------------------
DROP TABLE IF EXISTS `tools`;
CREATE TABLE `tools` (
  `id` int(225) NOT NULL AUTO_INCREMENT,
  `type` int(2) DEFAULT NULL,
  `toolClass` int(2) DEFAULT NULL,
  `useCount` int(225) DEFAULT NULL,
  `downloadCount` int(255) DEFAULT NULL,
  `uploadtime` datetime DEFAULT NULL,
  `uploaduid` int(10) DEFAULT NULL,
  `studyurl` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_uid` (`uploaduid`),
  CONSTRAINT `FK_uid` FOREIGN KEY (`uploaduid`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for train
-- ----------------------------
DROP TABLE IF EXISTS `train`;
CREATE TABLE `train` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `fileurl` varchar(1000) DEFAULT NULL,
  `temp` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `authorityID` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_authorityID` (`authorityID`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for workdynamic
-- ----------------------------
DROP TABLE IF EXISTS `workdynamic`;
CREATE TABLE `workdynamic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `cid` varchar(255) DEFAULT NULL,
  `temp` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cid` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
