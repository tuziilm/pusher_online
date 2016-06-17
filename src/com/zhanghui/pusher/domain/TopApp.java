package com.zhanghui.pusher.domain;

public class TopApp extends Id {
	private String appName;
	private String country;
	private Integer activeUser;
	private Integer useTimes;
	private String date;
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getActiveUser() {
		return activeUser;
	}
	public void setActiveUser(Integer activeUser) {
		this.activeUser = activeUser;
	}
	public Integer getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
