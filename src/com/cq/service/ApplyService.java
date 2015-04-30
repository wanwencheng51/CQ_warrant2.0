package com.cq.service;

import org.jbpm.api.task.Task;

public interface ApplyService {
	public Task startProcess() throws Exception;
	public void startApply(String taskid, String apply, String pid) throws Exception;
	
	public String checkCompanyApply(String wid, String eid);
	public String submitCompanyApply(String taskid, String wid, String eid, String selValue, String result, char type) throws Exception;
	
	public String checkPersonApply(String wid, String pid);
	public String submitPersonApply(String taskid, String wid, String pid, String selValue, String result, char type) throws Exception;
	
}
