	function showContractList() {
		var td = new Array();
		var m = document.getElementById("serviceType").value;
		if (m == "0") {
			td.push('<table width="97%" id="r">');
			td.push('<tr>');
			td.push('<td width="9%" align="center">1</td>');
			td.push('<td width="70%">委托担保合同(必选)</td>');
			td.push('<td width="21%" align="center"><label><input type="checkbox" name="checkboxw" id="checkboxw"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">2</td>');
			td.push('<td>反担保合同(必选)</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkboxf" id="checkboxf"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">3</td>');
			td.push('<td>股东会决议(必选)</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkboxg" id="checkboxg"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">4</td>');
			td.push('<td>补充协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">5</td>');
			td.push('<td>承诺函（借款人）</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">6</td>');
			td.push('<td>承诺函（反担保人）</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">7</td>');
			td.push('<td>房屋转让协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">8</td>');
			td.push('<td>房屋租赁协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">9</td>');
			td.push('<td>房产抵押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">10</td>');
			td.push('<td>股权质押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">11</td>');
			td.push('<td>车辆转让抵押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('</table>');
		} else {
			td.push('<table width="97%" id="r">');
			td.push('<tr>');
			td.push('<td width="9%" align="center">1</td>');
			td.push('<td width="70%">委托担保合同(必选)</td>');
			td.push('<td width="21%" align="center"><label><input type="checkbox" name="checkboxt" id="checkboxt"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">2</td>');
			td.push('<td>反担保合同(必选)</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkboxd" id="checkboxd"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">3</td>');
			td.push('<td>补充协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">4</td>');
			td.push('<td>共有人声明</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">5</td>');
			td.push('<td>承诺函（借款人）</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">6</td>');
			td.push('<td>承诺函（反担保人）</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">7</td>');
			td.push('<td>谈话笔录</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">8</td>');
			td.push('<td>房屋转让协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">9</td>');
			td.push('<td>房屋租赁协议</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">10</td>');
			td.push('<td>房产抵押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">11</td>');
			td.push('<td>股权质押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('<tr>');
			td.push('<td align="center">12</td>');
			td.push('<td>车辆转让抵押合同</td>');
			td.push('<td align="center"><label><input type="checkbox" name="checkbox" id="checkbox"></label></td>');
			td.push('</tr>');
			td.push('</table>');
		}
		document.getElementById("signHDdiv").innerHTML = td.join('');
		td = null;
	}