package com.samtech.finance.service;

import java.util.Date;
import java.util.List;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.domain.Account;
import com.samtech.finance.domain.AccountHistory;

public interface TAccountManagerService {
	/*
	 * juest new or not inited can be save.
	 */
	Account saveAccount(Account a)throws FinanceRuleException;
	/**
	 * init account will be check balance.
	 * @throws FinanceRuleException
	 */
	void initAccount()throws FinanceRuleException;
	
	List<AccountHistory> getAccountHistory(Integer accountId,Date startDate,Date endDate);
	/**
	 * include lastmonth balance
	 * @param accName
	 * @param accountId
	 * @param status
	 * @return
	 */
	List<Account> findTAccountStatus(String accName, Integer accountId, Short status);
	
	
}
