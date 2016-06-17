package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.AppIncomeSummary;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.persistence.AppIncomeSummaryMapper;

@Service
public class AppIncomeSummaryService extends BaseService<AppIncomeSummary> {
	private AppIncomeSummaryMapper appIncomeSummaryMapper;

	@Autowired
	public void setAppInfoSummaryMapper(AppIncomeSummaryMapper appIncomeSummaryMapper) {
		this.mapper = appIncomeSummaryMapper;
		this.appIncomeSummaryMapper =appIncomeSummaryMapper;
	}
	public SumData getSum(Paginator page){
		return this.appIncomeSummaryMapper.getSum(page);
	}
}
