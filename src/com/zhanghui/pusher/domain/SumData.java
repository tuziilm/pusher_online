package com.zhanghui.pusher.domain;
/**
 * 用于求汇总数据
 * @author Administrator
 *
 */
public class SumData {
	private Integer pluginActiveUser;
	private Integer pluginLostUser;
	private Integer activeUser;
	private Integer lostUser;
	private Integer validActiveUser;
	private Integer validLostUser;
	private Integer otherRequestTimes;
	private Integer requestTimes;
	private Integer otherShowTimes;
	private Integer showTimes;
	private Integer otherClickTimes;
	private Integer clickTimes;
	private Integer income;
	private double showPrice;
	private double clickPrice;
	private double clickRate;
	private double requestSuccessRate;
	
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
	public double getClickRate() {
		return clickRate;
	}
	public void setClickRate(double clickRate) {
		this.clickRate = clickRate;
	}
	public double getRequestSuccessRate() {
		return requestSuccessRate;
	}
	public void setRequestSuccessRate(double requestSuccessRate) {
		this.requestSuccessRate = requestSuccessRate;
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
	public double getClickPrice() {
		return clickPrice;
	}
	public void setClickPrice(double clickPrice) {
		this.clickPrice = clickPrice;
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
