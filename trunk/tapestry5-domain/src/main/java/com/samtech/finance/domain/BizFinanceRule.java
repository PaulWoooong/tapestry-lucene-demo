package com.samtech.finance.domain;

import java.io.Serializable;
import java.util.List;

public class BizFinanceRule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6025288353833331435L;

	/**
	 *业务类型 
	 */
	private String bizId;
	
	List<RuleItem> credits;
	
	List<RuleItem> debits;

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public List<RuleItem> getCredits() {
		return credits;
	}

	public void setCredits(List<RuleItem> credit) {
		this.credits = credit;
	}

	public List<RuleItem> getDebits() {
		return debits;
	}

	public void setDebits(List<RuleItem> debit) {
		this.debits = debit;
	}
	
	
	
}
