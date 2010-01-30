package com.samtech.finance.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.samtech.common.domain.IUser;

public abstract class AbstractAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware,Preparable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	final String LOGIN_KEY = "user_logined";
	final String USER_INFO_KEY = "user_info";
	private IUser user;

	public boolean isLoninRequired() {
		return true;
	}

	public void setServletRequest(HttpServletRequest request) {
		this._request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this._response = response;
	}

	protected HttpServletRequest getServletRequest() {
		// TODO Auto-generated method stub
		return _request;
	}

	protected HttpServletResponse getServletResponse() {
		return _response;
	}

	public void prepare() throws Exception {
		HttpServletRequest request = getServletRequest();
		HttpSession session = request.getSession();
		if (isLoninRequired()) {
			user = null;
			if (session != null) {

				Object o = session.getAttribute(LOGIN_KEY);
				if (o != null && ((Boolean) o).booleanValue()) {

					user = (IUser) session.getAttribute(USER_INFO_KEY);
				}
			}
			Map<String, Object> smap = ActionContext.getContext().getSession();
			if (smap != null) {
				Object o = smap.get(LOGIN_KEY);
				if (o != null && ((Boolean) o).booleanValue()) {

					user = (IUser) smap.get(USER_INFO_KEY);
				}
			}
		}
	}

	protected IUser getLoginUser() {
		return user;
	}
}
