function submitme() {
	document.getElementById("frmupload").submit();
	document.getElementById("projectdiv").style.display = "block";
	setTimeout("showFileList('projectdiv')", 500);
}

function myAddProblem(wid, taskname, start) {
	if (start == "1") {
		alert("此项目已通过项目决策，不能再添加问题");
		return;
	}
	addProblem(wid, taskname);
}

var wid = "";
var serviceType = "";
$(function() {
	wid = $("#wid").val();
	serviceType = getServiceType(wid);

	$("#serviceType").val(serviceType);
	getUserRight();
	addFdbyn();
	if (serviceType == "1") {
		$("#zsc").hide();
		$("#fyj").hide();
		$("#cyj").hide();
	}
	
	$("#con").tabs();
	
	$("#basicInfo, #projectInfo").accordion({
		heightStyle: "content",
		collapsible: true,
		active: 100,
		header: "a.header",
	});
	$("#returnBack, #add a").button();
	
	setTimeout(getProjectHistory(), 4000);
});

function getProjectHistory() {
	var phl = null;

	$.ajax({
		url : "GetProjectHistory",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			wid : wid,
			rn : Math.random()
		},
		success : function(data) {
			if (data != null) {
				phl = data.projectHistory;
			}
		}
	});

	var phdiv = $("#phldiv");
	phdiv.empty();
	for (var pi = 0; pi < phl.length; pi++) {
		var p = $("<p>");
		p.text(phl[pi].description);
		phdiv.append(p);
		phdiv.append($("<br />"));
	}
}

function addFdbyn() {
	var userRight = getUserRight();

	if (userRight.antiWarrant == 1) {
		document.getElementById("add").style.display = "block";
	} else {
		document.getElementById("add").style.display = "none";
	}
}

var pdl = null;
function showDocList(id, docList) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (pdl == null) {
		pdl = getProjectDocList(wid);
		if (pdl == null) {
			return null;
		}
	}
	var str = "";
	var size = pdl.length;
	if (size == 0) {
		str += "没有相关信息";
		document.getElementById(docList).innerHTML = str;
		return;
	}
	str += "";
	str += "<table>";
	str += "<tr align='center'>";
	str += "<th width='40%'>资料名称</th>";
	str += "<th width='40%'>资料描述</th>";
	str += "<th width='10%'>是否完整</th>";
	str += "</tr>";
	
	var intact = null;
	for (var i = 0; i < size; i++) {
		intact = pdl[i];
		str += "<tr>";
		str += "<td align='center'>" + intact.name + "</td>";
		str += "<td align='center'>" + intact.description + "</td>";
		if (intact.complete == "1") {
			str += "<td align='center'><input name='' type='checkbox' value=''"
				+ "checked disabled='disabled'></td>";
		} else {
			str += "<td align='center'><input name='' type='checkbox' value=''"
				+ "disabled='disabled'></td>";
		}
		str += "</tr>";
	}
	str += "</table>";
	document.getElementById(docList).innerHTML = str;
	return;
}

var rdl = null;
function showRDI(id, rdInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (rdl == null) {
		rdl = getReviewDataInfo(wid);
	}
	showReviewDataInfo(rdl, rdInfo);
}

var fsl = null;
function showFSI(id, fsInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (fsl == null) {
		fsl = getFieldSurveyInfo(wid);
	}
	showFieldSurveyInfo(fsl, fsInfo, serviceType);
}

var mal = null;
function showMAI (id, maInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (mal == null) {
		mal = getManauditInfo(wid);
	}
	showManauditInfo(mal, maInfo);
}

var lal = null;
function showLAI(id, laInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (lal == null) {
		lal = getLawauditInfo(wid);
	}
	showLawauditInfo(lal, laInfo);
}

var fel = null;
function showFEI(id, feInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (fel == null) {
		fel = getFinanceestInfo(wid);
	}
	showFinanceestInfo(fel, feInfo);
}

var rel = null;
function showREI(id, reInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}

	if (serviceType == "0") {
		if (rel == null) {
			rel = getCompanyRiskestInfo(wid);
		}
		showCompanyRiskestInfo(rel, reInfo);
	} else {
		if (rel == null) {
			rel = getPersonRiskestInfo(wid);
		}
		showPersonRiskestInfo(rel, reInfo);
	}
}

