use SISAC_CALL_CENTER;

INSERT INTO dnid (id, dnid) VALUES
(1, '18009543212'),
(2, '18009543213'),
(3, '18009543214'),
(4, '18009896444'),
(5, '18007895212'),
(6, '18007895413');

INSERT INTO script (id, title, initialDialogId, incorrectPhrase, timeoutPhrase) VALUES
(1, 'CallCenterAlodiga', 1, 'dialogVentasOpcionInvalida', 'dialogVentasTiempoExcedido');

INSERT INTO `SISAC_CALL_CENTER`.`dialog` (`id`, `scriptId`, `phase`, `title`, `type`, `action`, `actionData`, `speech`) VALUES
(1, 1, 'dialogVentasIdioma', 'VentasIdioma', 'CLOSED', 'NONE', NULL, 'Idioma selecciona'),
(2, 1, 'dialogVentasIntroduccion', 'Introduccion', 'INFORMATIVE', 'NEXT_DIALOG', '3', 'Bienvenidos a Alodiga'),
(3, 1, 'dialogVentasPreguntaBandaAncha', 'PreguntaBandaAncha', 'CLOSED', 'NEXT_DIALOG', '4', 'Posee internet banda ancha '),
(4, 1, 'dialogVentasPaises', 'OpcionesPaises', 'CLOSED', 'NEXT_DIALOG', '5', 'Pais frecuente al que llama'),
(5, 1, 'dialogVentasPreguntaTarjeta', 'PreguntaTarjeta', 'CLOSED', 'NEXT_DIALOG', '6', 'Posee tarjeta de credito '),
(6, 1, 'dialogVentasTranferirOperador', 'TransferirOperador', 'INFORMATIVE', 'DIAL', 'SIP/@pro-call', 'LLamada transferida al operador');


INSERT INTO `SISAC_CALL_CENTER`.`option` (`id`, `dtmf`, `minLimit`, `maxLimit`, `action`, `actionData`, `dialogId`, `title`) VALUES
(NULL, '1', 0, 0, 'SET', 'LANGUAGE()=es|2', 1, 'Español'),
(NULL, '2', 0, 0, 'SET', 'LANGUAGE()=en|2', 1, 'Ingles'),
(NULL, '1', 0, 0, 'NONE', NULL, 3, 'Acceso a internet banda ancha'),
(NULL, '2', 0, 0, 'NONE', NULL, 3, 'no Acceso a internet banda ancha'),
(NULL, '1', 0, 0, 'NONE', NULL, 4, 'Mexico'),
(NULL, '2', 0, 0, 'NONE', NULL, 4, 'Guatemala'),
(NULL, '3', 0, 0, 'NONE', NULL, 4, 'Nicaragua'),
(NULL, '4', 0, 0, 'NONE', NULL, 4, 'Honduras'),
(NULL, '5', 0, 0, 'NONE', NULL, 4, 'Colombia'),
(NULL, '6', 0, 0, 'NONE', NULL, 4, 'Venezuela'),
(NULL, '7', 0, 0, 'NONE', NULL, 4, 'Cuba'),
(NULL, '8', 0, 0, 'NONE', NULL, 4, 'Ecuador'),
(NULL, '9', 0, 0, 'NONE', NULL, 4, 'Republica Dominicana'),
(NULL, '0', 0, 0, 'NONE', NULL, 4, 'Sin pais'),
(NULL, '1', 0, 0, 'NONE', NULL, 5, 'Tarjeta de Credito o Debito'),
(NULL, '2', 0, 0, 'NONE', NULL, 5, 'No Tarjeta de Credito o Debito');


INSERT INTO script_schedule (id, beginingHour, endingHour, dayOfWeek, priority) VALUES
(1, '2009-10-28 00:00:00', '2009-10-28 23:59:59', 254, 1);

INSERT INTO dnid_has_script_in_schedule (id, dnidId, scriptId, scriptScheduleId) VALUES
(1, 1, 1, 1),
(2, 2, 1, 1),
(3, 3, 1, 1),
(4, 4, 1, 1),
(5, 5, 1, 1),
(6, 6, 1, 1);

INSERT INTO SISAC_CALL_CENTER.operator_type VALUES  
(1,'Call Center Admin',1),
(2,'Admin',3),
(3,'Operator',4);