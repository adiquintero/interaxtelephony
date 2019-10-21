package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abustamante
 */
public class RechargeInvoice implements Serializable {

    private static final long serialVersionUID = -1170376424763039018L;
    private Long customerId;
    private Long contractId;
    private Long orderId;
    private String creditCardNumber;
    private Float rechargedBalance;
    private BalanceRechargeCurrency currency;
    private Date creationDate;
    private Float taxChargesTotal;
    private Float total;
    private String TransactionNumber;
    

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public BalanceRechargeCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BalanceRechargeCurrency currency) {
        this.currency = currency;
    }

    public Float getRechargedBalance() {
        return rechargedBalance;
    }

    public void setRechargedBalance(Float rechargedBalance) {
        this.rechargedBalance = rechargedBalance;
    }

    public Float getTaxChargesTotal() {
        return taxChargesTotal;
    }

    public void setTaxChargesTotal(Float taxChargesTotal) {
        this.taxChargesTotal = taxChargesTotal;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

	public String getTransactionNumber() {
		return TransactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		TransactionNumber = transactionNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
}
