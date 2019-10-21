<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>
<xsl:template match="/">
<!--
#-
#  Advanced/System-wide Options
#-
P9999 = 0		# Enable Provider Lock. 0 - No, 1 - Yes.
P9998 = 		# Provider Lock Key. A string of up to 16 bytes.
P9997 = 		# Provider Authentication. A string of up to 16 bytes
P1 = 			# Password for configuration file authentication
P2 = admin		# Admin password for web interface
P49 = 0			# G723 rate. 0 - 6.3kbps encoding rate, 1 - 5.3kbps encoding rate
P97 = 0			# iLBC Frame Size. 0 - 20ms(default), 1 - 30ms.
P96 = 97		# iLBC payload type. Between 96 and 127, default is 97.
P50 = 0			# Silence Suppression. 0 - no, 1 - yes
P37 = 2			# Voice Frames per TX (up to 10/20/32/64 frames for G711/G726/G723/other codecs respectively)
P38 = 48		# Layer 3 QoS (IP Diff-Serv or Precedence value for RTP)
P51 = 0			# Layer 2 QoS. 802.1Q/VLAN Tag (VLAN classification for RTP)
P87 = 0			# Layer 2 QoS. 802.1p priority value (0 - 7)
P85 = 4			# No Key Entry Timeout. Default - 4 seconds.
P72 = 1			# Use # as Dial Key. 0 - no, 1 - yes
P39 = 5004 		# Local RTP port (1024-65535, default 5004)
P78 = 0			# Use Random Port. 0 - no, 1 - yes
P84 = 20		# Keep-alive interval (in seconds. default 20 seconds)
P101 =			# Use NAT IP.  This will enable our SIP client to use this IP in the SIP/SDP message. Example 64.3.153.50.
P76 = stun.mycompany.com	# STUN server
-->
#-
# Firmware Upgrade 
#-
<xsl:choose>
    <xsl:when test="phone_config/auto_provisioning/@protocol ='TFTP'">
P212 = 0		
</xsl:when>
<xsl:otherwise>
P212 = 1
</xsl:otherwise>
</xsl:choose>	
P192 = <xsl:value-of select="phone_config/auto_provisioning/@server_ip"/>			
P237 = <xsl:value-of select="phone_config/auto_provisioning/@server_ip"/>			
P232 =				
P233 =				
P234 =				
P235 =				
P145 = 1		
P194 = 0		
P193 = <xsl:value-of select="phone_config/auto_provisioning/@update_interval"/>	 
P238 = 0		
P240 = 0		
<!--
#-
# XML Phonebook
#-
P330 = 0		
P331 =			
P332 = 0		
P333 = 0		
P1304 = 		
#
# XML Idle Screen 
# N/A for GXP1200 and GXP280
P340 = 0		
P341 =			
-->
<xsl:for-each select="phone_config/voip/sip/accounts/account">
 <xsl:choose>
    <xsl:when test="@number ='1'">
#---------------------------------------
# Primary Account (Account 1) Settings
#---------------------------------------
P271 = 1 						
P270 = <xsl:value-of select="@username"/> 		
P47 = <xsl:value-of select="@server_ip"/>		
P48 = <xsl:value-of select="@server_ip"/>		
P35 = <xsl:value-of select="@username"/> 		
P36 = <xsl:value-of select="@username"/> 		
P34 = <xsl:value-of select="@password"/> 		
P40 = <xsl:value-of select="@server_port"/> 
P73 = 1			# Send DTMF. 8 - in audio, 1 - via RTP, 2 - via SIP INFO 11 - In Audio/RTP/SIP INFO, 9 - In Audio/RTP 10 - IN Audio/SIP INFO, 3 - RTP /SIP INFO	
P137 = 18		# Disable Multiple Media Attribute in SDP. 0 - no, 1 - yes
P131 = 1		# Use RFC3581 Symmetric Routing. 0 - no, 1 - yes	
<!--
P3 =			# Display Name (John Doe)
P103 = 0		# Use DNS SRV. 0 - No, 1 - Yes.
P63 = 0			# SIP User ID is phone number. 0 - no, 1 - yes
P31 = 1			# SIP Registration. 0 - no, 1 - yes
P81 = 0			# Unregister On Reboot. 0 - no, 1 - yes
P32 = 60		# Register Expiration (in minutes. default 1 hour, max 45 days)
P138 = 20		# SIP Registration Failure Retry Wait Time. In seconds. Between 1-3600, default is 20.
P209 = 100		# SIP T1 Timeout. RFC 3261 T1 value (RTT estimate) 50 - 0.5 sec, 100 - 1 sec, 200 - 2 sec. Default 100.
P250 = 400		# SIP T2 Interval. RFC 3261 T2 value. The maximum retransmit interval for non-INVITE requests and INVITE responses. 
			#200 - 2 sec, 400 - 4 sec, 800 - 8 sec. Default 400.
