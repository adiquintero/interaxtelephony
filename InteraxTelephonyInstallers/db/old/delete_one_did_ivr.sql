DELETE FROM cc_did_step WHERE action = 'IVR' AND data='18668777794'; 
DELETE FROM cc_did WHERE accessType = 'IVR' AND externalNumber='18668777794'; 
DELETE FROM it_did WHERE serviceType = 'CALLING_CARD' AND externalNumber='18668777794';
DELETE FROM rt_extension WHERE appdata = 'CALLING_CARD_AGI|1_USA_IVR' AND exten = '18668777794';