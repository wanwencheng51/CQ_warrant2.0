package com.cq.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.cq.service.ChargeService;
import com.cq.service.TaskBaseService;
import com.cq.util.tools;

public class ChargeAction {
	static Logger log = Logger.getLogger(ChargeAction.class);
	private String errorMsg;
	
	@Resource TaskBaseService taskBaseService;
	@Resource ChargeService chargeService;
	
	private String sel;
	private String wid;
	private String selValue;
	private String taskid;

	public String charge() throws Exception {
		String temp = "error";
		String result = "";

		if (sel.equals("nextLater")) {
			result = "pass";
			temp = chargeService.charge(taskid, result, selValue); 
		} else if (sel.equals("transfer")) {
			temp = taskBaseService.transfer(selValue, taskid);
		}
		if (temp.equals("error")) {
			tools.returnError(log, "项目收费业务处理出现错误");
		}
		return temp;
	}
	
	public void getChargeInfo() throws Exception {
		List<Double> chargeInfo = null;
		
		try {
			chargeInfo = chargeService.getCharge(wid);
			
			if((chargeInfo == null) || (chargeInfo.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				return;
			}
			tools.outputInfo(JSONArray.fromObject(chargeInfo));
		} catch (Exception e) {
			errorMsg = "获取企业项目收费相关信息时发生异常";
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
