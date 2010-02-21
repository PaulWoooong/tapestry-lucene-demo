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
<td colspan="10">流水帐查询</td>
</tr>
  <tr class="tab">
    <td class="td1">凭证号：</td><td><s:textfield  name="queryFinanceFormId" maxlength="15" size="12"></s:textfield></td>
    <td class="td1">摘要：</td><td><s:textfield  name="queryName" size="16"></s:textfield></td>
    <td class="td1">T账科目：</td><td><s:textfield  name="accountId" ></s:textfield></td>
   </tr>
   <tr class="tab">
    <td class="td1">日期：</td><td colspan="3"><s:textfield name="startDate" size="12" id="startDate" onclick="showCalendar('startDate')">
    <s:param name="value"><s:date name="startDate" format="yyyy-MM-dd"/></s:param></s:textfield>
     -<s:textfield id="endDate" name="endDate" size="12" onclick="showCalendar('endDate')">
     <s:param name="value"><s:date name="endDate" format="yyyy-MM-dd"/></s:param></s:textfield><s:hidden value="" id="calendarValue" name="calendarValue"/></td>
    </tr>
  <tr class="tab">
    <td colspan="10" align="center">
    <s:submit cssClass="button" value="查询" method="doQuery" 			id="doQuery"></s:submit>
    
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


function onInvokeAction(id) {
    $.jmesa.setExportToLimit(id, '');
	
    var parameterString = $.jmesa.createParameterStringForLimit(id);
    
    $.get('${pageContext.request.contextPath}/pgRunningAccountList.action?ajax=true&' + parameterString, function(data) {
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
location.href = '${pageContext.request.contextPath}/pgRunningAccountList.action?ajax=false&' + parameterString;
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
  

