package com.interax.telephony.service.balancerecharge.data;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BalanceRechargeAddress implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address1;
    private String zipCode;
    private Long CountryId;
    private Long cityId;
    private String cityName;
    private Long countyId;
    private String countyName;
    private Long stateId;
    private String stateName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Long getCountryId() {
		return CountryId;
	}
	public void setCountryId(Long countryId) {
		CountryId = countryId;
	}

    
}
