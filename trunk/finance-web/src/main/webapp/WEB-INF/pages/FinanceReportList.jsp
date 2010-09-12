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
<td colspan="10">报表查询</td>
</tr>
  
   <tr class="tab">
    <td class="td1">年月：</td><td colspan="3"><s:select name="year" size="1" id="startDate" list="genEnum" listKey="key" listValue="value">
    </s:select>
     -<s:select name="month"  size="1" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10','11':'11','12':'12'}">
     </s:select></td>
    </tr>
  <tr class="tab">
    <td colspan="10" align="center">
    <s:submit cssClass="button" value="查询" method="doQuery" 			id="doQuery"></s:submit>
    <s:submit cssClass="button" value="完税结转" method="doConfirm" 			id="doQuery"></s:submit>
    <s:submit cssClass="button" value="删除税务结转" method="refuse" click="return refuseconfirm();"	id="doQuery"></s:submit>
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
<s:form namespace="/" method="POST" id="printForm" action="ReportExport">
<div id="employee_container">
 ${user_tbl}
</div>
<s:if test="showTable">
<input type="button" value="导出Excel" onclick="submitexport()"/>
</s:if>
</s:form>
<script type='text/javascript'>
function submitexport(){
	 var form1=document.getElementById("printForm");
	 form1.submit();
}
function onInvokeAction(id) {
    $.jmesa.setExportToLimit(id, '');
	
    var parameterString = $.jmesa.createParameterStringForLimit(id);
    
    $.get('${pageContext.request.contextPath}/pgReportlist.action?ajax=true&' + parameterString, function(data) {
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
location.href = '${pageContext.request.contextPath}/pgReportlist.action?ajax=false&' + parameterString;
}

function del(v1){
	var pass =  confirm('确定要删除该记录吗？');
	if(pass){
		return true;
	}
	return false;
}
function refuseconfirm(){
	var pass =  confirm('确定要重新记税吗？');
	if(pass){
		return true;
	}
	return false;
}
</script>
</body>
  

