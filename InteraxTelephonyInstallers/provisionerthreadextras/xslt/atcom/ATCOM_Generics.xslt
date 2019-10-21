<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>
<xsl:template match="/"> 
&#60;&#60;VOIP CONFIG FILE&#62;&#62;Version:<xsl:value-of select="phone_config/general/file/@version"/>                           

&#60;GLOBAL CONFIG MODULE&#62;
Default Protocol   :2

&#60;LAN CONFIG MODULE&#62;
Lan Ip             :192.168.10.1
Lan NetMask        :255.255.255.0
Bridge Mode        :1
Time Zone          :16
Dtmf Payload Type  :101

<!--
Default Protocol   :4  IAX2
Static IP          :192.168.1.179
Static NetMask     :255.255.255.0
Static GateWay     :192.168.1.1
Primary DNS        :202.96.134.133
Alter DNS          :202.96.128.68
DHCP Mode          :1
DHCP Dns           :1
Domain Name        :
Host Name          :VOIP
Pppoe Mode         :0
HTL Start Port     :10000
HTL Port Number    :200
SNTP Server        :209.81.9.7
Enable SNTP        :1
Time Zone          :56
Enable Daylight    :0
SNTP Time Out      :60
DayLight Shift Min :60
DayLight Start Mon :3
DayLight Start Week:5
DayLight Start Wday:0
DayLight Start Hour:2
DayLight Start Min :0
DayLight End Mon   :10
DayLight End Week  :5
DayLight End Wday  :0
DayLight End Hour  :2
DayLight End Min   :0
MMI Set            :-1

&#60;LAN CONFIG MODULE&#62;
Lan Ip             :192.168.10.1
Lan NetMask        :255.255.255.0
Bridge Mode        :1
-->

--Dial Peer List-- :
Item1 Number       :*0
Item1 protocol     :2
Item1 address      :0.0.0.0
Item1 port         :5060
Item1 SipDomain    :
Item1 Alias        :
Item1 DelLen       :0
Item1 Suffix       :
Item1 type         :2

<!--
&#60;TELE CONFIG MODULE&#62;
Dial End With #    :0
Dial Fixed Length  :0
Fixed Length       :11
Dial With Timeout  :1
Dial Timeout value :5
Dialpeer With Line :0
Poll Sequence      :0
Accept Any Call    :1
Phone Prefix       :
Local Area Code    :
IP call network    :.
- -Port Config- -    :
P1 No Disturb      :0
P1 No Dial Out     :0
P1 No Empty Calling:0
P1 Enable CallerId :1
P1 Forward Service :0
P1 SIP TransNum    :
P1 SIP TransAddr   :
P1 SIP TransPort   :5060
P1 CallWaiting     :1
P1 CallTransfer    :1
P1 Call3Way        :1
P1 AutoAnswer      :0
P1 No Answer Time  :20
P1 Extention No.   :
P1 Hotline Num     :
P1 Record Server   :
P1 Enable Record   :0
P1 Auto Replace    :0
P1 Busy N/A Line   :0

&#60;DSP CONFIG MODULE&#62;

Signal Standard    :1
Handdown Time      :200
G729 Payload Length:1
G723 Bit Rate      :1
G722 Timestamps    :0
VAD                :0
Ring Type          :1
Dtmf Payload Type  :101
Disable Handfree   :0

Port Config   :
P1 Output Vol      :7
P1 Input Vol       :3
P1 HandFree Vol    :4
P1 RingTone Vol    :5
P1 Codec           :0
P1 Voice Record    :0
P1 Record Playing  :1
P1 UserDef Voice   :0
-->
&#60;DSP CONFIG MODULE&#62;
Port Config   :
<xsl:for-each select="phone_config/voip/dsp/codecs">
<xsl:if test= "@name='none'">P1 Codec     :-1</xsl:if>
<xsl:if test= "@name='g711alaw64k'">P1 Codec     :0</xsl:if>
<xsl:if test="@name='g711ulaw64k'">P1 Codec     :1</xsl:if>
<xsl:if test="@name='g722'">P1 Codec     :9</xsl:if>
<xsl:if test="@name='g723'">P1 Codec     :15</xsl:if>
<xsl:if test="@name='g729'">P1 Codec     :17</xsl:if>
</xsl:for-each>
<!--
P1 Second Codec    :15
P1 Third Codec     :17
P1 Forth Codec     : 1
P1 Fifth Codec     :-1
-->

