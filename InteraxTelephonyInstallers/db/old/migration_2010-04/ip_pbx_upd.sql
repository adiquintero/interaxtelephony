--
-- Actualiza el foreignId de ip_did con el id de ip_extension ó ip_virtual_offshore_number
--


UPDATE ip_did set foreignId = (SELECT id FROM ip_extension WHERE didId=ip_did.id) WHERE type='EXTENSION';  
UPDATE ip_did set foreignId = (SELECT id FROM ip_virtual_offshore_number WHERE didId=ip_did.id) WHERE type='VIRTUAL_OFFSHORE_NUMBER'; 