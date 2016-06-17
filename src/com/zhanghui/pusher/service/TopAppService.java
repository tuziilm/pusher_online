package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.domain.TopApp;
import com.zhanghui.pusher.persistence.TopAppMapper;

@Service
public class TopAppService extends BaseService<TopApp> {
	private TopAppMapper topAppMapper;

	@Autowired
	public void setTopAppMapper(TopAppMapper topAppMapper) {
		this.mapper = topAppMapper;
		this.topAppMapper =topAppMapper;
	}
}
