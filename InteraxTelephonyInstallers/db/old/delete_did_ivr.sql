DELETE FROM cc_did_step WHERE action = 'IVR'; 
DELETE FROM cc_did WHERE accessType = 'IVR'; 
DELETE FROM it_did WHERE serviceType = 'CALLING_CARD';
DELETE FROM rt_extension WHERE appdata = 'CALLING_CARD_AGI|1_USA_IVR';
