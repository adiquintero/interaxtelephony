SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP DATABASE IF EXISTS SISAC_CALL_CENTER;
CREATE DATABASE IF NOT EXISTS SISAC_CALL_CENTER;
USE `SISAC_CALL_CENTER`;

-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`lead`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`lead`;
CREATE TABLE  `SISAC_CALL_CENTER`.`lead` (
  `id` int(11) NOT NULL auto_increment,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY  (`id`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`ani_has_lead`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`ani_has_lead`;
CREATE TABLE  `SISAC_CALL_CENTER`.`ani_has_lead` (
  `id` int(11) NOT NULL auto_increment,
  `ani` varchar(50) NOT NULL,
  `leadId` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `lead_fk_constraint` (`leadId`),
  CONSTRAINT `lead_fk_constraint` FOREIGN KEY (`leadId`) REFERENCES `lead` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`dnid`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`dnid` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`dnid` (
          `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
          `dnid` VARCHAR(30) NULL DEFAULT NULL ,
          PRIMARY KEY (`id`) 
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`call`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`call` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`call` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
  `ani` VARCHAR(30) NULL DEFAULT NULL ,
  `dnidId` BIGINT(20) NULL DEFAULT NULL ,
  `status` VARCHAR(30) NULL DEFAULT NULL ,
  `beginingDate` DATETIME NULL DEFAULT NULL ,
  `endingDate` DATETIME NULL DEFAULT NULL ,
  `language` VARCHAR(30) NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `fk_call_dnidId` (`dnidId` ASC) ,
  CONSTRAINT `fk_call_dnidId`
    FOREIGN KEY (`dnidId` )
    REFERENCES `SISAC_CALL_CENTER`.`dnid` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`call_center`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`call_center` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`call_center` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `beginningDate` date NOT NULL,
  `endingDate` date default NULL,
  PRIMARY KEY  (`id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`call_center_has_service`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`call_center_has_service` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`call_center_has_service` (
      `id` INT(11) NOT NULL AUTO_INCREMENT ,
      `callCenterId` INT(11) NOT NULL ,
      `serviceId` INT(11) NOT NULL ,
      `beginningDate` DATE NOT NULL ,
      `endingDate` DATE NULL DEFAULT NULL ,
      PRIMARY KEY (`id`) ,
      INDEX `callCenterId` (`callCenterId` ASC) ,
      CONSTRAINT `FK_call_center_has_service_1`
        FOREIGN KEY (`callCenterId` )
        REFERENCES `SISAC_CALL_CENTER`.`call_center` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`script`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`script` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`script` (
                          `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
                          `title` VARCHAR(30) NULL DEFAULT NULL ,
                          `initialDialogId` BIGINT(20) NULL DEFAULT NULL ,
			  `incorrectPhrase` VARCHAR(30) NULL DEFAULT NULL ,
			  `timeoutPhrase` VARCHAR(30) NULL DEFAULT NULL ,
                          PRIMARY KEY (`id`) 
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`dialog`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`dialog` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`dialog` (
        `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
        `scriptId` BIGINT(20) NULL DEFAULT NULL ,
        `phase` VARCHAR(30) NULL DEFAULT NULL ,
        `title` VARCHAR(30) NULL DEFAULT NULL ,
        `type` VARCHAR(30) NULL DEFAULT NULL ,
        `action` VARCHAR(30) NULL DEFAULT NULL ,
        `actionData` VARCHAR(30) NULL DEFAULT NULL ,
        `speech` VARCHAR(254) NULL DEFAULT NULL ,
        PRIMARY KEY (`id`) ,
        INDEX `fk_dialog_script1` (`scriptId` ASC) ,
        CONSTRAINT `fk_dialog_script1`
          FOREIGN KEY (`scriptId` )
          REFERENCES `SISAC_CALL_CENTER`.`script` (`id` )
      
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`script_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`script_schedule` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`script_schedule` (
                              `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
                              `beginingHour` DATETIME NOT NULL ,
                              `endingHour` DATETIME NOT NULL ,
                              `dayOfWeek` INT(11) NOT NULL ,
                              `priority` INT(11) NOT NULL ,
                              PRIMARY KEY (`id`) 
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`dnid_has_script_in_schedule`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`dnid_has_script_in_schedule` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`dnid_has_script_in_schedule` (
			`id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
			`dnidId` BIGINT(20) NOT NULL ,
            `scriptId` BIGINT(20) NOT NULL ,
            `scriptScheduleId` BIGINT(20) NOT NULL ,
			 PRIMARY KEY (`id`) ,            
            INDEX `fk_dhss_dnidId` (`dnidId` ASC) ,
            INDEX `fk_dhss_scriptId` (`scriptId` ASC) ,
            INDEX `fk_dhss_scriptScheduleId` (`scriptScheduleId` ASC) ,
			UNIQUE KEY `uniqueid` (dnidId,scriptId,scriptScheduleId),
            CONSTRAINT `fk_dhss_dnidId`
              FOREIGN KEY (`dnidId` )
              REFERENCES `SISAC_CALL_CENTER`.`dnid` (`id` ),
            CONSTRAINT `fk_dhss_scriptId`
              FOREIGN KEY (`scriptId` )
              REFERENCES `SISAC_CALL_CENTER`.`script` (`id` ),
            CONSTRAINT `fk_dhss_scriptScheduleId`
              FOREIGN KEY (`scriptScheduleId` )
              REFERENCES `SISAC_CALL_CENTER`.`script_schedule` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`lead`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`lead` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`lead` (
              `id` INT(11) NOT NULL AUTO_INCREMENT ,
              `email` VARCHAR(50) NOT NULL ,
              `name` VARCHAR(50) NOT NULL ,
              `lastName` VARCHAR(50) NOT NULL ,
              `phone` VARCHAR(50) NOT NULL ,
              PRIMARY KEY (`id`, `email`) 
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`operator_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`operator_type`;
CREATE TABLE  `SISAC_CALL_CENTER`.`operator_type` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) NOT NULL,
  `access` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`operator`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`operator`;
CREATE TABLE  `SISAC_CALL_CENTER`.`operator` (
  `id` int(11) NOT NULL auto_increment,
  `callCenterId` int(11) default NULL,
  `operatorTypeId` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `lastName` varchar(50) NOT NULL,
  `beginningDate` date NOT NULL,
  `endingDate` date default NULL,
  `enabled` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `oplogin` USING BTREE (`login`),
  KEY `callCenterId` (`callCenterId`),
  KEY `operatorTypeId` (`operatorTypeId`),
  CONSTRAINT `FK_operator_1` FOREIGN KEY (`callCenterId`) REFERENCES `call_center` (`id`),
  CONSTRAINT `FK_operator_2` FOREIGN KEY (`operatorTypeId`) REFERENCES `operator_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`option`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`option` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`option` (
                      `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
                      `dtmf` CHAR(1) NULL DEFAULT NULL ,
                      `minLimit` INT(11) NULL DEFAULT '0' ,
                      `maxLimit` INT(11) NULL DEFAULT '0' ,
                      `action` VARCHAR(30) NULL DEFAULT NULL ,
                      `actionData` VARCHAR(30) NULL DEFAULT NULL ,
                      `dialogId` BIGINT(20) NULL DEFAULT NULL ,
                      `title` VARCHAR(30) NULL DEFAULT NULL ,
                      PRIMARY KEY (`id`) ,
                      INDEX `fk_option_dialog1` (`id` ASC) ,
                      CONSTRAINT `fk_option_dialog2`
                        FOREIGN KEY (`dialogId` )
                        REFERENCES `SISAC_CALL_CENTER`.`dialog` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`script_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`script_answer` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`script_answer` (
                            `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
                            `callId` BIGINT(20) NULL DEFAULT NULL ,
                            PRIMARY KEY (`id`) ,
                            INDEX `fk_script_answer_call1` (`callId` ASC) ,
                            CONSTRAINT `fk_script_answer_call1`
                              FOREIGN KEY (`callId` )
                              REFERENCES `SISAC_CALL_CENTER`.`call` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`option_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`option_answer` ;

CREATE  TABLE IF NOT EXISTS `SISAC_CALL_CENTER`.`option_answer` (
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT ,
                        `value` VARCHAR(30) NULL DEFAULT NULL ,
                        `dialogId` BIGINT(20) NULL DEFAULT NULL ,
                        `optionId` BIGINT(20) NULL DEFAULT NULL ,
                        `scriptAnswerId` BIGINT(20) NULL DEFAULT NULL ,
                        PRIMARY KEY (`id`) ,
                        INDEX `fk_option_answer_script_answer1` (`scriptAnswerId` ASC) ,
                        INDEX `fk_option_answer_option1` (`optionId` ASC) ,
                        INDEX `fk_dialog_dialog1` (`dialogId` ASC) ,
                        CONSTRAINT `fk_option_answer_script_answer1`
                          FOREIGN KEY (`scriptAnswerId` )
                          REFERENCES `SISAC_CALL_CENTER`.`script_answer` (`id` ),
                        CONSTRAINT `fk_option_answer_option1`
                          FOREIGN KEY (`optionId` )
                          REFERENCES `SISAC_CALL_CENTER`.`option` (`id` ),
                        CONSTRAINT `fk_dialog_dialog1`
                          FOREIGN KEY (`dialogId` )
                          REFERENCES `SISAC_CALL_CENTER`.`dialog` (`id` )
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- -----------------------------------------------------
-- Table `SISAC_CALL_CENTER`.`security_question_log`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `SISAC_CALL_CENTER`.`security_question_log`;
CREATE TABLE  `SISAC_CALL_CENTER`.`security_question_log` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `callId` int(10) unsigned default NULL,
  `customerId` int(10) unsigned NOT NULL,
  `operatorId` int(11) NOT NULL,
  `question` varchar(100) NOT NULL,
  `userAnswer` varchar(100) NOT NULL,
  `correctAnswer` varchar(100) NOT NULL,
  `date` datetime NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

