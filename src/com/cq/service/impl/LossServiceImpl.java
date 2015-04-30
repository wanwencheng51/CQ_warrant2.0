package com.cq.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.WarrantinfoDao;
import com.cq.service.LossService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblWarrantinfo;
import com.cq.util.WarrantException;
import com.cq.util.tools;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
public class LossServiceImpl implements LossService {
	static Logger log = Logger.getLogger(LossServiceImpl.class);
	private String errorMsg;
	
	private TaskBaseService taskBaseService;
	private WarrantinfoDao warrantinfoDao;

	@Override
	public String lossConfirm(String taskid, String wid, double lostMoney) throws Exception {
		try {
			TblWarrantinfo wi = warrantinfoDao.findWarrantinfoByWid(wid);
			if (wi == null) {
				errorMsg = "获取担保项目信息失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			wi.setLostMoney(lostMoney);
			warrantinfoDao.update(wi);
			
			String str = tools.getLoginUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", str);
			taskBaseService.setTaskVar(taskid, "history", "整个项目结束");
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "核销损失业务流程处理失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	public void setTaskBaseService(TaskBaseService taskBaseService) {
		this.taskBaseService = taskBaseService;
	}

	public void setWarrantinfoDao(WarrantinfoDao warrantinfoDao) {
		this.warrantinfoDao = warrantinfoDao;
	}

}
