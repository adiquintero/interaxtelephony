<?xml version="1.0" standalone="yes"?>
<xsl:stylesheet version="1.0"
   xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="xml"/>
<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
<xsl:template match="/">
&lt;APPLICATION APP_FILE_PATH="sip.ld" CONFIG_FILES="POLYCOM<xsl:value-of select="translate(phone_config/general/file/@name, $uppercase, $smallcase)"/>.cfg, sip.cfg" MISC_FILES="" LOG_FILE_DIRECTORY="" OVERRIDES_DIRECTORY="" CONTACTS_DIRECTORY=""/&gt;
</xsl:template>
</xsl:stylesheet>