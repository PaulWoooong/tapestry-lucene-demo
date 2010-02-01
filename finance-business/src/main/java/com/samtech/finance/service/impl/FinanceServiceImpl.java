package com.samtech.finance.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.database.RunningAccount;
import com.samtech.finance.database.TAccount;
import com.samtech.finance.domain.AccountBalance;
import com.samtech.finance.domain.BizFinanceRule;
import com.samtech.finance.service.FinanceService;
import com.samtech.hibernate3.impl.AbstractEntityService;

public class FinanceServiceImpl extends AbstractEntityService implements FinanceService{
protected static final Integer STATUS_ERROR = new Integer(20001);
protected static final Integer BALANCE_ERROR = new Integer(20002);
private static Integer synT=new Integer(1);
	public void confirmBizBalance(final String bizId) throws FinanceRuleException {
		this.getJpaTemplate().execute(new JpaCallback() {
			
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.business=:p_bizid");
				query.setParameter("p_bizid", bizId);
				List<RunningAccount> resultList = query.getResultList();
				synchronized (synT) {
					//FIXME CHECK PRV MONTH IS INITLIZATIONED.
					if(resultList!=null && !resultList.isEmpty()){
						double debit=0d,credit=0d;
						
						for (RunningAccount ra : resultList) {
							BalanceDirect direct = ra.getDirect();
							AccountStatus status = ra.getStatus();
							if(status!=null && (status.equals(AccountStatus.NORMAL)||
									status.equals(AccountStatus.REBACK)))return STATUS_ERROR;
							if(direct.equals(BalanceDirect.DEBIT)){
								debit+=ra.getAmount().doubleValue();
							}else{
								credit+=ra.getAmount().doubleValue();
							}
						}
						if(debit!=credit)return BALANCE_ERROR;
						
						for (RunningAccount ra : resultList) {
							Integer accountId = ra.getAccountId();
							TAccount t = em.find(TAccount.class, accountId);
							BalanceDirect direct = ra.getDirect();
							if(direct.equals(BalanceDirect.DEBIT)){
								BigDecimal debitBalance = t.getDebitBalance();
								if(debitBalance!=null){
									BigDecimal add = debitBalance.add(ra.getAmount());
									t.setDebitBalance(add.setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
							if(direct.equals(BalanceDirect.CREDIT)){
								BigDecimal debitBalance = t.getCreditBalance();
								if(debitBalance!=null){
									BigDecimal add = debitBalance.add(ra.getAmount());
									t.setCreditBalance(add.setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
							if(ra.getStatus().equals(AccountStatus.PENDING))
							ra.setStatus(AccountStatus.NORMAL);
							if(ra.getStatus().equals(AccountStatus.PRREBACK))
								ra.setStatus(AccountStatus.REBACK);
							em.merge(ra);
							em.merge(t);
						}
					}
				}
				return null;
			}
		}, true);
		
	}

	public BizFinanceRule getBizFinanceRule(String bizCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pendingBizForm(final String bizId, List<AccountBalance> items)
			throws FinanceRuleException {
		this.getJpaTemplate().execute(new JpaCallback() {
			
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query query = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.business=:p_bizid");
				query.setParameter("p_bizid", bizId);
				List<RunningAccount> resultList = query.getResultList();
				synchronized (synT) {
					//FIXME CHECK PRV MONTH IS INITLIZATIONED.
					if(resultList!=null && !resultList.isEmpty()){
						double debit=0d,credit=0d;
						
						for (RunningAccount ra : resultList) {
							BalanceDirect direct = ra.getDirect();
							AccountStatus status = ra.getStatus();
							if(status!=null && (status.equals(AccountStatus.NORMAL)||
									status.equals(AccountStatus.REBACK)))return STATUS_ERROR;
							if(direct.equals(BalanceDirect.DEBIT)){
								debit+=ra.getAmount().doubleValue();
							}else{
								credit+=ra.getAmount().doubleValue();
							}
						}
						if(debit!=credit)return BALANCE_ERROR;
						
						for (RunningAccount ra : resultList) {
							Integer accountId = ra.getAccountId();
							TAccount t = em.find(TAccount.class, accountId);
							BalanceDirect direct = ra.getDirect();
							if(direct.equals(BalanceDirect.DEBIT)){
								BigDecimal debitBalance = t.getDebitBalance();
								if(debitBalance!=null){
									BigDecimal add = debitBalance.add(ra.getAmount());
									t.setDebitBalance(add.setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
							if(direct.equals(BalanceDirect.CREDIT)){
								BigDecimal debitBalance = t.getCreditBalance();
								if(debitBalance!=null){
									BigDecimal add = debitBalance.add(ra.getAmount());
									t.setCreditBalance(add.setScale(2, BigDecimal.ROUND_HALF_UP));
								}
							}
							if(ra.getStatus().equals(AccountStatus.PENDING))
							ra.setStatus(AccountStatus.NORMAL);
							if(ra.getStatus().equals(AccountStatus.PRREBACK))
								ra.setStatus(AccountStatus.REBACK);
							em.merge(ra);
							em.merge(t);
						}
					}
				}
				return null;
			}
		}, true);
		
	}

	public void saveBizFinanceRule(BizFinanceRule r) {
		
		
	}

	public void confirmMonthReport(int year, int month)
			throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}

	public void refuseMonthReport(int year, int month)
			throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}

}
