package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.domain.AppIncome;
import com.zhanghui.pusher.persistence.AppIncomeMapper;

@Service
public class AppIncomeService extends BaseService<AppIncome> {
	private AppIncomeMapper appIncomeMapper;

	@Autowired
	public void setAppIncomeMapper(AppIncomeMapper appIncomeMapper) {
		this.mapper = appIncomeMapper;
		this.appIncomeMapper =appIncomeMapper;
	}
	
}
