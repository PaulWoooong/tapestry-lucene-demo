package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.samtech.finance.database.BalanceDirect;

public class RunningAccountHistory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2872467283356042863L;
	private Integer id;
	private Integer financeId;
	private Integer accountId;
	private BigDecimal amount;
	private BalanceDirect direct;
	private String companyId;
	private String context;
	private Date bizDate;
	private String className;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFinanceId() {
		return financeId;
	}
	public void setFinanceId(Integer financeId) {
		this.financeId = financeId;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BalanceDirect getDirect() {
		return direct;
	}
	public void setDirect(BalanceDirect direct) {
		this.direct = direct;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getBizDate() {
		return bizDate;
	}
	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((financeId == null) ? 0 : financeId.hashCode());
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
		RunningAccountHistory other = (RunningAccountHistory) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (financeId == null) {
			if (other.financeId != null)
				return false;
		} else if (!financeId.equals(other.financeId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
