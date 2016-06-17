package com.zhanghui.pusher.mvc;

import java.util.ArrayList;
import java.util.Date;
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

import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.PoiUtils;
import com.zhanghui.pusher.common.StatActionType;
import com.zhanghui.pusher.common.StringUtils;
import com.zhanghui.pusher.common.TaskType;
import com.zhanghui.pusher.domain.App;
import com.zhanghui.pusher.domain.Desktop;
import com.zhanghui.pusher.domain.Link;
import com.zhanghui.pusher.domain.Task;
import com.zhanghui.pusher.domain.TaskActionView;
import com.zhanghui.pusher.service.AdOwnerService;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.DesktopService;
import com.zhanghui.pusher.service.LinkService;
import com.zhanghui.pusher.service.TaskActionViewService;
import com.zhanghui.pusher.service.TaskService;

@Controller
@RequestMapping("/ta")
public class TaskActionViewController extends ListController<TaskActionView, TaskActionViewService, TaskActionViewController.Query> {
    private final static Logger log = LoggerFactory.getLogger(TaskActionViewController.class);
    @Resource
    private TaskService taskService;
    @Resource
    private DesktopService desktopService;
    @Resource
    private AdOwnerService adOwnerService;
    @Resource
    private AppService appService;
    @Resource
    private LinkService linkService;
    
    public TaskActionViewController() {
        super("ta");
    }

    @Resource
    public void setTaskActionViewService(TaskActionViewService service) {
        this.service = service;
    }

    @Override
    protected boolean preList(int page, Paginator paginator, Query query,	Model model) {
    	model.addAttribute("adOwners", adOwnerService.getAllAdOwnersCache());
    	model.addAttribute("adOwnerMap", adOwnerService.getAllAdOwnersMapCache());
        model.addAttribute("tasksMap", taskService.getAllTasksMapCache());
        model.addAttribute("tasks", taskService.getAllTasksCache());
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        model.addAttribute("countries",Country.countries);
        model.addAttribute("statActionTypeMap", StatActionType.getId2TypeMap());
        model.addAttribute("statActionTypes", StatActionType.getTypes());
        model.addAttribute("desktopMap", desktopService.getAllMapCache());
        model.addAttribute("desktops", desktopService.getAllListCache());
        query = addFilter(query);
        if(!"all".equals(query.getTaskId())){
        	model.addAttribute("adOwnerId", query.getAdOwnerId());
        }
        return super.preList(page, paginator, query, model);
    }
    public static class Query extends com.zhanghui.pusher.common.Query {
    	private Integer adOwnerId;
        private String taskId;
        private String desktopId;
        private String pushTaskId;
        private String country;
        protected String module;
        protected String startTime;
        private String actionId;
        private Integer[] taskIds;
        public Query() {
            this.startTime = DateFormatUtils.format(DateUtils.addDays(new Date(), -1), "yyyy-MM-dd");
            this.module = "TASK_ACTION_DAY_1";
        }

        public String getActionId() {
            return actionId;
        }

        public void setActionId(String actionId) {
            this.actionId = actionId;
            this.addItem("actionId", actionId);
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
            this.addItem("country", country);
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime.replaceAll("/", "-");
            this.addItem("startTime", startTime);
        }

        public String getModule() {
            return module;
        }

        public void setModule(String module) {
            this.module = module;
            this.addItem("module", module);
        }

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
            this.addItem("taskId", taskId);
        }

        public String getDesktopId() {
            return desktopId;
        }

        public void setDesktopId(String desktopId) {
            this.desktopId = desktopId;
            this.addItem("desktopId", desktopId);
        }

        public String getPushTaskId() {
            return pushTaskId;
        }

        public void setPushTaskId(String pushTaskId) {
            this.pushTaskId = pushTaskId;
            this.addItem("pushTaskId", pushTaskId);
        }

		public Integer getAdOwnerId() {
			return adOwnerId;
		}

		public void setAdOwnerId(Integer adOwnerId) {
			this.adOwnerId = adOwnerId;
			this.addItem("adOwnerId", adOwnerId);
		}

		public Integer[] getTaskIds() {
			return taskIds;
		}

