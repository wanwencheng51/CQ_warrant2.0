$(function() {
	companyInit();
	$("#con").tabs();
	$("#companyWarrant, #companyBasic").accordion({
		heightStyle: "content",
		collapsible: true,
		active: 0,
		header: "a.header",
	});
	$("#companyFinance, #companyCredit, #companyReginfo").accordion({
		heightStyle: "content",
		collapsible: true,
		active: 100,
		header: "a.header",
	});
	$("a.addbutton").button();
});

function companyInit() {
	var taskid = document.getElementById("taskid").value;
	var wid = document.getElementById("wid").value;
	getCompanyWarrantInfo(wid);
	getBankName();
	var eid = getTaskVar(taskid, "Eid");
	if(eid != "null" && eid != null && eid !="") {
		getCompanyInfo(eid);
	} else {
		document.getElementById("Eid").value="";
	}
	getCompanyType();
	getCompanyProperty();
}

var oldbankopt = null;
function getCompanyWarrantInfo(wid) {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetTblWarrantinfo" + "?rn=" + Math.random() + "&wid="
				+ wid, false);
		xmlhttp.send();
		// alert(xmlhttp.responseText);
		var wi = eval('(' + xmlhttp.responseText + ')');
		if (wi != null) {
			var descript = wi.description;
			var description = descript.replace(/<br>/g, "\n");
			var Usges = wi.usages;
			var usage = Usges.replace(/<br>/g, "\n");
			var PaySource = wi.paySource;
			var paySource = PaySource.replace(/<br>/g, "\n");
			var PayPlan = wi.payPlan;
			var payPlan = PayPlan.replace(/<br>/g, "\n");
			document.getElementById("Name").value = wi.name;
			document.getElementById("d11").value = wi.startDate;
			document.getElementById("Money").value = wi.practicalMoney;
			document.getElementById("Deadline").value = wi.deadline;
			document.getElementById("Usages").value = usage;
			document.getElementById("Description").value = description;
			document.getElementById("PaySource").value = paySource;
			document.getElementById("PayPlan").value = payPlan;
			var bank = document.getElementById("Bank");
			oldbankopt = document.createElement('option');
			oldbankopt.setAttribute('value', wi.bank);
			oldbankopt.innerHTML = "";
			bank.appendChild(oldbankopt);
			document.getElementById("warrantBtn").value = "修改";
			document.getElementById("wi_exist").value = "1";
		} else {
			document.getElementById("wi_exist").value = "0";
		}
	}
}

function getBankName() {
	var banks = getActiveBankInfo();
	if (null == banks || "" == banks) {
		return;
	}
	
	var bs = document.getElementById("Bank");
	var optDefault = document.createElement('option');
	optDefault.setAttribute('value', '');
	optDefault.innerHTML = '';
	bs.appendChild(optDefault);
	
	for (var i = 0; i < banks.length; i++) {
		var opt = document.createElement('option');
		if((oldbankopt != null) && (banks[i].bid == oldbankopt.value)) {
			oldbankopt.innerHTML = banks[i].name;
			continue;
		}
		opt.setAttribute('value', banks[i].bid);
		opt.innerHTML = banks[i].name;
		bs.appendChild(opt);
	}
}

function getActiveBankInfo() {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetBankName"
				+ "?rn=" + Math.random()
				+ "&active=1", false);
		xmlhttp.send();
		var banks = eval("(" + xmlhttp.responseText + ")");
		if (banks == null) {
			return null;
		} else {
			return banks.banks;
		}
	} else {
		return null;
	}
}

