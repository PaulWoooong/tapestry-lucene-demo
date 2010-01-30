package com.samtech.common.domain;

import java.io.Serializable;

public interface IUser {
	
	public String getUserName();
	
	public String getPassword();
	
	public Serializable getId();

}
