package com.cq.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.cq.dao.CominfoDao;
import com.cq.dao.base.BaseDao;
import com.cq.table.TblCominfo;
import com.cq.util.WarrantException;

public class CominfoDaoImpl extends BaseDao<TblCominfo> implements CominfoDao {
	static Logger log = Logger.getLogger(CominfoDaoImpl.class);
	private String errorMsg;

	public TblCominfo findCominfoByEid(String eid) {
		List<TblCominfo> cominfo = findByProperty("eid", eid);
		if (cominfo == null || cominfo.size() == 0) {
			return null;
		}
		if (cominfo.size() == 1) {
			return (TblCominfo) cominfo.get(0);
		} else {
			errorMsg = "有重复的公司编码";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

	public List<TblCominfo> findCominfoByWildName(String name) {
		List<TblCominfo> list = null;
		
		if (StringUtils.isBlank(name)) {
			list = list();
		} else {
			list = findByWildProperty("name", name);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findCompanyNameList() {
		List<String> nl = null;
		
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("name"));
		dc.setProjection(Projections.distinct(pl));
		nl = getHibernateTemplate().findByCriteria(dc);
		return nl;
	}

}
