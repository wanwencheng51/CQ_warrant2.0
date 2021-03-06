package com.cq.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cq.service.StationLetterService;
import com.cq.table.TblStationLetter;
import com.cq.util.tools;

/**
 * @author qibo
 *	站内信
 */
public class StationLetterAction {
	static Logger log = Logger.getLogger(StationLetterAction.class);
	private String errorMsg;
	
	private String sendID;
	private String recID;
	private String caption;
	private String message; 
	private Date sendDate;     
	private int dispatch;
	private int kid;
	
	//收件箱
	@Resource StationLetterService stationLetterService;
	
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void receiver() throws Exception {
		try {
			List<TblStationLetter> sl = stationLetterService.listLetterReceiver();
			if((sl == null) || (sl.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("没有查询到收件箱信息数据" + sl);
				return;
			}
			JsonConfig cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < sl.size(); i++) {
				TblStationLetter tb = (TblStationLetter) sl.get(i);
				ja.add(JSONObject.fromObject(tb, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("banks", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出收件箱数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	//发件箱
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void addresser() throws Exception {
		try {
			List<TblStationLetter> sl = stationLetterService.listLetterSend();
			if((sl == null) || (sl.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("没有查询到发件箱信息数据" + sl);
				return;
			}
			JsonConfig cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < sl.size(); i++) {
				TblStationLetter tb = (TblStationLetter) sl.get(i);
				ja.add(JSONObject.fromObject(tb, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("banks", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出发件箱数据失败";
			tools.throwException(e, log, errorMsg);
		}
	}
	
	//回收箱
	@Transactional (propagation=Propagation.NOT_SUPPORTED, readOnly=true)
	public void draft() throws Exception{
		try {
			List<TblStationLetter> sl = stationLetterService.listLetterCallback();
			if((sl == null) || (sl.size() <= 0)) {
				tools.outputInfo(JSONObject.fromObject(null));
				log.warn("没有查询到回收箱信息数据" + sl);
				return;
			}
			JsonConfig cfg = tools.getJsonConfig();
			JSONArray ja = new JSONArray();
			for(int i = 0; i < sl.size(); i++) {
				TblStationLetter tb = (TblStationLetter) sl.get(i);
				ja.add(JSONObject.fromObject(tb, cfg));
			}
			JSONObject result = new JSONObject();
			result.put("banks", ja.toString());
			tools.outputInfo(result);
		} catch (Exception e) {
			errorMsg = "输出回收箱数据失败";
			tools.throwException(e, log, errorMsg);
		}
		
	}
	
	//发送邮件
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String addReceiver() throws Exception{
		try {
			sendID = tools.getLoginUser();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sendDate = sdf.parse(sdf.format(new Date()));
			stationLetterService.addLetter(sendID, recID, caption, message, sendDate, dispatch);
		} catch (Exception e) {
			errorMsg = "发送个人邮件失败";
			tools.throwException(e, log, errorMsg);
		}
		return "letterSuccess";
	}
	
	//从发件箱删除邮件到垃圾箱
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String deleteSndLetter() throws Exception{
		try {
			stationLetterService.deleteSndLetter(kid);
		} catch (Exception e) {
			errorMsg = "删除邮件失败";
			tools.throwException(e, log, errorMsg);
		}
		return "letterSuccess";
	}
	
	//从收件箱删除邮件到垃圾箱
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String deleteRcvLetter() throws Exception{
		try {
			stationLetterService.deleteRcvLetter(kid);
		} catch (Exception e) {
			errorMsg = "删除邮件失败";
			tools.throwException(e, log, errorMsg);
		}
		return "letterSuccess";
	}
	
	//删除邮件(清空回收箱)
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String everDeleteLetter() throws Exception{
		try {
			stationLetterService.delete(kid);
		} catch (Exception e) {
			errorMsg = "删除邮件失败";
			tools.throwException(e, log, errorMsg);
		}
		return "letterSuccess";
	}
	
	//已读/未读
	@Transactional (propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	public String letterStatus() throws Exception{
		try {
			stationLetterService.updateReadStatus(kid);
		} catch (Exception e) {
			errorMsg = "更改邮件查看状态失败";
			tools.throwException(e, log, errorMsg);
		}
		return "letterSuccess";
	}

	public String getSendID() {
		return sendID;
	}

	public void setSendID(String sendID) {
		this.sendID = sendID;
	}

	public String getRecID() {
		return recID;
	}

	public void setRecID(String recID) {
		this.recID = recID;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public int getDispatch() {
		return dispatch;
	}

	public void setDispatch(int dispatch) {
		this.dispatch = dispatch;
	}

	public int getKid() {
		return kid;
	}

	public void setKid(int kid) {
		this.kid = kid;
	}

	public void setStationLetterService(StationLetterService stationLetterService) {
		this.stationLetterService = stationLetterService;
	}
}
