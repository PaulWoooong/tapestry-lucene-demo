package com.samtech.common.domain;

import java.io.Serializable;

public class ParamValue implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;
	private int type;// java.sql.Types;
	
	
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
}
