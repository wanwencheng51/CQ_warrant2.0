package com.cq.action;

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
import com.cq.dao.CashflowstatementDao;
import com.cq.table.TblCashflowstatement;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业现金流量报表信息", dataName="cfsm")
public class TblCashflowstatementAction {
	static Logger log = Logger.getLogger(TblCashflowstatementAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private char type;
	private String date;
	private Double ncffoa;
	private Double cifoa;
	private Double cofoa;
	private Double ncffia;
	private Double cifia;
	private Double cofia;
	private Double ncfffa;
	private Double ciffa;
	private Double coffa;
	private Double niocce;
	private TblCashflowstatement cfsm;
	
	@Resource CashflowstatementDao cashflowstatementDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblCashflowstatement() throws Exception {
		Date tmpDate = null;
		TblCashflowstatement tblCashflowstatement = null;

		try {
			tblCashflowstatement = new TblCashflowstatement();
			tblCashflowstatement.setEid(eid);
			tblCashflowstatement.setType(type);
	
			if (type == '0') {
				tmpDate = new SimpleDateFormat("yyyy").parse(date);
			} else {
				tmpDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			}
			tblCashflowstatement.setDate(tmpDate);
	
			tblCashflowstatement.setNcffoa(ncffoa);
			tblCashflowstatement.setCifoa(cifoa);
			tblCashflowstatement.setCofoa(cofoa);
			tblCashflowstatement.setNcffia(ncffia);
			tblCashflowstatement.setCifia(cifia);
			tblCashflowstatement.setCofia(cofia);
			tblCashflowstatement.setNcfffa(ncfffa);
			tblCashflowstatement.setCiffa(ciffa);
			tblCashflowstatement.setCoffa(coffa);
			tblCashflowstatement.setNiocce(niocce);
			cfsm = tblCashflowstatement;
			cashflowstatementDao.save(tblCashflowstatement);
			tools.fillQueryInfo(1, eid, "TblCashflowstatement");
		} catch (Exception e) {
			errorMsg = "添加现金流量表数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblCashflowstatement() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = request.getParameter("kid");

		try {
			cfsm = cashflowstatementDao.get(kid);
			cashflowstatementDao.delete(Integer.parseInt(id));
			this.selectAjaxTblCashflowstatement();
		} catch (Exception e) {
			errorMsg = "删除现金流量表数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblCashflowstatement() throws Exception {
		String date1 = null;
		String Type2 = null;
		StringBuffer outs = null;
		
		List<TblCashflowstatement> listTblCashflowstatement = cashflowstatementDao.findByProperty("eid", eid);
		if((listTblCashflowstatement == null)
				|| !(listTblCashflowstatement.size() > 0)) {
			log.warn("没有查询到现金流量表数据" + listTblCashflowstatement);
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th>报表日期</th>");
		outs.append("<th>报表类型</th>");
		outs.append("<th>经营活动产生的现金流入量</th>");
		outs.append("<th>经营活动产生的现金流出量</th>");
		outs.append("<th>经营活动产生的现金流量净额</th>");
		outs.append("<th>投资活动产生的现金流入量</th>");
		outs.append("<th>投资活动产生的现金流出量</th>");
		outs.append("<th>投资活动产生的现金流量净额</th>");
		outs.append("<th>筹资活动产生的现金流入量</th>");
		outs.append("<th>筹资活动产生的现金流出量</th>");
		outs.append("<th>筹资活动产生的现金流量净额</th>");
		outs.append("<th>现金及现金等价物净增加额</th>");
		outs.append("<th>操作</th>");
		outs.append("</tr>");
		
		for (int i = 0; i < listTblCashflowstatement.size(); i++) {
			char Type = listTblCashflowstatement.get(i).getType();
			if (Type == '0') {
				Type2 = "年度报表";
				date1 = new SimpleDateFormat("yyyy")
						.format(listTblCashflowstatement.get(i).getDate());
			} else {
				Type2 = "最新报表";
				date1 = new SimpleDateFormat("yyyy-MM-dd").format(listTblCashflowstatement.get(i).getDate());
			}
			DecimalFormat decimalFormat = new DecimalFormat("###0.00");
			
			outs.append("<tr>");
			outs.append("<td onclick='updateCashflowstatementJs("
					+ listTblCashflowstatement.get(i).getKid()
					+ ")'><a href='#'>" + date1);
			outs.append("</td>");
			outs.append("<td>" + Type2);
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCifoa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCofoa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getNcffoa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCifia()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCofia()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getNcffia()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCiffa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getCoffa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getNcfffa()));
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblCashflowstatement.get(i).getNiocce()));
			outs.append("</td>");
			outs.append("<td onclick='cashflowstatementListOne("
					+ listTblCashflowstatement.get(i).getKid() + ','
					+ '\"' + listTblCashflowstatement.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		
		tools.outputInfo(outs);
	}
	
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblCashflowstatement() throws Exception {
		Date tmpDate = null;

		if (type == '0') {
			tmpDate = new SimpleDateFormat("yyyy").parse(date);
		} else {
			tmpDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		}

		try {
			cfsm = cashflowstatementDao.get(kid);
			cfsm.setCiffa(ciffa);
			cfsm.setCofoa(cofoa);
			cfsm.setCifoa(cifoa);
			cfsm.setCoffa(coffa);
			cfsm.setCifia(cifia);
			cfsm.setCofia(cofia);
			cfsm.setDate(tmpDate);
			cfsm.setEid(eid);
			cfsm.setNcfffa(ncfffa);
			cfsm.setNcffia(ncffia);
			cfsm.setNcffoa(ncffoa);
			cfsm.setNiocce(niocce);
			cfsm.setType(type);
			cashflowstatementDao.update(cfsm);
			tools.fillQueryInfo(1, eid, "TblCashflowstatement");
		} catch (Exception e) {
			errorMsg = "修改现金流量表数据失败";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblCashflowstatement() throws Exception {
		TblCashflowstatement bi = cashflowstatementDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到信息" + kid);
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getNcffoa() {
		return ncffoa;
	}

	public void setNcffoa(Double ncffoa) {
		this.ncffoa = ncffoa;
	}

	public Double getCifoa() {
		return cifoa;
	}

	public void setCifoa(Double cifoa) {
		this.cifoa = cifoa;
	}

	public Double getCofoa() {
		return cofoa;
	}

	public void setCofoa(Double cofoa) {
		this.cofoa = cofoa;
	}

	public Double getNcffia() {
		return ncffia;
	}

	public void setNcffia(Double ncffia) {
		this.ncffia = ncffia;
	}

	public Double getCifia() {
		return cifia;
	}

	public void setCifia(Double cifia) {
		this.cifia = cifia;
	}

	public Double getCofia() {
		return cofia;
	}

	public void setCofia(Double cofia) {
		this.cofia = cofia;
	}

	public Double getNcfffa() {
		return ncfffa;
	}

	public void setNcfffa(Double ncfffa) {
		this.ncfffa = ncfffa;
	}

	public Double getCiffa() {
		return ciffa;
	}

	public void setCiffa(Double ciffa) {
		this.ciffa = ciffa;
	}

	public Double getCoffa() {
		return coffa;
	}

	public void setCoffa(Double coffa) {
		this.coffa = coffa;
	}

	public Double getNiocce() {
		return niocce;
	}

	public void setNiocce(Double niocce) {
		this.niocce = niocce;
	}

	public void setCashflowstatementDao(CashflowstatementDao cashflowstatementDao) {
		this.cashflowstatementDao = cashflowstatementDao;
	}

	public TblCashflowstatement getCfsm() {
		return cfsm;
	}

	public void setCfsm(TblCashflowstatement cfsm) {
		this.cfsm = cfsm;
	}
}
