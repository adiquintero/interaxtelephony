package com.interax.telephony.service.remote.ippbx;

import com.interax.telephony.service.exception.ippbx.IpPbxException;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;
import javax.ejb.Remote;

/**
 *
 * @author Interaxmedia
 */
@Remote
public interface IpPbxManagerEJB extends InteraxTelephonyGenericEjb {

    void activateIpPbxExtensionService(Long extensionAsteriskId) throws IpPbxException;

}
