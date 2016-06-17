package com.zhanghui.pusher.statistics.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

public class TaskActionData{
	private String module;
	private String key;
	private int count;
	private String date;

    public TaskActionData(String module, String date, String key, int count){
        this.module = module;
        this.key = key;
        this.date = date;
        this.count = count;
    }

    public class KeyEntry{
    	public String taskId;
        public String actionId;
        public String country;

        public KeyEntry(String taskId, String actionId, String country) {
        	this.taskId = taskId;
            this.actionId = actionId;
            this.country = country;
        }
    }

    public KeyEntry getKeyEntry(){
        String[] fields = key.split(Config.SEP);
        return new KeyEntry(fields[0], fields[2], fields[1]);
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
