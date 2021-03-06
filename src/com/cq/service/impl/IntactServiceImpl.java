package com.cq.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.bean.Intact;
import com.cq.dao.ComdoclistDao;
import com.cq.dao.DocchklistDao;
import com.cq.dao.WarrantindexDao;
import com.cq.service.IntactService;
import com.cq.table.TblCfgComdoclist;
import com.cq.table.TblDocchklist;
import com.cq.table.TblWarrantindex;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class IntactServiceImpl implements IntactService {
	static Logger log = Logger.getLogger(IntactServiceImpl.class);
	private String errorMsg;
	
	private ComdoclistDao comdoclistDao;
	private DocchklistDao docchklistDao;
	private WarrantindexDao warrantindexDao;
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly=true)
	public List<Intact> getList(String wid) throws Exception {
		List<Intact> ld = null;
		List<TblDocchklist> dcl = null;
		List<TblCfgComdoclist> cdl = null;
		TblDocchklist dc = null;
		TblCfgComdoclist cd = null;
		
		try {
			
			TblWarrantindex wi = warrantindexDao.findByWid(wid);
			if (wi == null) {
				errorMsg = "获取担保项目信息失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			int number = wi.getTimes();
			
			dcl = docchklistDao.list(wid, number);
			if ((dcl == null) || (dcl.size() == 0)) {
				errorMsg = "获取文档审查列表信息失败" + dcl;
				log.error(errorMsg);
				return null;
			}
			
			cdl = comdoclistDao.list();
			if ((cdl == null) || (cdl.size() == 0)) {
				errorMsg = "获取文档列表信息失败" + cdl;
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			
			ld = new ArrayList<Intact>();
			ld.clear();
			
			for (int i = 0; i < dcl.size(); i++) {
				dc = dcl.get(i);
				if (dc.getTimes() == number) {
					for (int j = 0; j < cdl.size(); j++) {
						cd = cdl.get(j);
						if (dc.getDid().equals(cd.getDid())) {
							Intact d = new Intact();
							d.setDBID(Integer.toString(dc.getKid()));
							d.setComplete(dc.getComplete());
							d.setDate(dc.getDate());
							d.setName(cd.getName());
							d.setDID(cd.getDid());
							d.setDescription(cd.getDescription());
							ld.add(d);
						}
					}
				}
			}
		} catch (Exception e) {
			errorMsg = "获取项目文档列表失败";
			tools.throwException(e, log, errorMsg);
		}
		return ld;
	}

	public void setComdoclistDao(ComdoclistDao comdoclistDao) {
		this.comdoclistDao = comdoclistDao;
	}

	public void setDocchklistDao(DocchklistDao docchklistDao) {
		this.docchklistDao = docchklistDao;
	}

	public void setWarrantindexDao(WarrantindexDao warrantindexDao) {
		this.warrantindexDao = warrantindexDao;
	}
}
