package com.samtech.finance.dao;

import java.io.Serializable;

import com.samtech.finance.database.TAccount;
import com.samtech.finance.domain.Account;
import com.samtech.hibernate3.impl.AbstractBaseDaoServiceBean;

public class AccountBaseDao extends AbstractBaseDaoServiceBean<Account> {
	
	public Account getObject(Serializable id) {
		TAccount o = this.getObject(TAccount.class, id);
		return this.convertT(o);
	}

	@Override
	protected Account convertT(Object o) {
		if(o==null)return null;
		if(o instanceof TAccount){
			TAccount a=(TAccount)o;
			Account account = new Account();
			account.setId(a.getId());
			account.setInited(a.getInited());
			account.setCreditBalance(a.getCreditBalance());
			account.setDebitBalance(a.getDebitBalance());
			account.setLevel(a.getLevel());
			account.setName(a.getName());
			account.setParentId(a.getParentId());
			return account;
		}
		return (Account)o;
	}
	
	
}
