package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.AppIncomeSummary;
import com.zhanghui.pusher.domain.SumData;

public interface AppIncomeSummaryMapper extends BaseMapper<AppIncomeSummary> {
	SumData getSum(Paginator page);
}
