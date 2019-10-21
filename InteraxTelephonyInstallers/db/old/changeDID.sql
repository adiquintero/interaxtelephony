UPDATE ip_did SET externalNumber='13232084919' WHERE externalNumber='13233299135';
UPDATE it_did SET externalNumber='13232084919' WHERE externalNumber='13233299135';
UPDATE rt_extension SET exten='13232084919' WHERE exten='13233299135';

SET @data = (SELECT REPLACE(appdata, '|13233299135', '|13232084919') FROM rt_extension WHERE exten='13232084919' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='13232084919' AND appdata LIKE "IP_PBX_LEAVE%";

UPDATE ip_did SET externalNumber='12065772723' WHERE externalNumber='12025963524';
UPDATE it_did SET externalNumber='12065772723' WHERE externalNumber='12025963524';
UPDATE rt_extension SET exten='12065772723' WHERE exten='12025963524';

SET @data = (SELECT REPLACE(appdata, '|12025963524', '|12065772723') FROM rt_extension WHERE exten='12065772723' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='12065772723' AND appdata LIKE "IP_PBX_LEAVE%";

UPDATE ip_did SET externalNumber='17862843134' WHERE externalNumber='17868662068';
UPDATE it_did SET externalNumber='17862843134' WHERE externalNumber='17868662068';
UPDATE rt_extension SET exten='17862843134' WHERE exten='17868662068';

SET @data = (SELECT REPLACE(appdata, '|17868662068', '|17862843134') FROM rt_extension WHERE exten='17862843134' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='17862843134' AND appdata LIKE "IP_PBX_LEAVE%";

UPDATE ip_did SET externalNumber='17862843135' WHERE externalNumber='17868662069';
UPDATE it_did SET externalNumber='17862843135' WHERE externalNumber='17868662069';
UPDATE rt_extension SET exten='17862843135' WHERE exten='17868662069';

SET @data = (SELECT REPLACE(appdata, '|17868662069', '|17862843135') FROM rt_extension WHERE exten='17862843135' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='17862843135' AND appdata LIKE "IP_PBX_LEAVE%";

UPDATE ip_did SET externalNumber='12132540003' WHERE externalNumber='12134021489';
UPDATE it_did SET externalNumber='12132540003' WHERE externalNumber='12134021489';
UPDATE rt_extension SET exten='12132540003' WHERE exten='12134021489';

SET @data = (SELECT REPLACE(appdata, '|12134021489', '|12132540003') FROM rt_extension WHERE exten='12132540003' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='12132540003' AND appdata LIKE "IP_PBX_LEAVE%";

UPDATE ip_did SET externalNumber='14084041789' WHERE externalNumber='14079568272';
UPDATE it_did SET externalNumber='14084041789' WHERE externalNumber='14079568272';
UPDATE rt_extension SET exten='14084041789' WHERE exten='14079568272';

SET @data = (SELECT REPLACE(appdata, '|14079568272', '|14084041789') FROM rt_extension WHERE exten='14084041789' AND appdata LIKE 'IP_PBX_LEAVE%');
UPDATE rt_extension SET appdata=@data WHERE exten='14084041789' AND appdata LIKE "IP_PBX_LEAVE%";
