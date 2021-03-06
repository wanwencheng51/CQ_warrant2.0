function addPersonInit() {
	var gender = document.getElementById("Gender");
	var opt = null;
	opt = document.createElement('option');
	opt.setAttribute('value', "0");
	opt.innerHTML = "女";
	gender.appendChild(opt);
	opt = document.createElement('option');
	opt.setAttribute('value', "1");
	opt.innerHTML = "男";
	gender.appendChild(opt);
}

$(function() {
	addPersonInit();
	
	$("#personinfo").Validform({
		tiptype : 3,
		label : ".label",
		showAllError : true,
		datatype : {
			"a" : /.|\s/,
			"a1" : /^()?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/,
			// 可以为负数 /^(-)?(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/
			"aa" : /^\d+$/,
			"a4" : /(^\d{15}$)|(^\d{17}([0-9]|X)$)/,
			"yd" : function() {
				var fa = /^\d+$/;
				var fax = $("#Mobile").val().replace(/\s+/g, "");
				if (fax == null || "" == fax) {
					return true;
				} else {
					return fa.test(fax);
				}
			},
            "gd" : function() {
				var fa = /^\d+$/;
				var fax = $("#Fix").val().replace(/\s+/g, "");
				if (fax == null || "" == fax) {
					return true;
				} else {
					return fa.test(fax);
				}
			},
			"eml" : function() {
				var em = /^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
				var emai = $("#Email").val().replace(/\s+/g, "");
				if (emai == null || "" == emai) {
					return true;
				} else {
					return em.test(emai);
				}
			},
			"piddatatype" : function(gets, obj, curform, regxp) {
				var pid = $("#PerID").val().replace(/\s+/g,"");
				var flag = "";
				if(pid != null && "" != pid) {
					$.ajax({
						url:"GetPersonInfo.action?rn=" + Math.random()
							+ "&perID=" + pid, 
						type:"post",
						dataType:"json",
						async:false,
						success: function(data) {
							if (data != null && data != "null")
								flag = data.id;
					 	}
					});
					if(flag == pid) {
						return "您输入的身份证号码已存在，请重新输入！";
					} else {
						return true;
					}
 				} else {
 					return true;
 				}
			},
		},
	});
	
	$("#personTabs").tabs();
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

