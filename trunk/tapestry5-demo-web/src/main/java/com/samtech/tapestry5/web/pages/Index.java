package com.samtech.tapestry5.web.pages;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.tapestry5.annotations.Retain;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.beaneditor.PropertyModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.grid.ColumnSort;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.grid.SortConstraint;
import org.apache.tapestry5.internal.beaneditor.BeanModelUtils;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;

import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.domain.RunningAccountHistory;
import com.samtech.finance.service.FinanceService;

/**
 * 
 * @author samwen
 *
 */
@IncludeStylesheet(value="context:/scripts/prompt/skin/dmm-green/ymPrompt.css")
@IncludeJavaScriptLibrary(value="context:/scripts/prompt/ymPrompt.js")
public class Index {
	// @Service
	@Inject
	// (value="itemService")
	private FinanceService financeManager;
	
	@Inject
	private BeanModelSource _beanModelSource;

	@Inject
	private ComponentResources _componentResources;
	@Inject
	private LinkSource _linkSource;
	
	//private List<AccountStatus> statuses;
	@Persist
	@Property
	private Date endDate;
	@Persist
	@Property
	private Date startDate;
	@Persist
	@Property
	private Integer accountId;
	@Persist
	@Property
	private String financeformId;
	@Persist
	@Property
	private String content;
	
	private Object results=null;

	@SuppressWarnings("unused")
	@Persist
	@Property
	private boolean searched;

	@Property
	private RunningAccountHistory item;
	
	private GridDataSource tableSource;
	
	private Format timeformat;
	private String sortColumnId;
	private BeanModel<RunningAccountHistory> mymodel;
	
	
	@Component(id = "form")
	private Form _form;

	

	@SuppressWarnings("unused")
	@Property
	@Persist
	private boolean isComputeFlag;

	// Or public void onSelectedFromComputeButton()
	@OnEvent(component = "submitButton", value = "selected")
	public void selectCompute() {

		isComputeFlag = true;
		
	}

	@OnEvent(value=EventConstants.ACTIVATE)
	public void activatePage(Object... ars){
		if(ars!=null && ars.length>0){
			
		}else{
		}
	}

	@OnEvent(component = "form", value = EventConstants.VALIDATE_FORM)
	public void validate() {
		if (this.startDate == null )
			_form.recordError("error");
	}

	@OnEvent(component = "form", value = EventConstants.SUCCESS)
	public void formSucess() {
		searched=false;
		results= financeManager.findRunningAccount(financeformId, accountId, content, null, startDate, endDate);
		if(results!=null ){
			if(!((List)results).isEmpty())
				searched = true;
			isComputeFlag = true;
			if(tableSource==null){
				GridDataSourceImpl d= new GridDataSourceImpl(this.financeManager);
				d.setAccountId(accountId);
				d.setContent(content);
				d.setStartDate(startDate);
				d.setEndDate(endDate);
				d.setFinanceformId(financeformId);
				tableSource=d;
			}
			
		}
	}

	@OnEvent(component = "form", value = EventConstants.FAILURE)
	public void formFailure() {
		searched = false;
	}
	
	public boolean isHasResult(){
		return this.searched && this.isComputeFlag;
	}
	/**
	 * 从到数据库提取数据,给grid显示(分页获取)
	 * @return
	 */
	
	public GridDataSource getTableSource() {
		if(tableSource==null){
			GridDataSourceImpl d= new GridDataSourceImpl(this.financeManager);
			d.setAccountId(accountId);
			d.setContent(content);
			d.setStartDate(startDate);
			d.setEndDate(endDate);
			d.setFinanceformId(financeformId);
			tableSource=d;
		}
		return tableSource;
	}
	
	protected static class GridDataSourceImpl implements GridDataSource{

		FinanceService financeManager;
		private int startIndex,endIndex;
		private int totalRows=-1;
		private List _result;
		private String sortField;
		private boolean isDesc;
		
		private Date endDate;
		
		private Date startDate;
		
		private Integer accountId;
		
		private String financeformId;
		
		private String content;
		
