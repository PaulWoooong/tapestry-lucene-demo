<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div id="login" class="dbx-box"
	style="position: relative; display: block;">
<h3 class="dbx-handle dbx-handle-cursor"
	style="position: relative; display: block;"><s:if test="logined">用户信息</s:if>
<s:else>登录信息</s:else></h3>
<div class="dbx-content">
<s:if test="logined">
	<div>欢迎 <s:property value="loginUser.UserName" /></div>
	<div>有效期至 <s:date name="loginUser.expireDate" format="yyyy-MM-dd"/></div>
	<div><s:a name="logout" action="logout">logout</s:a></div>
</s:if> <s:else>

	<table>
		<tr>
			<td align="left" style="font: bold; color: red">
			<s:fielderror >
			<s:param>username</s:param>
			<s:param>password</s:param>
			</s:fielderror>
			<s:actionerror /> <s:actionmessage /></td>
		</tr>
	</table>
	<s:form action="login" validate="true" id="loginfrm">
		<table class="tbl">
			<tr>
				<td class="label">用户帐号</td>
				<td class="inp"><s:textfield name="username" size="10"
					maxlength="12"></s:textfield></td>
			</tr>
			<tr>
				<td class="label">密码</td>
				<td class="inp"><s:password name="password" size="10"
					maxlength="10">
				</s:password></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit id="login_btn" value="登录" ></s:submit>
				</td>
			</tr>
		</table>
	</s:form>
</s:else>
</div>

</div>