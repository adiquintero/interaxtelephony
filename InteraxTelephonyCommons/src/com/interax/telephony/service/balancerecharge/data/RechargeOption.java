package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;

/**
 *
 * @author abustamante
 */
public class RechargeOption implements Serializable {

    private static final long serialVersionUID = -1456216924763039018L;
    private Float amount;
    private BalanceRechargeCurrency currency;

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public BalanceRechargeCurrency getCurrency() {
        return currency;
    }

    public void setCurrency(BalanceRechargeCurrency currency) {
        this.currency = currency;
    }
    
}
