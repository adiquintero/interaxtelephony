package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;

/**
 *
 * @author abustamante
 */
public class BalanceRechargeContract implements Serializable {

    private static final long serialVersionUID = -6170117924763039018L;
    private Long id;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private Float currentBalance;
    private BalanceRechargeCurrency currency;
    private String language;

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getClientLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public BalanceRechargeCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BalanceRechargeCurrency currency) {
        this.currency = currency;
    }

    public Float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    
}
