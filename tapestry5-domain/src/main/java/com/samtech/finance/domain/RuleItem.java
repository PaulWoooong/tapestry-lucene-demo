package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.samtech.finance.database.BalanceDirect;

public class RuleItem implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7673367505375955053L;

	private Integer financeId;
	
	private BalanceDirect direct;
	Date createDate;
	
	public Integer getFinanceId() {
		return financeId;
	}
	public void setFinanceId(Integer financeId) {
		this.financeId = financeId;
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
		RuleItem other = (RuleItem) obj;
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
