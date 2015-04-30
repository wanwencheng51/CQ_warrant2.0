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
</head>

<body>
	<div style="padding: 8px 0 0;">
		<form action="RunApply" id="runapply" target="c">
			<ul id="menuF">
				<li onclick="javascript:document.getElementById('businessInfo').click();"><a id="businessInfo" href="${pageContext.request.contextPath}/loan/loan.jsp" target="c">业务信息</a></li>
			</ul>
		</form>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-ui-1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
$(function() {
	$("#menuF").menu();
});
</script>
</body>
</html>