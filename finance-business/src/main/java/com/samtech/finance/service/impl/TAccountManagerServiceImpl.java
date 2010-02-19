package com.samtech.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.orm.jpa.JpaCallback;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.database.FinanceLevel;
import com.samtech.finance.database.RunningAccount;
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
				List<Account> results=new ArrayList<Account>(20);
				List<TAccount> ls = q.getResultList();
				Query query = em.createQuery("select o from "+TAccountHistory.class.getName()+" as o order by o.initDate desc");
				query.setMaxResults(500);
				List<TAccountHistory> hists = query.getResultList();
				if (ls != null && !ls.isEmpty()) {
					for (TAccount account : ls) {
						short inited = account.getInited();
						Account a=new Account();
						a.setId(account.getId());
						a.setInited(inited);
						a.setLevel(account.getLevel());
						a.setName(account.getName());
						a.setParentId(account.getParentId());
						Date initDate=null;
						double debit=0,credit=0;
						if(inited>0){//inited
							for(int k=0;k<hists.size();k++){
								TAccountHistory hist = hists.get(k);
								if(hist.getAccountId()!=null && hist.getAccountId().equals(account.getId())){
									BigDecimal debitBalance = hist.getDebitBalance();
									BigDecimal creditBalance = hist.getCreditBalance();
									//last month balance
									
									if(debitBalance!=null && creditBalance!=null){
										debit=debitBalance.doubleValue();
										credit=creditBalance.doubleValue();
										if(debit>credit){
											debit=debit-credit;
											credit=0;
										}else{
											credit=credit-debit;
											debit=0;
										}
									}
									a.setLastMonthDebitBalance(debitBalance);
									a.setLastMonthCreditBalance(creditBalance);
									initDate= hist.getInitDate();
									a.setLastDate(initDate);
								}
							}
						}else{
							BigDecimal creditBalance = account.getCreditBalance();
							BigDecimal debitBalance = account.getDebitBalance();
							if(debitBalance!=null)
							debit=debitBalance.doubleValue();
							if(creditBalance!=null)credit=creditBalance.doubleValue();
						}
							
						
						String qlString = "select o from "+RunningAccount.class.getName()+" as o where o.accountId=:p_accid ";
						if(initDate!=null){
							qlString+=" and o.createDate>=:p_cd";
						}
						query = em.createQuery(qlString);
						query.setParameter("p_accid", account.getId());
						if(initDate!=null){
							query.setParameter("p_cd", initDate,TemporalType.DATE);
						}
						List<RunningAccount> runnings = query.getResultList();
						if(runnings!=null && !runnings.isEmpty()){
							for (RunningAccount r : runnings) {
								if(r.getAccountId().equals(account.getId())){
									BalanceDirect direct = r.getDirect();
									if(direct!=null && direct.equals(BalanceDirect.DEBIT))
										debit+=r.getAmount().doubleValue();
									if(direct!=null && direct.equals(BalanceDirect.CREDIT))
										credit+=r.getAmount().doubleValue();
								}
							}
						}
						BigDecimal cDecimal = new BigDecimal(credit);
						a.setCreditBalance(cDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
						cDecimal = new BigDecimal(debit);
						a.setDebitBalance(cDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
						results.add(a);
					}
				}

				return results;
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
							/*if (acc.getInited() < 1) {
								FinanceRuleException ex = new FinanceRuleException();
								ex
										.setErrorCode(FinanceRuleException.NO_BALANCE);
								return ex;
							}*/
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
						if(a.getLevel()!=null && a.getLevel().equals(FinanceLevel.TWO)){
							if(a.getParentId()==null){
								FinanceRuleException ex = new FinanceRuleException("没设上级科目");
								ex.setErrorCode(FinanceRuleException.UNKNOW);
								return ex;
							}
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

	public Account getAccountById(final Integer id) {
		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				TAccount a = em.find(TAccount.class, id);
				boolean found = a != null;
				if(found){
				Account acc = new Account();
						acc.setId(a.getId());
						acc.setCreditBalance(a.getCreditBalance());
						acc.setDebitBalance(a.getDebitBalance());
						acc.setInited(a.getInited());
						acc.setLevel(a.getLevel());
						acc.setName(a.getName());
						acc.setParentId(a.getParentId());
						return acc;
				}
				return null;
			}
		}, true);
		if (o != null && o instanceof Account)
			return (Account) o;
		
		return null;
	}

	public void deleteAccount(final Integer id) throws FinanceRuleException {

		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				TAccount a = em.find(TAccount.class, id);
				boolean found = a != null;
				if(found){
					synchronized (synInit) {
						if(a.getInited()>0 ){
							FinanceRuleException ex = new FinanceRuleException("科目已经使用");
							ex.setErrorCode(FinanceRuleException.UNKNOW);
							return ex;
						}
						if(a.getLevel()!=null && a.getLevel().equals(FinanceLevel.ONE)){
							Query q = em.createQuery("select o from "+TAccount.class.getName()+" as o where o.parentId="+a.getId());
							List lst = q.getResultList();
							if(lst!=null && !lst.isEmpty()){
								FinanceRuleException ex = new FinanceRuleException("子科目没有删除，不能删除些科目");
								ex.setErrorCode(FinanceRuleException.UNKNOW);
								return ex;
							}
						}
						
					}
				}
				return null;
			}
		}, true);
		

	
	}

	public List<Account> findTAccountlike(final String accName) {

		Object o = this.getJpaTemplate().execute(new JpaCallback() {

			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String ql = "select o from "
						+ TAccount.class.getName() + " as o ";
				StringBuffer buf=new StringBuffer();
				if(accName!=null){
					if(buf.length()>0) buf.append(" and ");
					buf.append("(o.name like :p_name");
					buf.append(" or convert(o.id,char(6)) like :p_id )");
				}
				
				if(buf.length()>0)ql+=" where "+buf.toString();
				Query q = em.createQuery(ql);
				if(accName!=null){
					q.setParameter("p_name", accName.trim()+"%");
					q.setParameter("p_id", accName.trim()+"%");
				}
				
				List<Account> results=new ArrayList<Account>(20);
				List<TAccount> ls = q.getResultList();
				if(ls!=null && !ls.isEmpty()){
					for (TAccount account : ls) {
						Account a=new Account();
						a.setId(account.getId());
						a.setInited(account.getInited());
						a.setLevel(account.getLevel());
						a.setName(account.getName());
						a.setParentId(account.getParentId());
						results.add(a);
					}
				}
				return results;
			}
		}, true);

		return (List<Account>) o;

	}

}
