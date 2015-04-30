<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.css" />
<link rel="stylesheet" href="<%=basePath%>Validform/style.css" />
<link rel="stylesheet" href="<%=basePath%>css/apply.css" />
<link rel="stylesheet" href="<%=basePath%>css/bankInfo.css" />
<title>系统参数</title>
<style>
	.ui-resizable-se {
		bottom: 17px;
	}
	#awglist .ui-selecting { background: #FECA40; }
	#awglist .ui-selected { background: #F39814; color: white; }
	#awglist { list-style-type: none; margin: 0; padding: 0; width: 450px; }
	#awglist li { margin: 3px; padding: 1px; float: left; width: 100px; height: 20px; font-size: 12px; text-align: center; }
</style>
</head>

<body>
	<fieldset style="margin-top:10px">
		<legend style="color:#CC9900">系统参数配置</legend>
		<div class="edit_comp">
			<iframe name="hideIframe" style="display: none;"></iframe>
			<form method="post" id="frmSysParam" target="hideIframe" action="UpdateTblCfgSysparam">
				<input type="text" style="display:none" id="kid" name="kid" />
				<table width="100%">
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>密码超期提前提醒(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="passwordReminderDays" id="passwordReminderDays"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>密码超期时间(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="passwordExpireDays" id="passwordExpireDays"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>任务超期提前提醒(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="taskReminderDays" id="taskReminderDays"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>任务超期时间(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="taskExpireDays" id="taskExpireDays"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>项目还款提前通知(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="advancedReminderDays" id="advancedReminderDays"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>项目跟踪周期(天)：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="projectTrackPeriod" id="projectTrackPeriod"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>每页显示日志记录数：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="maxLogResults" id="maxLogResults"
							style="width: 50%;" type="text" onkeyup="inputInteger(this)" />
						</td>
					</tr>
					<!-- Begin: Add by Luke Chen on 2015/04/23, for process control -->
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>评审收费至资料收集标志：</span></td>
						<td class="edit_comp_r">
							<input class="comp_input" name="reviewChargeToReceive" id="reviewChargeToReceive"
							style="width: 50%;" type="text" />
						</td>
					</tr>
					<!-- End: Add by Luke Chen on 2015/04/23, for process control -->
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>评审专家组名称：</span></td>
						<td class="edit_comp_r">
							<select name="reviewGroupName" id="reviewGroupName"></select>
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 30%;"><span>具有修改反担保措施权限的组：</span></td>
						<td class="edit_comp_r">
							<textarea class="comp_input" name="antiWarrantGroups" id="antiWarrantGroups"
							style="width: 50%; height:50px" readonly onclick="showAntiGroups('groupList')"
							title="点击这里选择，再次点击确认选择"></textarea>
						</td>
					</tr>
					<tr id="awtdiv" style="display:none;">
						<td></td>
						<td>
							<div id="groupList">
							</div>
							<br />
						</td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" id="spBtn" value="保存" /></td>
					</tr>
				</table>
			</form>
		</div>
	</fieldset>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/rootPath.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/decimal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/systemParameter.js"></script>
</body>
</html>
