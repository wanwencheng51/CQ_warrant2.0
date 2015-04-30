package com.cq.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cq.service.LoginService;
import com.cq.service.OperlogService;
import com.cq.service.UserService;
import com.cq.util.tools;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction {
	static Logger log = Logger.getLogger(LoginAction.class);
	private String errorMsg;
	
	@Resource LoginService loginService;
	@Resource UserService userService;
	@Resource OperlogService operlogService;
	
	private String username;
	private String password;
	private String verifycode;

	public String userLogin() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String su = null;
		
		Map<String, Object> ss = ActionContext.getContext().getSession();
		String verCode = (String) ss.get("randCode");
		ss.put("randCode", null);
		if (!(verifycode.equalsIgnoreCase(verCode))) {
			request.setAttribute("loginerror", "验证码输入错误，请重新输入");
			request.getRequestDispatcher("/newLogin.jsp").forward(request,
					response);
			operlogService.logLogin(username, "验证码错误");
			return su;
		}
		
		String passMd=tools.md5(password);
		boolean bl = loginService.logins(username, passMd);
		if (bl) {
			List<String> groupList = userService.groupList(username);
			ss.put("groupList", groupList);
			ss.put("user", username);
			ss.put("pass", passMd);
			su = "success";
			operlogService.logLogin(username, "登录成功");
		} else {
			request.setAttribute("loginerror", "账号或密码错误");
			request.getRequestDispatcher("/newLogin.jsp").forward(request,
					response);
			operlogService.logLogin(username, "账号或密码错误");
		}
		return su;
	}

	public String getData() {

		return "success";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

}
