package com.zhanghui.pusher.domain;

public class AppIncome extends Id {
	private String pkgName;
	private String pageName;//¹ã¸æÎ»
	private String adOwner;
	private Integer requestTimes;
	private Integer showTimes;
	private Integer clickTimes;
	private double clickRate;
	private Integer income;
	private double clickPrice;
	private double showPrice;
	private String date;
	
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
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAdOwner() {
		return adOwner;
	}
	public void setAdOwner(String adOwner) {
		this.adOwner = adOwner;
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
	public double getClickPrice() {
		return clickPrice;
	}
	public void setClickPrice(double clickPrice) {
		this.clickPrice = clickPrice;
	}
	public double getShowPrice() {
		return showPrice;
	}
	public void setShowPrice(double showPrice) {
		this.showPrice = showPrice;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
}
