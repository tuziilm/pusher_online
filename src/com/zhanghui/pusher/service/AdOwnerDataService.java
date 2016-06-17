package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.AdOwnerData;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.persistence.AdOwnerDataMapper;

@Service
public class AdOwnerDataService extends BaseService<AdOwnerData> {
	private AdOwnerDataMapper adOwnerDataMapper;

	@Autowired
	public void setTotalDataMapper(AdOwnerDataMapper adOwnerDataMapper) {
		this.mapper = adOwnerDataMapper;
		this.adOwnerDataMapper =adOwnerDataMapper;
	}
	public SumData getSum(Paginator page){
		return this.adOwnerDataMapper.getSum(page);
	}
}
