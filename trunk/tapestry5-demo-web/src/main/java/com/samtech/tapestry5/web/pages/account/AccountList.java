package com.samtech.tapestry5.web.pages.account;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.annotations.IncludeStylesheet;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.PageAttached;
import org.apache.tapestry5.annotations.PageDetached;
import org.apache.tapestry5.annotations.PageLoaded;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.beaneditor.BeanModelUtils;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.samtech.finance.domain.Account;
import com.samtech.finance.service.TAccountManagerService;
import com.samtech.tapestry5.web.annotation.ProtectedPage;
import com.samtech.tapestry5.web.base.BasePage;

/**
 * 
 * @author samwen
 *
 */
@ProtectedPage
@IncludeStylesheet(value="context:/scripts/prompt/skin/dmm-green/ymPrompt.css")
@IncludeJavaScriptLibrary(value="context:/scripts/prompt/ymPrompt.js")
public class AccountList extends BasePage {
	
	@Inject
	private TAccountManagerService accountManager;
	@Inject
	private BeanModelSource _beanModelSource;
	@Inject
	private ComponentResources _componentResources;
	@Inject
	private LinkSource _linkSource;
	
	 
	
	@Persist
	@Property
	private Integer accountId;
	@Persist
	@Property
	private String accName;
	@Persist
	@Property
	private Short status;
	
	
	private Object results=null;

	
	@Persist
	@Property
	private boolean searched;

	@Property
	private Account item;
	@Property
	private String message;
	private GridDataSource tableSource;
	
	private Format timeformat;
	
	
	private BeanModel<Account> mymodel;
	
	
	@Component(id = "form")
	private Form _form;

	/**
	 * {@link <a href="http://tapestry.apache.org/tapestry5/guide/pagenav.html">pagenav<a>}
	 * {@linkplain  }
	 * @param ars
	 */
	@OnEvent(value=EventConstants.ACTIVATE)
	public void activatePage(Object... ars){
		if(ars!=null && ars.length>0){
			
		}else{
		}
	}
	
	
	
	
	// Or public void onSelectedFromComputeButton()
	@OnEvent(component = "submitButton", value = "selected")
	public void selectCompute() {

		System.out.println("select subitmit ");
		
	}
	
	

	@OnEvent(component = "form", value = EventConstants.VALIDATE_FORM)
	public void validate() {
		if(status!=null && status.intValue()==-1)status=null;
		boolean hasErrors = _form.getHasErrors();
		if(hasErrors){
			System.out.println("input validate error!");
		}
	}

	@OnEvent(component = "form", value = EventConstants.SUCCESS)
	public void formSucess() {
		searched=false;
		results= this.accountManager.findTAccountStatus(accName, accountId, status);
		if(results!=null ){
			if(!((List)results).isEmpty())
				searched = true;
			else{
				this.message=this.getMessages().format("search-nofound");
				return ;
			}
				
			/*if(tableSource==null){
				GridDataSourceImpl d= new GridDataSourceImpl(this.accountManager);
				d.setAccountId(accountId);
				d.setAccountName(accName);
				d.setStatus(status);
				
				tableSource=d;
			}*/
		}else{
			this.message=this.getMessages().format("search-nofound");
		}
	}

	@OnEvent(component = "form", value = EventConstants.FAILURE)
	public void formFailure() {
		searched = false;
	}
	
	public boolean isHasResult(){
		return this.searched ;
	}
	/**
	 * 从到数据库提取数据,给grid显示(分页获取)
	 * @return
	 */
	
	public GridDataSource getTableSource() {
		if(tableSource==null){
			if(tableSource==null){
				GridDataSourceImpl d= new GridDataSourceImpl(this.accountManager);
				d.setAccountId(accountId);
				d.setAccountName(accName);
				d.setStatus(status);
				tableSource=d;
			}
		}
		return tableSource;
	}
	
	protected static class GridDataSourceImpl implements GridDataSource{

