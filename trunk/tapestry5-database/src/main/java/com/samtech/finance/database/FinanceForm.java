package com.samtech.finance.database;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="finance_form")
@GenericGenerator(name="c_financeform_id",strategy="assigned")
public class FinanceForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3549555386853671230L;
	
	private String id;
	
	private Date bizDate;
	
	private String businessId;
	
	private String context;
	
	
	private BigDecimal amount;
	
	
	private AccountStatus Status = AccountStatus.PENDING;
	
	private Date confirmDate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="c_financeform_id")
	@Column(name="id",length=12)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="biz_date")
	@Temporal(TemporalType.DATE)
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	@Column(name = "business", nullable = true,length=30)
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	@Column(name="context",length=300)
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@Column(name = "amount", scale = 2, nullable = false)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name = "account_status")
	@Enumerated(value=EnumType.STRING)
	public AccountStatus getStatus() {
		return Status;
	}
	public void setStatus(AccountStatus status) {
		Status = status;
	}
	@Column(name="confirm_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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
		FinanceForm other = (FinanceForm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
}
