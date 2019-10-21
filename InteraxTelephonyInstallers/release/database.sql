ALTER TABLE `asterisk`.`ip_extension` ADD COLUMN `ldiPrefix` VARCHAR(5)  NOT NULL AFTER `language`,
 ADD COLUMN `ldnPrefix` VARCHAR(5)  NOT NULL AFTER `ldiPrefix`,
 ADD COLUMN `localNumberMinLength` INT  NOT NULL AFTER `ldnPrefix`,
 ADD COLUMN `localNumberMaxLength` INT  NOT NULL AFTER `localNumberMinLength`;

CREATE TABLE  `asterisk`.`it_prefix` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `prefixNumber` varchar(20) NOT NULL,
  `serviceFamily` varchar(20) NOT NULL,
  `servicetype` varchar(50) NOT NULL,
  `foreignId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `it_prefix_prefixNumber` (`prefixNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE  `asterisk`.`pd_customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `contextSuffix` varchar(50) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE  `asterisk`.`pd_ani` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) unsigned NOT NULL,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `accessType` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pd_ani` (`customerId`),
  CONSTRAINT `fk_pd_ani` FOREIGN KEY (`customerId`) REFERENCES `pd_customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE  `asterisk`.`pd_prefix` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `prefixNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `language` char(5) DEFAULT NULL,
  `serviceType` varchar(50) DEFAULT NULL,
  `accessType` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#09-11-2010
ALTER TABLE `asterisk`.`ip_did` MODIFY COLUMN `type` VARCHAR(250) DEFAULT NULL;
ALTER TABLE `asterisk`.`ip_ip_pbx` MODIFY COLUMN `type` VARCHAR(250) NOT NULL;

CREATE UNIQUE INDEX pd_ani_externalNumber ON asterisk.pd_ani (externalNumber);

#04-01-2011
ALTER TABLE `asterisk`.`it_did` ADD COLUMN `enabled` INTEGER  NOT NULL DEFAULT 1 AFTER `foreignId`;
UPDATE it_did SET enabled = 0 WHERE externalNumber IN ('18007895414','18007896919','18007897001','18007897075');

#02-02-2011 
#Correcion de desaproviosionamiento de equipo
ALTER TABLE `asterisk`.`ip_extension_has_equipment` MODIFY COLUMN `activationAttempts` INTEGER UNSIGNED NOT NULL DEFAULT 0;


#17-02-2011
#Cambios de vt_peer para soportar voicetraffic login y password
ALTER TABLE `asterisk`.`vt_peer` ADD COLUMN `login` VARCHAR(20) DEFAULT NULL AFTER `host`;

#19-02-2011
#Agregadas entidades para manejo de llamadas al nextone
CREATE TABLE it_outgoing_route (
    id bigint(20) unsigned NOT NULL  auto_increment,
    enterpriseId bigint(20) unsigned NOT NULL ,
    serviceFamily varchar(50) NULL DEFAULT NULL COMMENT '',
    serviceType varchar(50) NULL DEFAULT NULL COMMENT '',
    accessType varchar(50) NULL DEFAULT NULL COMMENT '',
    ldiCode varchar(5) NOT NULL DEFAULT '' COMMENT '',
    ldnCode varchar(5) NOT NULL DEFAULT '' COMMENT '',
    PRIMARY KEY (id),
    UNIQUE uk_const (enterpriseId, serviceFamily, serviceType, accessType, ldiCode, ldnCode),
    INDEX fk_route_outgoing_enterprise (enterpriseId)
);

CREATE TABLE it_outgoing_route_step (
    id bigint(20) unsigned NOT NULL  auto_increment,
    routeId bigint(20) unsigned NOT NULL ,
    providerPeerId bigint(20) unsigned NOT NULL ,
    priority tinyint(4) NOT NULL DEFAULT '1' ,
    outgoingPrefix varchar(50) NOT NULL DEFAULT '' COMMENT '',
    PRIMARY KEY (id),
    UNIQUE uk_const_step (routeId, providerPeerId, priority, outgoingPrefix),
    INDEX fk_route_outgoing_step (routeId),
    INDEX fk_step_provider_peer (providerPeerId)
);

