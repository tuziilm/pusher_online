package com.zhanghui.pusher.mvc;

import java.util.Set;

import com.zhanghui.pusher.common.*;
import com.zhanghui.pusher.domain.Link;
import com.zhanghui.pusher.exception.UploadException;
import com.zhanghui.pusher.mvc.TaskController.Form;
import com.zhanghui.pusher.service.AdOwnerService;
import com.zhanghui.pusher.service.AppService;
import com.zhanghui.pusher.service.LinkService;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
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
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/adv/link")
public class LinkController extends CRUDController<Link, LinkService, LinkController.Form, Query.NameQuery>{
	private final Logger log=LoggerFactory.getLogger(getClass());
	
	@Resource
    private AdOwnerService adOwnerService;
	
	public LinkController() {
		super("adv/link");
	}
	@Resource
	public void setLinkService(LinkService linkService){
		this.service=linkService;
	}
	
	@Override
	protected void postCreate(Model model) {
		model.addAttribute("appTypes", AppType.types);
		model.addAttribute("countries", Country.countries);
		model.addAttribute("adOwners", adOwnerService.getAllAdOwnersCache());
	}
	@Override
	protected void postModify(int id, Link obj, Model model) {
		postCreate(model);
	}
	@Override
    protected void onSaveError(Form form, BindingResult errors, Model model, HttpServletRequest request, HttpServletResponse response) {
        postCreate(model);
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
	public void innerSave(Form form, BindingResult errors, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		Link link=form.toObj();
		try{
            if(!form.imgFile.isEmpty()){
                uploadImg(link,form.imgFile);
            }
            service.saveOrUpdate(link);
		} catch (UploadException e) {
			errors.addError(new ObjectError("upload", e.getMessage()));
		} 
	}
	
	private void uploadImg(Link link, MultipartFile linkFile) throws UploadException{
		String linkPath=UpLoads.upload(linkFile, UploadType.PIC);
		link.setImgPath(linkPath);
		link.setImgFileName(linkFile.getOriginalFilename());
	}

	public static class Form extends RemarkForm<Link> {
        @NotBlank(message="广告名称不能为空")
		private String name;
        @URL(message = "链接格式不合法")
        private String url;
		private MultipartFile imgFile;
		private Integer adOwner;
		private String price;
		private Integer type;
		private Integer redirectType;
		@Size(min=1, message="国家不能为空")
		private Set<String> supportCountriesObject;

        @Override
        public Link newObj() {
            return new Link();
        }

        @Override
        public void populateObj(Link link) {
            link.setName(name);
            link.setUrl(url);
            link.setRemark(remark);
            link.setAdOwner(adOwner);
            link.setPrice(price);
            link.setType(type==null?0:type);
            link.setRedirectType(redirectType==null?0:redirectType);
            link.setSupportCountriesObject(supportCountriesObject);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public MultipartFile getImgFile() {
            return imgFile;
        }

        public void setImgFile(MultipartFile imgFile) {
            this.imgFile = imgFile;
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

		public Integer getRedirectType() {
			return redirectType;
		}

		public void setRedirectType(Integer redirectType) {
			this.redirectType = redirectType;
		}
		
    }
}
