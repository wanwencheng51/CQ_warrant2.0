package com.cq.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cq.bean.TaskName;
import com.cq.bean.UserRight;
import com.cq.service.TaskGroupService;
import com.cq.service.UserService;
import com.cq.table.TblCfgTaskgroup;
import com.cq.util.GlobalData;
import com.cq.util.tools;

public class CustomerAction {
	static Logger log = Logger.getLogger(RoleAction.class);
	private String errorMsg;
	
	private String taskName;
	private String branch;
	
	@Resource UserService userService;
	@Resource TaskGroupService taskgroupService;
	
	public void addCustomer() throws Exception {
		String str = "n";
		branch = URLDecoder.decode(branch, "UTF-8");
		boolean boo = taskgroupService.addTaskGroupList(taskName, branch);
		if (boo) {
			str = "y";
		}
		tools.outputInfo(str);
	}

	public void deleteCustomer() throws Exception {
		String str = "n";
		branch = URLDecoder.decode(branch, "UTF-8");
		boolean boo = taskgroupService.deleteTaskGroup(taskName, branch);
		if (boo) {
			str = "y";
		}
		tools.outputInfo(str);
	}
	
	public void cxCustomer() throws Exception {
		branch = URLDecoder.decode(branch, "UTF-8");
		boolean showAll = false;
		
		Iterator<?> iter = GlobalData.taskNames.keySet().iterator();
		if (iter.hasNext() == false) {
			tools.outputInfo("");
		} else {
			if (StringUtils.isBlank(taskName) && StringUtils.isBlank(branch)) {
				showAll = true;
			}
			List<TblCfgTaskgroup> tgl = taskgroupService.getTaskGroupList(taskName, branch);
			StringBuffer sb = new StringBuffer();
			
			sb.append("<table width='100%' height='25' border='0' align='center'>");
			sb.append("<tr id='r'>");
			sb.append("<th>名称</th>");
			sb.append("<th>角色</th>");
			sb.append("<th>操作</th>");
			sb.append("</tr>");
			
			while (iter.hasNext()) {
				String name = (String) iter.next();
				String cname = GlobalData.taskNames.get(name);
				List<String> gl = getGroupListByTask(name, tgl);
				
				if (gl == null || gl.size() == 0) {
					if (showAll) {
						sb.append("<tr id='d'>");
						sb.append("<td>" + cname + "</td>");
						sb.append("<td></td>");
						sb.append("<td></td>");
						sb.append("</tr>");
					}
				} else {
					sb.append("<tr id='d'>");
					sb.append("<td rowspan='" + gl.size() + "'>"
							+ cname + "</td>");
					for (int j = 0; j < gl.size(); j++) {
						if (j != 0) {
							sb.append("<tr id='d'>");
						}
						sb.append("<td>" + gl.get(j) + "</td>");
						sb.append("<td><a onclick='deleteCustomer(\""
								+ name + "\", \""
								+ gl.get(j) + "\""
								+ ")' style='cursor:pointer;'>删除</a></td>");
						sb.append("</tr>");
					}
				}
			}
			sb.append("</table>");
			tools.outputInfo(sb);
		}
		return;
	}
	
	public void getTaskNameList() throws Exception {
		JSONArray ja = new JSONArray();
		Iterator<?> iter = GlobalData.taskNames.keySet().iterator();
		if (iter.hasNext() == false) {
			ja.add(JSONObject.fromObject(null));
		} else {
			while (iter.hasNext()) {
				TaskName tn = new TaskName();
				String name = (String) iter.next();
				tn.setEnglishName(name);
				tn.setChineseName(GlobalData.taskNames.get(name));
				ja.add(JSONObject.fromObject(tn));
			}
		}
		JSONObject result = new JSONObject();
		result.put("customerList", ja.toString());
		tools.outputInfo(result);
	}
	
	public void getUserRight() throws Exception {
		try {
			UserRight ur = taskgroupService.getUserRight();
			
			if (ur == null) {
				log.warn("当前用户没有分配任何角色");
				tools.outputInfo(JSONObject.fromObject(null));
			} else {
				tools.outputInfo(JSONObject.fromObject(ur));
			}
		} catch (Exception e) {
			errorMsg = "获取用户权限发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return;
	}
	
	private List<String> getGroupListByTask(String taskName, List<TblCfgTaskgroup> tgl) {
		if (tgl == null || tgl.size() == 0) {
			return null;
		}
		
		List<String> gl = new ArrayList<String>();
		for (int i = 0; i < tgl.size(); i++) {
			TblCfgTaskgroup tg = tgl.get(i);
			if (taskName.equals(tg.getTaskid())) {
				gl.add(tg.getGroupid());
			}
		}
		return gl;
	}
	
	public String getTaskName() {
		return taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

}
