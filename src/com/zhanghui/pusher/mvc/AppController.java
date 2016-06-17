package com.zhanghui.pusher.mvc;

import java.io.IOException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;
import com.zhanghui.pusher.common.ApkParser;
import com.zhanghui.pusher.common.AppType;
import com.zhanghui.pusher.common.Config;
import com.zhanghui.pusher.common.Country;
import com.zhanghui.pusher.common.IconType;
import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.common.Query;
import com.zhanghui.pusher.common.RemarkForm;
import com.zhanghui.pusher.common.UpLoads;
import com.zhanghui.pusher.common.UploadType;
import com.zhanghui.pusher.domain.App;
import com.zhanghui.pusher.exception.SimpleMessageException;
import com.zhanghui.pusher.exception.UploadException;
import com.zhanghui.pusher.service.AdOwnerService;
import com.zhanghui.pusher.service.AppService;

@Controller
@RequestMapping("/adv/app")
public class AppController extends CRUDController<App, AppService, com.zhanghui.pusher.mvc.AppController.Form, Query.NameQuery>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Resource
	private AdOwnerService adOwnerService;
	public AppController() {
		super("adv/app");
	}
	@Resource
	public void setAppService(AppService appService){
		this.service=appService;
	}

    @Override
    protected boolean preList(int page, Paginator paginator, Query.NameQuery query, Model model) {
        paginator.setNeedTotal(true);
        model.addAttribute("adOwnersMap", adOwnerService.getAllAdOwnersMapCache());
        model.addAttribute("countryMap", Country.shortcut2CountryMap);
        model.addAttribute("appTypeMap", AppType.id2AppTypeMap);
        return super.preList(page, paginator, query, model);
    }
    @Override
	protected void postCreate(Model model) {
    	model.addAttribute("appTypes", AppType.types);
    	model.addAttribute("countries", Country.countries);
		model.addAttribute("adOwners", adOwnerService.getAllAdOwnersCache());
	}
    @Override
    protected void onSaveError(Form form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        postCreate(model);
    }
    @Override
	protected void postModify(int id, App obj, Model model) {
    	postCreate(model);
	}
    @Override
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		App app=form.toObj();
		try{
			if(form.isModified()){
				if(form.redirectType==0&&!form.file.isEmpty()){
					uploadApk(app,form.file);
				}else if(form.redirectType!=0&&!form.imgFile.isEmpty()){
					uploadImg(app,form);
				}else if(form.redirectType!=0&&form.imgFile.isEmpty()){
					setParam(app, form);
				}
				service.update(app);
			}else{
				if(form.redirectType==0&&form.file.isEmpty()){
					throw new UploadException("上传文件不能为空!");
				}else if(form.redirectType==0&&!form.file.isEmpty()){
					uploadApk(app, form.file);
				}else if(form.redirectType!=0&&form.imgFile.isEmpty()){
					throw new UploadException("上传图片不能为空!");
				}else if(form.redirectType!=0&&!form.imgFile.isEmpty()){
					uploadImg(app,form);
				}
				service.save(app);
			}
		} catch (UploadException e) {
            log.error("failure to upload", e);
			errors.addError(new ObjectError("upload", e.getMessage()));
		} 
	}
    private void uploadImg(App app, Form form) throws UploadException{
		String imgPath=UpLoads.upload(form.imgFile, UploadType.PIC);
		app.setAppIcon(imgPath);
		setParam(app, form);
	}
    private void setParam(App app, Form form){
    	app.setAppSize(Long.parseLong(form.appSize));
		app.setAppPackageName(form.appPackageName);
		app.setAppPath(form.appPath);
		app.setAppLabel(form.appLabel);
		app.setVersion(form.version);
    }
	private void uploadApk(App app, MultipartFile appFile) throws UploadException{
		String appPath=UpLoads.upload(appFile, UploadType.APK);
		app.setAppPath(appPath);
		app.setAppFileName(appFile.getOriginalFilename());
		ApkParser parser = new ApkParser(appPath);
		try {
			parser.parse();
			app.setAppSize(appFile.getSize());
			app.setVersion(parser.getVersionName());
			app.setAppVersionCode(parser.getVersionCode());
			app.setAppMinSdkVersion(parser.getMinSdkVersion());
			app.setAppPackageName(parser.getPackageName());
            app.setAppLabel(parser.getLabel());
			String relativeFilename = UpLoads.prepareUpload(UploadType.ICON, parser.getIcon(), Files.getFileExtension(parser.getIcon()));
            app.setAppIconObject(parser.saveIcon(Config.UPLOAD_ROOT_DIR , relativeFilename, IconType.M300));
		} catch (Exception e) {
			throw new UploadException("服务器无法解释APK，请确保APK文件的正确性！", e);
		}finally{
			try {
				parser.clean();
			} catch (IOException e) {
				log.error("fail to clean apk parser", e);
			}
		}
	}

	public static class Form extends RemarkForm<App> {
        @NotBlank(message="应用名称不能为空")
		private String name;
        @NotBlank(message="下载量不能为空")
        private String dlNum;
        @NotBlank(message="应用描述不能为空")
        private String desc;
		private MultipartFile file;
		private Integer adOwner;
		private String price;
		private Integer type;
		private Byte autoDl;
		private Byte autoOpen;
		private String netEnvironment;
		@Size(min=1, message="国家不能为空")
		private Set<String> supportCountriesObject;
		private Integer redirectType;
		//apk链接输入以下信息
		private String appSize;
		private MultipartFile imgFile;
		private String appPath;
		private String appPackageName;
		private String appLabel;
		private String version;

        @Override
        public App newObj() {
            return new App();
        }

        @Override
        public void populateObj(App app) {
            app.setName(name);
            app.setDlNum(dlNum);
            app.setDesc(desc);
            app.setRemark(remark);
            app.setAdOwner(adOwner);
            app.setPrice(price);
            app.setType(type==null?0:type);
            app.setAutoDl(autoDl);
            app.setAutoOpen(autoOpen);
            app.setNetEnvironment(netEnvironment);
            app.setRedirectType(redirectType==null?0:redirectType);
            app.setSupportCountriesObject(supportCountriesObject);
        }

        public String getDlNum() {
            return dlNum;
        }

        public void setDlNum(String dlNum) {
            this.dlNum = dlNum;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public MultipartFile getFile() {
			return file;
		}
		
		public void setFile(MultipartFile file) {
			this.file = file;
		}

        public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public Integer getAdOwner() {
			return adOwner;
		}

		public void setAdOwner(Integer adOwner) {
			this.adOwner = adOwner;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public Set<String> getSupportCountriesObject() {
			return supportCountriesObject;
		}

		public void setSupportCountriesObject(Set<String> supportCountriesObject) {
			this.supportCountriesObject = supportCountriesObject;
		}

		public MultipartFile getImgFile() {
			return imgFile;
		}

		public void setImgFile(MultipartFile imgFile) {
			this.imgFile = imgFile;
		}

		public Integer getRedirectType() {
			return redirectType;
		}

		public void setRedirectType(Integer redirectType) {
			this.redirectType = redirectType;
		}

		public String getAppPath() {
			return appPath;
		}

		public void setAppPath(String appPath) {
			this.appPath = appPath;
		}

		public String getAppPackageName() {
			return appPackageName;
		}

		public void setAppPackageName(String appPackageName) {
			this.appPackageName = appPackageName;
		}

		public String getAppSize() {
			return appSize;
		}

		public void setAppSize(String appSize) {
			this.appSize = appSize;
		}

		public String getAppLabel() {
			return appLabel;
		}

		public void setAppLabel(String appLabel) {
			this.appLabel = appLabel;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public Byte getAutoDl() {
			return autoDl;
		}

		public void setAutoDl(Byte autoDl) {
			this.autoDl = autoDl;
		}

		public Byte getAutoOpen() {
			return autoOpen;
		}

		public void setAutoOpen(Byte autoOpen) {
			this.autoOpen = autoOpen;
		}

		public String getNetEnvironment() {
			return netEnvironment;
		}

		public void setNetEnvironment(String netEnvironment) {
			this.netEnvironment = netEnvironment;
		}
		
    }
}
