package com.zhanghui.pusher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * from
 * @author Administrator
 *
 */
public final class From {
    public final static List<From> froms = initFroms();
    public final static Map<String,From> fromMap= initFromMap();

    private String code;
    private String name;

    private static Map<String,From> initFromMap() {
        Map<String, From> map=new HashMap<>(froms.size());
        for(From from : froms){
            map.put(from.getCode(), from);
        }
        return map;
    }

    private static List<From> initFroms() {
        List<From> froms=new ArrayList<From>(13);
        froms.add(new From("9942","9942"));
        froms.add(new From("9916","9916"));
        froms.add(new From("9875","9875"));
        froms.add(new From("9874","9874"));
        froms.add(new From("9841","9841"));
        froms.add(new From("9840","9840"));
        froms.add(new From("9839","9839"));
        froms.add(new From("9838","9838"));
        froms.add(new From("9837","9837"));
        froms.add(new From("9818","9818"));
        froms.add(new From("9772","9772"));
        froms.add(new From("9733","9733"));
        froms.add(new From("9732","9732"));
        froms.add(new From("9722","9722"));
        froms.add(new From("9695","9695"));
        froms.add(new From("9679","9679"));
        froms.add(new From("9678","9678"));
        froms.add(new From("9677","9677"));
        froms.add(new From("9676","9676"));
        froms.add(new From("9416","9416"));
        froms.add(new From("9184","9184"));
        return froms;
    }


    public From(String code, String name) {
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

	public static List<From> getFroms() {
		return froms;
	}
    
}
