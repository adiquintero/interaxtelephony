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
-- Definition of procedure `CC_REPORT_TRAFFIC`
--

DROP PROCEDURE IF EXISTS `CC_REPORT_TRAFFIC`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`interaxtelephony`@`%` PROCEDURE  `CC_REPORT_TRAFFIC`()
BEGIN
    SELECT
        *
    FROM
        `cdr_call_detail_record` cdr
    LIMIT 10;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `DW_CREATE_POINTS`
--

DROP PROCEDURE IF EXISTS `DW_CREATE_POINTS`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`interaxtelephony`@`%` PROCEDURE  `DW_CREATE_POINTS`(baseDate DATE)
BEGIN

SET @i=0;
SET @startDate=baseDate;
  
WHILE @i<(24*60) DO
   SET @i=@i+1;
   INSERT INTO asterisk.`dw_cdr_gx_point`(id, pointDate) VALUES(NULL, ADDDATE(@startDate, INTERVAL @i MINUTE));
END WHILE;


END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `GET_EMAIL_TEMPLATE`
--

DROP PROCEDURE IF EXISTS `GET_EMAIL_TEMPLATE`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE PROCEDURE  `GET_EMAIL_TEMPLATE`(IN template VARCHAR(30))
BEGIN

	SELECT CONCAT(e.format,'=*=', e.subject,'=*=', e.senderName,'=*=', e.senderAddress, '=*=', e.dateFormat  ,'=*=', e.language ,'=*=',e.body)
	FROM vm_email_template e
	WHERE e.uniqueId=template;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;

--
-- Definition of procedure `REGISTER_INTENT`
--
DROP PROCEDURE IF EXISTS `REGISTER_INTENT`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='' */ $$
CREATE DEFINER=`interaxtelephony`@`%` PROCEDURE  `REGISTER_INTENT`()
BEGIN -- constantes de prefijo <cons>
DECLARE loopDone boolean DEFAULT false;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET loopDone:= true;
	LevelREGISTER2: BEGIN -- variables de prefijo <reg>
        	DECLARE regIp varchar(30);
        	DECLARE regRecordNumber bigint(20);
        	DECLARE existe bigint(20);
    		DECLARE it_register_detailsss CURSOR FOR
            	SELECT srcIpAdress, COUNT(*)
                FROM it_register_details
                WHERE date>=DATE_SUB(NOW(),INTERVAL 20 MiNUTE) GROUP BY srcIpAdress;
    	    OPEN it_register_detailsss;
	    IteREGISTER2: loop
    		FETCH it_register_detailsss INTO regIp,regRecordNumber;
        	IF loopDone THEN
            	    CLOSE it_register_detailsss;
		    SET loopDone:=false;
            	    LEAVE IteREGISTER2;
        	END IF;
        	LevelINTENT: BEGIN -- variables de prefijo <int>
	          DECLARE intIp varchar(30);
                  DECLARE intNewId bigint(20);
                  DECLARE intLastRecordNumber bigint(20);
		  DECLARE existRegistIntetns int(12);
    	          DECLARE it_register_intents CURSOR FOR
            	     SELECT ip,lastRecordNumber
                     FROM it_register_intent
                     WHERE ip=regIp;
    	          OPEN it_register_intents;
	          IteINTENT: loop
    		     	FETCH it_register_intents INTO intIp,intLastRecordNumber;
                         SELECT COUNT(*) INTO existRegistIntetns FROM asterisk.it_register_intent where ip=intIp; 
			 IF(existRegistIntetns<=0) THEN
 			-- INSERTANDO REGISTER
	                   INSERT INTO asterisk.it_register_intent(ip,runDate,recordNumber,lastRecordNumber)
	                   VALUES (regIp,now(),regRecordNumber,regRecordNumber);
                         ELSE
                           UPDATE it_register_intent SET runDate=now(),recordNumber=regRecordNumber, lastRecordNumber=intLastRecordNumber+regRecordNumber WHERE ip=intIp;
                         END IF;  
			IF loopDone THEN
            	             CLOSE it_register_intents;
		             SET loopDone:=false;
            	             LEAVE IteINTENT;
        	         END IF;
                       END LOOP IteINTENT;
               END LevelINTENT;
           END LOOP IteREGISTER2; 
        END LevelREGISTER2;
END$$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;