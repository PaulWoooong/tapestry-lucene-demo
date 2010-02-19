<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>csn</title>
<link href="css/skin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"	src="<s:url value='/scripts/jquery/jquery-1.4.1.js' />" ></script>
<script src="<s:url value='/scripts/DatePicker/DatePicker.js'/>" type="text/javascript"></script>
<script src="<s:url value='/scripts/jquery.autocomplete.js'/>" type="text/javascript"></script>

<style type="text/css">
<!--
.STYLE2 {
	color: #339900;
	font-weight: bold;
}
-->
</style>
</head>


<body>
<font color="red"><s:fielderror>
			</s:fielderror>
			<s:actionerror/> <s:actionmessage/></font>

<s:form method="POST" id="questionAddForm" action="FinanceFormEdit" namespace="/">
<table width="100%" border="0" cellpadding="3" cellspacing="1"  class="tabg">
		<tr class="tr1">
		<td colspan="4">
		 <s:if test="queryFinanceFormId==null ">新增记账凭证</s:if>
		 <s:else> 修改记账凭证
		    <s:hidden name="queryFinanceFormId"/>
		 </s:else>
		        
		</td>
		</tr>
          <tr class="tab">
            <td width="16%" class="td1">凭证号：</td>
            <td>
               <s:if test="queryFinanceFormId==null">
                   <s:textfield  name="FinanceForms.id"  size="20" maxlength="30" ></s:textfield><span class="red">*</span>
               </s:if>
               <s:else> 
		          <s:textfield  name="financeForms.id"  size="20" maxlength="30" readonly="true"></s:textfield>
		       </s:else>
              </td>
            <td width="16%" class="td1">原始单号：</td>
            <td><s:textfield  name="financeForms.businessId"  size="20" maxlength="30"></s:textfield></td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">发生金额：</td>
            <td><s:textfield   name="financeForms.amount"   size="20" maxlength="30"></s:textfield><span class="red">*</span></td>
            <td width="16%" class="td1">日期</td>
            <td><s:textfield   name="financeForms.bizDate"   id="calendar_DatePicker1" size="20" maxlength="12" readonly="true" onClick="showCalendar('calendar_DatePicker1');"></s:textfield><span class="red">*</span>
			<s:hidden value="" id="calendarValue" name="calendarValue"/></td>            
          </tr>
          <tr class="tab">
          	<td width="16%" class="td1">备注</td>
            <td colspan="3"><s:textfield   name="financeForms.context"   size="26" maxlength="30"></s:textfield>
            </td>
          
                       
          </tr>
</table>
	<table id="customerteltable" width="100%" border="0" cellpadding="3" cellspacing="1"  class="tabg">
		 <tr class="tr1">
		 	<td>科目代码</td><td>借/贷</td><td>金额</td><td>公司代码</td><td>操作(<a href="#" onclick="addTelTr();return false;">add</a>)</td>
		 </tr>
		 <s:if test="begincounter"></s:if>
		 <s:iterator value="financeForms.debits" var="item" >
		 <tr id='teltr<s:property value="IndexKey"/>'>
		 <td><input id='acct_<s:property value="IndexKey"/>' name='acct_<s:property value="IndexKey"/>' value='<s:property value="financeId"/>' /></td>
		 <td><select name='direct_<s:property value="IndexKey"/>'  >
		 <option value="DEBIT" selected="selected">借</option>
    		<option value="CREDIT">贷</option>
		 </select></td>
		 <td><input name='amt_<s:property value="IndexKey"/>' value='<s:property value="amount"/>'/></td>
		 <td><input name='cmp_<s:property value="IndexKey"/>' value='<s:property value="companyId"/>'/></td>
		 <td><a href="#" onclick="delTelTr('teltr<s:property value="IndexKey"/>');">delete</a></td>
		 </tr>
		 <s:if test="counter"></s:if>
		 </s:iterator>
		 <s:iterator value="financeForms.credits" var="item" >
		 <tr id='teltr<s:property value="IndexKey"/>'>
		 <td><input id='acct_<s:property value="IndexKey"/>' name='acct_<s:property value="IndexKey"/>' value='<s:property value="financeId"/>' /></td>
		 <td><select name='direct_<s:property value="IndexKey"/>'  >
		 <option value="DEBIT" >借</option>
    		<option value="CREDIT" selected="selected">贷</option>
		 </select></td>
		 <td><input name='amt_<s:property value="IndexKey"/>' value='<s:property value="amount"/>'/></td>
		 <td><input name='cmp_<s:property value="IndexKey"/>' value='<s:property value="companyId"/>'/></td>
		 <td><a href="#" onclick="delTelTr('teltr<s:property value="IndexKey"/>');">delete</a></td>
		 </tr>
		 <s:if test="counter"></s:if>
		 </s:iterator>
	</table>	

	<p align="center">
	   	 <s:if test="queryFinanceFormId==null ">
			 <s:submit name="submit" method="doAddAndNew" id="doAddAndNew" value="保存并新增" />
	  	 </s:if>
     	 <s:else> 
			<s:submit name="submit" method="doAddAndView"  id="doAddAndView"  value="保存"/>
         </s:else>
		<input type="button" value="关闭" onClick="parent.ymPrompt.close();" />
	</p>
