package com.cq.action;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.jbpm.api.identity.User;

import com.cq.bean.ProjectInfo;
import com.cq.service.ProjectManageService;
import com.cq.service.UserService;
import com.cq.table.CQUser;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class UserAction {
	static Logger log = Logger.getLogger(UserAction.class);
	private String errorMsg;
	
	@Resource UserService userService;
	@Resource ProjectManageService projectManageService;
	
	private String userName;
	private String passWord;
	private String word;
	private String userPass;
	private String pass;
	private String bra;
	private String email;
	private String Xemail;
	private String selText;

	//获取问题跟踪人名单
	public void getProblemTrackPerson() throws Exception {
		try {
			List<User> userList = userService.userList();
			
			if((userList == null) || (userList.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("获取用户列表信息失败" + userList);
				return;
			}
			JSONArray ja = new JSONArray();
			for(int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getId().equals("root")) {
					continue;
				}
				ja.add(JSONObject.fromObject(userList.get(i)));
			}
			JSONObject result = new JSONObject();
			result.put("userList", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出问题跟踪人信息时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	public void getUserList() throws Exception {
		try {
			List<User> userList = userService.userList();
			
			if((userList == null) || (userList.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("获取用户列表信息失败" + userList);
				return;
			}
			JSONArray ja = new JSONArray();
			for(int i = 0; i < userList.size(); i++) {
				CQUser user = (CQUser) userList.get(i);
				if (user.getId().equals("root")) {
					continue;
				}
				ja.add(JSONObject.fromObject(user));
			}
			JSONObject result = new JSONObject();
			result.put("userList", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出用户列表信息时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	public void getUserByName() throws Exception {
		String un = URLDecoder.decode(userName, "UTF-8");
		
		try {
			CQUser cquser = userService.getUserByName(un);
			
			if(cquser == null) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("根据用户名获取用户信息失败" + cquser);
				return;
			}
			tools.outputInfo(JSONObject.fromObject(cquser, tools.getJsonConfig()));
		} catch (Exception e) {
			errorMsg = "输出用户信息时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	/**
	 * 添加用户
	 * @throws Exception 
	 * */
	public String addUser() throws Exception {
		if (userName.trim().equals("")) {
			errorMsg = "输入的用户名不合法，请输入正确的用户名";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}

		if (userPass.equals(pass) && userPass.length() > 6) {
			String p = tools.md5(userPass);
			String rt = userService.addUser(userName, p, email);
			if (rt.equals("success")) {
				String rp = userService.updatePassword(p, userName);
				if (rp.equals("success")) {
					return "success";
				}
			}
			errorMsg = "输入的用户已存在，请重新输入";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		} else {
			errorMsg = "输入的密码与确认密码不相同或者小于7位，请重新输入";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

	/**
	 * 模糊查询用户
	 * @throws Exception 
	 * */
	public void CXsel() throws Exception {
		JsonConfig cfg = null;
		
		try {
			List<CQUser> CListI = userService.getUserByWildId(selText);
			
			if(CListI == null || CListI.size() == 0) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("获取模糊查询用户列表信息失败" + CListI);
				return;
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < CListI.size(); i++) {
				ja.add(JSONObject.fromObject(CListI.get(i), cfg));
			}
			JSONObject result = new JSONObject();
			result.put("userList", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出模糊查询用户结果时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	/**
	 * 删除用户
	 * @throws Exception 
	 * */
	public String deleteUser() throws Exception {
		String un = URLDecoder.decode(userName, "UTF-8");
		List<ProjectInfo> pil = projectManageService.getProjectInfo("0", "");
		
		for (int i = 0; i < pil.size(); i++) {
			ProjectInfo pi = pil.get(i);
			if (pi.getAssignee().size() > 0) {
				for (int j = 0; j < pi.getAssignee().size(); j++) {
					String ass = pi.getAssignee().get(j);
					if (un.equals(ass)) {
						tools.outputInfo("y");
						return null;
					}
				}
			}
		}
		try {
			userService.deleteUser(un);
		} catch (Exception e) {
			errorMsg = "删除用户失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	/**
	 * 修改用户信息
	 * @throws Exception 
	 * */
	public String updateUser() throws Exception {
		String u = "";
		String ema = "success";
		String up = "success";
		
		if (null != passWord && !"".equals(passWord.trim()) && null != userPass && !"".equals(userPass.trim())) {
			String w = tools.md5(word);
			if (!w.equals(passWord)) {
				errorMsg = "原密码错误，请从新输入";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			if (!userPass.equals(u)) {
				up = this.updatePassword();
				if (!up.equals("success")) {
					errorMsg = "修改密码失败";
					log.error(errorMsg);
					throw new WarrantException(errorMsg);
				}
				u = "0";
			}
		} else {
			u = "0";
			up = "success";
		}
		if (!email.equals(Xemail)) {
			ema = userService.updateEmail(userName, Xemail);
			if (!ema.equals("success")) {
				errorMsg = "修改邮箱失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
		}
		if (u.equals("0") && "success".equals(ema) && "success".equals(up)) {
			return "success";
		} else {
			errorMsg = "没有任何修改项";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

	/**
	 * 修改密码
	 * @throws Exception 
	 * */
	private String updatePassword() throws Exception {
		try {
			String u = tools.md5(userPass);
			
			if (!u.equals(passWord) && userPass.length() > 6) {
				if (pass.equals(userPass)) {
					userService.updatePassword(u, userName);
				} else {
					errorMsg = "输入的新密码与确认密码不相同，请重新输入";
					log.error(errorMsg);
					throw new WarrantException(errorMsg);
				}
			} else {
				errorMsg = "输入的密码与之前的相同或者小于7位，请重新输入";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			} 
		} catch (Exception e) {
			errorMsg = "修改密码失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getXemail() {
		return Xemail;
	}

	public void setXemail(String xemail) {
		Xemail = xemail;
	}

	public String getSelText() {
		return selText;
	}

	public void setSelText(String selText) {
		this.selText = selText;
	}

	public String getBra() {
		return bra;
	}

	public void setBra(String bra) {
		this.bra = bra;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
