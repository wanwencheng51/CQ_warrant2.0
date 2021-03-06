package com.cq.action;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.UnduebillDao;
import com.cq.table.TblUnduebill;
import com.cq.util.GlobalData;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业未结清票据信息" , dataName="ub")
public class TblUnduebillAction {
	static Logger log = Logger.getLogger(TblUnduebillAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private char type;
	private String startDate;
	private String endDate;
	private String loaner;
	private char mode;
	private double money;
	private double remaining;
	private String description;
	private TblUnduebill ub;
	
	@Resource UnduebillDao unduebillDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblUnduebill() throws Exception {
		Date tmpDate = null;
		try {
			TblUnduebill tblUnduebill = new TblUnduebill();
			tblUnduebill.setEid(eid);
			tblUnduebill.setType(type);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			tblUnduebill.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			tblUnduebill.setEndDate(tmpDate);
			
			tblUnduebill.setLoaner(loaner);
			tblUnduebill.setMode(mode);
			tblUnduebill.setMoney(money);
			tblUnduebill.setRemaining(remaining);
			tblUnduebill.setDescription(description);
			
			tools.fillQueryInfo(1, eid, "TblUnduebill");
			ub = tblUnduebill;
			unduebillDao.save(ub);
		} catch (Exception e) {
			errorMsg = "保存企业未结清票据信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblUnduebill() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("kid");
		
		try {
			ub = unduebillDao.get(Integer.parseInt(id));
			unduebillDao.delete(Integer.parseInt(id));
			this.selectAjaxTblUnduebill();
		} catch (Exception e) {
			errorMsg = "删除企业未结清票据信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblUnduebill() throws Exception {
		String Type2 = null;
		String Mode2 = null;
		StringBuffer outs = null;
		
		List<TblUnduebill> listTblUnduebill = unduebillDao.findByProperty("eid", eid);
		if ((listTblUnduebill == null)
				|| !(listTblUnduebill.size() > 0)) {
			log.warn("没有查询到企业未结清票数信息");
			return;
		}
		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='13%'>业务品种</th>");
		outs.append("<th width='13%'>合同起始日期</th>");
		outs.append("<th width='13%'>合同终止日期</th>");
		outs.append("<th width='13%'>合同金额</th>");
		outs.append("<th width='13%'>债权人</th>");
		outs.append("<th width='13%'>担保方式</th>");
		outs.append("<th width='13%'>目前余额</th>");
		outs.append("<th width='9%'>操作</th>");
		outs.append("</tr>");
		
		for (int i = 0; i < listTblUnduebill.size(); i++) {
			DecimalFormat decimalFormat = new DecimalFormat("###0.00");
			Type2 = GlobalData.serviceTypes.get(
					Character.toString(listTblUnduebill.get(i).getType()));
			Mode2 = GlobalData.warrantModes.get(
					Character.toString(listTblUnduebill.get(i).getMode()));
			
			outs.append("<tr>");
			outs.append("<td onclick='updateUnduebillJs("
					+ listTblUnduebill.get(i).getKid()
					+ ")'><a href='#'>" + Type2);
			outs.append("</td>");
			outs.append("<td>"
					+ listTblUnduebill.get(i).getStartDate());
			outs.append("</td>");
			outs.append("<td>"
					+ listTblUnduebill.get(i).getEndDate());
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblUnduebill.get(i).getMoney()));
			outs.append("</td>");
			outs.append("<td>"
					+ listTblUnduebill.get(i).getLoaner());
			outs.append("</td>");
			outs.append("<td>" + Mode2);
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblUnduebill.get(i).getRemaining()));
			outs.append("</td>");
			outs.append("<td onclick='unduebillListOne("
					+ listTblUnduebill.get(i).getKid() + ','
					+ '\"' + listTblUnduebill.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		
		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblUnduebill() throws Exception {
		Date tmpDate = null;
		
		try {
			ub = unduebillDao.get(kid);
			
			ub.setType(type);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			ub.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			ub.setEndDate(tmpDate);
			
			ub.setLoaner(loaner);
			ub.setMode(mode);
			ub.setMoney(money);
			ub.setRemaining(remaining);
			ub.setDescription(description);

			tools.fillQueryInfo(1, eid, "TblUnduebill");
			unduebillDao.update(ub);
		} catch (Exception e) {
			errorMsg = "修改企业未结清票据信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblUnduebill() throws Exception {
		TblUnduebill bi = unduebillDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到企业未结清票据信息" + kid);
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

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLoaner() {
		return loaner;
	}

	public void setLoaner(String loaner) {
		this.loaner = loaner;
	}

	public Character getMode() {
		return mode;
	}

	public void setMode(Character mode) {
		this.mode = mode;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getRemaining() {
		return remaining;
	}

	public void setRemaining(double remaining) {
		this.remaining = remaining;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

	public void setUnduebillDao(UnduebillDao unduebillDao) {
		this.unduebillDao = unduebillDao;
	}

	public TblUnduebill getUb() {
		return ub;
	}

	public void setUb(TblUnduebill ub) {
		this.ub = ub;
	}

}
