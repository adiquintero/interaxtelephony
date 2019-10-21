-- MySQL dump 10.11
--
-- Host: localhost    Database: asterisk
-- ------------------------------------------------------
-- Server version	5.0.38-Ubuntu_0ubuntu1.4-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE asterisk;

--
-- Dumping data for table `admin_action`
--

LOCK TABLES `admin_action` WRITE;
/*!40000 ALTER TABLE `admin_action` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cdr_call_detail_record`
--

LOCK TABLES `cdr_call_detail_record` WRITE;
/*!40000 ALTER TABLE `cdr_call_detail_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `cdr_call_detail_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_did`
--

LOCK TABLES `ip_did` WRITE;
/*!40000 ALTER TABLE `ip_did` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_did` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_did_step`
--

LOCK TABLES `ip_did_step` WRITE;
/*!40000 ALTER TABLE `ip_did_step` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_did_step` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `it_enterprise`
--

LOCK TABLES `it_enterprise` WRITE;
/*!40000 ALTER TABLE `it_enterprise` DISABLE KEYS */;
INSERT INTO `it_enterprise` (`id`, `name`, `contextSuffix`, `audioFolder`) VALUES (1,'Wixtel','wixtel', 'wixtel'), (2,'Alodiga-Ve','alodigave', 'wixtel') , (3,'AlodigaColombia','AlodigaColombia', 'AlodigaColombia');
/*!40000 ALTER TABLE `it_enterprise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_equipment`
--

LOCK TABLES `ip_equipment` WRITE;
/*!40000 ALTER TABLE `ip_equipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_equipment_activation_attempt`
--

LOCK TABLES `ip_equipment_activation_attempt` WRITE;
/*!40000 ALTER TABLE `ip_equipment_activation_attempt` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_equipment_activation_attempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_equipment_manufacturer`
--

LOCK TABLES `ip_equipment_manufacturer` WRITE;
/*!40000 ALTER TABLE `ip_equipment_manufacturer` DISABLE KEYS */;
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (1,'ATCOM','ATCOM');
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (2,'Aastra','AASTRA');
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (3,'Cisco Systems Inc.','CISCO');
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (4,'Grandstream','GRANDSTREAM');
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (5,'Linksys','LINKSYS');
INSERT INTO `ip_equipment_manufacturer` (`id`, `name`, `internalName`) VALUES (6,'Polycom','POLYCOM');
/*!40000 ALTER TABLE `ip_equipment_manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_equipment_model`
--

LOCK TABLES `ip_equipment_model` WRITE;
/*!40000 ALTER TABLE `ip_equipment_model` DISABLE KEYS */;
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (1,2,'51i','Aastra 51i');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (2,2,'55i','Aastra 55i');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (3,2,'480i','Aastra 480i');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (4,1,'AT-530','ATCOM AT-530');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (5,1,'AG-188N','ATCOM AG-188N');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (6,3,'7940','Cisco 7940');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (7,4,'GPX-280','Grandstream GPX-280');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (8,4,'GPX-2010','Grandstream GPX-2010');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (9,5,'SPA3102','Linksys SPA3102');
INSERT INTO `ip_equipment_model` (`id`, `equipmentManufacturerId`, `productNumber`, `name`) VALUES (10,6,'IP-650','Polycom Soundpoint IP 650');
/*!40000 ALTER TABLE `ip_equipment_model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_extension`
--

LOCK TABLES `ip_extension` WRITE;
/*!40000 ALTER TABLE `ip_extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_equipment`
--

LOCK TABLES `ip_equipment` WRITE;
/*!40000 ALTER TABLE `ip_equipment` DISABLE KEYS */;
INSERT INTO `ip_equipment` (`id`, `equipmentModelId`, `serialNumber`, `macAddress`) VALUES (0,0,'000000000000','000000000000');
/*!40000 ALTER TABLE `ip_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rt_extension`
--

LOCK TABLES `rt_extension` WRITE;
/*!40000 ALTER TABLE `rt_extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `rt_extension` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `ip_extension_has_equipment`
--

LOCK TABLES `ip_extension_has_equipment` WRITE;
/*!40000 ALTER TABLE `ip_extension_has_equipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_extension_has_equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_ip_pbx`
--

LOCK TABLES `ip_ip_pbx` WRITE;
/*!40000 ALTER TABLE `ip_ip_pbx` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_ip_pbx` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_ring_group`
--

LOCK TABLES `ip_ring_group` WRITE;
/*!40000 ALTER TABLE `ip_ring_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_ring_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_ring_group_has_extension`
--

LOCK TABLES `ip_ring_group_has_extension` WRITE;
/*!40000 ALTER TABLE `ip_ring_group_has_extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_ring_group_has_extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_virtual_offshore_number`
--

LOCK TABLES `ip_virtual_offshore_number` WRITE;
/*!40000 ALTER TABLE `ip_virtual_offshore_number` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_virtual_offshore_number` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `ip_voice_mail_box`
--

LOCK TABLES `ip_voice_mail_box` WRITE;
/*!40000 ALTER TABLE `ip_voice_mail_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `ip_voice_mail_box` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rt_extension`
--

LOCK TABLES `rt_extension` WRITE;
/*!40000 ALTER TABLE `rt_extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `rt_extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rt_iax_peer`
--

LOCK TABLES `rt_iax_peer` WRITE;
/*!40000 ALTER TABLE `rt_iax_peer` DISABLE KEYS */;
/*!40000 ALTER TABLE `rt_iax_peer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rt_sip_peer`
--

LOCK TABLES `rt_sip_peer` WRITE;
/*!40000 ALTER TABLE `rt_sip_peer` DISABLE KEYS */;
/*!40000 ALTER TABLE `rt_sip_peer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rt_voice_mail_user`
--

LOCK TABLES `rt_voice_mail_user` WRITE;
/*!40000 ALTER TABLE `rt_voice_mail_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `rt_voice_mail_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vm_email_template`
--

LOCK TABLES `vm_email_template` WRITE;
/*!40000 ALTER TABLE `vm_email_template` DISABLE KEYS */;
INSERT INTO `vm_email_template` (`id`, `uniqueid`, `format`, `subject`, `body`, `senderName`, `senderAddress`) VALUES (1,'wixtel','text/html','Mensaje nuevo en casilla de ${VM_MAILBOX_NAME}','<html xmlns="http://www.w3.org/1999/xhtml"><head><title>Untitled Document</title></head><body topmargin=0><table width="600" border="0" align="center" cellpadding="0" cellspacing="0">  <tr>    <td colspan="3"><img src="http://www.alodiga.com/images/email_header.jpg" width="600" height="99" /></td>  </tr>  <tr>    <td width="20" height="31" background="http://www.alodiga.com/images/email_barra.jpg">&nbsp;</td>    <td width="557" background="http://www.alodiga.com/images/email_barra.jpg" align="right"><font face="Arial, Helvetica, sans-serif" size="2" color="#333333"><em>Nuevo mensaje de voz</em></font></td>    <td width="20" background="http://www.alodiga.com/images/email_barra.jpg">&nbsp;</td>  </tr>  <tr>    <td colspan="3"><table width="600" border="0" cellspacing="3" cellpadding="0">      <tr>        <td width="11">&nbsp;</td>        <td width="580">&nbsp;</td>      </tr>      <tr>        <td>&nbsp;</td>        <td><font face="Arial, Helvetica, sans-serif" size="3" color="#333333"><em>&iexcl;Hola ${VM_MAILBOX_NAME}!</em></font></td>      </tr>      <tr>        <td>&nbsp;</td>        <td>&nbsp;</td>      </tr>      <tr>        <td>&nbsp;</td>        <td>	<font face="Arial, Helvetica, sans-serif" color="#333333" size="2">Queremos notificarle que ha recibido un mensaje de <b>${VM_DUR}</b> de duración en su buzón de voz. El mensaje fue recibido el <b>${VM_DATE}</b> desde el número <b>${VM_CIDNUM}</b>&#44; por lo que es posible que desee comprobarlo cuando llegue una oportunidad.<br><br>¡Gracias!	</font>	</td>      </tr>      <tr>        <td>&nbsp;</td>        <td>&nbsp;</td>      </tr>      <tr>        <td>&nbsp;</td>        <td><ul>              <li><font face="Arial, Helvetica, sans-serif" color="#333333" size="2"><strong>Virtual PhoneOffice: </strong>Hosted IP PBX con la Red de Nueva Generaci&oacute;n que ampl&iacute;a los beneficios de tu negocio.</font></li>              <li><font face="Arial, Helvetica, sans-serif" color="#333333" size="2"><strong>Business Kit: </strong>Soluciones de comunicaci&oacute;n que aumentan la efectividad de tu empresa.</font></li>              <li><font face="Arial, Helvetica, sans-serif" color="#333333" size="2"><strong>Favorite Destination: </strong>Selecciona destinos de tu preferencia de acuerdo a las necesidades de tu negocio.</font></li>          </ul></font></td>      </tr>      <tr>        <td>&nbsp;</td>        <td>&nbsp;</td>      </tr>      <tr>        <td>&nbsp;</td>        <td><font face="Arial, Helvetica, sans-serif" color="#333333" size="2">Son sólo algunos de los servicios que podrás disfrutar gracias a tu suscripción en <strong>alodiga.com</strong></font></td>      </tr>            <tr>        <td colspan="2">&nbsp;</td>        </tr>      <tr>        <td height="17" colspan="2" align="center" bgcolor="#FFFFE6"><font face="Arial, Helvetica, sans-serif" color="#333333" size="1">Esta es una cuenta de correo no monitoreada. Por favor&#44; no respondas ni reenv&iacute;es mensajes a esta cuenta.</font></td>        </tr>    </table></td>  </tr>    <tr>    <td height="1" colspan="3" ></td>  </tr>  <tr>    <td height="5" colspan="3"></td>  </tr>  <tr>    <td colspan="3" align="center"><font face="Arial, Helvetica, sans-serif" color="#333333" size="1">&copy; Copyright 2009 - Alodiga&#44; C.A.    Todos los derechos reservados<br /><a href="#" style="color:#333333">http://www.alodiga.com</a></font></td>  </tr></table></body></html>', 'Alodiga', 'info@alodiga.com');
/*!40000 ALTER TABLE `vm_email_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `vm_voice_message`
--

LOCK TABLES `vm_voice_message` WRITE;
/*!40000 ALTER TABLE `vm_voice_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `vm_voice_message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-12-17 19:56:49
