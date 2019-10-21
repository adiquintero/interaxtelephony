<?xml version="1.0" encoding="ISO-8859-1"?> <xsl:stylesheet version = '1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform'> <xsl:output method="text"/>
<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
   <xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
<xsl:template match="/"> 
# *** 
# *** SPA3102 5.1.10(GW) Configuration Parameters
# *** 


# *** System Configuration

Restricted_Access_Domains                       "" ;
Enable_Web_Admin_Access                         "Yes" ;
Admin_Passwd                                    "" ;
User_Password                                 ! "" ;

# *** Internet Connection Settings

Connection_Type                               ! "DHCP" ;  

# ***  Static IP Settings

Static_IP                                     ! "" ;
NetMask                                       ! "" ;
Gateway                                       ! "" ;

# ***  PPPoE Settings

PPPOE_Login_Name                              ! "" ;
PPPOE_Login_Password                          ! "" ;
PPPOE_Service_Name                            ! "" ;

# ***  Optional Settings

HostName                                      ! "" ;
Domain                                        ! "" ;
Primary_DNS                                   ! "" ;
Secondary_DNS                                 ! "" ;
DNS_Server_Order                                "Manual" ;  
DNS_Query_Mode                                  "Parallel" ;  
Primary_NTP_Server                              "" ;
Secondary_NTP_Server                            "" ;
DHCP_IP_Revalidate_Timer                        "0" ;
Networking_Service                            ! "NAT" ; 
Auto_NetService_Private_IP_Ranges             ! "10.0.0.0-10.255.255.255,192.168.0.0-192.168.255.255,172.16.0.0-172.31.255.255" ;

# *** LAN Network Settings

LAN_IP_Address                                ! "192.168.0.1" ;
LAN_Subnet_Mask                               ! "255.255.255.0" ;  

# *** 

Enable_DHCP_Server                            ! "Yes" ;
DHCP_Lease_Time                               ! "24" ;
DHCP_Client_Starting_IP_Address               ! "2" ;
Number_of_Client_IP_Addresses                 ! "50" ;

# *** Static DHCP Lease Settings


# *** MAC Clone Settings

Enable_MAC_Clone_Service                      ! "No" ;
Cloned_MAC_Address                            ! "" ;

# *** Remote Management

Enable_WAN_Web_Server                          "Yes" ;
WAN_Web_Server_Port                            "80" ;

# *** QOS Settings

QOS_Policy                                      "Always On" ;  
QOS_QDisc                                     ! "NONE" ; 
Maximum_Uplink_Speed                          ! "128" ;

# *** VLAN Settings

Enable_VLAN                                   ! "No" ;
VLAN_ID                                       ! "1" ;

# *** Miscellaneous Settings

Syslog_Server                                   "" ;
Debug_Server                                    "" ;
Debug_Level                                     "0" ;  

# *** Product Information


# *** System Status


# *** Configuration Profile

Provision_Enable                                "Yes" ;
Resync_On_Reset                                 "Yes" ;
Resync_Random_Delay                             "2" ;
Resync_Periodic                   				"<xsl:value-of select="phone_config/auto_provisioning/@update_interval"/>" ;
Resync_Error_Retry_Delay                        "3600" ;
Forced_Resync_Delay                             "14400" ;
Resync_From_SIP                                 "Yes" ;
Resync_After_Upgrade_Attempt                    "Yes" ;
Resync_Trigger_1                                "" ;
Resync_Trigger_2                                "" ;
Resync_Fails_On_FNF                             "Yes" ;
Profile_Rule                                    "tftp://<xsl:value-of select="/phone_config/auto_provisioning/@server_ip"/>/tftpboot/<xsl:value-of select="phone_config/general/file/@name"/>.cfg" ;
Profile_Rule_B                                  "" ;
Profile_Rule_C                                  "" ;
Profile_Rule_D                                  "" ;
Log_Resync_Request_Msg                          "$PN $MAC -- Requesting resync $SCHEME://$SERVIP:$PORT$PATH" ;
Log_Resync_Success_Msg                          "$PN $MAC -- Successful resync $SCHEME://$SERVIP:$PORT$PATH" ;
Log_Resync_Failure_Msg                          "$PN $MAC -- Resync failed: $ERR" ;
Report_Rule                                     "" ;

# *** Firmware Upgrade

Upgrade_Enable                                  "No" ;
Upgrade_Error_Retry_Delay                       "3600" ;
Downgrade_Rev_Limit                             "" ;
Upgrade_Rule                                    "" ;
Log_Upgrade_Request_Msg                         "$PN $MAC -- Requesting upgrade $SCHEME://$SERVIP:$PORT$PATH" ;
Log_Upgrade_Success_Msg                         "$PN $MAC -- Successful upgrade $SCHEME://$SERVIP:$PORT$PATH -- $ERR" ;
Log_Upgrade_Failure_Msg                         "$PN $MAC -- Upgrade failed: $ERR" ;
License_Keys                                    "" ;
Recovery_URL                                    "" ;

