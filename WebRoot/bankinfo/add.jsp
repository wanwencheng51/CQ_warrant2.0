<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String id = request.getParameter("id");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.css" />

<title>银行信息</title>
</head>

<body>
	<form id="frmbankinfo" method="post">
		<input type="text" id="id" style="display : none" value="<%=id%>" />
		<input type="text" name="remaining" id="remaining" style="display : none" />
		<table>
			<tr>
				<td class="edit_comp_l" style="width: 150px;">
				<span>银行名称：</span></td>
				<td class="edit_comp_r" style="width: 200px"><input class="comp_input"
					name="name" id="name" style="width: 200px;" type="text" />
				</td>
			</tr>
			<tr id="trBid">
				<td class="edit_comp_l" style="width: 150px;">
				<span>银行代码：</span></td>
				<td class="edit_comp_r"><input class="comp_input"
					name="bid" id="bid" style="width: 200px;" type="text" /></td>
			</tr>
			<tr>
				<td class="edit_comp_l" style="width: 150px;">
				<span>授信起始时间：</span></td>
				<td class="edit_comp_r">
					<input style="width: 200px;" id="startdate" class="Wdate" type="text"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'enddate\')||\'2050-10-01\'}'})"
						name="startdate" />
				</td>
			</tr>
			<tr>
				<td class="edit_comp_l" style="width: 150px;">
				<span>授信终止时间：</span></td>
				<td class="edit_comp_r">
					<input style="width: 200px;" id="enddate" class="Wdate" type="text"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startdate\')}',maxDate:'2050-10-01'})"
						name="enddate" />
				</td>
			</tr>
			<tr>
				<td class="edit_comp_l" style="width: 150px;">
				<span>授信额度(万)：</span></td>
				<td class="edit_comp_r"><input class="comp_input"
					name="quota" id="quota" style="width: 200px;" type="text" /></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<button type="button" id="submitBtn">提交</button>
					<button type="button" onclick="window.close();">返回</button>
					<button type="reset">重置</button>
				</td>
			</tr>
		</table>
		注：添加银行时当日时间必须在授信起止时间和授信终止时间之内
	</form>
	<div id='alertDiv' title='信息'>
		<p id='alertTxt'></p>
	</div>

<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/rootPath.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bankinfo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/alert.js"></script>
<script type="text/javascript">
$(function() {
	listBankinfo();
});
</script>
</body>
</html>