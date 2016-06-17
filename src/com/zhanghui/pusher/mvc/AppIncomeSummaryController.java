package com.zhanghui.pusher.mvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Pkg;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.AppIncomeSummary;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.service.AppIncomeSummaryService;

@Controller
@RequestMapping("/app_income_summary")
public class AppIncomeSummaryController extends ListController<AppIncomeSummary, AppIncomeSummaryService, AppIncomeSummaryController.Query>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public AppIncomeSummaryController() {
		super("app_income_summary");
	}
	@Resource
	public void setAppIncomeSummaryService(AppIncomeSummaryService appIncomeSummaryService){
		this.service=appIncomeSummaryService;
	}
    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("pkgMap", Pkg.pkgMap);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query, Model model) {
    	SumData sumData = this.service.getSum(paginator);
    	if(sumData!=null){
    		if(sumData.getClickTimes()==0){
    			sumData.setClickPrice(0);
    		}else{
    			sumData.setClickPrice(new BigDecimal((double)sumData.getIncome()/100.0/(double)sumData.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(sumData.getShowTimes()==0){
    			sumData.setClickRate(0);
    			sumData.setShowPrice(0);
    		}else{
    			sumData.setShowPrice(new BigDecimal((double)sumData.getIncome()/(double)sumData.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			sumData.setClickRate(new BigDecimal((double)sumData.getClickTimes()/(double)sumData.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	List<AppIncomeSummary> list = (List<AppIncomeSummary>)model.asMap().get("datas");
    	Iterator<AppIncomeSummary> it = list.iterator();
    	while(it.hasNext()){
    		AppIncomeSummary ta = it.next();
    		if(ta.getClickTimes()==0){
    			ta.setClickPrice(0);
    		}else{
    			ta.setClickPrice(new BigDecimal((double)ta.getIncome()/100.0/(double)ta.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(ta.getShowTimes()==0){
    			ta.setClickRate(0);
    			ta.setShowPrice(0);
    		}else{
    			ta.setShowPrice(new BigDecimal((double)ta.getIncome()/(double)ta.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	model.addAttribute("sum", sumData);
    	model.addAttribute("datas", list);
    	super.postList(page, paginator, query, model);
    }

	public static class Query extends com.zhanghui.pusher.common.Query {
        protected String startTime;
        protected String endTime;

        public Query() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
            this.endTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime.replaceAll("/", "-");
            this.addItem("startTime", startTime);
        }

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime.replaceAll("/", "-");
			this.addItem("endTime", endTime);
		}

    }
    @RequestMapping("/export")
	private void exportAppIncomeTotalXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadAppIncomeTotalTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<AppIncomeSummary> list = service.list(paginator);
        	Iterator<AppIncomeSummary> it = list.iterator();
        	while(it.hasNext()){
        		AppIncomeSummary ta = it.next();
        		if(ta.getClickTimes()==0){
        			ta.setClickPrice(0);
        		}else{
        			ta.setClickPrice(new BigDecimal(ta.getIncome()/(double)ta.getClickTimes()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        		if(ta.getShowTimes()==0){
        			ta.setClickRate(0);
        			ta.setShowPrice(0);
        		}else{
        			ta.setShowPrice(new BigDecimal(ta.getIncome()/(double)ta.getShowTimes()*1000).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
        			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        	}
			for(AppIncomeSummary ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						String.class, Pkg.pkgMap.get(ta.getPkgName())==null?ta.getPkgName():Pkg.pkgMap.get(ta.getPkgName()).getAppName(),//数据库中有的包名没有对应的应用名
						double.class, ta.getRequestTimes().doubleValue(),
						double.class, ta.getShowTimes().doubleValue(),
						double.class, ta.getClickTimes().doubleValue(),
						double.class, ta.getClickRate(),
						double.class, ta.getClickPrice(),
						double.class, new BigDecimal((double)ta.getIncome()/100.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(),
						double.class, ta.getShowPrice()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=app_income_total.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