		GridDataSourceImpl(FinanceService service){
			this.financeManager=service;
			_result=null;
			totalRows=-1;
		}
		public void prepare(int startIndex, int endIndex, List<SortConstraint> sortConstraints) {
			this.startIndex=startIndex;
			this.endIndex=endIndex;
			if(sortConstraints!=null && !sortConstraints.isEmpty()){
				SortConstraint sort = sortConstraints.get(0);
				StringBuffer buf=new StringBuffer(20);
				if(sort!=null){
					ColumnSort columnSort = sort.getColumnSort();
					//String name = columnSort.name();
					final PropertyModel propertyModel = sort.getPropertyModel();
					if(propertyModel!=null){
						String propertyName = propertyModel.getPropertyName();
						sortField=propertyName;
						buf.append("Model id="+propertyModel.getId())
						.append("label="+propertyModel.getLabel())
						.append(";prop name="+propertyName)
						.append(";prop type="+propertyModel.getPropertyType())
						.append("; data type"+propertyModel.getDataType());
						buf.append("\n");
					}
					if(columnSort!=null){
						isDesc=!ColumnSort.ASCENDING.equals(columnSort);
						isDesc=ColumnSort.DESCENDING.equals(columnSort);
						if(ColumnSort.UNSORTED.equals(columnSort)){
						 sortField=null;	
						}
						buf.append("sort by "+columnSort.name());
					}
				}
			}
			if(_result==null)
				_result= financeManager.findRunningAccount(financeformId, accountId, content, null, startDate, endDate);
		}

		@SuppressWarnings("unchecked")
		public Object getRowValue(int index) {
			if(_result==null){
				_result= financeManager.findRunningAccount(financeformId, accountId, content, null, startDate, endDate);
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
			
			return RunningAccountHistory.class;
		}

		public int getAvailableRows() {
			if(totalRows==-1){
			if(_result==null)
				_result = financeManager.findRunningAccount(financeformId, accountId, content, null, startDate, endDate);
			
			if(_result!=null)return totalRows=((List)_result).size();
			totalRows=0;
			}
			return totalRows;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Integer getAccountId() {
			return accountId;
		}
		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
		}
		public String getFinanceformId() {
			return financeformId;
		}
		public void setFinanceformId(String financeformId) {
			this.financeformId = financeformId;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	
	}
	
	/**
	 * 构建BeanModel,并设定显示列和可排序列.
	 * @return
	 */
	public BeanModel<RunningAccountHistory> getBeanModel(){
		//"add=edit",
		//"include=flightNo,flightStatus,shipSect,customerName,certificateId,grade,cabin,fltClass,ffpTier,baggageNum,pnr,transfer,leavPort,arvPort,hobby",
		if(mymodel==null){
			mymodel=_beanModelSource.createDisplayModel(RunningAccountHistory.class, _componentResources.getMessages());
			BeanModelUtils.add(mymodel, "edit");
			BeanModelUtils.include(mymodel, "financeId, accountName,context,direct,amount,bizDate,status,companyId,edit");
			BeanModelUtils.reorder(mymodel, "financeId, accountName,context,direct,amount,bizDate,status,companyId");
			List<String> propertyNames = mymodel.getPropertyNames();
			for (String prop : propertyNames) {
				if(prop.equalsIgnoreCase("financeId")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("accountName")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("context")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
					//propertyModel
				}
				if(prop.equalsIgnoreCase("direct")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("amount")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("bizDate")){
					PropertyModel propertyModel = mymodel.get(prop);
					propertyModel.sortable(false);
				}
				if(prop.equalsIgnoreCase("status")){
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
	
	public String[] getTicketParams(){
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
		return new String[]{item.getFinanceId() };
	}
	
	protected boolean isVipAuthority() {
		return false;
	}

	@PageLoaded
	public void loaded() {
		/*if(decCity==null)decCity="CAN";
		if(flyType==null)flyType="1";*/
	}

	@PageDetached
	public void detached() {
		item = null;
		timeformat=null;
		//tableSource=null;
	}
	@BeginRender
	public void beginrender(){
		
		if(this.startDate==null){
		Calendar cld = Calendar.getInstance();
		cld.add(Calendar.DATE, -3);
		this.startDate=cld.getTime();
		}
	}
	
	@PageAttached
	public void attached() {
		
		if(timeformat==null)timeformat=new SimpleDateFormat("HH:mm");
	}
	
	public String showAccountName(Object e){
		if( e!=null && e instanceof RunningAccountHistory){
			RunningAccountHistory o=(RunningAccountHistory)e;
			StringBuffer buf=new StringBuffer(10);
			buf.append(o.getAccountId()).append(o.getAccountName());
			return buf.toString();
		}
		return "";
	}
	public String showStatus(Object status){
		
		if (status != null
				&& status instanceof AccountStatus ) {
			if(AccountStatus.PENDING.equals(status))
			return("挂靠");
			else if(AccountStatus.PRREBACK.equals(status))
				return("冲账待核");
			else if(AccountStatus.NORMAL.equals(status))
				return("已核");
			else if(AccountStatus.REBACK.equals(status))
				return("冲账已核");
		}
		return "";
	}
	
	public String buildUrl(String[] args){
		try{
		Link link = _linkSource.createPageRenderLink("financeform/financeformEdit", true, args);
		return link.toURI();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "";
	}
	public String showTime(Date d){
		if(d!=null)
		return timeformat.format(d);
		return "";
	}

}