P130 = 1		# SIP Transport. 1 - UDP, 2 - TCP.
P131 = 1		# Use RFC3581 Symmetric Routing. 0 - no, 1 - yes
P52 = 1			# NAT Traversal. 0 - yes, 1 - no, 2 - No, but send keep-alive
P99 = 0			# SUBSCRIBE for MWI. (Whether or not send SUBSCRIBE for Message Waiting Indication) 0 - No, 1 - Yes.
P188 = 0		# PUBLISH for Presence. 0 - no, 1 - yes
P197 =			# Proxy-Require (A SIP extension to enable firewall penetration)
P33 =			# Voice Mail UserID (User ID/extension for 3rd party voice mail system)

P29 = 0			# Early Dial (use "Yes" only if proxy supports 484 response). 0 - no, 1 - yes
P66 =			# Dial Plan Prefix (dial plan prefix string).
P139 = 20		# Delayed Call Forward Wait Time. 1 to 120 seconds. Default 20 seconds.
P191 = 1		# Enable Call Features.  0 - no, 1 - yes
P182 = 0		# Call Log. 0 - Log All, 1 - Log Incoming/Outgoing only (Missed calls NOT recorded), 2 - Disable Call Log
P260 = 180		# Session Expiration (in seconds. default 180 seconds. Allowed value: 90-65535)
P261 = 90		# Minimum SE (in seconds. default 90 seconds, must be lower than or equal to P260)
P1328 = 60		# Ring Timeout.  (in seconds. Between 30-3600, default is 60)
P262 = 0		# Caller Request Timer (Request for timer when calling) 0 - no, 1 - yes
P263 = 0		# Callee Request Timer (Request for timer when called. i.e. if remote party supports timer but did not request for one) 0 - no, 1 - yes
P264 = 0		# Force Timer (Still use timer when remote party does not support timer) 0 - no, 1 - yes
P265 = 0		# Force INVITE (Always refresh with INVITE instead of UPDATE even when remote party supports UPDATE) 0 - no, 1 - yes
P266 = 0		# UAC Specify Refresher. 0 - omit, 1 - UAC, 2 - UAS
P267 = 1		# UAS Specify Refresher. 1 - UAC, 2 - UAS
P272 = 0		# Enable 100rel. 0 - no, 1 - yes
P104 = 0		# Account Ring Tone. 0 - system ring tone, 1 - custom ring tone 1, 2 - custom ring tone 2, 3 - custom ring tone 3.
P65 = 0			# Send Anonymous (caller ID will be blocked if set to Yes). 0 - no, 1 - yes
P268 = 0		# Anonymous Method. 0 - Use From Header, 1 - Use Privacy Header.
P129 = 0		# Anonymous Call Rejection. 0 - no, 1 - yes
P90 = 0			# Auto Answer. 0 - no, 1 - yes
P298 = 0		# Allow Auto Answer by Call-Info. 0 - no, 1 - yes
P299 = 0		# Turn off speaker on remote disconnect. 0 - no, 1 - yes
P258 = 0		# Check SIP User ID for incoming INVITE. 0 - no, 1 - yes
P135 = 0		# Refer-To Use Target Contact. 0 - no, 1 - yes
P137 = 18		# Disable Multiple Media Attribute in SDP. 0 - no, 1 - yes
# Preferred Vocoder 0 - PCMU, 2 - G.726-32, 3 - GSM, 4 - G.723.1, 8 - PCMA, 9 - G.722, 18 - G.729A/B, 98 - iLBC
P57 = 0			# choice 1.
P58 = 8			# choice 2.
P59 = 4			# choice 3
P60 = 18		# choice 4
P61 = 2			# choice 5
P62 = 98		# choice 6
P46 = 9			# choice 7
P98 = 3			# choice 8
P183 = 0		# SRTP Mode 0 = Disable, 1 = Enabled but not forced, 2 = Enabled and forced
P134 = 			# eventlist BLF URI N/A for GXP1200 and GXP280
P198 = 100		# Special Feature.    100 - Standard, 101 - Nortel MCS, 102- Broadsoft, 108 - CBCOM, 109 - RNK, 110 - Sylantro.
-->
</xsl:when>
<xsl:otherwise>
#---------------------------------------
# SIP Account <xsl:value-of select="@number"/> Settings
#---------------------------------------
P<xsl:value-of select="@number+2"/>01 = 1  							
P<xsl:value-of select="@number+2"/>17 = <xsl:value-of select="@username"/>  			
P<xsl:value-of select="@number+2"/>02 = <xsl:value-of select="@server_ip"/>			
P<xsl:value-of select="@number+2"/>03 = <xsl:value-of select="@server_ip"/>			
P<xsl:value-of select="@number+2"/>04 = <xsl:value-of select="@username"/>			
P<xsl:value-of select="@number+2"/>05 = <xsl:value-of select="@username"/>			
P<xsl:value-of select="@number+2"/>06 = <xsl:value-of select="@password"/>			
P<xsl:value-of select="@number+2"/>13 = <xsl:value-of select="@server_port"/> 			
<!--
P<xsl:value-of select="@number+2"/>07 =			# Display Name (John Doe)
P<xsl:value-of select="@number+2"/>08 = 0		# Use DNS SRV. 0 - No, 1 - Yes.
P<xsl:value-of select="@number+2"/>09 = 0		# User ID is phone number; "user=phone". 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>10 = 1		# SIP Registration. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>11 = 0		# Unregister on Reboot. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>12 = 60		# Register Expiration (in minutes. default 1 hour, max 45 days)
P<xsl:value-of select="@number+2"/>71 = 20		# SIP Registration Failure Retry Wait Time. In seconds. Between 1-3600, default is 20.
P<xsl:value-of select="@number+2"/>40 = 100		# SIP T1 Timeout. RFC 3261 T1 value (RTT estimate) 50 - 0.5 sec, 100 - 1 sec, 200 - 2 sec. Default 100.
P<xsl:value-of select="@number+2"/>41 = 400		# SIP T2 Interval. RFC 3261 T2 value. The maximum retransmit interval for non-INVITE requests and INVITE responses. 
							# 200 - 2 sec, 400 - 4 sec, 800 - 8 sec. Default 400.
