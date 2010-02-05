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

@Entity
@Table(name="taccount_history")
public class TAccountHistory implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1884898280599913673L;
	
	
	
	private Integer id;
	
	private Integer accountId;
	
	private String name;
	
	private BigDecimal creditBalance;
	
	private BigDecimal debitBalance;
	
	private FinanceLevel level=FinanceLevel.ONE;
		
	
	private Integer parentId;
	
	private Date initDate;
	
	
	/**
	 * 主键
	 * @return
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 科目代码
	 * @return
	 */
	@Column(name="account_id")
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	/**
	 * 科目名称
	 * @return
	 */
	@Column(length=30,name="name",nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 贷方金额
	 * @return
	 */
	@Column(scale=2,name="credit_balance",nullable=false)
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}
	/**
	 * 借方金额
	 * @return
	 */
	@Column(scale=2,name="debit_balance",nullable=false)
	public BigDecimal getDebitBalance() {
		return debitBalance;
	}
	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}
	/**
	 * 科目级别 {@link FinanceLevel}
	 * @return
	 */
	@Enumerated(value=EnumType.STRING)
	public FinanceLevel getLevel() {
		return level;
	}
	public void setLevel(FinanceLevel level) {
		this.level = level;
	}
	/**
	 * 上级科目代码
	 * @return
	 */
	@Column(name="parent_id")
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	@Column(name="init_date")
	@Temporal(TemporalType.DATE)
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
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
		TAccountHistory other = (TAccountHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
