<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>南航在线客服平台</title>
<style>
body{margin:120px 0px 0px 0px;font-size:14px;background:#006699;}
#wrap{margin:auto;width:985px;position:relative;}
.top-nav{width:545px;}
.content{background:#FFFFFF none repeat scroll 0 0;clear:both;overflow:hidden;padding:25px 25px 15px;width:495px;}
.nav{background:transparent url(images/content-top-bg.gif) no-repeat scroll left top;height:26px;}
.content ul{list-style-type:none;color:#003366;font-weight:bold;padding:10px 0px 0px 100px}
.content ul li{padding:0px 0px 13px 0px;}

.red{color:red}
.inpu{border:1px solid #3399CC;font-size:14px;margin:0px 0px 0px 5px;height:18px;padding:3px 0px 0px 3px;}
.inbu{width:87px;height:30px;padding:4px 0px 0px 0px;background:url("images/login_bt.gif");border:0px solid #FFF;color:#003366;font-size:12px;font-weight:bold;}
.footer{background:transparent url(/images/content-bottom-bg.png) no-repeat scroll 0 0;color:#282828;font-size:87%;
line-height:15px;padding:12px 0 0 10px;}
</style>
<script language=JavaScript type=text/javascript><!--
	window.onload = function (){
		document.Form0.loginUsername.focus();
		document.Form0.loginUsername.select();
	}
// --></script>
</head>

<body>
 <div id="wrap">
 
		<s:form action="login" namespace="/"  name="Form0" cssClass="v">
			<div class="top-nav">
			<div class="nav"></div>
			<div class="content">
			<font color="red"><s:fielderror >
			<s:param>username</s:param>
			<s:param>password</s:param>
			</s:fielderror>
			<s:actionerror /> <s:actionmessage /></font>
				<ul>
					<li>用户名:<s:textfield name="username" cssClass="inpu"></s:textfield></li>
					<li>密　码:<s:password  name="password" cssClass="inpu"/></li>
					<li style="padding-left:55px;">
					<s:submit value="登录"   cssClass="inbu"></s:submit>
					<input type="reset" value="清 空" name=Submit  class="cancle inbu"/>
					</li>
				</ul>	
			</div>
			<div class="footer"></div>
			</div>
		</s:form>
</div>
</body>
</html>
<script>
if (top.location != this.location) {
 top.location = this.location;
}
</script>
