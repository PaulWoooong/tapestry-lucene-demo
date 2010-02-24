package com.samtech.tapestry5.web.base;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.samtech.common.domain.IUser;

public class BasePage {

	@SessionState(create=false)
	private IUser _user;
		
	
	@Inject
	private Messages _messages;

	protected Messages getMessages() {
		return _messages;
	}
	
	public IUser getUser(){
		return _user;
	}
}