</s:form>
<script type="text/javascript">//<!--
var calendar_DatePicker = new Calendar();
calendar_DatePicker.create();
calendar_DatePicker.onchange = function() {
 field = document.getElementById(document.getElementById("calendarValue").value);
 var value = this.formatDate();
  if (field.value != value) {
    field.value = value;
    if (field.onchange) {
      field.onchange();
    }
  }
}
jQuery(document).ready(function(){
	var startindex=0;
	while(true){
		var tr=document.getElementById("acct_"+startindex);
		if(tr!=null){
		 startindex++;
			$(tr).autocomplete({
				serviceUrl: 'searchAccount.action',
		        minChars:2,
		        
		        fnFormatResult: function(value, data) {
		                return '<span style="color:red">' + data.id+" "+data.name + '</span>';
		        }
		});
		}else{
		if(startindex++>50)
		 break;
		}
	}


});
function ajaxauto(el){
	$(el).autocomplete({
		serviceUrl: 'searchAccount.action',
        minChars:2,
        fnFormatResult: function(value, data) {
            return '<span style="color:red">' + data.id+" "+data.name + '</span>';
    }
});
}
function showCalendar(v1){
	document.getElementById("calendarValue").value=v1;
	calendar_DatePicker.toggle(document.getElementById(v1));
}



function addTelTr(){
var psgtable=document.getElementById("customerteltable");
var startindex=0;
while(true){
	var tr=document.getElementById("teltr"+startindex);
	if(tr!=null){
	 startindex++;
	}else{
	 break;
	}
}
if(startindex>50){
    alert('记录不能大于50条!');
    return;
}
var tr=psgtable.insertRow(-1);
tr.id="teltr"+startindex;
tr.name="teltr"+startindex;
tr.setAttribute("align","center");
//tr.bgColor="#f7f7f7";

var td=tr.insertCell(-1);
var inp=createTelInput('acct_'+startindex,'');
td.appendChild(inp);
ajaxauto(inp);
td=tr.insertCell(-1);
inp=addTelSelectOptions('direct_'+startindex);
td.appendChild(inp);

td=tr.insertCell(-1);
inp=createTelInput('amt_'+startindex,'',6);
td.appendChild(inp);

td=tr.insertCell(-1);
inp=createTelInput('cmp_'+startindex,'',8);
td.appendChild(inp);


td=tr.insertCell(-1);
inp=addtelbutton('删除','teltr'+startindex);
td.appendChild(inp);
}
function delTelTr(trid){
    var psgtable=document.getElementById("customerteltable");
    var teltr=document.getElementById(trid);
    psgtable.deleteRow(teltr.rowIndex);
}
function addtelbutton(value,name) {
	var delbutton = document.createElement("button");
	delbutton.appendChild(document.createTextNode(value));
	delbutton.className="button";
    delbutton.onclick=new Function("delTelTr('"+ name+"')");
	return delbutton;
}
function createTelInput(name,value,sizes){
	var inp= document.createElement("input");
	inp.setAttribute("name" ,name);
	inp.setAttribute("id",name);
	inp.setAttribute("type","text");
	inp.setAttribute("value",value);
	inp.setAttribute("size",sizes);
	inp.className='txt';
	return inp;
	}
function addTelSelectOptions(name){
    var inp=document.createElement("select");
    inp.setAttribute("name",name);
	inp.setAttribute("id",name);
	var selectvalue=new Array('DEBIT','CREDIT');
	var selectdisp=new Array('借','贷');
	 for(i=0;i<2;i++){
		op=document.createElement("option");
		op.setAttribute("value",selectvalue[i]);
		text= document.createTextNode(selectdisp[i]);
		op.appendChild(text);
		inp.appendChild(op);
	 }
	 return inp;
}
//-->
</script>
</body>
</html>
