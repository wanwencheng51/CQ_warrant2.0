package com.cq.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cq.dao.WarrantindexDao;
import com.cq.dao.base.BaseDao;
import com.cq.table.TblWarrantindex;
import com.cq.util.WarrantException;

public class WarrantindexDaoImpl extends BaseDao<TblWarrantindex> implements
		WarrantindexDao {
	static Logger log = Logger.getLogger(WarrantinfoDaoImpl.class);
	private String errorMsg;

	public long getCountByTypeAndId(String wid, char type, String id) {
		/*String sql = "select count(*) from tbl_warrantindex where WID='" + wid
				+ "' and Type='" + type + "' and ID='" + id + "'";*/
		long temp = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wid", wid);
		map.put("id", id);
		map.put("type", type);
		
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		dc.add(Restrictions.allEq(map));
		temp = countByCriterias(dc);
		
		return temp;
	}

	public List<TblWarrantindex> findWarrantInfoByIdAndType(String id, char type) {
		String hql = "from TblWarrantindex wi where wi.id='" + id
				+ "' and wi.type='" + type + "'";
		return findByHql(hql);
	}

	public TblWarrantindex findByWid(String wid) {
		
		List<TblWarrantindex> warrantindex = findByProperty("wid", wid);
		
		if (warrantindex == null || warrantindex.size() == 0) {
			return null;
		}
		if (warrantindex.size() == 1) {
			return (TblWarrantindex) warrantindex.get(0);
		} else {
			errorMsg = "有重复的项目编号";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		}
	}

}
