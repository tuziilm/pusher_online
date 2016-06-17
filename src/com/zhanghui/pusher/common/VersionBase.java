/*
package com.zhanghui.pusher.common;

import java.util.*;
import java.util.Collections;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhanghui.pusher.domain.Rule;
*/
/**
 * 定义push版本推相应广告平台
 *
 *//*

public final class VersionBase {
	public final static Map<Integer,Rule> versionBase = initBase();
    public static enum AdType{
        BANNER,SCREEN
    }

	public static Map<Integer,Rule> initBase(){
		Map<Integer,Rule> version =  Maps.newHashMap();
		addVersion(version,1,new Rule(Sets.newHashSet(SdkType.GOOGLE), Collections.EMPTY_SET));
		addVersion(version,2,new Rule(Sets.newHashSet(SdkType.GOOGLE),Collections.EMPTY_SET));
		addVersion(version,3,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI), Collections.EMPTY_SET));
		addVersion(version,4,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI,SdkType.INMOBI),
										Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI,SdkType.INMOBI,SdkType.AIRPUSH)));
		addVersion(version,5,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI),
										Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI,SdkType.INMOBI,SdkType.AIRPUSH)));
		addVersion(version,6,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI,SdkType.STARTAPP),
										Sets.newHashSet(SdkType.GOOGLE,SdkType.YEAHMOBI,SdkType.INMOBI,SdkType.AIRPUSH,SdkType.STARTAPP)));
		addVersion(version,7,new Rule(Collections.EMPTY_SET,Sets.newHashSet(SdkType.CHARTBOOST)));
		addVersion(version,8,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
										Sets.newHashSet(SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
        addVersion(version,9,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
                Sets.newHashSet(SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		addVersion(version,10,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
				Sets.newHashSet(SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		addVersion(version,11,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
				Sets.newHashSet(SdkType.GOOGLE,SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		addVersion(version,12,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
				Sets.newHashSet(SdkType.GOOGLE,SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		addVersion(version,13,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
				Sets.newHashSet(SdkType.GOOGLE,SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		addVersion(version,14,new Rule(Sets.newHashSet(SdkType.GOOGLE,SdkType.INMOBI,SdkType.STARTAPP),
				Sets.newHashSet(SdkType.GOOGLE,SdkType.AIRPUSH,SdkType.INMOBI,SdkType.STARTAPP)));
		return version;
	}
	public static void addVersion(Map<Integer,Rule> version,Integer i,Rule rule){
		version.put(i, rule);
	}
    public static boolean screenSupport(int version, int sdkType){
        return support(AdType.SCREEN, version, sdkType);
    }
    public static boolean bannerSupport(int version, int sdkType){
        return support(AdType.BANNER, version, sdkType);
    }
    public static boolean support(AdType adType, int version, int sdkType){
        Rule rule = versionBase.get(version);
        if(rule==null){
            return false;
        }
        return (adType==AdType.BANNER?rule.getBanner():rule.getScreen()).contains(sdkType);
    }
}
*/