		TAccountManagerService accManager;
		private int startIndex,endIndex;
		private int totalRows=-1;
		private List _result;
		private String sortField;
		
		
		private Integer accountId;
		
		private String accountName;
		
		private Short status;
		
		GridDataSourceImpl(TAccountManagerService service){
			this.accManager=service;
			_result=null;
			totalRows=-1;
		}
		public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) {
			this.startIndex=startIndex;
			this.endIndex=endIndex;
			String propertyName=null;
			ColumnSort sort=null;
			if(sortConstraints!=null && !sortConstraints.isEmpty()){
				for (SortConstraint sc : sortConstraints) {
					ColumnSort columnSort = sc.getColumnSort();
					if(!ColumnSort.UNSORTED.equals(columnSort))sort=columnSort;
					propertyName= sc.getPropertyModel().getPropertyName();
					break;
				}
			}
			if(_result==null)
			_result= this.accManager.findTAccountStatus(accountName, accountId, status);
			if(_result!=null){
				sortList(propertyName,ColumnSort.ASCENDING.equals(sort));
			}
		}
		
		private void sortList(String s,boolean asc){
			//TODO sort
		}
		
		@SuppressWarnings("unchecked")
		public Object getRowValue(int index) {
			if(_result==null){
				_result= this.accManager.findTAccountStatus(accountName, accountId, status);
			}
			if(_result!=null){
				List r=(List)_result;
				System.out.println("result size="+r.size()+"-startIndex="+startIndex+" get row idx="+index);
				//if((index-startIndex)<r.size())
				return r.get(index);
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		public Class getRowType() {
			
			return Account.class;
		}

		public int getAvailableRows() {
			if(totalRows==-1){
			if(_result==null)
				_result =  this.accManager.findTAccountStatus(accountName, accountId, status);
			
			if(_result!=null)return totalRows=((List)_result).size();
			totalRows=0;
			}
			return totalRows;
		}
		
		public Integer getAccountId() {
			return accountId;
		}
		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public Short getStatus() {
			return status;
		}
		public void setStatus(Short status) {
			this.status = status;
		}
		
	
	}
	
	/**
	 * 构建BeanModel,并设定显示列和可排序列.
	 * @return
	 */
	public BeanModel<Account> getBeanModel(){
		
		if(mymodel==null){
			mymodel=_beanModelSource.createDisplayModel(Account.class, _componentResources.getMessages());
			BeanModelUtils.add(mymodel, "edit");
			BeanModelUtils.include(mymodel, "id,name,level,debitBalance,creditBalance,inited,parentId,edit");
			BeanModelUtils.reorder(mymodel, "id,name,level,debitBalance,creditBalance,inited,parentId");
			List<String> propertyNames = mymodel.getPropertyNames();
			for (String prop : propertyNames) {
				if(prop.equalsIgnoreCase("id")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				
				
				if(prop.equalsIgnoreCase("debitBalance")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("creditBalance")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				
				if(prop.equalsIgnoreCase("parentId")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("edit")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
			}
		}
		return mymodel;
	}
	
	public Integer[] getTicketParams(){
		
		return new Integer[]{item.getId() };
	}
	
	
	/**
	 * {@link <a href="http://tapestry.apache.org/tapestry5/guide/lifecycle.html">lifecycle</a>}
	 */
	@PageLoaded
	public void loaded() {
		/*if(decCity==null)decCity="CAN";
		if(flyType==null)flyType="1";*/
	}

	@PageDetached
	public void detached() {
		item = null;
		timeformat=null;
		message=null;
	}
	
	@BeginRender
	public void beginrender(){
		
	}
	
	@PageAttached
	public void attached() {
		
		if(timeformat==null)timeformat=new SimpleDateFormat("HH:mm");
	}
	
	
	
	public String buildUrl(Integer[] args){
		try{
		Link link = _linkSource.createPageRenderLink("account/accountEdit", true, args);
		return link.toURI();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	
	
	/*public String showTime(Date d){
		if(d!=null)
		return timeformat.format(d);
		return "";
	}*/

}
