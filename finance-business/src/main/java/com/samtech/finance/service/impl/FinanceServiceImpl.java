package com.samtech.finance.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.jpa.JpaCallback;

import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.database.BusinessAccountRule;
import com.samtech.finance.database.FinanceForm;
import com.samtech.finance.database.RunningAccount;
import com.samtech.finance.database.TAccount;
import com.samtech.finance.database.TAccountHistory;
import com.samtech.finance.domain.BalanceItem;
import com.samtech.finance.domain.BizFinanceRule;
import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.domain.RuleItem;
import com.samtech.finance.service.FinanceService;
import com.samtech.hibernate3.impl.AbstractEntityService;

public class FinanceServiceImpl extends AbstractEntityService implements FinanceService{
protected static final Integer STATUS_ERROR = new Integer(20001);
protected static final Integer BALANCE_ERROR = new Integer(20002);
protected static final Integer FREEZE_ERROR = new Integer(20005);
protected static final Integer FINANCE_RULE_ERROR = new Integer(20006);
private static Integer synT=new Integer(1);
private static Integer synTax=new Integer(2);
	public void confirmBizBalance(final String bizId) throws FinanceRuleException {
		
			
			
	
		
	}

	public BizFinanceRule getBizFinanceRule(String bizCode) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pendingBizForm(final FinanceForms form)
			throws FinanceRuleException {
		Object obj = this.getJpaTemplate().execute(new JpaCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String id = form.getId();
				AccountStatus status = form.getStatus();
				Date bizDate = form.getBizDate();
				boolean b=isTaxHistory(bizDate,em);
				if(b)return FREEZE_ERROR;
				FinanceForm dform = em.find(FinanceForm.class, id);
				List<BalanceItem> debits = form.getDebits();
				List<BalanceItem> credits = form.getCredits();
				Query query = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.businessId=:p_bizid");
				query.setParameter("p_bizid", id);
				List<RunningAccount> resultList = query.getResultList();
				synchronized (synT) {
					if(dform!=null && dform.getStatus()!=null && (dform.getStatus().equals(AccountStatus.NORMAL)||
							dform.getStatus().equals(AccountStatus.REBACK)))return STATUS_ERROR;
					
					if(status!=null && (status.equals(AccountStatus.NORMAL)||
							status.equals(AccountStatus.REBACK)))return STATUS_ERROR;
					double debit=0d,credit=0d;
					boolean found=false;
					List<RunningAccount> items=new ArrayList<RunningAccount>(10);
					for (BalanceItem balanceItem : debits) {
						debit+=balanceItem.getAmount().doubleValue();
						found=false;
						if(balanceItem.getDirect()==null ||!BalanceDirect.DEBIT.equals(balanceItem.getDirect()))
						balanceItem.setDirect(BalanceDirect.DEBIT);
						if(resultList!=null && !resultList.isEmpty()){
							for (RunningAccount ra : resultList) {
								BalanceDirect direct = ra.getDirect();
								Integer accountId = ra.getAccountId();
								if(direct.equals(balanceItem.getDirect()) && accountId.equals(balanceItem.getFinanceId())){
									ra.setAmount(balanceItem.getAmount());
									ra.setCompanyId(balanceItem.getCompanyId());
									
									ra.setContext(balanceItem.getContext());
									found=true;
									items.add(ra);
									resultList.remove(ra);
									break;
								}
							}
						}
						
						if(!found){
							RunningAccount o=new RunningAccount();
							o.setAccountId(balanceItem.getFinanceId());
							o.setAmount(balanceItem.getAmount());
							o.setBusinessId(id);
							o.setCompanyId(balanceItem.getCompanyId());
							o.setDirect(balanceItem.getDirect());
							o.setContext(balanceItem.getContext());
							o.setCreateDate(new Date());
							items.add(o);
						}
					}
					for (BalanceItem balanceItem : credits) {
						credit+=balanceItem.getAmount().doubleValue();
						found=false;
						if(balanceItem.getDirect()==null ||!BalanceDirect.CREDIT.equals(balanceItem.getDirect()))
							balanceItem.setDirect(BalanceDirect.CREDIT);
						if(resultList!=null && !resultList.isEmpty()){
							for (RunningAccount ra : resultList) {
								BalanceDirect direct = ra.getDirect();
								Integer accountId = ra.getAccountId();
								if(direct.equals(balanceItem.getDirect()) && accountId.equals(balanceItem.getFinanceId())){
									ra.setAmount(balanceItem.getAmount());
									ra.setCompanyId(balanceItem.getCompanyId());
									
									ra.setContext(balanceItem.getContext());
									found=true;
									items.add(ra);
									resultList.remove(ra);
									break;
								}
							}
						}
						
						if(!found){
							RunningAccount o=new RunningAccount();
							o.setAccountId(balanceItem.getFinanceId());
							o.setAmount(balanceItem.getAmount());
							o.setBusinessId(id);
							o.setCompanyId(balanceItem.getCompanyId());
							o.setDirect(balanceItem.getDirect());
							o.setContext(balanceItem.getContext());
							o.setCreateDate(new Date());
							items.add(o);
						}
					}
					if(credit!=debit && debit!=form.getAmount().doubleValue())
						return BALANCE_ERROR;
					boolean fb=checkFinanceRule(items,em);
					if(fb==false)return FINANCE_RULE_ERROR;
					if(dform==null){
						dform=new FinanceForm();
						String id2 = form.getId();
						DateFormat format=new SimpleDateFormat("yyMMddHHmm");
						if(id2==null){
							long l=Math.round(Math.floor(Math.random()*100));
							String s=String.valueOf(l);
							if(s.length()<2)s="0"+s;
							if(s.length()>2)s=s.substring(0, 2);
							id2=format.format(new Date())+s;
						}
						dform.setId(id2);
						dform.setAmount(form.getAmount());
						dform.setBizDate(form.getBizDate());
						dform.setBusinessId(form.getBusinessId());
						dform.setContext(form.getContext());
						dform.setStatus(form.getStatus());
						em.merge(dform);
						//em.persist(dform);
					}else{
						dform.setAmount(form.getAmount());
						dform.setBizDate(form.getBizDate());
						dform.setBusinessId(form.getBusinessId());
						dform.setContext(form.getContext());
						dform.setStatus(form.getStatus());
						em.merge(dform);
					}
					for (RunningAccount item : items) {
						item.setBusinessId(dform.getId());
						if(item.getId()==null){
							em.merge(item);
							//em.persist(item);
						}else{
							em.merge(item);
						}
					}
					if(resultList!=null && !resultList.isEmpty()){
						for (RunningAccount ra : resultList) {
							em.remove(ra);
						}
					}
				}
				return null;
			}

			
		}, true);
		if(obj!=null && obj instanceof Integer){
			if(FINANCE_RULE_ERROR.equals(obj)){
				FinanceRuleException ex = new FinanceRuleException("FINANCE_RULE_ERROR");
				ex.setErrorCode(FinanceRuleException.UNKNOW);
				throw ex;
			}
			if(STATUS_ERROR.equals(obj)){
				FinanceRuleException ex = new FinanceRuleException("STATUS_ERROR");
				ex.setErrorCode(FinanceRuleException.UNKNOW);
				throw ex;
			}
			if(BALANCE_ERROR.equals(obj)){
				FinanceRuleException ex = new FinanceRuleException("BALANCE_ERROR");
				ex.setErrorCode(FinanceRuleException.NO_BALANCE);
				throw ex;
			}
			if(FREEZE_ERROR.equals(obj)){
				FinanceRuleException ex = new FinanceRuleException("FREEZE_ERROR");
				ex.setErrorCode(FinanceRuleException.FREEZE_BALANCE);
				throw ex;
			}
			
				FinanceRuleException ex = new FinanceRuleException(obj.toString());
				ex.setErrorCode(FinanceRuleException.UNKNOW);
				throw ex;
			
		}
	}

	public void saveBizFinanceRule(final BizFinanceRule r) {
		this.getJpaTemplate().execute(new JpaCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String bizId = r.getBizId();
				List<RuleItem> credits = r.getCredits();
				List<RuleItem> debits = r.getDebits();
				Query q = em.createQuery("select o from "+BusinessAccountRule.class.getName()+" as o where o.bizType=:p_type");
				 q.setParameter("p_type", bizId);
				List<BusinessAccountRule> rules = q.getResultList();
				List<BusinessAccountRule> items=new ArrayList<BusinessAccountRule>(10);
				boolean found=false;
				for (RuleItem balanceItem : debits) {
					found=false;
					if(balanceItem.getDirect()==null ||!BalanceDirect.DEBIT.equals(balanceItem.getDirect()))
					balanceItem.setDirect(BalanceDirect.DEBIT);
					if(rules!=null && !rules.isEmpty()){
						for (BusinessAccountRule ra : rules) {
							BalanceDirect direct = ra.getDirect();
							Integer accountId = ra.getAccountId();
							if(direct.equals(balanceItem.getDirect()) && accountId.equals(balanceItem.getFinanceId())){
								found=true;
								items.add(ra);
								rules.remove(ra);
								break;
							}
						}
					}
					
					if(!found){
						BusinessAccountRule o=new BusinessAccountRule();
						o.setAccountId(balanceItem.getFinanceId());
						o.setBizType(bizId);
						
						o.setDirect(balanceItem.getDirect());
						o.setCreateDate(new Date());
						items.add(o);
					}
				}
				for (RuleItem balanceItem : credits) {
					
					found=false;
					if(balanceItem.getDirect()==null ||!BalanceDirect.CREDIT.equals(balanceItem.getDirect()))
						balanceItem.setDirect(BalanceDirect.CREDIT);
					if(rules!=null && !rules.isEmpty()){
						for (BusinessAccountRule ra : rules) {
							BalanceDirect direct = ra.getDirect();
							Integer accountId = ra.getAccountId();
							if(direct.equals(balanceItem.getDirect()) && accountId.equals(balanceItem.getFinanceId())){
								found=true;
								items.add(ra);
								rules.remove(ra);
								break;
							}
						}
					}
					
					if(!found){
						BusinessAccountRule o=new BusinessAccountRule();
						o.setAccountId(balanceItem.getFinanceId());
						o.setBizType(bizId);
						o.setDirect(balanceItem.getDirect());
						o.setCreateDate(new Date());
						items.add(o);
					}
				}
				/*checkparent*/
				
				for (BusinessAccountRule item : items) {
					if(item.getId()==null){
						em.persist(item);
					}
				}
				if(rules!=null && !rules.isEmpty()){
					for (BusinessAccountRule ra : rules) {
						em.remove(ra);
					}
				}
				return null;
			}
		}, true);
		
	}

	public void confirmMonthReport(int year, int month)
			throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}

	public void refuseMonthReport(int year, int month)
			throws FinanceRuleException {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("unchecked")
	private boolean isTaxHistory(Date d,EntityManager em){
		synchronized (synTax) {
			long time = d.getTime();
			Calendar cld = Calendar.getInstance();
			cld.setTimeInMillis(time);
			cld.set(Calendar.DAY_OF_MONTH, 1);
			cld.set(Calendar.HOUR_OF_DAY, 0);
			cld.set(Calendar.MINUTE, 0);
			cld.set(Calendar.SECOND, 0);
			Date sdate = cld.getTime();
			cld.add(Calendar.MONTH, 1);
			Date edate=cld.getTime();
		 	Query query= em.createQuery( "select o from "+TAccountHistory.class.getName()+" as o where o.initDate>=:p_sdate and o.initDate<:p_edate ");
		 	query.setParameter("p_sdate", sdate,TemporalType.DATE).setParameter("p_edate", edate,TemporalType.DATE);
		 	List resultList = query.getResultList();
		 	if(resultList!=null && !resultList.isEmpty())return true;
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	private boolean checkFinanceRule(List<RunningAccount> items,
			EntityManager em) {
		if(items==null || items.isEmpty())return true;
		List<Integer> ids=new ArrayList<Integer>(items.size());
		Map<Integer,Object> mdebit=new HashMap<Integer,Object>(10),mcredit=new HashMap<Integer,Object>(10);
		for (RunningAccount r : items) {
			Integer accountId = r.getAccountId();
			BalanceDirect direct = r.getDirect();
			if(direct.equals(BalanceDirect.DEBIT)){
				mdebit.put(accountId, direct.toString());
			}
			if(direct.equals(BalanceDirect.CREDIT)){
				mcredit.put(accountId, direct.toString());
			}
			if(!ids.contains(accountId))
				ids.add(accountId);
		}
		Query q = em.createQuery("select o from "+TAccount.class.getName()+" as o where o.id in (:p_lst)");
		q.setParameter("p_lst", ids);
		
		List<TAccount> rs = q.getResultList();
		if(rs!=null && !rs.isEmpty()){
			for (TAccount taccount : rs) {
				Integer id = taccount.getId();
				Integer parentId = taccount.getParentId();
				if(id!=null && parentId!=null){
					if(mdebit.containsKey(id) && mdebit.containsKey(parentId))return false;
					if(mcredit.containsKey(id) && mcredit.containsKey(parentId))return false;
				}
			}
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private Object confirmItem(final String bizId, EntityManager em) throws PersistenceException {
		FinanceForm form = em.find(FinanceForm.class, bizId);
		if(form==null)return null;
		AccountStatus status = form.getStatus();
		Query query = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.business=:p_bizid");
		query.setParameter("p_bizid", bizId);
		List<RunningAccount> resultList = query.getResultList();
		synchronized (synT) {
			//FIXME CHECK PRV MONTH IS INITLIZATIONED.
			if(resultList!=null && !resultList.isEmpty()){
				double debit=0d,credit=0d;
				
				for (RunningAccount ra : resultList) {
					BalanceDirect direct = ra.getDirect();
					
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
					
					em.merge(ra);
					em.merge(t);
				}
				if(form.getStatus().equals(AccountStatus.PENDING))
					form.setStatus(AccountStatus.NORMAL);
					if(form.getStatus().equals(AccountStatus.PRREBACK))
						form.setStatus(AccountStatus.REBACK);
				form.setConfirmDate(new Date());	
			}
		}
		return null;
	}

	public void deleteFinanceForm(final String financeid) throws FinanceRuleException {

		Object object = this.getJpaTemplate().execute(new JpaCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				
				FinanceForm dform = em.find(FinanceForm.class, financeid);
				if(dform==null)return null;
				synchronized (synT) {
					AccountStatus status = dform.getStatus();
					if(status!=null && (status.equals(AccountStatus.NORMAL)||status.equals(AccountStatus.REBACK))){
							FinanceRuleException ex = new FinanceRuleException("已记账完税，不能删除");
							ex.setErrorCode(FinanceRuleException.UNKNOW);
							return ex;
					}
					//em.createQuery("").e
					Query q = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.businessId=:p_id");
					q.setParameter("p_id", dform.getId());
					List<RunningAccount> lst = q.getResultList();
					if(lst!=null && !lst.isEmpty()){
						for (RunningAccount r : lst) {
							em.remove(r);
						}
					}
					em.remove(dform);
				}
				return null;
			}

			
		}, true);
		if(object!=null && object instanceof FinanceRuleException)throw (FinanceRuleException) object;
	
	}

	@SuppressWarnings("unchecked")
	public List<FinanceForms> findFinanceForms(final String financeformId,final String bizId,
			final Date startDate,final Date endDate) {


		Object object = this.getJpaTemplate().execute(new JpaCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInJpa(EntityManager em) throws PersistenceException {
				String ql="select o from "+FinanceForm.class.getName()+" as o ";
				StringBuffer buf=new StringBuffer();
				
				if(StringUtils.isNotBlank(financeformId)){
					if(buf.length()>0)buf.append(" and ");
					buf.append("  o.id=:p_id");
				}
				if(StringUtils.isNotBlank(bizId)){
					if(buf.length()>0)buf.append(" and ");
					buf.append("  o.businessId=:p_bid");
				}
				if(startDate!=null){
					if(buf.length()>0)buf.append(" and ");
					buf.append(" o.bizDate>=:p_sdate");
					
				}
				if(endDate!=null){
					if(buf.length()>0)buf.append(" and ");
					buf.append(" o.bizDate<=:p_edate");
				}
				
				Query query = em.createQuery(ql+(buf.length()>0?(" where "+buf.toString()):""));
				if(StringUtils.isNotBlank(financeformId)){
					query.setParameter("p_id", financeformId.trim());
					
				}
				if(StringUtils.isNotBlank(bizId)){
					query.setParameter("p_bid", bizId.trim());
				}
				if(startDate!=null){
					query.setParameter("p_sdate", startDate,TemporalType.DATE);
					
					
				}
				if(endDate!=null){
					query.setParameter("p_edate", endDate,TemporalType.DATE);
				}
				
				List<FinanceForm> resultList = query.getResultList();
				List<String> ls=new ArrayList<String>(25);
				List<FinanceForms> results=new ArrayList<FinanceForms>(25);
				FinanceForms fm=null;
				for (FinanceForm f : resultList) {
					ls.add(f.getId());
					 fm = convertToDomain(f);
					 results.add(fm);
				}
				if(!ls.isEmpty()){
					Query q = em.createQuery("select o from "+RunningAccount.class.getName()+" as o where o.businessId in( :p_id )");
					q.setParameter("p_id", ls);
					List<RunningAccount> lst = q.getResultList();
					if(lst!=null && !lst.isEmpty()){
						for (RunningAccount r : lst) {
							BalanceItem item = convertToBalanceItem(r);
							String businessId = r.getBusinessId();
							for (FinanceForms f1 : results) {
								if(f1.getId().equals(r.getBusinessId())){
									BalanceDirect direct = r.getDirect();
									List<BalanceItem> debits = f1.getDebits();
									List<BalanceItem> credits = f1.getCredits();
									if(BalanceDirect.DEBIT.equals(direct)){
										if(debits==null){
											debits=new ArrayList<BalanceItem>();
											f1.setDebits(debits);
										}
										debits.add(item);
									}
									if(BalanceDirect.CREDIT.equals(direct)){
										if(credits==null){
											credits=new ArrayList<BalanceItem>();
											f1.setCredits(credits);
										}
										credits.add(item);
									}
								}
							}
						}
					}
				}
				
				return results;
			}

			

			
		}, true);
		
		return (List<FinanceForms>)object;
	}

	public static FinanceForms convertToDomain(FinanceForm f){
		FinanceForms fm=new FinanceForms();
		fm.setId(f.getId());
		fm.setAmount(f.getAmount());
		fm.setBizDate(f.getBizDate());
		fm.setBusinessId(f.getBusinessId());
		fm.setConfirmDate(f.getConfirmDate());
		fm.setContext(f.getContext());
		fm.setStatus(f.getStatus());
		return fm;
	}
	public static BalanceItem convertToBalanceItem(RunningAccount r) {
		BalanceItem item=new BalanceItem();
		item.setAmount(r.getAmount());
		item.setCompanyId(r.getCompanyId());
		item.setContext(r.getContext());
		item.setDirect(r.getDirect());
		item.setFinanceId(r.getAccountId());
		return item;
	}

}
