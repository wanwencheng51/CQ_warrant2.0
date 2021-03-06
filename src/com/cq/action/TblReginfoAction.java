package com.cq.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.ReginfoDao;
import com.cq.table.TblReginfo;
import com.cq.util.WarrantException;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业注册信息", dataName="rif")
public class TblReginfoAction {
	static Logger log = Logger.getLogger(TblReginfoAction.class);
	private String errorMsg;
	
	private String rid;
	private String eid;
	private String regName;
	private String regAddress;
	private String regDept;
	private String regDate;
	private int timeLimit;
	private double regMoney;
	private String stateTaxNum;
	private String localTaxNum;
	private String scope;
	private TblReginfo rif;
	
	@Resource ReginfoDao reginfoDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void savaTblReginfo() throws Exception {
		DateFormat df = null;
		TblReginfo tblReginfo = null;

		try {
			df = new SimpleDateFormat("yyyy-MM-dd");

			tblReginfo = new TblReginfo();
			tblReginfo.setRid(rid);
			tblReginfo.setEid(eid);
			tblReginfo.setRegName(regName);
			tblReginfo.setRegAddress(regAddress);
			tblReginfo.setRegDept(regDept);

			tblReginfo.setRegDate(df.parse(regDate));

			tblReginfo.setTimeLimit(timeLimit);
			tblReginfo.setRegMoney(regMoney);
			tblReginfo.setStateTaxNum(stateTaxNum);
			tblReginfo.setLocalTaxNum(localTaxNum);
			tblReginfo.setScope(scope);

			rif = tblReginfo;
			reginfoDao.save(rif);
		} catch (Exception e) {
			errorMsg = "添加企业注册信息失败";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void updateTblReginfo() throws Exception {
		DateFormat df = null;
		TblReginfo tblReginfo = null;

		try {
			List<TblReginfo> rl = reginfoDao.findByProperty("rid", rid);
			
			if (rl == null || rl.size() == 0) {
				errorMsg = "获取企业注册信息异常";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			} else if (rl.size() == 1) {
				df = new SimpleDateFormat("yyyy-MM-dd");

				tblReginfo = rl.get(0);
				tblReginfo.setEid(eid);
				tblReginfo.setRegName(regName);
				tblReginfo.setRegAddress(regAddress);
				tblReginfo.setRegDept(regDept);

				tblReginfo.setRegDate(df.parse(regDate));

				tblReginfo.setTimeLimit(timeLimit);
				tblReginfo.setRegMoney(regMoney);
				tblReginfo.setStateTaxNum(stateTaxNum);
				tblReginfo.setLocalTaxNum(localTaxNum);
				tblReginfo.setScope(scope);

				rif = tblReginfo;
				reginfoDao.update(tblReginfo);
			} else {
				errorMsg = "数据库中有一个公司的多条注册信息";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
		} catch (Exception e) {
			errorMsg = "修改企业注册信息失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getReginfo() throws Exception {
		List<TblReginfo> list= null;

		try {
			list= reginfoDao.findByProperty("eid", eid);

			if (list != null && 0 < list.size()) {
				tools.outputInfo(JSONObject.fromObject((TblReginfo) list.get(0), tools.getJsonConfig()));
			} else {
				tools.outputInfo(JSONObject.fromObject(null));
			}
		} catch (Exception e) {
			errorMsg = "获取企业信息失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRegName() {
		return regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getRegDept() {
		return regDept;
	}

	public void setRegDept(String regDept) {
		this.regDept = regDept;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public double getRegMoney() {
		return regMoney;
	}

	public void setRegMoney(double regMoney) {
		this.regMoney = regMoney;
	}

	public String getStateTaxNum() {
		return stateTaxNum;
	}

	public void setStateTaxNum(String stateTaxNum) {
		this.stateTaxNum = stateTaxNum;
	}

	public String getLocalTaxNum() {
		return localTaxNum;
	}

	public void setLocalTaxNum(String localTaxNum) {
		this.localTaxNum = localTaxNum;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public void setReginfoDao(ReginfoDao reginfoDao) {
		this.reginfoDao = reginfoDao;
	}

	public TblReginfo getRif() {
		return rif;
	}

	public void setRif(TblReginfo rif) {
		this.rif = rif;
	}
}