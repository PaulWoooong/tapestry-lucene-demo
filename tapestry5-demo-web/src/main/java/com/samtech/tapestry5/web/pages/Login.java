package com.samtech.tapestry5.web.pages;



import java.io.IOException;


import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.Session;
import org.slf4j.Logger;

import com.samtech.business.AuthorizeException;
import com.samtech.business.service.UserManagerService;
import com.samtech.common.domain.IUser;
import com.samtech.tapestry5.web.base.BasePage;

public class Login extends BasePage {

	@Property
	private String _loginId;
	@Property
	private String redirectURI;
	@Property
	private String _password;
	@SuppressWarnings("unused")
	@Property
	private ValueEncoder<String> stringEncoder=new StringValueEncoder();
	
	
	@Component(id = "login")
	private Form _form;

	@Component(id = "loginId")
	private TextField _loginIdField;
	@Component(id="password")
	private PasswordField _passowrdField;
	@Inject
	private UserManagerService userManager;
	@Inject
	private ApplicationStateManager sessionStateManager;
	
	@Inject
	private Response response;
	@Inject
	private Request request;
	@Inject
	private Logger _logger;
	@Inject
	private ComponentResources _componentResources;
	
	String onPassivate() {
		return _loginId;
	}
	@OnEvent(value=EventConstants.ACTIVATE)
	void activatePage(Object... args) {
		if(args!=null && args.length>0){
			_loginId =(String)args[0];
		}
		
		boolean haveenv=false;
		
		Session session = request.getSession(false);
		if(session!=null && !haveenv){
			
			redirectURI=(String) session.getAttribute("login_callback");
			session.setAttribute("login_callback", null);
		}
	}

	void onValidateForm() {
		try {
			// Authenticate the user
			
			IUser user = userManager.login(_loginId, _password);

			sessionStateManager.set(IUser.class, user);
			
			_logger.info(user.getUserName() + " has logged in.");
		}
		catch (AuthorizeException e) {
			Integer code = e.getCode();
			if(code!=null){
				if(AuthorizeException.USER_NOTFOUND.equals(code))
					_form.recordError(_loginIdField, this._componentResources.getMessages().format("user-nofound", _loginId));
				else if(AuthorizeException.PASSWORD_ERROR.equals(code))
				_form.recordError(_passowrdField, this._componentResources.getMessages().format("password-error"));//password-error
				else
					_form.recordError(_loginIdField, this._componentResources.getMessages().format("account-error"));
			}
			else{
			_form.recordError(_loginIdField, e.getLocalizedMessage());
			}
		}
		catch (Exception e) {
			_logger.info("Could not log in.  Stack trace follows...");
			e.printStackTrace();
			_form.recordError(_componentResources.getMessages().get("login_problem"));
		}
	}
	
	public ApplicationStateManager getStateManager(){
		return this.sessionStateManager;
	}
	Object onSuccess() {
		/*if(callback!=null){
			Link backLink = callback.getBackLink();
			String burl=backLink.toAbsoluteURI();
			sessionStateManager.set(CallBack.class, null);
			callback=null;
			 return burl; 
		}*/
		if(StringUtils.isNotBlank(redirectURI))
			try {
				response.sendRedirect(redirectURI);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		return Index.class;
	}

	

}