P<xsl:value-of select="@number+2"/>48 = 1		# SIP Transport. 1 - UDP, 2 - TCP.
P<xsl:value-of select="@number+2"/>47 = 0		# Use RFC3581 Symmetric Routing. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>14 = 1		# NAT Traversal. 0 - yes, 1 - no, 2 - No, but send keep-alive
P<xsl:value-of select="@number+2"/>15 = 0		# Subscribe MWI. (Whether or not send SUBSCRIBE for Message Waiting Indication) 0 - No, 1 - Yes.
P<xsl:value-of select="@number+2"/>88 = 0		# PUBLISH for Presence. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>18 =			# Proxy Require
P<xsl:value-of select="@number+2"/>26 =			# Voice Mail UserID (User ID/extension for 3rd party voice mail system)
P<xsl:value-of select="@number+2"/>16 = 8		# Send DTMF. 8 - in audio, 1 - via RTP, 2 - via SIP INFO, 11 - In Audio / RTP /SIP INFO, 9 - In Audio/RTP, 
							# 10 - IN Audio / SIP INFO,3 - RTP / SIP INFO
P<xsl:value-of select="@number+2"/>22 = 0		# Early Dial. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>19 =			# Dial Plan Prefix
P<xsl:value-of select="@number+2"/>70 = 20		# Delayed Call Forward Wait Time. 1 to 120 seconds. Default 20 seconds.
P<xsl:value-of select="@number+2"/>20 = 1		# Enable Call Features. 0 - no, 1 - yes.
P<xsl:value-of select="@number+2"/>42 = 0		# Call Log. 0 - Log All, 1 - Log Incoming/Outgoing only (Missed calls NOT recorded), 2 - Disable Call Log
P<xsl:value-of select="@number+2"/>34 = 180		# Default Session Expires (in seconds. default 180 seconds. Allowed value: 90-65535)
P<xsl:value-of select="@number+2"/>27 = 90		# Minimum SE (in seconds. default 90 seconds, must be lower than or equal to P260)
P<xsl:value-of select="@number+2"/>76 = 60		#Ring Timeout. in seconds. Between 30-3600, default is 60
P<xsl:value-of select="@number+2"/>28 = 0		# Caller Request Timer (Request for timer when calling) 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>29 = 0		# Callee Request Timer (Request for timer when called. i.e. if remote party supports timer but did not request for one)
 							# 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>30 = 0		# Force Timer (Still use timer when remote party does not support timer) 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>31 = 0		# Force INVITE (Always refresh with INVITE instead of UPDATE even when remote party supports UPDATE) 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>32 = 0		# UAC Specify Refresher. 0 - omit, 1 - UAC, 2 - UAS
