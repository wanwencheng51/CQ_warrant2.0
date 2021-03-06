package com.cq.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.cq.service.DecisionService;
import com.cq.util.tools;

public class PersonalDecisionAction {
	static Logger log = Logger.getLogger(PersonalDecisionAction.class);
	private String errorMsg;
	
	@Resource DecisionService decisionService;
	
	private String wid;

	public void personalDecisionSign() throws Exception {
		StringBuffer outs = null;
		
		try {
			outs = decisionService.personCreditGrade(wid);
			tools.outputInfo(outs.toString());
		} catch (Exception e) {
			errorMsg = "输出个人客户信用评分时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

}
