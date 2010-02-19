package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.samtech.finance.database.AccountStatus;

public class FinanceForms implements Serializable {


	
	private String id;
	
	private Date bizDate;
	
	private String businessId;
	
	private String context;
	
	private BigDecimal amount;
	
	private AccountStatus Status = AccountStatus.PENDING;
	
	private Date confirmDate;
	
	
	private List<BalanceItem> debits;
	
	private List<BalanceItem> credits;

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

	public List<BalanceItem> getDebits() {
		if(debits==null){
			debits=new ArrayList<BalanceItem>(5);
		}
		return debits;
	}

	public void setDebits(List<BalanceItem> debits) {
		this.debits = debits;
	}

	public List<BalanceItem> getCredits() {
		if(credits==null){
			credits=new ArrayList<BalanceItem>(5);
		}
		return credits;
	}

	public void setCredits(List<BalanceItem> credits) {
		this.credits = credits;
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
		FinanceForms other = (FinanceForms) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
}
