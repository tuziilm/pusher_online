package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.AdOwnerData;
import com.zhanghui.pusher.domain.SumData;

public interface AdOwnerDataMapper extends BaseMapper<AdOwnerData> {
	SumData getSum(Paginator page);
}
