package com.samtech.finance.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;


import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.samtech.common.domain.IUser;
import com.samtech.finance.web.action.AbstractAction;
import com.samtech.finance.web.anontation.Protected;

public class AuthorityInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7865539928724374629L;

	public static final String NO_PERMISSION = "noPermission";
	public static final String LOGIN = "loginredirect";

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		ActionContext actionContext = actionInvocation.getInvocationContext();
		//String actionName = actionContext.getName();
		HttpServletRequest request = (HttpServletRequest) actionContext
				.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession();
		ActionProxy actionProxy = actionInvocation.getProxy();
		Object action = actionProxy.getAction();
		boolean isProtected = false;
		//boolean isAuther=false;
		if(action.getClass().isAnnotationPresent(Protected.class)){
			isProtected=true;
		}else{
			try{
				Object obj = BeanUtils.getProperty(action, "protected");
				if (obj != null) {
					isProtected = Boolean.valueOf(obj.toString());
				}
			}catch(Exception e){}
		}
		
		/*if(action.getClass().getSuperclass().getName().equals(_BaseAction.class.getName())){
			isAuther=((_BaseAction)action).isAuther();
		}else{
			try{
				Object obj = BeanUtils.getProperty(action, "auther");
				if (obj != null) {
					isAuther = Boolean.valueOf(obj.toString());
				}
			}catch(Exception e){}
		}*/

		
		if(!isProtected){
			return actionInvocation.invoke();
		}
		
		if (session == null && isProtected) { 
			return LOGIN;
		}

		IUser user = (IUser) session.getAttribute(AbstractAction.USER_INFO_KEY);
		if (user == null && isProtected) {
			return LOGIN;
		}

		/*IHasAuth auth=new HasAuthBaseImpl();
		if(!isAuther ||  auth.hasAuth(actionName, user))*/
			return actionInvocation.invoke();
		/*else
			return NO_PERMISSION;*/

	}


}
