-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (i486)
--
-- Host: pbx2    Database: asterisk
-- ------------------------------------------------------
-- Server version	5.1.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS asterisk;
CREATE DATABASE asterisk;
USE asterisk;

--
-- Table structure for table `admin_action`
--

DROP TABLE IF EXISTS `admin_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_action` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `objectType` varchar(32) NOT NULL DEFAULT '',
  `objectIds` varchar(80) NOT NULL DEFAULT '',
  `userLogin` varchar(255) NOT NULL DEFAULT '',
  `actionType` varchar(32) NOT NULL DEFAULT '',
  `actionInfo` varchar(255) DEFAULT NULL,
  `actionTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_application`
--

DROP TABLE IF EXISTS `cc_application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cc_application` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `configName` varchar(50) NOT NULL,
  `country` char(5) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cc_application_it_enterprise` (`enterpriseId`),
  CONSTRAINT `fk_cc_application_it_enterprise` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_did`
--

DROP TABLE IF EXISTS `cc_did`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cc_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL DEFAULT '0',
  `serviceType` varchar(50) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `accessType` varchar(50) DEFAULT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cc_did_cc_application` (`foreignId`),
  CONSTRAINT `fk_cc_did_cc_application` FOREIGN KEY (`foreignId`) REFERENCES `cc_application` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cc_did_step`
--

DROP TABLE IF EXISTS `cc_did_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cc_did_step` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cc_dids_did` (`didId`),
  CONSTRAINT `fk_cc_dids_did` FOREIGN KEY (`didId`) REFERENCES `cc_did` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cdr_call_detail_record`
--

