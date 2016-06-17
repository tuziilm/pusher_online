package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.CountryData;
import com.zhanghui.pusher.domain.SumData;

public interface CountryDataMapper extends BaseMapper<CountryData> {
	SumData getSum(Paginator page);
}
