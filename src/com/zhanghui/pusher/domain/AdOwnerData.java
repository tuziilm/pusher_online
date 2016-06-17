package com.zhanghui.pusher.domain;

public class AdOwnerData extends Id {
	private String adOwner;
	private String showWay;
	private String from;
	private String date;
	private Integer requestTimes;
	private Integer otherRequestTimes;
	private Integer showTimes;
	private Integer otherShowTimes;
	private Integer clickTimes;
	private Integer otherClickTimes;
	private double requestSuccessRate;
	private double clickRate;
	private Integer income;
	private double showPrice;
	private double clickPrice;
	private Integer activeUser;
	private Integer validActiveUser;
	
	public String getAdOwner() {
		return adOwner;
	}
	public void setAdOwner(String adOwner) {
		this.adOwner = adOwner;
	}
	public String getShowWay() {
		return showWay;
	}
	public void setShowWay(String showWay) {
		this.showWay = showWay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public Integer getRequestTimes() {
		return requestTimes;
	}
	public void setRequestTimes(Integer requestTimes) {
		this.requestTimes = requestTimes;
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
	public double getClickPrice() {
		return clickPrice;
	}
	public void setClickPrice(double clickPrice) {
		this.clickPrice = clickPrice;
	}
	public Integer getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(Integer activeUser) {
		this.activeUser = activeUser;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Integer getValidActiveUser() {
		return validActiveUser;
	}
	public void setValidActiveUser(Integer validActiveUser) {
		this.validActiveUser = validActiveUser;
	}
	
}
