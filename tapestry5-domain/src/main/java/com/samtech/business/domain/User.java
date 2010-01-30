package com.samtech.business.domain;

import java.io.Serializable;
import java.util.Date;

import com.samtech.common.domain.IUser;

public class User implements Serializable,IUser{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6839373319527668518L;

	private String employeeId;
	
	private String education;
	
	private String professionTitle;
	
	private Date createDate;
	
	private String password;
	
	private Date expireDate;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getProfessionTitle() {
		return professionTitle;
	}
	public void setProfessionTitle(String professionTitle) {
		this.professionTitle = professionTitle;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeId == null) ? 0 : employeeId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}
	public Serializable getId() {
		return this.employeeId;
	}
	public String getUserName() {
		return this.getEmployeeId();
		
	}
	
	
	


}
