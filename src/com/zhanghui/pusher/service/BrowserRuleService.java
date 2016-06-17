package com.zhanghui.pusher.service;

import com.zhanghui.pusher.domain.BrowserRule;
import com.zhanghui.pusher.persistence.BrowserRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BrowserRuleService extends ListBasedCacheSupportService<BrowserRule> {
    @Autowired
    public void setBrowserRuleMapper(BrowserRuleMapper browserRuleMapper) {
        this.mapper = browserRuleMapper;
    }

    public Collection<BrowserRule> getBrowserRulesCache(){
        return getCache();
    }
}
