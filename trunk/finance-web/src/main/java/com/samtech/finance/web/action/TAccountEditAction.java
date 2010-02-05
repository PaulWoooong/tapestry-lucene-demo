package com.samtech.finance.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.samtech.finance.domain.Account;
import com.samtech.finance.service.TAccountManagerService;

public class TAccountEditAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431428717032717369L;

	private Account account;
	private Integer accountId;
	private TAccountManagerService accountManager;

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer o) {
		this.accountId = o;
	}

	public Account getAccount() {
		if (account == null)
			account = new Account();
		return account;
	}

	public void setAccount(Account O) {
		this.account = O;
	}
	
	public static class LevelValue{
		private String key,value;
		public LevelValue(){
			
		}
		LevelValue(String key,String value){
			this.key=key;
			this.value=value;
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
	public List<LevelValue> getGenderValues(){
		
		List<LevelValue> values=new ArrayList<LevelValue>();
		values.add(new LevelValue("ONE", "一级科目"));
		values.add(new LevelValue("TWO", "二级科目"));
		return values;
	}
	@Override
	public void prepare() throws Exception {
		
		super.prepare();
		HttpServletRequest request = this.getServletRequest();
		String parameter = request.getParameter("accountId");
		String method = request.getMethod();
		if(method!=null && "get".equalsIgnoreCase(method))
		if(parameter!=null && parameter.trim().length()>0){
			Account u = (Account) accountManager.getAccountById(
					Integer.valueOf(parameter));
			if(u!=null)this.setAccount(u);
		}
		
	}
	@Override
	public String execute() throws Exception {
		Integer qid = this.getAccountId();
		if(qid!=null && this.account==null){
			Account u = (Account) accountManager.getAccountById(qid);
			if(u!=null)this.setAccount(u);
			else{
				this.addActionError("没有该用户 employeeId="+qid);
			}
		}
		return super.execute();
	}
	// 保存页面并返回新增问题页面
	public String doAddAndNew() {
		checkEditError();
		Map<String, List<String>> fieldErrors = this.getFieldErrors();
		if(fieldErrors!=null && !fieldErrors.isEmpty())return INPUT;
		accountId = null;
		try {
			// search the id whether have be register
			Account u = (Account) accountManager.getAccountById(
					this.account.getId());
			if (u == null) {
				// if never register then report error
				accountManager.saveAccount(account);
				this.setAccount(new Account());
				this.addActionMessage("用户新增成功！");
			} else {
				if(u.getInited()<1)
					accountManager.saveAccount(account);
				else
				addActionMessage("已经初始化！");
			}
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
		if(fieldErrors!=null && !fieldErrors.isEmpty())return INPUT;
		try {
			Account u = (Account) accountManager.getAccountById(this.account.getId());
			if (u == null) {
				accountManager.saveAccount(account);
				addActionMessage("新增用户成功！");
			} else {
				if(u.getInited()<1){
				account=(Account) accountManager.saveAccount(account);
				addActionMessage("修改用户成功！");
				}else
					addActionMessage("已经初始化！");

			}
			this.setAccountId(account.getId());
		} catch (Exception e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	private void checkEditError() {

	}

	public void setAccountManager(TAccountManagerService manager){
    	this.accountManager=manager;
    }

}
