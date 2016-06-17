package com.zhanghui.pusher.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.HighChartData;
import com.zhanghui.pusher.domain.PageView;
import com.zhanghui.pusher.domain.Series;
import com.zhanghui.pusher.domain.TaskActionView;
import com.zhanghui.pusher.persistence.PageViewMapper;
import com.zhanghui.pusher.persistence.TaskActionViewMapper;

/**
 * 页面统计服务
 * <pre>
 * use LogStatistics instead
 * </pre>
 * 
 */
@Service
public class TaskActionViewService extends BaseService<TaskActionView> {
	private TaskActionViewMapper taskActionViewMapper;

	@Autowired
	public void setTaskActionViewMapper(TaskActionViewMapper taskActionViewMapper) {
		this.mapper = taskActionViewMapper;
		this.taskActionViewMapper =taskActionViewMapper;
	}

}
