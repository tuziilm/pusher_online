package com.zhanghui.pusher.domain;

public class TaskActionView extends Id{
	private String module;
	private String taskId;
	private String country;
    private String actionId;
	private Integer count;
	private String schedule;


    public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getActionId() {
		return actionId;
	}
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}
	
}
