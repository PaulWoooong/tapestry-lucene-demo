package com.samtech.tapestry5.web.pages;



import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.slf4j.Logger;

import com.samtech.business.AuthorizeException;
import com.samtech.business.service.UserManagerService;
import com.samtech.common.domain.IUser;
import com.samtech.tapestry5.web.base.BasePage;
import com.samtech.tapestry5.web.base.CallBack;

// To make this page accessible only by HTTPS, annotate it with @Secure and ensure your web server can deliver HTTPS.
// See http://tapestry.apache.org/tapestry5/guide/secure.html .
// @Secure
public class Login extends BasePage {

	@Property
	private String _loginId;
	
	@Property
	private String _password;
	@SessionState(create=false)
	private CallBack callback;
	
	@Component(id = "login")
	private Form _form;

	@Component(id = "loginId")
	private TextField _loginIdField;
	@Inject
	private UserManagerService userManager;
	@Inject
	private ApplicationStateManager sessionStateManager;
	
	@Inject
	private Logger _logger;
	
	String onPassivate() {
		return _loginId;
	}
	
	void onActivate(String loginId) {
		_loginId = loginId;
	}

	void onValidateForm() {
		try {
			// Authenticate the user
			
			IUser user = userManager.login(_loginId, _password);

			sessionStateManager.set(IUser.class, user);
			
			_logger.info(user.getUserName() + " has logged in.");
		}
		catch (AuthorizeException e) {
			_form.recordError(_loginIdField, e.getLocalizedMessage());
		}
		catch (Exception e) {
			_logger.info("Could not log in.  Stack trace follows...");
			e.printStackTrace();
			_form.recordError(getMessages().get("login_problem"));
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
		return Index.class;
	}

	

}