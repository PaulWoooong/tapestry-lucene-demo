package com.samtech.finance.service;

import java.util.List;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.domain.AccountBalance;
import com.samtech.finance.domain.BizFinanceRule;

public interface FinanceService {
	/**
	 * 记账规则
	 * @param bizCode
	 * @return
	 */
	public BizFinanceRule getBizFinanceRule(String bizCode);
	/**
	 * 保存记账规则
	 * @param r
	 */
	public void saveBizFinanceRule(BizFinanceRule r);
	/**
	 * 业务记账挂靠
	 * @param bizId
	 * @param items
	 * @throws FinanceRuleException
	 */
	public void pendingBizForm(String bizId,List<AccountBalance> items)throws FinanceRuleException;
	/**
	 * 业务记账核实
	 * @param bizId
	 * @throws FinanceRuleException
	 */
	public void confirmBizBalance(String bizId)throws FinanceRuleException;
	
	public void confirmMonthReport(int year,int month)throws FinanceRuleException;
	
	public void refuseMonthReport(int year,int month)throws FinanceRuleException;
}
