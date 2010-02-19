package com.samtech.finance.web.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.samtech.finance.domain.Account;
import com.samtech.finance.service.TAccountManagerService;

public class AccountQueryAction extends ActionSupport implements
ServletRequestAware, ServletResponseAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4330310241860945420L;
	private ResultType children;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private TAccountManagerService accountManager;

	public ResultType getChildren() {
		return children;
	}

	public void setChildren(ResultType children) {
		this.children = children;
	}
	public static class ResultType implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 8972002528221071428L;
		private String query;
		private List<String> suggestions;
		private List<Account> data;
		public List<Account> getData() {
			return data;
		}
		public void setData(List<Account> data) {
			this.data = data;
		}
		public String getQuery() {
			return query;
		}
		public void setQuery(String value) {
			this.query = value;
		}
		public List<String> getSuggestions() {
			return suggestions;
		}
		public void setSuggestions(List<String> data) {
			this.suggestions = data;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((query == null) ? 0 : query.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ResultType other = (ResultType) obj;
			if (query == null) {
				if (other.query != null)
					return false;
			} else if (!query.equals(other.query))
				return false;
			return true;
		}
		
	}
@Override
public String execute() throws Exception {
	String p = this.request.getParameter("q");
	if(p==null)
	p=this.request.getParameter("query");
	if(StringUtils.isNotBlank(p)){
		List<Account> ls = accountManager.findTAccountlike(p);
		if(ls!=null && !ls.isEmpty()){
			children=new ResultType();
			children.setQuery(p);
			List<String> sug=new ArrayList<String>(ls.size());
			List<Account> d=new ArrayList<Account>(ls.size());
			for (Account account : ls) {
				sug.add(account.getId().toString());
				d.add(account);
			}
			children.setSuggestions(sug);
			children.setData(d);
		}
	}
	return SUCCESS;
}

public void setServletRequest(HttpServletRequest request) {
	this.request=request;
	
}

public void setServletResponse(HttpServletResponse response) {
	this.response=response;
}
public void setAccountManager(TAccountManagerService manager){
	this.accountManager=manager;
}
}
