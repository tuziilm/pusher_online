package com.zhanghui.pusher.service;

import com.google.common.collect.Lists;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.HighChartData;
import com.zhanghui.pusher.domain.PageView;
import com.zhanghui.pusher.domain.Series;
import com.zhanghui.pusher.persistence.PageViewMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 页面统计服务
 * <pre>
 * use LogStatistics instead
 * </pre>
 * 
 */
@Service
public class PageViewService extends BaseService<PageView> {
	private PageViewMapper pageViewMapper;

	@Autowired
	public void setPageViewMapper(PageViewMapper pageViewMapper) {
		this.mapper = pageViewMapper;
		this.pageViewMapper =pageViewMapper;
	}

    public List<PageView> getChartDatas(Paginator paginator){
        return pageViewMapper.getChartDatas(paginator);
    }

    public HighChartData handleList(List<PageView> list, String startTime, String endTime) throws Exception{
        HighChartData hcd = new HighChartData();
        List<String> dateList = getAllDates(startTime, endTime);
        Series pv = new Series("PV", dateList.size());
        Series uv = new Series("UV", dateList.size());
        hcd.setDate(dateList);
        hcd.setSeries(Lists.newArrayList(pv, uv));
        Map<String, PageView> datas = convertLinkNodeAccessLogStatistics2Map(list);
        for(String date : dateList){
            PageView pageView = datas.get(date);
            if(pageView==null){
                pv.addQuantity(0);
                uv.addQuantity(0);
            }else {
                pv.addQuantity(pageView.getPv());
                uv.addQuantity(pageView.getUv());
            }
        }
        return hcd;
    }

    private Map<String, PageView> convertLinkNodeAccessLogStatistics2Map(List<PageView> list) {
        Map<String, PageView> map = new HashMap<>(list.size());
        for(PageView s : list){
            map.put(s.getSchedule(), s);
        }
        return map;
    }

    /**
     * 获取查询时间段内的所有日期字符串
     */
    private List<String> getAllDates(String startTime, String endTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin=sdf.parse(startTime);
        Date end=sdf.parse(endTime);
        if(begin.after(end)){
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>();
        while(true){
            list.add(sdf.format(begin));
            if(begin.compareTo(end)==0){
                break;
            }
            begin = DateUtils.addDays(begin, 1);
        }
        return list;
    }
}
