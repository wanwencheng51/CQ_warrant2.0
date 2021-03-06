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
import com.cq.dao.PersonalwarrantDao;
import com.cq.table.TblPersonalwarrant;
import com.cq.util.GlobalData;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="个人担保信息", dataName="pw")
public class TblPersonalwarrantAction {
	static Logger log = Logger.getLogger(TblPersonalwarrantAction.class);
	private String errorMsg;
	
	private int kid;
	private String id;
	private String name;
	private String startDate;
	private String endDate;
	private char mode;
	private double money;
	private double remaining;
	private String description;
	private TblPersonalwarrant pw;
	
	@Resource PersonalwarrantDao personalwarrantDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblPersonalwarrant() throws Exception{
		Date tmpDate = null;
		TblPersonalwarrant tw = null;
		
		try {
			tw = new TblPersonalwarrant();
			
			tw.setKid(kid);
			tw.setId(id);
			tw.setName(name);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			tw.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			tw.setEndDate(tmpDate);
			
			tw.setMode(mode);
			tw.setMoney(money);
			tw.setRemaining(remaining);
			tw.setDescription(tools.multiLine(description));
            pw = tw;
			personalwarrantDao.save(pw);
			tools.fillQueryInfo(3, id, "TblPersonalwarrant");
		} catch (Exception e) {
			errorMsg = "保存个人担保信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblPersonalwarrant() throws Exception {
		try {
			pw = personalwarrantDao.get(kid);
			personalwarrantDao.delete(kid);
			this.selectAjaxTblPersonalwarrant();
		} catch (Exception e) {
			errorMsg = "删除个人担保信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void selectAjaxTblPersonalwarrant() throws Exception {
		TblPersonalwarrant tw = null;
		
		String startDate1 = null;
		String endDate1 = null;
		StringBuffer outs = null;
		
		List<TblPersonalwarrant> listTblPersonalwarrant = personalwarrantDao.findByProperty("id", id);
		if (listTblPersonalwarrant == null || listTblPersonalwarrant.size() == 0) {
			log.warn("没有查询到个人担保信息");
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='15%'>被担保人名称</th>");
		outs.append("<th width='13%'>担保起始日期</th>");
		outs.append("<th width='13%'>担保终止日期</th>");
		outs.append("<th width='10%'>担保方式</th>");
		outs.append("<th width='11%'>担保金额</th>");
		outs.append("<th width='11%'>余额</th>");
		outs.append("<th width='20%'>说明</th>");
		outs.append("<th width='7%'>操作</th>");
		outs.append("</tr>");
			
		for (int i = 0; i < listTblPersonalwarrant.size(); i++) {
			tw = listTblPersonalwarrant.get(i);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			startDate1 = df.format(tw.getStartDate());
			endDate1 = df.format(tw.getEndDate());
			String mode2 = GlobalData.warrantModes.get(
					Character.toString(tw.getMode()));
			
			outs.append("<tr>");
			outs.append("<td onclick='updatePersonalwarrantJs("
					+ tw.getKid()
					+ ")'><a href='#'>" + tw.getName());
			outs.append("</a></td>");
			outs.append("<td>" + startDate1);
			outs.append("</td>");
			outs.append("<td>" + endDate1);
			outs.append("</td>");
			outs.append("<td>" + mode2);
			outs.append("</td>");
			outs.append("<td>" + tw.getMoney());
			outs.append("</td>");
			outs.append("<td>" + tw.getRemaining());
			outs.append("</td>");
			outs.append("<td>" + tw.getDescription());
			outs.append("</td>");
			outs.append("<td onclick='personalwarrantListOne("
					+ tw.getKid() + ","
					+ '\"' + tw.getId() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblPersonalwarrant() throws Exception {
		Date tmpDate = null;
		
		try {
			pw = personalwarrantDao.get(kid);

			pw.setName(name);
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			tmpDate = df.parse(startDate);
			pw.setStartDate(tmpDate);
			tmpDate = df.parse(endDate);
			pw.setEndDate(tmpDate);
			
			pw.setMode(mode);
			pw.setMoney(money);
			pw.setRemaining(remaining);
			pw.setDescription(tools.multiLine(description));
			tools.fillQueryInfo(3, id, "TblPersonalwarrant");
			personalwarrantDao.update(pw);
		} catch (Exception e) {
			errorMsg = "修改个人担保信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblPersonalwarrant() throws Exception {
		TblPersonalwarrant bi = personalwarrantDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到个人担保信息" + kid);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public char getMode() {
		return mode;
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPersonalwarrantDao(PersonalwarrantDao personalwarrantDao) {
		this.personalwarrantDao = personalwarrantDao;
	}

	public TblPersonalwarrant getPw() {
		return pw;
	}

	public void setPw(TblPersonalwarrant pw) {
		this.pw = pw;
	}
}
