package com.samtech.finance.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.samtech.finance.database.BalanceDirect;



public class AccountData implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 8680575846630312273L;
private Integer accountId;
private String accountName;
private List<String> formIds=new ArrayList<String>();
private int forms=-1;
private BigDecimal debit;
private BigDecimal credit;

public AccountData() {
	super();
}
public AccountData(Integer accountId) {
	super();
	this.accountId = accountId;
}

public void addForms(Integer accountId,String formId,BalanceDirect direct,BigDecimal amount){
	if(accountId!=null && accountId.equals(this.accountId)){
		if(formId!=null){
			if(!formIds.contains(formId.trim()))
				formIds.add(formId.trim());
		}
		if(direct!=null && amount!=null){
			if(debit==null){
				debit=new BigDecimal(0d);
				debit=debit.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			if(credit==null){
				credit=new BigDecimal(0d);
				credit=credit.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			if(direct.equals(BalanceDirect.DEBIT)){
				debit=debit.add(amount);
			}
			if(direct.equals(BalanceDirect.CREDIT)){
				credit=credit.add(amount);
			}
		}
	}
}
public Integer getAccountId() {
	return accountId;
}
public void setAccountId(Integer accountId) {
	this.accountId = accountId;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
public void setForms(int c){
	this.forms=c;
}
public int getForms() {
	if(forms<0)
	return formIds.size();
	return forms;
}
public BigDecimal getDebit() {
	if(debit!=null){
		if(debit.scale()!=2)
			debit.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	return debit;
}
public BigDecimal getCredit() {
	if(credit!=null){
		if(credit.scale()!=2)
			credit.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	return credit;
}
public void setDebit(BigDecimal debit) {
	this.debit = debit;
}
public void setCredit(BigDecimal credit) {
	this.credit = credit;
}
public String getAccountName() {
	return accountName;
}
public void setAccountName(String accountName) {
	this.accountName = accountName;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
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
	AccountData other = (AccountData) obj;
	if (accountId == null) {
		if (other.accountId != null)
			return false;
	} else if (!accountId.equals(other.accountId))
		return false;
	return true;
}


}
