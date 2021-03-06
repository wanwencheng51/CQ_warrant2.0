<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String username = (String) session.getAttribute("user");
	String taskid = null;
	if(request.getAttribute("taskidx") == null) {
		taskid = (String)request.getParameter("id");
	} else if(request.getParameter("id") == null) {
		taskid = (String) request.getAttribute("taskidx");
	}
	String wid = (String)request.getParameter("wid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>个人担保申请信息</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>Validform/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/apply.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/personApply.css" />
</head>

<body>
	<div id="con">
<!-- 		<ul>
			<li><a href="#personWarrant" class="selectTag">个人担保申请信息</a></li>
			<li><a href="#personBasic">个人基本信息</a></li>
			<li><a href="#personCredit">个人信用信息</a></li>
		</ul> -->
		<div class="tagContent" id="personWarrant">
			<h1 align="center" style="font-size: 16px;padding-bottom: 5px;color: #0D7DB9;">个人担保申请</h1>
			<a class="header" href="#">个人基本信息</a>
			<div>
				<form class="personform" target="hideIframe"
					method="post" id="personinfo">
					<input type="text" style="display:none" id="person_exist" value="" datatype="personflag" />
					<input name="taskid" id="taskid" type="text" style="display: none;" value="<%=taskid%>" />
					<table width="100%">
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>身份证号码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="PerID" id="PerID" style="width: 50%;" type="text"
								maxlength="18" onchange="getPersonInfo(this.value)"
								datatype="a4" sucmsg=" " nullmsg="请填写身份证信息"
								errormsg="身份证号码二代18位/一代15位" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>姓名：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="perName" style="width: 50%;" type="text"
								datatype="*" sucmsg=" " nullmsg="请填写姓名" errormsg="姓名不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>性别：</span></td>
							<td class="edit_comp_r"><select class="comp_input"
								name="Gender" id="Gender" style="width: 50.8%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>出生日期：</span></td>
							<td class="edit_comp_r"><input id="Birthday" name="Birthday" style="width: 50.2%;"
								type="text" class="Wdate" onFocus="WdatePicker()" datatype="*"
								sucmsg=" " nullmsg="请填写出生日期" errormsg="出生日期不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>家庭住址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Address" id="Address" style="width: 50%;" type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>户口所在地：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegisterAddress" id="RegisterAddress"
								style="width: 50%;" type="text"  datatype="*"
								sucmsg=" " nullmsg="请填写户口所在地" errormsg="户口所在地不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>职业：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Vocation" id="Vocation" style="width: 50%;" type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>移动电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Mobile" id="Mobile" style="width: 50%;" type="text" datatype="yd"
								sucmsg=" " errormsg="移动电话为数字" onkeyup="inputInteger(this)" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>固定电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Fix"
								id="Fix" style="width: 50%;" type="text" datatype="yd"
								sucmsg=" " errormsg="固定电话为数字" onkeyup="inputInteger(this)" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>电子邮箱：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Email" id="Email" style="width: 50%;" type="text" datatype="eml"
								sucmsg=" " errormsg="请填写正确格式的电子邮箱，例如 myemail@qq.com" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>说明：</span></td>
							<td class="edit_comp_r">
								<textarea name="Description" altercss="gray" id="perDescription"></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="button" id="perBtn" value="保存" onclick="perBtnClick()" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<a class="header" href="#" id="reginfo_span">个人担保申请信息</a>
			<div class="edit_comp">
				<iframe name="hideIframe" style="display: none;"></iframe>
				<form method="post" id="frmWarrantinfo"
					target="hideIframe" class="demoform"  >
					<input type="text" style="display:none" id="wi_exist" name="wi_exist" datatype="wiflag" value="" />
					<input name="taskid" id="taskid" type="text"
						style="display: none;" value="<%=taskid%>" /><input name="wid"
						id="wid" style="display: none;" type="text" value="<%=wid%>" />
					<input name="applyName" id="applyName" style="display: none;"
						type="text" value="" />
					<table width="100%">
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>担保项目名称：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="Name" style="width: 50%;" type="text"
								value="" datatype="s" sucmsg=" " nullmsg="请填写担保项目名称"
								errormsg="担保项目名称不能含特殊字符" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目起始日期：</span></td>
							<td><input id="d11" type="text" class="Wdate" style="width: 50%;"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="StartDate"
								value="" datatype="*" sucmsg=" " nullmsg="请填写项目起始日期"
								errormsg="项目起始日期不能为空" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>申请担保金额(万)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Money" id="Money" style="width: 50%;" type="text"
								value="" datatype="m" sucmsg=" " nullmsg="请填写申请担保金额"
								errormsg="不能为负数且小数点后最多保留两位" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款银行：</span></td>
							<td><select name="Bank" id="Bank" style="width: 50%;" datatype="bank" sucmsg=" " nullmsg="请填写贷款银行"
								errormsg="贷款银行不能为空">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款期限(月)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Deadline" id="Deadline" style="width: 50%;"
								type="text" value="" datatype="m1" sucmsg=" " nullmsg="请填写贷款期限"
								errormsg="贷款期限为整数" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款用途：</span></td>
							<td class="edit_comp_r"><textarea name="Usages"
									id="Usages" altercss="gray"></textarea></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目简介：</span></td>
							<td class="edit_comp_r"><textarea name="Description"
									id="Description" altercss="gray"></textarea></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>主要还款来源：</span></td>
							<td class="edit_comp_r"><textarea xtype="tbtext" wrap=""
									name="PaySource" id="PaySource" altercss="gray"></textarea></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目还款计划：</span></td>
							<td class="edit_comp_r"><textarea nnowrap=true=
									"true" name="PayPlan" id="PayPlan" altercss="gray"></textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="warrantBtn" id="warrantBtn"
								value="保存" /></td>
						</tr>
					</table>
				</form>
				注：选择银行时只能选择授信终止时间在今天之前的，如需修改或者添加，可在银行信息管理中操作
			</div>
			
			<a class="header" href="#" onclick="javascript:TblAntiwarrantAjax();">反担保措施</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:antiwarrant();">添加反担保措施</a>
				<div id="TblAntiwarrantdiv"></div>
			</div>
			<br />
			<fieldset>
				<legend>项目提交</legend>
				<form action="ApplyPass" method="post" id="applyPerson_Form">
					<div style="display: none;">
						<input name="wid" type="text" value="<%=wid%>" />
						<input name="taskid" type="text" value="<%=taskid%>" />
						<input name="type" type="text" value="1" />
						<input name="pid" id="perid" type="text" />
						<input type="text" id="username" value="<%=username%>" />
						<input type="text" id="num" value="personApply" />
					</div>
					<table>
						<tr>
							<td width="90">处理方式：</td>
							<td><select name="sel" id="processid" style="width: 150px">
								<option value="nextLater">下一处理环节</option>
								<option value="transfer">委托他人处理</option>
							</select></td>
						</tr>
						<tr>
							<td width="90">指定处理人：</td>
							<td id="selOpe"></td>
						</tr>
						<tr>
							<td>
								<button type="button" onclick="applyPerson()">提交</button>
							</td>
						</tr>
					</table>
				</form>
			</fieldset>
		</div>
		<div class="tagContent none" id="personBasic">
			<a class="header" href="#">个人基本信息</a>
			<div>
				<form class="personform" target="hideIframe"
					method="post" id="personinfo">
					<input type="text" style="display:none" id="person_exist" value="" datatype="personflag" />
					<input name="taskid" id="taskid" type="text" style="display: none;" value="<%=taskid%>" />
					<table width="100%">
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>身份证号码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="PerID" id="PerID" style="width: 50%;" type="text"
								maxlength="18" onchange="getPersonInfo(this.value)"
								datatype="a4" sucmsg=" " nullmsg="请填写身份证信息"
								errormsg="身份证号码二代18位/一代15位" placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>姓名：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="perName" style="width: 50%;" type="text"
								datatype="*" sucmsg=" " nullmsg="请填写姓名" errormsg="姓名不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>性别：</span></td>
							<td class="edit_comp_r"><select class="comp_input"
								name="Gender" id="Gender" style="width: 50%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>出生日期：</span></td>
							<td class="edit_comp_r"><input id="Birthday" name="Birthday" style="width: 50%;"
								type="text" class="Wdate" onFocus="WdatePicker()" datatype="*"
								sucmsg=" " nullmsg="请填写出生日期" errormsg="出生日期不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>家庭住址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Address" id="Address" style="width: 50%;" type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>户口所在地：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegisterAddress" id="RegisterAddress"
								style="width: 50%;" type="text"  datatype="*"
								sucmsg=" " nullmsg="请填写户口所在地" errormsg="户口所在地不能为空"
								placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>职业：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Vocation" id="Vocation" style="width: 50%;" type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>移动电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Mobile" id="Mobile" style="width: 50%;" type="text" datatype="yd"
								sucmsg=" " errormsg="移动电话为数字" onkeyup="inputInteger(this)" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>固定电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Fix"
								id="Fix" style="width: 50%;" type="text" datatype="yd"
								sucmsg=" " errormsg="固定电话为数字" onkeyup="inputInteger(this)" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>电子邮箱：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Email" id="Email" style="width: 50%;" type="text" datatype="eml"
								sucmsg=" " errormsg="请填写正确格式的电子邮箱，例如 myemail@qq.com" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>说明：</span></td>
							<td class="edit_comp_r">
								<textarea name="Description" altercss="gray" id="perDescription"></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<input type="button" id="perBtn" value="保存" onclick="perBtnClick()" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<a class="header" href="#"
				onclick="javascript:onclickSelectPerAjax('TblFamily');">家庭成员信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:family();">添加家庭成员信息</a>
				<div id="TblFamilydiv"></div>
			</div>
		</div>
		<div class="tagContent none" id="personCredit">
			<a class="header" href="#" 
				onclick="javascript:onclickSelectPerAjax('TblDebt');">个人债务信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:debt();">添加个人债务信息</a>
				<div id="TblDebtdiv"></div>
			</div>
			
			<a class="header" href="#"
				onclick="javascript:onclickSelectPerAjax('TblPersonalwarrant');">个人担保信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:personalwarrant();">添加个人担保信息</a>
				<div id="TblPersonalwarrantdiv"></div>
			</div>
		</div>
	</div>
	<div id='alertDiv' title='信息'>
		<p id='alertTxt'></p>
	</div>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>Validform/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/rootPath.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/decimal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/antiWarrantType.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personApply.js"></script>
<script type="text/javascript" src="<%=basePath%>js/transfer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personinit.js"></script>
<script type="text/javascript" src="<%=basePath%>js/selectPro.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personApp.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personDiv.js"></script>
<script type="text/javascript" src="<%=basePath%>js/antiwarrant.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/alert.js"></script>
</body>
</html>
