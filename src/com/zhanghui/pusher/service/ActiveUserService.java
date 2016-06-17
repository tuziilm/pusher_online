package com.zhanghui.pusher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.ActiveUser;
import com.zhanghui.pusher.persistence.ActiveUserMapper;

@Service
public class ActiveUserService extends BaseService<ActiveUser> {
	private ActiveUserMapper activeUserMapper;

	@Autowired
	public void setActiveUserMapper(ActiveUserMapper activeUserMapper) {
		this.mapper = activeUserMapper;
		this.activeUserMapper =activeUserMapper;
	}
	
	public List<ActiveUser> select(Paginator page){
		return activeUserMapper.select(page);
	}
}