var rrl = null;
function showRRI (id, rrInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (rrl == null) {
		rrl = getRiskReviewInfo(wid);
		if (rrl == null) {
			return null;
		}
	}
	var str = "";
	var size = rrl.length;
	if (size == 0) {
		return;
	}
	str += "";
	str += "<table width='100%'>";
	
	str += "<tr>";
	str += "<th width='15%'>审核人姓名</th>";
	for (var i = 0; i < size; i++) {
		var ss = rrl[i];
		str += "<td>" + ss.name + "</td>";
	}
	str += "</tr>";
	
	str += "<tr>";
	str += "<th>审核时间</th>";
	for (var i = 0; i < size; i++) {
		var ss = rrl[i];
		str += "<td>" + ss.date + "</td>";
	}
	str += "</tr>";
	
	str += "<tr>";
	str += "<th>审核意见</th>";
	for (var i = 0; i < size; i++) {
		var ss = rrl[i];
		str += "<td>" + ss.checks + "</td>";
	}
	str += "</tr>";
	
	str += "</table>";
	document.getElementById(rrInfo).innerHTML = str;
	return;
}

function getCounterSignInfo(wid) {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetCounterSignInfo" + "?rn=" + Math.random() + "&wid="
				+ wid, false);
		xmlhttp.send();
		var data = xmlhttp.responseText;
		var csl = eval("(" + data + ")");
		if (csl == null) {
			return null;
		}
		return csl.counterSignInfo;
	}
	return null;
}

var csl = null;
function showCounterSignInfo(id, csInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (csl == null) {
		csl = getCounterSignInfo(wid);
	}
	if (csl == null || csl.length == 0) {
		return;
	}
	
	var td = new Array();
	
	td.push('<table width="100%">');
	
	td.push('<tr>');
	td.push('<th width="15%">会签人名称</th>');
	for (var csn = 0; csn < csl.length; csn++) {
		td.push('<td>' + csl[csn].name + '</td>');
	}
	td.push('</tr>');
	
	td.push('<tr>');
	td.push('<th>会签日期</th>');
	for (var csn = 0; csn < csl.length; csn++) {
		td.push('<td>' + csl[csn].date + '</td>');
	}
	td.push('</tr>');
	
	td.push('<tr>');
	td.push('<th>会签结论</th>');
	for (var csn = 0; csn < csl.length; csn++) {
		if (csl[csn].result == "1") {
			td.push('<td>不同意</td>');
		} else {
			td.push('<td>同意</td>');
		}
	}
	td.push('</tr>');
	
	td.push('<tr>');
	td.push('<th>情况说明</th>');
	for (var csn = 0; csn < csl.length; csn++) {
		td.push('<td>' + csl[csn].description + '</td>');
	}
	td.push('</tr>');
	td.push('</table>');
	
	document.getElementById(csInfo).innerHTML = td.join('');
	td = null;
}

function getDecisionResultInfo(wid) {
	var dri = null;
	
	$.ajax({
		url : "GetDecisionResultInfo",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			wid : wid,
			rn : Math.random()
		},
		success : function(data) {
			if (data != null) {
				dri = data.decisionResultInfo;
			}
		}
	});
	return dri;
}

var dri = null;
function showDecisionResultinfo(id, drInfo) {
	var ret = pullDown(id);
	if (ret == false) {
		return;
	}
	if (dri == null) {
		dri = getDecisionResultInfo(wid);
	}
	if (dri == null || dri.length == 0) {
		return;
	}
	
	var td = new Array();
	
	td.push('<table width="100%">');
	
	td.push('<tr>');
	td.push('<th width="15%">决策日期</th>');
	for (var csn = 0; csn < dri.length; csn++) {
		td.push('<td>' + dri[csn].date + '</td>');
	}
	td.push('</tr>');
	
	td.push('<tr>');
	td.push('<th>决策结果</th>');
	for (var csn = 0; csn < dri.length; csn++) {
		var result = "";
		switch (dri[csn].result) {
		case "0":
			result = "同意";
			break;
		case "1":
			result = "不同意，项目终止，不退费";
			break;
		case "2":
			result = "不同意，项目终止，退评审费";
			break;
		case "3":
			result = "重新收集信息";
			break;
		default:
			result = "";
			break;
		}
		td.push('<td>' + result + '</td>');
	}
	td.push('</tr>');
	
	td.push('<tr>');
	td.push('<th>情况说明</th>');
	for (var csn = 0; csn < dri.length; csn++) {
		td.push('<td>' + dri[csn].explains + '</td>');
	}
	td.push('</tr>');
	td.push('</table>');
	
	document.getElementById(drInfo).innerHTML = td.join('');
	td = null;
}




