<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0);

%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
<head>
<title><decorator:title default="Struts Showcase" /></title>
<link
	href="<s:url value='/styles/main.css' encode='false' includeParams='none'/>"
	rel="stylesheet" type="text/css" media="all" />
<link
	href="<s:url value='/styles/dbx.css' encode='false' includeParams='none'/>"
	rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript"	src="<s:url value='/scripts/dbx/dbx.js' />" ></script>
    <script type="text/javascript" src="<s:url value='/scripts/dbx/dbx-key.js' />"  ></script>
<sj:head compressed="false" useJqGridPlugin="false" jqueryui="false"  locale="zh-CN" defaultIndicator="myDefaultIndicator"/>
<decorator:head/>
<!--[if lt IE 7]>
<link rel="stylesheet" href="<s:url value='/styles/ie-gif.css'/>" type="text/css" />
<![endif]-->
    
</head>

<body >
<div id="page">
<div id="wrapper">
    
        <div id="header" >
            <div id="branding">
            </div><!-- end branding -->
            <div id="search">
            </div><!-- end search -->
            <div style="clear: both;"></div>
        </div>
    

	<div id="sidebar-left" class="dbx-group" style="position: relative; display: block;">
	  <div id="nav_plugin" class="dbx-box " style="position: relative; display: block;">
	   <h3 class="dbx-handle dbx-handle-cursor" style="position: relative; display: block;" title="click-down and drag to move this box">
	     navigation
	   </h3>
	   <div class="dbx-content">
	                  <ul>
	                   <li><s:a action="etairportlist">用户管理</s:a></li>
	                  <li><s:a action="gogtlist">原始凭证</s:a></li>
	                  <li><s:a action="slidertablelist">记账凭证查询</s:a></li>
	                  <li><s:a action="gmesalist">流水账查询</s:a></li>
	                  <li><s:a action="gmesalist">T账查询</s:a></li>
                  	   <li><s:a action="gmesalist">T账报表</s:a></li>
                </ul>
	  </div>
	</div>
	 <s:action var="logininfo" name="loginInfo" namespace="/" executeResult="true" ignoreContextParams="true"/>
</div><!-- end sidebar-left -->
	<div id="left-col">
	<div id="nav"></div>
    <div id="content">
          <decorator:body/>
        
    </div><!-- end content -->
    <div id="footer" >
        <p>Copyright &copy; 2010 The samtech.co.</p>
        
    </div><!-- end footer -->
    
    </div>
	
    <!-- div id="sidebar-right" class="dbx-group" style="position: relative; display: block;">
    
      <div id="msg" class="dbx-box"  style="position: relative; display: block;">
      	<h3 class="dbx-handle dbx-handle-cursor" style="position: relative; display: block;">
      	 msg
      	</h3>
      	<div class="dbx-content">
      	  message
      	</div>
      </div>
      
    </div -->
    <!-- end sidebar-right -->
     <hr class="hidden"/>
	</div>
</div><!-- end page -->
<script type="text/javascript">
function registerfrm(){
	var logfrm=$('#loginfrm');
	if(logfrm)
	logfrm.ajaxForm({//target
			success:function(data){
			$('#login').replaceWith(data);
			if(window["sidebar_left"])
			window["sidebar_left"].refresh(false);
			registerfrm();
		  }
			
		});
	};
$(document).ready(registerfrm);
</script>
</body>
</html>
