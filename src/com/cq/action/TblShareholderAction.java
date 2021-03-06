package com.cq.action;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.ShareholderDao;
import com.cq.table.TblShareholder;
import com.cq.util.GlobalData;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业主要股东信息", dataName="tsh")
public class TblShareholderAction {
	static Logger log = Logger.getLogger(TblShareholderAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private String name;
	private char type;
	private String SID;
	private double money;
	private char mode;
	private double Proportion;
	private String description;
	
	private TblShareholder tsh;
	
	@Resource ShareholderDao shareholderDao;

	// 获取数据库所有股东投资比例之和
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getstock() throws Exception {
		try {
			double number = shareholderDao.getStockProportionSum(eid);
			tools.outputInfo(number);
		} catch (Exception e) {
			errorMsg = "获取所有股东投资比例之和时出现异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	// 验证股东信息是否重复
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void yzstockerfhx() throws Exception {
		//String sql = null;
		String str = "stok";
		
		try {
			/*sql = "select SID from  tbl_shareholder  where Eid='" + eid
					+ "'and Name='" + name + "'and Mode='" + mode
					+ "'and SID='" + SID + "'";*/
			DetachedCriteria dc = DetachedCriteria.forClass(TblShareholder.class);
			dc.add(Restrictions.eq("eid", eid));
			dc.add(Restrictions.eq("name", name));
			dc.add(Restrictions.eq("mode", mode));
			dc.add(Restrictions.eq("sid", SID));
			
			List<TblShareholder> listTblShareholder = shareholderDao.findByCriterias(dc);
			if (listTblShareholder != null && listTblShareholder.size() > 0) {
				str = eid;
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "股东信息重复";
			tools.throwException(e, log, errorMsg);
		}
	}
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void yzstockerfhx1() throws Exception {
		//String sql = null;
		String str = "stok";
		
		try {
			/*sql = "select SID from  tbl_shareholder  where Eid='" + eid
					+ "'and Name='" + name + "'and Mode='" + mode
					+ "'and SID='" + SID + "'and kid!='"+kid+"'";*/
			DetachedCriteria dc = DetachedCriteria.forClass(TblShareholder.class);
			dc.add(Restrictions.eq("eid", eid));
			dc.add(Restrictions.eq("name", name));
			dc.add(Restrictions.eq("mode", mode));
			dc.add(Restrictions.eq("sid", SID));
			dc.add(Restrictions.ne("kid", kid));
			
			List<TblShareholder> listTblShareholder = shareholderDao.findByCriterias(dc);
			if (listTblShareholder != null && listTblShareholder.size() > 0) {
				str=eid;
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "股东信息重复";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void applyYanZhengtype() throws Exception {
		String str = "stk";
		
		try {
			String[] s = new String[1];
			s[0] = Character.toString('0');
			
			DetachedCriteria dc = DetachedCriteria.forClass(TblShareholder.class).add(Restrictions.eq("eid", eid)).add(Restrictions.eq("type", type)).add(Restrictions.in("type", s));
			List<TblShareholder> listTblShareholder = shareholderDao.findByCriterias(dc);
			if (listTblShareholder != null && listTblShareholder.size() > 0) {
				str = listTblShareholder.get(0).getEid();
			}
			tools.outputInfo(str);
		} catch (Exception e) {
			errorMsg = "输入的股东类型不合法";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblShareholder() throws Exception {
		TblShareholder tblShareholder = null;
		try {
			tblShareholder = new TblShareholder();
			tblShareholder.setKid(kid);
			tblShareholder.setEid(eid);
			tblShareholder.setName(name);
			tblShareholder.setType(type);
			tblShareholder.setSid(SID);
			tblShareholder.setMoney(money);
			tblShareholder.setMode(mode);
			tblShareholder.setProportion(Proportion);
			tblShareholder.setDescription(tools.multiLine(description));
			
			shareholderDao.save(tblShareholder);
			tsh = tblShareholder;
			tools.fillQueryInfo(1, eid, "TblShareholder");
		} catch (Exception e) {
			errorMsg = "保存企业注册股东信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblShareholder() throws Exception {
		try {
			tsh = shareholderDao.get(kid);
			shareholderDao.delete(kid);
			this.selectAjaxTblShareholder();
		} catch (Exception e) {
			errorMsg = "删除企业注册股东信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblShareholder() throws Exception {
		String name1 = null;
		String type1 = null;
		String mode1 = null;
		StringBuffer outs = null;
		
		List<TblShareholder> listStock = shareholderDao.findByProperty("eid", eid);
		if (listStock == null || listStock.size() == 0) {
			log.warn("没有查询到企业注册股东信息");
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		if (listStock.size() > 0) {
			outs.append("<tr>");
			outs.append("<th width='13%'>股东名称</th>");
			outs.append("<th width='13%'>股东类型</th>");
			outs.append("<th width='13%'>股东编码</th>");
			outs.append("<th width='11%'>投资金额</th>");
			outs.append("<th width='13%'>入资形式</th>");
			outs.append("<th width='11%'>投资比例</th>");
			outs.append("<th width='19%'>描述</th>");
			outs.append("<th width='7%'>操作</th>");
			outs.append("</tr>");
		}
		for (int i = 0; i < listStock.size(); i++) {
			name1 = listStock.get(i).getName();
            char type2 = listStock.get(i).getType();
			if ('0' == type2) {
				type1 = "法人股东";
			} else if ('1' == type2) {
				type1 = "自然人股东";
			} else {
				type1 = "";
			}
			
			mode1 = GlobalData.capitalModes.get(
					Character.toString(listStock.get(i).getMode()));
			DecimalFormat decimalFormat = new DecimalFormat("###0.00");
			
			outs.append("<tr>");
			outs.append("<td onclick='updateStockJs("
					+ listStock.get(i).getKid()
					+ ")'><a href='#'>" + name1);
			outs.append("</td>");
			outs.append("<td>" + type1);
			outs.append("</td>");
			outs.append("<td>" + listStock.get(i).getSid());
			outs.append("</td>");
			outs.append("<td>" + decimalFormat.format(listStock.get(i).getMoney()));
			outs.append("</td>");
			outs.append("<td>" + mode1);
			outs.append("</td>");
			outs.append("<td>" + listStock.get(i).getProportion());
			outs.append("</td>");
			outs.append("<td>"
					+ listStock.get(i).getDescription());
			outs.append("</td>");
			outs.append("<td onclick='stockListOne("
					+ listStock.get(i).getKid() + ',' + '\"'
					+ listStock.get(i).getEid() + '\"'
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");

		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblShareholder() throws Exception {
		try {
			tsh = shareholderDao.get(kid);
			tsh.setName(name);
			tsh.setType(type);
			tsh.setSid(SID);
			tsh.setMoney(money);
			tsh.setMode(mode);
			tsh.setProportion(Proportion);
			tsh.setDescription(tools.multiLine(description));

			shareholderDao.update(tsh);
			tools.fillQueryInfo(1, eid, "TblShareholder");
		} catch (Exception e) {
			errorMsg = "修改企业注册股东信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblShareholder() throws Exception {
		TblShareholder bi = shareholderDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到企业主要股东信息" + kid);
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

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public char getMode() {
		return mode;
	}

	public void setMode(char mode) {
		this.mode = mode;
	}

	public double getProportion() {
		return Proportion;
	}

	public void setProportion(double proportion) {
		Proportion = proportion;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setShareholderDao(ShareholderDao shareholderDao) {
		this.shareholderDao = shareholderDao;
	}

	public TblShareholder getTsh() {
		return tsh;
	}

	public void setTsh(TblShareholder tsh) {
		this.tsh = tsh;
	}
}
