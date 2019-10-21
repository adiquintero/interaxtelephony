#
# MySQLDiff 1.5.0
#
# http://www.mysqldiff.org
# (c) 2001-2004, Lippe-Net Online-Service
#
# Create time: 26.04.2010 12:22
#
# --------------------------------------------------------
# Source info
# Host: 192.168.201.11
# Database: asterisk
# --------------------------------------------------------
# Target info
# Host: 192.168.3.55
# Database: asterisk
# --------------------------------------------------------
#
USE asterisk;
SET FOREIGN_KEY_CHECKS = 0;

#
# DDL START
#
CREATE TABLE cc_application (
    id bigint(20) unsigned NOT NULL COMMENT '' auto_increment,
    enterpriseId bigint(20) unsigned NOT NULL COMMENT '',
    configName varchar(50) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    country char(5) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    PRIMARY KEY (id),
    INDEX fk_cc_application_it_enterprise (enterpriseId)
) DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE ivr_did (
    id bigint(20) unsigned NOT NULL COMMENT '' auto_increment,
    externalNumber varchar(20) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    monitored tinyint(1) NOT NULL COMMENT '',
    language char(5) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci,
    serviceType varchar(50) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci,
    accessType varchar(50) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci,
    foreignId bigint(20) unsigned NOT NULL COMMENT '',
    PRIMARY KEY (id),
    INDEX fk_ivr_ivr (foreignId)
) DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE ivr_did_step (
    id bigint(20) unsigned NOT NULL COMMENT '' auto_increment,
    didId bigint(20) unsigned NOT NULL COMMENT '',
    `action` varchar(50) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    data varchar(255) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    PRIMARY KEY (id),
    INDEX fk_ivr_dids_did (didId)
) DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

CREATE TABLE ivr_interactive_voice_response (
    id bigint(20) unsigned NOT NULL COMMENT '' auto_increment,
    enterpriseId bigint(20) unsigned NOT NULL COMMENT '',
    name varchar(20) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    type varchar(50) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    PRIMARY KEY (id),
    INDEX fk_it_enterprise (enterpriseId)
) DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

ALTER TABLE admin_action
    COMMENT='';


ALTER TABLE cc_did
    ADD monitored tinyint(1) NOT NULL COMMENT '' AFTER externalNumber,
    ADD serviceType varchar(50) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER monitored,
    ADD foreignId bigint(20) unsigned NOT NULL COMMENT '' AFTER accessType,
    DROP configName,
    DROP country,
    DROP FOREIGN KEY fk_enterprise_did,
    DROP INDEX fk_enterprise_did,
    DROP enterpriseId,
    ADD INDEX fk_cc_did_cc_application (foreignId),
    COMMENT='';


ALTER TABLE cc_did_step
    COMMENT='';


ALTER TABLE cdr_call_detail_record
    COMMENT='';


ALTER TABLE ip_did
    ADD serviceType varchar(50) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER language,
    ADD accessType varchar(30) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER type,
    ADD foreignId bigint(20) unsigned NOT NULL COMMENT '' AFTER accessType,
    COMMENT='';


ALTER TABLE ip_did_step
    COMMENT='';


ALTER TABLE ip_equipment
    COMMENT='';


ALTER TABLE ip_equipment_activation_attempt
    COMMENT='';


ALTER TABLE ip_equipment_manufacturer
    COMMENT='';


ALTER TABLE ip_equipment_model
    COMMENT='';


ALTER TABLE ip_extension
    COMMENT='';


ALTER TABLE ip_extension_has_equipment
    COMMENT='';


ALTER TABLE ip_ip_pbx
    COMMENT='';


ALTER TABLE ip_ring_group
    COMMENT='';


ALTER TABLE ip_ring_group_has_extension
    COMMENT='';


ALTER TABLE ip_virtual_offshore_number
    COMMENT='';


ALTER TABLE ip_voice_mail_box
    COMMENT='';


ALTER TABLE it_did
    ADD foreignId bigint(20) unsigned NOT NULL COMMENT '' AFTER servicetype,
    MODIFY servicetype varchar(50) NOT NULL COMMENT '' COLLATE latin1_swedish_ci,
    DROP foreignDidId,
    COMMENT='';
#
#  Fieldformat of
#    it_did.servicetype changed from varchar(25) NOT NULL COMMENT '' COLLATE latin1_swedish_ci to varchar(50) NOT NULL COMMENT '' COLLATE latin1_swedish_ci.
#  Possibly data modifications needed!
#

ALTER TABLE it_enterprise
    COMMENT='';


ALTER TABLE it_peer
    COMMENT='';


ALTER TABLE it_voice_mail_box
    ADD foreignId bigint(20) unsigned NOT NULL COMMENT '' AFTER type,
    DROP foreingVoiceMailBoxId,
    COMMENT='';


ALTER TABLE provisioning_file
    COMMENT='';


ALTER TABLE provisioning_file_request
    COMMENT='';


ALTER TABLE rt_extension
    COMMENT='';


ALTER TABLE rt_iax_peer
    COMMENT='';


ALTER TABLE rt_sip_peer
    COMMENT='';


ALTER TABLE rt_voice_mail_user
    MODIFY stamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '' on update CURRENT_TIMESTAMP,
    COMMENT='';
#
#  Fieldformat of
#    rt_voice_mail_user.stamp changed from timestamp NOT NULL DEFAULT 'CURRENT_TIMESTAMP' COMMENT '' to timestamp NOT NULL DEFAULT 'CURRENT_TIMESTAMP' COMMENT '' on update CURRENT_TIMESTAMP.
#  Possibly data modifications needed!
#

ALTER TABLE vm_email_template
    ADD dateFormat varchar(30) NOT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER senderAddress,
    ADD language varchar(2) NOT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER dateFormat,
    COMMENT='';


ALTER TABLE vm_voice_message
    MODIFY dir varchar(100) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci,
    COMMENT='';
#
#  Fieldformat of
#    vm_voice_message.dir changed from varchar(80) NULL DEFAULT '' COMMENT '' COLLATE latin1_swedish_ci to varchar(100) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci.
#  Possibly data modifications needed!
#

ALTER TABLE vt_customer
    COMMENT='';


ALTER TABLE vt_did
    ADD serviceType varchar(50) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER language,
    ADD accessType varchar(30) NULL DEFAULT NULL COMMENT '' COLLATE latin1_swedish_ci AFTER type,
    ADD foreignId bigint(20) unsigned NOT NULL COMMENT '' AFTER accessType,
    COMMENT='';


ALTER TABLE vt_did_step
    COMMENT='';


ALTER TABLE vt_peer
    COMMENT='';


ALTER TABLE vt_virtual_offshore_number
    COMMENT='';


#
# DDL END
#

SET FOREIGN_KEY_CHECKS = 1;


