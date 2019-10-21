--
-- Host: localhost    Database: asterisk
-- ------------------------------------------------------
-- Server version	5.0.38-Ubuntu_0ubuntu1.4-log

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
CREATE TABLE `admin_action` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `objectType` varchar(32) NOT NULL default '',
  `objectIds` varchar(80) NOT NULL default '',
  `userLogin` varchar(255) NOT NULL default '',
  `actionType` varchar(32) NOT NULL default '',
  `actionInfo` varchar(255) default NULL,
  `actionTime` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `cdr_call_detail_record`
--

DROP TABLE IF EXISTS `cdr_call_detail_record`;
CREATE TABLE `cdr_call_detail_record` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `uniqueid` varchar(32) NOT NULL default '',
  `startdate` datetime NOT NULL default '0000-00-00 00:00:00',
  `clid` varchar(80) NOT NULL default '',
  `src` varchar(80) NOT NULL default '',
  `dst` varchar(80) NOT NULL default '',
  `dcontext` varchar(80) NOT NULL default '',
  `channel` varchar(80) NOT NULL default '',
  `dstchannel` varchar(80) NOT NULL default '',
  `lastapp` varchar(80) NOT NULL default '',
  `lastdata` varchar(80) NOT NULL default '',
  `duration` int(11) NOT NULL default '0',
  `billsec` int(11) NOT NULL default '0',
  `disposition` varchar(45) NOT NULL default '',
  `amaflags` int(11) NOT NULL default '0',
  `accountcode` varchar(20) NOT NULL default '',
  `userfield` varchar(255) NOT NULL default '',
  `asteriskId` varchar(255) NOT NULL,
  `answerdate` datetime NOT NULL default '0000-00-00 00:00:00',
  `stopdate` datetime NOT NULL default '0000-00-00 00:00:00',
  `hangupcause` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uniqueid` (`uniqueid`),
  KEY `uniqueId_index` (`uniqueid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


--
-- Table structure for table `dw_cdr_gx_data`
--

DROP TABLE IF EXISTS `dw_cdr_gx_data`;
CREATE TABLE `dw_cdr_gx_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `serviceFamily` varchar(30) NOT NULL,
  `enterprise` varchar(2) NOT NULL,
  `pointId` bigint(20) unsigned NOT NULL,
  `calls` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

--
-- Table structure for table `dw_cdr_gx_point`
--

DROP TABLE IF EXISTS `dw_cdr_gx_point`;
CREATE TABLE `dw_cdr_gx_point` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `pointDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_did`
--

DROP TABLE IF EXISTS `ip_did`;
CREATE TABLE `ip_did` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) default NULL,
  `type` VARCHAR(30) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ippbx_did` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_did` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_did_step`
--

DROP TABLE IF EXISTS `ip_did_step`;
CREATE TABLE `ip_did_step` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_dids_did` (`didId`),
  CONSTRAINT `fk_dids_did` FOREIGN KEY (`didId`) REFERENCES `ip_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `it_did`
--

