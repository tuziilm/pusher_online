package com.zhanghui.pusher.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class Link extends RemarkId {
    private String name;
    private String url;
    private String imgPath;
    private String imgFileName;
    /**广告主*/
    private Integer adOwner;
    /**国家*/
    private Set<String> supportCountries;
    /**单价*/
    private String price;
    /**应用类型*/
    private Integer type;
    /**跳转类型*/
    private Integer redirectType;
    
    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getsupportCountries() {
        if(supportCountries==null || supportCountries.isEmpty()){
            return "";
        }
        return Joiner.on(",").skipNulls().join(supportCountries);
    }

    public void setSupportCountries(String supportCountries) {
        if(Strings.isNullOrEmpty(supportCountries)){
            return;
        }
        this.supportCountries = Sets.newHashSet(Splitter.on(",").omitEmptyStrings().trimResults().split(supportCountries));
    }

    public Set<String> getSupportCountriesObject(){
        return this.supportCountries;
    }

    public void setSupportCountriesObject(Set<String> supportCountries){
        this.supportCountries = supportCountries;
    }

	public Integer getAdOwner() {
		return adOwner;
	}

	public void setAdOwner(Integer adOwner) {
		this.adOwner = adOwner;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(Integer redirectType) {
		this.redirectType = redirectType;
	}
	
}
