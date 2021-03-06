package com.cq.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.ChangeinfoDao;
import com.cq.table.TblChangeinfo;
import com.cq.util.GlobalData;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业注册变更信息", dataName="ci")
public class TblChangeinfoAction {
	static Logger log = Logger.getLogger(TblChangeinfoAction.class);
	private String errorMsg;
	
	private int kid;
	private String rid;
	private Date chgDate;
	private char chgType;
	private String description;
	private TblChangeinfo ci;
	
	@Resource ChangeinfoDao changeinfoDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblChangeinfo() throws Exception {
		try {
			TblChangeinfo tblChangeinfo = new TblChangeinfo();
			tblChangeinfo.setRid(rid);
			tblChangeinfo.setChgDate(chgDate);
			tblChangeinfo.setChgType(chgType);
			tblChangeinfo.setDescription(tools.multiLine(description));
            ci = tblChangeinfo;
            changeinfoDao.save(tblChangeinfo);
			tools.fillQueryInfo(2, rid, "TblChangeinfo");
		} catch (Exception e) {
			errorMsg = "添加企业的历史变更数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblChangeinfo() throws Exception {
		try {
			ci = changeinfoDao.get(kid);
			changeinfoDao.delete(kid);
			this.selectAjaxTblChangeinfo();
		} catch (Exception e) {
			errorMsg = "删除失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblChangeinfo() throws Exception {
		String date1 = null;
		String chgType2 = null;
		StringBuffer outs = null;
		
		List<TblChangeinfo> listTblChangeinfo = changeinfoDao.findByProperty("rid", rid);
		if (listTblChangeinfo == null || listTblChangeinfo.size() == 0) {
			return;
		}

		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='20%'>变更时间</th>");
		outs.append("<th width='25%'>变更类型</th>");
		outs.append("<th width='45%'>变更内容</th>");
		outs.append("<th width='10%'>操作</th>");
		outs.append("</tr>");

		for (int i = 0; i < listTblChangeinfo.size(); i++) {
			DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			date1 = date.format(listTblChangeinfo.get(i).getChgDate());
			chgType2 = GlobalData.changeTypes.get(
					Character.toString(listTblChangeinfo.get(i).getChgType()));
			
			outs.append("<tr>");
			outs.append("<td onclick='updateChangeinfoJs("
					+ listTblChangeinfo.get(i).getKid()
					+ ")'><a href='#'>" + date1 + "</a></td>");
			outs.append("<td>" + chgType2 + "</td>");
			outs.append("<td>" + listTblChangeinfo.get(i).getDescription() + "</td>");
			outs.append("<td onclick='changeinfoListOne("
						+ listTblChangeinfo.get(i).getKid() + ',' + '\"'
						+ listTblChangeinfo.get(i).getRid() + '\"'
						+ ")'><a href='#'>删除</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");

		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblChangeinfo() throws Exception {
		try {
			ci = changeinfoDao.get(kid);
			ci.setChgDate(chgDate);
			ci.setChgType(chgType);
			ci.setDescription(tools.multiLine(description));
			tools.fillQueryInfo(2, rid, "TblChangeinfo");
			changeinfoDao.update(ci);
		} catch (Exception e) {
			errorMsg = "修改企业的历史变更数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblChangeinfo() throws Exception {
		TblChangeinfo bi = changeinfoDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到公司变更信息" + kid);
			return;
		}
		tools.outputInfo(JSONObject.fromObject(bi, tools.getJsonConfig()));
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public Date getChgDate() {
		return chgDate;
	}

	public void setChgDate(Date chgDate) {
		this.chgDate = chgDate;
	}

	public char getChgType() {
		return chgType;
	}

	public void setChgType(char chgType) {
		this.chgType = chgType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setChangeinfoDao(ChangeinfoDao changeinfoDao) {
		this.changeinfoDao = changeinfoDao;
	}

	public TblChangeinfo getCi() {
		return ci;
	}

	public void setCi(TblChangeinfo ci) {
		this.ci = ci;
	}
}
