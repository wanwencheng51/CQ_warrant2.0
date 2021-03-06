package com.cq.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.BranchinfoDao;
import com.cq.table.TblBranchinfo;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业分支结构信息", dataName="bcif")
public class TblBranchinfoAction {
	static Logger log = Logger.getLogger(TblBranchinfoAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private String name;
	private String description;
	private TblBranchinfo bcif;
	
	@Resource BranchinfoDao branchinfoDao;

	// 验证机构名称是否存在
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void applyYanZhengfzname() throws Exception {
		String str = "fenzhi";
		
		try {
			List<TblBranchinfo> listTblBranchinfo = branchinfoDao.findBranchinfoByEidAndName(eid, name);
			if (listTblBranchinfo != null && listTblBranchinfo.size() > 0) {
				str = listTblBranchinfo.get(0).getEid();
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "查询机构名称失败";
			tools.throwException(e, log, errorMsg);
		}
	}
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void applyYanZhengfzname1() throws Exception {
		String str = "fenzhi";
		
		try {
			List<TblBranchinfo> listTblBranchinfo = branchinfoDao.findBranchinfoByEidAndName1(kid,eid, name);
			if (listTblBranchinfo != null && listTblBranchinfo.size() > 0) {
				str = listTblBranchinfo.get(0).getEid();
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "查询机构名称失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblBranchinfo() throws Exception {
		TblBranchinfo tblBranchinfo = null;

		try {
			tblBranchinfo = new TblBranchinfo();
			tblBranchinfo.setEid(eid);
			tblBranchinfo.setName(name);
			tblBranchinfo.setDescription(tools.multiLine(description));
            bcif = tblBranchinfo;
			branchinfoDao.save(tblBranchinfo);
			tools.fillQueryInfo(1, eid, "TblBranchinfo");
		} catch (Exception e) {
			errorMsg = "添加分支机构数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblBranchinfo() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("kid");
		
		try {
			bcif = branchinfoDao.get(kid);
			branchinfoDao.delete(Integer.parseInt(id));
			this.selectAjaxTblBranchinfo();
		} catch (Exception e) {
			errorMsg = "删除分支机构数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblBranchinfo() throws Exception {
		String name1 = null;
		String description1 = null;
		StringBuffer outs = null;
		
		List<TblBranchinfo> listTblBranchinfo = branchinfoDao.findByProperty("eid", eid);
		if (listTblBranchinfo == null || listTblBranchinfo.size() == 0) {
			log.warn("没有查询到分支机构数据" + listTblBranchinfo);
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='40%'>机构名称</th>");
		outs.append("<th width='45%'>机构说明</th>");
		outs.append("<th width='15%'>操作</th>");
		outs.append("</tr>");
		
		for (int i = 0; i < listTblBranchinfo.size(); i++) {
			name1 = listTblBranchinfo.get(i).getName();
			description1 = listTblBranchinfo.get(i).getDescription();
			
			outs.append("<tr>");
			outs.append("<td onclick='updateBranchinfoJs("
					+ listTblBranchinfo.get(i).getKid()
					+ ")'><a href='#'>"	+ name1);
			outs.append("</td>");
			outs.append("<td>" + description1);
			outs.append("</td>");
			outs.append("<td onclick='branchinfoListOne("
					+ listTblBranchinfo.get(i).getKid() + ','
					+ '\"' + listTblBranchinfo.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		
		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblBranchinfo() throws Exception {
		try {
			bcif = branchinfoDao.get(kid);
			
			bcif.setName(name);
			bcif.setDescription(tools.multiLine(description));
			tools.fillQueryInfo(1, eid, "TblBranchinfo");
			branchinfoDao.update(bcif);
		} catch (Exception e) {
			errorMsg = "修改分支机构数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblBranchinfo() throws Exception {
		TblBranchinfo bi = branchinfoDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到公司分支机构信息" + kid);
			return;
		}
		tools.outputInfo(JSONObject.fromObject(bi));
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBranchinfoDao(BranchinfoDao branchinfoDao) {
		this.branchinfoDao = branchinfoDao;
	}

	public TblBranchinfo getBcif() {
		return bcif;
	}
	public void setBcif(TblBranchinfo bcif) {
		this.bcif = bcif;
	}
}
