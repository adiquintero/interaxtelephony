package com.interax.telephony.service.remote.balancerecharge;

import com.interax.telephony.service.balancerecharge.data.BalanceRechargeAddress;
import com.interax.telephony.service.balancerecharge.data.BalanceRechargeContract;
import com.interax.telephony.service.balancerecharge.data.BalanceRechargePaymentInfo;
import com.interax.telephony.service.balancerecharge.data.BalanceRechargePin;
import com.interax.telephony.service.balancerecharge.data.RechargeInvoice;
import com.interax.telephony.service.balancerecharge.data.RechargeOption;
import com.interax.telephony.service.balancerecharge.data.RechargeOrder;
import com.interax.telephony.service.balancerecharge.data.RechargePreOrder;
import com.interax.telephony.service.exception.NullParameterException;
import com.interax.telephony.service.remote.balancerecharge.exception.BalanceRechargePaymentDeclinedException;
import com.interax.telephony.service.remote.balancerecharge.exception.ContractNotFoundException;
import com.interax.telephony.service.remote.balancerecharge.exception.GeneralException;
import com.interax.telephony.service.remote.balancerecharge.exception.InvalidCreditCardException;
import com.interax.telephony.service.remote.balancerecharge.exception.PaymentInfoNotFoundException;
import com.interax.telephony.service.remote.balancerecharge.exception.PinNotFoundException;
import com.interax.telephony.service.remote.balancerecharge.exception.RechargeOptionNotFoundException;
import com.interax.telephony.util.InteraxTelephonyGenericEjb;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface BalanceRechargeEJB extends InteraxTelephonyGenericEjb {

    BalanceRechargePin searchPin(Long ani) throws PinNotFoundException, GeneralException;
    
    BalanceRechargePin searchPin(String serial) throws PinNotFoundException, GeneralException;

    BalanceRechargeContract searchContract(Long extensionId) throws ContractNotFoundException, GeneralException;

    List<RechargeOption> getRechargeOptions(Long enterpriseId, boolean contractRequired) throws RechargeOptionNotFoundException, GeneralException;

    BalanceRechargePaymentInfo getPaymentInfo(Long contractId) throws PaymentInfoNotFoundException, GeneralException;

    BalanceRechargePaymentInfo getLastPaymentInfo(Long customerId) throws PaymentInfoNotFoundException, GeneralException;

    RechargeOrder getOrder(RechargePreOrder preOrder) throws GeneralException;

    RechargeInvoice processOrder(RechargeOrder order) throws BalanceRechargePaymentDeclinedException, GeneralException;

    void validateCreditCard(String creditCardNumber) throws InvalidCreditCardException, GeneralException;

    BalanceRechargePaymentInfo saveSisacPaymentInfo(BalanceRechargePaymentInfo paymentInfo, Long contractId, String zipCode) throws GeneralException;
    
    BalanceRechargePaymentInfo saveSisacPaymentInfoDistribution(BalanceRechargePaymentInfo paymentInfo, Long contractId, BalanceRechargeAddress address, String creditCardName) throws GeneralException;

    String getCreditCardType(java.lang.String creditCardNumber) throws GeneralException;

    //void saveDistributionPinOperation(Long serial, Float amount, String comment) throws GeneralException, NullParameterException;

}
