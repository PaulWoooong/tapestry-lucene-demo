package com.samtech.finance.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.samtech.finance.database.BalanceDirect;

public class RunningHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023024113696887768L;
	private Long id;
	private String financeId;
	private Integer accountId;
	private BigDecimal amount;
	private BalanceDirect direct;
	private Date bizDate;
	private String context;
	
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="finance_id",length=30,nullable=false)
	public String getFinanceId() {
		return financeId;
	}

	public void setFinanceId(String financeId) {
		this.financeId = financeId;
	}
	@Column(name="acc_id",nullable=false)
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@Column(name="amount",scale=2)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name="direct")
	@Enumerated(value=EnumType.STRING)
	public BalanceDirect getDirect() {
		return direct;
	}

	public void setDirect(BalanceDirect direct) {
		this.direct = direct;
	}
	@Column(name="biz_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	public Date getBizDate() {
		return bizDate;
	}

	public void setBizDate(Date bizDate) {
		this.bizDate = bizDate;
	}
	@Column(name="context",length=100)
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
		RunningHistory other = (RunningHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}
