package com.samtech.finance.dao;

import java.io.Serializable;

import com.samtech.finance.database.TAccount;
import com.samtech.hibernate3.impl.AbstractBaseDaoServiceBean;

public class TAccountBaseDao extends AbstractBaseDaoServiceBean<TAccount> {
	
	public TAccount getObject(Serializable id) {
		return this.getObject(TAccount.class, id);
	}
	
	
}