&#60;SIP CONFIG MODULE&#62;
SIP  Port          :5060
Stun Address       :
Stun Port          :3478
Stun Effect Time   :50
SIP  Differv       :0
DTMF Mode          :1
Extern Address     :
Url Convert        :0
Quote Name         :0
Reg Retry Time     :30
Dtmf Info Convert  :1

--SIP Line List--  :
<xsl:for-each select="phone_config/voip/sip/accounts/account">

SIP<xsl:value-of select="@number"/> Phone Number  :<xsl:value-of select="@username"/>
SIP<xsl:value-of select="@number"/> Register Addr :<xsl:value-of select="@server_ip"/>
SIP<xsl:value-of select="@number"/> Register Port :<xsl:value-of select="@server_port"/>
SIP<xsl:value-of select="@number"/> Register User :<xsl:value-of select="@username"/>
SIP<xsl:value-of select="@number"/> Register Pwd  :<xsl:value-of select="@password"/>
SIP<xsl:value-of select="@number"/> Register TTL  :60
SIP<xsl:value-of select="@number"/> Enable Reg    :1
SIP<xsl:value-of select="@number"/> Proxy Addr    :<xsl:value-of select="@server_ip"/>
SIP<xsl:value-of select="@number"/> Proxy Port    :<xsl:value-of select="@server_port"/>
SIP<xsl:value-of select="@number"/> Proxy User    :<xsl:value-of select="@username"/>
SIP<xsl:value-of select="@number"/> Proxy Pwd     :<xsl:value-of select="@password"/>
SIP<xsl:value-of select="@number"/> Signal Enc    :0
SIP<xsl:value-of select="@number"/> Signal Key    :
SIP<xsl:value-of select="@number"/> Media Enc     :0
SIP<xsl:value-of select="@number"/> Media Key     :
SIP<xsl:value-of select="@number"/> Local Domain  :
SIP<xsl:value-of select="@number"/> Fwd Service   :0
SIP<xsl:value-of select="@number"/> Fwd Number    :
SIP<xsl:value-of select="@number"/> Enable Detect :0
SIP<xsl:value-of select="@number"/> Detect TTL    :60
SIP<xsl:value-of select="@number"/> Server Type   :0
SIP<xsl:value-of select="@number"/> User Agent    :Voip Phone 1.0
SIP<xsl:value-of select="@number"/> PRACK         :0
SIP<xsl:value-of select="@number"/> KEEP AUTH     :0
SIP<xsl:value-of select="@number"/> Session Timer :0
SIP<xsl:value-of select="@number"/> DTMF Mode     :1
SIP<xsl:value-of select="@number"/> Use Stun      :0
SIP<xsl:value-of select="@number"/> Via Port      :1
SIP<xsl:value-of select="@number"/> Subscribe     :0
SIP<xsl:value-of select="@number"/> Sub Expire    :300
SIP<xsl:value-of select="@number"/> Single Codec  :0
SIP<xsl:value-of select="@number"/> CLIR          :0
SIP<xsl:value-of select="@number"/> DNS SRV       :0
SIP<xsl:value-of select="@number"/> Ban Anonymous :0
SIP<xsl:value-of select="@number"/> Dial Without R:0
SIP<xsl:value-of select="@number"/> RFC Ver       :1
SIP<xsl:value-of select="@number"/> Signal Port   :5060
SIP<xsl:value-of select="@number"/> Transport     :0
SIP<xsl:value-of select="@number"/> Use Mixer     :0






