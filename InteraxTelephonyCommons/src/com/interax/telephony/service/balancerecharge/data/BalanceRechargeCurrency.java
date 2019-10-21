package com.interax.telephony.service.balancerecharge.data;

import java.io.Serializable;

/**
 *
 * @author abustamante
 */
public class BalanceRechargeCurrency implements Serializable {

    private static final long serialVersionUID = -1170119035763039018L;
    private Long id;
    private String abbreviation;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
