<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
 	String basePath = request.getScheme() + "://"
 		   + request.getServerName() + ":" + request.getServerPort()
 		   + path + "/";
 	
 	String eid = request.getParameter("eid");
	String kid = request.getParameter("kid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/apply.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>Validform/style.css" />
<title>企业对外投资信息</title>
</head>

<body>
	<div class="center_content_head">
		<span class="cchhover"><font class="head_fcl"></font>企业对外投资</span>
	</div>
	<div class="center_content_top"></div>
	<div class="edit_wrap">
		<div class="edit_comp">
			<form method="post" class="qydw_form" id="inver">
				<input id="kid"	type="text" value="<%=kid%>" style="display: none;" />
				<input name="eid" id="eid" type="text" value="<%=eid%>" style="display: none;" />
				<table>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>投资项目名称：</span></td>
						<td class="edit_comp_r"><input class="comp_input"
							name="Name" id="Name" style="width: 200px;" type="text"
							datatype="s,inve" errormsg="不允许输入特殊字符" placeholder="必须填写" sucmsg=" " /></td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>投资起始日期：</span></td>
						<td class="edit_comp_r"><input id="d4311" name="StartDate" style="width: 200px;"
							class="Wdate" type="text"
							onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2050-10-01\'}'})"
							datatype="a2" errormsg="请填写信息！" placeholder="必须填写" sucmsg=" " />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>投资终止日期：</span></td>
						<td class="edit_comp_r"><input id="d4312" class="Wdate" style="width: 200px;"
							name="EndDate" type="text"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2050-10-01'})"
							datatype="a2" errormsg="投资起始日期不能大于投资终止日期" placeholder="必须填写" sucmsg=" " />
						</td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>投资金额：</span></td>
						<td class="edit_comp_r"><input class="comp_input"
							name="Money" id="Money" style="width: 200px;" type="text"
							datatype="a3" nullmsg="请填写信息！" errormsg="请填写数字！ "
							placeholder="必须填写" sucmsg=" " /></td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>投资方式：</span></td>
						<td class="edit_comp_r"><select name="InvestMode" id="mode" style="width: 205px;">
						</select></td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>上年投资收益：</span></td>
						<td class="edit_comp_r"><input class="comp_input"
							name="LastYearIncome" id="LastYearIncome" style="width: 200px;"
							type="text" datatype="a3"  errormsg="请填写数字！ "
							placeholder="必须填写" sucmsg=" " /></td>
					</tr>
					<tr>
						<td class="edit_comp_l" style="width: 260px;"><span>当年收益预测：</span></td>
						<td class="edit_comp_r"><input class="comp_input"
							name="ForecastIncome" id="ForecastIncome" style="width: 200px;"
							type="text" datatype="a3" nullmsg="请填写信息！" errormsg="请填写数字！ "
							placeholder="必须填写" sucmsg=" " /></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" value="提交" />
							<input type="button" onclick="javascript:window.close()" value="返回" />
							<input type="reset" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>Validform/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/rootPath.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/investMode.js"></script>
<script type="text/javascript" src="<%=basePath%>js/invest.js"></script>
</body>
</html>