<!--
SIP<xsl:value-of select="@number"/> Display Name  :
SIP<xsl:value-of select="@number"/> Sip Name      :
SIP<xsl:value-of select="@number"/> Proxy Addr    :192.168.3.96
SIP<xsl:value-of select="@number"/> Proxy Port    :5060
SIP<xsl:value-of select="@number"/> Proxy User    :501
SIP<xsl:value-of select="@number"/> Proxy Pwd     :1234
SIP<xsl:value-of select="@number"/> Signal Enc    :0
SIP<xsl:value-of select="@number"/> Signal Key    :
SIP<xsl:value-of select="@number"/> Media Enc     :0
SIP<xsl:value-of select="@number"/> Media Key     :
SIP<xsl:value-of select="@number"/> Local Domain  :
SIP<xsl:value-of select="@number"/> Fwd Service   :0
SIP<xsl:value-of select="@number"/> Ring Type     :0
SIP<xsl:value-of select="@number"/> Fwd Number    :
SIP<xsl:value-of select="@number"/> Hotline Number:
SIP<xsl:value-of select="@number"/> Enable Detect :0
SIP<xsl:value-of select="@number"/> Detect TTL    :60
SIP<xsl:value-of select="@number"/> Server Type   :0
SIP<xsl:value-of select="@number"/> User Agent    :Voip Phone 1.0
SIP<xsl:value-of select="@number"/> PRACK         :0
SIP<xsl:value-of select="@number"/> KEEP AUTH     :0
SIP<xsl:value-of select="@number"/> Session Timer :0
SIP<xsl:value-of select="@number"/> Gruu          :0
SIP<xsl:value-of select="@number"/> DTMF Mode     :1
SIP<xsl:value-of select="@number"/> Use Stun      :0
SIP<xsl:value-of select="@number"/> Via Port      :1
SIP<xsl:value-of select="@number"/> Subscribe     :0
SIP<xsl:value-of select="@number"/> Sub Expire    :300
SIP<xsl:value-of select="@number"/> Single Codec  :0
SIP<xsl:value-of select="@number"/> CLIR          :0
SIP<xsl:value-of select="@number"/> Strict Proxy  :0
SIP<xsl:value-of select="@number"/> Direct Contact:0
SIP<xsl:value-of select="@number"/> History Info  :0
SIP<xsl:value-of select="@number"/> DNS SRV       :0
SIP<xsl:value-of select="@number"/> Transfer Expir:0
SIP<xsl:value-of select="@number"/> Ban Anonymous :0
SIP<xsl:value-of select="@number"/> Dial Without R:0
SIP<xsl:value-of select="@number"/> DisplayName Qu:0
SIP<xsl:value-of select="@number"/> RFC Ver       :1
SIP<xsl:value-of select="@number"/> Signal Port   :5060
SIP<xsl:value-of select="@number"/> Transport     :0
SIP<xsl:value-of select="@number"/> Use Mixer     :0
SIP<xsl:value-of select="@number"/> Mixer Uri     :
SIP<xsl:value-of select="@number"/> Long Contact  :0
SIP<xsl:value-of select="@number"/> Auto TCP      :0
SIP<xsl:value-of select="@number"/> Click to Talk :0
SIP<xsl:value-of select="@number"/> Mwi No.       :
SIP<xsl:value-of select="@number"/> Park No.      :
SIP<xsl:value-of select="@number"/> Help No.      :
-->
</xsl:for-each>


&#60;IAX2 CONFIG MODULE&#62;
Server   Address   :
Server   Port      :4569
User     Name      :
User     Password  :
User     Number    :
Voice    Number    :0
Voice    Text      :mail
EchoTest Number    :
EchoTest Text      :echo
Local    Port      :4569
Enable   Register  :0
Refresh  Time      :60
Enable   G.729     :0
<!--
Voice    Number    :0
Voice    Text      :mail
EchoTest Number    :1
EchoTest Text      :echo
Local    Port      :4569

</xsl:for-each>
-->

