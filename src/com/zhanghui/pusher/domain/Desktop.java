package com.zhanghui.pusher.domain;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Desktop extends RemarkStatusId {
    private String name;
    private Integer type;
    private Set<Integer> refIds;
    private String mtitle;
    private Set<String> countries;
    private Integer priority;
    private String iconPath;

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

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
    
}
