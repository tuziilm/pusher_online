package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.Link;
import com.zhanghui.pusher.persistence.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LinkService extends ObjectBasedGroupCacheSupportService<Link> {
    private final static String LIST_ALL_KEY="list_all_key";
    private final static String MAP_ALL_KEY="map_all_key";
	private LinkMapper linkMapper;
	@Autowired
	public void setLinkMapper(LinkMapper linkMapper) {
		this.mapper = linkMapper;
		this.linkMapper=linkMapper;
	}

    @Override
    public String[] cacheGroupKeys() {
        return new String[]{LIST_ALL_KEY, MAP_ALL_KEY};
    }

    @Override
    public Object newObject(String cacheGroupKey) {
        if(cacheGroupKey.startsWith("map")){
            return new HashMap<String, Link>();
        }else{
            return new ArrayList<Link>();
        }
    }

    @Override
    public void updateCacheList(Map<String, Object> update, Link link) {
        ((List<Link>)update.get(LIST_ALL_KEY)).add(link);
        ((Map<Integer, Link>)update.get(MAP_ALL_KEY)).put(link.getId(), link);
    }
    public List<Link> getAllLinksCache(){
        return (List<Link>)getCache(LIST_ALL_KEY);
    }

    public Map<Integer, Link> getAllLinksMapCache(){
        return (Map<Integer, Link>)getCache(MAP_ALL_KEY);
    }
}
