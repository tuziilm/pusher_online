package com.zhanghui.pusher.domain;

public class TotalData extends Id {
	private Integer pluginActiveUser;
	private Integer pluginLostUser;
	private Integer activeUser;
	private Integer lostUser;
	private Integer validActiveUser;
	private Integer validLostUser;
	private Integer requestTimes;
	private Integer showTimes;
	private Integer clickTimes;
	private Integer income;
	private double showPrice;
	private String date;
	private double reqsuccessRate;
	private double clickRate;
	
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public double getReqsuccessRate() {
		return reqsuccessRate;
	}
	public void setReqsuccessRate(double reqsuccessRate) {
		this.reqsuccessRate = reqsuccessRate;
	}
	public double getClickRate() {
		return clickRate;
	}
	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
	public Integer getLostUser() {
		return lostUser;
	}
	public void setLostUser(Integer lostUser) {
		this.lostUser = lostUser;
	}
	public Integer getPluginActiveUser() {
		return pluginActiveUser;
	}
	public void setPluginActiveUser(Integer pluginActiveUser) {
		this.pluginActiveUser = pluginActiveUser;
	}
	public Integer getPluginLostUser() {
		return pluginLostUser;
	}
	public void setPluginLostUser(Integer pluginLostUser) {
		this.pluginLostUser = pluginLostUser;
	}
	public Integer getValidActiveUser() {
		return validActiveUser;
	}
	public void setValidActiveUser(Integer validActiveUser) {
		this.validActiveUser = validActiveUser;
	}
	public Integer getValidLostUser() {
		return validLostUser;
	}
	public void setValidLostUser(Integer validLostUser) {
		this.validLostUser = validLostUser;
	}
	
}
