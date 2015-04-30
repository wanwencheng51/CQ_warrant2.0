package com.cq.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cq.bean.Intact;
import com.cq.dao.ComdoclistDao;
import com.cq.service.CollectDataService;
import com.cq.service.IntactService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblCfgComdoclist;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class CollectDataAction {
	static Logger log = Logger.getLogger(CollectDataAction.class);
	private String errorMsg;
	
	@Resource TaskBaseService taskBaseService;
	@Resource CollectDataService collectDataService;
	@Resource IntactService intactService;
	@Resource ComdoclistDao comdoclistDao;
	
	private String sel;
	private String wid;
	private String checkbox;
	private String selValue;
	private String taskid;

	public String collect() throws Exception {
		String temp = "error";
		
		// Begin: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		if (StringUtils.isBlank(selValue)) {
			errorMsg = "请指定处理人，若无处理人可选，请检查用户权限配置";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		// End: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		
		if (sel.equals("transfer")){
			temp = taskBaseService.transfer(selValue, taskid);
		} else if (sel.equals("nextLater")) {
			temp = collectDataService.collectData(taskid, wid, checkbox);
			if ("success".equals(temp)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("user", selValue);
				taskBaseService.setTaskVar(taskid, "history", "提交到下一步（资料审查）处理");
				taskBaseService.nextStep(taskid, map);
				return "success";
			}
		}
		if (temp.equals("error")) {
			tools.returnError(log, "处理收集资料业务时系统出现错误");
		}
		return temp;
	}
	
	public void getProjectDocList() throws Exception {
		List<Intact> docList = null;
		
		Intact intact = null;
		JsonConfig cfg = null;
		
		try {
			docList = intactService.getList(wid);
			if((docList == null) || (docList.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				return;
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < docList.size(); i++) {
				intact = (Intact) docList.get(i);
				ja.add(JSONObject.fromObject(intact, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("docList", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "获取项目收集资料列表时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	public void getComDocList() throws Exception {
		List<TblCfgComdoclist> docList = null;
		
		TblCfgComdoclist intact = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		JsonConfig cfg = null;
		
		try {
			docList = comdoclistDao.findDocByType(type);
			if((docList == null) || (docList.size() <= 0)) {
				errorMsg = "系统中没有配置资料列表数据";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < docList.size(); i++) {
				intact = docList.get(i);
				ja.add(JSONObject.fromObject(intact, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("docList", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "获取项目收集资料列表时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(String checkbox) {
		this.checkbox = checkbox;
	}

	public String getSelValue() {
		return selValue;
	}

	public void setSelValue(String selValue) {
		this.selValue = selValue;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
}
