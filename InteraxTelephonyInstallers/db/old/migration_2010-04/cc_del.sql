--
-- ELIMINAR TODOS LOS DID'S ASOCIADOS AL IVR VENTAS PROCALL
--

DELETE FROM `rt_extension` WHERE exten IN (SELECT externalNumber FROM `cc_did` WHERE accessType='CallCenter');
DELETE FROM `cc_did_step` WHERE didId IN (SELECT id FROM `cc_did` WHERE accessType='CallCenter');

INSERT INTO `cc_application` VALUES (null,1,'1_USA_IVR','USA'); 
INSERT INTO `cc_application` VALUES (null,1,'1_USA_IVR_DIALER','USA');

update `cc_did` set foreignId=1 WHERE accessType = 'IVR';
update `cc_did` set foreignId = 2 WHERE accessType = 'IVR_DIALER';
