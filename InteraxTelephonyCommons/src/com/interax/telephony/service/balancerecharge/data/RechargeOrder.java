package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author abustamante
 */
public class RechargeOrder implements Serializable {

    private static final long serialVersionUID = -3120767924763039018L;
    private Long id;
    private RechargePreOrder rechargePreOrder;
    private Date rechargeOrderDate;
    private Float taxChargesTotal;
    private Float total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRechargeOrderDate() {
        return rechargeOrderDate;
    }

    public void setRechargeOrderDate(Date rechargeOrderDate) {
        this.rechargeOrderDate = rechargeOrderDate;
    }

    public RechargePreOrder getRechargePreOrder() {
        return rechargePreOrder;
    }

    public void setRechargePreOrder(RechargePreOrder rechargePreOrder) {
        this.rechargePreOrder = rechargePreOrder;
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

}
