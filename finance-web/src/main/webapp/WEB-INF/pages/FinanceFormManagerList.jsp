<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<head>
<title>user manager.</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/styles/jmesa.css" media="all"/>
<script type="text/javascript" src="<%= request.getContextPath() %>/scripts/jquery.jmesa.js"></script>
<link href="<s:url value='/scripts/prompt/skin/dmm-green/ymPrompt.css'/>" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<s:url value='/scripts/prompt/ymPrompt.js'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/prompt/jquery_ymPrompt.js'/>"></script>
<script type="text/javascript" src="<s:url value='/scripts/DatePicker/DatePicker.js'/>"></script>
</head>
<body>
<s:form  namespace="/" method="POST" id="queryForm">
<table width="100%" class="tabg" border="0" cellpadding="3" cellspacing="1" >
<tr class="tr1">
<td colspan="4">记账凭证查询</td>
</tr>
  <tr class="tab">
    <td class="td1">凭证号：</td><td><s:textfield  name="queryFinanceFormId" maxlength="15" size="12"></s:textfield></td>
    <td class="td1">原始单号：</td><td><s:textfield  name="queryName" size="12" maxlength="15"></s:textfield></td>
   </tr>
   <tr class="tab">
    <td class="td1">日期：</td><td colspan="3"><s:textfield name="startDate" size="12" id="startDate" onclick="showCalendar('startDate')"/> -<s:textfield id="endDate" name="endDate" size="12" onclick="showCalendar('endDate')"/><s:hidden value="" id="calendarValue" name="calendarValue"/></td>
    </tr>
  <tr class="tab">
    <td colspan="4" align="center">
    <s:submit cssClass="button" value="查询" method="doQuery" 			id="doQuery"></s:submit>
    
    <input class="button" type="button" onClick="$('#newUserWin').click();" value="新增记账凭证"/>
    <div style="display:none">
    <a href="javascript:void(0)" id="newUserWin" href1="FinanceFormEdit.action" class="ymPrompt" title="新增记账凭证">新增记账凭证</a>
    
    </div>
    </td>
  </tr>
</table>
</s:form>
<br/>
<s:fielderror >
</s:fielderror>
<s:actionerror /> 
<s:actionmessage/>
<br/>
<div id="employee_container">
 ${user_tbl}
</div>
<script type='text/javascript'>
var calendar_DatePicker = new Calendar();
$(document).ready(function(){
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
});
function showCalendar(v1){
	document.getElementById("calendarValue").value=v1;
	calendar_DatePicker.toggle(document.getElementById(v1));
}

function onInvokeAction(id) {
    $.jmesa.setExportToLimit(id, '');
	
    var parameterString = $.jmesa.createParameterStringForLimit(id);
    
    $.get('${pageContext.request.contextPath}/pgFinanceFormList.action?ajax=true&' + parameterString, function(data) {
    $("#employee_container").html(data);
    $('.ymPrompt').each(function (){
		$(this).click(function(){
			var _href = null;
			var _title= null;
			var _handler=null;
			_href = $(this).attr("href1");
			_title = $(this).attr("title");
			_handler=$(this).attr("handler");
			ymPrompt.win({message:_href,iframe:true,width:800,height:400,title:_title,showMask:false,handler:_handler});
			return false;
		});
		
	});
});
}

function onInvokeExportAction(id) {
var parameterString = $.jmesa.createParameterStringForLimit(id);
location.href = '${pageContext.request.contextPath}/pgTAccountList.action?ajax=false&' + parameterString;
}

function del(v1){
	var pass =  confirm('确定要删除该记录吗？');
	if(pass){
		return true;
	}
	return false;
}
</script>
</body>
  

