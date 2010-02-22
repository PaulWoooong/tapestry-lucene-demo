package com.samtech.finance.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.domain.BalanceItem;
import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.service.FinanceService;
import com.samtech.finance.web.anontation.Protected;
@Protected
public class FinanceFormEditAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431428717032717369L;

	private FinanceForms account;
	private String queryFinanceFormId;
	private FinanceService financeManager;
	private int counter;
	public String getQueryFinanceFormId() {
		return queryFinanceFormId;
	}

	public void setQueryFinanceFormId(String q) {
		this.queryFinanceFormId = q;
	}

	public boolean isBegincounter() {
		counter=0;
		return false;
	}
	public boolean isCounter() {
		counter++;
		return false;
	}
	public Integer getIndexKey() {
		return new Integer(counter);
		
	}
	public FinanceForms getFinanceForms() {
		if (account == null)
			account = new FinanceForms();
		return account;
	}

	public void setFinanceForms(FinanceForms O) {
		this.account = O;
	}

	public static class LevelValue {
		private String key, value;

		public LevelValue() {

		}

		LevelValue(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
	public List<LevelValue> getLevels(){
		//DEBIT,		CREDIT
		List<LevelValue> values=new ArrayList<LevelValue>();
		values.add(new LevelValue("DEBIT", "借"));
		values.add(new LevelValue("CREDIT", "贷"));
		return values;
	}
	@Override
	public void prepare() throws Exception {

		super.prepare();
		HttpServletRequest request = this.getServletRequest();
		String parameter = request.getParameter("queryFinanceFormId");
		String method = request.getMethod();
		if (method != null && "get".equalsIgnoreCase(method))
			if (parameter != null && parameter.trim().length() > 0) {
				List<FinanceForms> forms = financeManager.findFinanceForms(
						parameter, null, null, null);
				if (forms != null && !forms.isEmpty()) {
					this.setFinanceForms(forms.get(0));
				}

			}

	}

	@Override
	public String execute() throws Exception {
		String qid = this.getQueryFinanceFormId();
		if (qid != null && this.account == null) {

			List<FinanceForms> forms = financeManager.findFinanceForms(qid,
					null, null, null);
			if (forms != null && !forms.isEmpty()) {
				this.setFinanceForms(forms.get(0));
			}

		}
		return super.execute();
	}

	// 保存页面并返回新增问题页面
	public String doAddAndNew() {
		checkEditError();
		Map<String, List<String>> fieldErrors = this.getFieldErrors();
		if (fieldErrors != null && !fieldErrors.isEmpty())
			return INPUT;
		queryFinanceFormId = null;
		try {
			
			financeManager.pendingBizForm(account);
			this.setFinanceForms(new FinanceForms());
			this.addActionMessage("挂靠成功！");

		} catch (Exception e) {

			this.addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	// 请求增加问题页面并浏览面

	public String doAddAndView() {
		checkEditError();
		Map<String, List<String>> fieldErrors = this.getFieldErrors();
		if (fieldErrors != null && !fieldErrors.isEmpty())
			return INPUT;
		try {

			financeManager.pendingBizForm(account);
			this.addActionMessage("挂靠成功！");

			this.setQueryFinanceFormId(account.getId());
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	private void checkEditError() {
		HttpServletRequest request = this.getServletRequest();
		Date bizDate = this.getFinanceForms().getBizDate();
		if(bizDate==null){
			this.addFieldError("financeForms.bizDate", "required value");
		}
		int k=0;
		boolean error=false;
		for(k=0;k<50;k++){
			error=false;
		String acc = request.getParameter("acct_"+k);
		 String direct = request.getParameter("direct_"+k);
		 String amt = request.getParameter("amt_"+k);
		 String comp = request.getParameter("cmp_"+k);
		 BigDecimal bamt=null;
		 if(direct!=null){
			 if(StringUtils.isBlank(acc)){
				 this.addFieldError("acct_"+k, "required value"); 
				 error=true;
			 }else{
				try{ Integer.valueOf(acc);}catch(NumberFormatException ex){
					 this.addFieldError("acct_"+k, "Integer value"); 
					 error=true;
				}
			 }
			 if(StringUtils.isBlank(amt)){
				 this.addFieldError("amt_"+k, "required value"); 
				 error=true;
			 }else if( NumberUtils.isNumber(amt)){
				 bamt=new BigDecimal(amt);
				 bamt=bamt.setScale(2, BigDecimal.ROUND_HALF_UP);
			 }else{
				 this.addFieldError("amt_"+k, "must number value");  
				 error=true;
			 }
			if(!error){
				BalanceItem balanceItem = new BalanceItem();
				List<BalanceItem> lst=null;
				if(direct.equalsIgnoreCase("DEBIT")){
					lst=this.getFinanceForms().getDebits();
					balanceItem.setDirect(BalanceDirect.DEBIT);
				}else{
					lst= this.getFinanceForms().getCredits();
					balanceItem.setDirect(BalanceDirect.CREDIT);
				}
				balanceItem.setAmount(bamt);
				balanceItem.setFinanceId(new Integer(acc));
				balanceItem.setCompanyId(comp);
				lst.add(balanceItem);
			}
		 }
		}
	}

	public void setFinanceManager(FinanceService manager) {
		this.financeManager = manager;
	}

}
