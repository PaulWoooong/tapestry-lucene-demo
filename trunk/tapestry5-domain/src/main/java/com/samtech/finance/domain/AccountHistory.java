package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.samtech.finance.database.BalanceDirect;

public class AccountHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023024113696887768L;
	private String financeId;
	private Integer accountId;
	private BigDecimal creditBalance;
	private BigDecimal debitBalance;
	private BigDecimal amount;
	private BalanceDirect direct;
	private Date bizDate;

	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getCreditBalance() {
		return creditBalance;
	}

	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	public BigDecimal getDebitBalance() {
		return debitBalance;
	}

	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
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

	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((financeId == null) ? 0 : financeId.hashCode());
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
		AccountHistory other = (AccountHistory) obj;
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
		return true;
	}

}
