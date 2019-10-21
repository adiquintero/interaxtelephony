SET @ENTERPRISE_ID := '1';

INSERT INTO `ivr_interactive_voice_response` VALUES (NULL,@ENTERPRISE_ID,'IVR_VENTAS_PROCALL','SALES');

CALL `asterisk`.`IVR_DID_CHANGER`();

DELETE FROM `cc_did` WHERE accessType='CallCenter';

DROP PROCEDURE IF EXISTS `asterisk`.`IVR_DID_CHANGER`;