		public void setTaskIds(Integer[] taskIds) {
			this.taskIds = taskIds;
			this.addItem("taskIds", taskIds);
		}
        
    }
    public Query addFilter(Query query){
        //查询包含广告主的任务和桌面快捷方式,后期加入横幅和插屏之后不做广告主区分，默认查全部广告主
       	List<Task> taskList = taskService.getAllTasksCache();	
       	List<Desktop> desktopList = desktopService.getAllListCache();
       	Map<Integer, App> appMap = appService.getAllAppsMapCache();
       	Map<Integer, Link> linkMap = linkService.getAllLinksMapCache();
       	List<Integer> taskIds=new ArrayList<Integer>();
       	for(Task task : taskList){
       		if(!task.getRefIdsObject().isEmpty()){
       			if(task.getType()==TaskType.SINGLE_LINK){
       				for(Integer id : task.getRefIdsObject()){
       					Link link = linkMap.get(id);
       					if(link==null){
       						continue;
       					}
       					if(query.getAdOwnerId()==null || query.getAdOwnerId().equals(link.getAdOwner())){
       						taskIds.add(task.getId());
       						break;
       					}
       				}
       			}else {
       				for(Integer id : task.getRefIdsObject()){
       					App app = appMap.get(id);
       					if(app==null){
       						continue;
       					}
       					if(query.getAdOwnerId()==null || query.getAdOwnerId().equals(app.getAdOwner())){
       						taskIds.add(task.getId());
       						break;
       					}
       				}
       			}
       		}
       	}
       	//都是应用，没有连接
       	List<Integer> desktopIds=new ArrayList<Integer>();
       	for(Desktop desktop : desktopList){
       		if(!desktop.getRefIdsObject().isEmpty()){
       			for(Integer id : desktop.getRefIdsObject()){
       				App app = appMap.get(id);
   					if(app==null){
   						continue;
   					}
       				if(query.getAdOwnerId()==null || query.getAdOwnerId().equals(app.getAdOwner())){
       					desktopIds.add(desktop.getId());
       					break;
       				}
       			}
       		}
       	}
           if("TASK_ACTION_DAY_1".equals(query.getModule())){
               query.setTaskId(query.getPushTaskId());
               //当任务为合计的时候，不过滤广告主
               if(!"all".equals(query.getTaskId())){
            	   query.setTaskIds(taskIds.toArray(new Integer[taskIds.size()]));
               }
           }else if ("DESKTOP_ACTION_DAY_1".equals(query.getModule())){
               query.setTaskId(query.getDesktopId());
               if(!"all".equals(query.getTaskId())){
            	   query.setTaskIds(desktopIds.toArray(new Integer[desktopIds.size()]));
               }
           }
    	return query;
    }
    @RequestMapping("/export")
	private void exportTaskActionInfoXls(Query query,HttpServletResponse response){
		try {
			XSSFWorkbook wb = PoiUtils.loadTaskActionInfoTemplate();
			XSSFSheet sh = wb.getSheetAt(0);
			int  rowIdx = 1;
			Paginator paginator = new Paginator(1, 10000);
			query = addFilter(query);
			paginator.setQuery(query);
            Map<Integer, Task> allTasksMap = taskService.getAllTasksMapCache();
            Map<Integer, Desktop> allDesktopMap = desktopService.getAllMapCache();
            Map<String, StatActionType> statActionTypeMap = StatActionType.getId2TypeMap();
            List<TaskActionView> list = service.list(paginator);
			for(TaskActionView ta : list ){
				XSSFRow row = sh.createRow(rowIdx);
                String taskName=null;
                if("all".equals(ta.getTaskId())){
                    taskName="所有任务";
                }else if("TASK_ACTION_DAY_1".equals(ta.getModule())) {
                    Task task = allTasksMap.get(StringUtils.atoi(ta.getTaskId(),0));
                    taskName = task==null?null:task.getName();
                }else {
                    Desktop desktop = allDesktopMap.get(ta.getTaskId());
                    taskName = desktop==null?null:desktop.getName();
                }
                Country country = Country.shortcut2CountryMap.get(ta.getCountry());
                StatActionType statActionType = statActionTypeMap.get(ta.getActionId());
                Object[] datas = new Object[]{
						String.class, taskName,
						String.class, query.getAdOwnerId()==null?"全部":adOwnerService.getAllAdOwnersMapCache().get(query.getAdOwnerId()).getName(),
						String.class, ta.getCountry().equals("all")?"所有国家": country==null?null:country.getChineseName(),
						String.class, statActionType==null?null:statActionType.getName(),
						double.class, ta.getCount().doubleValue()	
				};
				PoiUtils.createAndWriteCells(row, datas);
				rowIdx++;
			}
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=task_action_info.xls");
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			log.error("failure to exportTaskActionInfo",e);
		}
	}
}
