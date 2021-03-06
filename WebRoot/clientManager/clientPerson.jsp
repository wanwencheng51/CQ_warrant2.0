<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	String pid = request.getParameter("getWid");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.css" />
<link rel="stylesheet" href="<%=basePath%>Validform/style.css" />
<link rel="stylesheet" href="<%=basePath%>css/apply.css" />
<link rel="stylesheet" href="<%=basePath%>css/personApply.css" />
<title>个人客户详细信息</title>
</head>

<body>
<iframe name="hideIframe" style="display: none;"></iframe>
	<input id="PerID" value="<%=pid%>" style="display: none;">
	<div id="con">
		<ul>
			<li><a class="selectTag" href="#personBasic">个人基本信息</a></li>
			<li><a href="#personCredit">个人信用信息</a></li>
		</ul>
		<div id="personBasic">
			<a class="header" href="#">个人基本信息</a>
			<div id="personDiv"></div>
			
			<a class="header" href="#"
				onclick="javascript:onclickSelectPerAjax('TblFamily');">家庭成员信息</a>
			<div>
				<a class="addbutton" href="#" onclick="javascript:family();">添加家庭成员信息</a>
				<div id="TblFamilydiv"></div>
			</div>
		</div>
		<div id="personCredit">
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
<script type="text/javascript" src="<%=basePath%>jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="<%=basePath%>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>Validform/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/rootPath.js"></script>
<script type="text/javascript" src="<%=basePath%>js/public/initXMLHTTP.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personApply.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personApp.js"></script>
<script type="text/javascript" src="<%=basePath%>js/personDiv.js"></script>
<script type="text/javascript" src="<%=basePath%>js/client.js"></script>
<script type="text/javascript">
$(function() {
	showClientPerson("<%=pid%>");
	$("#con").tabs();
	$("#personBasic").accordion({
		heightStyle: "content",
		collapsible: true,
		active: 0,
		header: "a.header",
	});
	$("#personCredit").accordion({
		heightStyle: "content",
		collapsible: true,
		active: 100,
		header: "a.header",
	});
	$("a.addbutton").button();
});
</script>
</body>
</html>