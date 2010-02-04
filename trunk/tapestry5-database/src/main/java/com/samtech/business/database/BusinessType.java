package com.samtech.business.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "business_type")
@GenericGenerator(name="c_biz_code",strategy="assigned")
public class BusinessType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3270584648155299998L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO,generator="c_biz_code")
	@Column(name = "business_code", length = 10)
	private String bizCode;
	@Column(name = "description", length = 256,nullable=false)
	private String description;
	@Column(name = "parent_id", length = 10)
	private String parentId;
	@Column(name="create_date",nullable=false)
	private Date createDate;
	
	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bizCode == null) ? 0 : bizCode.hashCode());
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
		BusinessType other = (BusinessType) obj;
		if (bizCode == null) {
			if (other.bizCode != null)
				return false;
		} else if (!bizCode.equals(other.bizCode))
			return false;
		return true;
	}

}
