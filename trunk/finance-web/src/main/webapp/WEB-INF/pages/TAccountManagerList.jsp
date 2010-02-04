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
<td colspan="10">T账查询</td>
</tr>
  <tr class="tab">
    <td class="td1">ID：</td><td><s:textfield  name="queryUserId" maxlength="6" size="5"></s:textfield></td>
    <td class="td1">名称：</td><td><s:textfield  name="queryName" size="20"></s:textfield></td>
    <td class="td1">状态：</td><td><s:select  name="accountStatus"></s:select></td>
   </tr>
  <tr class="tab">
    <td colspan="10" align="center">
    <s:submit cssClass="button" value="查询" method="doQuery" 			id="doQuery"></s:submit>
    <s:submit cssClass="button" value="初始化T帐" method="doInit" 	id="doInit"></s:submit>
    <input class="button" type="button" onClick="$('#newUserWin').click();" value="新增T账"/>
    <div style="display:none">
    <a href="javascript:void(0)" id="newUserWin" href1="UserEdit.action" class="ymPrompt" title="新增用户">新增T账</a>
    
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


function onInvokeAction(id) {
    $.jmesa.setExportToLimit(id, '');
	
    var parameterString = $.jmesa.createParameterStringForLimit(id);
    
    $.get('${pageContext.request.contextPath}/pgUserList.action?ajax=true&' + parameterString, function(data) {
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
location.href = '${pageContext.request.contextPath}/pgUserList.action?ajax=false&' + parameterString;
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
  

