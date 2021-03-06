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
import com.cq.dao.CountersignDao;
import com.cq.table.TblCountersign;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName = "评委会签意见", dataName = "cts")
public class TblCountersignAction {
	static Logger log = Logger.getLogger(TblCountersignAction.class);
	private String errorMsg;

	private int kid;
	private String wid;
	private String name;
	private Date date;
	private char result;
	private String description;
	private int number;
	private TblCountersign cts;

	@Resource
	CountersignDao countersignDao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String savaTblCountersign() throws Exception {
		TblCountersign tblCountersign = null;
		try {
			tblCountersign = new TblCountersign();
			tblCountersign.setKid(kid);
			tblCountersign.setWid(wid);
			tblCountersign.setName(name);
			tblCountersign.setDate(date);
			tblCountersign.setResult(result);
			tblCountersign.setNumber(number);
			tblCountersign.setDescription(tools.multiLine(description));
			tools.fillQueryInfo(0, wid, "TblCountersign");
			cts = tblCountersign;
			countersignDao.save(cts);
		} catch (Exception e) {
			errorMsg = "保存评委会签信息发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String updateTblCountersign() throws Exception {
		cts = countersignDao.get(kid);

		try {
			cts.setWid(wid);
			cts.setName(name);
			cts.setDate(date);
			cts.setNumber(number);
			cts.setResult(result);
			cts.setDescription(tools.multiLine(description));
			tools.fillQueryInfo(0, wid, "TblCountersign");
			countersignDao.update(cts);
		} catch (Exception e) {
			errorMsg = "修改评委会签信息发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void applyYanZhengcousign() throws Exception {
		String str = "countersign";

		try {
			List<TblCountersign> listTblCountersign = countersignDao
					.findbyName(wid, name);
			if (listTblCountersign != null && listTblCountersign.size() > 0) {
				str = wid;
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "查询评委会签信息失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void selectAjaxTblCountersign() throws Exception {
		String date1 = null;
		String chgType2 = null;
		StringBuffer outs = null;

		List<TblCountersign> ccl = countersignDao.findByProperty("wid", wid);
		if (ccl == null || ccl.size() == 0) {
			return;
		}

		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='15%' align='center'>会签人名称</th>");
		outs.append("<th width='15%' align='center'>会签日期</th>");
		outs.append("<th width='15%' align='center'>会签结论</th>");
		outs.append("<th id='case' align='center'>情况说明</th>");
		outs.append("</tr>");

		for (int i = 0; i < ccl.size(); i++) {
			DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			date1 = date.format(ccl.get(i).getDate());

			char chgType = ccl.get(i).getResult();
			if (chgType == '0') {
				chgType2 = "同意";
			} else if (chgType == '1') {
				chgType2 = "不同意";
			} else {
				chgType2 = "";
			}

			outs.append("<tr>");
			outs.append("<td onclick='updateCountersign(" + ccl.get(i).getKid()
					+ ")'><a href='#'>" + ccl.get(i).getName() + "</a></td>");
			outs.append("<td>" + date1 + "</td>");
			outs.append("<td>" + chgType2 + "</td>");
			outs.append("<td>" + ccl.get(i).getDescription() + "</td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		tools.outputInfo(outs);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public void getTblCountersign() throws Exception {
		TblCountersign bi = countersignDao.get(kid);

		if (bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到个人家庭信息" + kid);
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

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public char getResult() {
		return result;
	}

	public void setResult(char result) {
		this.result = result;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setCountersignDao(CountersignDao countersignDao) {
		this.countersignDao = countersignDao;
	}

	public TblCountersign getCts() {
		return cts;
	}

	public void setCts(TblCountersign cts) {
		this.cts = cts;
	}

}
