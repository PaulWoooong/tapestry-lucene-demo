package com.samtech.finance.database;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_account")
public class TAccount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338273290694408393L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Integer id;
	
	@Column(length=30,name="name",nullable=false)
	private String name;
	@Column(scale=2,name="credit_balance",nullable=false)
	private BigDecimal creditBalance;
	@Column(scale=2,name="debit_balance",nullable=false)
	private BigDecimal debitBalance;
	@Enumerated(EnumType.STRING)
	private FinanceLevel level=FinanceLevel.ONE;
	@Column(name="parent_id")
	private Integer parentId;
	
	/**
	 * 主键科目代码
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 科目名称
	 * @return
	 */
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
		TAccount other = (TAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
