package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.domain.TotalData;
import com.zhanghui.pusher.persistence.TotalDataMapper;

@Service
public class TotalDataService extends BaseService<TotalData> {
	private TotalDataMapper totalDataMapper;

	@Autowired
	public void setTotalDataMapper(TotalDataMapper totalDataMapper) {
		this.mapper = totalDataMapper;
		this.totalDataMapper =totalDataMapper;
	}
	public SumData getSum(Paginator page){
		return this.totalDataMapper.getSum(page);
	}
}