<!--
&#60;PPPoE CONFIG MODULE&#62;
Pppoe User         :user123
Pppoe Password     :password
Pppoe Service      :ANY
Pppoe Ip Address   :

&#60;MMI CONFIG MODULE&#62;
Telnet Port        :23
Web Port           :80
Remote Control     :1
Enable MMI Filter  :0
Telnet Prompt      :
- -MMI Account- -    :
Account1 Name      :admin
Account1 Pass      :admin
Account1 Level     :10
Account2 Name      :guest
Account2 Pass      :guest
Account2 Level     :5

&#60;QOS CONFIG MODULE&#62;
Enable VLAN        :0
Enable diffServ    :0
DiffServ Value     :184
VLAN ID            :256
802.1P Value       :0
VLAN Recv Check    :1
Data VLAN ID       :254
Data 802.1P Value  :0
Diff Data Voice    :0
Enable PVID        :0
PVID Value         :0

&#60;DEBUG CONFIG MODULE&#62;
MGR Trace Level    :0
SIP Trace Level    :0
IAX Trace Level    :0
Trace File Info    :0

&#60;AAA CONFIG MODULE&#62;
Enable Syslog      :0
Syslog address     :0.0.0.0
Syslog port        :514

&#60;ACCESS CONFIG MODULE&#62;
Enable In Access   :0
Enable Out Access  :0

&#60;DHCP CONFIG MODULE&#62;
Enable DHCP Server :1
Enable DNS Relay   :1
DHCP Update Flag   :0
TFTP  Server       :0.0.0.0
- -DHCP List- -      :
Item1 name         :lan
Item1 Start Ip     :192.168.10.1
Item1 End Ip       :192.168.10.30
Item1 Param        :snmk=255.255.255.0:maxl=1440:rout=192.168.10.1:dnsv=192.168.10.1

&#60;NAT CONFIG MODULE&#62;
Enable Nat         :1
Enable Ftp ALG     :1
Enable H323 ALG    :0
Enable PPTP ALG    :1
Enable IPSec ALG   :1

&#60;PHONE CONFIG MODULE&#62;
Keypad Password    :123
LCD Logo           :VOIP PHONE
MWI Number         :
Time 12hours       :0
Memory Key 1       :
Memory Key 2       :
Memory Key 3       :
Memory Key 4       :
Memory Key 5       :
Memory Key 6       :
Memory Key 7       :
Memory Key 8       :
Memory Key 9       :
Memory Key 10      :
-->
&#60;DHCP CONFIG MODULE&#62;
Enable DHCP Server :1
Enable DNS Relay   :1
DHCP Update Flag   :0
TFTP  Server       :<xsl:value-of select="phone_config/auto_provisioning/@server_ip"/>

&#60;PHONE CONFIG MODULE&#62;
Keypad Password    :123
LCD Logo           :<xsl:value-of select="phone_config/general/display/@name"/>
MWI Number         :
Time 12hours       :0
Memory Key 1       :
Memory Key 2       :
Memory Key 3       :
Memory Key 4       :
Memory Key 5       :
Memory Key 6       :
Memory Key 7       :
Memory Key 8       :
Memory Key 9       :
Memory Key 10      :

&#60;AUTOUPDATE CONFIG MODULE&#62;
Download Username  :
Download password  :
Download Server IP :<xsl:value-of select="phone_config/auto_provisioning/@server_ip"/>
Config File Name   :
Config File Key    :<xsl:value-of select="phone_config/general/file/@encryption_key"/>
Download Protocol  :2
Download Mode      :2
Download Interval  :<xsl:value-of select="phone_config/auto_provisioning/@update_interval"/>
<!--
&#60;VPN CONFIG MODULE&#62;
VPN mode           :0
L2TP LNS IP        :
L2TP User Name     :
L2TP Password      :
Enable VPN Tunnel  :0
VPN Server IP      :0.0.0.0
VPN Server Port    :80
Server Group ID    :VPN
Server Area Code   :12345
-->

&#60;&#60;END OF FILE&#62;&#62;
</xsl:template>
</xsl:stylesheet>