# *** General Purpose Parameters

GPP_A                                           "" ;
GPP_B                                           "" ;
GPP_C                                           "" ;
GPP_D                                           "" ;
GPP_E                                           "" ;
GPP_F                                           "" ;
GPP_G                                           "" ;
GPP_H                                           "" ;
GPP_I                                           "" ;
GPP_J                                           "" ;
GPP_K                                           "" ;
GPP_L                                           "" ;
GPP_M                                           "" ;
GPP_N                                           "" ;
GPP_O                                           "" ;
GPP_P                                           "" ;
GPP_SA                                          "" ;
GPP_SB                                          "" ;
GPP_SC                                          "" ;
GPP_SD                                          "" ;

# *** SIP Parameters

Max_Forward                                     "70" ;
Max_Redirection                                 "5" ;
Max_Auth                                        "2" ;
SIP_User_Agent_Name                             "$VERSION" ;
SIP_Server_Name                                 "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@server_ip"/>" ;
SIP_Reg_User_Agent_Name                         "" ;
SIP_Accept_Language                             "" ;
DTMF_Relay_MIME_Type                            "application/dtmf-relay" ;
Hook_Flash_MIME_Type                            "application/hook-flash" ;
Remove_Last_Reg                                 "No" ;
Use_Compact_Header                              "No" ;
Escape_Display_Name                             "No" ;
RFC_2543_Call_Hold                              "Yes" ;
Mark_All_AVT_Packets                            "Yes" ;
SIP_TCP_Port_Min                                "5060" ;
SIP_TCP_Port_Max                                "5080" ;

# *** SIP Timer Values (sec)

SIP_T1                                          ".5" ;
SIP_T2                                          "4" ;
SIP_T4                                          "5" ;
SIP_Timer_B                                     "32" ;
SIP_Timer_F                                     "32" ;
SIP_Timer_H                                     "32" ;
SIP_Timer_D                                     "32" ;
SIP_Timer_J                                     "32" ;
INVITE_Expires                                  "240" ;
ReINVITE_Expires                                "30" ;
Reg_Min_Expires                                 "1" ;
Reg_Max_Expires                                 "7200" ;
Reg_Retry_Intvl                                 "30" ;
Reg_Retry_Long_Intvl                            "1200" ;
Reg_Retry_Random_Delay                          "" ;
Reg_Retry_Long_Random_Delay                     "" ;
Reg_Retry_Intvl_Cap                             "" ;

# *** Response Status Code Handling

SIT1_RSC                                        "" ;
SIT2_RSC                                        "" ;
SIT3_RSC                                        "" ;
SIT4_RSC                                        "" ;
Try_Backup_RSC                                  "" ;
Retry_Reg_RSC                                   "" ;

# *** RTP Parameters

RTP_Port_Min                                    "16384" ;
RTP_Port_Max                                    "16482" ;
RTP_Packet_Size                                 "0.030" ;
Max_RTP_ICMP_Err                                "0" ;
RTCP_Tx_Interval                                "0" ;
No_UDP_Checksum                                 "No" ;
Stats_In_BYE                                    "No" ;

# *** SDP Payload Types

NSE_Dynamic_Payload                             "100" ;
AVT_Dynamic_Payload                             "101" ;
INFOREQ_Dynamic_Payload                         "" ;
G726r16_Dynamic_Payload                         "98" ;
G726r24_Dynamic_Payload                         "97" ;
G726r32_Dynamic_Payload                         "2" ;
G726r40_Dynamic_Payload                         "96" ;
G729b_Dynamic_Payload                           "99" ;
EncapRTP_Dynamic_Payload                        "112" ;
RTP-Start-Loopback_Dynamic_Payload              "113" ;
RTP-Start-Loopback_Codec                        "G729a" ;  
NSE_Codec_Name                                  "NSE" ;
AVT_Codec_Name                                  "telephone-event" ;
G711u_Codec_Name                                "PCMU" ;
G711a_Codec_Name                                "PCMA" ;
G726r16_Codec_Name                              "G726-16" ;
G726r24_Codec_Name                              "G726-24" ;
G726r32_Codec_Name                              "G726-32" ;
G726r40_Codec_Name                              "G726-40" ;
G729a_Codec_Name                                "G729a" ;
G729b_Codec_Name                                "G729ab" ;
G723_Codec_Name                                 "G723" ;
EncapRTP_Codec_Name                             "encaprtp" ;

# *** NAT Support Parameters

