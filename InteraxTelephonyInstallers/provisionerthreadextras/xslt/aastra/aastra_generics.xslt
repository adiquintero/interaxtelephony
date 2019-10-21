<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>
<xsl:template match="/">
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
<xsl:choose>
    <xsl:when test="phone_config/voip/dsp/codecs/codec/@number ='1'">
<xsl:if test= "@name='None'">sip customized codec: payload=-1; ptime=10; silsupp=on</xsl:if>
<xsl:if test= "@name='All'">sip customized codec: payload=-3; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='Basic'">sip customized codec: payload=-2; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.722'">sip customized codec: payload=9; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711u (8K)'">sip customized codec: payload=0; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711u (16K)'">sip customized codec: payload=110; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711a (8K)'">sip customized codec: payload=8; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711a (16K)'">sip customized codec: payload=111; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.729'">sip customized codec: payload=18; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-16'">sip customized codec: payload=98; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-24'">sip customized codec: payload=97; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-32'">sip customized codec: payload=115; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-40'">sip customized codec: payload=96; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='BV16 (8K)'">sip customized codec: payload=106; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='BV32 (16K)'">sip customized codec: payload=107; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='L16 (8K)'">sip customized codec: payload=112; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='L16 (16K)'">sip customized codec: payload=113; ptime=10; silsupp=on</xsl:if>
</xsl:when>
<xsl:otherwise>
<xsl:if test= "@name='None'">,sip customized codec: payload=-1; ptime=10; silsupp=on</xsl:if>
<xsl:if test= "@name='All'">,sip customized codec: payload=-3; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='Basic'">,sip customized codec: payload=-2; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.722'">,sip customized codec: payload=9; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711u (8K)'">,sip customized codec: payload=0; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711u (16K)'">,sip customized codec: payload=110; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711a (8K)'">,sip customized codec: payload=8; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.711a (16K)'">,sip customized codec: payload=111; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.729'">,sip customized codec: payload=18; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-16'">,sip customized codec: payload=98; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-24'">,sip customized codec: payload=97; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-32'">,sip customized codec: payload=115; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='G.726-40'">,sip customized codec: payload=96; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='BV16 (8K)'">,sip customized codec: payload=106; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='BV32 (16K)'">,sip customized codec: payload=107; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='L16 (8K)'">,sip customized codec: payload=112; ptime=10; silsupp=on</xsl:if>
<xsl:if test="@name='L16 (16K)'">,sip customized codec: payload=113; ptime=10; silsupp=on</xsl:if>
</xsl:otherwise>
</xsl:choose>	
#Line Settings <xsl:for-each select="phone_config/voip/sip/accounts/account">
sip line<xsl:value-of select="@number"/> auth name:<xsl:value-of select="@username"/>      # SIP Registrar request authorization name.
sip line<xsl:value-of select="@number"/> password:<xsl:value-of select="@password"/>        # SIP Registrar request passcode.
sip line<xsl:value-of select="@number"/> user name:<xsl:value-of select="@username"/>      
sip line<xsl:value-of select="@number"/> display name:<xsl:value-of select="@username"/>  # Name used for SIP messages.
sip line<xsl:value-of select="@number"/> screen name:<xsl:value-of select="/phone_config/general/display/@name"/>   # User's name seen on the idle screen of the user's phone.
sip line<xsl:value-of select="@number"/> proxy ip:<xsl:value-of select="@server_ip"/>
sip line<xsl:value-of select="@number"/> proxy port:<xsl:value-of select="@server_port"/>
sip line<xsl:value-of select="@number"/> registrar ip:<xsl:value-of select="@server_ip"/>
sip line<xsl:value-of select="@number"/> registrar port:<xsl:value-of select="@server_port"/>
sip line<xsl:value-of select="@number"/> mode: 0

</xsl:for-each>
auto resync mode:<xsl:value-of select="phone_config/voip/auto_provisioning/@update_mode"/>
auto resync time:<xsl:value-of select="phone_config/voip/auto_provisioning/@time"/>
download protocol:<xsl:value-of select="phone_config/voip/auto_provisioning/@protocol"/>
<!--
#  Softkey Speed Dials
<xsl:for-each select="phone_config/voip/sip/accounts/account">
softkey<xsl:value-of select="@number"/> type: speeddial
softkey<xsl:value-of select="@number"/> label: "Boss"
softkey<xsl:value-of select="@number"/> value: 1234
softkey<xsl:value-of select="@number"/> states: idle
</xsl:for-each>

# audio adjust 
headset tx gain: 0
headset sidetone gain: 0
handset tx gain: 0
handset sidetone gain: 0
handsfree tx gain: 0

# Example 1 - handset tx gain: -5	(decrease the transmitted audio gain by 5 db)
# Example 2 - handset sidetone gain: 5	(increase the local sidetone by 5 db)

# XML 
xml application URI: http://XX.XXX.X.XXX/test.xml	# full uri of xml file
xml application post list:                 # ip address of server/s
xml application title: test.xml			# customises the name displayed under the services menu
-->
</xsl:template>
</xsl:stylesheet>
