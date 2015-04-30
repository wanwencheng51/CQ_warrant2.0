package com.cq.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.SrvchkDao;
import com.cq.service.ServiceCheckService;
import com.cq.table.TblCfgSrvchk;

@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=true)
public class ServiceCheckServiceImpl implements ServiceCheckService {
	static Logger log = Logger.getLogger(ServiceCheckServiceImpl.class);
	private String errorMsg;
	
	private SrvchkDao srvchkDao;
	
	@Override
	public boolean checkCompanyService(String comtype, double comrevenue,
			int employee, int year, double capital, double money,
			double mortgage, String bank) {

		return true;
	}

	@Override
	public boolean checkPersonService(String address, String vocation,
			double income, String marry, double personmoney,
			double personmortgage, String bank) {

		return true;
	}

	@Override
	public List<TblCfgSrvchk> getSrvchk() {
		List<TblCfgSrvchk> ctlist = null;

		ctlist = srvchkDao.list();
		return ctlist;
	}
	
	@Override
	public List<TblCfgSrvchk> getSrvchk(String comtype) {
		List<TblCfgSrvchk> tcf = null;
		
		tcf = srvchkDao.findByWildProperty("comtype", comtype);
		return tcf;
	}

	public void setSrvchkDao(SrvchkDao srvchkDao) {
		this.srvchkDao = srvchkDao;
	}

}