DROP TABLE IF EXISTS `cdr_call_detail_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdr_call_detail_record` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uniqueid` varchar(32) NOT NULL DEFAULT '',
  `startdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `clid` varchar(80) NOT NULL DEFAULT '',
  `src` varchar(80) NOT NULL DEFAULT '',
  `dst` varchar(80) NOT NULL DEFAULT '',
  `dcontext` varchar(80) NOT NULL DEFAULT '',
  `channel` varchar(80) NOT NULL DEFAULT '',
  `dstchannel` varchar(80) NOT NULL DEFAULT '',
  `lastapp` varchar(80) NOT NULL DEFAULT '',
  `lastdata` varchar(80) NOT NULL DEFAULT '',
  `duration` int(11) NOT NULL DEFAULT '0',
  `billsec` int(11) NOT NULL DEFAULT '0',
  `disposition` varchar(45) NOT NULL DEFAULT '',
  `amaflags` int(11) NOT NULL DEFAULT '0',
  `accountcode` varchar(20) NOT NULL DEFAULT '',
  `userfield` varchar(255) NOT NULL DEFAULT '',
  `asteriskId` varchar(255) NOT NULL,
  `answerdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `stopdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hangupcause` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniqueid` (`uniqueid`),
  KEY `uniqueId_index` (`uniqueid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dw_cdr_gx_data`
--

DROP TABLE IF EXISTS `dw_cdr_gx_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dw_cdr_gx_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `serviceFamily` varchar(30) NOT NULL,
  `enterprise` varchar(2) NOT NULL,
  `pointId` bigint(20) unsigned NOT NULL,
  `calls` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dw_cdr_gx_point`
--

DROP TABLE IF EXISTS `dw_cdr_gx_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dw_cdr_gx_point` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pointDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_did`
--

DROP TABLE IF EXISTS `ip_did`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `accessType` varchar(30) DEFAULT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ippbx_did` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_did` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_did_step`
--

DROP TABLE IF EXISTS `ip_did_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_did_step` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_dids_did` (`didId`),
  CONSTRAINT `fk_dids_did` FOREIGN KEY (`didId`) REFERENCES `ip_did` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_equipment`
--

DROP TABLE IF EXISTS `ip_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_equipment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `equipmentModelId` bigint(20) unsigned NOT NULL,
  `serialNumber` varchar(50) NOT NULL,
  `macAddress` varchar(12) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_serialNumber` (`serialNumber`),
  UNIQUE KEY `uq_macAddress` (`macAddress`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_equipment_activation_attempt`
--

DROP TABLE IF EXISTS `ip_equipment_activation_attempt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_equipment_activation_attempt` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `equipmentId` bigint(20) unsigned NOT NULL,
  `attemptDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `activationCode` varchar(10) NOT NULL,
  `success` tinyint(1) NOT NULL,
  `extensionId` bigint(20) unsigned NOT NULL,
  `ipAddress` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_equipment_manufacturer`
--

DROP TABLE IF EXISTS `ip_equipment_manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_equipment_manufacturer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `internalName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_equipment_model`
--

DROP TABLE IF EXISTS `ip_equipment_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_equipment_model` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `equipmentManufacturerId` bigint(20) unsigned NOT NULL,
  `productNumber` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_extension`
--

DROP TABLE IF EXISTS `ip_extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_extension` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `internalNumber` smallint(5) unsigned NOT NULL,
  `callerId` varchar(20) NOT NULL,
  `didId` bigint(20) unsigned NOT NULL,
  `adminEnabled` tinyint(1) NOT NULL,
  `hasEmergencyAddress` tinyint(1) NOT NULL,
  `hasIax` tinyint(1) NOT NULL,
  `hasSip` tinyint(1) NOT NULL,
  `equipmentId` bigint(20) unsigned NOT NULL,
  `voiceMailBoxId` bigint(20) unsigned NOT NULL,
  `language` char(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ippbx_extension` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_extension` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_extension_has_equipment`
--

DROP TABLE IF EXISTS `ip_extension_has_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_extension_has_equipment` (
  `extensionId` bigint(20) unsigned NOT NULL,
  `equipmentId` bigint(20) unsigned NOT NULL,
  `applyDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `enabled` tinyint(1) NOT NULL,
  `disableDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `activated` tinyint(1) NOT NULL,
  `activationDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `activationCode` varchar(10) NOT NULL,
  `activationAttempts` int(10) unsigned NOT NULL DEFAULT 0, 
  `encryptionKey` text,
  `fileVersion` text,
  PRIMARY KEY (`extensionId`,`equipmentId`,`applyDate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_ip_pbx`
--

DROP TABLE IF EXISTS `ip_ip_pbx`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_ip_pbx` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_ring_group`
--

DROP TABLE IF EXISTS `ip_ring_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_ring_group` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_ring_group_has_extension`
--

DROP TABLE IF EXISTS `ip_ring_group_has_extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_ring_group_has_extension` (
  `ringGroupId` bigint(20) unsigned NOT NULL,
  `extensionId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`ringGroupId`,`extensionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_virtual_offshore_number`
--

DROP TABLE IF EXISTS `ip_virtual_offshore_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_virtual_offshore_number` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `didId` bigint(20) unsigned NOT NULL,
  `language` char(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ippbx_von` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_von` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ip_voice_mail_box`
--

DROP TABLE IF EXISTS `ip_voice_mail_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ip_voice_mail_box` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(5) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_did`
--

DROP TABLE IF EXISTS `it_did`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `externalNumber` varchar(20) NOT NULL,
  `serviceFamily` varchar(20) NOT NULL,
  `servicetype` varchar(50) NOT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `it_did_externalNumber` (`externalNumber`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_enterprise`
--

DROP TABLE IF EXISTS `it_enterprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_enterprise` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `ip` varchar(30) NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `audioFolder` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `it_ip_blacklist`
--

DROP TABLE IF EXISTS `it_ip_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_ip_blacklist` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(30) NOT NULL,
  `recordNumber` bigint(20) NOT NULL,
  `status` varchar(20) NOT NULL,
  `runDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `it_outgoing_route`
--

DROP TABLE IF EXISTS `it_outgoing_route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_outgoing_route` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `serviceFamily` varchar(50) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `accessType` varchar(50) DEFAULT NULL,
  `ldiCode` varchar(5) NOT NULL,
  `ldnCode` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_const` (`enterpriseId`,`serviceFamily`,`serviceType`,`accessType`,`ldiCode`,`ldnCode`),
  KEY `fk_route_outgoing_enterprise` (`enterpriseId`),
  CONSTRAINT `fk_route_outgoing_enterprise` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_outgoing_route_step`
--

DROP TABLE IF EXISTS `it_outgoing_route_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_outgoing_route_step` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `routeId` bigint(20) unsigned NOT NULL,
  `providerPeerId` bigint(20) unsigned NOT NULL,
  `priority` tinyint(4) NOT NULL DEFAULT '1',
  `outgoingPrefix` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_const_step` (`routeId`,`providerPeerId`,`priority`,`outgoingPrefix`),
  KEY `fk_route_outgoing_step` (`routeId`),
  KEY `fk_step_provider_peer` (`providerPeerId`),
  CONSTRAINT `fk_route_outgoing_step` FOREIGN KEY (`routeId`) REFERENCES `it_outgoing_route` (`id`),
  CONSTRAINT `fk_step_provider_peer` FOREIGN KEY (`providerPeerId`) REFERENCES `it_provider_peer` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_peer`
--

DROP TABLE IF EXISTS `it_peer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_peer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_provider`
--

DROP TABLE IF EXISTS `it_provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_provider` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_provider_peer`
--

DROP TABLE IF EXISTS `it_provider_peer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_provider_peer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `providerId` bigint(20) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `hasIax` tinyint(1) NOT NULL,
  `hasSip` tinyint(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `host` varchar(20) DEFAULT NULL,
  `secret` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_it_provider_peer` (`providerId`),
  CONSTRAINT `fk_it_provider_peer` FOREIGN KEY (`providerId`) REFERENCES `it_provider` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `it_register_details`
--

DROP TABLE IF EXISTS `it_register_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_register_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) NOT NULL,
  `secret` varchar(80) NOT NULL,
  `srcIpAdress` varchar(30) NOT NULL,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `it_register_intent`
--

DROP TABLE IF EXISTS `it_register_intent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_register_intent` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(30) NOT NULL,
  `runDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `recordNumber` bigint(20) NOT NULL,
  `lastRecordNumber` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `it_voice_mail_box`
--

DROP TABLE IF EXISTS `it_voice_mail_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `it_voice_mail_box` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ivr_did`
--

DROP TABLE IF EXISTS `ivr_did`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ivr_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `accessType` varchar(50) DEFAULT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ivr_ivr` (`foreignId`),
  CONSTRAINT `fk_ivr_ivr` FOREIGN KEY (`foreignId`) REFERENCES `ivr_interactive_voice_response` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ivr_did_step`
--

DROP TABLE IF EXISTS `ivr_did_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ivr_did_step` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ivr_dids_did` (`didId`),
  CONSTRAINT `fk_ivr_dids_did` FOREIGN KEY (`didId`) REFERENCES `ivr_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ivr_interactive_voice_response`
--

DROP TABLE IF EXISTS `ivr_interactive_voice_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ivr_interactive_voice_response` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `configName` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_it_enterprise` (`enterpriseId`),
  CONSTRAINT `fk_it_enterprise` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provisioning_file`
--

DROP TABLE IF EXISTS `provisioning_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provisioning_file` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(50) NOT NULL,
  `contents` blob NOT NULL,
  `version` varchar(20) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `provisioning_file_request`
--

DROP TABLE IF EXISTS `provisioning_file_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provisioning_file_request` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `filename` varchar(50) NOT NULL,
  `requestdate` datetime NOT NULL,
  `requestuseragent` varchar(20) NOT NULL,
  `requestaddress` varchar(20) NOT NULL,
  `response` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rt_extension`
--

DROP TABLE IF EXISTS `rt_extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rt_extension` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `context` varchar(20) NOT NULL DEFAULT '',
  `exten` varchar(20) NOT NULL DEFAULT '',
  `priority` tinyint(4) NOT NULL DEFAULT '0',
  `app` varchar(20) NOT NULL DEFAULT '',
  `appdata` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cntx_ext_prior` (`context`,`exten`,`priority`) USING BTREE,
  KEY `cntx_ext_prior_index` (`context`,`exten`,`priority`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rt_iax_peer`
--

DROP TABLE IF EXISTS `rt_iax_peer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rt_iax_peer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `username` varchar(30) DEFAULT NULL,
  `type` varchar(6) NOT NULL,
  `secret` varchar(50) DEFAULT NULL,
  `md5secret` varchar(32) DEFAULT NULL,
  `dbsecret` varchar(100) DEFAULT NULL,
  `notransfer` varchar(10) DEFAULT NULL,
  `inkeys` varchar(100) DEFAULT NULL,
  `outkey` varchar(100) DEFAULT NULL,
  `auth` varchar(100) DEFAULT NULL,
  `accountcode` varchar(100) DEFAULT NULL,
  `amaflags` varchar(100) DEFAULT NULL,
  `callerid` varchar(100) DEFAULT NULL,
  `context` varchar(100) DEFAULT NULL,
  `defaultip` varchar(15) DEFAULT NULL,
  `host` varchar(31) NOT NULL DEFAULT 'dynamic',
  `language` char(5) DEFAULT NULL,
  `mailbox` varchar(50) DEFAULT NULL,
  `deny` varchar(95) DEFAULT NULL,
  `permit` varchar(95) DEFAULT NULL,
  `qualify` varchar(4) DEFAULT NULL,
  `disallow` varchar(100) DEFAULT NULL,
  `allow` varchar(100) DEFAULT NULL,
  `ipaddr` varchar(15) DEFAULT NULL,
  `port` int(11) DEFAULT '0',
  `regseconds` int(11) DEFAULT '0',
  `itPeerId` bigint(20) unsigned NOT NULL,
  `itPeerType` varchar(15) NOT NULL DEFAULT '',
  `userfield` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `iax_peer_name` (`name`),
  UNIQUE KEY `iax_peer_username_idx` (`username`),
  KEY `name_index` (`name`),
  KEY `username_index` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rt_sip_peer`
--

DROP TABLE IF EXISTS `rt_sip_peer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rt_sip_peer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(80) NOT NULL DEFAULT '',
  `host` varchar(31) NOT NULL DEFAULT '',
  `nat` varchar(5) NOT NULL DEFAULT 'no',
  `type` enum('user','peer','friend') NOT NULL DEFAULT 'friend',
  `accountcode` varchar(20) DEFAULT NULL,
  `amaflags` varchar(13) DEFAULT NULL,
  `call-limit` smallint(5) unsigned DEFAULT NULL,
  `callgroup` varchar(10) DEFAULT NULL,
  `callerid` varchar(80) DEFAULT NULL,
  `cancallforward` char(3) DEFAULT 'yes',
  `canreinvite` char(3) DEFAULT 'yes',
  `context` varchar(80) DEFAULT NULL,
  `defaultip` varchar(15) DEFAULT NULL,
  `dtmfmode` varchar(7) DEFAULT NULL,
  `fromuser` varchar(80) DEFAULT NULL,
  `fromdomain` varchar(80) DEFAULT NULL,
  `insecure` varchar(4) DEFAULT NULL,
  `language` char(5) DEFAULT NULL,
  `mailbox` varchar(50) DEFAULT NULL,
  `md5secret` varchar(80) DEFAULT NULL,
  `deny` varchar(95) DEFAULT NULL,
  `permit` varchar(95) DEFAULT NULL,
  `mask` varchar(95) DEFAULT NULL,
  `musiconhold` varchar(100) DEFAULT NULL,
  `pickupgroup` varchar(10) DEFAULT NULL,
  `qualify` char(3) DEFAULT NULL,
  `regexten` varchar(80) DEFAULT NULL,
  `restrictcid` char(3) DEFAULT NULL,
  `rtptimeout` char(3) DEFAULT NULL,
  `rtpholdtimeout` char(3) DEFAULT NULL,
  `secret` varchar(80) DEFAULT NULL,
  `setvar` varchar(100) DEFAULT NULL,
  `disallow` varchar(100) DEFAULT 'all',
  `allow` varchar(100) DEFAULT 'g729;ilbc;gsm;ulaw;alaw',
  `fullcontact` varchar(80) NOT NULL DEFAULT '',
  `ipaddr` varchar(15) NOT NULL DEFAULT '',
  `port` smallint(5) unsigned NOT NULL DEFAULT '0',
  `regserver` varchar(100) DEFAULT NULL,
  `regseconds` int(11) NOT NULL DEFAULT '0',
  `username` varchar(80) NOT NULL DEFAULT '',
  `defaultuser` varchar(80) NOT NULL DEFAULT '',
  `subscribecontext` varchar(80) DEFAULT NULL,
  `itPeerId` bigint(20) unsigned NOT NULL,
  `itPeerType` varchar(15) NOT NULL DEFAULT '',
  `userfield` varchar(256) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `sip_peer_username_idx` (`username`),
  KEY `name_index` (`name`),
  KEY `username_index` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rt_voice_mail_user`
--

DROP TABLE IF EXISTS `rt_voice_mail_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rt_voice_mail_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uniqueid` int(11) NOT NULL,
  `customer_id` varchar(11) NOT NULL DEFAULT '0',
  `context` varchar(50) NOT NULL DEFAULT '',
  `mailbox` varchar(11) NOT NULL DEFAULT '0',
  `password` varchar(5) NOT NULL DEFAULT '0',
  `fullname` varchar(150) NOT NULL DEFAULT '',
  `email` varchar(50) NOT NULL DEFAULT '',
  `pager` varchar(50) NOT NULL DEFAULT '',
  `tz` varchar(10) NOT NULL DEFAULT 'central',
  `attach` varchar(4) NOT NULL DEFAULT 'yes',
  `saycid` varchar(4) NOT NULL DEFAULT 'yes',
  `dialout` varchar(10) NOT NULL DEFAULT '',
  `callback` varchar(10) NOT NULL DEFAULT '',
  `review` varchar(4) NOT NULL DEFAULT 'no',
  `operator` varchar(4) NOT NULL DEFAULT 'no',
  `envelope` varchar(4) NOT NULL DEFAULT 'no',
  `sayduration` varchar(4) NOT NULL DEFAULT 'no',
  `saydurationm` tinyint(4) NOT NULL DEFAULT '1',
  `sendvoicemail` varchar(4) NOT NULL DEFAULT 'no',
  `delete` varchar(4) NOT NULL DEFAULT 'no',
  `nextaftercmd` varchar(4) NOT NULL DEFAULT 'yes',
  `forcename` varchar(4) NOT NULL DEFAULT 'no',
  `forcegreetings` varchar(4) NOT NULL DEFAULT 'no',
  `hidefromdir` varchar(4) NOT NULL DEFAULT 'yes',
  `stamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `itVoiceMailBoxId` bigint(20) unsigned NOT NULL,
  `emailTemplate` varchar(30) NOT NULL,
  `mailboxName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `rt_vmu_uniqueId` (`uniqueid`),
  KEY `mailbox_context` (`mailbox`,`context`),
  KEY `rt_vmu_uniqueId_index` (`uniqueid`) USING BTREE,
  KEY `email_template` (`emailTemplate`),
  KEY `rt_voice_mail_user_ibfk_1` (`emailTemplate`),
  CONSTRAINT `rt_voice_mail_user_ibfk_1` FOREIGN KEY (`emailTemplate`) REFERENCES `vm_email_template` (`uniqueid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vm_email_template`
--

DROP TABLE IF EXISTS `vm_email_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vm_email_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uniqueid` varchar(30) NOT NULL,
  `format` varchar(30) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `body` text NOT NULL,
  `senderName` varchar(100) NOT NULL,
  `senderAddress` varchar(100) NOT NULL,
  `dateFormat` varchar(40) DEFAULT NULL,
  `language` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_id` (`uniqueid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vm_voice_message`
--

DROP TABLE IF EXISTS `vm_voice_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vm_voice_message` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `msgnum` int(11) NOT NULL DEFAULT '0',
  `dir` varchar(100) DEFAULT '',
  `context` varchar(80) DEFAULT '',
  `macrocontext` varchar(80) DEFAULT '',
  `callerid` varchar(40) DEFAULT '',
  `origtime` varchar(40) DEFAULT '',
  `duration` varchar(20) DEFAULT '',
  `mailboxuser` varchar(80) DEFAULT '',
  `mailboxcontext` varchar(80) DEFAULT '',
  `recording` longblob,
  PRIMARY KEY (`id`),
  KEY `dir` (`dir`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_customer`
--

DROP TABLE IF EXISTS `vt_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_did`
--

DROP TABLE IF EXISTS `vt_did`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) unsigned NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `accessType` varchar(30) DEFAULT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_did` (`customerId`),
  CONSTRAINT `fk_customer_did` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_did_step`
--

DROP TABLE IF EXISTS `vt_did_step`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_did_step` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vt_dids_did` (`didId`),
  CONSTRAINT `fk_vt_dids_did` FOREIGN KEY (`didId`) REFERENCES `vt_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_peer`
--

DROP TABLE IF EXISTS `vt_peer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_peer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `hasIax` tinyint(1) NOT NULL,
  `hasSip` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `host` char(20) DEFAULT NULL,
  `secret` char(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vt_peer` (`customerId`),
  CONSTRAINT `fk_vt_peer` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vt_virtual_offshore_number`
--

DROP TABLE IF EXISTS `vt_virtual_offshore_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vt_virtual_offshore_number` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) unsigned NOT NULL,
  `didId` bigint(20) unsigned NOT NULL,
  `language` char(5) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_von` (`customerId`),
  KEY `fk_vt_did` (`didId`),
  CONSTRAINT `fk_customer_von` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`),
  CONSTRAINT `fk_vt_did` FOREIGN KEY (`didId`) REFERENCES `vt_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-08-31 12:34:46
