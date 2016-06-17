package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.domain.TotalData;

public interface TotalDataMapper extends BaseMapper<TotalData> {
	SumData getSum(Paginator page);
}
