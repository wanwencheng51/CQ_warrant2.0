package com.cq.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.service.ChargeService;
import com.cq.service.RefundService;
import com.cq.service.TaskBaseService;
import com.cq.util.ChargeType;
import com.cq.util.tools;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
public class RefundServiceImpl implements RefundService {
	static Logger log = Logger.getLogger(RefundServiceImpl.class);
	private String errorMsg;
	
	private ChargeService chargeService;
	private TaskBaseService taskBaseService;
	
	@Override
	public String refund(String taskid, String selValue, String wid, double money) throws Exception {
		String user = tools.getLoginUser();
		
		try {
			chargeService.updateChargeDate(wid, ChargeType.REFUND);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			taskBaseService.setTaskVar(taskid, "history", "整个项目结束");
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "退评审费业务流程处理失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	public void setTaskBaseService(TaskBaseService taskBaseService) {
		this.taskBaseService = taskBaseService;
	}

	public void setChargeService(ChargeService chargeService) {
		this.chargeService = chargeService;
	}

}
