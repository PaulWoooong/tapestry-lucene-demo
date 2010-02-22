package com.samtech.tapestry5.web.components;

import java.io.IOException;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;

import com.samtech.common.domain.IUser;

public class Layout {
   

    @Inject
    private Request request;

    @Inject
    private Response response;

   

    @Inject
    private ComponentResources resources;

    @Parameter(required = true)
    private String title;

    /*@OnEvent(value = "action", component = "locale")
    void changeLocale() {
        Locale current = localeService.get();
        if (current == null || current.equals(PERSIAN_LOCAL)) {
            localeService.set(ENGLISH_LOCAL);
        } else {
            localeService.set(PERSIAN_LOCAL);
        }
    }*/

    @OnEvent(value = "action")
    Object changePage(String page) {
        return  page;//"secure/" +
    }

    @OnEvent(value = "action", component = "logout")
    Object logout() throws IOException {
        Session session = request.getSession(true);
        session.invalidate();
        return null;
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
