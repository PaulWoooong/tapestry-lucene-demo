package com.samtech.tapestry5.web.services;

import java.io.IOException;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.runtime.Component;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.Dispatcher;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;

import com.samtech.common.domain.IUser;
import com.samtech.tapestry5.web.annotation.ProtectedPage;

public class ProtectedPageGateKeeper implements Dispatcher {
	private final static String LOGIN_PAGE = "login";
	

	private ApplicationStateManager SessionStateManager;
	private final ComponentClassResolver componentClassResolver;
	private final ComponentSource componentSource;
	private PageRenderLinkSource pageRenderLinkSource;
	
	//private final Logger logger;

	/**
	 * Receive all the services needed as constructor arguments. When we bind this service, T5 IoC will provide all the
	 * services !
	 */
	public ProtectedPageGateKeeper(ApplicationStateManager asm, ComponentClassResolver resolver,
			ComponentSource componentSource, PageRenderLinkSource pageRenderLinkSource) {
		this.SessionStateManager = asm;
		this.componentClassResolver = resolver;
		this.componentSource = componentSource;
		this.pageRenderLinkSource=pageRenderLinkSource;
		
		
	}

	public boolean dispatch(Request request, Response response) throws IOException {
		/*
		 * We need to get the Tapestry page requested by the user. So we parse the path extracted from the request
		 */
		String path = request.getPath();
		int nextslashx = path.length();
		String pageName;

		while (true) {
			pageName = path.substring(1, nextslashx);
			if (!pageName.endsWith("/") && componentClassResolver.isPageName(pageName))
				break;
			nextslashx = path.lastIndexOf('/', nextslashx - 1);
			if (nextslashx <= 1)
				return false;
		}
		if(pageName.equalsIgnoreCase(this.LOGIN_PAGE))return false;
		return checkAccess(pageName, request, response);
	}

	/**
	 * Check the rights of the user for the page requested
	 * 
	 * @throws IOException
	 */
	public boolean checkAccess(String pageName, Request request, Response response) throws IOException {
		boolean canAccess = true;

		/* Is the requested page private ? */
		Component page = componentSource.getPage(pageName);
		boolean protectedPage = page.getClass().getAnnotation(ProtectedPage.class) != null;

		if (protectedPage) {
			canAccess = false;
			// If a Visit already exists in the session then you have already been authenticated
			if (SessionStateManager.exists(IUser.class)) {
				canAccess = true;
			}
			
			
		}

		/*
		 * This page can't be requested by a non-authenticated user => we redirect him to the LogIn page
		 */
		if (!canAccess) {
			String path = request.getPath();
			String[] activationContextParams=new String[0];
			if(path.length()>=pageName.length()+2){
				activationContextParams= path.substring(pageName.length() +
			2).split("\\/");
			}
			 Component page2 = componentSource.getPage("login");
			 //PageCallbackContainer page1 =(PageCallbackContainer)page2;
			 Class[] interfaces = page2.getClass().getInterfaces();
			 //boolean  found=false;
			 
			Link backlink = pageRenderLinkSource.createPageRenderLinkWithContext(pageName,
			(Object[]) activationContextParams);
			if(backlink!=null){
				Session session = request.getSession(true);
				if(session!=null)session.setAttribute("login_callback", backlink.toRedirectURI());
			}
			Link back = pageRenderLinkSource.createPageRenderLink("login");
			response.sendRedirect(back);
			return true; // Make sure to leave the chain
		}

		return false;
	}
	
}
	
	

	
