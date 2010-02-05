package com.samtech.finance.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.Action;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.event.MouseRowEvent;

import com.samtech.business.database.Gender;
import com.samtech.common.domain.IUser;
import com.samtech.common.domain.PagingAndSorting;
import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.FinanceLevel;
import com.samtech.finance.domain.Account;
import com.samtech.finance.service.TAccountManagerService;



public class TAccountManagerListAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
		private boolean logined = false;
		private Integer queryAccountId;
        private String  queryName;
        private InputStream pgtableResult;
        private Short accountStatus; 
        
		private static String tblid = "user_tbl";
        private List<Account> accs;
        
        private TAccountManagerService accountManager;
        		
		
		
		private void setPgInputStream(InputStream in) {
    		pgtableResult = in;
    	}

    	public InputStream getPgInputStream() {
    		
    		return pgtableResult;
    	}
    	
    	public String doQuery(){
    		checkError();
    		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
    		//Collection<String> actionErrors = this.getActionErrors();
			if(fieldErrors!=null && !fieldErrors.isEmpty()){
				return INPUT;
			}
    		String tblid = "user_tbl";
    		HttpServletRequest request = this.getServletRequest();
    		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
    		
    		//tableFacade.setMaxRows(10);
    		
    		HttpSession session = request.getSession();
    		if (session != null) {
    				session.setAttribute(tblid + "_q_name",this.queryName);
    				session.setAttribute(tblid + "_q_id",this.queryAccountId);
    				session.setAttribute(tblid + "_q_status",this.accountStatus);//status = (Short) session.getAttribute(tblid + "_q_status");
    		}
    		isMaxRow=true;
    		String qname=this.queryName;
    		Integer qid=this.queryAccountId;
    		Short status=accountStatus;
    		tableFacade.setStateAttr("restore");
    		tableFacade.setMaxRows(10);		
    		tableFacade.setMaxRowsIncrements(10, 20, 50);    
    		initTable(tableFacade, qname, qid,status);
    		if(accs==null || accs.isEmpty()){
    			this.addActionError("查询没有记录！");
    		}else{
    		final String buildTable = buildTable(tableFacade);
			request.setAttribute("user_tbl", buildTable);
    		}
    		return SUCCESS;
    	}
    	
    	public String doDelete(){
    		if(queryAccountId!=null){
    			
    			Integer qid=this.queryAccountId;
    			try {
					this.accountManager.deleteAccount(qid);
					
    			this.addActionMessage("删除成功！");
    			} catch (FinanceRuleException e) {
    				this.addActionError(e.getMessage());
    			}
    		}
    		HttpServletRequest request = this.getServletRequest();
    		HttpSession session = request.getSession();
    		String qname=null;
    		Integer qid=null;
    		Short status=null;
    		if (session != null) {
    				qname = (String) session.getAttribute(tblid + "_q_name");
    				qid = (Integer) session.getAttribute(tblid + "_q_id");
    				status = (Short) session.getAttribute(tblid + "_q_status");
    		}
    		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
    		
    		tableFacade.setMaxRows(10);		
    		tableFacade.setMaxRowsIncrements(10, 20, 50);    
    		initTable(tableFacade, qname, qid,status);
    		if(accs==null || accs.isEmpty()){
    			this.addActionError("查询没有记录！");
    		}else{
    		final String buildTable = buildTable(tableFacade);
			request.setAttribute("user_tbl", buildTable);
    		}
    		return SUCCESS;
    	}

	protected void checkError(){
      
 	   /*if(StringUtils.isBlank( id) && StringUtils.isBlank( qname) ){
 		   this.addFieldError("queryName",  "请输入名字");
 		   this.addFieldError("queryUserId",  "请输入登录ID");
 		   
 	   }*/
    }
	
	boolean isMaxRow=false;
    @SuppressWarnings("unchecked")
	public String paging() {
		HttpServletRequest request = this.getServletRequest();
		Integer accountId=null;
		Short status=null;
		String qname = request.getParameter(tblid + "_q_name");
		String qid = request.getParameter(tblid + "_q_id");
		String pst = request.getParameter(tblid + "_q_status");
		if(StringUtils.isNotBlank(qid))
			accountId=Integer.valueOf(qid);
		if(StringUtils.isNotBlank(pst))
			status=Short.valueOf(pst);
		
		HttpSession session = request.getSession();
		if (session != null) {
				qname = (String) session.getAttribute(tblid + "_q_name");
				accountId = (Integer) session.getAttribute(tblid + "_q_id");
				status = (Short) session.getAttribute(tblid + "_q_status");
				
		}
		Map parameterMap = request.getParameterMap();
		String maxrowkey=tblid+"_" + Action.MAX_ROWS.toParam();
		String pagekey=tblid+"_" + Action.PAGE.toParam();
		String sortkey=tblid+"_" + Action.SORT.toParam();
		
		if(parameterMap.containsKey(maxrowkey)){
			Object object = parameterMap.get(maxrowkey);
			if(object!=null){
				if(object.getClass().isArray()){
					String[] pvalues=(String[]) object;
					if(!("null".equalsIgnoreCase(pvalues[0].toString())|| "".equals(pvalues[0])))
						isMaxRow=true;
				}else if(!("null".equalsIgnoreCase(object.toString())|| "".equals(object.toString())))
					isMaxRow=true;
			}
			if(!isMaxRow)request.setAttribute(maxrowkey, 10);
			object = parameterMap.get(pagekey);
			
			
		}
		
		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
		
		tableFacade.setStateAttr("restore");
		
		initTable(tableFacade, qname, accountId,status);
		final String buildTable = buildTable(tableFacade);
		ByteArrayInputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(buildTable.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setPgInputStream(inputStream);
		return "pgresult";
	}

	@SuppressWarnings("unchecked")
	private void initTable(TableFacade tableFacade, String qname, Integer qid,Short status) {
		
			
			accs = accountManager.findTAccountStatus(qname, qid, status);
		tableFacade.setTotalRows(accs.size());
		tableFacade.setMaxRowsIncrements(10, 20, 50);    
		/*if(rsl!=null){
			rsl.getMaxRows();
		}else*/
		//tableFacade.setMaxRows(10);
		
		Limit limit = tableFacade.getLimit();
		RowSelect rowSelect = limit.getRowSelect();
		
		if (rowSelect != null) {
			int rowStart = rowSelect.getRowStart();
			int rowEnd = rowSelect.getRowEnd();
			int page = rowSelect.getPage();
			System.out.println("rowstart=" + rowStart + ";rowend=" + rowEnd
					+ ";page=" + page);
			ArrayList rs = new ArrayList(10);
			for (int i = rowStart; i < rowEnd && i < accs.size(); i++) {
				rs.add(accs.get(i));
			}
			tableFacade.setItems(rs);
		}
	}
    

		public boolean isLogined() {
                return logined;
        }

        public Integer getQueryAccountId() {
                return queryAccountId;
        }

        public void setQueryAccountId(Integer q) {
                this.queryAccountId = q;
        }

        public String getQueryName() {
                return queryName;
        }

        public void setQueryName(String username) {
                this.queryName = username;
        }

        public Short getAccountStatus() {
			return accountStatus;
		}

		public void setAccountStatus(Short accountStatus) {
			this.accountStatus = accountStatus;
		}
		
        @Override
        public void prepare() throws Exception {
        
        super.prepare();
        IUser loginUser = this.getLoginUser();
        if(loginUser!=null)this.logined=true;
        }
		

        
        private String buildTable(TableFacade tableFacade) {
        	final HttpServletRequest request = this.getServletRequest();
    		tableFacade.setColumnProperties("id", "name","level","inited","lastMonthDebitBalance",
    				"lastMonthCreditBalance","lastDate","debitBalance",
    				"creditBalance", "operator");

    		HtmlTable table = (HtmlTable) tableFacade.getTable();
    		table.getTableRenderer().setWidth("100%");//475px
    		HtmlRow row = table.getRow();
    		row.setFilterable(Boolean.FALSE);
    		MouseRowEvent onmouseout = new MouseRowEvent();
    		onmouseout.setStyleClass("odd");
    		MouseRowEvent onmouseover = new MouseRowEvent();
    		onmouseout.setStyleClass("even");
    		row.setOnmouseout(onmouseout);
    		
    		HtmlColumn id = row.getColumn("id");
    		id.setFilterable(false);
    		id.setTitle("帐号");
    		//id.setSortable(Boolean.FALSE);
    		HtmlColumn firstName = row.getColumn("name");
    		firstName.setTitle("姓名");

    		HtmlColumn genderAction = row.getColumn("level");
    		genderAction.setTitle("科目级别");
    		genderAction.setSortable(Boolean.FALSE);
    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null
    						&& value instanceof FinanceLevel) {
    					if(FinanceLevel.ONE.equals(value)){
    						html.append("一级科目");
    					}
    					if(FinanceLevel.TWO.equals(value)){
    						html.append("二级科目");
    					}
    				} else
    					html.append(value);
    				return html.toString();
    			}
    		});
    		
    		genderAction = row.getColumn("inited");
    		genderAction.setTitle("初始化");
    		genderAction.setSortable(Boolean.FALSE);
    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null
    						&& value instanceof Number ) {
    					if(((Number)value).intValue()>0)
    					html.append("是");
    					else html.append("否");
    				}
    				return html.toString();
    			}
    		});
    		
    		genderAction = row.getColumn("lastMonthDebitBalance");
    		genderAction.setTitle("上次借方余额");
    		genderAction.setSortable(Boolean.FALSE);
    		
    		genderAction = row.getColumn("lastMonthCreditBalance");
    		genderAction.setTitle("上次贷方余额");
    		genderAction.setSortable(Boolean.FALSE);
    	
    		genderAction = row.getColumn("lastDate");
    		genderAction.setTitle("上次日期");
    		genderAction.setSortable(Boolean.FALSE);
    		final Format datefm=new SimpleDateFormat("yyyy-MM-dd");
    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null ) {
    					html.append(datefm.format(value));
    				} else
    					html.append("-");
    				return html.toString();
    			}
    		});
    		
    		genderAction = row.getColumn("debitBalance");
    		genderAction.setTitle("借方余额");
    		genderAction.setSortable(Boolean.FALSE);
    		
    		genderAction = row.getColumn("creditBalance");
    		genderAction.setTitle("贷方余额");
    		genderAction.setSortable(Boolean.FALSE);
    		
    		HtmlColumn operatorAction = row.getColumn("operator");
    		operatorAction.setTitle("操作");
    		operatorAction.setSortable(Boolean.FALSE);
    		
    		operatorAction.getCellRenderer().setCellEditor(new CellEditor() {
    			
    			public Object getValue(Object item, String property, int rowcount) {
    				
    				HtmlBuilder html = new HtmlBuilder();
    				Object id = ItemUtils.getItemValue(item, "id");
    				Object inited = ItemUtils.getItemValue(item, "inited");
    				if(inited==null || ((Number)inited).intValue()<1){
    				String js = " onclick='return del(\"tableId\",\"" +id + "\");'"; //
    				html.a().append(js).href().quote().append(
    						request.getContextPath()
    								+ "/deleteTAccount.action?queryUserId=" + id).quote()
    						.close();
    				/*html.img().src(request.getContextPath() + "/images/pencil.png")
    						.border("none").end();*/
    				html.append("删除");
    				html.aEnd();
    				html.append("&#160;");
    				//<a href="javascript:void(0)" href1="<s:property value="#url"/>"  class="ymPrompt" title="修改用户">修改</a>
    				html.a().href().quote().append("javascript:void(0)").quote().append( " href1=\""+request.getContextPath()
    								+ "/TAccountEdit.action?queryUserId=" + id+"\"").styleClass("ymPrompt").title("修改T帐")
    						.close();
    				html.append("修改");
    				html.aEnd();
    				}else html.append("-");
    				return html.toString();
    				
    			}
    		});
    		
    		return tableFacade.render();
    	}
        
        
        public void setUserManager(TAccountManagerService manager){
        	this.accountManager=manager;
        }
        
       
}
