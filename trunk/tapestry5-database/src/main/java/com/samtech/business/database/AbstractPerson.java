package com.samtech.business.database;

import java.util.Date;


public abstract class AbstractPerson {
	
	
	
	abstract public String getName() ;
	abstract public void setName(String name);
	abstract public Gender getGender();
	abstract public void setGender(Gender gender); 
	abstract public Date getBirthDay();
	abstract public void setBirthDay(Date birthDay) ;
	
	
}
