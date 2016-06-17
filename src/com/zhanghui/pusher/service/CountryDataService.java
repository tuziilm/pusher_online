package com.zhanghui.pusher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.CountryData;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.persistence.CountryDataMapper;

@Service
public class CountryDataService extends BaseService<CountryData> {
	private CountryDataMapper countryDataMapper;

	@Autowired
	public void setTotalDataMapper(CountryDataMapper countryDataMapper) {
		this.mapper = countryDataMapper;
		this.countryDataMapper =countryDataMapper;
	}
	public SumData getSum(Paginator page){
		return this.countryDataMapper.getSum(page);
	}
}
