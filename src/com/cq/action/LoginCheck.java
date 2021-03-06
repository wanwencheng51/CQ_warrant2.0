package com.cq.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.cq.service.UserService;
import com.cq.util.tools;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginCheck extends AbstractInterceptor {
	static Logger log = Logger.getLogger(LoginCheck.class);
	private String errorMsg;
	
	@Resource UserService userService;
	
	private static final long serialVersionUID = -5150103448544611312L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String user = tools.getLoginUser();
		if ((user != null) && (userService.getUserByName(user) != null)) {
			return invocation.invoke();
		}
		
		return Action.LOGIN;
	}

}
