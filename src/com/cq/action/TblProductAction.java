package com.cq.action;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.annotation.CQAnnotationOperlog;
import com.cq.dao.ProductDao;
import com.cq.table.TblProduct;
import com.cq.util.tools;

@CQAnnotationOperlog(tableName="企业主要产品信息", dataName="product")
public class TblProductAction {
	static Logger log = Logger.getLogger(TblProductAction.class);
	private String errorMsg;
	
	private int kid;
	private String eid;
	private String name;
	private Character type;
	private String purpose;
	private Character tech;
	private String saleDomain;
	private String saleArea;
	private BigDecimal incomeProportion;
	private BigDecimal marketShare;
	private TblProduct product;
	
	@Resource ProductDao productDao;

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String savaTblProduct() throws Exception {
		try {
			TblProduct tblProduct = new TblProduct();
			tblProduct.setEid(eid);
			tblProduct.setName(name);
			tblProduct.setType(type);
			tblProduct.setPurpose(tools.multiLine(purpose));
			tblProduct.setTech(tech);
			tblProduct.setSaleDomain(tools.multiLine(saleDomain));
			tblProduct.setSaleArea(tools.multiLine(saleArea));
			if (null != incomeProportion) {
				tblProduct.setIncomeProportion(incomeProportion);
			} else {
				BigDecimal bd = new BigDecimal("0");
				tblProduct.setIncomeProportion(bd);
			}
			if (null != marketShare) {
				tblProduct.setMarketShare(marketShare);
			} else {
				BigDecimal bd = new BigDecimal("0");
				tblProduct.setMarketShare(bd);
			}
			product = tblProduct;
			productDao.save(product);
			tools.fillQueryInfo(1, eid, "TblProduct");
		} catch (Exception e) {
			errorMsg = "保存企业主要产品信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public void outDeleteTblProduct() throws Exception {
		try {
			product = productDao.get(kid);
			productDao.delete(kid);
			this.selectAjaxTblProduct();
		} catch (Exception e) {
			errorMsg = "删除企业主要产品信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void selectAjaxTblProduct() throws Exception {
		String name1 = null;
		String type2 = null;
		String purpose1 = null;
		String tech2 = null;
		String saleDomain1 = null;
		String saleArea1 = null;
		StringBuffer outs = null;
		
		List<TblProduct> listTblProduct = productDao.findByProperty("eid", eid);
		if (listTblProduct == null || listTblProduct.size() == 0) {
			log.warn("没有查询到企业主要产品信息");
			return;
		}
		
		outs = new StringBuffer();
		outs.append("<table>");
		outs.append("<tr>");
		outs.append("<th width='12%'>产品名称</th>");
		outs.append("<th width='12%'>产品类型</th>");
		outs.append("<th width='12%'>产品用途</th>");
		outs.append("<th width='12%'>技术含量</th>");
		outs.append("<th width='12%'>销售领域</th>");
		outs.append("<th width='12%'>销售地区</th>");
		outs.append("<th width='12%'>销售收入占比(%)</th>");
		outs.append("<th width='12%'>市场占有率(%)</th>");
		outs.append("<th width='4%'>操作</th>");
		outs.append("</tr>");
		
		for (int i = 0; i < listTblProduct.size(); i++) {
			name1 = listTblProduct.get(i).getName();
			char type1 = listTblProduct.get(i).getType();
			if ('0' == type1) {
				type2 = "实物";
			} else if ('1' == type1) {
				type2 = "服务";
			} else {
				type2 = "";
			}
			
			purpose1 = listTblProduct.get(i).getPurpose();
			
			char tech1 = listTblProduct.get(i).getTech();
			if ('0' == tech1) {
				tech2 = "高";
			} else if ('1' == tech1) {
				tech2 = "中";
			} else if ('2' == tech1) {
				tech2 = "低";
			} else {
				tech2 = "";
			}
			saleDomain1 = listTblProduct.get(i).getSaleDomain();
			saleArea1 = listTblProduct.get(i).getSaleArea();
			
			outs.append("<tr>");
			outs.append("<td onclick='updateZycpJs("
					+ listTblProduct.get(i).getKid()   
					+ ")'><a href='#'>" + name1);
			outs.append("</a></td>");
			outs.append("<td>" + type2);
			outs.append("</td>");
			outs.append("<td>" + purpose1);
			outs.append("</td>");
			outs.append("<td>" + tech2);
			outs.append("</td>");
			outs.append("<td>" + saleDomain1);
			outs.append("</td>");
			outs.append("<td>" + saleArea1);
			outs.append("</td>");
			outs.append("<td>"
					+ listTblProduct.get(i).getIncomeProportion());
			outs.append("</td>");
			outs.append("<td>"
					+ listTblProduct.get(i).getMarketShare());
			outs.append("</td>");
			outs.append("<td onclick='zycpListOne("
					+ listTblProduct.get(i).getKid() + ',' 
					+ listTblProduct.get(i).getEid() 
					+ ")'><a href='#'>删除");
			outs.append("</a></td>");
			outs.append("</tr>");
		}
		outs.append("</table>");
		
		tools.outputInfo(outs);
	}

	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String updateTblProduct() throws Exception {
		try {
			product = productDao.get(kid);
			product.setName(name);
			product.setType(type);
			product.setPurpose(tools.multiLine(purpose));
			product.setTech(tech);
			product.setSaleDomain(tools.multiLine(saleDomain));
			product.setSaleArea(tools.multiLine(saleArea));
			product.setIncomeProportion(incomeProportion);
			product.setMarketShare(marketShare);
			productDao.update(product);
			tools.fillQueryInfo(1, eid, "TblProduct");
		} catch (Exception e) {
			errorMsg = "修改企业主要产品信息时发生异常";
			tools.throwException(e, log, errorMsg);
		}
		return "success";
	}
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getTblProduct() throws Exception {
		TblProduct bi = productDao.get(kid);
		
		if(bi == null) {
			tools.outputInfo(JSONObject.fromObject(""));
			log.warn("没有查询到企业主要产品信息" + kid);
			return;
		}
		tools.outputInfo(JSONObject.fromObject(bi));
	}
	
	/**
	 * 
	 * @Title: getProductSale 
	 * @Description:(从数据库中获取销售占比总和) 
	 * @return 返回类型 void
	 * @author cfe 
	 * @throws Exception 
	 * @date 2014年7月3日 下午4:13:38
	 */
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void getProductSale() throws Exception {
		try {
			double number = productDao.getIncomeProportionSum(eid);
			tools.outputInfo(number);
		} catch (Exception e) {
			errorMsg = "获取企业主要产品销售占比时发生异常";
			tools.throwException(e, log, errorMsg);
		}
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

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Character getTech() {
		return tech;
	}

	public void setTech(Character tech) {
		this.tech = tech;
	}

	public String getSaleDomain() {
		return saleDomain;
	}

	public void setSaleDomain(String saleDomain) {
		this.saleDomain = saleDomain;
	}

	public String getSaleArea() {
		return saleArea;
	}

	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}

	public BigDecimal getIncomeProportion() {
		return incomeProportion;
	}

	public void setIncomeProportion(BigDecimal incomeProportion) {
		this.incomeProportion = incomeProportion;
	}

	public BigDecimal getMarketShare() {
		return marketShare;
	}

	public void setMarketShare(BigDecimal marketShare) {
		this.marketShare = marketShare;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public TblProduct getProduct() {
		return product;
	}

	public void setProduct(TblProduct product) {
		this.product = product;
	}
}
