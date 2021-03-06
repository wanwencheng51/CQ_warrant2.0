package com.cq.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.WarrantinfoDao;
import com.cq.service.GuarantyService;
import com.cq.table.TblWarrantinfo;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class GuarantyServiceImpl implements GuarantyService {
	static Logger log = Logger.getLogger(GuarantyServiceImpl.class);
	private String errorMsg;
	
	private WarrantinfoDao warrantinfoDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String guaranty(String taskid, String sel, String selValue,
			String wid, String startDate, String endDate, String noticeDate) throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null == noticeDate || "".equals(noticeDate)) {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(endDate));
				calendar.add(Calendar.DAY_OF_MONTH, -7);
				date = calendar.getTime();
				noticeDate = sdf.format(date);
			} catch (Exception e) {
				errorMsg = "计算担保解除通知时间失败";
				tools.throwException(e, log, errorMsg);
			}
		}

		TblWarrantinfo wi = null;
		try {
			wi = warrantinfoDao.findWarrantinfoByWid(wid);
			if (wi == null) {
				errorMsg = "获取担保项目信息失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			wi.setWarrantStartDate(sdf.parse(startDate));
			wi.setWarrantEndDate(sdf.parse(endDate));
			wi.setNoticeDate(sdf.parse(noticeDate));
			warrantinfoDao.update(wi);
		} catch (Exception e) {
			errorMsg = "修改担保信息中担保开始时间、结束时间、通知时间失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	public void setWarrantinfoDao(WarrantinfoDao warrantinfoDao) {
		this.warrantinfoDao = warrantinfoDao;
	}

}
