package com.cq.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.cq.dao.PersonDao;
import com.cq.dao.base.BaseDao;
import com.cq.table.TblPerson;
import com.cq.util.WarrantException;

public class PersonDaoImpl extends BaseDao<TblPerson> implements PersonDao {
	static Logger log = Logger.getLogger(CominfoDaoImpl.class);
	private String errorMsg;
	
	public List<TblPerson> findPersonByWildName(String name) {
		List<TblPerson> list = null;
		
		if (StringUtils.isBlank(name)) {
			list = list();
		} else {
			list = findByWildProperty("name", name);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findPersonNameList() {
		List<String> nl = null;
		
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("name"));
		dc.setProjection(Projections.distinct(pl));
		nl = getHibernateTemplate().findByCriteria(dc);
		return nl;
	}
	
	@Override
	public TblPerson findPersonByID(String id) {
		List<TblPerson> person = findByProperty("id", id);
		if (person == null || person.size() == 0) {
			return null;
		}
		if (person.size() == 1) {
			return (TblPerson) person.get(0);
		} else {
			errorMsg = "有重复的个人信息，ID为" + id;
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

}
