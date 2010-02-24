package com.samtech.tapestry5.web.pages.financeform;

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
import com.samtech.tapestry5.web.annotation.ProtectedPage;
import com.samtech.tapestry5.web.base.BasePage;
@ProtectedPage
@IncludeJavaScriptLibrary({ "${tapestry.scriptaculous}/controls.js","financeforms.js"})
public class FinanceformEdit extends BasePage{
	@Property
	private String financeId;
	
	@Property
	private FinanceForms financeForms;
	@Retain
	@Property
	private StringValueEncoder encoder=new StringValueEncoder();
	@Property
	private BalanceItem item;
	@Inject
	private FinanceService financeManager;
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
	
	private int counter;
	//Inject
	//private FormSupport _formSupport;
	@Component(id="questionAddForm")
	private Form _form;
	@OnEvent(value=EventConstants.ACTIVATE)
	public void activatePage(Object ...ars){
		//System.out.println("ticket"+ticketno+";fltdate="+fltdate);
		
			SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
			
		
		if(ars!=null){
			int length = ars.length;
			if(length>0){
				financeId=(String)ars[0];
				
			}else{
				//remove;
			}
		}else {
			//remove;
		}
		initItem();
	}
	
	@OnEvent(value=EventConstants.PASSIVATE)
	public String passitivePage(){
		return financeId;
	}
	
	private void initItem(){
		if(StringUtils.isNotBlank(this.financeId)){
			if(this.financeForms!=null && this.financeForms.getId()!=null &&  this.financeForms.getId().equals(financeId))
				return;
			List<FinanceForms> ls = financeManager.findFinanceForms(financeId, null, null, null);
			if(ls!=null && !ls.isEmpty()){
				financeForms=ls.get(0);
			}else{
				financeForms=new FinanceForms();
			}
		}else{
			financeForms=new FinanceForms();
		}
	}
	
	
	// 保存页面并返回新增问题页面
	@OnEvent(component="doAddAndNew",value=EventConstants.SELECTED)
	public String doAddAndNew() {
		
		initbalance();
		boolean hasErrors = _form.getHasErrors();
		if(hasErrors)
		return null;
		
		financeId = null;
		try {
			
			financeManager.pendingBizForm(financeForms);
			financeForms=new FinanceForms();
			
		} catch (Exception e) {

			
		}
		return null;
	}

	// 请求增加问题页面并浏览面
	@OnEvent(component="doAddAndView",value=EventConstants.SELECTED)
	public String doAddAndView() {
		initbalance();
		boolean hasErrors = _form.getHasErrors();
		if(hasErrors)
		return null;
		try {

			financeManager.pendingBizForm(financeForms);
			//this.addActionMessage("挂靠成功！");
			financeId=financeForms.getId();
			//this.setQueryFinanceFormId(account.getId());
		} catch (Exception e) {
			
		}
		return null;
	}
	void initbalance(){
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
				 _form.recordError("acct_"+k+ " required value"); 
				 error=true;
			 }else{
				try{ Integer.valueOf(acc);}catch(NumberFormatException ex){
					_form.recordError("acct_"+k + " Integer value"); 
					 error=true;
				}
			 }
			 if(StringUtils.isBlank(amt)){
				 _form.recordError("amt_"+k+" required value"); 
				 error=true;
			 }else if( NumberUtils.isNumber(amt)){
				 bamt=new BigDecimal(amt);
				 bamt=bamt.setScale(2, BigDecimal.ROUND_HALF_UP);
			 }else{
				 _form.recordError("amt_"+k+" must number value");  
				 error=true;
			 }
			if(!error){
				BalanceItem balanceItem = new BalanceItem();
				List<BalanceItem> lst=null;
				if(direct.equalsIgnoreCase("DEBIT")){
					lst=financeForms.getDebits();
					balanceItem.setDirect(BalanceDirect.DEBIT);
				}else{
					lst= financeForms.getCredits();
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
		
	@OnEvent(value="autocomplete")
	public  StreamResponse queryAccount(){
		String q=request.getParameter("search");
		List<Account> list = accountManager.findTAccountlike(q);
		final ContentType contentType = new ContentType("text/html");
		MarkupWriter writer = factory.newMarkupWriter(contentType);
		writer.element("ul");

        for (Account o : list)
        {
            writer.element("li");
            writer.write(o.getId().toString());
            writer.end();
        }

        writer.end();
		return new TextStreamResponse(contentType.toString(),writer.toString());
	
	}
	
	protected JSONArray generateResponseMarkup(List matches)
	{
		JSONArray jsonObject = new JSONArray();
		for (Object o : matches)
		{
			Object value =null;// translate.toClient(o);
			Object label =null;// propertyAccess.get(o, labelPropertyName);
			JSONObject item = new JSONObject();
			item.put("text", label);
			item.put("value", value);
			jsonObject.put(item);
		}

		return jsonObject;
	}
	
	public String getAutoCompleteUrl(){
		org.apache.tapestry5.runtime.Component page = resources.getPage();
		Link eventLink = resources.createEventLink("autocomplete", new Object[0]);//_linkSource.createComponentEventLink((Page)page,null,"autocomplete", false, new Object[0]);
		return eventLink.toAbsoluteURI();
	}
	
	public boolean isBegincounter() {
		counter=0;
		return false;
	}
	public boolean isCounter() {
		try{
		JSONObject config = new JSONObject();
		//config.put("url", getAutoCompleteUrl());
		config.put("paramName", "search");
		config.put("minChars", 2);
		
		renderSupport.addScript("new Ajax.Autocompleter('%s', 'auto_component','%s',%s);", "acct_"+counter,getAutoCompleteUrl(), config);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		counter++;
		return false;
	}
	public Integer getIndexKey() {
		return new Integer(counter);
		
	}
	
}
