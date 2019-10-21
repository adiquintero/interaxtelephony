<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"> <xsl:output method="xml" version="1.0"/>
<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
<xsl:template match="/">
&lt;!-- Per-phone Configuration File --&gt;
&lt;!--  Revision: <xsl:value-of select="phone_config/general/file/@version"/>   --&gt;
&lt;POLYCOM<xsl:value-of select="translate(phone_config/general/file/@name, $uppercase, $smallcase)"/>&gt; 
  &lt;reg reg.1.displayName="<xsl:value-of select="phone_config/voip/sip/accounts/account/@username"/>" reg.1.address="<xsl:value-of select="phone_config/general/display/@name"/>" reg.1.label="" reg.1.type="private" reg.1.thirdPartyName="" reg.1.auth.userId="<xsl:value-of select="phone_config/voip/sip/accounts/account/@username"/>" reg.1.auth.password="<xsl:value-of select="phone_config/voip/sip/accounts/account/@password"/>" reg.1.ringType="2" reg.1.lineKeys="" reg.1.callsPerLineKey="" reg.1.lcs="" reg.1.outboundProxy.address="" reg.1.outboundProxy.port="" reg.1.outboundProxy.transport="DNSnaptr" reg.1.server.1.address="<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_ip"/>" reg.1.server.1.port="<xsl:value-of select="phone_config/voip/sip/accounts/account/@server_port"/>" reg.1.server.1.transport="DNSnaptr" reg.1.server.1.expires="" reg.1.server.1.expires.overlap="60" reg.1.server.1.register="" reg.1.server.1.retryTimeOut="" reg.1.server.1.retryMaxCount="" reg.1.server.1.expires.lineSeize="" reg.1.server.2.address="" reg.1.server.2.port="" reg.1.server.2.transport="DNSnaptr" reg.1.server.2.expires="" reg.1.server.2.expires.overlap="60" reg.1.server.2.register="" reg.1.server.2.retryTimeOut="" reg.1.server.2.retryMaxCount="" reg.1.server.2.expires.lineSeize="" reg.1.acd-login-logout="0" reg.1.acd-agent-available="0"/&gt;  
  &lt;call&gt; 
    &lt;donotdisturb call.donotdisturb.perReg="0"/&gt;  
    &lt;autoOffHook call.autoOffHook.1.enabled="0" call.autoOffHook.1.contact=""/&gt;  
    &lt;serverMissedCall call.serverMissedCall.1.enabled="0"/&gt; 
  &lt;/call&gt;  
  &lt;divert divert.1.contact="" divert.1.autoOnSpecificCaller="1" divert.1.sharedDisabled="1"&gt; 
    &lt;fwd divert.fwd.1.enabled="0"/&gt;  
    &lt;busy divert.busy.1.enabled="1" divert.busy.1.contact=""/&gt;  
    &lt;noanswer divert.noanswer.1.enabled="1" divert.noanswer.1.timeout="60" divert.noanswer.1.contact=""/&gt;  
    &lt;dnd divert.dnd.1.enabled="0" divert.dnd.1.contact=""/&gt; 
  &lt;/divert&gt;  
  &lt;dialplan dialplan.1.impossibleMatchHandling="0" dialplan.1.removeEndOfDial="1"&gt; 
    &lt;digitmap dialplan.1.digitmap="" dialplan.1.digitmap.timeOut=""/&gt;  
    &lt;routing&gt; 
      &lt;server dialplan.1.routing.server.1.address="" dialplan.1.routing.server.1.port=""/&gt;  
      &lt;emergency dialplan.1.routing.emergency.1.value="" dialplan.1.routing.emergency.1.server.1="1"/&gt; 
    &lt;/routing&gt; 
  &lt;/dialplan&gt;  
  &lt;msg msg.bypassInstantMessage="0"&gt; 
    &lt;mwi msg.mwi.1.subscribe="" msg.mwi.1.callBackMode="" msg.mwi.1.callBack=""/&gt; 
  &lt;/msg&gt;  
  &lt;nat nat.ip="" nat.signalPort="" nat.mediaPortStart=""/&gt;  
  &lt;attendant attendant.uri="" attendant.reg=""/&gt;  
  &lt;roaming_buddies roaming_buddies.reg=""/&gt;  
  &lt;roaming_privacy roaming_privacy.reg=""/&gt; 
&lt;/POLYCOM<xsl:value-of select="translate(phone_config/general/file/@name, $uppercase, $smallcase)"/>&gt;
</xsl:template>
</xsl:stylesheet>
