package com.samtech.finance.web.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.Order;
import org.jmesa.limit.RowSelect;
import org.jmesa.limit.Sort;
import org.jmesa.limit.SortSet;
import org.jmesa.util.ItemUtils;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlBuilder;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.event.MouseRowEvent;

import com.opensymphony.xwork2.inject.Inject;
import com.samtech.business.dao.UserBaseDao;
import com.samtech.business.database.Employee;
import com.samtech.business.database.Gender;
import com.samtech.business.domain.User;
import com.samtech.business.service.UserManagerService;
import com.samtech.common.domain.IUser;
import com.samtech.common.domain.PagingAndSorting;
import com.samtech.common.domain.SortColumn;



public class UserManagerListAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
		private boolean logined = false;
        private String queryUserId, queryName;
        private InputStream pgtableResult;
        private UserManagerService userManager;
        @Inject(value="UserBaseDao")
        UserBaseDao userBaseDao;
        
		private void setPgInputStream(InputStream in) {
    		pgtableResult = in;
    	}

    	public InputStream getPgInputStream() {
    		
    		return pgtableResult;
    	}
    	public String doQuery(){
    		checkError();
    		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
    		Collection<String> actionErrors = this.getActionErrors();
			if(fieldErrors!=null && !fieldErrors.isEmpty()){
				return INPUT;
			}
			
    		String tblid = "user_tbl";
    		HttpServletRequest request = this.getServletRequest();
    		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
    		
    		tableFacade.setMaxRows(10);
    		String qname=this.queryName;
    		String qid=this.queryUserId;
    		initTable(tableFacade, qname, qid);
    		final String buildTable = buildTable(tableFacade);
			request.setAttribute("etairport_tbl", buildTable);
    		
    		return SUCCESS;
    	}
        
    protected void checkError(){
        String id = this.getQueryUserId();
 	   String qname = this.getQueryName();
 	   if(StringUtils.isBlank( id) && StringUtils.isBlank( qname) ){
 		   this.addFieldError("queryName",  "请输入名字");
 		   this.addFieldError("queryUserId",  "请输入登录ID");
 		   
 	   }
    }

    @SuppressWarnings("unchecked")
	public String paging() {
		HttpServletRequest request = this.getServletRequest();
		String tblid = "user_tbl";
		String qname = request.getParameter(tblid + "_q_name");
		String qid = request.getParameter(tblid + "_q_id");
		HttpSession session = request.getSession();
		if (session != null) {
				qname = (String) session.getAttribute(tblid + "_q_name");
				qid = (String) session.getAttribute(tblid + "_q_id");
		}
		
		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
		tableFacade.setStateAttr("restore");
		tableFacade.setMaxRows(10);
		initTable(tableFacade, qname, qid);
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
	private void initTable(TableFacade tableFacade, String qname, String qid) {
		Map<String, Object> param = new HashMap(2);
		PagingAndSorting pg = new PagingAndSorting();

		String sql = "select o from " + Employee.class.getName()
				+ " as o ";
		StringBuffer wbuf=new StringBuffer();
		if(qname!=null){
			if(wbuf.length()>0)wbuf.append(" and ");
			wbuf.append("o.name=:p_name");
			param.put("p_name", qname);
		}
		if(qid!=null){
			if(wbuf.length()>0)wbuf.append(" and ");
			wbuf.append("o.employeeId=:p_id");
			param.put("p_id", qid);
		}
		if(wbuf.length()>0){
			sql+=" where "+wbuf.toString();
		}
		Limit limit = tableFacade.getLimit();
		SortSet sortSet = limit.getSortSet();
		if (sortSet != null) {
			Collection<Sort> sorts = sortSet.getSorts();
			pg.setAlias("o");
			if (sorts != null) {
				List<SortColumn> columns=new ArrayList<SortColumn>(5);
				for (Iterator iterator = sorts.iterator(); iterator.hasNext();) {
					Sort sort = (Sort) iterator.next();
					Order order = sort.getOrder();
					com.samtech.common.domain.Order o=null;
					String s = "";
					if (order.equals(Order.ASC)){
						s = "asc";
						o=com.samtech.common.domain.Order.ASC;
					}
					if (order.equals(Order.DESC)){
						s = "desc";
						o=com.samtech.common.domain.Order.DESC;
					}
					int position = sort.getPosition();
					String property = sort.getProperty();
					System.out.println("{property:'" + property + "',position:"
							+ position + ",order:'" + s + "'}");
					if(property!=null && o!=null){
						SortColumn c = new SortColumn(property,o);
						c.setPrior(position);
						columns.add(c);
					}
				}
				if(columns.isEmpty()){
					Collections.sort(columns);
					for(int ik=0;ik<columns.size();ik++){
						pg.addSortColumn(columns.get(ik));
					}
				}
			}
		}
		List<User> users = this.getUserBaseDao().query(sql, param, pg);
		tableFacade.setTotalRows(users.size());
		tableFacade.setMaxRowsIncrements(10, 20, 50);
		
		RowSelect rowSelect = limit.getRowSelect();
		
		if (rowSelect != null) {
			int rowStart = rowSelect.getRowStart();
			int rowEnd = rowSelect.getRowEnd();
			int page = rowSelect.getPage();
			System.out.println("rowstart=" + rowStart + ";rowend=" + rowEnd
					+ ";page=" + page);
			ArrayList rs = new ArrayList(10);
			for (int i = rowStart; i < rowEnd && i < users.size(); i++) {
				rs.add(users.get(i));
			}
			tableFacade.setItems(rs);
		}
	}
    

		public boolean isLogined() {
                return logined;
        }

        public String getQueryUserId() {
                return queryUserId;
        }

        public void setQueryUserId(String password) {
                this.queryUserId = password;
        }

        public String getQueryName() {
                return queryName;
        }

        public void setQueryName(String username) {
                this.queryName = username;
        }

       
        @Override
        public void prepare() throws Exception {
        
        super.prepare();
        IUser loginUser = this.getLoginUser();
        if(loginUser!=null)this.logined=true;
        }
		

        
        private String buildTable(TableFacade tableFacade) {
        	final HttpServletRequest request = this.getServletRequest();
    		tableFacade.setColumnProperties("employeeId", "name", "gender",
    				"expireDate", "operator");

    		HtmlTable table = (HtmlTable) tableFacade.getTable();
    		table.getTableRenderer().setWidth("350px");
    		HtmlRow row = table.getRow();
    		row.setFilterable(Boolean.FALSE);
    		MouseRowEvent onmouseout = new MouseRowEvent();
    		onmouseout.setStyleClass("odd");
    		MouseRowEvent onmouseover = new MouseRowEvent();
    		onmouseout.setStyleClass("even");
    		row.setOnmouseout(onmouseout);
    		// table.setCaption("测试用户信息列表");

    		HtmlColumn id = row.getColumn("employeeId");
    		id.setFilterable(false);
    		id.setTitle("登录ID");
    		//id.setSortable(Boolean.FALSE);
    		HtmlColumn firstName = row.getColumn("name");
    		firstName.setTitle("姓名");

    		HtmlColumn genderAction = row.getColumn("gender");
    		genderAction.setTitle("性别");
    		genderAction.setSortable(Boolean.FALSE);

    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null
    						&& value instanceof Gender) {
    					if(Gender.MALE.equals(value)){
    						html.append("男");
    					}
    					if(Gender.FEMALE.equals(value)){
    						html.append("女");
    					}
    				} else
    					html.append(value);
    				return html.toString();
    			}
    		});
    		
    		HtmlColumn fltdateAction = row.getColumn("expireDate");
    		fltdateAction.setTitle("有效期至");
    		
    		fltdateAction.getCellRenderer().setCellEditor(new CellEditor() {
    			private Format sm = new SimpleDateFormat("yyyy-MM-dd");

    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				html.append(sm.format(value));
    				return html.toString();
    			}
    		});
    		HtmlColumn operatorAction = row.getColumn("operator");
    		operatorAction.setTitle("操作");
    		operatorAction.setSortable(Boolean.FALSE);
    		
    		operatorAction.getCellRenderer().setCellEditor(new CellEditor() {
    			
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				Object id = ItemUtils.getItemValue(item, "employeeId");
    				String js = " onclick='javascript:del(\"tableId\"," + id + ")'"; //
    				html.a().append(js).href().quote().append(
    						request.getContextPath()
    								+ "/deleteUser.action?queryUserId=" + id).quote()
    						.close();
    				/*html.img().src(request.getContextPath() + "/images/pencil.png")
    						.border("none").end();*/
    				html.append("删除");
    				html.aEnd();
    				html.append("#160");
    				//<a href="javascript:void(0)" href1="<s:property value="#url"/>"  class="ymPrompt" title="修改用户">修改</a>
    				html.a().href().quote().append("javascript:void(0)").quote().append( " href1=\""+request.getContextPath()
    								+ "/modifyUser.action?queryUserId=" + id+"\"").styleClass("ymPrompt").title("修改用户")
    						.close();
    				html.append("修改");
    				html.aEnd();
    				
    				return html.toString();
    				
    			}
    		});
    		
    		return tableFacade.render();
    	}
        
        
        public void setUserManager(UserManagerService manager){
        	this.userManager=manager;
        }
        
        private UserBaseDao getUserBaseDao() {
			return userBaseDao;
		}

		public void setUserBaseDao(UserBaseDao userBaseDao) {
			this.userBaseDao = userBaseDao;
		}
}
