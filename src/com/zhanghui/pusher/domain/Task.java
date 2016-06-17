package com.zhanghui.pusher.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Task extends RemarkStatusId {
    private String name;
    private Integer type;
    private Set<Integer> refIds;
    private String showTime;
    private String autoShow;
    private String tips;
    private String title;
    private String mtitle;
    private String iconPath;//Ð¡Í¼±ê
    private String content;
    private Set<String> countries;
    private Set<String> mccmncs;
    private Date startDate;
    private Date endDate;
    private Integer priority;
    private String shutDown;
    private String picPath;//´óÍ¼Æ¬
    private Integer startDays;
    
    private Integer maxReqCount;

    public Integer getMaxReqCount() {
        return maxReqCount;
    }

    public void setMaxReqCount(Integer maxReqCount) {
        this.maxReqCount = maxReqCount;
    }

    public boolean isAvailable(long time){
        if(startDate==null || endDate==null){
            return false;
        }
        return startDate.getTime()<= time && endDate.getTime()>= time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMccmncs() {
        if(mccmncs==null || mccmncs.isEmpty()){
            return "";
        }
        return Joiner.on(",").skipNulls().join(mccmncs);
    }
    public void setMccmncs(String mccmncs) {
        if(Strings.isNullOrEmpty(mccmncs)){
            return;
        }
        this.mccmncs= Sets.newHashSet(Splitter.on(",").omitEmptyStrings().trimResults().split(mccmncs));
    }

    public Set<String> getMccmncsObject(){
        return this.mccmncs;
    }

    public void setMccmncsObject(Set<String> mccmncs){
        this.mccmncs= mccmncs;
    }

    public String getCountries() {
        if(countries==null || countries.isEmpty()){
            return "";
        }
        return Joiner.on(",").skipNulls().join(countries);
    }

    public void setCountries(String countries) {
        if(Strings.isNullOrEmpty(countries)){
            return;
        }
        this.countries = Sets.newHashSet(Splitter.on(",").omitEmptyStrings().trimResults().split(countries));
    }

    public Set<String> getCountriesObject(){
        return this.countries;
    }

    public void setCountriesObject(Set<String> countries){
        this.countries = countries;
    }

    public Set<Integer> getRefIdsObject() {
        return refIds;
    }

    public String getRefIds() {
        if(refIds==null || refIds.isEmpty()){
            return null;
        }
        return Joiner.on(",").join(refIds);
    }

    public void setRefIds(String refIds) {
        if(Strings.isNullOrEmpty(refIds)){
            return;
        }
        Iterator<String> it= Splitter.on(",").omitEmptyStrings().split(refIds).iterator();
        this.refIds = new HashSet<>();
        while (it.hasNext()){
            try {
                this.refIds.add(Integer.valueOf(it.next()));
            }catch (Exception e){
                //do nothings
            }
        }
    }
    public void setRefIdsObject(Set<Integer> refIds) {
        this.refIds = refIds;
    }

    public boolean _isAutoShow(){
        return "y".equals(autoShow);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getAutoShow() {
        return autoShow;
    }

    public void setAutoShow(boolean autoShow){
        this.autoShow = autoShow?"y":"n";
    }
    
    public void setAutoShow(String autoShow) {
        this.autoShow = autoShow;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getShutDown() {
		return shutDown;
	}

	public void setShutDown(String shutDown) {
		this.shutDown = shutDown;
	}
	
	public boolean _isShutDown(){
	    return "y".equals(shutDown);
	}
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Integer getStartDays() {
		return startDays;
	}

	public void setStartDays(Integer startDays) {
		this.startDays = startDays;
	}
    
}
