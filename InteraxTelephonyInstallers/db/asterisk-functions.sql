-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.34


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

USE asterisk;

--
-- Definition of function `IP_GET_EXTENSION_ID`
--

DROP FUNCTION IF EXISTS `IP_GET_EXTENSION_ID`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */ $$
CREATE FUNCTION  `IP_GET_EXTENSION_ID`(ipPbxId BIGINT(20), internalNumber BIGINT(20)) RETURNS bigint(20)
    READS SQL DATA
BEGIN

SET @extensionId = (
	SELECT id 
	FROM ip_extension e
	WHERE	
		e.ipPbxId=ipPbxId 
		AND e.internalNumber=internalNumber
		AND e.enabled=1
		AND e.deleted=0
);

IF @extensionId IS NULL THEN
	RETURN -1;
ELSE
	RETURN @extensionId;
END IF;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `IP_GET_MAILBOX_NAME`
--

DROP FUNCTION IF EXISTS `IP_GET_MAILBOX_NAME`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */ $$
CREATE FUNCTION  `IP_GET_MAILBOX_NAME`(ipPbxId BIGINT(20), internalNumber BIGINT(20)) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

SET @extensionId = (
	SELECT CONCAT('ip-vm-', e.voiceMailBoxId) 
	FROM ip_extension e
	WHERE	
		e.ipPbxId=ipPbxId 
		AND e.internalNumber=internalNumber
		AND e.deleted=0
);

IF @extensionId IS NULL THEN
	RETURN 'ip-vm-null';
ELSE
	RETURN @extensionId;
END IF;

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `IP_GET_RING_GROUP_PEERS`
--

DROP FUNCTION IF EXISTS `IP_GET_RING_GROUP_PEERS`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */ $$
CREATE FUNCTION  `IP_GET_RING_GROUP_PEERS`(groupId BIGINT(20)) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

DECLARE done TINYINT DEFAULT 0;
DECLARE peerName CHAR(30);
DECLARE peerTech CHAR(5);
DECLARE peerCursor CURSOR FOR(
   SELECT * FROM(
       SELECT ip.name, 'IAX2'          FROM ip_ring_group_has_extension rghe, rt_iax_peer ip ,ip_extension ipe
       WHERE rghe.ringGroupId=groupId AND rghe.extensionId=ip.itPeerId AND ip.itPeerType='IP_EXTENSION' AND  ipe.id = rghe.extensionId AND ipe.enabled = 1 AND ipe.adminEnabled = 1
         UNION ALL
         SELECT sp.name, 'SIP'
       FROM ip_ring_group_has_extension rghe, rt_sip_peer sp ,ip_extension ipe
       WHERE rghe.ringGroupId=groupId AND rghe.extensionId=sp.itPeerId AND sp.itPeerType='IP_EXTENSION' AND  ipe.id = rghe.extensionId AND ipe.enabled = 1 AND ipe.adminEnabled = 1
   )x

);
DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;

SET @dialString = '';

OPEN peerCursor;
FETCH peerCursor INTO peerName, peerTech;
 WHILE(done=0) DO
   IF(LENGTH(@dialString)>0) THEN
       SET @dialString = CONCAT(@dialString, '&', peerTech, '/', peerName);
   ELSE
       SET @dialString = CONCAT(@dialString, peerTech, '/', peerName);
   END IF;
       FETCH peerCursor INTO peerName, peerTech;
 END WHILE;   CLOSE peerCursor;


RETURN @dialString;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `IT_GET_OUTGOING_ROUTE`
--

DROP FUNCTION IF EXISTS `IT_GET_OUTGOING_ROUTE`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE FUNCTION `IT_GET_OUTGOING_ROUTE`(
    serviceFamily VARCHAR(50),
    serviceType VARCHAR(50),
    accessType VARCHAR(50),
    enterpriseId BIGINT(20),
    number VARCHAR(50)
) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

    RETURN IT_GET_OUTGOING_ROUTE_BY_PRIORITY(serviceFamily, serviceType, accessType, enterpriseId, number, 1);

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of function `IT_GET_OUTGOING_ROUTE_BY_PRIORITY`
--

DROP FUNCTION IF EXISTS `IT_GET_OUTGOING_ROUTE_BY_PRIORITY`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE FUNCTION  `asterisk`.`IT_GET_OUTGOING_ROUTE_BY_PRIORITY`(
    serviceFamily VARCHAR(50),
    serviceType VARCHAR(50),
    accessType VARCHAR(50),
    enterprise BIGINT(20),
    number VARCHAR(50),
    routePriority INT
) RETURNS varchar(255) CHARSET latin1
    READS SQL DATA
BEGIN

    # DATOS
    DECLARE protocol VARCHAR(50) DEFAULT '';
    DECLARE prefix VARCHAR(50) DEFAULT '';
    DECLARE peer VARCHAR(50) DEFAULT '';
    DECLARE dialString VARCHAR(100) DEFAULT NULL;

    # PESOS
    DECLARE enterprise_weight INT DEFAULT 8;
    DECLARE service_family_weight INT DEFAULT 4;
    DECLARE service_type_weight INT DEFAULT 2;
    DECLARE access_type_weight INT DEFAULT 1;
    #DECLARE ldi_weight INT DEFAULT 10000;
    #DECLARE ldn_weight INT DEFAULT 1000;

    # OPCIONALES
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
            # EL CUARTO CASO NO SE PERMITE (LDN SIN LDI)
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

END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;