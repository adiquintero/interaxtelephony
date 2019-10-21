--
-- Cambiar los contextos
--

UPDATE`rt_extension` SET context='rt_cc_incoming'
WHERE context='cc_incoming';

UPDATE`rt_extension` SET context='rt_ip_incoming'
WHERE context='ip_incoming';


--
-- Cambiar formato de Data
--

UPDATE `rt_extension` SET appData= REPLACE(appData, 'ip-vm-', '')
WHERE app LIKE 'Macro';


--
-- Cambiar appData del Dial y app Dial a Macro 
--


UPDATE `rt_extension` SET appData=  SUBSTRING_INDEX(appdata, '|', 1)  WHERE app LIKE 'Dial';
UPDATE `rt_extension` SET appData=  SUBSTRING_INDEX(appdata, '/', -1)  WHERE app LIKE 'Dial' ;
UPDATE `rt_extension` SET appData= CONCAT('IP_PBX_CALL_EXTENSION|',SUBSTRING_INDEX(appdata, '-', -1))
WHERE app LIKE 'Dial';

UPDATE `rt_extension` SET app= 'Macro'
WHERE app LIKE 'Dial';
