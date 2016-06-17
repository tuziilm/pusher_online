package com.zhanghui.pusher.mvc;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.zhanghui.pusher.common.*;
import com.zhanghui.pusher.domain.Task;
import com.zhanghui.pusher.exception.UploadException;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.LinkService;
import com.zhanghui.pusher.service.TaskService;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/push/task")
public class TaskController extends CRUDController<Task, TaskService, TaskController.Form, Query.NameQuery>{
	private final Logger log=LoggerFactory.getLogger(getClass());
    @Resource
    private AppService appService;
    @Resource
    private LinkService linkService;

	public TaskController(){
		super("push/task");
	}

	@Resource
	public void setTaskService(TaskService taskService){
		this.service=taskService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("appMap", appService.getAllAppsMapCache());
        model.addAttribute("linkMap", linkService.getAllLinksMapCache());
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }

    @Override
    protected void postList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        List<Task> tasks = (List<Task>)model.asMap().get("datas");
        if(tasks!=null && !tasks.isEmpty()){
            model.addAttribute("usedReqCountMap", service.getUsedReqCountMap(tasks));
        }
        super.postList(page, paginator, query, model);
    }

    @Override
    protected void postCreate(Model model) {
        model.addAttribute("countries", Country.countries);
        model.addAttribute("apps", appService.getAllAppsCache());
        model.addAttribute("links", linkService.getAllLinksCache());
    }

    @Override
    protected void postModify(int id, Task obj, Model model) {
        postCreate(model);
    }

    @Override
    protected void onSaveError(Form form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        postCreate(model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Task task=form.toObj();
        if(task.getRefIdsObject()==null || task.getRefIdsObject().isEmpty()){
            errors.addError(new ObjectError("refIdsObject", "应用或链接不能为空"));
            return;
        }
        if(task.getType()!=TaskType.MULTI_APP){
            task.setMtitle(null);
            if(task.getRefIdsObject().size()!=1) {
                errors.addError(new ObjectError("refIdsObject", "单应用或链接类型时只能选择一个应用或链接"));
                return;
            }
        }else if(Strings.isNullOrEmpty(task.getMtitle())){
            errors.addError(new ObjectError("mtitle", "多应用类型下多应用标题不能为空"));
            return;
        }
		try{
            if(!form.simgFile.isEmpty()){
            	task.setIconPath(uploadImg(task,form.simgFile));
            }
            if(!form.bimgFile.isEmpty()){
                task.setPicPath(uploadImg(task,form.bimgFile));
            }
            service.saveOrUpdate(task);
		} catch (UploadException e) {
			errors.addError(new ObjectError("upload", e.getMessage()));
		} 
	}
	
	private String uploadImg(Task task, MultipartFile taskFile) throws UploadException{
		String url=UpLoads.upload(taskFile, UploadType.PIC);
		return url;
	}

	public static class Form extends RemarkForm<Task> {
        private Set<Integer> refIdsObject;
        @NotBlank(message = "名称不能为空")
        private String name;
        @Pattern(regexp = "\\d\\d:\\d\\d-\\d\\d:\\d\\d", message = "显示时间格式不正确")
        private String showTime;
        private Integer type;
        private String autoShow;
        private String tips;
        private String title;
        private String mtitle;
        private String content;
        private String shutDown;
        private Set<String> countriesObject;
        private MultipartFile simgFile;//小图标
        private MultipartFile bimgFile;//大图片
        private Set<String> mccmncsObject;
        @NotNull(message = "开始时间不能为空")
        private Date startDate;
        @NotNull(message = "结束时间不能为空")
        private Date endDate;
        @NotNull(message = "优先级不能为空")
        private Integer priority;
        @NotNull(message = "天最大请求量不能为空")
        private Integer maxReqCount;
        @NotNull(message = "开始天数不能为空")
        private Integer startDays;
        @Override
        public Task newObj() {
            return new Task();
        }

        @Override
        public void populateObj(Task task) {
            task.setName(name);
            task.setType(type);
            task.setShowTime(showTime);
            task.setAutoShow(autoShow);
            task.setShutDown(shutDown);
            task.setTips(tips);
            task.setTitle(title);
            task.setMtitle(mtitle);
            task.setContent(content);
            task.setRefIdsObject(refIdsObject);
            task.setCountriesObject(countriesObject);
            task.setMccmncsObject(mccmncsObject);
            task.setPriority(priority);
            task.setMaxReqCount(maxReqCount);
            task.setStartDate(startDate);
            task.setEndDate(endDate);
            task.setStartDays(startDays);
        }

        public Integer getMaxReqCount() {
            return maxReqCount;
        }

        public void setMaxReqCount(Integer maxReqCount) {
            this.maxReqCount = maxReqCount;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public void setMtitle(String mtitle) {
            this.mtitle = mtitle;
        }

        public String getMtitle() {
            return mtitle;
        }

        public Set<Integer> getRefIdsObject() {
            return refIdsObject;
        }

        public void setRefIdsObject(Set<Integer> refIdsObject) {
            this.refIdsObject = refIdsObject;
        }

        public Set<String> getCountriesObject() {
            return countriesObject;
        }

        public void setCountriesObject(Set<String> countriesObject) {
            this.countriesObject = countriesObject;
        }

        public Set<String> getMccmncsObject() {
            return mccmncsObject;
        }

        public void setMccmncsObject(Set<String> mccmncsObject) {
            this.mccmncsObject = mccmncsObject;
        }

        public void setMccmncs(String mccmncs){
            if(Strings.isNullOrEmpty(mccmncs)){
                return;
            }
            this.mccmncsObject= Sets.newHashSet(Splitter.on(",").omitEmptyStrings().trimResults().split(mccmncs));
        }
        
        public String getMccmncs(){
        	if(this.mccmncsObject!=null&&!this.mccmncsObject.isEmpty()){
        		return this.mccmncsObject.toString();
        	}else{
        		return "";
        	}
        }
        
        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
        
        public String getShutDown() {
			return shutDown;
		}

		public void setShutDown(String shutDown) {
			this.shutDown = shutDown;
		}

		public MultipartFile getSimgFile() {
			return simgFile;
		}

		public void setSimgFile(MultipartFile simgFile) {
			this.simgFile = simgFile;
		}

		public MultipartFile getBimgFile() {
			return bimgFile;
		}

		public void setBimgFile(MultipartFile bimgFile) {
			this.bimgFile = bimgFile;
		}

		public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getAutoShow() {
            return autoShow;
        }

        public void setAutoShow(String autoShow) {
            this.autoShow = autoShow;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

		public Integer getStartDays() {
			return startDays;
		}

		public void setStartDays(Integer startDays) {
			this.startDays = startDays;
		}
        
    }
}
