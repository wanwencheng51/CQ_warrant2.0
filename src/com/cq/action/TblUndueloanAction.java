package com.cq.action;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.UndueloanDao;
import com.cq.table.TblUndueloan;
import com.cq.util.GlobalData;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业借款信息", dataName="ul")
public class TblUndueloanAction {
	static Logger log = Logger.getLogger(TblUndueloanAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private String borrower;
	private String startDate;
	private String endDate;
	private double rate;
	private String  bank;
	private String loaner;
	private char wmode;
	private char mode;
	private double money;
	private double remaining;
	private TblUndueloan ul;
	
	@Resource UndueloanDao undueloanDao;
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblUndueloan() throws Exception{
		Date tmpDate = null;
		try {
			TblUndueloan tblUndueloan = new TblUndueloan();
			tblUndueloan.setKid(kid);
			tblUndueloan.setEid(eid);
			tblUndueloan.setBorrower(borrower);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			tblUndueloan.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			tblUndueloan.setEndDate(tmpDate);
			
			tblUndueloan.setBank(bank);
			tblUndueloan.setRate(rate);
			tblUndueloan.setLoaner(loaner);
			tblUndueloan.setWmode(wmode);

			tblUndueloan.setMode(mode);
			tblUndueloan.setMoney(money);
			tblUndueloan.setRemaining(remaining);

			tools.fillQueryInfo(1, eid, "TblUndueloan");
			ul = tblUndueloan;
			undueloanDao.save(ul);
		} catch (Exception e) {
			errorMsg = "保存企业借款信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblUndueloan() throws Exception {
		try {
			ul = undueloanDao.get(kid);
			undueloanDao.delete(kid);
			this.selectAjaxTblUndueloan();
		} catch (Exception e) {
			errorMsg = "删除企业借款信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblUndueloan() throws Exception {
		String startDate1 = null;
		String borrower1 = null;
		String endDate1 = null;
		String wMode2 = null;
		String mode2 = null;
		StringBuffer outs = null;
		
		List<TblUndueloan> listTblUndueloan = undueloanDao.findByProperty("eid", eid);
		if (listTblUndueloan == null || listTblUndueloan.size() == 0) {
			log.warn("没有查询到企业借款信息");
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		if (listTblUndueloan.size() > 0) {
			outs.append("<tr>");
			outs.append("<th>借款主体</th>");
			outs.append("<th>贷款起始日期</th>");
			outs.append("<th>贷款终止日期</th>");
			outs.append("<th>贷款行</th>");
			outs.append("<th>利率</th>");
			outs.append("<th>债权人</th>");
			outs.append("<th>担保方式</th>");
			outs.append("<th>贷款方式</th>");
			outs.append("<th>贷款金额</th>");
			outs.append("<th>目前余额</th>");
			outs.append("<th>操作</th>");
			outs.append("</tr>");
		}
		for (int i = 0; i < listTblUndueloan.size(); i++) {
			borrower1 = listTblUndueloan.get(i).getBorrower();
			DecimalFormat decimalFormat = new DecimalFormat("###0.00");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			startDate1 = df.format(listTblUndueloan.get(i).getStartDate());
			endDate1 = df.format(listTblUndueloan.get(i).getEndDate());
			
			wMode2 = GlobalData.warrantModes.get(
					Character.toString(listTblUndueloan.get(i).getWmode()));
			mode2 = GlobalData.loanModes.get(
					Character.toString(listTblUndueloan.get(i).getMode()));
			
			outs.append("<tr>");
			outs.append("<td onclick='updateUndueloanJs("
					+ listTblUndueloan.get(i).getKid()
					+ ")'><a href='#'>" + borrower1);
			outs.append("</td>");
			outs.append("<td>" + startDate1);
			outs.append("</td>");
			outs.append("<td>" + endDate1);
			outs.append("</td>");
			outs.append("<td>"
					+ listTblUndueloan.get(i).getBank());
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblUndueloan.get(i).getRate()));
			outs.append("</td>");
			outs.append("<td>"
					+ listTblUndueloan.get(i).getLoaner());
			outs.append("</td>");
			outs.append("<td>" + wMode2);
			outs.append("</td>");
			outs.append("<td>" + mode2);
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblUndueloan.get(i).getMoney()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblUndueloan.get(i).getRemaining()));
			outs.append("</td>");
			outs.append("<td onclick='undueloanListOne("
					+ listTblUndueloan.get(i).getKid() +','
					+ '\"' + listTblUndueloan.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		
		tools.outputInfo(outs);
	}
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblUndueloan() throws Exception {
		Date tmpDate = null;
		
		try {
			ul = undueloanDao.get(kid);
			
			ul.setBorrower(borrower);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			ul.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			ul.setEndDate(tmpDate);
			
			ul.setBank(bank);
			ul.setRate(rate);
			ul.setLoaner(loaner);
			ul.setWmode(wmode);

			ul.setMode(mode);
			ul.setMoney(money);
			ul.setRemaining(remaining);

			tools.fillQueryInfo(1, eid, "TblUndueloan");
			undueloanDao.update(ul);
		} catch (Exception e) {
			errorMsg = "修改企业借款信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblUndueloan() throws Exception {
		TblUndueloan bi = undueloanDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到借款信息" + kid);
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

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
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

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getLoaner() {
		return loaner;
	}

	public void setLoaner(String loaner) {
		this.loaner = loaner;
	}

	public Character getWmode() {
		return wmode;
	}

	public void setWmode(Character wmode) {
		this.wmode = wmode;
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

	public void setWmode(char wmode) {
		this.wmode = wmode;
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

	public void setUndueloanDao(UndueloanDao undueloanDao) {
		this.undueloanDao = undueloanDao;
	}

	public TblUndueloan getUl() {
		return ul;
	}

	public void setUl(TblUndueloan ul) {
		this.ul = ul;
	}
}
