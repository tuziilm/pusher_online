package com.zhanghui.pusher.mvc;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;
import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.common.RemarkForm;
import com.zhanghui.pusher.common.TaskType;
import com.zhanghui.pusher.common.UpLoads;
import com.zhanghui.pusher.common.UploadType;
import com.zhanghui.pusher.domain.Desktop;
import com.zhanghui.pusher.domain.Task;
import com.zhanghui.pusher.exception.UploadException;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.DesktopService;

@Controller
@RequestMapping("/push/desktop")
public class DesktopController extends CRUDController<Desktop, DesktopService, DesktopController.Form, Query.NameQuery>{
	private final Logger log=LoggerFactory.getLogger(getClass());
    @Resource
    private AppService appService;

	public DesktopController(){
		super("push/desktop");
	}

	@Resource
	public void setDesktopService(DesktopService desktopService){
		this.service=desktopService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("appMap", appService.getAllAppsMapCache());
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        return super.preList(page, paginator, query, model);
    }

    @Override
    protected void postCreate(Model model) {
        model.addAttribute("countries", Country.countries);
        model.addAttribute("apps", appService.getAllAppsCache());
    }

    @Override
    protected void postModify(int id, Desktop obj, Model model) {
        postCreate(model);
    }

    @Override
    protected void onSaveError(Form form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        postCreate(model);
    }

    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Desktop task=form.toObj();
        if(task.getRefIdsObject()==null || task.getRefIdsObject().isEmpty()){
            errors.addError(new ObjectError("refIdsObject", "应用不能为空"));
            return;
        }
        if(task.getType()!=TaskType.MULTI_APP){
            task.setMtitle(null);
            if(task.getRefIdsObject().size()!=1) {
                errors.addError(new ObjectError("refIdsObject", "单应用类型时只能选择一个应用"));
                return;
            }
        }else if(Strings.isNullOrEmpty(task.getMtitle())){
            errors.addError(new ObjectError("mtitle", "多应用类型下多应用标题不能为空"));
            return;
        }
        try {
            if(!form.isModified() && form.imgFile.isEmpty()){
                errors.addError(new ObjectError("icon", "图标不能为空"));
                return;
            }
        	if(!form.imgFile.isEmpty()){
                task.setIconPath(UpLoads.upload(form.imgFile, UploadType.PIC));
        	}
		} catch (UploadException e) {
            log.error("failure to upload", e);
            errors.addError(new ObjectError("upload", e.getMessage()));
            return;
		}
        service.saveOrUpdate(task);
	}
    
	public static class Form extends RemarkForm<Desktop> {
        private Set<Integer> refIdsObject;
        @NotBlank(message = "名称不能为空")
        private String name;
        private Integer type;
        private String mtitle;
        private Set<String> countriesObject;
        @NotNull(message = "优先级不能为空")
        private Integer priority;
        private MultipartFile imgFile;//图标
        @Override
        public Desktop newObj() {
            return new Desktop();
        }

        @Override
        public void populateObj(Desktop task) {
            task.setName(name);
            task.setType(type);
            task.setMtitle(mtitle);
            task.setRefIdsObject(refIdsObject);
            task.setCountriesObject(countriesObject);
            task.setPriority(priority);
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

		public MultipartFile getImgFile() {
			return imgFile;
		}

		public void setImgFile(MultipartFile imgFile) {
			this.imgFile = imgFile;
		}
        
    }
}
