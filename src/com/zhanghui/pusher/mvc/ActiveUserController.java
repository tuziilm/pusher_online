package com.zhanghui.pusher.mvc;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.pusher.common.AdOwner;
import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.From;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.ActiveUser;
import com.zhanghui.pusher.service.ActiveUserService;
import com.zhanghui.pusher.statistics.common.Config;

@Controller
@RequestMapping("/active_user")
public class ActiveUserController extends ListController<ActiveUser, ActiveUserService, ActiveUserController.Query> {
	private final Logger log=LoggerFactory.getLogger(getClass());
	public ActiveUserController() {
		super("active_user");
	}
	@Resource
	public void setActiveUserService(ActiveUserService activeUserService){
		this.service=activeUserService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("froms", From.getFroms());
        model.addAttribute("adowners", AdOwner.getTypes());
        model.addAttribute("countries", Country.countries);
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query,	Model model){
        List<ActiveUser> list = service.select(paginator);
    	List<ActiveUser> resultList = new ArrayList<ActiveUser>();
    	//Map<时间,Map<国家&from,Object>>
    	Map<String,Map<String,ActiveUser>> resultMap = new HashMap<String, Map<String,ActiveUser>>();
    	//将所有数据按日期封装到相应的Map对象
    	for(ActiveUser ac : list){
    		if(!resultMap.containsKey(ac.getDate())){
    			Map<String,ActiveUser> map = new HashMap<String,ActiveUser>();
    			map.put(Config.SEP+ac.getCountry()+Config.SEP+ac.getFrom(), ac);
    			resultMap.put(ac.getDate(),map);
    		}else{
    			resultMap.get(ac.getDate()).put(Config.SEP+ac.getCountry()+Config.SEP+ac.getFrom(), ac);
    		}
    	}
    	try {
    		//求留存率
 			List<String> dateList =  getAllDates(query.startTime, query.endTime);
			for(int i = dateList.size()-1 ; i > 0 ; i--){
				//当前日期的数据
				Map<String,ActiveUser> currMap = resultMap.get(dateList.get(i)); 
				//前一天的数据
				Map<String,ActiveUser> preMap = resultMap.get(dateList.get(i-1));
				if(currMap!=null){
					for (String key : currMap.keySet()) {
						ActiveUser ac = currMap.get(key);
						if(preMap!=null&&preMap.containsKey(key)){
							ac.setRetentionRate(new BigDecimal((double)ac.getActiveUser()*100/(double)(preMap.get(key).getActiveUser()+ac.getAddUser())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						}else{
							ac.setRetentionRate(100.0);
						}
						resultList.add(ac);
					}
				}
			}
		} catch (ParseException e) {
			log.error("failure to parse", e);
		}
    	//分页
    	int fromPage = 0+12*(page-1);
    	int toPage = 12+12*(page-1);
    	if(resultList.size() == 0){
    		paginator.setHasData(false);
    	}else if(resultList.size() <= 12){
    		model.addAttribute("results", resultList);
    		paginator.setHasNextPage(false);
    	}else if(resultList.size() < toPage){
    		model.addAttribute("results", resultList.subList(fromPage, resultList.size()));
    		paginator.setHasNextPage(false);
    	}else if(resultList.size() > toPage){
    		model.addAttribute("results", resultList.subList(fromPage, toPage));
    		paginator.setHasNextPage(true);
    	}
    	paginator.setTotal(resultList.size());
    	super.postList(page, paginator, query, model);
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
    public static class Query extends com.zhanghui.pusher.common.Query {
    	protected String country;
    	protected String from;
        protected String startTime;
        protected String endTime;

        public Query() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -2), "yyyy-MM-dd");
            this.endTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime.replaceAll("/", "-");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
				Date begin=sdf.parse(this.startTime);
				begin = DateUtils.addDays(begin, -1);
				this.startTime = sdf.format(begin);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            this.addItem("startTime", startTime);
        }

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime.replaceAll("/", "-");
			this.addItem("endTime", endTime);
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
			this.addItem("country", country);
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
			this.addItem("from", from);
		}
		
    }
    @RequestMapping("/export")
	private void exportActiveUserXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadActiveUserTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
	        List<ActiveUser> list = service.select(paginator);
	    	List<ActiveUser> resultList = new ArrayList<ActiveUser>();
	    	//Map<时间,Map<国家&from,Object>>
	    	Map<String,Map<String,ActiveUser>> resultMap = new HashMap<String, Map<String,ActiveUser>>();
	    	//将所有数据按日期封装到相应的Map对象
	    	for(ActiveUser ac : list){
	    		if(!resultMap.containsKey(ac.getDate())){
	    			Map<String,ActiveUser> map = new HashMap<String,ActiveUser>();
	    			map.put(Config.SEP+ac.getCountry()+Config.SEP+ac.getFrom(), ac);
	    			resultMap.put(ac.getDate(),map);
	    		}else{
	    			resultMap.get(ac.getDate()).put(Config.SEP+ac.getCountry()+Config.SEP+ac.getFrom(), ac);
	    		}
	    	}
	    	try {
	    		//求留存率
	 			List<String> dateList =  getAllDates(query.startTime, query.endTime);
				for(int i = dateList.size()-1 ; i > 0 ; i--){
					//当前日期的数据
					Map<String,ActiveUser> currMap = resultMap.get(dateList.get(i)); 
					//前一天的数据
					Map<String,ActiveUser> preMap = resultMap.get(dateList.get(i-1));
					if(currMap!=null){
						for (String key : currMap.keySet()) {
							ActiveUser ac = currMap.get(key);
							if(preMap!=null&&preMap.containsKey(key)){
								ac.setRetentionRate(new BigDecimal((double)ac.getActiveUser()*100/(double)(preMap.get(key).getActiveUser()+ac.getAddUser())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
							}else{
								ac.setRetentionRate(100.0);
							}
							resultList.add(ac);
						}
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for(ActiveUser ta : resultList ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						String.class, ta.getCountry().equals("all")?"所有国家":Country.shortcut2CountryMap.get(ta.getCountry()).getChineseName(),
						String.class, ta.getFrom(),
						double.class, ta.getActiveUser().doubleValue(),
						double.class, ta.getValidActiveUser().doubleValue(),
						double.class, ta.getAddUser().doubleValue(),
						double.class, ta.getRetentionRate()	
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=active_user.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
