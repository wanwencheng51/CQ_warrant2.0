package com.cq.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cq.service.DecisionService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblDecision;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class DecisionAction {
	static Logger log = Logger.getLogger(DecisionAction.class);
	private String errorMsg;
	
	private String dbid;
	private String wid;
	private String state;
	private Date StateDate;
	private String person;
	private Date endData;
	private char type;
	private String description;
	private String explains;
	private String sel;
	private String decisionList;
	private double money;
	private double deposit;
	private double assure;
	private double commission;
	private double evaluate;
	private String explain;
	private String cause;
	private int times;
	private String selValue;
	private String taskid;
	private double rate;
	private double refundMoney;
	
	@Resource TaskBaseService taskBaseService;
	@Resource DecisionService decisionService;

	public String decision() throws Exception  {
		String s = "error";
		
		// Begin: Mod by Luke Chen on 2015/04/22, for stop process going to next step if there's no role to haddle
		if (StringUtils.isBlank(selValue)) {
			errorMsg = "请指定处理人，若无处理人可选，请检查用户权限配置";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		// End: Mod by Luke Chen on 2015/04/22, for stop process going to next step if there's no role to haddle
		
		if (sel.equals("transfer")) {
			s = taskBaseService.transfer(selValue, taskid);
			return s;
		}
		s = decisionService.projectDecision(taskid, sel, selValue,
				wid, decisionList, explain, money,
				deposit, assure, commission, evaluate,
				cause, rate, refundMoney);
		if ("success".equals(s) == false) {
			tools.returnError(log, "处理项目决策业务时系统出现错误");
			return "error";
		}
		return "success";
	}

	public void decisionSign() throws Exception {
		StringBuffer outs = null;
		
		try {
			outs = decisionService.companyCreditGrade(wid);
			tools.outputInfo(outs.toString());
		} catch (Exception e) {
			errorMsg = "输出企业客户信用评分信息时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	public void getDecisionResultInfo() throws Exception {
		List<TblDecision> csl = null;
		TblDecision cs = null;
		JsonConfig cfg = null;
		
		try {
			csl = decisionService.getTblDecision(wid);
			
			if((csl == null) || (csl.size() == 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				return;
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < csl.size(); i++) {
				cs = (TblDecision) csl.get(i);
				ja.add(JSONObject.fromObject(cs, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("decisionResultInfo", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "获取评委专家会签意见时系统出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getDbid() {
		return dbid;
	}

	public void setDbid(String dbid) {
		this.dbid = dbid;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStateDate() {
		return StateDate;
	}

	public void setStateDate(Date stateDate) {
		StateDate = stateDate;
	}

	public String getExplains() {
		return explains;
	}

	public void setExplains(String explains) {
		this.explains = explains;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Date getEndData() {
		return endData;
	}

	public void setEndData(Date endData) {
		this.endData = endData;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
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

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getAssure() {
		return assure;
	}

	public void setAssure(double assure) {
		this.assure = assure;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public double getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(double evaluate) {
		this.evaluate = evaluate;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getDecisionList() {
		return decisionList;
	}

	public void setDecisionList(String decisionList) {
		this.decisionList = decisionList;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(double refundMoney) {
		this.refundMoney = refundMoney;
	}
}
