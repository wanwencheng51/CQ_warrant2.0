package com.cq.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cq.service.ConsiderationService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblManaudit;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class ConsiderationAction {
	static Logger log = Logger.getLogger(ConsiderationAction.class);
	private String errorMsg;
	
	@Resource TaskBaseService taskBaseService;
	@Resource ConsiderationService considerationService;
	
	private String sel;
	private String finance;
	private String law;
	private String wid;
	private String over;
	private String emcee;
	private String value;
	private String serviceType;
	private String selValue;
	private String taskid;
	private double refundMoney;

	public String consideration() throws Exception  {
		try {
			wid = wid.substring(wid.lastIndexOf("w"));
		} catch (Exception e) {
				errorMsg = "处理输入参数wid时发生错误" + wid;
				tools.throwException(e, log, errorMsg);
		}
		
		// Begin: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		if (StringUtils.isBlank(selValue)) {
			errorMsg = "请指定处理人，若无处理人可选，请检查用户权限配置";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		// End: Mod by Luke Chen on 2015/04/21, for stop process going to next step if there's no role to haddle
		
		if (sel.equals("transfer")) {
			try {
				return taskBaseService.transfer(selValue, taskid);
			} catch (Exception e) {
				errorMsg = "处理输入参数selValue时发生错误" + selValue;
				tools.throwException(e, log, errorMsg);
			}
		}
		
		try {
			considerationService.consideration(taskid, wid,
				sel, finance, law, over, emcee, value, serviceType, selValue, refundMoney);
		} catch (Exception e) {
			errorMsg = "业务部经理审核业务流程失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	public void getManauditInfo() throws Exception {
		List<TblManaudit> mal = null;
		
		TblManaudit ss = null;
		JsonConfig cfg = null;
		
		try {
			mal = considerationService.getTblManaudit(wid);
			
			if((mal == null) || (mal.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				return;
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < mal.size(); i++) {
				ss = (TblManaudit) mal.get(i);
				ja.add(JSONObject.fromObject(ss, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("manAuditInfo", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "获取业务部经理审核信息时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getFinance() {
		return finance;
	}

	public void setFinance(String finance) {
		this.finance = finance;
	}

	public String getLaw() {
		return law;
	}

	public void setLaw(String law) {
		this.law = law;
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

	public String getEmcee() {
		return emcee;
	}

	public void setEmcee(String emcee) {
		this.emcee = emcee;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