P<xsl:value-of select="@number+2"/>33 = 1		# UAS Specify Refresher. 1 - UAC, 2 - UAS
P<xsl:value-of select="@number+2"/>35 = 0		# Enable 100rel. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>23 = 0		# Account Ring Tone. 0 - system ring tone, 1 - custom ring tone 1, 2 - custom ring tone 2, 3 - custom ring tone 3.
P<xsl:value-of select="@number+2"/>21 = 0		# Send Anonymous. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>68 = 0		# Anonymous Method. 0 - Use From Header, 1 - Use Privacy Header.
P<xsl:value-of select="@number+2"/>46 = 0		# Anonymous Call Rejection. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>25 = 0		# Auto Answer. 0 - no, 1 - yes.
P<xsl:value-of select="@number+2"/>38 = 0		# Allow Auto Answer by Call-Info. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>39 = 0		# Turn off speaker on remote disconnect. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>49 = 0		# Check SIP User ID for incoming INVITE. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>69 = 0		# Refer-To Use Target Contact. 0 - no, 1 - yes
P<xsl:value-of select="@number+2"/>87 = 0		# Disable Multiple Media Attribute in SDP. 0 - no, 1 - yes
--> 
# Preferred Vocoder. 0 - PCMU, 2 - G.726-32, 3 - GSM, 4 - G.723.1, 8 - PCMA, 9 - G.722, 18 - G.729A/B, 98 - iLBC
P<xsl:value-of select="@number+2"/>51 = 18		# choice 1.   
P<xsl:value-of select="@number+2"/>52 = 18		# choice 2.  
P<xsl:value-of select="@number+2"/>53 = 18		# choice 3   
P<xsl:value-of select="@number+2"/>54 = 18		# choice 4.   
P<xsl:value-of select="@number+2"/>55 = 18		# choice 5. 
P<xsl:value-of select="@number+2"/>56 = 18		# choice 6.
P<xsl:value-of select="@number+2"/>57 = 4		# choice 7. 
P<xsl:value-of select="@number+2"/>58 = 9		# choice 8.
P<xsl:value-of select="@number+2"/>43 = 0		# SRTP Mode. 0 = Disabled/ 1 = Enabled but not forced / 2 = Enabled and forced
P<xsl:value-of select="@number+2"/>44 = 		# eventlist BLF URI N/A for GXP1200 and GXP280
P<xsl:value-of select="@number+2"/>24 = 100		# Special Feature.    100 - Standard, 101 - Nortel MCS, 102- Broadsoft, 108 - CBCOM, 109 - RNK, 110 - Sylantro.

</xsl:otherwise>
</xsl:choose>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>