DROP TABLE IF EXISTS `it_did`;
CREATE TABLE  `it_did` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `externalNumber` varchar(20) NOT NULL,
  `serviceFamily` varchar(20) NOT NULL,
  `servicetype` varchar(50) NOT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `it_did_externalNumber` (`externalNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `it_peer`
--

DROP TABLE IF EXISTS `it_peer`;
CREATE TABLE  `asterisk`.`it_peer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `it_peer`
--

DROP TABLE IF EXISTS `it_voice_mail_box`;
CREATE TABLE  `asterisk`.`it_voice_mail_box` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `type` varchar(30) NOT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `it_enterprise`
--

DROP TABLE IF EXISTS `it_enterprise`;
CREATE TABLE `it_enterprise` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `audioFolder` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_equipment`
--

DROP TABLE IF EXISTS `ip_equipment`;
CREATE TABLE `ip_equipment` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `equipmentModelId` bigint(20) unsigned NOT NULL,
  `serialNumber` varchar(50) NOT NULL,
  `macAddress` varchar(12) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_equipment_activation_attempt`
--

DROP TABLE IF EXISTS `ip_equipment_activation_attempt`;
CREATE TABLE `ip_equipment_activation_attempt` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `equipmentId` bigint(20) unsigned NOT NULL,
  `attemptDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `activationCode` varchar(10) NOT NULL,
  `success` tinyint(1) NOT NULL,
  `extensionId` bigint(20) unsigned NOT NULL,
  `ipAddress` varchar(15) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_equipment_manufacturer`
--

DROP TABLE IF EXISTS `ip_equipment_manufacturer`;
CREATE TABLE `ip_equipment_manufacturer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(255) NOT NULL,
  `internalName` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_equipment_model`
--

DROP TABLE IF EXISTS `ip_equipment_model`;
CREATE TABLE `ip_equipment_model` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `equipmentManufacturerId` bigint(20) unsigned NOT NULL,
  `productNumber` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_extension`
--

DROP TABLE IF EXISTS `ip_extension`;
CREATE TABLE `ip_extension` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
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
  `language` char(5) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ippbx_extension` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_extension` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_extension_has_equipment`
--

DROP TABLE IF EXISTS `ip_extension_has_equipment`;
CREATE TABLE `ip_extension_has_equipment` (
  `extensionId` bigint(20) unsigned NOT NULL,
  `equipmentId` bigint(20) unsigned NOT NULL,
  `applyDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `enabled` tinyint(1) NOT NULL,
  `disableDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `activated` tinyint(1) NOT NULL,
  `activationDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `activationCode` varchar(10) NOT NULL,
  `activationAttempts` int(10) unsigned NOT NULL,
  `encryptionKey` text,
  `fileVersion` text,
  PRIMARY KEY  (`extensionId`,`equipmentId`,`applyDate`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_ip_pbx`
--

DROP TABLE IF EXISTS `ip_ip_pbx`;
CREATE TABLE `ip_ip_pbx` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_ring_group`
--

DROP TABLE IF EXISTS `ip_ring_group`;
CREATE TABLE `ip_ring_group` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_ring_group_has_extension`
--

DROP TABLE IF EXISTS `ip_ring_group_has_extension`;
CREATE TABLE `ip_ring_group_has_extension` (
  `ringGroupId` bigint(20) unsigned NOT NULL,
  `extensionId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`ringGroupId`,`extensionId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_virtual_offshore_number`
--

DROP TABLE IF EXISTS `ip_virtual_offshore_number`;
CREATE TABLE `ip_virtual_offshore_number` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `didId` bigint(20) unsigned NOT NULL,
  `language` char(5) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ippbx_von` (`ipPbxId`),
  CONSTRAINT `fk_ippbx_von` FOREIGN KEY (`ipPbxId`) REFERENCES `ip_ip_pbx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `ip_voice_mail_box`
--

DROP TABLE IF EXISTS `ip_voice_mail_box`;
CREATE TABLE `ip_voice_mail_box` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `ipPbxId` bigint(20) unsigned NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(5) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `provisioning_file`
--
DROP TABLE IF EXISTS `provisioning_file`;
CREATE TABLE  `provisioning_file` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `filename` varchar(50) NOT NULL,
  `contents` blob NOT NULL,
  `version` varchar(20) NOT NULL,
  `creationdate` datetime NOT NULL,
  `modificationdate` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `provisioning_file_request`
--
DROP TABLE IF EXISTS `provisioning_file_request`;
CREATE TABLE  `provisioning_file_request` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `filename` varchar(50) NOT NULL,
  `requestdate` datetime NOT NULL,
  `requestuseragent` varchar(20) NOT NULL,
  `requestaddress` varchar(20) NOT NULL,
  `response` varchar(20) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Table structure for table `rt_extension`
--

DROP TABLE IF EXISTS `rt_extension`;
CREATE TABLE `rt_extension` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `context` varchar(20) NOT NULL default '',
  `exten` varchar(20) NOT NULL default '',
  `priority` tinyint(4) NOT NULL default '0',
  `app` varchar(20) NOT NULL default '',
  `appdata` varchar(128) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `cntx_ext_prior` USING BTREE (`context`,`exten`,`priority`),
  KEY `cntx_ext_prior_index` (`context`,`exten`,`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `rt_iax_peer`
--

DROP TABLE IF EXISTS `rt_iax_peer`;
CREATE TABLE `rt_iax_peer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `username` varchar(30) default NULL,
  `type` varchar(6) NOT NULL,
  `secret` varchar(50) default NULL,
  `md5secret` varchar(32) default NULL,
  `dbsecret` varchar(100) default NULL,
  `notransfer` varchar(10) default NULL,
  `inkeys` varchar(100) default NULL,
  `outkey` varchar(100) default NULL,
  `auth` varchar(100) default NULL,
  `accountcode` varchar(100) default NULL,
  `amaflags` varchar(100) default NULL,
  `callerid` varchar(100) default NULL,
  `context` varchar(100) default NULL,
  `defaultip` varchar(15) default NULL,
  `host` varchar(31) NOT NULL default 'dynamic',
  `language` char(5) default NULL,
  `mailbox` varchar(50) default NULL,
  `deny` varchar(95) default NULL,
  `permit` varchar(95) default NULL,
  `qualify` varchar(4) default NULL,
  `disallow` varchar(100) default NULL,
  `allow` varchar(100) default NULL,
  `ipaddr` varchar(15) default NULL,
  `port` int(11) default '0',
  `regseconds` int(11) default '0',
  `itPeerId` bigint(20) unsigned NOT NULL,
  `itPeerType` varchar(15) NOT NULL default '',
  `userfield` varchar(256) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `iax_peer_name` (`name`),
  UNIQUE KEY `iax_peer_username_idx` (`username`),
  KEY `name_index` (`name`),
  KEY `username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `rt_sip_peer`
--

DROP TABLE IF EXISTS `rt_sip_peer`;
CREATE TABLE `rt_sip_peer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(80) NOT NULL default '',
  `host` varchar(31) NOT NULL default '',
  `nat` varchar(5) NOT NULL default 'no',
  `type` enum('user','peer','friend') NOT NULL default 'friend',
  `accountcode` varchar(20) default NULL,
  `amaflags` varchar(13) default NULL,
  `call-limit` smallint(5) unsigned default NULL,
  `callgroup` varchar(10) default NULL,
  `callerid` varchar(80) default NULL,
  `cancallforward` char(3) default 'yes',
  `canreinvite` char(3) default 'yes',
  `context` varchar(80) default NULL,
  `defaultip` varchar(15) default NULL,
  `dtmfmode` varchar(7) default NULL,
  `fromuser` varchar(80) default NULL,
  `fromdomain` varchar(80) default NULL,
  `insecure` varchar(4) default NULL,
  `language` char(5) default NULL,
  `mailbox` varchar(50) default NULL,
  `md5secret` varchar(80) default NULL,
  `deny` varchar(95) default NULL,
  `permit` varchar(95) default NULL,
  `mask` varchar(95) default NULL,
  `musiconhold` varchar(100) default NULL,
  `pickupgroup` varchar(10) default NULL,
  `qualify` char(3) default NULL,
  `regexten` varchar(80) default NULL,
  `restrictcid` char(3) default NULL,
  `rtptimeout` char(3) default NULL,
  `rtpholdtimeout` char(3) default NULL,
  `secret` varchar(80) default NULL,
  `setvar` varchar(100) default NULL,
  `disallow` varchar(100) default 'all',
  `allow` varchar(100) default 'g729;ilbc;gsm;ulaw;alaw',
  `fullcontact` varchar(80) NOT NULL default '',
  `ipaddr` varchar(15) NOT NULL default '',
  `port` smallint(5) unsigned NOT NULL default '0',
  `regserver` varchar(100) default NULL,
  `regseconds` int(11) NOT NULL default '0',
  `username` varchar(80) NOT NULL default '',
  `defaultuser` varchar(80) NOT NULL default '',
  `subscribecontext` varchar(80) default NULL,
  `itPeerId` bigint(20) unsigned NOT NULL,
  `itPeerType` varchar(15) NOT NULL default '',
  `userfield` varchar(256) NOT NULL default '',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `name_index` (`name`),
  UNIQUE KEY `sip_peer_username_idx` (`username`),
  KEY `username_index` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `rt_voice_mail_user`
--

DROP TABLE IF EXISTS `rt_voice_mail_user`;
CREATE TABLE `rt_voice_mail_user` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `uniqueid` int(11) NOT NULL,
  `customer_id` varchar(11) NOT NULL default '0',
  `context` varchar(50) NOT NULL default '',
  `mailbox` varchar(11) NOT NULL default '0',
  `password` varchar(5) NOT NULL default '0',
  `fullname` varchar(150) NOT NULL default '',
  `email` varchar(50) NOT NULL default '',
  `pager` varchar(50) NOT NULL default '',
  `tz` varchar(10) NOT NULL default 'central',
  `attach` varchar(4) NOT NULL default 'yes',
  `saycid` varchar(4) NOT NULL default 'yes',
  `dialout` varchar(10) NOT NULL default '',
  `callback` varchar(10) NOT NULL default '',
  `review` varchar(4) NOT NULL default 'no',
  `operator` varchar(4) NOT NULL default 'no',
  `envelope` varchar(4) NOT NULL default 'no',
  `sayduration` varchar(4) NOT NULL default 'no',
  `saydurationm` tinyint(4) NOT NULL default '1',
  `sendvoicemail` varchar(4) NOT NULL default 'no',
  `delete` varchar(4) NOT NULL default 'no',
  `nextaftercmd` varchar(4) NOT NULL default 'yes',
  `forcename` varchar(4) NOT NULL default 'no',
  `forcegreetings` varchar(4) NOT NULL default 'no',
  `hidefromdir` varchar(4) NOT NULL default 'yes',
  `stamp` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `itVoiceMailBoxId` bigint(20) unsigned NOT NULL,
  `emailTemplate` varchar(30) NOT NULL,
  `mailboxName` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `rt_vmu_uniqueId` (`uniqueid`),
  KEY `mailbox_context` (`mailbox`,`context`),
  KEY `rt_vmu_uniqueId_index` USING BTREE (`uniqueid`),
  KEY `email_template` (`emailTemplate`),
  KEY `rt_voice_mail_user_ibfk_1` (`emailTemplate`),
  CONSTRAINT `rt_voice_mail_user_ibfk_1` FOREIGN KEY (`emailTemplate`) REFERENCES `vm_email_template` (`uniqueid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vm_email_template`
--

DROP TABLE IF EXISTS `vm_email_template`;
CREATE TABLE `vm_email_template` (
  `id` int(11) NOT NULL auto_increment,
  `uniqueid` varchar(30) NOT NULL,
  `format` varchar(30) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `body` text NOT NULL,
  `senderName` varchar(100) NOT NULL,
  `senderAddress` varchar(100) NOT NULL,
  `dateFormat` varchar(40) DEFAULT NULL,
  `language` varchar(2) DEFAULT NULL,
  PRIMARY KEY  USING BTREE (`id`),
  UNIQUE KEY `unique_id` (`uniqueid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vm_voice_message`
--

DROP TABLE IF EXISTS `vm_voice_message`;
CREATE TABLE `vm_voice_message` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `msgnum` int(11) NOT NULL default '0',
  `dir` varchar(100) default '',
  `context` varchar(80) default '',
  `macrocontext` varchar(80) default '',
  `callerid` varchar(40) default '',
  `origtime` varchar(40) default '',
  `duration` varchar(20) default '',
  `mailboxuser` varchar(80) default '',
  `mailboxcontext` varchar(80) default '',
  `recording` longblob,
  PRIMARY KEY  (`id`),
  KEY `dir` (`dir`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vt_customer`
--

DROP TABLE IF EXISTS `vt_customer`;
CREATE TABLE `vt_customer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vt_peer`
--

DROP TABLE IF EXISTS `vt_peer`;
CREATE TABLE `vt_peer` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `customerId` bigint(20) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `hasIax` tinyint(1) NOT NULL,
  `hasSip` tinyint(1) NOT NULL,
  `language` char(5) default NULL,
  `host` char(20) default NULL,
  `secret` char(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_vt_peer` (`customerId`),
  CONSTRAINT `fk_vt_peer` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vt_did`
--
DROP TABLE IF EXISTS `vt_did`;
CREATE TABLE  `asterisk`.`vt_did` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `customerId` bigint(20) unsigned NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) default NULL,
  `type` VARCHAR(30) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_customer_did` (`customerId`),
  CONSTRAINT `fk_customer_did` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `vt_did_step`
--
DROP TABLE IF EXISTS `vt_did_step`;
CREATE TABLE  `asterisk`.`vt_did_step` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_vt_dids_did` (`didId`),
  CONSTRAINT `fk_vt_dids_did` FOREIGN KEY (`didId`) REFERENCES `vt_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Table structure for table `vt_virtual_offshore_number`
--
DROP TABLE IF EXISTS `vt_virtual_offshore_number`;
CREATE TABLE  `asterisk`.`vt_virtual_offshore_number` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `customerId` bigint(20) unsigned NOT NULL,
  `didId` bigint(20) unsigned NOT NULL,
  `language` char(5) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_customer_von` (`customerId`),
  KEY `fk_vt_did` (`didId`),
  CONSTRAINT `fk_vt_did` FOREIGN KEY (`didId`) REFERENCES `vt_did` (`id`),
  CONSTRAINT `fk_customer_von` FOREIGN KEY (`customerId`) REFERENCES `vt_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `cc_did`
--

DROP TABLE IF EXISTS `cc_did`;
CREATE TABLE  `asterisk`.`cc_did` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `configName` varchar(50) NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `country` char(5) default NULL,
  `type` varchar(30) default NULL,
  `accessType` varchar(50) default NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_enterprise_did` (`enterpriseId`),
  CONSTRAINT `fk_enterprise_did` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `cc_did_step`
--

DROP TABLE IF EXISTS `cc_did_step`;
CREATE TABLE  `asterisk`.`cc_did_step` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_cc_dids_did` (`didId`),
  CONSTRAINT `fk_cc_dids_did` FOREIGN KEY (`didId`) REFERENCES `cc_did` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-12-29 15:46:17
