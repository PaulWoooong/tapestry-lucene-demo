package com.samtech.tapestry5.web.components;

import java.io.IOException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;

import com.samtech.common.domain.IUser;
import com.samtech.tapestry5.web.pages.Login;

/**
 *
 */
@IncludeStylesheet(value={"context:/styles/main.css","context:/styles/dbx.css"})
@IncludeJavaScriptLibrary(value="context:/scripts/dbx/dbx.js")
public class Layout {
   

    @Inject
    private Request request;

    @Inject
    private Response response;

   

    @Inject
    private ComponentResources resources;

    @Parameter(required = true)
    private String title;

    

    @OnEvent(value = "action")
    Object changePage(String page) {
        return  page;//"secure/" +
    }

    @OnEvent(value = "action", component = "logout")
    Object logout() throws IOException {
    	response.setHeader( "Cache-Control" , "no-cache" ); 
		//Forces caches to obtain a new copy of the page from the origin server 
    	response.setHeader( "Cache-Control" , "no-store" ); 
		//Directs caches not to store the page under any circumstance 
    	response.setDateHeader( "Expires" , 0); 
		//Causes the proxy cache to see the page as "stale" 
    	response.setHeader( "Pragma" , "no-cache" );
        Session session = request.getSession(false);
        if(session!=null)
        session.invalidate();
        return Login.class;
    }

    public String getContext() {
        return request.getContextPath();
    }

    public boolean isAuthenticated() {
       /* SecurityContext context = (SecurityContext)
                request.getSession(true).getAttribute(SecurityService.SECURITY_CONTEXT);
        return securityService.isUserAuthenticated(context);*/
    	 Session session = request.getSession(true);
    	 Object u = session.getAttribute("user");
    	 if(u!=null)return true;
    	 return false;
    }

    public String getLoginName() {
        /*SecurityContext context = (SecurityContext)
                request.getSession(true).getAttribute(SecurityService.SECURITY_CONTEXT);
        return securityService.getAuthenticatedUserName(context);*/
    	Session session = request.getSession(true);
   	 Object u = session.getAttribute("user");
   	 if(u!=null)return ((IUser)u).getUserName();
   	 return "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /*public String getHomeCss() {
        String page = getPage();
        return page.contains("/Home") ? "selected" : "";
    }

    public String getListCss() {
        return getPage().contains("/List")
                || getPage().contains("/View")
                || getPage().contains("/Edit")
                ? "selected" : "";
    }

    public String getInsertCss() {
        return getPage().contains("/Insert") ? "selected" : "";
    }*/

    private String getPage() {
        return resources.getPageName();
    }
}
