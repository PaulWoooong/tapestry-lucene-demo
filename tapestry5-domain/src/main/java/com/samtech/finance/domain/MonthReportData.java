package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MonthReportData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2945293120457480757L;
	private int inited;
	private int totalForms;
	private int year;
	private int month;
	private BigDecimal debit;
	private BigDecimal credit;
	private List<AccountData> accounts=new ArrayList<AccountData>(8);
	
	
	public int getInited() {
		return inited;
	}
	public void setInited(int inited) {
		this.inited = inited;
	}
	public int getTotalForms() {
		return totalForms;
	}
	public void setTotalForms(int totalForms) {
		this.totalForms = totalForms;
	}
	public BigDecimal getDebit() {
		if(debit==null)initAmonts();
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		if(credit==null)initAmonts();
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	private synchronized void initAmonts(){
		double d=0d;
		double c=0d;
		if(accounts!=null && !accounts.isEmpty()){
			for (AccountData item : accounts) {
				BigDecimal db = item.getDebit();
				if(db!=null )d+=db.doubleValue();
				db=item.getCredit();
				if(db!=null )c+=db.doubleValue();
			}
		}
		BigDecimal tmp = new BigDecimal(d);
		this.debit=tmp.setScale(2, BigDecimal.ROUND_HALF_UP);
		tmp=new BigDecimal(c);
		this.credit=tmp.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	
	public List<AccountData> getAccounts() {
		return accounts;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	public void addForm(FinanceForms form){
		if(form!=null){
			totalForms++;
			String id = form.getId();
			List<BalanceItem> debits = form.getDebits();
			List<BalanceItem> credits = form.getCredits();
			if(debits!=null && !debits.isEmpty()){
				for (BalanceItem item : debits) {
					Integer financeId = item.getFinanceId();
					if(financeId!=null){
						AccountData accountData = getAccountData(financeId);
						if(accountData!=null)accountData.addForms(financeId, id, item.getDirect(), item.getAmount());
					}
				}
			}
			if(credits!=null && !credits.isEmpty()){
				for (BalanceItem item : credits) {
					Integer financeId = item.getFinanceId();
					if(financeId!=null){
						AccountData accountData = getAccountData(financeId);
						if(accountData!=null)accountData.addForms(financeId, id, item.getDirect(), item.getAmount());
					}
				}
			}
		}
		
	}
	
	private AccountData getAccountData(Integer accId) {
		for (AccountData item : accounts) {
			if(item.getAccountId().equals(accId))
				return item;
		}
		AccountData accountData = new AccountData(accId);
		accounts.add(accountData);
		return accountData;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + month;
		result = prime * result + year;
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
		MonthReportData other = (MonthReportData) obj;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	
}
