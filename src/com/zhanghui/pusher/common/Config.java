package com.zhanghui.pusher.common;

import com.google.common.collect.Sets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

/**
 * 集中定义一些常量
 *
 */
public final class Config {
    private final static Random rand = new Random(System.currentTimeMillis());
	public final static Properties props=new Properties();
	static{
		try {
			props.load(Resources.getResource("config.properties").openStream());
		} catch (IOException e) {
			System.exit(1);
		}
	}
	/** 文件上传根路径*/
	public final static String UPLOAD_ROOT_DIR=props.getProperty("upload.root.dir");
	public final static String DOMAIN=props.getProperty("domain");
    public final static String HOST_URL="http://"+DOMAIN;
	public final static String APP_NAME=props.getProperty("app.name");
    public static String[] DOWNLOAD_URL=props.getProperty("download.url").split("\\s+");
    public final static Set<String> TEST_IDENTITIES= Sets.newHashSet();
//    public final static Set<String> TEST_IDENTITIES= Sets.newHashSet("460019002606393","460028174372880","460028998577977","460013621977906","460013621977909","460013621977911","460023020860632","460029157438486","460012188615308","460012188615306","460023020983429","204043085885479","460028138819880");
    public final static String AAPT=props.getProperty("aapt","aapt");
    public final static String IMAGE_MAGICK_PATH=props.getProperty("image.magick.path","/usr/bin/");
//    public final static Set<String> NO_AD_COUNTRIES=Sets.newHashSet("cn","tw","hk","mo");
    public final static Set<String> NO_AD_COUNTRIES=Sets.newHashSet();
//    public final static Set<String> BLOCKING_FROMS=Sets.newHashSet("9888","9916");
    public final static Set<String> BLOCKING_FROMS=Sets.newHashSet();
    public final static Set<String> BLOCKING_CHANNEL=Sets.newHashSet();

    public final static String randomDownloadURL(){
        if (DOWNLOAD_URL==null || DOWNLOAD_URL.length<1){
            return HOST_URL;
        }
        return DOWNLOAD_URL[rand.nextInt(DOWNLOAD_URL.length)];
    }

    public final static String randomDownloadURL(String prefix, String path){
        if(path==null){
            return "";
        }
        return randomDownloadURL()+prefix+path;
    }
}
