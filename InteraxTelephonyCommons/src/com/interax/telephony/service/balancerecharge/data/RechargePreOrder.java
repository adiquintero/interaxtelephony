package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;

/**
 *
 * @author abustamante
 */
public class RechargePreOrder implements Serializable {

    private static final long serialVersionUID = -1000117924763039018L;
    private Long enterpriseId;
    private Long sisacPinSerial;
    private Long contractId;
    private String itemDescription;
    private BalanceRechargePaymentInfo paymentInfo;
    private Float balanceToRecharge;
    private BalanceRechargeCurrency currency;
    private String rechargeSource;
    public static final String RECHARGE_DISTRIBUTION_SRC = "distribution";
    public static final String RECHARGE_IVR_SRC = "ivr";

    public Long getSisacPinSerial() {
        return sisacPinSerial;
    }

    public void setSisacPinSerial(Long sisacPinSerial) {
        this.sisacPinSerial = sisacPinSerial;
    }

    public Float getBalanceToRecharge() {
        return balanceToRecharge;
    }

    public void setBalanceToRecharge(Float balanceToRecharge) {
        this.balanceToRecharge = balanceToRecharge;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public BalanceRechargeCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BalanceRechargeCurrency currency) {
        this.currency = currency;
    }

    public BalanceRechargePaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(BalanceRechargePaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

	public String getRechargeSource() {
		return rechargeSource;
	}

	public void setRechargeSource(String rechargeSource) {
		this.rechargeSource = rechargeSource;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
    
    
    
}
