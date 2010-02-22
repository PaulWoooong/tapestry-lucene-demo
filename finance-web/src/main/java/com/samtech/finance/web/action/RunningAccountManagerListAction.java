package com.samtech.finance.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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

import com.samtech.common.domain.IUser;
import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.domain.RunningAccountHistory;
import com.samtech.finance.service.FinanceService;
import com.samtech.finance.web.anontation.Protected;


@Protected
public class RunningAccountManagerListAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
		private boolean logined = false;
		private String queryFinanceFormId;
        private String  queryName;
        private Integer accountId;
        private Date startDate,endDate;
        
        private InputStream pgtableResult;
        private InputStream excelInputStream;
        
		private static String tblid = "racc_tbl";
        private List<RunningAccountHistory> accs;
        private boolean showtable;
        //private TAccountManagerService accountManager;
        private FinanceService financeManager;		
		
		
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
			showtable=false;
    		//String tblid = "racc_tbl";
    		HttpServletRequest request = this.getServletRequest();
    		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
    		
    		//tableFacade.setMaxRows(10);
    		
    		HttpSession session = request.getSession();
    		if (session != null) {
    				session.setAttribute(tblid + "_q_name",this.queryName);
    				if(this.accountId!=null)
    				session.setAttribute(tblid + "_q_id",this.accountId);
    				session.setAttribute(tblid + "_q_fid",this.queryFinanceFormId);
    				if (startDate != null) {
    					session.setAttribute(tblid + "_sdate", startDate);
    				}
    				if (endDate != null) {
    					session.setAttribute(tblid + "_edate", endDate);
    				}
    		}
    		isMaxRow=true;
    		String qname=this.queryName;
    		Integer qid=accountId;//this.queryAccountId;
    		
    		tableFacade.setStateAttr("restore");
    		tableFacade.setMaxRows(10);		
    		tableFacade.setMaxRowsIncrements(10, 20, 50);    
    		initTable(tableFacade, qname, this.queryFinanceFormId,qid,startDate,endDate);
    		if(accs==null || accs.isEmpty()){
    			this.addActionError("查询没有记录！");
    		}else{
    			showtable=true;
    		final String buildTable = buildTable(tableFacade);
			request.setAttribute("user_tbl", buildTable);
    		}
    		return SUCCESS;
    	}
    	
    	
    	
    	

	

	protected void checkError(){
      
 	  
    }
	
	boolean isMaxRow=false;
    @SuppressWarnings("unchecked")
	public String paging() {
		HttpServletRequest request = this.getServletRequest();
		Integer accountId=null;
		
		String qname = null;
		String qfid =null;
		Date sDate = null, eDate = null;
		HttpSession session = request.getSession();
		if (session != null) {
				qname = (String) session.getAttribute(tblid + "_q_name");
				accountId = (Integer) session.getAttribute(tblid + "_q_id");
				
				session.setAttribute(tblid + "_q_name",this.queryName);
				
				
				qfid=(String)session.getAttribute(tblid + "_q_fid");
				
				Object o = session.getAttribute(tblid + "_sdate");
				if (o != null)
					sDate = (Date) o;
				o = session.getAttribute(tblid + "_edate");
				if (o != null)
					eDate = (Date) o;
				
		}
		Map parameterMap = request.getParameterMap();
		String maxrowkey=tblid+"_" + Action.MAX_ROWS.toParam();
		String pagekey=tblid+"_" + Action.PAGE.toParam();
		//String sortkey=tblid+"_" + Action.SORT.toParam();
		
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
		showtable=true;
		initTable(tableFacade, qname,qfid,accountId,sDate,eDate);
		final String buildTable = buildTable(tableFacade);
		ByteArrayInputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(buildTable.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.setPgInputStream(inputStream);
		return "pgresult";
	}
public String exportExcel(){
		
		List<RunningAccountHistory> dataList=null;
		HttpServletRequest request = this.getServletRequest();
		Integer accountId=null;
		
		String qname = null;
		String qfid =null;
		Date sDate = null, eDate = null;
		HttpSession session = request.getSession();
		if (session != null) {
				qname = (String) session.getAttribute(tblid + "_q_name");
				accountId = (Integer) session.getAttribute(tblid + "_q_id");
				
				session.setAttribute(tblid + "_q_name",this.queryName);
				
				
				qfid=(String)session.getAttribute(tblid + "_q_fid");
				
				Object o = session.getAttribute(tblid + "_sdate");
				if (o != null)
					sDate = (Date) o;
				o = session.getAttribute(tblid + "_edate");
				if (o != null)
					eDate = (Date) o;
				
		}
		
		try {
			
			dataList =this.financeManager.findRunningAccount(qfid, accountId, qname, null, sDate, eDate);
			
			
			if(dataList==null || dataList.isEmpty())return INPUT;
			this.printExcel(dataList);
		} catch (RowsExceededException e) {
			
			e.printStackTrace();
			return INPUT;
		} catch (BiffException e) {
			
			e.printStackTrace();
			return INPUT;
		} catch (WriteException e) {
			
			e.printStackTrace();
			return INPUT;
		} catch (IOException e) {
			
			e.printStackTrace();
			return INPUT;
		}
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	private void initTable(TableFacade tableFacade, String context,
				String financeFormId, Integer accid, Date startDate,
				Date endDate) {
		if(StringUtils.isBlank(context))
			context=null;
		if(StringUtils.isBlank(financeFormId))
			financeFormId=null;	
		accs =this.financeManager.findRunningAccount(financeFormId, accid, context, null, startDate, endDate);
		tableFacade.setTotalRows(accs.size());
		tableFacade.setMaxRowsIncrements(10, 20, 50);    
		
		
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
	private String getExcelFileName() {
		return "runnings.xls";
	}
	private void printExcel(List<RunningAccountHistory> dataList) throws BiffException, IOException, RowsExceededException, WriteException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		
		WritableWorkbook workbook = Workbook.createWorkbook(baos);
		String version = Workbook.getVersion();
		System.out.println("version="+version);
		excelInputStream=null;
		
		// 创建工作表实例
		WritableSheet sheet = workbook.createSheet("sss",0);
		// HSSFSheet sheet = workbook.createSheet("sxtaExcel");
		// 记账凭证 T账号 摘要 借/贷 金额 日期 状态 公司
		
		// 获取样式
		/*SheetSettings settings = sheet.getSettings();
		if(settings!=null){
			sheet.get
		}*/
		String[] fieldNames={"记账凭证","T账号","摘要","借/贷","金额","日期","状态","公司"};
		if (dataList != null && !dataList.isEmpty()) {
			
			Format fm=new SimpleDateFormat("yyyy年MM月dd日");
			WritableCellFormat labelCellFormat = new WritableCellFormat(NumberFormats.DEFAULT);
			WritableCellFormat numberCellFormat = new WritableCellFormat(NumberFormats.THOUSANDS_FLOAT);
			
			// 给excel填充数据
			for(int j=0;j<fieldNames.length;j++){
				Label l = new Label(j,0,fieldNames[j],labelCellFormat);//sheet.getWritableCell(j, 0);
				sheet.addCell(l);
			}
			
			for (int i = 0; i < dataList.size(); i++) {
				// 将dataList里面的数据取出来
				RunningAccountHistory form = dataList.get(i);
				
				Label label =null;
				
				label=new Label(0,i+1,form.getFinanceId(),labelCellFormat);
				sheet.addCell(label);
				StringBuffer buf=new StringBuffer();
				if(form.getAccountId()!=null)buf.append(form.getAccountId());
				if(form.getAccountName()!=null)buf.append(form.getAccountName().trim());
				label=new Label(1,i+1,buf.toString(),labelCellFormat);
				sheet.addCell(label);
				label=new Label(2,i+1,form.getContext(),labelCellFormat);
				sheet.addCell(label);
				String s="";
				if(form.getDirect()!=null){
					if(form.getDirect().equals(BalanceDirect.DEBIT))s="借";
					if(form.getDirect().equals(BalanceDirect.CREDIT))s="贷";
				}
				label=new Label(3,i+1,s,labelCellFormat);
				sheet.addCell(label);
				Number number = new Number(4,i+1,form.getAmount()!=null?form.getAmount().doubleValue():0,numberCellFormat);
				sheet.addCell(label);
				label=new Label(5,i+1,fm.format(form.getBizDate()),labelCellFormat);
				sheet.addCell(label);
				if(form.getStatus()!=null){
					if(AccountStatus.PENDING.equals(form.getStatus()))
    					s=("挂靠");
    					else if(AccountStatus.PRREBACK.equals(form.getStatus()))
        					s=("冲账待核");
    					else if(AccountStatus.NORMAL.equals(form.getStatus()))
        					s=("已核");
    					else if(AccountStatus.REBACK.equals(form.getStatus()))
        					s=("冲账已核");
				}
				label=new Label(6,i+1,s,labelCellFormat);
				sheet.addCell(label);
				label=new Label(7,i+1,form.getCompanyId()!=null?form.getCompanyId():"",labelCellFormat);
				sheet.addCell(label);
			}
			
			String fileNames = getExcelFileName();
			this
					.getServletResponse()
					.addHeader(
							"Content-disposition",
							"attachment;filename=\""
									+ new String(fileNames.getBytes(), "ISO-8859-1")
									+ "\"");

			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write();
			baos.flush();
			workbook.close();
			byte[] aa = baos.toByteArray();
			excelInputStream = new ByteArrayInputStream(aa, 0, aa.length);

		}
		
	}

		public boolean isLogined() {
                return logined;
        }

        

        public String getQueryName() {
                return queryName;
        }

        public void setQueryName(String username) {
                this.queryName = username;
        }

       
		
        public String getQueryFinanceFormId() {
			return queryFinanceFormId;
		}

		public void setQueryFinanceFormId(String queryFinanceFormId) {
			this.queryFinanceFormId = queryFinanceFormId;
		}

		public Integer getAccountId() {
			return accountId;
		}

		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		
		public InputStream getExcelInputStream() {
			return excelInputStream;
		}

		public void setExcelInputStream(InputStream excelInputStream) {
			this.excelInputStream = excelInputStream;
		}

		public boolean isShowTable(){
			return showtable;
		}
		@Override
        public void prepare() throws Exception {
        
        super.prepare();
        IUser loginUser = this.getLoginUser();
        if(loginUser!=null)this.logined=true;
        }
		

        
        private String buildTable(TableFacade tableFacade) {
        	final HttpServletRequest request = this.getServletRequest();
    		tableFacade.setColumnProperties("financeId", "accountName","context","direct","amount",
    				"bizDate","status","companyId", "operator");

    		HtmlTable table = (HtmlTable) tableFacade.getTable();
    		table.getTableRenderer().setWidth("100%");//475px
    		HtmlRow row = table.getRow();
    		row.setFilterable(Boolean.FALSE);
    		MouseRowEvent onmouseout = new MouseRowEvent();
    		    		
    		onmouseout.setStyleClass("even");
    		row.setOnmouseout(onmouseout);
    		
    		HtmlColumn id = row.getColumn("financeId");
    		id.setFilterable(false);
    		id.setTitle("记账凭证");
    		 id = row.getColumn("context");
    		id.setFilterable(false);
    		id.setTitle("摘要");
    		id.getCellRenderer().setStyle("overflow:hidden");
    		
    		//id.setSortable(Boolean.FALSE);
    		HtmlColumn firstName = row.getColumn("accountName");
    		firstName.setTitle("T账号");
    		firstName.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				Object acc = new BasicCellEditor().getValue(item, "accountId",
    						rowcount);
    				if (value != null &&
    						StringUtils.isNotBlank((String)value)) {
    					html.append(acc).append(value);
    				} else
    					html.append(acc);
    				return html.toString();
    			}
    		});
    		
    		HtmlColumn genderAction = row.getColumn("direct");
    		genderAction.setTitle("借/贷");
    		genderAction.setSortable(Boolean.FALSE);
    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null
    						&& value instanceof BalanceDirect) {
    					if(BalanceDirect.DEBIT.equals(value)){
    						html.append("借");
    					}
    					if(BalanceDirect.CREDIT.equals(value)){
    						html.append("贷");
    					}
    				} else
    					html.append(value);
    				return html.toString();
    			}
    		});
    		
    		genderAction = row.getColumn("status");
    		genderAction.setTitle("状态");
    		genderAction.setSortable(Boolean.FALSE);
    		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
    			public Object getValue(Object item, String property, int rowcount) {
    				Object value = new BasicCellEditor().getValue(item, property,
    						rowcount);
    				HtmlBuilder html = new HtmlBuilder();
    				
    				if (value != null
    						&& value instanceof AccountStatus ) {
    					if(AccountStatus.PENDING.equals(value))
    					html.append("挂靠");
    					else if(AccountStatus.PRREBACK.equals(value))
        					html.append("冲账待核");
    					else if(AccountStatus.NORMAL.equals(value))
        					html.append("已核");
    					else if(AccountStatus.REBACK.equals(value))
        					html.append("冲账已核");
    					else html.append(value.toString());
    				}
    				return html.toString();
    			}
    		});
    		
    		genderAction = row.getColumn("amount");
    		genderAction.setTitle("金额");
    		genderAction.setSortable(Boolean.FALSE);
    		
    		genderAction = row.getColumn("companyId");
    		genderAction.setTitle("公司");
    		genderAction.setSortable(Boolean.FALSE);
    	
    		genderAction = row.getColumn("bizDate");
    		genderAction.setTitle("日期");
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
    		
    		
    		
    		HtmlColumn operatorAction = row.getColumn("operator");
    		operatorAction.setTitle("操作");
    		operatorAction.setSortable(Boolean.FALSE);
    		
    		operatorAction.getCellRenderer().setCellEditor(new CellEditor() {
    			
    			public Object getValue(Object item, String property, int rowcount) {
    				
    				HtmlBuilder html = new HtmlBuilder();
    				Object id = ItemUtils.getItemValue(item, "financeId");
    				
    				Object inited = ItemUtils.getItemValue(item, "status");
    				if (inited == null || AccountStatus.PENDING.equals( inited)||AccountStatus.PRREBACK.equals( inited) ) {
    					String js = " onclick='return del(\"tableId\",\"" + id
    							+ "\");'"; //
    					html
    							.a()
    							.append(js)
    							.href()
    							.quote()
    							.append(
    									request.getContextPath()
    											+ "/deleteFinanceForm.action?queryFinanceFormId="
    											+ id).quote().close();
    					
    					html.append("删除");
    					html.aEnd();
    					html.append("&#160;");
    					
    					html
    							.a()
    							.href()
    							.quote()
    							.append("javascript:void(0)")
    							.quote()
    							.append(
    									" href1=\""
    											+ request.getContextPath()
    											+ "/FinanceFormEdit.action?queryFinanceFormId="
    											+ id + "\"").styleClass("ymPrompt")
    							.title("修改凭证").close();
    					html.append("修改");
    					html.aEnd();
    				}else html.append("-");
    				return html.toString();
    				
    			}
    		});
    		
    		return tableFacade.render();
    	}
        
        
       /* public void setAccountManager(TAccountManagerService manager){
        	this.accountManager=manager;
        }*/
        public void setFinanceManager(FinanceService manager) {
    		this.financeManager = manager;
    	}
       
}
