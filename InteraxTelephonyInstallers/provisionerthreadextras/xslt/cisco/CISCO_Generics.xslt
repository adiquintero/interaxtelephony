<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>

<xsl:template match="/"> 

# SIP Configuration File
    # 7940 w/2 lines

<xsl:for-each select="phone_config/voip/sip/accounts/account">
    # Line <xsl:value-of select="@number"/> Parameters
    line<xsl:value-of select="@number"/>_name: "<xsl:value-of select="@username"/>"
    line<xsl:value-of select="@number"/>_shortname: "<xsl:value-of select="@username"/>"
    line<xsl:value-of select="@number"/>_authname: "<xsl:value-of select="@username"/>"
    line<xsl:value-of select="@number"/>_displayname: "<xsl:value-of select="@username"/>"
    line<xsl:value-of select="@number"/>_password: "<xsl:value-of select="@password"/>"
 </xsl:for-each>
<!--
    # Phone Label (Text desired to be displayed in upper right corner)
    # Has no effect on SIP messaging
    phone_label: ""
 
    # Remote Access Parameters for console or telnet login
    phone_prompt:   "SIP Phone"
    phone_password: "secretpassword"
    user_info: none
-->

</xsl:template>
</xsl:stylesheet>
