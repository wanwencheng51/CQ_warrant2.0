<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/apply.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/project.css" />
<title>担保项目管理</title>
</head>

<body>
	<table style="margin-left: 10px">
		<tr>
			<td><ul id="ptmenu">
				<li>
					<a id="apt" href="#">所有项目</a>
					<ul>
						<li onclick="javascript:document.getElementById('all').click();"><a id="all" href="#" class="ptclass">所有</a></li>
						<li></li>
						<li onclick="javascript:document.getElementById('company').click();"><a id="company" href="#" class="ptclass">企业</a></li>
						<li></li>
						<li onclick="javascript:document.getElementById('person').click();"><a id="person" href="#" class="ptclass">个人</a></li>
					</ul>
				</li>
			</ul></td>
			<td><input type="hidden" id="projectType" value="0"/></td>
			<td><label for="projectName">项目名称：</label></td>
			<td><input type="text" id="projectName" /></td>
			<td><input type="button" value="查询" onclick="projectdivclose()" /></td>
		</tr>
	</table>
	<div id="projectDiv"></div>
	
	<div id="deleteProjectDiv">
		<p id="delRetInfo">该操作将删除项目相关流程，并且无法恢复，请确认是否执行？</p>
	</div>
	<div id="endProjectDiv">
		<p id="endRetInfo">该操作将强行终止项目相关流程，并且无法恢复，请确认是否执行？</p>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/rootPath.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/initXMLHTTP.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/project.js"></script>
</body>
</html>