Handle_VIA_received                             "No" ;
Handle_VIA_rport                                "No" ;
Insert_VIA_received                             "No" ;
Insert_VIA_rport                                "No" ;
Substitute_VIA_Addr                             "No" ;
Send_Resp_To_Src_Port                           "No" ;
STUN_Enable                                     "No" ;
STUN_Test_Enable                                "No" ;
STUN_Server                                     "" ;
TURN_Server                                     "" ;
Auth_Server                                     "" ;
EXT_IP                                          "" ;
EXT_RTP_Port_Min                                "" ;
NAT_Keep_Alive_Intvl                            "15" ;


<xsl:for-each select="phone_config/voip/sip/accounts/account">
# *** 

Line_Enable[<xsl:value-of select="@number"/>] 					"Yes" ;

# *** Streaming Audio Server (SAS)

SAS_Enable[<xsl:value-of select="@number"/>]   		 			"No" ;
SAS_DLG_Refresh_Intvl[<xsl:value-of select="@number"/>]				"30" ;
SAS_Inbound_RTP_Sink[<xsl:value-of select="@number"/>]                         	"" ;

# *** NAT Settings

NAT_Mapping_Enable[<xsl:value-of select="@number"/>]                           "No" ;
NAT_Keep_Alive_Enable[<xsl:value-of select="@number"/>]                        "No" ;
NAT_Keep_Alive_Msg[<xsl:value-of select="@number"/>]                           "$NOTIFY" ;
NAT_Keep_Alive_Dest[<xsl:value-of select="@number"/>]                          "$PROXY" ;

# *** Network Settings

SIP_ToS/DiffServ_Value[<xsl:value-of select="@number"/>]                       "0x68" ;
SIP_CoS_Value[<xsl:value-of select="@number"/>]                                "3" ;
RTP_ToS/DiffServ_Value[<xsl:value-of select="@number"/>]                       "0xb8" ;
RTP_CoS_Value[<xsl:value-of select="@number"/>]                                "6" ;
Network_Jitter_Level[<xsl:value-of select="@number"/>]                         "high" ;  
Jitter_Buffer_Adjustment[<xsl:value-of select="@number"/>]                     "up and down" ;  

# *** SIP Settings

SIP_Transport[<xsl:value-of select="@number"/>]                                "UDP" ;  
SIP_Port[<xsl:value-of select="@number"/>]                                     "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@server_port"/>" ;
SIP_100REL_Enable[<xsl:value-of select="@number"/>]                            "No" ;
EXT_SIP_Port[<xsl:value-of select="@number"/>]                                 "" ;
Auth_Resync-Reboot[<xsl:value-of select="@number"/>]                           "Yes" ;
SIP_Proxy-Require[<xsl:value-of select="@number"/>]                            "" ;
SIP_Remote-Party-ID[<xsl:value-of select="@number"/>]                          "Yes" ;
SIP_GUID[<xsl:value-of select="@number"/>]                                     "No" ;
SIP_Debug_Option[<xsl:value-of select="@number"/>]                             "none" ;  
RTP_Log_Intvl[<xsl:value-of select="@number"/>]                                "0" ;
Restrict_Source_IP[<xsl:value-of select="@number"/>]                           "No" ;
Referor_Bye_Delay[<xsl:value-of select="@number"/>]                            "4" ;
Refer_Target_Bye_Delay[<xsl:value-of select="@number"/>]                       "0" ;
Referee_Bye_Delay[<xsl:value-of select="@number"/>]                            "0" ;
Refer-To_Target_Contact[<xsl:value-of select="@number"/>]                      "No" ;
Sticky_183[<xsl:value-of select="@number"/>]                                   "No" ;
Auth_INVITE[<xsl:value-of select="@number"/>]                                  "No" ;
Reply_182_On_Call_Waiting[<xsl:value-of select="@number"/>]                    "No" ;

# *** Call Feature Settings

Blind_Attn-Xfer_Enable[<xsl:value-of select="@number"/>]                       "No" ;
MOH_Server[<xsl:value-of select="@number"/>]                                   "" ;
Xfer_When_Hangup_Conf[<xsl:value-of select="@number"/>]                        "Yes" ;

# *** Proxy and Registration

