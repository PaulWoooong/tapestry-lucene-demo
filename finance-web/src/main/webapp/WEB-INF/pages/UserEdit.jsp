<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>csn</title>
<link href="css/skin.css" rel="stylesheet" type="text/css" />
<script src="<s:url value='/js/jquery/jquery.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="js/show.js"></script>

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
<s:iterator value="validErrors" var="msg">
 <li><font color="red"><s:property value="msg"></s:property></font></li>
</s:iterator>
<s:property value="message"/>
<s:form method="POST" id="questionAddForm" action="StafferEdit" namespace="/">
<table width="100%" border="0" cellpadding="3" cellspacing="1"  class="tabg">
		<tr class="tr1">
		<td colspan="4">
		 <s:if test="stafferId==null || stafferId==''">新增用户</s:if>
		 <s:else> 修改用户 
		    <s:hidden name="stafferId"/>
		 </s:else>
		        
		</td>
		</tr>
          <tr class="tab">
            <td width="16%" class="td1">登录名：</td>
            <td>
               <s:if test="stafferId==null || stafferId==''">
                   <s:textfield  name="staffer.id"  size="20" maxlength="30" ></s:textfield><span class="red">*</span>
               </s:if>
               <s:else> 
		          <s:textfield  name="staffer.id"  size="20" maxlength="30" readonly="true"></s:textfield>
		       </s:else>
              </td>
            <td width="16%" class="td1">姓名：</td>
            <td><s:textfield  name="staffer.stafferName"  size="20" maxlength="30"></s:textfield><span class="red">*</span></td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">密码：</td>
            <td><input type="password"   name="staffer.password"  value="<s:property value='staffer.password'/>" size="20" maxlength="30"></input><span class="red">*</span></td>
            <td width="16%" class="td1">部门：</td>
            <td><s:textfield  name="staffer.departmentId"  size="20" maxlength="30"></s:textfield></td>            
          </tr>
          <tr class="tab">
            <td width="16%" class="td1">座机：</td>
            <td><s:textfield  name="staffer.telphone"  size="20" maxlength="30"></s:textfield></td>
            <td width="16%" class="td1">手机：</td>
            <td><s:textfield  name="staffer.mobile"  size="20" maxlength="30"></s:textfield></td>            
          </tr>
</table>
		

	<p align="center">
	   	 <s:if test="stafferId==null || stafferId==''">
			 <s:submit name="submit" method="addAndNew" id="doAddAndNew" value="保存并新增" />
	  	 </s:if>
     	 <s:else> 
			<s:submit name="submit" method="addAndView"  id="doAddAndView"  value="保存"/>
         </s:else>
		<input type="button" value="关闭" onClick="parent.ymPrompt.close();" />
	</p>
</s:form>
<script type="text/javascript">
</script>
</body>
</html>
