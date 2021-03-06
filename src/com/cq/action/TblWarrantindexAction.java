package com.cq.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.dao.WarrantindexDao;
import com.cq.table.TblWarrantindex;
import com.cq.util.tools;

public class TblWarrantindexAction {
	static Logger log = Logger.getLogger(TblWarrantindexAction.class);
	private String errorMsg;
	private String wid;
	
	@Resource WarrantindexDao warrantindexDao;
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getServiceType() throws Exception {
		String t = null;
		TblWarrantindex wti = null;

		try {
			wti = warrantindexDao.findByWid(wid);
			
			if(wti != null) {
				t = Character.toString(wti.getType());
			}
			tools.outputInfo(t);
		} catch (Exception e) {
			errorMsg = "查询信息失败";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}

	public void setWarrantindexDao(WarrantindexDao warrantindexDao) {
		this.warrantindexDao = warrantindexDao;
	}

}
