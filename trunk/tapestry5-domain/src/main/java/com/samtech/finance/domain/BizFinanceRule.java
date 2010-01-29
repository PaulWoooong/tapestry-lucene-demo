package com.samtech.finance.domain;

import java.io.Serializable;
import java.util.List;

public class BizFinanceRule implements Serializable{
	/**
	 *业务单号 
	 */
	private String bizId;
	
	List<BalanceItem> credit;
	
	List<BalanceItem> debit;

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public List<BalanceItem> getCredit() {
		return credit;
	}

	public void setCredit(List<BalanceItem> credit) {
		this.credit = credit;
	}

	public List<BalanceItem> getDebit() {
		return debit;
	}

	public void setDebit(List<BalanceItem> debit) {
		this.debit = debit;
	}
	
	
	
}
