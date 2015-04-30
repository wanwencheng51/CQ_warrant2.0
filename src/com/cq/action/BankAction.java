package com.cq.action;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cq.service.BankService;
import com.cq.service.TaskBaseService;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class BankAction {
	static Logger log = Logger.getLogger(BankAction.class);
	private String errorMsg;
	
	@Resource TaskBaseService taskBaseService;
	@Resource BankService bankService;
	
	private String sel;
	private String check;
	private String wid;
	private String over;
	private String selValue;
	private String taskid;
	private double refundMoney;
	
	public String bank() throws Exception {
		String result = "";
		
		// Begin: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		if (StringUtils.isBlank(selValue)) {
			errorMsg = "请指定处理人，若无处理人可选，请检查用户权限配置";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		// End: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		
		if (sel.equals("transfer")) {
			return taskBaseService.transfer(selValue, taskid);
		}
		
		if (sel.equals("nextLater")) {
			if (!"on".equals(check)) {
				errorMsg = "请确认所需的资料是否已经上传";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			} else {
				result = "pass";
			}
		} else if (sel.equals("refund")) {
			if (over.equals("")) {
				errorMsg = "退评审费后项目会终止，需要填写项目终止原因";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			} else {
				result = "refund";
			}
		} else if (sel.equals("stop")) {
			if (over.equals("")) {
				errorMsg = "项目终止需要填写项目终止原因";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			} else {
				result = "stop";
			}
		} else {
			errorMsg = "请选择正确的处理方式";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		
		String temp = bankService.noticeBank(taskid, wid, result, selValue, over, refundMoney);
		if ("success".equals(temp) == false) {
			tools.returnError(log, "处理通知银行业务时系统出现错误");
			return "error";
		}
		return "success";
	}
	
	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getOver() {
		return over;
	}

	public void setOver(String over) {
		this.over = over;
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

	public double getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(double refundMoney) {
		this.refundMoney = refundMoney;
	}
}
