<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>csn</title>
<link href="css/skin.css" rel="stylesheet" type="text/css" />
<script src="<s:url value='/scripts/DatePicker/DatePicker.js'/>" type="text/javascript"></script>

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
<body>
<font color="red"><s:fielderror >
			<s:param>username</s:param>
			<s:param>password</s:param>
			</s:fielderror>
			<s:actionerror /> <s:actionmessage /></font>

<s:form method="POST" id="questionAddForm" action="UserEdit" namespace="/">
<table width="100%" border="0" cellpadding="3" cellspacing="1"  class="tabg">
		<tr class="tr1">
		<td colspan="4">
		 <s:if test="queryUserId==null || queryUserId==''">新增用户</s:if>
		 <s:else> 修改用户 
		    <s:hidden name="queryUserId"/>
		 </s:else>
		        
		</td>
		</tr>
          <tr class="tab">
            <td width="16%" class="td1">登录名：</td>
            <td>
               <s:if test="queryUserId==null || queryUserId==''">
                   <s:textfield  name="editUser.employeeId"  size="20" maxlength="30" ></s:textfield><span class="red">*</span>
               </s:if>
               <s:else> 
		          <s:textfield  name="editUser.employeeId"  size="20" maxlength="30" readonly="true"></s:textfield>
		       </s:else>
              </td>
            <td width="16%" class="td1">姓名：</td>
            <td><s:textfield  name="editUser.name"  size="20" maxlength="30"></s:textfield><span class="red">*</span></td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">密码：</td>
            <td><input type="password"   name="editUser.password"  value="<s:property value='editUser.password'/>" size="20" maxlength="30"></input><span class="red">*</span></td>
            <td width="16%" class="td1">性别：</td>
            <td>
            	<s:select name="editUser.gender" value="editUser.gender" list="genderValues" listKey="key" listValue="value">  
		        <!-- option value="MALE">男</option>  
		        <option value="FEMALE">女</option-->  
     			</s:select>  
			</td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">出生日期：</td>
            <td><s:textfield  name="editUser.birthDay" id="calendar_DatePicker0" size="20" maxlength="12" readonly="true" onClick="showCalendar('calendar_DatePicker0');">
            <s:param name="value"><s:date name="editUser.birthDay" format="yyyy-MM-dd"/></s:param>
            </s:textfield></td>
            <td width="16%" class="td1">帐号有效期至：</td>
            <td><s:textfield  name="editUser.expireDate" id="calendar_DatePicker1" size="20" maxlength="12" readonly="true" onClick="showCalendar('calendar_DatePicker1');">
            <s:param name="value"><s:date name="editUser.expireDate" format="yyyy-MM-dd"/></s:param>
            </s:textfield><s:hidden value="" id="calendarValue" name="calendarValue"/></td>            
          </tr>
</table>
		

	<p align="center">
	   	 <s:if test="queryUserId==null || queryUserId==''">
			 <s:submit name="submit" method="addAndNew" id="doAddAndNew" value="保存并新增" />
	  	 </s:if>
     	 <s:else> 
			<s:submit name="submit" method="addAndView"  id="doAddAndView"  value="保存"/>
         </s:else>
		<input type="button" value="关闭" onClick="parent.ymPrompt.close();" />
	</p>
</s:form>
<script type="text/javascript">
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
function showCalendar(v1){
	document.getElementById("calendarValue").value=v1;
	calendar_DatePicker.toggle(document.getElementById(v1));
}
</script>
</body>
</html>
