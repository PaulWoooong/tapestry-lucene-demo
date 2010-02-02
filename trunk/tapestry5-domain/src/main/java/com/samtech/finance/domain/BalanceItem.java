package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.samtech.finance.database.BalanceDirect;

public class BalanceItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2228489119474657168L;
	private Integer financeId;
	private BigDecimal amount;
	private BalanceDirect direct;
	private String companyId;
	private String context;
	
	public Integer getFinanceId() {
		return financeId;
	}
	public void setFinanceId(Integer financeId) {
		this.financeId = financeId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direct == null) ? 0 : direct.hashCode());
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
		BalanceItem other = (BalanceItem) obj;
		if (direct == null) {
			if (other.direct != null)
				return false;
		} else if (!direct.equals(other.direct))
			return false;
		if (financeId == null) {
			if (other.financeId != null)
				return false;
		} else if (!financeId.equals(other.financeId))
			return false;
		return true;
	}
	
	
	
}