Proxy[<xsl:value-of select="@number"/>]                                        "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@server_ip"/>" ;
Outbound_Proxy[<xsl:value-of select="@number"/>]                               "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@server_ip"/>" ;
Use_Outbound_Proxy[<xsl:value-of select="@number"/>]                           "No" ;
Use_OB_Proxy_In_Dialog[<xsl:value-of select="@number"/>]                       "Yes" ;
Register[<xsl:value-of select="@number"/>]                                     "Yes" ;
Make_Call_Without_Reg[<xsl:value-of select="@number"/>]                        "No" ;
Register_Expires[<xsl:value-of select="@number"/>]                             "3600" ;
Ans_Call_Without_Reg[<xsl:value-of select="@number"/>]                         "No" ;
Use_DNS_SRV[<xsl:value-of select="@number"/>]                                  "No" ;
DNS_SRV_Auto_Prefix[<xsl:value-of select="@number"/>]                          "No" ;
Proxy_Fallback_Intvl[<xsl:value-of select="@number"/>]                         "3600" ;
Proxy_Redundancy_Method[<xsl:value-of select="@number"/>]                      "Normal" ;  
Voice_Mail_Server[<xsl:value-of select="@number"/>]                            "" ;
Mailbox_Subscribe_Expires[<xsl:value-of select="@number"/>]                    "2147483647" ;

# *** Subscriber Information

Display_Name[<xsl:value-of select="@number"/>]                                 "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@username"/>" ;
User_ID[<xsl:value-of select="@number"/>]                                      "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@username"/>" ;
Password[<xsl:value-of select="@number"/>]                                     "<xsl:value-of select="/phone_config/voip/sip/accounts/account/@password"/>" ;
Use_Auth_ID[<xsl:value-of select="@number"/>]                                  "No" ;
Auth_ID[<xsl:value-of select="@number"/>]                                      "" ;
Mini_Certificate[<xsl:value-of select="@number"/>]                             "" ;
SRTP_Private_Key[<xsl:value-of select="@number"/>]                             "" ;

# *** Supplementary Service Subscription

Call_Waiting_Serv[<xsl:value-of select="@number"/>]                            "Yes" ;
Block_CID_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Block_ANC_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Dist_Ring_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Cfwd_All_Serv[<xsl:value-of select="@number"/>]                                "Yes" ;
Cfwd_Busy_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Cfwd_No_Ans_Serv[<xsl:value-of select="@number"/>]                             "Yes" ;
Cfwd_Sel_Serv[<xsl:value-of select="@number"/>]                                "Yes" ;
Cfwd_Last_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Block_Last_Serv[<xsl:value-of select="@number"/>]                              "Yes" ;
Accept_Last_Serv[<xsl:value-of select="@number"/>]                             "Yes" ;
DND_Serv[<xsl:value-of select="@number"/>]                                     "Yes" ;
CID_Serv[<xsl:value-of select="@number"/>]                                     "Yes" ;
CWCID_Serv[<xsl:value-of select="@number"/>]                                   "Yes" ;
Call_Return_Serv[<xsl:value-of select="@number"/>]                             "Yes" ;
Call_Redial_Serv[<xsl:value-of select="@number"/>]                             "Yes" ;
Call_Back_Serv[<xsl:value-of select="@number"/>]                               "Yes" ;
Three_Way_Call_Serv[<xsl:value-of select="@number"/>]                          "Yes" ;
Three_Way_Conf_Serv[<xsl:value-of select="@number"/>]                          "Yes" ;
Attn_Transfer_Serv[<xsl:value-of select="@number"/>]                           "Yes" ;
Unattn_Transfer_Serv[<xsl:value-of select="@number"/>]                         "Yes" ;
MWI_Serv[<xsl:value-of select="@number"/>]                                     "Yes" ;
VMWI_Serv[<xsl:value-of select="@number"/>]                                    "Yes" ;
Speed_Dial_Serv[<xsl:value-of select="@number"/>]                              "Yes" ;
Secure_Call_Serv[<xsl:value-of select="@number"/>]                             "Yes" ;
Referral_Serv[<xsl:value-of select="@number"/>]                                "Yes" ;
Feature_Dial_Serv[<xsl:value-of select="@number"/>]                            "Yes" ;
Service_Announcement_Serv[<xsl:value-of select="@number"/>]                    "No" ;

# *** Gateway Accounts

Gateway_1[<xsl:value-of select="@number"/>]                                    "" ;
GW1_NAT_Mapping_Enable[<xsl:value-of select="@number"/>]                       "No" ;
GW1_Auth_ID[<xsl:value-of select="@number"/>]                                  "" ;
GW1_Password[<xsl:value-of select="@number"/>]                                 "" ;
Gateway_2[<xsl:value-of select="@number"/>]                                    "" ;
GW2_NAT_Mapping_Enable[<xsl:value-of select="@number"/>]                       "No" ;
GW2_Auth_ID[<xsl:value-of select="@number"/>]                                  "" ;
GW2_Password[<xsl:value-of select="@number"/>]                                 "" ;
Gateway_3[<xsl:value-of select="@number"/>]                                    "" ;
GW3_NAT_Mapping_Enable[<xsl:value-of select="@number"/>]                       "No" ;
GW3_Auth_ID[<xsl:value-of select="@number"/>]                                  "" ;
GW3_Password[<xsl:value-of select="@number"/>]                                 "" ;
Gateway_4[<xsl:value-of select="@number"/>]                                    "" ;
GW4_NAT_Mapping_Enable[<xsl:value-of select="@number"/>]                       "No" ;
GW4_Auth_ID[<xsl:value-of select="@number"/>]                                  "" ;
GW4_Password[<xsl:value-of select="@number"/>]                                 "" ;

