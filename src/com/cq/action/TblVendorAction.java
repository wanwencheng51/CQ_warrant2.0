package com.cq.action;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.VendorDao;
import com.cq.table.TblVendor;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业主要供货商", dataName="vd")
public class TblVendorAction {
	static Logger log = Logger.getLogger(TblVendorAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private String name;
	private Character yearsOfCooperation;
	private Double annualVolume;
	private Double proportion;
	private TblVendor vd;
	
	@Resource VendorDao vendorDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblVendor() throws Exception {
		TblVendor tblVendor = null;

		try {
			tblVendor = new TblVendor();
			tblVendor.setKid(kid);
			tblVendor.setEid(eid);
			tblVendor.setName(name);
			tblVendor.setYearsOfCooperation(yearsOfCooperation);
			tblVendor.setAnnualVolume(annualVolume);
			tblVendor.setProportion(proportion);
            vd = tblVendor;
			vendorDao.save(tblVendor);
			tools.fillQueryInfo(1, eid, "TblVendor");
		} catch (Exception e) {
			errorMsg = "保存企业供应商信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblVendor() throws Exception {
		try {
			vd = vendorDao.get(kid);
			vendorDao.delete(kid);
			this.selectAjaxTblVendor();
		} catch (Exception e) {
			errorMsg = "删除企业供应商信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblVendor() throws Exception {
		String name1 = null;
		StringBuffer outs = null;
		
		List<TblVendor> listTblVendor = vendorDao.findByProperty("eid", eid);
		if (listTblVendor == null || listTblVendor.size() == 0) {
			log.warn("没有查询到企业供应商信息");
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		if (listTblVendor.size() > 0) {
			outs.append("<tr>");
			outs.append("<th width='30%'>供货商名称</th>");
			outs.append("<th width='15%'>合作年限</th>");
			outs.append("<th width='20%'>年交易额</th>");
			outs.append("<th width='20%'>交易额占比</th>");
			outs.append("<th width='15%'>操作</th>");
			outs.append("</tr>");
		}
		for (int i = 0; i < listTblVendor.size(); i++) {
			name1 = listTblVendor.get(i).getName();
			DecimalFormat decimalFormat = new DecimalFormat("###0.00");
			
			outs.append("<tr>");
			outs.append("<td onclick='updateVendorJs("
					+ listTblVendor.get(i).getKid()
					+ ")'><a href='#'>" + name1);
			outs.append("</td>");
			outs.append("<td>"
					+ listTblVendor.get(i).getYearsOfCooperation());
			outs.append("</td>");
			outs.append("<td>"
					+ decimalFormat.format(listTblVendor.get(i).getAnnualVolume()));
			outs.append("</td>");
			outs.append("<td>"
					+ listTblVendor.get(i).getProportion());
			outs.append("</td>");
			outs.append("<td onclick='vendorListOne("
					+ listTblVendor.get(i).getKid() + ',' + '\"'
					+ listTblVendor.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");

		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblVendor() throws Exception {
		try {
			vd = vendorDao.get(kid);
			vd.setName(name);
			vd.setYearsOfCooperation(yearsOfCooperation);
			vd.setAnnualVolume(annualVolume);
			vd.setProportion(proportion);

			vendorDao.update(vd);
			tools.fillQueryInfo(1, eid, "TblVendor");
		} catch (Exception e) {
			errorMsg = "修改企业供应商信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblVendor() throws Exception {
		TblVendor bi = vendorDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到企业主要供应商信息" + kid);
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

	public Character getYearsOfCooperation() {
		return yearsOfCooperation;
	}

	public void setYearsOfCooperation(Character yearsOfCooperation) {
		this.yearsOfCooperation = yearsOfCooperation;
	}

	public Double getAnnualVolume() {
		return annualVolume;
	}

	public void setAnnualVolume(Double annualVolume) {
		this.annualVolume = annualVolume;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public TblVendor getVd() {
		return vd;
	}

	public void setVd(TblVendor vd) {
		this.vd = vd;
	}
}
