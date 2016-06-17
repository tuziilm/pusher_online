package com.zhanghui.pusher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StatActionType {
    public final static List<StatActionType> types = initTypes();
    public final static Map<String, StatActionType> id2TypeMap = initId2TypeMap();

    private String id;
    private String name;

    public StatActionType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private static Map<String, StatActionType> initId2TypeMap() {
        Map<String, StatActionType> map = new HashMap<>(types.size());
        for (StatActionType type : types) {
            map.put(type.getId(), type);
        }
        return map;
    }

    private static List<StatActionType> initTypes() {
        List<StatActionType> types = new ArrayList<StatActionType>(10);
        types.add(new StatActionType("all", "所有操作"));
        types.add(new StatActionType("10001", "展示"));
        types.add(new StatActionType("10002", "点击"));
        types.add(new StatActionType("10003", "下载"));
        types.add(new StatActionType("10004", "安装"));
        types.add(new StatActionType("10005", "多应用点击"));
        types.add(new StatActionType("10001", "展示"));
        types.add(new StatActionType("20001", "桌面快捷方式点击"));
        types.add(new StatActionType("20002", "桌面快捷方式安装"));
        return types;
    }

    public static List<StatActionType> getTypes() {
        return types;
    }

    public static Map<String, StatActionType> getId2TypeMap() {
        return id2TypeMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