CREATE TABLE it_provider (
    id bigint(20) unsigned NOT NULL auto_increment,
    name varchar(50) NOT NULL DEFAULT '' COMMENT '',
    enabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    PRIMARY KEY (id)
);

CREATE TABLE it_provider_peer (
    id bigint(20) unsigned NOT NULL auto_increment,
    providerId bigint(20) unsigned NOT NULL ,
    enabled tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    deleted tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    hasIax tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    hasSip tinyint(1) NOT NULL DEFAULT 0 COMMENT '',
    name varchar(50) NOT NULL DEFAULT '' COMMENT '',
    host varchar(20) NULL DEFAULT NULL COMMENT '',
    secret varchar(20) NULL DEFAULT NULL COMMENT '',
    PRIMARY KEY (id),
    INDEX fk_it_provider_peer (providerId)
);



ALTER TABLE ivr_interactive_voice_response
    ADD configName varchar(50) NOT NULL DEFAULT '' COMMENT '' AFTER enterpriseId;


ALTER TABLE vm_email_template
    MODIFY dateFormat varchar(40) NULL DEFAULT NULL COMMENT '',
    MODIFY language varchar(2) NULL DEFAULT NULL COMMENT '';
    
    

INSERT INTO `asterisk`.`it_provider` VALUES  (1,'Alodiga',1,0),
 (2,'123.com.ve',1,0),
 (3,'Calea',1,0),
 (4,'ldtelecom',1,0),
 (5,'dash',1,0),
 (6,'global-crossing',1,0),
 (7,'net-uno',1,0),
 (8,'pro-call',1,0),
 (9,'rdimarketing',1,0);
 
 
 
 INSERT INTO `asterisk`.`it_provider_peer` VALUES  (1,1,1,0,0,1,'nextone-miami','209.130.194.100',''),
 (2,1,1,0,0,1,'nextone-ccs','200.73.192.234',''),
 (3,2,1,0,0,1,'pbx-interaxmedia','200.73.198.8',''),
 (4,2,1,0,0,1,'pbx-123comve','200.73.192.219',''),
 (5,3,1,0,0,1,'calea-1','64.19.98.203',''),
 (6,4,1,0,0,1,'ldtelecomPeer-1','208.65.92.87',''),
 (7,4,1,0,0,1,'ldtelecomPeer-2','208.65.92.32',''),
 (8,5,1,0,0,1,'dash-1','208.94.157.10','vzVuXr4002'),
 (9,5,1,0,0,1,'dash-2','208.94.159.10','mCjInC1372'),
 (10,6,1,0,0,1,'global-crossing-1','67.16.111.117',''),
 (11,7,1,0,0,1,'global-crossing-1','64.86.104.31',''),
 (12,8,1,0,0,1,'pro-call-1','174.34.162.170',''),
 (13,9,1,0,0,1,'rdimarketing-1','207.250.126.177','');
   
 
 
 
 
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('1','1','CALLING_CARD','*','IVR','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('2','1','CALLING_CARD','*','IVR_PIN_FREE','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('4','1','IP_PBX','*','FORWARD','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('3','1','IP_PBX','*','OUTGOING','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('7','1','PICK_DIALING','*','OUTGOING','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('6','1','VOICE_TRAFFIC','*','FORWARD','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('5','1','VOICE_TRAFFIC','*','OUTGOING','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('8','2','CALLING_CARD','*','IVR','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('9','2','CALLING_CARD','*','IVR_PIN_FREE','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('10','2','IP_PBX','*','FORWARD','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('11','2','IP_PBX','*','OUTGOING','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('14','2','PICK_DIALING','*','OUTGOING','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('12','2','VOICE_TRAFFIC','*','FORWARD','','');
INSERT INTO it_outgoing_route (id,enterpriseId,serviceFamily,serviceType,accessType,ldiCode,ldnCode) VALUES ('13','2','VOICE_TRAFFIC','*','OUTGOING','','');

INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('1','1','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('2','1','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('3','2','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('4','2','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('5','3','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('6','3','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('7','4','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('8','4','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('9','5','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('10','5','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('11','6','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('12','6','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('14','7','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('13','7','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('15','8','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('16','8','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('17','9','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('18','9','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('19','10','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('20','10','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('21','11','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('22','11','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('23','12','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('24','12','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('25','13','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('26','13','2','2','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('28','14','1','1','30006*');
INSERT INTO it_outgoing_route_step (id,routeId,providerPeerId,priority,outgoingPrefix) VALUES ('27','14','2','2','30006*');


INSERT INTO `asterisk`.`rt_sip_peer` VALUES  (1,'it-pv-sip-1','209.130.194.100','no','peer','Alodiga',NULL,NULL,NULL,'1<Alodiga>',NULL,'no','rt_it_outgoing_1',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-1','',NULL,1,'IT_PROVIDER','1'),
 (2,'it-pv-sip-2','200.73.192.234','no','peer','Alodiga',NULL,NULL,NULL,'2<Alodiga>',NULL,'no','rt_it_outgoing_1',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-2','',NULL,2,'IT_PROVIDER','2'),
 (3,'it-pv-sip-3','200.73.198.8','no','peer','123.com.ve',NULL,NULL,NULL,'3<123.com.ve>',NULL,'no','rt_it_outgoing_2',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-3','',NULL,3,'IT_PROVIDER','3'),
 (4,'it-pv-sip-4','200.73.192.219','no','peer','123.com.ve',NULL,NULL,NULL,'4<123.com.ve>',NULL,'no','rt_it_outgoing_2',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-4','',NULL,4,'IT_PROVIDER','4'),
 (5,'it-pv-sip-5','64.19.98.203','no','peer','Calea',NULL,NULL,NULL,'5<Calea>',NULL,'no','rt_it_outgoing_3',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-5','',NULL,5,'IT_PROVIDER','5'),
 (6,'it-pv-sip-6','208.65.92.87','no','peer','ldtelecom',NULL,NULL,NULL,'6<ldtelecom>',NULL,'no','rt_it_outgoing_4',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-6','',NULL,6,'IT_PROVIDER','6'),
 (7,'it-pv-sip-7','208.65.92.32','no','peer','ldtelecom',NULL,NULL,NULL,'7<ldtelecom>',NULL,'no','rt_it_outgoing_4',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-7','',NULL,7,'IT_PROVIDER','7'),
 (8,'it-pv-sip-8','208.94.157.10','no','peer','dash',NULL,NULL,NULL,'8<dash>',NULL,'no','rt_it_outgoing_5',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-8','',NULL,8,'IT_PROVIDER','8'),
 (9,'it-pv-sip-9','208.94.159.10','no','peer','dash',NULL,NULL,NULL,'9<dash>',NULL,'no','rt_it_outgoing_5',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-9','',NULL,9,'IT_PROVIDER','9'),
 (10,'it-pv-sip-10','67.16.111.117','no','peer','global-crossing',NULL,NULL,NULL,'10<global-crossing>',NULL,'no','rt_it_outgoing_6',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-10','',NULL,10,'IT_PROVIDER','10'),
 (11,'it-pv-sip-11','64.86.104.31','no','peer','net-uno',NULL,NULL,NULL,'11<net-uno>',NULL,'no','rt_it_outgoing_7',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-11','',NULL,11,'IT_PROVIDER','11'),
 (12,'it-pv-sip-12','174.34.162.170','no','peer','pro-call',NULL,NULL,NULL,'12<pro-call>',NULL,'no','rt_it_outgoing_8',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-12','',NULL,12,'IT_PROVIDER','12'),
 (13,'it-pv-sip-13','207.250.126.177','no','peer','rdimarketing',NULL,NULL,NULL,'13<rdimarketing>',NULL,'no','rt_it_outgoing_9',NULL,'rfc2833',NULL,NULL,'very',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'no',NULL,NULL,NULL,NULL,'',NULL,'all','g729','','',5060,NULL,0,'it-pv-sip-13','',NULL,13,'IT_PROVIDER','13');

    
#IP_EXTENSION
    
UPDATE  `asterisk`.`ip_extension` SET localNumberMinLength = 7 , localNumberMaxLength = 7 , ldiPrefix = 1 WHERE id >= 0; 
UPDATE  `asterisk`.`ip_extension` SET ldnPrefix = (SELECT  SUBSTRING(`ip_did`.`externalNumber`,2,3) FROM ip_did WHERE  ip_did.id = ip_extension.didId  ) 
WHERE   `ip_extension`.`didId` in (SELECT `ip_did`.`id` FROM `ip_did` WHERE `ip_did`.`type` = 'EXTENSION');  



#IT_ENTERPRISE

UPDATE `asterisk`.`it_enterprise` SET name = 'Alodiga-Ve' , contextSuffix = 'alodigave' , audioFolder = 'wixtel' WHERE id = '2';



#IVR
UPDATE  `asterisk`.`ivr_interactive_voice_response` SET configName = 'Ventas', name = 'SALES' , `type` = 'callcenter' WHERE id = 1 ;
UPDATE  `asterisk`.`ivr_interactive_voice_response` SET configName = 'Asistencia', name = 'ASSISTANCE' , `type` = 'callcenter' WHERE id = 2 ;
UPDATE  `asterisk`.`ivr_interactive_voice_response` SET configName = 'Asistencia CLEC', name = 'ASSISTANCE' , `type` = 'callcenter' WHERE id = 3 ;



#PROCEDURES

DELIMITER $$

DROP FUNCTION IF EXISTS `asterisk`.`IT_GET_OUTGOING_ROUTE`$$
CREATE DEFINER=`interaxtelephony`@`%` FUNCTION `IT_GET_OUTGOING_ROUTE`(
    serviceFamily VARCHAR(50),
    serviceType VARCHAR(50),
    accessType VARCHAR(50),
    enterpriseId BIGINT(20),
    number VARCHAR(50)
) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

    RETURN IT_GET_OUTGOING_ROUTE_BY_PRIORITY(serviceFamily, serviceType, accessType, enterpriseId, number, 1);

END$$

DELIMITER ;






DELIMITER $$

DROP FUNCTION IF EXISTS `asterisk`.`IT_GET_OUTGOING_ROUTE_BY_PRIORITY`$$
CREATE DEFINER=`interaxtelephony`@`%` FUNCTION `IT_GET_OUTGOING_ROUTE_BY_PRIORITY`(
    serviceFamily VARCHAR(50),
    serviceType VARCHAR(50),
    accessType VARCHAR(50),
    enterprise BIGINT(20),
    number VARCHAR(50),
    routePriority INT
) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

    
    DECLARE protocol VARCHAR(50) DEFAULT '';
    DECLARE prefix VARCHAR(50) DEFAULT '';
    DECLARE peer VARCHAR(50) DEFAULT '';
    DECLARE dialString VARCHAR(100) DEFAULT NULL;

    
    DECLARE enterprise_weight INT DEFAULT 8;
    DECLARE service_family_weight INT DEFAULT 4;
    DECLARE service_type_weight INT DEFAULT 2;
    DECLARE access_type_weight INT DEFAULT 1;
    
    

    
    DECLARE optionalE INT DEFAULT 1;
    DECLARE optionalSF INT DEFAULT 1;
    DECLARE optionalST INT DEFAULT 1;   
    DECLARE optionalAT INT DEFAULT 1;
    DECLARE optionalLDI INT DEFAULT 1;
    DECLARE optionalLDN INT DEFAULT 1;   

    SELECT
        iors.outgoingPrefix,
        ipp.id,
        IF(ipp.hasSip=1,'SIP',IF(ipp.hasIax=1,'IAX2','-'))
       
    INTO
        prefix,
        peer,
        protocol       
           
    FROM                
        asterisk.it_outgoing_route_step iors,
        asterisk.it_outgoing_route ior,
        asterisk.it_provider_peer ipp,
	asterisk.it_provider ip
       
    WHERE 
        iors.routeId=ior.id
        AND (ior.enterpriseId=enterprise OR (ior.enterpriseId=0 AND optionalE=1))
        AND (ior.serviceFamily=serviceFamily OR (ior.serviceFamily='*' AND optionalSF=1))
        AND (ior.serviceType=serviceType OR (ior.serviceType='*' AND optionalST=1))
        AND (ior.accessType=accessType OR (ior.accessType='*' AND optionalAT=1))
        AND (
            (ior.ldiCode<>'' AND ior.ldnCode<>'' AND number LIKE CONCAT(ior.ldiCode,ior.ldnCode, '%'))
            OR
            (ior.ldiCode<>'' AND ior.ldnCode='' AND number LIKE CONCAT(ior.ldiCode, '%') AND optionalLDN=1)
            OR
            (ior.ldiCode='' AND ior.ldnCode='' AND optionalLDI=1 AND optionalLDN=1)
            
        )
        AND iors.providerPeerId=ipp.id
	AND ipp.enabled=1
	AND ipp.deleted=0
	AND ipp.providerId=ip.id
	AND ip.enabled=1
	AND ip.deleted=0
	AND iors.priority=routePriority
   
    ORDER BY
        IF(ior.enterpriseId=enterprise,enterprise_weight,0) +
        IF(ior.serviceFamily=serviceFamily,service_family_weight,0) +
        IF(ior.serviceType=serviceType,service_type_weight,0) +
        IF(ior.accessType=accessType,access_type_weight,0)
        DESC,
        LENGTH(ior.ldiCode) DESC,
        LENGTH(ior.ldnCode) DESC,
	iors.priority ASC
   
    LIMIT 1;
   
       
    IF (peer <> '') THEN
        SET dialString = CONCAT(protocol,'/', prefix, number,'@it-pv-',LOWER(protocol),'-',peer);
    END IF;

    RETURN dialString;

END$$

DELIMITER ;


#09-12-2011
INSERT INTO `asterisk`.`ip_equipment_manufacturer` VALUES
(7,'Chinos','CHINOS'),
(8,'Alodiga','ALODIGA');


#14-12-2011
#Agregadas entidades para manejo de llamadas al nextone


CREATE TABLE `asterisk`.`ip_extension_has_forward` 
(
  `extensionId` BIGINT(20) unsigned NOT NULL,
  `forwardType` VARCHAR(20) NOT NULL, 
  `number` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`extensionId`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



ALTER TABLE `asterisk`.`ip_extension` ADD COLUMN forwardEnable tinyint(1) NOT NULL DEFAULT 0;


INSERT INTO `asterisk`.`ip_equipment_model` VALUES
(11,7,'','Generic Dialer'),
(12,8,'','Softphone');

### 17/05/2012 Creacion del producto DID

CREATE TABLE IF NOT EXISTS `asterisk`.`did_service` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `foreingId` bigint(20) unsigned,
  `enterpriseId` bigint(20) unsigned NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `deleted` tinyint(1) NOT NULL,
  `terminationIp` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
;
  
CREATE TABLE  `asterisk`.`did_did` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `externalNumber` varchar(20) NOT NULL,
  `monitored` tinyint(1) NOT NULL,
  `accessType` varchar(30) DEFAULT NULL,
  `didServiceId` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_did_service_id` FOREIGN KEY (`didServiceId`) REFERENCES `did_service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

### 17/05/2012 FIN Creacion del producto DID

### 14/01/2013 Nestor . cambios en libre marcación 

ALTER TABLE asterisk.pd_ani ADD COLUMN `ldn` tinyint(1) NOT NULL,
  ADD COLUMN `ldi` tinyint(1) NOT NULL,
  ADD COLUMN `ldnPrefix` varchar(5) NOT NULL,
  ADD COLUMN `ldiPrefix` varchar(2) NOT NULL;


