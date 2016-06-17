package com.zhanghui.pusher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AppType {
    public final static List<AppType> types = initTypes();
    public final static Map<Integer,AppType> id2AppTypeMap= initId2AppTypeMap();

    private Integer id;
    private String name;

    private static Map<Integer,AppType> initId2AppTypeMap() {
        Map<Integer, AppType> map=new HashMap<>(types.size());
        for(AppType type : types){
            map.put(type.getId(), type);
        }
        return map;
    }

    private static List<AppType> initTypes() {
        List<AppType> types=new ArrayList<AppType>(26);
        types.add(new AppType(16,"游戏"));
        types.add(new AppType(17,"美女"));
        types.add(new AppType(18,"桌面"));
        types.add(new AppType(19,"社交"));
        types.add(new AppType(20,"应用市场"));
        types.add(new AppType(21,"工具"));
        types.add(new AppType(22,"购物"));
        types.add(new AppType(23,"新闻"));
        types.add(new AppType(24,"壁纸"));
        types.add(new AppType(15,"其它"));
        return types;
    }


    public AppType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<AppType> getTypes() {
		return types;
	}
    
}
