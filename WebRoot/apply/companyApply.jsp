<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
 	String basePath = request.getScheme() + "://"
 		   + request.getServerName() + ":" + request.getServerPort()
 		   + path + "/";
 	
 	String username = (String) session.getAttribute("user");
	String taskid = null;
	if (request.getAttribute("taskidx") == null) {
		taskid = (String) request.getParameter("id");
	}
	if (request.getParameter("id") == null) {
		taskid = (String) request.getAttribute("taskidx");
	}

	String Wid = (String) request.getParameter("wid");
	String Eid = (String) request.getParameter("eid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>企业担保申请信息</title>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.css" />
<link rel="stylesheet" href="<%=basePath%>Validform/style.css" />
<link rel="stylesheet" href="<%=basePath%>css/apply.css" />
<link rel="stylesheet" href="<%=basePath%>css/companyApply.css" />
</head>

<body>
	<div id="con">
<!-- 		<ul>
			<li><a href="#companyWarrant" class="selectTag">企业担保申请信息</a></li> -->
			<!-- <li><a href="#companyBasic">企业基本信息</a></li> -->
			<!-- <li><a href="#companyFinance">企业财务信息</a></li> -->
			<!-- <li><a href="#companyCredit">企业信用信息</a></li> -->
			<!-- <li><a href="#companyReginfo">企业注册信息</a></li> -->
		<!-- </ul> -->
		<div class="tagContent" id="companyWarrant">
			<h1 align="center" style="font-size: 16px;padding-bottom: 5px;color: #0D7DB9;">企业担保申请</h1>
			<a class="header" href="#" id="">企业基本信息</a>
			<div>
				<form class="registerform" 
					target="hideIframe" method="post" id="cominfo">
					<input type="text" style="display:none" id="cominfo_exist" value="" datatype="cominfoflag" />
					<table width="100%">
	                       <tr style="display: none;">
							<td class="edit_comp_l" style="width: 20%;"><span>taskid：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="taskid" id="taskid" style="width: 50%;" type="text"
								value="<%=taskid%>" /></td>
						</tr>  
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业代码：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Eid"
								id="Eid" style="width: 50%;" maxlength="9"
								datatype="al" nullmsg="请输入9位数字或大写拉丁字母"
								errormsg="企业代码为9位数字或大写拉丁字母" placeholder="必须填写" sucmsg=" "
								type="text" value="<%=Eid%>" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业名称：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="comName" style="width: 50%;" type="text"
								value=""
								datatype="*,s" errormsg="请填写信息！" placeholder="必须填写"
								sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>所属行业：</span></td>
							<td class="edit_comp_r"><select name="Type" id="type_cname" style="width: 50.9%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>办公地址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Address" id="Address" style="width: 50%;" type="text"
								value="" datatype="*"
								errormsg="请填写信息！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业性质：</span></td>
							<td class="edit_comp_r"><select name="Nature" id="nature" style="width: 50.9%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>成立时间：</span></td>
							<td class="edit_comp_r"><input  type="text" style="width: 50.3%;"
								class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								name="FoundDate" id="FoundDate"
								value="" datatype="*"
								errormsg="请填写信息！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>联系人：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Contacts" id="Contacts" style="width: 50%;" type="text"
								value=""  datatype="pe" errormsg="不允许填写特殊字符"  sucmsg=" "  /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>联系电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Phone" id="Phone" style="width: 50%;" type="text"
								value="" maxlength="11" datatype="aa" errormsg="请填写0-9数字！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>传真：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Fax"
								id="Fax" style="width: 50%;" type="text"
								value=""  /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>公司网址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="website" id="website" style="width: 50%;" type="text"
								value="" datatype="url" sucmsg=" " errormsg="请填写正确网址" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>电子邮箱：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Email" id="Email" style="width: 50%;" type="text"
								value="" datatype="e" sucmsg=" " errormsg="请填写正确格式的电子邮箱，例如 myemail@qq.com" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>邮政编码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="PostCode" id="PostCode" style="width: 50%;" type="text"
								value="" maxlength="7" datatype="a8" sucmsg=" " errormsg="请填写数字" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>经营面积(m<sup><small>2</small></sup>)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="ManageArea" id="ManageArea" style="width: 50%;"
								type="text" value=""  datatype="pm" maxlength="20" errormsg="请输入数字" 
								sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>基本户开户银行：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="BasicBankName" id="BasicBankName" style="width: 50%;"
								type="text" value=""
								datatype="*,s" errormsg="不允许输入特殊字符" placeholder="必须填写" sucmsg=" " />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>基本户账号：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="BasicAccount" id="BasicAccount" style="width: 50%;"
								type="text" value=""
								datatype="aa" maxlength="19" errormsg="请输入数字" placeholder="必须填写" sucmsg=" " />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>主营业务：</span></td>
							<td class="edit_comp_r"><textarea id="mainBusiness"
								name="MainBusiness"
								altercss="gray" placeholder="请在这里输入。" sucmsg=" "></textarea></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>资质及证书：</span></td>
							<td class="edit_comp_r"><textarea id="qualification"
								name="Qualification"
								altercss="gray" placeholder="请在这里输入。" sucmsg=" "></textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" id="comBtn" value="保存" /></td>
						</tr>
					</table>
				</form>
			</div>
			<a class="header" href="#" id="reginfo_span">企业担保申请信息</a>
			<div class="edit_comp">
				<iframe name="hideIframe" style="display: none;"></iframe>
				<form method="post" id="frmWarrantinfo" target="hideIframe" class="demoform" >
					<input type="text" style="display:none" id="wi_exist" name="wi_exist" datatype="wiflag" value="" />
					<table width="100%">
						<tr style="display: none;">
							<td class="edit_comp_l" style="width: 20%;"><span>taskid：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="taskid" id="taskid" style="width: 50%;" type="text"
								value="<%=taskid%>" /></td>
						</tr>
						<tr style="display: none;">
							<td class="edit_comp_l" style="width: 20%;"><span>担保项目编码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="wid" id="wid"
								style="width: 50%; background: none; border: none;"
								type="text" value="<%=Wid%>" /></td>
						</tr>

						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>担保项目名称：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="Name" style="width: 50%;" type="text"
								value="" title="请输入担保项目名称"/></td>
						</tr>
						
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目起始日期：</span></td>
							<td><input id="d11" type="text" class="Wdate" style="width: 50%;"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name="StartDate"
								value="" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>申请担保金额(万)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Money" id="Money" style="width: 50%;" type="text"
								value="" datatype="m1" sucmsg=" "
								placeholder="必须填写" title="请输入担保金额，单位为人民币万元"/></td>
						</tr>
						<!-- /^\d+$/ -->
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款银行：</span></td>
							<td><select name="Bank" id="Bank"
								datatype="*,bank" sucmsg=" " style="width: 50%;"></select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款期限(月)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Deadline" id="Deadline" style="width: 50%;"
								type="text" value="" errormsg="贷款期限为大于0的整数"
								datatype="qx" sucmsg=" " placeholder="必须填写"
								title="贷款期限也就是担保期限，单位为月" /></td>
						</tr>

						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>贷款用途：</span></td>
							<td class="edit_comp_r"><textarea name="Usages" id="Usages"
									altercss="gray" tip="" title="请详细描述贷款的详细用途"></textarea>
							</td>
						</tr>

						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目简介：</span></td>
							<td class="edit_comp_r"><textarea name="Description" id="Description"
									altercss="gray" title="请详细介绍项目情况"></textarea>
							</td>
						</tr>

						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>主要还款来源：</span></td>
							<td class="edit_comp_r"><textarea name="PaySource" id="PaySource"
									altercss="gray" title="请详细说明主要的还款来源"></textarea>
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>项目还款计划：</span></td>
							<td class="edit_comp_r"><textarea name="PayPlan" id="PayPlan"
									altercss="gray" title="请提供详细的还款计划"></textarea>
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="warrantBtn" id="warrantBtn" value="保存"></td>
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
				<form action="ApplyPass" method="post" id="applyPass_Form">
					<input style="display: none;" type="text" id="num" value="companyApply" />
					<input style="display: none;" type="text" id="username" value="<%=username%>" />
					<table>
						<tr style="display: none;">
							<td><span>担保项目编码：</span></td>
							<td><input class="comp_input"
								name="wid" id="wid"
								style=" background: none; border: none;"
								type="text" value="<%=Wid%>" readonly /></td>
						</tr>
						<tr style="display: none;">
							<td><span>企业代码：</span></td>
							<td><input class="comp_input"
								name="eid" id="zEid"
								style="background: none; border: none;"
								type="text" value="" /></td>
						</tr>
						<tr style="display: none;">
							<td><input class="comp_input"
								name="type" id="type"
								style="background: none; border: none;"
								type="text" value="0" readonly /></td>
						</tr>
						<tr style="display: none;">
							<td><input class="comp_input"
								name="name" id="name"
								style="background: none; border: none;"
								type="text" value="<%=username%>" readonly /></td>
						</tr>
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
							<td class="edit_comp_r"><input type="text" name="taskid"
								value="<%=taskid%>" style="display: none;" /></td>
						</tr>
						<tr>
							<td><button
								type="button" onclick="applyPassTj()">提交</button></td>
						</tr>
					</table>
				</form>
			</fieldset>
		</div>
		<div class="tagContent none" id="companyBasic">
			<a class="header" href="#">企业基本信息</a>
			<div>
				<form class="registerform" 
					target="hideIframe" method="post" id="cominfo">
					<input type="text" style="display:none" id="cominfo_exist" value="" datatype="cominfoflag" />
					<table width="100%">
	                       <tr style="display: none;">
							<td class="edit_comp_l" style="width: 20%;"><span>taskid：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="taskid" id="taskid" style="width: 50%;" type="text"
								value="<%=taskid%>" /></td>
						</tr>  
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业代码：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Eid"
								id="Eid" style="width: 50%;" maxlength="9"
								datatype="al" nullmsg="请输入9位数字或大写拉丁字母"
								errormsg="企业代码为9位数字或大写拉丁字母" placeholder="必须填写" sucmsg=" "
								type="text" value="<%=Eid%>" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业名称：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Name" id="comName" style="width: 50%;" type="text"
								value=""
								datatype="*,s" errormsg="请填写信息！" placeholder="必须填写"
								sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>所属行业：</span></td>
							<td class="edit_comp_r"><select name="Type" id="type_cname" style="width: 50%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>办公地址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Address" id="Address" style="width: 50%;" type="text"
								value="" datatype="*"
								errormsg="请填写信息！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>企业性质：</span></td>
							<td class="edit_comp_r"><select name="Nature" id="nature" style="width: 50%;">
							</select></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>成立时间：</span></td>
							<td class="edit_comp_r"><input  type="text" style="width: 50%;"
								class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								name="FoundDate" id="FoundDate"
								value="" datatype="*"
								errormsg="请填写信息！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>联系人：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Contacts" id="Contacts" style="width: 50%;" type="text"
								value=""  datatype="pe" errormsg="不允许填写特殊字符"  sucmsg=" "  /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>联系电话：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Phone" id="Phone" style="width: 50%;" type="text"
								value="" maxlength="11" datatype="aa" errormsg="请填写0-9数字！" placeholder="必须填写" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>传真：</span></td>
							<td class="edit_comp_r"><input class="comp_input" name="Fax"
								id="Fax" style="width: 50%;" type="text"
								value=""  /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>公司网址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="website" id="website" style="width: 50%;" type="text"
								value="" datatype="url" sucmsg=" " errormsg="请填写正确网址" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>电子邮箱：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Email" id="Email" style="width: 50%;" type="text"
								value="" datatype="e" sucmsg=" " errormsg="请填写正确格式的电子邮箱，例如 myemail@qq.com" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>邮政编码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="PostCode" id="PostCode" style="width: 50%;" type="text"
								value="" maxlength="7" datatype="a8" sucmsg=" " errormsg="请填写数字" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>经营面积(m<sup><small>2</small></sup>)：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="ManageArea" id="ManageArea" style="width: 50%;"
								type="text" value=""  datatype="pm" maxlength="20" errormsg="请输入数字" 
								sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>基本户开户银行：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="BasicBankName" id="BasicBankName" style="width: 50%;"
								type="text" value=""
								datatype="*,s" errormsg="不允许输入特殊字符" placeholder="必须填写" sucmsg=" " />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>基本户账号：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="BasicAccount" id="BasicAccount" style="width: 50%;"
								type="text" value=""
								datatype="aa" maxlength="19" errormsg="请输入数字" placeholder="必须填写" sucmsg=" " />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>主营业务：</span></td>
							<td class="edit_comp_r"><textarea id="mainBusiness"
								name="MainBusiness"
								altercss="gray" placeholder="请在这里输入。" sucmsg=" "></textarea></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>资质及证书：</span></td>
							<td class="edit_comp_r"><textarea id="qualification"
								name="Qualification"
								altercss="gray" placeholder="请在这里输入。" sucmsg=" "></textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" id="comBtn" value="保存" /></td>
						</tr>
					</table>
				</form>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblShareholder');">企业主要股东信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:stock();">添加股东信息</a>
				<div id="TblShareholderdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblManinfo');">企业管理人员信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:manager();">添加企业管理人员信息</a>
				<div id="TblManinfodiv"></div>
			</div>

			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblDeptinfo');">企业内部部门信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:inEnterprise();">添加企业内部部门信息</a>
				<div id="TblDeptinfodiv"></div>
			</div>

			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblBranchinfo');">企业分支机构</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:branchinfo();">添加企业分支机构</a>
				<div id="TblBranchinfodiv"></div>
			</div>
			
			<a class="header" href="#" id="qiye_span">企业人员配备</a>
			<div>
				<form method="post" target="hideIframe" class="tablEmpinfo_form" id="frmEmpinfo">
					<input type="text" style="display:none" id="empinfo_exist" value="" />
					<table width="100%">
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
							</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="eid" id="eid_emp" style="width: 50%;" type="hidden"
								value="" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>研究生及以上人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="MasterOrUp " id="masterOrUpE" style="width: 50%;"
								type="text" onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								大学本科人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Bachelor " id="bachelorE" style="width: 50%;"
								type="text" onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								大/中专人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="BachelorBelow " id="bachelorBelowE" style="width: 50%;"
								type="text"	onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								高级职称人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Senior " id="seniorE" style="width: 50%;" type="text"
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								中级职称人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Middle" id="middleE" style="width: 50%;" type="text"
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								初级职称人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Junior" id="juniorE" style="width: 50%;" type="text"
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								经营管理人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Manager" id="managerE" style="width: 50%;" type="text"
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								生产销售人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="ProductAndSale" id="productAndSaleE"
								style="width: 50%;" type="text"
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>
								员工总人数：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="Total" id="totalE" style="width: 50%;" type="text"
								placeholder="必须填写" datatype="n" errormsg="员工总人数必须为正整数"
								sucmsg=" "
								onkeyup="inputInteger(this);" />
							</td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" id="submit" value="" /></td>
						</tr>
					</table>
				</form>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblHistory');">企业重大历史事件</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:zdls();">添加企业重大历史事件</a>
				<div id="TblHistorydiv"></div>
			</div>

			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblProduct');">企业主要产品</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:zycp();">添加企业主要产品</a>
				<div id="TblProductdiv"></div>
			</div>

			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblVendor');">企业主要供货商</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:vendor();">添加企业主要供货商</a>
				<div id="TblVendordiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblRelatedcominfo');">关联企业信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:relatedcominfo();">添加关联企业信息</a>
				<div id="TblRelatedcominfodiv"></div>
			</div>
		</div>
		<div class="tagContent none" id="companyFinance">
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblBanlancesheet');">企业资产负债表</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:banlancesheet();">添加企业资产负债表信息</a>
				<div id="TblBanlancesheetdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblIncomestatement');">企业损益表信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:incomestatement();">添加企业损益表信息</a>
				<div id="TblIncomestatementdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblCashflowstatement');">企业现金流量表信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:cashflowstatement();">添加企业现金流量信息</a>
				<div id="TblCashflowstatementdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblSalerevenuecheck');">销售收入核查信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:salerevenuecheck();">添加销售收入核查信息</a>
				<div id="TblSalerevenuecheckdiv"></div>
			</div>
		</div>
		<div class="tagContent none" id="companyCredit">
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblUndueloan');">企业借款信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:undueloan();">添加企业借款信息</a>
				<div id="TblUndueloandiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblUnduewarrant');">企业未到期对外担保信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:unduewarrant();">添加企业对外担保信息</a>
				<div id="TblUnduewarrantdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblUnduebill');">企业未结清票据</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:unduebill();">添加企业未结清票据信息</a>
				<div id="TblUnduebilldiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblInvest');">企业对外投资</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:invest();">添加企业对外投资信息</a>
				<div id="TblInvestdiv"></div>
			</div>
		</div>
		<div class="tagContent none" id="companyReginfo">
			<a class="header" href="#" onclick="javascript:addRegInfo();">企业注册信息</a>
			<div>
				<form class="registerform_infor" method="post" target="hideIframe" id="reginfo">
					<table width="100%">
						<tr>
							<td class="edit_comp_l" style="width: 20%;"></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="eid" id="eid_reginfo" style="width: 50%;" type="hidden"
								value="" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册代码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="rid" id="rid" style="width: 50%;" maxlength="18" type="text" datatype="*" errormsg="请填写注册代码" 
								sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册名称：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegName" id="RegName" style="width: 50%;" type="text" datatype="s" errormsg="不允许输入特殊字符"
								sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册地址：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegAddress" id="RegAddress" style="width: 50%;"   datatype="s" errormsg="不允许输入特殊字符"
								sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册登记机关：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegDept" id="RegDept" style="width: 50%;" type="text" datatype="s" errormsg="不允许输入特殊字符"
								sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册时间：</span></td>
							<td class="edit_comp_r"><input id="RegDate" type="text" style="width: 50%;"
								class="Wdate" onfocus="WdatePicker()" name="regDate" datatype="*" errormsg="不允许输入特殊字符"
								sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>注册资本：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="RegMoney" id="RegMoney" style="width: 50%;"
								type="text" datatype="ad" errormsg="小数后保留两位有效数字" sucmsg=" " placeholder="必须填写" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>国税登记号码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="StateTaxNum" id="StateTaxNum" style="width: 50%;" type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>地税登记号码：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="LocalTaxNum " id="LocalTaxNum" style="width: 50%;"
								type="text" /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>经营期限：</span></td>
							<td class="edit_comp_r"><input class="comp_input"
								name="TimeLimit" id="TimeLimit" style="width: 50%;"	type="text"
								datatype="regqx" errormsg="经营期限为大于0的整数" sucmsg=" " /></td>
						</tr>
						<tr>
							<td class="edit_comp_l" style="width: 20%;"><span>经营范围：</span></td>
							<td class="edit_comp_r"><textarea name="Scope"
								id="Scope" altercss="gray" placeholder="请在这里输入。" sucmsg=" "></textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" id="regsubmit" value="" /></td>
						</tr>
					</table>
				</form>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblRegshareholder');">企业注册股东信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:regshareholder();">添加企业注册股东信息</a>
				<div id="TblRegshareholderdiv"></div>
			</div>
			
			<a class="header" href="#" 
				onclick="javascript:onclickSelectAjax('TblChangeinfo');">企业注册变更信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:changeinfo();">添加企业注册变更信息</a>
				<div id="TblChangeinfodiv"></div>
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
<script type="text/javascript" src="<%=basePath%>js/public/tooltip.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/decimal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/antiWarrantType.js"></script>
<script type="text/javascript" src="<%=basePath%>js/transfer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/companyApply.js"></script>
<script type="text/javascript" src="<%=basePath%>js/companyApplyDiv.js"></script>
<script type="text/javascript" src="<%=basePath%>js/companyApplyValid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/company.js"></script>
<script type="text/javascript" src="<%=basePath%>js/selectPro.js"></script>
<script type="text/javascript" src="<%=basePath%>js/warrantinfo.js"></script>
<script type="text/javascript" src="<%=basePath%>js/companyinit.js"></script>
<script type="text/javascript" src="<%=basePath%>js/antiwarrant.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/alert.js"></script>
</body>
</html>
