package com.samtech.finance.dao;

import java.io.Serializable;

import com.samtech.finance.database.RunningAccount;
import com.samtech.hibernate3.impl.AbstractBaseDaoServiceBean;

public class RunningAccountBaseDao extends AbstractBaseDaoServiceBean<RunningAccount> {
	
	public RunningAccount getObject(Serializable id) {
		return this.getObject(RunningAccount.class, id);
	}
	
	
}
