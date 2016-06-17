package com.zhanghui.pusher.domain;

public class CountryData extends Id{
	private String adOwner;
	private Integer activeUser;
	private Integer validActiveUser;
	private Integer addUser;
	private Integer otherRequestTimes;
	private Integer requestTimes;
	private Integer otherShowTimes;
	private Integer showTimes;
	private Integer otherClickTimes;
	private Integer clickTimes;
	private double requestSuccessRate;
	private double clickRate;
	private Integer income;
	private double showPrice;
	private String country;
	private String date;
	
	public String getAdOwner() {
		return adOwner;
	}
	public void setAdOwner(String adOwner) {
		this.adOwner = adOwner;
	}
	public Integer getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(Integer activeUser) {
		this.activeUser = activeUser;
	}
	public Integer getRequestTimes() {
		return requestTimes;
	}
	public void setRequestTimes(Integer requestTimes) {
		this.requestTimes = requestTimes;
	}
	public Integer getShowTimes() {
		return showTimes;
	}
	public void setShowTimes(Integer showTimes) {
		this.showTimes = showTimes;
	}
	public Integer getClickTimes() {
		return clickTimes;
	}
	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getAddUser() {
		return addUser;
	}
	public void setAddUser(Integer addUser) {
		this.addUser = addUser;
	}
	public Integer getOtherRequestTimes() {
		return otherRequestTimes;
	}
	public void setOtherRequestTimes(Integer otherRequestTimes) {
		this.otherRequestTimes = otherRequestTimes;
	}
	public Integer getOtherShowTimes() {
		return otherShowTimes;
	}
	public void setOtherShowTimes(Integer otherShowTimes) {
		this.otherShowTimes = otherShowTimes;
	}
	public Integer getOtherClickTimes() {
		return otherClickTimes;
	}
	public void setOtherClickTimes(Integer otherClickTimes) {
		this.otherClickTimes = otherClickTimes;
	}
	public double getRequestSuccessRate() {
		return requestSuccessRate;
	}
	public void setRequestSuccessRate(double requestSuccessRate) {
		this.requestSuccessRate = requestSuccessRate;
	}
	public double getClickRate() {
		return clickRate;
	}
	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public double getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(double showPrice) {
		this.showPrice = showPrice;
	}
	public Integer getValidActiveUser() {
		return validActiveUser;
	}
	public void setValidActiveUser(Integer validActiveUser) {
		this.validActiveUser = validActiveUser;
	}
	
}
