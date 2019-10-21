SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 ;

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



--
-- Base de datos: `asterisk`
--

USE asterisk;


--
-- Estructura de tabla para la tabla `ivr_interactive_voice_response`
--

CREATE TABLE IF NOT EXISTS `ivr_interactive_voice_response` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_it_enterprise` (`enterpriseId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ivr_did`
--

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `ivr_did` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) default NULL,
  `serviceType` varchar(50) default NULL,
  `accessType` varchar(50) default NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ivr_ivr` (`foreignId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;



--
-- Estructura de tabla para la tabla `ivr_did_step`
--

CREATE TABLE IF NOT EXISTS `ivr_did_step` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `didId` bigint(20) unsigned NOT NULL,
  `action` varchar(50) NOT NULL,
  `data` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_ivr_dids_did` (`didId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Volcar la base de datos para la tabla `ivr_did_step`
--



--
-- Filtros para la tabla `ivr_did`
--


ALTER TABLE `ivr_did`
  ADD CONSTRAINT `fk_ivr_ivr` FOREIGN KEY (`foreignId`) REFERENCES `ivr_interactive_voice_response` (`id`);

--
-- Filtros para la tabla `ivr_did_step`
--
ALTER TABLE `ivr_did_step`
  ADD CONSTRAINT `fk_ivr_dids_did` FOREIGN KEY (`didId`) REFERENCES `ivr_did` (`id`);

--
-- Filtros para la tabla `ivr_interactive_voice_response`
--
ALTER TABLE `ivr_interactive_voice_response`
  ADD CONSTRAINT `fk_it_enterprise` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`);



CREATE TABLE IF NOT EXISTS `cc_application` (
  `id` bigint(20) unsigned NOT NULL auto_increment,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `configName` varchar(50) NOT NULL,
  `country` char(5) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `fk_cc_application_it_enterprise` (`enterpriseId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcar la base de datos para la tabla `cc_application`
--

INSERT INTO `cc_application` (`id`, `enterpriseId`, `configName`, `country`) VALUES
(1, 1, '1_USA_IVR', 'USA'),
(2, 1, '1_VEN_IVR', 'VEN');

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `cc_application`
--
ALTER TABLE `cc_application`
  ADD CONSTRAINT `fk_cc_application_it_enterprise` FOREIGN KEY (`enterpriseId`) REFERENCES `it_enterprise` (`id`);



ALTER TABLE `ip_did` ADD COLUMN `serviceType` VARCHAR(50) default NULL  AFTER `language`;
ALTER TABLE `ip_did` ADD COLUMN `accessType` VARCHAR(30) default NULL;
ALTER TABLE `ip_did` ADD COLUMN `foreignId` bigint(20) unsigned NOT NULL AFTER `accessType`;





ALTER TABLE `vt_did` ADD COLUMN `serviceType` VARCHAR(50) default NULL  AFTER `language`;
ALTER TABLE `vt_did` ADD COLUMN `accessType` VARCHAR(30) default NULL;
ALTER TABLE `vt_did` ADD COLUMN `foreignId` bigint(20) unsigned NOT NULL AFTER `accessType`;



ALTER TABLE `asterisk`.`cc_did` DROP FOREIGN KEY `fk_enterprise_did`;




ALTER TABLE `cc_did` ADD COLUMN `monitored` tinyint(1) NOT NULL  default 0  AFTER  `externalNumber`;
ALTER TABLE `cc_did` ADD COLUMN `serviceType` VARCHAR(50) default NULL  AFTER  `country`;
ALTER TABLE `cc_did` ADD COLUMN `foreignId` bigint(20) unsigned NOT NULL AFTER `accessType`;

ALTER TABLE `cc_did` DROP COLUMN `enterpriseId` ;
ALTER TABLE `cc_did` DROP COLUMN `configName` ;
ALTER TABLE `cc_did` DROP COLUMN `country` ;

ALTER TABLE `asterisk`.`cc_did` ADD CONSTRAINT `fk_cc_did_cc_application` FOREIGN KEY `fk_cc_did_cc_application` (`foreignId`)
    REFERENCES `cc_application` (`id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

