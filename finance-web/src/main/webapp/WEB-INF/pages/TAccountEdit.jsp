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
<font color="red"><s:fielderror>
			</s:fielderror>
			<s:actionerror/> <s:actionmessage/></font>

<s:form method="POST" id="questionAddForm" action="TAccountEdit" namespace="/">
<table width="100%" border="0" cellpadding="3" cellspacing="1"  class="tabg">
		<tr class="tr1">
		<td colspan="4">
		 <s:if test="accountId==null ">新增T账户</s:if>
		 <s:else> 修改T账户
		    <s:hidden name="accountId"/>
		 </s:else>
		        
		</td>
		</tr>
          <tr class="tab">
            <td width="16%" class="td1">T账账号(key)：</td>
            <td>
               <s:if test="accountId==null">
                   <s:textfield  name="account.id"  size="20" maxlength="30" ></s:textfield><span class="red">*</span>
               </s:if>
               <s:else> 
		          <s:textfield  name="account.id"  size="20" maxlength="30" readonly="true"></s:textfield>
		       </s:else>
              </td>
            <td width="16%" class="td1">账户名称：</td>
            <td><s:textfield  name="account.name"  size="20" maxlength="30"></s:textfield><span class="red">*</span></td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">借方余额：</td>
            <td><s:textfield   name="account.debitBalance"   size="20" maxlength="30"></s:textfield><span class="red">*</span></td>
            <td width="16%" class="td1">贷方余额：</td>
            <td><s:textfield   name="account.creditBalance"   size="20" maxlength="30"></s:textfield><span class="red">*</span>
            	
			</td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">科目级别：</td>
            <td><s:select name="account.level" value="account.level" list="genderValues" listKey="key" listValue="value">  
     			</s:select>  
           </td>
            <td width="16%" class="td1">上级科目代码：</td>
            <td><s:textfield  name="account.parentId"  size="10" >
            </s:textfield><s:hidden value="" id="calendarValue" name="calendarValue"/></td>            
          </tr>
</table>
		

	<p align="center">
	   	 <s:if test="accountId==null ">
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
function showCalendar(v1){
	document.getElementById("calendarValue").value=v1;
	calendar_DatePicker.toggle(document.getElementById(v1));
}
//-->
</script>
</body>
</html>
