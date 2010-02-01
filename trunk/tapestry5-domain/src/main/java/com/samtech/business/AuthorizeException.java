package com.samtech.business;

public class AuthorizeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7536844898108087515L;
	public final static Integer USER_NOTFOUND=new Integer(1001);
	public final static Integer ACCOUNT_ERROR=new Integer(1002);
	public final static Integer PASSWORD_ERROR=new Integer(1003);
	
	private Integer code;
	
	public AuthorizeException() {
		
	}

		
	
	public AuthorizeException(String message, Throwable cause) {
		super(message, cause);
		
	}



	public AuthorizeException(String message) {
		super(message);
		
	}



	public AuthorizeException(Throwable cause) {
		super(cause);
		
	}



	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
