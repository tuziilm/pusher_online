package com.zhanghui.pusher.domain;

/**
 * 
 * 终端用户表
 *
 */
public class User  extends Id {
    /**
     * 服务器端应当以imsi标识用户唯一身份，客户端要保证传递这个参数给服务器端，就算是没有通话功能的的平板电脑也要传这个参数(这种情况平板电脑客户端会随机生成一个UUID做为imsi值传给服务器
     */
    private String identity;
    /**
     * 渠道商KEY（字母和数字的组合，长度32位，如：845c155316284c3982ef08a1dd86aa1f），提交服务器时的参数名称
     */
    private String from;
    /**
     * 是一串64位的编码（十六进制的字符串），是随机生成的。用户重新刷机或恢复出厂设置时，可能会重新分配
     */
    private String androidId;
    /**
     * 蓝牙的物理地址
     */
    private String btMac;
    private String isPad;//如果是平板电脑的话此值为y，否则为n，客户端这边把没有通话硬件模块的设备当做平板电脑，有通话硬件模块的就当做手机
    private String mac;//wifi的物理地址
    private String imei;//IMEI号
    private String imsi;//手机sim卡对应的串号
    private String version;//插件版本，即这个插件的版本号，这个是我们自己定义的
    private String androidVer;//android;//系统版本，比如2.3.5
    private Integer androidLevel;//Android  API  Level，比如 10
    private Integer wifi;//可选，表示手机端此时此刻在调用这个接口时使用的网络类型，1表示WIFI，0表示GPRS
    private String apkName;//带有这个插件的APK的名称，如  新浪微博1.2
    private String pkgName;//表示本应用的包名，这个参数可以标识这个产品的唯一性
    private String versionName;//表示本身这个apk应用的版本，字符串，这是给用户看的，用户在应用管理器里看到的应用版本，即系AndroidManifest.xml里定义的 android;//versionName="12.1"
    private String versionCode;//表示应用实际的版号，整数，这个是给开发者使用的，在检测升级时用到，即系AndroidManifest.xml里定义的 android;//versionCode="12"
    private String mcc;//为SIM卡对应的移动国家号码，由3位数字组成，唯一地识别移动客户所属的国家，中国为460
    private String mnc;//为SIM卡对应的网络id，由2位数字组成(有的国家可能是3位?)，用于识别移动客户所归属的移动网络，比如 中国移动为00，中国联通为01,中国电信为03
    private String simCountry;//SIM卡所在国家的缩写，比如 cn
    private String operatorName;//SIM卡使用的网络运营商名称，比如 中国移动
    private Integer sdcardCountSpare;//表示SDCARD空间总大小,单位为MB
    private Integer sdcardAvailableSpare;//表示SDCARD空间剩余大小，单位为MB
    private Integer systemCountSpare;//表示系统空间总大小,单位为MB
    private Integer systemAvailableSpare;//表示系统空间剩余大小,单位为MB
    private String resolution;//表示手机分辨率
    private String brand;//表示手机品牌
    private String model;//表示手机型号
    private String sysApps;//系统空间(/system/app/)里的应用信息，数据格式，请参考 备注1
    private String userApps;//用户空间(/data/app/)里的应用信息，，数据格式，请参考 备注1
    private String smsc;//表示短信中心号码
    private Integer inSys;//表示本身这个apk是不是在系统空间里（即/system/app/），是的话用1表示，不是的话用0表示
    private String loc;//表示gps坐标
    public String getNetwork() {
        return wifi == null ? "未知" : (wifi == 1 ? "wifi" : "gprs");
    }

    public boolean isPad(){
        return "y".equals(isPad);
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getBtMac() {
        return btMac;
    }

    public void setBtMac(String btMac) {
        this.btMac = btMac;
    }

    public String getIsPad() {
        return isPad;
    }

    public void setIsPad(String isPad) {
        this.isPad = isPad;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAndroidVer() {
        return androidVer;
    }

    public void setAndroidVer(String androidVer) {
        this.androidVer = androidVer;
    }

    public Integer getAndroidLevel() {
        return androidLevel;
    }

    public void setAndroidLevel(Integer androidLevel) {
        this.androidLevel = androidLevel;
    }

    public Integer getWifi() {
        return wifi;
    }

    public void setWifi(Integer wifi) {
        this.wifi = wifi;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMnc() {
        return mnc;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public String getSimCountry() {
        return simCountry;
    }

    public void setSimCountry(String simCountry) {
        this.simCountry = simCountry;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getSdcardCountSpare() {
        return sdcardCountSpare==null?-1:sdcardCountSpare;
    }

    public void setSdcardCountSpare(Integer sdcardCountSpare) {
        this.sdcardCountSpare = sdcardCountSpare;
    }

    public Integer getSdcardAvailableSpare() {
        return sdcardAvailableSpare==null?-1:sdcardAvailableSpare;
    }

    public void setSdcardAvailableSpare(Integer sdcardAvailableSpare) {
        this.sdcardAvailableSpare = sdcardAvailableSpare;
    }

    public Integer getSystemCountSpare() {
        return systemCountSpare==null?-1:systemCountSpare;
    }

    public void setSystemCountSpare(Integer systemCountSpare) {
        this.systemCountSpare = systemCountSpare;
    }

    public Integer getSystemAvailableSpare() {
        return systemAvailableSpare==null?-1:systemAvailableSpare;
    }

    public void setSystemAvailableSpare(Integer systemAvailableSpare) {
        this.systemAvailableSpare = systemAvailableSpare;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSysApps() {
        return sysApps;
    }

    public void setSysApps(String sysApps) {
        this.sysApps = sysApps;
    }

    public String getUserApps() {
        return userApps;
    }

    public void setUserApps(String userApps) {
        this.userApps = userApps;
    }

    public String getSmsc() {
        return smsc;
    }

    public void setSmsc(String smsc) {
        this.smsc = smsc;
    }

    public Integer getInSys() {
        return inSys;
    }

    public void setInSys(Integer inSys) {
        this.inSys = inSys;
    }

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
    
}
