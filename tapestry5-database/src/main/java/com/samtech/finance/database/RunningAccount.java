package com.samtech.finance.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "running_account")
public class RunningAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1335332172333428991L;
	
	private Integer id;
	
	private Integer accountId;
	
	private String context;
	
	
	private BalanceDirect direct = BalanceDirect.DEBIT;
	
	private BigDecimal amount;
	/**
	 *financeform id 
	 *
	 */
	
	private String businessId;

	
	
	private String companyId;
	
	private Date createDate;
	
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "account_id", nullable = false, updatable = false)
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	@Column(name = "direct", updatable = false)
	@Enumerated(value=EnumType.STRING)
	public BalanceDirect getDirect() {
		return direct;
	}

	public void setDirect(BalanceDirect direct) {
		this.direct = direct;
	}
	@Column(name = "amount", scale = 2, nullable = false)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name = "business", nullable = false,length=30)
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	
	@Column(name = "company_id", length = 30)
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	@Column(name = "create_date", updatable = false, nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date insertDate) {
		this.createDate = insertDate;
	}

	
	@Column(name="context",length=100)
	public String getContext() {
		return context;
	}

	public void setContext(String content) {
		this.context = content;
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
		RunningAccount other = (RunningAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
