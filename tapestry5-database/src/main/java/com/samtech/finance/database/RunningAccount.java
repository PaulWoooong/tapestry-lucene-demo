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
	@Id
	private Integer id;
	@Column(name = "account_id", nullable = false, updatable = false)
	private Integer accountId;
	@Column(name="content")
	private String content;
	
	

	@Enumerated(EnumType.STRING)
	@Column(name = "direct", updatable = false)
	private BalanceDirect direct = BalanceDirect.DEBIT;
	@Column(name = "amount", scale = 2, nullable = false)
	private BigDecimal amount;
	@Column(name = "business", nullable = false,length=30)
	private String businessId;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_status")
	private AccountStatus Status = AccountStatus.PENDING;
	@Column(name = "company_id", length = 30)
	private String companyId;
	@Column(name = "create_date", updatable = false, nullable = false)
	private Date createDate;
	@Column(name = "confirm_date")
	private Date confirmDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public AccountStatus getStatus() {
		return Status;
	}

	public void setStatus(AccountStatus status) {
		Status = status;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date insertDate) {
		this.createDate = insertDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
