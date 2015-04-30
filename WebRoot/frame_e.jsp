<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.css">
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	list-style-type: none;
}
ul li a {
	text-decoration: none;
}
</style>
<title></title>
<%
	String user = (String) session.getAttribute("user");
	if (user == null || user.trim().length() == 0) {
		out.println("用户登录已经过期，请重新登录");
		return;
	}
%>
</head>
<body>
	<form action="RunApply" id="runapply" target="c">
	</form>
	<div style="padding: 8px 0 0;">
		<%
		if (!(user.equals("root"))) {
		%>
			<ul id="menuE">
				<li onclick="javascript:document.getElementById('warrantServiceAcceptance').click();"><a id="warrantServiceAcceptance" onclick="pro()" target="c" style="cursor:pointer;">担保项目受理</a></li>
				<li></li>
				<li onclick="javascript:document.getElementById('customerManage').click();"><a id="customerManage" href="${pageContext.request.contextPath}/clientManager/clientManager.jsp" target="c">客户管理</a></li>
				<li></li>
				<li onclick="javascript:document.getElementById('projectManage').click();"><a id="projectManage" href="${pageContext.request.contextPath}/project/project.jsp" target="c">项目管理</a></li>
				<li></li>
				<li onclick="javascript:document.getElementById('documentManage').click();"><a id="documentManage" href="${pageContext.request.contextPath}/document/DownloadFile.jsp" target="c">文档管理</a></li>
			</ul>
		<%
		}
		%>
	</div>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
$(function() {
	$("#menuE").menu();
});

function pro() {
	document.getElementById("runapply").submit();
}

function clickLi(e)
{
    //兼容ie 火狐 chrome
    e = e || window.event;
    e.target = e.target || e.srcElement;
    alert(e.target.nodeName);
}
</script>
</body>
</html>