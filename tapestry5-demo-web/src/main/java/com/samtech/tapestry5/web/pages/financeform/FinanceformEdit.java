package com.samtech.tapestry5.web.pages.financeform;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.internal.services.StringValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.service.FinanceService;

public class FinanceformEdit {
	@Property
	private String financeId;
	@Property
	private FinanceForms financeForms;
	@Retain
	@Property
	private StringValueEncoder encoder=new StringValueEncoder();
	
	@Inject
	private FinanceService financeManager;
	//Inject
	//private FormSupport _formSupport;
	@Component(id="questionAddForm")
	Form _form;
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
	}
	
	private void initItem(){
		if(StringUtils.isNotBlank(this.financeId)){
			List<FinanceForms> ls = financeManager.findFinanceForms(financeId, null, null, null);
			if(ls!=null && !ls.isEmpty()){
				financeForms=ls.get(0);
			}else{
				
			}
		}
	}
	
	
	// 保存页面并返回新增问题页面
	@OnEvent(component="doAddAndNew",value=EventConstants.SELECTED)
	public String doAddAndNew() {
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
}
