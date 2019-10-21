DELIMITER $$

DROP PROCEDURE IF EXISTS `asterisk`.`IVR_DID_CHANGER`$$
CREATE PROCEDURE `asterisk`.`IVR_DID_CHANGER` ()
BEGIN
  DECLARE done BOOLEAN DEFAULT FALSE;
  DECLARE externalNumberTmp VARCHAR(20) DEFAULT '' ;
  DECLARE c1 CURSOR FOR SELECT externalNumber FROM `cc_did` WHERE accessType='CallCenter';
  DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = TRUE;
 

  OPEN c1;

  REPEAT
    FETCH c1 INTO externalNumberTmp;
    IF NOT done THEN
		INSERT INTO `ivr_did` VALUES (NULL,externalNumberTmp,0,'es','SALES','INCOMING',1);
		SET @ivr_did_id = LAST_INSERT_ID();
		UPDATE `it_did` SET serviceFamily='IVR',servicetype='SALES',foreignId=@ivr_did_id WHERE externalNumber=externalNumberTmp ;
		INSERT INTO `ivr_did_step` VALUES (NULL,@ivr_did_id,'IVR',externalNumberTmp);
		INSERT INTO `rt_extension` VALUES (NULL,'ivr_incoming',externalNumberTmp,1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');
    END IF;
  UNTIL done END REPEAT;
  CLOSE c1;
END$$

DELIMITER ;