package com.interax.telephony.server.agi.dial;

import org.asteriskjava.fastagi.AgiChannel;
import org.asteriskjava.fastagi.AgiException;
import org.asteriskjava.fastagi.AgiRequest;
import org.asteriskjava.fastagi.BaseAgiScript;

public class DialAgi extends BaseAgiScript{

	public void service(AgiRequest request, AgiChannel channel)	throws AgiException {
			
		String parentCdrId = getVariable("PARENT_CDR_UNIQUE_ID");
		if(parentCdrId == null){
			parentCdrId = getVariable("CDR(uniqueid)");
		}
		exec("NoOp","parentCdrId: " + parentCdrId);
		String childrenCdrId = getVariable("CHILDREN_CDR_UNIQUE_ID");
		if(childrenCdrId == null){
			childrenCdrId = "0";
		}
		exec("NoOp","childrenCdrId: " + childrenCdrId);
		
		String dialOption = getVariable("DIALDATA") + "|" + request.getParameter("TIMEOUT") + "|" + request.getParameter("OPTION") ;
		exec("Dial",dialOption);
		
		
			String nextCdrId;
			//String[] cdrInfo;

			int nextChildrenCdrId;

			if(childrenCdrId==null){
				nextChildrenCdrId = 0;
			}else{
				nextChildrenCdrId = Integer.valueOf(childrenCdrId);
			}
			this.setVariable("CHILDREN_CDR_UNIQUE_ID", ""+ (++nextChildrenCdrId));

			nextCdrId = parentCdrId + "-" + nextChildrenCdrId;
			exec("NoOp","nextCdrId: " + nextCdrId);
			setVariable("CDR(uniqueid)", nextCdrId);
			String serviceType = getVariable("IP_PBX_SERVICE_TYPE");
			//String accessType = getVariable("IP_PBX_ACCESS_TYPE");
			//IpPbxAccessType accessType = IpPbxAccessType.valueOf(strAccessType);
			String accessType = request.getParameter("ACCESSTYPE");
			String pbxId = getVariable("IP_PBX_IPPBX_ID");
			String extensionId = getVariable("IP_PBX_SRC_EXTENSION_ID");
			if(extensionId == null){
				extensionId = "-";
			}
			//this.setVariable("CDR(userfield)", InteraxTelephonyCdrFormater.formatCdrUserField(accessType,serviceType,pbxId,extensionId,null);
			setVariable("CDR(amaflags)", "DOCUMENTATION");
			this.setVariable("CDR(userfield)", accessType + "|" + serviceType + "|" + pbxId + ":" + extensionId + "|-");
			exec("ResetCdr","w");
			setVariable("CDR(amaflags)", "IGNORE");
		if(getVariable("DIALSTATUS").equalsIgnoreCase("ANSWER")){
			hangup();
		}
	
	}
	
	
}
