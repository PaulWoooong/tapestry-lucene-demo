package com.samtech.tapestry5.web.base;

import org.apache.tapestry5.annotations.SessionState;

import com.samtech.common.domain.IUser;

public class BasePage {

	@SessionState(create=false)
	private IUser _user;
		
	
	public IUser getUser(){
		return _user;
	}
}
