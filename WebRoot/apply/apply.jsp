<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
 	String id = request.getParameter("id");
	String wid = request.getParameter("wid").trim();
	String xpid = null;
	if (wid != null) {
		xpid = wid.substring(0, wid.length() - 8);
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/apply.css">
<title>担保申请</title>
</head>

<style type="text/css">
body{background:url(tabs/images/bg.gif) repeat-x 0 5px;}
</style>

<body>
	<div class="apply" style="width: 90%; float: left">
		<form action="ApplyWarrant" method="post" id="applyWarrant"
			class="form_css">
			<input type="text" name="xpid" id="wxpid" value="<%=xpid%>"	style="display: none;" />
			<input type="text" name="taskid" id="wtaskid" value="<%=id%>" style="display: none;" />
			<input type="text" name="wid" id="wwid" value="<%=wid%>" style="display: none;" />
			<fieldset style="margin-top:10px">
				<legend style="color:#CC9900">申请的客户类型</legend>
				<div id="customtype">
					<input type="radio" name="apply" value="company" id="corporateApply" /><label for="corporateApply">企业客户</label>
					<input type="radio" name="apply" value="person" id="personalApply" /><label for="personalApply">个人客户</label>
					<!-- begin: add by Luke on 2015/04/01 -->
					<input type="radio" name="apply" value="personalManageLoan" id="personalManageLoanApply"/><label for="personalManageLoanApply" style="display:none">个人经营贷</label>
					<!-- end: add by Luke on 2015/04/01 -->
				</div>
			</fieldset>
			<br />
			<button type="button" id="but">提交</button>
		</form>
	</div>
	<div id='alertDiv' title='信息'>
		<p id='alertTxt'></p>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/apply.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/alert.js"></script>
</body>
</html>