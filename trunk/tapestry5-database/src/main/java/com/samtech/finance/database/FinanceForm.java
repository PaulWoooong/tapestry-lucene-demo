package com.samtech.finance.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="finance_form")
public class FinanceForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3549555386853671230L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="id",length=10)
	private String id;
	@Column(name="biz_date")
	@Temporal(TemporalType.DATE)
	private Date bizDate;
	@Column(name = "business", nullable = true,length=30)
	private String businessId;
	@Column(name="context",length=300)
	private String context;
	
	@Column(name = "amount", scale = 2, nullable = false)
	private BigDecimal amount;
	
	@Column(name = "account_status")
	private AccountStatus Status = AccountStatus.PENDING;
	@Column(name="confirm_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public AccountStatus getStatus() {
		return Status;
	}
	public void setStatus(AccountStatus status) {
		Status = status;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
	
	
	
}
