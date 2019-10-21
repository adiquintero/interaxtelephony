<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>
<xsl:template match="/">
#DHCP Setting
dhcp: 1                            # DHCP enabled.

upgrade file name:<xsl:value-of select="file_name"/>
upgrade ip address:<xsl:value-of select="server_ip"/>
upgrade uri: tftp://192.168.3.96
model: <xsl:value-of select="phone_config/voip/sip/accounts/account/@product_number"/>
version: 2.4.1.37
firmware md5: 88abe6cf4e857af27b02f30d2f7edb5e
tftp server: 192.168.3.96
alternate tftp server: 192.168.3.96
use alternate tftp: 1
                 
#Additional Network Settings:
sip registration time: 300        # Eg. every 300 seconds, a re-register  request is sent to the SIP server.
sip rtp port: 3000                # Eg. RTP packets are sent to port 3000.
sip silence suppression: 2         # "0" = off, "1" = on, "2" = default
sip digit timeout: 4
sip dial plan: "[2-9]11|9911|1[01]xx|[2-9]xxxxxx|1[2-9]xxxxxxxxx|011x+#|xx*|*xx+#|xxxxxxxxxxxxxxxxx#"

#SIP registrar and Proxy Server Settings
sip proxy ip:<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_ip"/>                    # IP of proxy server.
sip proxy port:<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_port"/>            # 5060 is set by default.
sip registrar ip:<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_ip"/>                # IP of registrar.
sip registrar port:<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_port"/>          # 5060 is set by default.
sip digit timeout: 6             
sip registration period:3600
sip nortel nat support:0
sip nortel nat timer:0
sip broadsoft talk:0
sip broadsoft hold:0
sip broadsoft conference:0
sip dial plan: "x+#""
</xsl:template>
</xsl:stylesheet>
