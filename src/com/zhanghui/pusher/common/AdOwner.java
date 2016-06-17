package com.zhanghui.pusher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 广告平台
 * @author Administrator
 *
 */
public final class AdOwner {
    public final static List<AdOwner> adOwners = initTypes();
    public final static Map<String,AdOwner> code2AdOwnerMap= initCode2AdOwnerMap();

    private String code;
    private String name;

    private static Map<String,AdOwner> initCode2AdOwnerMap() {
        Map<String, AdOwner> map=new HashMap<>(adOwners.size());
        for(AdOwner owner : adOwners){
            map.put(owner.getCode(), owner);
        }
        return map;
    }

    private static List<AdOwner> initTypes() {
        List<AdOwner> types=new ArrayList<AdOwner>(6);
        types.add(new AdOwner("admob","admob"));
        types.add(new AdOwner("yeahmobi","yeahmobi"));
        types.add(new AdOwner("inmobi-api","inmobi-api"));
        types.add(new AdOwner("airpush","airpush"));
        types.add(new AdOwner("startapp","startapp"));
        types.add(new AdOwner("chartboost","chartboost"));
        types.add(new AdOwner("inmobi-sdk","inmobi-sdk"));
        return types;
    }


    public AdOwner(String code, String name) {
        this.code = code;
        this.name = name;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<AdOwner> getTypes() {
		return adOwners;
	}
    
}
