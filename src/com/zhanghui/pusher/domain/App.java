package com.zhanghui.pusher.domain;

import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.zhanghui.pusher.common.IconType;

public class App extends RemarkId{
    /** Ӧ������*/
    private String name;
    /**Ӧ�����ֻ�����ʾ������*/
    private String appLabel;
    /** �ļ���С*/
    private Long appSize;
    private Size appSizeObject;
    /** �汾��*/
    private String version;
    /** Ӧ��·��*/
    private String appPath;
    /**Ӧ���ļ���ԭʼ����*/
    private String appFileName;
    /**Ӧ�õ�ͼ��,0:ԭͼ��1��300���µ�����ͼ*/
    private String[] appIcon;
    /** Ӧ�õ��ڲ��汾��*/
    private Integer appVersionCode;
    /** Ӧ�õİ���*/
    private String appPackageName;
    /** Ӧ�õ���Сsdk�汾*/
    private Integer appMinSdkVersion;
    /** ���� */
    private String desc;
    /**������*/
    private String dlNum;
    /**�����*/
    private Integer adOwner;
    /**����*/
    private Set<String> supportCountries;
    /**����*/
    private String price;
    /**Ӧ������*/
    private Integer type;
    /**��ת����*/
    private Integer redirectType;
    /**�Ƿ��Զ�����*/
    private Byte autoDl;
    /**�Ƿ��Զ���*/
    private Byte autoOpen;
    /**���绷��*/
    private String netEnvironment;

    public String getAppLabel() {
        return appLabel;
    }

    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    public String getDlNum() {
        return dlNum;
    }

    public void setDlNum(String dlNum) {
        this.dlNum = dlNum;
    }

    public Size getAppSizeObject() {
        return appSizeObject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAppSize() {
        return appSize;
    }

    public void setAppSize(Long appSize) {
        this.appSize = appSize;
        this.appSizeObject = Size.valueOf(appSize==null?0L:appSize);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppFileName() {
        return appFileName;
    }

    public void setAppFileName(String appFileName) {
        this.appFileName = appFileName;
    }

    public String getAppIcon() {
        if (appIcon==null||appIcon.length<1){
            return null;
        }
        return Joiner.on(",").join(appIcon);
    }

    public String getAppIcon(IconType iconType){
        if(appIcon==null || appIcon.length<1){
            return null;
        }
        if(iconType == IconType.ORI){
            return appIcon[0];
        }else if(iconType == IconType.M300 && appIcon.length>1){
            return appIcon[1];
        }
        return appIcon[0];
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon.split(",");
    }

    public String[] getAppIconObject(){
        return appIcon;
    }

    public void setAppIconObject(String[] appIconObject){
        this.appIcon = appIconObject;
    }

    public Integer getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(Integer appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public Integer getAppMinSdkVersion() {
        return appMinSdkVersion;
    }

    public void setAppMinSdkVersion(Integer appMinSdkVersion) {
        this.appMinSdkVersion = appMinSdkVersion;
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

	public Byte getAutoDl() {
		return autoDl;
	}

    public boolean _isAutoDl(){
        return autoDl!=null && autoDl== 1;
    }

	public void setAutoDl(Byte autoDl) {
		this.autoDl = autoDl;
	}

	public Byte getAutoOpen() {
		return autoOpen;
	}

    public boolean _isAutoOpen(){
        return autoOpen != null && autoOpen == 1;
    }

	public void setAutoOpen(Byte autoOpen) {
		this.autoOpen = autoOpen;
	}

	public String getNetEnvironment() {
		return netEnvironment;
	}

	public void setNetEnvironment(String netEnvironment) {
		this.netEnvironment = netEnvironment;
	}
	
}