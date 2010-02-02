package com.samtech.business.dao;

import java.io.Serializable;

import com.samtech.business.database.BusinessType;
import com.samtech.hibernate3.impl.AbstractBaseDaoServiceBean;

public class BusinessTypeBaseDao extends AbstractBaseDaoServiceBean<BusinessType> {
	
	public BusinessType getObject(Serializable id) {
		return this.getObject(BusinessType.class, id);
	}

	@Override
	protected BusinessType convertT(Object o) {
		return (BusinessType)o;
	}
	
	
}
