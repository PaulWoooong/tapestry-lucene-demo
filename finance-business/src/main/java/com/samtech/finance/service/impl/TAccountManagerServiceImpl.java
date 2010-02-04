package com.samtech.finance.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.TAccount;
import com.samtech.finance.database.TAccountHistory;
import com.samtech.finance.domain.Account;
import com.samtech.finance.domain.AccountHistory;
import com.samtech.finance.service.TAccountManagerService;
import com.samtech.hibernate3.impl.AbstractEntityService;

public class TAccountManagerServiceImpl extends AbstractEntityService implements
		TAccountManagerService {
	private Integer synInit = new Integer(2);

	@SuppressWarnings("unchecked")
	public List<Account> findTAccountStatus(final String accName,
			final Integer accountId, final Short status) {

		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				String ql = "select o from "
						+ TAccount.class.getName() + " as o ";
				StringBuffer buf=new StringBuffer();
				if(accName!=null){
					if(buf.length()>0) buf.append(" and ");
					buf.append("o.name=:p_name");
				}
				if(accountId!=null){
					if(buf.length()>0) buf.append(" and ");
					buf.append("o.id=:p_id");
				}
				if(status!=null){
					if(buf.length()>0) buf.append(" and ");
					buf.append("o.inited=:p_init");
				}
				if(buf.length()>0)ql+=" where "+buf.toString();
				Query q = em.createQuery(ql);
				if(accName!=null){
					q.setParameter("p_name", accName.trim());
				}
				if(accountId!=null){
					q.setParameter("p_id", accountId);
				}
				if(status!=null){
					q.setParameter("p_init", status);
				}
				
				List<TAccount> ls = q.getResultList();
				if (ls != null && !ls.isEmpty()) {
					double debits = 0, credits = 0;
					for (TAccount acc : ls) {

						BigDecimal creditBalance = acc.getCreditBalance();
						if (creditBalance != null)
							credits += creditBalance.doubleValue();
						BigDecimal debitBalance = acc.getDebitBalance();
						if (debitBalance != null)
							debits += debitBalance.doubleValue();
						if (acc.getInited() < 1) {
							FinanceRuleException ex = new FinanceRuleException();
							ex.setErrorCode(FinanceRuleException.NO_BALANCE);
							return ex;
						}
					}
					if (debits != credits) {
						FinanceRuleException ex = new FinanceRuleException();
						ex.setErrorCode(FinanceRuleException.NO_BALANCE);
						return ex;
					}
					Calendar cld = Calendar.getInstance();
					cld.set(1980, Calendar.JANUARY, 1);
					for (TAccount acc : ls) {
						acc.setInited((short) 1);
						em.merge(acc);
						TAccountHistory hist = new TAccountHistory();
						hist.setAccountId(acc.getId());
						hist.setCreditBalance(acc.getCreditBalance());
						hist.setDebitBalance(acc.getDebitBalance());
						hist.setInitDate(cld.getTime());
						hist.setLevel(acc.getLevel());
						hist.setName(acc.getName());
						hist.setParentId(acc.getParentId());
						em.merge(hist);
					}

				}

				return null;
			}
		}, true);

		return (List<Account>) o;

	}

	public List<AccountHistory> getAccountHistory(Integer accountId,
			Date startDate, Date endDate) {

		return null;
	}

	public void initAccount() throws FinanceRuleException {

		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("select o from "
						+ TAccount.class.getName() + " as o where o.inited<1");

				synchronized (synInit) {
					List<TAccount> ls = q.getResultList();
					if (ls != null && !ls.isEmpty()) {
						double debits = 0, credits = 0;
						for (TAccount acc : ls) {

							BigDecimal creditBalance = acc.getCreditBalance();
							if (creditBalance != null)
								credits += creditBalance.doubleValue();
							BigDecimal debitBalance = acc.getDebitBalance();
							if (debitBalance != null)
								debits += debitBalance.doubleValue();
							if (acc.getInited() < 1) {
								FinanceRuleException ex = new FinanceRuleException();
								ex
										.setErrorCode(FinanceRuleException.NO_BALANCE);
								return ex;
							}
						}
						if (debits != credits) {
							FinanceRuleException ex = new FinanceRuleException();
							ex.setErrorCode(FinanceRuleException.NO_BALANCE);
							return ex;
						}
						Calendar cld = Calendar.getInstance();
						cld.set(1980, Calendar.JANUARY, 1);
						for (TAccount acc : ls) {
							acc.setInited((short) 1);
							em.merge(acc);
							TAccountHistory hist = new TAccountHistory();
							hist.setAccountId(acc.getId());
							hist.setCreditBalance(acc.getCreditBalance());
							hist.setDebitBalance(acc.getDebitBalance());
							hist.setInitDate(cld.getTime());
							hist.setLevel(acc.getLevel());
							hist.setName(acc.getName());
							hist.setParentId(acc.getParentId());
							em.merge(hist);
						}

					}
				}
				return null;
			}
		}, true);

		if (o != null && o instanceof FinanceRuleException)
			throw (FinanceRuleException) o;

	}

	public Account saveAccount(final Account a) throws FinanceRuleException {
		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				TAccount acc = em.find(TAccount.class, a.getId());
				boolean found = acc != null;
				synchronized (synInit) {
					if (found) {
						short inited = acc.getInited();
						if (inited > 0) {
							FinanceRuleException ex = new FinanceRuleException();
							ex
									.setErrorCode(FinanceRuleException.ACCOUNT_INITED);
							return ex;
						}
						acc.setId(a.getId());
						acc.setCreditBalance(a.getCreditBalance());
						acc.setDebitBalance(a.getDebitBalance());
						acc.setInited((short) 0);
						acc.setLevel(a.getLevel());
						acc.setName(a.getName());
						acc.setParentId(a.getParentId());
						em.merge(acc);
					} else {
						acc = new TAccount();
						acc.setId(a.getId());
						acc.setCreditBalance(a.getCreditBalance());
						acc.setDebitBalance(a.getDebitBalance());
						acc.setInited((short) 0);
						acc.setLevel(a.getLevel());
						acc.setName(a.getName());
						acc.setParentId(a.getParentId());
						em.merge(acc);
					}
				}
				return a;
			}
		}, true);
		if (o != null && o instanceof Account)
			return (Account) o;
		if (o != null && o instanceof FinanceRuleException)
			throw (FinanceRuleException) o;
		return a;
	}

}