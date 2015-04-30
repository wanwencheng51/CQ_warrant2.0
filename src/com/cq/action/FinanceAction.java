package com.cq.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;

import com.cq.service.FinanceService;
import com.cq.service.TaskBaseService;
import com.cq.table.TblFinanceest;
import com.cq.util.WarrantException;
import com.cq.util.tools;

public class FinanceAction extends TaskBaseAction{
	static Logger log = Logger.getLogger(FinanceAction.class);
	private String errorMsg;
	
	private String sel;
	private String wid;
	private String selValue;
	private String taskid;
	private String startDate;
	private String assets;
	private double debts;
	private double asset;
	private double betAsset;
	private double loanRemaining;
	private double flowAsset;
	private double flowDebt;
	private double saleCash;
	private double dueDebt;
	private double saleMoney;
	private double meanAsset;
	private double netProfit;
	private double thisYearSale;
	private double lastYearSale;
	private double dueCash;
	private double loan;
	
	@Resource TaskBaseService taskBaseService;
	@Resource FinanceService financeService;

	public String finance() throws Exception {
		if (sel.equals("transfer")) {
			return taskBaseService.transfer(selValue, taskid);
		}
		
		if (startDate == null) {
			errorMsg = "对不起，您未输入时间，提交失败";
			log.error(errorMsg);
			throw new WarrantException(errorMsg);
		} else {
			String[] sd = startDate.split(",");
			int start = Integer.parseInt(sd[0].trim());
			int end = Integer.parseInt(sd[1].trim());
			if (end <= start) {
				errorMsg = "截止时间不能大于开始时间，提交失败";
				log.error(errorMsg);
				throw new WarrantException(errorMsg);
			}
			startDate = startDate.replaceAll(", ", "-");
		}
		
		String temp = financeService.finance(taskid, sel, selValue, 
				wid, startDate, debts, asset, betAsset,
				loanRemaining, flowAsset, flowDebt, saleCash,
				dueDebt, saleMoney, meanAsset, netProfit,
				thisYearSale, lastYearSale, dueCash, loan);
		if ("success".equals(temp) == false) {
			tools.returnError(log, "处理财务审查业务时系统出现错误");
			return "error";
		}
		return "success";
	}
	
	public void getFinanceestInfo() throws Exception {
		List<TblFinanceest> fel = null;
		
		TblFinanceest ss = null;
		JsonConfig cfg = null;
		
		try {
			fel = financeService.getTblFinanceest(wid);
			
			if((fel == null) || (fel.size() <= 0)) {
				log.warn("系统中没有项目" + wid +"的财务评估数据");
				tools.outputInfo(JSONObject.fromObject(null));
//				errorMsg = "系统中没有项目" + wid +"的财务评估数据";
//				log.error(errorMsg);
//				throw new WarrantException(errorMsg);
				return;
			}
			cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < fel.size(); i++) {
				ss = (TblFinanceest) fel.get(i);
				ja.add(JSONObject.fromObject(ss, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("financeEstInfo", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出财务评估数据时系统发生异常";
			tools.throwException(e, log, errorMsg);
		}
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public double getDebts() {
		return debts;
	}

	public void setDebts(double debts) {
		this.debts = debts;
	}

	public double getAsset() {
		return asset;
	}

	public void setAsset(double asset) {
		this.asset = asset;
	}

	public double getBetAsset() {
		return betAsset;
	}

	public void setBetAsset(double betAsset) {
		this.betAsset = betAsset;
	}

	public double getLoanRemaining() {
		return loanRemaining;
	}

	public void setLoanRemaining(double loanRemaining) {
		this.loanRemaining = loanRemaining;
	}

	public double getFlowAsset() {
		return flowAsset;
	}

	public void setFlowAsset(double flowAsset) {
		this.flowAsset = flowAsset;
	}

	public double getFlowDebt() {
		return flowDebt;
	}

	public void setFlowDebt(double flowDebt) {
		this.flowDebt = flowDebt;
	}

	public double getSaleCash() {
		return saleCash;
	}

	public void setSaleCash(double saleCash) {
		this.saleCash = saleCash;
	}

	public double getDueDebt() {
		return dueDebt;
	}

	public void setDueDebt(double dueDebt) {
		this.dueDebt = dueDebt;
	}

	public double getSaleMoney() {
		return saleMoney;
	}

	public void setSaleMoney(double saleMoney) {
		this.saleMoney = saleMoney;
	}

	public double getMeanAsset() {
		return meanAsset;
	}

	public void setMeanAsset(double meanAsset) {
		this.meanAsset = meanAsset;
	}

	public double getNetProfit() {
		return netProfit;
	}

	public void setNetProfit(double netProfit) {
		this.netProfit = netProfit;
	}

	public double getThisYearSale() {
		return thisYearSale;
	}

	public void setThisYearSale(double thisYearSale) {
		this.thisYearSale = thisYearSale;
	}

	public double getLastYearSale() {
		return lastYearSale;
	}

	public void setLastYearSale(double lastYearSale) {
		this.lastYearSale = lastYearSale;
	}

	public double getDueCash() {
		return dueCash;
	}

	public void setDueCash(double dueCash) {
		this.dueCash = dueCash;
	}

	public double getLoan() {
		return loan;
	}

	public void setLoan(double loan) {
		this.loan = loan;
	}

	public String getSelValue() {
		return selValue;
	}

	public void setSelValue(String selValue) {
		this.selValue = selValue;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

}
