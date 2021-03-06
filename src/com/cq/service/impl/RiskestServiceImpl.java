package com.cq.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.PersonalriskDao;
import com.cq.dao.RiskestDao;
import com.cq.service.RiskestService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblPersonalrisk;
import com.cq.table.TblRiskest;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class RiskestServiceImpl implements RiskestService {
	static Logger log = Logger.getLogger(RiskestServiceImpl.class);
	private String errorMsg;
	
	private PersonalriskDao personalriskDao;
	private RiskestDao riskestDao;
	private TaskBaseService taskBaseService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String companyRiskEst(String taskid, String selValue, String wid,
			Date startDate, String managerial, String achievement, String brand,
			String occupancy, String reserve, String nature, String client,
			String right, String patent, String ability, String assessSummarize,
			String due, String warrant, String credit, String enterprise, String creditSummary,
			int purpose, int experience, int prospect, int resource, int skill,
			int credits) throws Exception {
		
		String user = tools.getLoginUser();

		try {
			setFinanceest(wid, user, startDate, tools.multiLine(managerial),
				tools.multiLine(achievement), tools.multiLine(brand),
				tools.multiLine(occupancy), tools.multiLine(reserve),
				tools.multiLine(nature), tools.multiLine(client),
				tools.multiLine(right), tools.multiLine(patent),
				tools.multiLine(ability), tools.multiLine(assessSummarize),
				tools.multiLine(due), tools.multiLine(warrant),
				tools.multiLine(credit), tools.multiLine(enterprise),
				tools.multiLine(creditSummary), purpose, experience,
				prospect, resource, skill, credits);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			taskBaseService.setTaskVar(taskid, "history", "提交到下一步（发起会签）处理");
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "企业客户的风险评估流程处理失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String personRiskEst(String taskid, String selValue, String wid,
			 int age, int ducationLevel, int martialStatus, int liveTime, int socialSecurity,
			 int job, double familyIncome, int numb, double grossDebt, double totalAssets,
			 int housSituation, String reimbursement, String cguemeforianaly,
			 int purpos) throws Exception {
		String user = tools.getLoginUser();
		TblPersonalrisk tblPersonalrisk = null;
		
		try {
			Date da = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = null;
			d = sdf.parse(sdf.format(da));
			
			tblPersonalrisk = new TblPersonalrisk();
			tblPersonalrisk.setWid(wid);
			tblPersonalrisk.setAge(age);
			tblPersonalrisk.setUser(user);
			tblPersonalrisk.setDucationLevel(ducationLevel);
			tblPersonalrisk.setMartialStatus(martialStatus);
			tblPersonalrisk.setLiveTime(liveTime);
			tblPersonalrisk.setSocialSecurity(socialSecurity);
			tblPersonalrisk.setJob(job);
			tblPersonalrisk.setFamilyIncome(familyIncome);
			tblPersonalrisk.setNumb(numb);
			tblPersonalrisk.setGrossDebt(grossDebt);
			tblPersonalrisk.setTotalAssets(totalAssets);
			tblPersonalrisk.setHousSituation(housSituation);
			tblPersonalrisk.setPurpos(purpos);
			
			tblPersonalrisk.setStartDate(d);
			tblPersonalrisk.setEndDate(d);

			tblPersonalrisk.setReimbursement(tools.multiLine(reimbursement));
			tblPersonalrisk.setCguemeforianaly(tools.multiLine(cguemeforianaly));
			personalriskDao.save(tblPersonalrisk);
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			taskBaseService.setTaskVar(taskid, "history", "提交到下一步（发起会签）处理");
			taskBaseService.nextStep(taskid, map);
		} catch (Exception e) {
			errorMsg = "个人客户的风险评估流程处理失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void setFinanceest(String wid, String name, Date startDate,
			String managerial, String achievement, String brand,
			String occupancy, String reserve, String nature, String client,
			String right, String patent, String ability,
			String assessSummarize, String due, String warrant, String credit,
			String enterprise, String creditSummary,int purpose,
			int experience, int prospect, int resource, int skill, int credits) throws Exception {
		Date da = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		
		try {
			d = sdf.parse(sdf.format(da));
		} catch (Exception e) {
			errorMsg = "日期格式处理错误";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
		
		TblRiskest tblRiskest = null;
		try {
			tblRiskest = new TblRiskest();
			tblRiskest.setWid(wid);
			tblRiskest.setName(name);
			tblRiskest.setStartDate(d);
			tblRiskest.setEndDate(d);
			tblRiskest.setManagerial(managerial);
			tblRiskest.setAchievement(achievement);
			tblRiskest.setBrand(brand);
			tblRiskest.setOccupancy(occupancy);
			tblRiskest.setReser(reserve);
			tblRiskest.setNature(nature);
			tblRiskest.setClient(client);
			tblRiskest.setMyright(right);
			tblRiskest.setPatent(patent);
			tblRiskest.setAbility(ability);
			tblRiskest.setAssessSummarize(assessSummarize);
			tblRiskest.setDue(due);
			tblRiskest.setWarrant(warrant);
			tblRiskest.setCredit(credit);
			tblRiskest.setEnterprise(enterprise);
			tblRiskest.setCreditSummary(creditSummary);
			tblRiskest.setPurpose(purpose);
			tblRiskest.setExperience(experience);
			tblRiskest.setProspect(prospect);
			tblRiskest.setResource(resource);
			tblRiskest.setSkill(skill);
			tblRiskest.setCredits(credits);
			
			riskestDao.save(tblRiskest);
		} catch (Exception e) {
			errorMsg = "增加企业风险评估数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=true)
	public List<TblRiskest> getTblRiskest(String wid) {
		List<TblRiskest> ld = null;
		
		ld = riskestDao.findByProperty("wid", wid);
		return ld;
	}
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=true)
	public List<TblPersonalrisk> getTblPersonalrisk(String wid) {
		List<TblPersonalrisk> pers = null;
		
		pers = personalriskDao.findByProperty("wid", wid);
		
		return pers;
	}

	public void setPersonalriskDao(PersonalriskDao personalriskDao) {
		this.personalriskDao = personalriskDao;
	}
	
	public void setRiskestDao(RiskestDao riskestDao) {
		this.riskestDao = riskestDao;
	}

	public void setTaskBaseService(TaskBaseService taskBaseService) {
		this.taskBaseService = taskBaseService;
	}

}
