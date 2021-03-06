package com.cq.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.BankinfoDao;
import com.cq.dao.WarrantinfoDao;
import com.cq.service.BankService;
import com.cq.service.ChargeService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblCfgBankinfo;
import com.cq.table.TblWarrantinfo;
import com.cq.util.ChargeType;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class BankServiceImpl implements BankService {
	static Logger log = Logger.getLogger(BankServiceImpl.class);
	private String errorMsg;
	
	private BankinfoDao bankinfoDao;
	private ChargeService chargeService;
	private TaskBaseService taskBaseService;
	private WarrantinfoDao warrantinfoDao;
	
	@Override
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String noticeBank(String taskid, String wid,
			String result, String selValue, String endCause, 
			double refundMoney) throws Exception {
		String history = null;
		TblWarrantinfo TblWarrantinfo = warrantinfoDao.findWarrantinfoByWid(wid);
		if (TblWarrantinfo == null) {
			errorMsg = "获取担保项目信息失败";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String bid = TblWarrantinfo.getBank();
		TblCfgBankinfo bank = bankinfoDao.findBankinfoByBidAndDate(bid, sdf.format(date));

		try {
			if ("pass".equals(result) == false) {
				TblWarrantinfo.setEndDate(sdf.parse(sdf.format(date)));
				TblWarrantinfo.setCause(endCause);
				warrantinfoDao.update(TblWarrantinfo);
				
				if (bank != null) {
					double quota = bank.getQuota();
					double remaining = bank.getRemaining()
							+ TblWarrantinfo.getMoney();
					if (remaining >= quota)
						remaining = quota;
					
					bank.setRemaining(remaining);
					bankinfoDao.update(bank);
				}
			}
			if ("refund".equals(result)) {
				chargeService.setTblCharge(refundMoney, wid, tools.getLoginUser(), ChargeType.REFUND);
				history = "项目终止，退评审费";
			} else if ("stop".equals(result)) {
				history = "项目终止";
			} else if ("pass".equals(result)) {
				history = "提交到下一步（项目收费）处理";
			} else {
				history = "";
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("bank", result);
			if ("stop".equals(result) == false) {
				map.put("user", selValue);
			}
			taskBaseService.setTaskVar(taskid, "history", history);
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "流程操作失败";
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

	public void setBankinfoDao(BankinfoDao bankinfoDao) {
		this.bankinfoDao = bankinfoDao;
	}

	public void setChargeService(ChargeService chargeService) {
		this.chargeService = chargeService;
	}
}