# *** VoIP Fallback To PSTN

Auto_PSTN_Fallback[<xsl:value-of select="@number"/>]                           "Yes" ;

# *** Dial Plan

Dial_Plan[<xsl:value-of select="@number"/>]                                    "(*69|<*0,:>xx.<:@gw0>|xx.)" ;
Enable_IP_Dialing[<xsl:value-of select="@number"/>]                            "No" ;
Emergency_Number[<xsl:value-of select="@number"/>]                             "" ;

# *** FXS Port Polarity Configuration

Idle_Polarity[<xsl:value-of select="@number"/>]                                "Forward" ;  # options: Forward/Reverse
Caller_Conn_Polarity[<xsl:value-of select="@number"/>]                         "Forward" ;  # options: Forward/Reverse
Callee_Conn_Polarity[<xsl:value-of select="@number"/>]                         "Forward" ;  # options: Forward/Reverse

# *** Call Forward Settings

Cfwd_All_Dest[<xsl:value-of select="@number"/>]                              ! "" ;
Cfwd_Busy_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_No_Ans_Dest[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_No_Ans_Delay[<xsl:value-of select="@number"/>]                          ! "20" ;

# *** Selective Call Forward Settings

Cfwd_Sel1_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel1_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel2_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel2_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel3_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel3_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel4_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel4_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel5_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel5_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel6_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel6_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel7_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel7_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Sel8_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Sel8_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Cfwd_Last_Caller[<xsl:value-of select="@number"/>]                           ! "" ;
Cfwd_Last_Dest[<xsl:value-of select="@number"/>]                             ! "" ;
Block_Last_Caller[<xsl:value-of select="@number"/>]                          ! "" ;
Accept_Last_Caller[<xsl:value-of select="@number"/>]                         ! "" ;

# *** Speed Dial Settings

Speed_Dial_2[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_3[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_4[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_5[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_6[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_7[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_8[<xsl:value-of select="@number"/>]                               ! "" ;
Speed_Dial_9[<xsl:value-of select="@number"/>]                               ! "" ;

# *** Supplementary Service Settings

CW_Setting[<xsl:value-of select="@number"/>]                                 ! "Yes" ;
Block_CID_Setting[<xsl:value-of select="@number"/>]                          ! "No" ;
Block_ANC_Setting[<xsl:value-of select="@number"/>]                          ! "No" ;
DND_Setting[<xsl:value-of select="@number"/>]                                ! "No" ;
CID_Setting[<xsl:value-of select="@number"/>]                                ! "Yes" ;
CWCID_Setting[<xsl:value-of select="@number"/>]                              ! "Yes" ;
Dist_Ring_Setting[<xsl:value-of select="@number"/>]                          ! "Yes" ;
Secure_Call_Setting[<xsl:value-of select="@number"/>]                          "No" ;
Message_Waiting[<xsl:value-of select="@number"/>]                            ! "No" ;
Accept_Media_Loopback_Request[<xsl:value-of select="@number"/>]                "automatic" ;  
Media_Loopback_Mode[<xsl:value-of select="@number"/>]                          "source" ;  
Media_Loopback_Type[<xsl:value-of select="@number"/>]                          "media" ;  

# *** Distinctive Ring Settings

Ring1_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring2_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring3_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring4_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring5_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring6_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring7_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring8_Caller[<xsl:value-of select="@number"/>]                               ! "" ;
Ring9_Caller[<xsl:value-of select="@number"/>]                                 "" ;

# *** Ring Settings

Default_Ring[<xsl:value-of select="@number"/>]                               ! "1" ;  
Default_CWT[<xsl:value-of select="@number"/>]                                ! "1" ;  
Hold_Reminder_Ring[<xsl:value-of select="@number"/>]                         ! "8" ;  
Call_Back_Ring[<xsl:value-of select="@number"/>]                             ! "7" ;  
Cfwd_Ring_Splash_Len[<xsl:value-of select="@number"/>]                       ! "0" ;
Cblk_Ring_Splash_Len[<xsl:value-of select="@number"/>]                       ! "0" ;
VMWI_Ring_Splash_Len[<xsl:value-of select="@number"/>]                       ! "0" ;
VMWI_Ring_Policy[<xsl:value-of select="@number"/>]                             "New VM Available" ;  
Ring_On_No_New_VM[<xsl:value-of select="@number"/>]                            "No" ;
</xsl:for-each>

# *** Audio Configuration
<xsl:for-each select="phone_config/voip/dsp/codecs">
Preferred_Codec[<xsl:value-of select="@number"/>]                              "<xsl:value-of select="translate(/phone_config/voip/dsp/codecs/@name, $smallcase, $uppercase)"/>a" ;
Second_Preferred_Codec[<xsl:value-of select="@number"/>]                       "Unspecified" ;  
Third_Preferred_Codec[<xsl:value-of select="@number"/>]                        "Unspecified" ;  
Use_Pref_Codec_Only[<xsl:value-of select="@number"/>]                          "Yes" ;
Silence_Supp_Enable[<xsl:value-of select="@number"/>]                          "No" ;
Silence_Threshold[<xsl:value-of select="@number"/>]                            "medium" ;  
G729a_Enable[<xsl:value-of select="@number"/>]                                 "Yes" ;
Echo_Canc_Enable[<xsl:value-of select="@number"/>]                             "Yes" ;
G723_Enable[<xsl:value-of select="@number"/>]                                  "Yes" ;
Echo_Canc_Adapt_Enable[<xsl:value-of select="@number"/>]                       "Yes" ;
G726-16_Enable[<xsl:value-of select="@number"/>]                               "Yes" ;
Echo_Supp_Enable[<xsl:value-of select="@number"/>]                             "Yes" ;
G726-24_Enable[<xsl:value-of select="@number"/>]                               "Yes" ;
FAX_CED_Detect_Enable[<xsl:value-of select="@number"/>]                        "Yes" ;
G726-32_Enable[<xsl:value-of select="@number"/>]                               "Yes" ;
FAX_CNG_Detect_Enable[<xsl:value-of select="@number"/>]                        "Yes" ;
G726-40_Enable[<xsl:value-of select="@number"/>]                               "Yes" ;
FAX_Passthru_Codec[<xsl:value-of select="@number"/>]                           "G711u" ;  
DTMF_Process_INFO[<xsl:value-of select="@number"/>]                            "Yes" ;
FAX_Codec_Symmetric[<xsl:value-of select="@number"/>]                          "Yes" ;
DTMF_Process_AVT[<xsl:value-of select="@number"/>]                             "Yes" ;
FAX_Passthru_Method[<xsl:value-of select="@number"/>]                          "NSE" ;  
DTMF_Tx_Method[<xsl:value-of select="@number"/>]                               "Auto" ;  
DTMF_Tx_Mode[<xsl:value-of select="@number"/>]                                 "Strict" ;  
DTMF_Tx_Strict_Hold_Off_Time[<xsl:value-of select="@number"/>]                 "40" ;
FAX_Process_NSE[<xsl:value-of select="@number"/>]                              "Yes" ;
Hook_Flash_Tx_Method[<xsl:value-of select="@number"/>]                         "None" ;  
FAX_Disable_ECAN[<xsl:value-of select="@number"/>]                             "No" ;
Release_Unused_Codec[<xsl:value-of select="@number"/>]                         "Yes" ;
FAX_Enable_T38[<xsl:value-of select="@number"/>]                               "Yes" ;
FAX_T38_Redundancy[<xsl:value-of select="@number"/>]                           "1" ;  
FAX_Tone_Detect_Mode[<xsl:value-of select="@number"/>]                         "caller or callee" ;  
Symmetric_RTP[<xsl:value-of select="@number"/>]                                "Yes" ;
</xsl:for-each>

# *** Call Progress Tones

Dial_Tone                                       "350@-19,440@-19;10(*/0/1+2)" ;
Second_Dial_Tone                                "420@-19,520@-19;10(*/0/1+2)" ;
Outside_Dial_Tone                               "420@-16;10(*/0/1)" ;
Prompt_Tone                                     "520@-19,620@-19;10(*/0/1+2)" ;
Busy_Tone                                       "480@-19,620@-19;10(.5/.5/1+2)" ;
Reorder_Tone                                    "480@-19,620@-19;10(.25/.25/1+2)" ;
Off_Hook_Warning_Tone                           "480@-10,620@0;10(.125/.125/1+2)" ;
Ring_Back_Tone                                  "440@-19,480@-19;*(2/4/1+2)" ;
Ring_Back_2_Tone                                "440@-19,480@-19;*(1/1/1+2)" ;
Confirm_Tone                                    "600@-16;1(.25/.25/1)" ;
SIT1_Tone                                       "985@-16,1428@-16,1777@-16;20(.380/0/1,.380/0/2,.380/0/3,0/4/0)" ;
SIT2_Tone                                       "914@-16,1371@-16,1777@-16;20(.274/0/1,.274/0/2,.380/0/3,0/4/0)" ;
SIT3_Tone                                       "914@-16,1371@-16,1777@-16;20(.380/0/1,.380/0/2,.380/0/3,0/4/0)" ;
SIT4_Tone                                       "985@-16,1371@-16,1777@-16;20(.380/0/1,.274/0/2,.380/0/3,0/4/0)" ;
MWI_Dial_Tone                                   "350@-19,440@-19;2(.1/.1/1+2);10(*/0/1+2)" ;
Cfwd_Dial_Tone                                  "350@-19,440@-19;2(.2/.2/1+2);10(*/0/1+2)" ;
Holding_Tone                                    "600@-19;*(.1/.1/1,.1/.1/1,.1/9.5/1)" ;
Conference_Tone                                 "350@-19;20(.1/.1/1,.1/9.7/1)" ;
Secure_Call_Indication_Tone                     "397@-19,507@-19;15(0/2/0,.2/.1/1,.1/2.1/2)" ;
VoIP_PIN_Tone                                   "600@-10;*(0/1/1,.1/.1/1,.1/.1/1,.1/.5/1)" ;
PSTN_PIN_Tone                                   "600@-10;*(0/.7/1,.2/.1/1,.2/.1/1,.2/.5/1)" ;
Feature_Invocation_Tone                         "350@-16;*(.1/.1/1)" ;

# *** Distinctive Ring Patterns

Ring1_Cadence                                   "60(2/4)" ;
Ring2_Cadence                                   "60(.8/.4,.8/4)" ;
Ring3_Cadence                                   "60(.4/.2,.4/.2,.8/4)" ;
Ring4_Cadence                                   "60(.3/.2,1/.2,.3/4)" ;
Ring5_Cadence                                   "1(.5/.5)" ;
Ring6_Cadence                                   "60(.2/.4,.2/.4,.2/4)" ;
Ring7_Cadence                                   "60(.4/.2,.4/.2,.4/4)" ;
Ring8_Cadence                                   "60(0.25/9.75)" ;
Ring9_Cadence                                   "60(.4/.2,.4/2)" ;

# *** Distinctive Call Waiting Tone Patterns

CWT1_Cadence                                    "30(.3/9.7)" ;
CWT2_Cadence                                    "30(.1/.1, .1/9.7)" ;
CWT3_Cadence                                    "30(.1/.1, .1/.1, .1/9.7)" ;
CWT4_Cadence                                    "30(.1/.1,.3/.1,.1/9.3)" ;
CWT5_Cadence                                    "1(.5/.5)" ;
CWT6_Cadence                                    "30(.1/.1,.3/.2,.3/9.1)" ;
CWT7_Cadence                                    "30(.3/.1,.3/.1,.1/9.1)" ;
CWT8_Cadence                                    "2.3(.3/2)" ;
CWT9_Cadence                                    "30(.3/9.7)" ;

# *** Distinctive Ring/CWT Pattern Names

Ring1_Name                                      "Bellcore-r1" ;
Ring2_Name                                      "Bellcore-r2" ;
Ring3_Name                                      "Bellcore-r3" ;
Ring4_Name                                      "Bellcore-r4" ;
Ring5_Name                                      "Bellcore-r5" ;
Ring6_Name                                      "Bellcore-r6" ;
Ring7_Name                                      "Bellcore-r7" ;
Ring8_Name                                      "Bellcore-r8" ;
Ring9_Name                                      "Bellcore-r9" ;

# *** Ring and Call Waiting Tone Spec

Ring_Waveform                                   "Trapezoid" ;  
Ring_Frequency                                  "20" ;
Ring_Voltage                                    "85" ;
CWT_Frequency                                   "440@-10" ;

# *** Control Timer Values (sec)

Hook_Flash_Timer_Min                            ".1" ;
Hook_Flash_Timer_Max                            ".9" ;
Callee_On_Hook_Delay                            "0" ;
Reorder_Delay                                   "5" ;
Call_Back_Expires                               "1800" ;
Call_Back_Retry_Intvl                           "30" ;
Call_Back_Delay                                 ".5" ;
VMWI_Refresh_Intvl                              "0" ;
Interdigit_Long_Timer                           "10" ;
Interdigit_Short_Timer                          "3" ;
CPC_Delay                                       "2" ;
CPC_Duration                                    "0" ;

# *** Vertical Service Activation Codes

Call_Return_Code                                "*69" ;
Call_Redial_Code                                "*07" ;
Blind_Transfer_Code                             "*98" ;
Call_Back_Act_Code                              "*66" ;
Call_Back_Deact_Code                            "*86" ;
Call_Back_Busy_Act_Code                         "*05" ;
Cfwd_All_Act_Code                               "*72" ;
Cfwd_All_Deact_Code                             "*73" ;
Cfwd_Busy_Act_Code                              "*90" ;
Cfwd_Busy_Deact_Code                            "*91" ;
Cfwd_No_Ans_Act_Code                            "*92" ;
Cfwd_No_Ans_Deact_Code                          "*93" ;
Cfwd_Last_Act_Code                              "*63" ;
Cfwd_Last_Deact_Code                            "*83" ;
Block_Last_Act_Code                             "*60" ;
Block_Last_Deact_Code                           "*80" ;
Accept_Last_Act_Code                            "*64" ;
Accept_Last_Deact_Code                          "*84" ;
CW_Act_Code                                     "*56" ;
CW_Deact_Code                                   "*57" ;
CW_Per_Call_Act_Code                            "*71" ;
CW_Per_Call_Deact_Code                          "*70" ;
Block_CID_Act_Code                              "*67" ;
Block_CID_Deact_Code                            "*68" ;
Block_CID_Per_Call_Act_Code                     "*81" ;
Block_CID_Per_Call_Deact_Code                   "*82" ;
Block_ANC_Act_Code                              "*77" ;
Block_ANC_Deact_Code                            "*87" ;
DND_Act_Code                                    "*78" ;
DND_Deact_Code                                  "*79" ;
CID_Act_Code                                    "*65" ;
CID_Deact_Code                                  "*85" ;
CWCID_Act_Code                                  "*25" ;
CWCID_Deact_Code                                "*45" ;
Dist_Ring_Act_Code                              "*26" ;
Dist_Ring_Deact_Code                            "*46" ;
Speed_Dial_Act_Code                             "*74" ;
Secure_All_Call_Act_Code                        "*16" ;
Secure_No_Call_Act_Code                         "*17" ;
Secure_One_Call_Act_Code                        "*18" ;
Secure_One_Call_Deact_Code                      "*19" ;
Conference_Act_Code                             "" ;
Attn-Xfer_Act_Code                              "" ;
Modem_Line_Toggle_Code                          "*99" ;
FAX_Line_Toggle_Code                            "#99" ;
Media_Loopback_Code                             "*03" ;
Referral_Services_Codes                         "" ;
Feature_Dial_Services_Codes                     "" ;

# *** Vertical Service Announcement Codes

Service_Annc_Base_Number                        "" ;
Service_Annc_Extension_Codes                    "" ;

# *** Outbound Call Codec Selection Codes

Prefer_G711u_Code                               "*017110" ;
Force_G711u_Code                                "*027110" ;
Prefer_G711a_Code                               "*017111" ;
Force_G711a_Code                                "*027111" ;
Prefer_G723_Code                                "*01723" ;
Force_G723_Code                                 "*02723" ;
Prefer_G726r16_Code                             "*0172616" ;
Force_G726r16_Code                              "*0272616" ;
Prefer_G726r24_Code                             "*0172624" ;
Force_G726r24_Code                              "*0272624" ;
Prefer_G726r32_Code                             "*0172632" ;
Force_G726r32_Code                              "*0272632" ;
Prefer_G726r40_Code                             "*0172640" ;
Force_G726r40_Code                              "*0272640" ;
Prefer_G729a_Code                               "*01729" ;
Force_G729a_Code                                "*02729" ;

# *** Miscellaneous

Set_Local_Date_(mm/dd)                          "" ;
Set_Local_Time_(HH/mm)                          "" ;
Time_Zone                                       "GMT-08:00" ;  
FXS_Port_Impedance                              "600" ;  
Daylight_Saving_Time_Rule                       "start=4/1/7;end=10/-1/7;save=1" ;
FXS_Port_Input_Gain                             "-3" ;
FXS_Port_Output_Gain                            "-3" ;
DTMF_Playback_Level                             "-16" ;
DTMF_Playback_Length                            ".1" ;
Detect_ABCD                                     "Yes" ;
Playback_ABCD                                   "Yes" ;
Caller_ID_Method                                "Bellcore(N.Amer,China)" ;  
Caller_ID_FSK_Standard                          "bell 202" ; 
Feature_Invocation_Method                       "Default" ;  
More_Echo_Suppression                           "No" ;

# *** Port Forwarding Settings


# *** DMZ Settings

Enable_DMZ                                    ! "No" ;
DMZ_Host_IP_Address                           ! "0" ;

# *** Miscellaneous Settings

Multicast_Passthru                            ! "Disabled" ;  

# *** System Reserved Ports Range

Starting_Port                                 ! "50000" ;
Num_of_Ports_Reserved                         ! "256" ;  

# *** 

Protect_IVR_FactoryReset                        "No" ;
</xsl:template>
</xsl:stylesheet>
