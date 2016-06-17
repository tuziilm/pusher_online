package com.zhanghui.pusher.mvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhanghui.pusher.common.IdForm;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.domain.SumData;
import com.zhanghui.pusher.domain.TotalData;
import com.zhanghui.pusher.service.TotalDataService;

@Controller
@RequestMapping("/data_statistics/total")
public class TotalDataController extends CRUDController<TotalData, TotalDataService, com.zhanghui.pusher.mvc.TotalDataController.Form, TotalDataController.Query>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	public TotalDataController() {
		super("data_statistics/total");
	}
	@Resource
	public void setTotalDataService(TotalDataService totalDataService){
		this.service=totalDataService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query query, Model model) {
        paginator.setNeedTotal(true);
        return super.preList(page, paginator, query, model);
    }
    @Override
    protected void postList(int page, Paginator paginator, Query query, Model model) {
    	SumData sumData = service.getSum(paginator);
    	if(sumData!=null){
    		if(sumData.getShowTimes()==0){
    			sumData.setShowPrice(0);
    			sumData.setClickRate(0);
    		}else{
    			sumData.setShowPrice(new BigDecimal((double)(sumData.getIncome()/100)/(double)sumData.getShowTimes()*1000).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			sumData.setClickRate(new BigDecimal((double)sumData.getClickTimes()/(double)sumData.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(sumData.getRequestTimes()==0){
    			sumData.setRequestSuccessRate(0);
    		}else{
    			sumData.setRequestSuccessRate(new BigDecimal((double)sumData.getShowTimes()/(double)sumData.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	List<TotalData> list = (List<TotalData>)model.asMap().get("datas");
    	Iterator<TotalData> it = list.iterator();
    	while(it.hasNext()){
    		TotalData ta = it.next();
    		if(ta.getShowTimes()==0){
    			ta.setShowPrice(0);
    			ta.setClickRate(0);
    		}else{
    			ta.setShowPrice(new BigDecimal((double)ta.getIncome()/(double)ta.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
    			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    		if(ta.getRequestTimes()==0){
    			ta.setReqsuccessRate(0);
    		}else{
    			ta.setReqsuccessRate(new BigDecimal((double)ta.getShowTimes()/(double)ta.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
    		}
    	}
    	model.addAttribute("sum", sumData);
    	model.addAttribute("datas", list);
    	super.postList(page, paginator, query, model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
    	TotalData totalData=form.toObj();
    	service.update(totalData);
	}

	public static class Form extends IdForm<TotalData> {
		private double income;

        @Override
        public TotalData newObj() {
            return new TotalData();
        }

        @Override
        public void populateObj(TotalData totalData) {
        	totalData.setIncome((int)(income*100));
        }

		public double getIncome() {
			return income;
		}

		public void setIncome(double income) {
			this.income = income;
		}
    }
	public static class Query extends com.zhanghui.pusher.common.Query {
        protected String startTime;
        protected String endTime;

        public Query() {
        	this.startTime = DateFormatUtils.format(DateUtils.addMonths(new Date(), -1), "yyyy-MM-dd");
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
	private void exportTotalDataXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadTotalDataTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			paginator.setQuery(query);
            List<TotalData> list = service.list(paginator);
        	Iterator<TotalData> it = list.iterator();
        	while(it.hasNext()){
        		TotalData ta = it.next();
        		if(ta.getShowTimes()==0){
        			ta.setShowPrice(0);
        			ta.setClickRate(0);
        		}else{
        			ta.setShowPrice(new BigDecimal((double)ta.getIncome()/(double)ta.getShowTimes()*10).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());//千次展示价格
        			ta.setClickRate(new BigDecimal((double)ta.getClickTimes()/(double)ta.getShowTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        		if(ta.getRequestTimes()==0){
        			ta.setReqsuccessRate(0);
        		}else{
        			ta.setReqsuccessRate(new BigDecimal((double)ta.getShowTimes()/(double)ta.getRequestTimes()*100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
        		}
        	}
			for(TotalData ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                Object[] datas = new Object[]{
						String.class, ta.getDate(),
						double.class, ta.getPluginActiveUser().doubleValue(),
						double.class, ta.getPluginLostUser().doubleValue(),
						double.class, ta.getActiveUser().doubleValue(),
						double.class, ta.getLostUser().doubleValue(),
						double.class, ta.getValidActiveUser().doubleValue(),
						double.class, ta.getValidLostUser().doubleValue(),
						double.class, ta.getRequestTimes().doubleValue(),
						double.class, ta.getShowTimes().doubleValue(),
						double.class, ta.getReqsuccessRate(),	
						double.class, ta.getClickTimes().doubleValue(),
						double.class, ta.getClickRate(),
						double.class, new BigDecimal((double)ta.getIncome()/100.0).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(),
						double.class, ta.getShowPrice()
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=total_data.xlsx");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
