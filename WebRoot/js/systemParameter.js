var group = null;
var spdata = null;

$(function() {
	group = getGroupList();
	spdata = getSp();
	showReviewGroup('reviewGroupName');
	showSystemParameter();
	$("#awtdiv").hide();
	
	$("td a").button();
	$("#antiWarrantGroups").resizable({
		handles : "se"
	});
});

function getGroupList() {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetGroupList" + "?rn=" + Math.random(), false);
		xmlhttp.send();
		var data = xmlhttp.responseText;
		var ul = eval("(" + data + ")");
		return ul;
	}
	return null;
}

function showReviewGroup(div) {
	var rg = $("#" + div);
	
	rg.empty();
	for (var i = 0; i < group.length; i++) {
		var option = $("<option>");
		option.attr('value', group[i]);
		option.text(group[i]);
		rg.append(option);
	}
}

function showAntiGroups(div) {
	$("#spBtn").toggle();
	$("#awtdiv").toggle();
	var grouplist = $("#" + div);
	grouplist.empty();
	/*var table = $("<table>");
	grouplist.append(table);
	var col = 1;
	var len = group.length;
	var row = parseInt(len / col);
	var left = len % col;
	for (var rowi = 0; rowi < row; rowi++) {
		var tr = $("<tr>");
		table.append(tr);
		for (var coli = 0; coli < col; coli++) {
			var td = $("<td>");
			tr.append(td);
			var index = rowi * col + coli;
			var gn = group[index];
			var inp = $("<input type='checkbox' name='groupcheckbox'>").attr("value", gn);
			inp.attr("checked", isSelected(gn));
			inp.attr("id", "awg" + index);
			td.append(inp);
			var label = $("<label>").text(gn);
			label.attr("for", "awg" + index);
			td.append(label);
		}
	}
	var lefttr = $("<tr>"); 
	table.append(lefttr);
	for (var lefti = (row * col); lefti < (row * col + left); lefti++) {
		var gn = group[lefti];
		var td = $("<td>");
		lefttr.append(td);
		var inp = $("<input type='checkbox' name='groupcheckbox'>").attr("value", gn);
		inp.attr("checked", isSelected(gn));
		inp.attr("id", "awg" + lefti);
		td.append(inp);
		var label = $("<label>").text(gn);
		label.attr("for", "awg" + lefti);
		td.append(label);
	}
	
	$("input[name='groupcheckbox']").change(function(){
		writeAntiGroups();
	});
	$("#" + div).buttonset();*/
	var ol = $("<ol id='awglist'>");
	grouplist.append(ol);
	for(var i = 0; i < group.length; i++) {
		var gn = group[i];
		var li = $("<li class='ui-state-default'>").text(gn);
		ol.append(li);
	}
	ol.selectable({
		stop : function() {
			var rl = new Array();
			$(".ui-selected", this).each(function() {
				rl.push($(this).text());
			});
			$("#antiWarrantGroups").empty();
			$("#antiWarrantGroups").val(rl);
		}
	});
}

/*function writeAntiGroups() {
	var ag = new Array();
	var agi = 0;
	$("[name='groupcheckbox']").each(function() {
		if ($(this).is(":checked")) {
			ag[agi] = $(this).val();
			agi++;
		}
	});
	$("#antiWarrantGroups").val(ag.join(","));
}

function isSelected(gn) {
	var flag = false;

	var ag = new Array();
	ag = $("#antiWarrantGroups").val().split(",");
	for (var gi = 0; gi < ag.length; gi++) {
		if (gn == ag[gi]) {
			flag = true;
			break;
		}
	}
	return flag;
}*/

function getSp() {
	var sp = null;
	$.ajax({
		url : "GetTblCfgSysparam",
		type : "post",
		dataType : "json",
		data : {
			rn: Math.random()
		},
		async : false,
		success : function(data) {
			sp = data;
		}
	});
	return sp;
}

function showSystemParameter() {
	if (spdata != null) {
		$("#kid").val(spdata.kid);
		$("#passwordReminderDays").val(spdata.passwordReminderDays);
		$("#passwordExpireDays").val(spdata.passwordExpireDays);
		$("#taskReminderDays").val(spdata.taskReminderDays);
		$("#taskExpireDays").val(spdata.taskExpireDays);
		$("#advancedReminderDays").val(spdata.advancedReminderDays);
		$("#projectTrackPeriod").val(spdata.projectTrackPeriod);
		$("#maxLogResults").val(spdata.maxLogResults);
		$("#reviewChargeToReceive").val(spdata.reviewChargeToReceive);
		$("#reviewGroupName").val(spdata.reviewGroupName);
		$("#antiWarrantGroups").val(spdata.antiWarrantGroups);
	}
} 