var oldctopt = null;
var oldcpopt = null;
function getCompanyInfo(eid) {
	var xmlhttp = initxmlhttp();

	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetComInfo" + "?rn=" + Math.random() + "&eid="
				+ eid, false);
		xmlhttp.send();
		var pi = eval("(" + xmlhttp.responseText +")");
		if (pi != null) {
			document.getElementById("Eid").value = pi.eid;
			document.getElementById("comName").value = pi.name;
			document.getElementById("FoundDate").value = pi.foundDate;
			var companytype = document.getElementById("type_cname");
			oldctopt = document.createElement('option');
			oldctopt.setAttribute('value', pi.type);
			oldctopt.innerHTML = "";
			companytype.appendChild(oldctopt);
			document.getElementById("Address").value = pi.address;
			var companyproperty = document.getElementById("nature");
			oldcpopt = document.createElement('option');
			oldcpopt.setAttribute('value', pi.nature);
			oldcpopt.innerHTML = "";
			companyproperty.appendChild(oldcpopt);
			document.getElementById("Contacts").value = pi.contacts;
			document.getElementById("Phone").value = pi.phone;
			document.getElementById("Fax").value = pi.fax;
			document.getElementById("website").value = pi.website;
			document.getElementById("Email").value = pi.email;
			document.getElementById("PostCode").value = pi.postCode;
			document.getElementById("ManageArea").value = pi.manageArea;
			document.getElementById("BasicBankName").value = pi.basicBankName;
			document.getElementById("BasicAccount").value = pi.basicAccount;
			document.getElementById("mainBusiness").value = pi.mainBusiness.replace(/<br>/g, "\n");
			document.getElementById("qualification").value = pi.qualification.replace(/<br>/g, "\n");
			document.getElementById("comBtn").value = "修改";
			document.getElementById("cominfo_exist").value = "1";
		} else {
			document.getElementById("cominfo_exist").value = "0";
			document.getElementById("comName").value = "";
			document.getElementById("FoundDate").value = "";
			document.getElementById("type_cname").value = "";
			document.getElementById("Address").value = "";
			document.getElementById("nature").value = "";
			document.getElementById("Contacts").value = "";
			document.getElementById("Phone").value = "";
			document.getElementById("Fax").value = "";
			document.getElementById("website").value = "";
			document.getElementById("Email").value = "";
			document.getElementById("PostCode").value = "";
			document.getElementById("ManageArea").value = "";
			document.getElementById("BasicBankName").value = "";
			document.getElementById("BasicAccount").value = "";
			document.getElementById("mainBusiness").value = "";
			document.getElementById("qualification").value = "";
			document.getElementById("comBtn").value = "保存";
		}
	}
}

function getCompanyType() {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetCompanyType" + "?rn=" + Math.random(), false);
		xmlhttp.send();
		var data = xmlhttp.responseText;
		var wi = eval("("+data+")");
		var ctList = wi.ctList;
		var companyType = document.getElementById("type_cname");
		for (var i = 0; i < ctList.length; i++) {
			var opt = document.createElement('option');
			if((oldctopt != null) && (ctList[i].kid == oldctopt.value)) {
				oldctopt.innerHTML = ctList[i].cname;
				continue;
			}
			opt.setAttribute('value', ctList[i].kid);
			opt.innerHTML = ctList[i].cname;
			companyType.appendChild(opt);
		}
	}
}

function getCompanyProperty() {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetCompanyProperty" + "?rn=" + Math.random(), false);
		xmlhttp.send();
		//alert(xmlhttp.responseText);
		var data = xmlhttp.responseText;
		//alert(data);
		var wi = eval("("+data+")");
		var cpList = wi.cpList;
		var companyProperty = document.getElementById("nature");
		for (var i = 0; i < cpList.length; i++) {
			var opt = document.createElement('option');
			if((oldcpopt != null) && (cpList[i].kid == oldcpopt.value)) {
				oldcpopt.innerHTML = cpList[i].cname;
				continue;
			}
			opt.setAttribute('value', cpList[i].kid);
			opt.innerHTML = cpList[i].cname;
			companyProperty.appendChild(opt);
		}
	}
}

function getTaskVar(taskid, name) {
	var xmlhttp = initxmlhttp();
	
	if (xmlhttp != null) {
		xmlhttp.open("GET", "GetTaskVar" + "?rn=" + Math.random() + "&taskid="
				+ taskid + "&varName=" + name, false);
		xmlhttp.send();
		//alert(xmlhttp.responseText);
		var data = xmlhttp.responseText;
		//var wi = eval("(" + data + ")");
		return data;
	}
}
