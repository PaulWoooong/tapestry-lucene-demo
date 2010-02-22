package com.samtech.tapestry5.web.pages.financeform;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.service.FinanceService;

public class FinanceformEdit {
	@Property
	private String financeId;
	@Property
	private FinanceForms financeForms;
	
	@Inject
	private FinanceService financeManager;
	
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
}
