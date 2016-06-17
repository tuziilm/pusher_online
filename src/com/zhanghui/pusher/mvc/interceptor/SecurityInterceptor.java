package com.zhanghui.pusher.mvc.interceptor;

import com.google.common.collect.Sets;
import com.zhanghui.pusher.common.LoginContext;
import com.zhanghui.pusher.common.SystemUserType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private final static Set<String> openApis= Sets.newHashSet("/user/register","/user/other_info","/login","/user/ad_screen","/user/register_gzip");

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		if(uri.startsWith(contextPath)){
			uri=uri.substring(contextPath.length());
		}
		if(uri.startsWith("/callback") || uri.startsWith("/get/") || uri.startsWith("/static") ||uri.startsWith("/public") || openApis.contains(uri)){//static resource or login page or callback interface, not authorize
			return true;
		}else{
			boolean isLogin=LoginContext.checkLogin(request.getSession());
			if(!isLogin)
				response.sendRedirect(contextPath+"/login");
			else{
				SystemUserType sut = LoginContext.get().systemUserType;
				if(sut==SystemUserType.ADMIN){
					return isLogin;
				}else{
					if(uri.equals("/")){
						return true;
					}
					for(String res : sut.getResources()){
						if(uri.startsWith(res)){
							return isLogin;
						}
					}
					response.sendRedirect(contextPath+"/login");
					return false;
				}
			}
			return isLogin;
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		LoginContext.clear();
	}
}
