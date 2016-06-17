package com.zhanghui.pusher.service;

import com.zhanghui.pusher.common.LogModule;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LinkNodeService {
    private Map<String, LinkNode> map;
    private List<LinkNode> list;

    public LinkNodeService(){
        list = new ArrayList<>(LogModule.values().length);
        map = new HashMap<>(LogModule.values().length);
        for(LogModule lm : LogModule.values()){
            LinkNode linkNode = new LinkNode(lm.name(),lm.getTitle());
            list.add(linkNode);
            map.put(lm.name(), linkNode);
        }
    }
    public class LinkNode{
        private String code;
        private String name;

        public LinkNode(String code, String name){
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
    }

    public List<LinkNode> getList() {
        return list;
    }

    public Map<String, LinkNode> getMap() {
        return map;
    }
}
