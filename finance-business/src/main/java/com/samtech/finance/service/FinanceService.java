package com.samtech.finance.service;

import java.util.Date;
import java.util.List;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.domain.BizFinanceRule;
import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.domain.RunningAccountHistory;

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
	public void pendingBizForm(FinanceForms form)throws FinanceRuleException;
	/**
	 * 业务记账核实
	 * @param bizId
	 * @throws FinanceRuleException
	 */
	public void confirmBizBalance(String bizId)throws FinanceRuleException;
	
	public void confirmMonthReport(int year,int month)throws FinanceRuleException;
	
	public void refuseMonthReport(int year,int month)throws FinanceRuleException;
	/**
	 * 
	 * @param financeid
	 * @throws FinanceRuleException
	 */
	public void deleteFinanceForm(String financeid)throws FinanceRuleException;
	/**
	 * 
	 * @param financeformId
	 * @param bizId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<FinanceForms> findFinanceForms(String financeformId, String bizId,
			Date startDate, Date endDate);

	public List<RunningAccountHistory> findRunningAccount(String financeformId, Integer accountId,
			String content,List<AccountStatus> statuses, Date startDate, Date endDate);

}
