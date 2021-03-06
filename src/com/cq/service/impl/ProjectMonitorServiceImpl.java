package com.cq.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.ProjectmonitorDao;
import com.cq.dao.WarrantinfoDao;
import com.cq.service.ProjectMonitorService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblProjectmonitor;
import com.cq.table.TblWarrantinfo;
import com.cq.util.GlobalData;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class ProjectMonitorServiceImpl implements ProjectMonitorService {
	static Logger log = Logger.getLogger(ProjectMonitorServiceImpl.class);
	private String errorMsg;
	
	private ProjectmonitorDao projectmonitorDao;
	private TaskBaseService taskBaseService;
	private WarrantinfoDao warrantinfoDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String projectTrack(String taskid, String selValue, String wid, String finance,
			String work, String fund, String antiwarrant, String matter, String conclusion,
			String issue) throws Exception {
		String result = "";
		String user = tools.getLoginUser();
		
		TblWarrantinfo wi= warrantinfoDao.findWarrantinfoByWid(wid);
		if (wi == null) {
			errorMsg = "获取担保项目信息失败";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		
		SimpleDateFormat formatter = null;
		try {
			setTblProjectmonitor(wid, user, tools.multiLine(work.trim()),
						tools.multiLine(fund.trim()), tools.multiLine(finance.trim()),
						tools.multiLine(antiwarrant.trim()), tools.multiLine(matter.trim()),
						tools.multiLine(conclusion.trim()), tools.multiLine(issue.trim()));
			result = "yes";

			Calendar cal = null;
			cal = Calendar.getInstance();
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			// 得到当前时间日期
			String currDate = formatter.format(cal.getTime());
			// 得到担保项目通知还款日期
			Date dueDate = formatter.parse(wi.getNoticeDate()
					.toString());
			// 计算通知还款日期与当前日期之间的天数
			long temp1 = dueDate.getTime();
			long temp2 = formatter.parse(currDate).getTime();
			long day = (temp1 - temp2) / (24 * 60 * 60 * 1000);
			// 如果天数差大于7天,则继续进行项目跟踪,如果小于7天,则进入还款通知
			// 项目跟踪的周期暂定为7天,后续可以通过数据配置修改
			if (day >= GlobalData.advancedReminderDays) {
				return "success";
			} else {
				System.out.println("进入还款通知");
			}
		} catch (Exception e) {
			errorMsg = "计算担保项目还款日期出现错误";
			tools.throwException(e, log, errorMsg);
		} finally {
			formatter = null;
		}
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("expire", result);
			map.put("user", selValue);
			taskBaseService.setTaskVar(taskid, "history", "提交到下一步（还款通知）处理");
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "项目跟踪业务流程处理失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	private void setTblProjectmonitor(String wid, String person, String work,
			String fund, String finance, String antiwarrant, String matter,
			String conclusion, String issue) throws Exception {
		Date da = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		
		try {
			d = sdf.parse(sdf.format(da));
		} catch (Exception e) {
			errorMsg = "日期格式处理失败";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}		

		try {
			TblProjectmonitor tblProjectmonitor = new TblProjectmonitor();
			tblProjectmonitor.setWid(wid);
			tblProjectmonitor.setDate(d);
			tblProjectmonitor.setPerson(person);
			tblProjectmonitor.setWork(work);
			tblProjectmonitor.setFund(fund);
			tblProjectmonitor.setFinance(finance);
			tblProjectmonitor.setAntiwarrant(antiwarrant);
			tblProjectmonitor.setMatter(matter);
			tblProjectmonitor.setConclusion(conclusion);
			tblProjectmonitor.setIssue(issue);
			
			projectmonitorDao.save(tblProjectmonitor);
		} catch (Exception e) {
			errorMsg = "增加项目跟踪数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=true)
	public List<TblProjectmonitor> getTblProjectmonitor(String wid) {
		List<TblProjectmonitor> ld = null;
		
		ld = projectmonitorDao.findByProperty("wid", wid);
		return ld;
	}
	
	@Override
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String loanDateWarrant(String taskid, String wid, String warrantLoanDate, String antiwarrant)
			throws Exception {
		try {
			TblWarrantinfo TblWarrantinfo = warrantinfoDao.findWarrantinfoByWid(wid);
			if (TblWarrantinfo == null) {
				errorMsg = "获取担保项目信息失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (null != warrantLoanDate && !"".equals(warrantLoanDate)) {
				TblWarrantinfo.setWarrantLoanDate(sdf.parse(warrantLoanDate));
				warrantinfoDao.update(TblWarrantinfo);
			}
		} catch (Exception e) {
			errorMsg = "担保解除业务流程失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	public void setProjectmonitorDao(ProjectmonitorDao projectmonitorDao) {
		this.projectmonitorDao = projectmonitorDao;
	}

	public void setWarrantinfoDao(WarrantinfoDao warrantinfoDao) {
		this.warrantinfoDao = warrantinfoDao;
	}

	public void setTaskBaseService(TaskBaseService taskBaseService) {
		this.taskBaseService = taskBaseService;
	}

}
