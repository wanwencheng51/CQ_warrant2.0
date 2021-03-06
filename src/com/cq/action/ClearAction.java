package com.cq.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cq.service.ClearService;
import com.cq.service.TaskBaseService;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class ClearAction {
	static Logger log = Logger.getLogger(ClearAction.class);
	private String errorMsg;
	
	@Resource TaskBaseService taskBaseService;
	@Resource ClearService clearService;
	
	private String sel;
	private String wid;
	private double money;
	private double warMon;
	private String WarrantReleaseDate;
	private String selValue;
	private String taskid;

	public String clear() throws Exception {
		String temp = "error";

		// Begin: Mod by Luke Chen on 2015/04/22, for stop process going to next step if there's no role to haddle
		if (StringUtils.isBlank(selValue)) {
			errorMsg = "请指定处理人，若无处理人可选，请检查用户权限配置";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		// End: Mod by Luke Chen on 2015/04/22, for stop process going to next step if there's no role to haddle
		
		if (sel.equals("transfer")) {
			temp = taskBaseService.transfer(selValue, taskid);
		} else if ((sel.equals("nextLater")) || (sel.equals("compensatory"))) {
			temp = clearService.clearWarrant(wid, taskid, selValue,
					WarrantReleaseDate, money, warMon);
		}
		if (temp.equals("error")) {
			tools.returnError(log, "处理担保解除业务时系统出现错误");
		}
		return temp;
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

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getWarMon() {
		return warMon;
	}

	public void setWarMon(double warMon) {
		this.warMon = warMon;
	}

	public String getWarrantReleaseDate() {
		return WarrantReleaseDate;
	}

	public void setWarrantReleaseDate(String warrantReleaseDate) {
		WarrantReleaseDate = warrantReleaseDate;
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
