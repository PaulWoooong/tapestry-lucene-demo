package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

import com.samtech.finance.database.FinanceLevel;

public class Account implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7338273290694408393L;
	
	private Integer id;
	
	
	private String name;
	
	private BigDecimal creditBalance;
	
	private BigDecimal debitBalance;
	private BigDecimal lastMonthDebitBalance;
	private BigDecimal lastMonthCreditBalance;
	
	private FinanceLevel level=FinanceLevel.ONE;
	/*0/1 */
	@Column(name="init_flag",length=1)
	private short inited;
	
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
	
	public short getInited() {
		return inited;
	}
	public void setInited(short inited) {
		this.inited = inited;
	}
	
	
	public BigDecimal getLastMonthDebitBalance() {
		return lastMonthDebitBalance;
	}
	public void setLastMonthDebitBalance(BigDecimal lastMonthDebitBalance) {
		this.lastMonthDebitBalance = lastMonthDebitBalance;
	}
	public BigDecimal getLastMonthCreditBalance() {
		return lastMonthCreditBalance;
	}
	public void setLastMonthCreditBalance(BigDecimal lastMonthCreditBalance) {
		this.lastMonthCreditBalance = lastMonthCreditBalance;
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
		Account other = (Account) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
