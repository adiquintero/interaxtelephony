SET @IVR := 'IVR', @COUNTRY := 'USA', @IVR_DIALER := 'IVR_DIALER', @ENTERPRISE_ID := '1';
SET @configName = (SELECT CONCAT_WS('_',@ENTERPRISE_ID, @COUNTRY, @IVR));

USE asterisk;

--
-- DID 18009543212 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18009543212',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18009543212','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18009543212');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18009543212',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18009543213 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18009543213',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18009543213','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18009543213');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18009543213',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18009543214 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18009543214',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18009543214','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18009543214');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18009543214',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18009896444 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18009896444',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18009896444','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18009896444');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18009896444',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18007895212 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18007895212',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18007895212','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18007895212');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18007895212',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18007895413 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18007895413',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18007895413','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18007895413');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18007895413',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');






--
-- DID 18005990174 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18005990174',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18005990174','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18005990174');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18005990174',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18005994576 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18005994576',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18005994576','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18005994576');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18005994576',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18005996789 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18005996789',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18005996789','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18005996789');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18005996789',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006991815 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006991815',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006991815','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006991815');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006991815',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006991819 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006991819',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006991819','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006991819');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006991819',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006995241 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006995241',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006995241','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006995241');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006995241',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006996890 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006996890',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006996890','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006996890');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006996890',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006998957 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006998957',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006998957','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006998957');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006998957',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006998963 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006998963',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006998963','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006998963');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006998963',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006963991 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006963991',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006963991','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006963991');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006963991',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18006963995 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18006963995',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006963995','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18006963995');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18006963995',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');




--
-- DID 18002772928 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18002772928',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18006963995','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18002772928');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18002772928',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18002775895 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18002775895',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18002775895','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18002775895');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18002775895',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18002777339 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18002777339',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18002777339','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18002777339');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18002777339',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');

--
-- DID 18002777970 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18002777970',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18002777970','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18002777970');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18002777970',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');



--
-- DID 18004661261 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18004661261',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18004661261','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18004661261');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18004661261',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18004661954 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18004661954',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18004661954','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18004661954');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18004661954',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18002777970 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18004663217',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18004663217','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18004663217');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18004663217',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');


--
-- DID 18002777970 CALL_CENTER
--
INSERT INTO `cc_did` VALUES (NULL,1,'callcenter','18004669944',@COUNTRY,'ACCESS_NUMBER','CallCenter');
SELECT @cc_did_id := LAST_INSERT_ID();
INSERT INTO `it_did` VALUES (NULL,'18004669944','CALLING_CARD','CALL_CENTER',@cc_did_id);
INSERT INTO `cc_did_step` VALUES (NULL,@cc_did_id,'CallCenter','18004669944');
INSERT INTO `rt_extension` VALUES (NULL,'cc_incoming','18004669944',1,'Agi','agi://localhost/CallCenterAgi.agi?configName=callcenter');








