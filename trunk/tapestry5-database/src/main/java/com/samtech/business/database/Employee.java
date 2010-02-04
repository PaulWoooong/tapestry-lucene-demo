package com.samtech.business.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employee")
@GenericGenerator(name = "employee_gid", strategy = "assigned")
public class Employee extends AbstractPerson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 334005813599435792L;

	private String employeeId;

	private String education;

	private String professionTitle;

	private Date createDate;

	private String password;

	private Date expireDate;

	private String name;

	private Gender gender = Gender.MALE;

	private Date birthDay;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "employee_gid")
	@Column(name = "employee_id", length = 10)
	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Column(name = "education", length = 30)
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@Column(name = "profession_title", length = 30)
	public String getProfessionTitle() {
		return professionTitle;
	}

	public void setProfessionTitle(String professionTitle) {
		this.professionTitle = professionTitle;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "password", length = 30)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "expire_date")
	@Temporal(TemporalType.DATE)
	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	@Column(name = "name", length = 30, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "gender", length = 10)
	@Enumerated(value = EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Column(name = "birth_day")
	@Temporal(TemporalType.DATE)
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
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
		Employee other = (Employee) obj;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		return true;
	}

}
