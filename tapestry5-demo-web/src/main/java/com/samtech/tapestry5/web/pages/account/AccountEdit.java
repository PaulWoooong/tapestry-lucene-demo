package com.samtech.tapestry5.web.pages.account;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.MarkupWriterFactory;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.util.TextStreamResponse;

import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.domain.Account;
import com.samtech.finance.domain.BalanceItem;
import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.service.FinanceService;
import com.samtech.finance.service.TAccountManagerService;

@IncludeJavaScriptLibrary({ "${tapestry.scriptaculous}/controls.js"})
public class AccountEdit {
	@Property
	private Integer accountId;
	
	@Property
	private Account account;
	@Retain
	@Property
	private StringValueEncoder encoder=new StringValueEncoder();
	
	
	@Inject
	private TAccountManagerService accountManager;
	
	@Inject 
	private Request request;
	@Inject
	private LinkSource _linkSource;
	@Inject
    private ComponentResources resources;
	@Inject
    private MarkupWriterFactory factory;
	
	@Inject
	private RenderSupport renderSupport;
		
	//Inject
	//private FormSupport _formSupport;
	@Component(id="editForm")
	private Form _form;
	@OnEvent(value=EventConstants.ACTIVATE)
	public void activatePage(Object ...ars){
		//System.out.println("ticket"+ticketno+";fltdate="+fltdate);
		
			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			
		
		if(ars!=null){
			int length = ars.length;
			if(length>0){
				Object o=ars[0];
				if(o!=null && o instanceof Integer)
				accountId=(Integer)ars[0];
				if(o!=null && o instanceof String)
					accountId=Integer.valueOf((String)ars[0]);
				
			}else{
				//remove;
			}
		}else {
			//remove;
		}
		initItem();
	}
	
	@OnEvent(value=EventConstants.PASSIVATE)
	public Integer passitivePage(){
		return accountId;
	}
	
	private void initItem(){
		if(this.accountId!=null){
			if(this.account!=null && this.account.getId()!=null &&  this.account.getId().equals(accountId))
				return;
			this.account = this.accountManager.getAccountById(accountId);
			
		}else{
			account=new Account();
		}
	}
	
	
}
