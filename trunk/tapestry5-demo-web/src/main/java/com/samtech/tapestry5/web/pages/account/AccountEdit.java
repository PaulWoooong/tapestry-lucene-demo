package com.samtech.tapestry5.web.pages.account;

import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.util.AbstractSelectModel;
import org.apache.tapestry5.util.EnumValueEncoder;

import com.samtech.finance.database.FinanceLevel;
import com.samtech.finance.domain.Account;
import com.samtech.finance.service.TAccountManagerService;
import com.samtech.tapestry5.web.annotation.ProtectedPage;
import com.samtech.tapestry5.web.base.BasePage;
/**
 * 
 * @author samwen
 * use BeanEditor  
 * {@link org.apache.tapestry5.corelib.components.PropertyEditor} 
 * {@link  org.apache.tapestry5.services.PropertyEditContext}
 *  and {@link org.apache.tapestry5.corelib.pages.PropertyEditBlocks}
 */
@ProtectedPage
public class AccountEdit extends BasePage {
	@Property
	private Integer accountId;
	
	@Property
	private Account account;
	@Property
	private String message;
	
	@Inject
	private TAccountManagerService accountManager;
	
	@Inject
	private ComponentResources _componentResources;
	
	
	
	 @InjectComponent(value="updatezone")
	 private Zone updatezone;	
	//Inject
	//private FormSupport _formSupport;
	@Component(id="editForm")
	private Form _form;

	@OnEvent(value=EventConstants.ACTIVATE)
	public void activatePage(Object ...ars){
		
		
			
		
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
	
	@OnEvent(component="editForm",value=EventConstants.SUBMIT)
	public Object doSave() {


		boolean hasErrors = _form.getHasErrors();
		if(hasErrors)
		return updatezone.getBody();
		
		try {
			
			this.accountManager.saveAccount(account);
			
		} catch (Exception e) {
			this.message="SAVE FAILURE";
		}
		return updatezone.getBody();
	}
	
	@OnEvent(component="editForm" ,value=EventConstants.SUCCESS)
	public void formFailure(){
		
	}
	@OnEvent(component="editForm" ,value=EventConstants.SUCCESS)
	public void formSucess(){
		this.message=this._componentResources.getMessages().get("save-successful");
	}
	
	public ValueEncoder<FinanceLevel> getLevelEncoder(){
		return new EnumValueEncoder<FinanceLevel>(FinanceLevel.class);
	}
	public SelectModel getLevelModel(){
		return new AbstractSelectModel(){
			private final List<OptionModel> options = CollectionFactory.newList();
			public List<OptionGroupModel> getOptionGroups() {
				
				return null;
			}

			public List<OptionModel> getOptions() {
				options.add(new OptionModelImpl(_componentResources.getMessages().get("levelone-msg"),FinanceLevel.ONE));
				options.add(new OptionModelImpl(_componentResources.getMessages().get("leveltwo-msg"),FinanceLevel.TWO));
				return options;
			}
			
		};
	}
}
