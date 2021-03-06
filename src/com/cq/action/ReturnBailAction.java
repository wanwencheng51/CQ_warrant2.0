package com.cq.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.cq.service.ReturnBailService;
import com.cq.service.TaskBaseService;
import com.cq.util.tools;

public class ReturnBailAction {
	static Logger log = Logger.getLogger(ReturnBailAction.class);
	private String errorMsg;
	
	private String sel;
	private double money;
	private String wid;
	private String selValue;
	private String taskid;
	
	@Resource TaskBaseService taskBaseService;
	@Resource ReturnBailService returnBailService;

	public String returnBail() throws Exception {
		String s = "error";
		
		if (sel.equals("transfer")) {
			s = taskBaseService.transfer(selValue, taskid);
		} else if (sel.equals("nextLater")) {
			s = returnBailService.returnBail(taskid, selValue, wid, money);
		}
		if (s.equals("success") == false) {
			tools.returnError(log, "处理退保证金业务时系统出现错误");
			return "error";
		}
		return s;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
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
