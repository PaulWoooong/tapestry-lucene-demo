package com.samtech.finance.service.impl;

import java.util.List;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.domain.AccountBalance;
import com.samtech.finance.domain.BizFinanceRule;
import com.samtech.finance.service.FinanceService;
import com.samtech.hibernate3.impl.AbstractEntityService;

public class FinanceServiceImpl extends AbstractEntityService implements FinanceService{

	public void confirmBizBalance(String bizId) throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}

	public BizFinanceRule getBizFinanceRule(String bizCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pendingBizForm(String bizId, List<AccountBalance> items)
			throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}

	public void saveBizFinanceRule(BizFinanceRule r) {
		// TODO Auto-generated method stub
		
	}

}
