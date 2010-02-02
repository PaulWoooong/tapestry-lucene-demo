package com.samtech.finance.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="biz_account_rule")
public class BusinessAccountRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1680260483117452675L;
	@Id
	Integer id;
	@Column(name="business_type",length=10)
	String bizType;
	@Column(name="account_id")
	Integer accountId;
	@Enumerated(EnumType.STRING)
	BalanceDirect direct;
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public BalanceDirect getDirect() {
		return direct;
	}
	public void setDirect(BalanceDirect direct) {
		this.direct = direct;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BusinessAccountRule other = (BusinessAccountRule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
