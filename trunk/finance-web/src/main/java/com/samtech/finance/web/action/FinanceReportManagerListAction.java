package com.samtech.finance.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeImpl;
import org.jmesa.limit.Action;
import org.jmesa.limit.Limit;
import org.jmesa.limit.RowSelect;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.component.HtmlRow;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.event.MouseRowEvent;

import com.samtech.common.domain.IUser;
import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.domain.AccountData;
import com.samtech.finance.domain.MonthReportData;
import com.samtech.finance.domain.RunningAccountHistory;
import com.samtech.finance.service.FinanceService;
import com.samtech.finance.web.anontation.Protected;


@Protected
public class FinanceReportManagerListAction extends AbstractAction  {

        /**
         * 
         */
        private static final long serialVersionUID = 2414729349608270422L;
		private boolean logined = false;
		
        private Integer year;
        private Integer month;
       
        
        private InputStream pgtableResult;
        private InputStream excelInputStream;
        
		private static String tblid = "finreport_tbl";
		private MonthReportData result;
        private List<AccountData> accs;
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
    		if(this.year==null || this.month==null){
    			this.addFieldError("year", "please input year and month.");
      		}
    		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
    		//Collection<String> actionErrors = this.getActionErrors();
			if(fieldErrors!=null && !fieldErrors.isEmpty()){
				return INPUT;
			}
			showtable=false;
			Integer imonth=this.month;
			if(this.month!=null)
			if(Calendar.JANUARY==0)
			imonth=this.month-1;
    		//String tblid = "racc_tbl";
    		HttpServletRequest request = this.getServletRequest();
    		TableFacade tableFacade = new TableFacadeImpl(tblid, request);
    		
    		//tableFacade.setMaxRows(10);
    		
    		HttpSession session = request.getSession();
    		if (session != null) {
    				session.setAttribute(tblid + "_q_year",this.year);
    				if(this.month!=null)
    				session.setAttribute(tblid + "_q_month",imonth);
    				
    		}
    		
    		isMaxRow=true;
    		
    		
    		tableFacade.setStateAttr("restore");
    		tableFacade.setMaxRows(10);		
    		tableFacade.setMaxRowsIncrements(10, 20, 50);    
    		initTable(tableFacade, this.year,imonth);
    		if(accs==null || accs.isEmpty()){
    			this.addActionError("查询没有记录！");
    		}else{
    			showtable=true;
    		final String buildTable = buildTable(tableFacade);
			request.setAttribute("user_tbl", buildTable);
    		}
    		return SUCCESS;
    	}
    	public String doConfirm(){
    		checkError();
    		if(this.year==null || this.month==null){
    			this.addFieldError("year", "please input year and month.");
    			
    		}
    		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
    		//Collection<String> actionErrors = this.getActionErrors();
			if(fieldErrors!=null && !fieldErrors.isEmpty()){
				return INPUT;
			}
			showtable=false;
			Integer imonth=this.month;
			if(this.month!=null)
			if(Calendar.JANUARY==0)
			imonth=this.month-1;
			HttpServletRequest request = this.getServletRequest();
			HttpSession session = request.getSession();
    		if (session != null) {
    				session.removeAttribute(tblid + "_q_year");
     				session.removeAttribute(tblid + "_q_month");
    				
    		}
    		try {
				this.financeManager.confirmMonthReport(this.year, imonth);
			} catch (FinanceRuleException e) {
				String message = e.getMessage();
				e.printStackTrace();
				if(message!=null)
				this.addActionError(message);
				else
					this.addActionError(e.getErrorCode().toString());
			}catch(Exception ex){
				ex.printStackTrace();
				this.addActionError(ex.getMessage());
			}
    		
    		return SUCCESS;
    	}
    	public String refuse(){
    		checkError();
    		if(this.year==null || this.month==null){
    			this.addFieldError("year", "please input year and month.");
     		}
    		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
    		//Collection<String> actionErrors = this.getActionErrors();
			if(fieldErrors!=null && !fieldErrors.isEmpty()){
				return INPUT;
			}
			showtable=false;
			Integer imonth=this.month;
			if(this.month!=null)
			if(Calendar.JANUARY==0)
			imonth=this.month-1;
			HttpServletRequest request = this.getServletRequest();
			HttpSession session = request.getSession();
    		if (session != null) {
    				session.removeAttribute(tblid + "_q_year");
     				session.removeAttribute(tblid + "_q_month");
    				
    		}
    		try {
				this.financeManager.refuseMonthReport(this.year, imonth);
			} catch (FinanceRuleException e) {
				String message = e.getMessage();
				e.printStackTrace();
				if(message!=null)
				this.addActionError(message);
				else
					this.addActionError(e.getErrorCode().toString());
			}catch(Exception ex){
				ex.printStackTrace();
				this.addActionError(ex.getMessage());
			}
    		
    		return SUCCESS;
    	}
    	
    	public static class LevelValue {
    		private String key, value;

    		public LevelValue() {

    		}

    		LevelValue(String key, String value) {
    			this.key = key;
    			this.value = value;
    		}

    		public String getKey() {
    			return key;
    		}

    		public void setKey(String key) {
    			this.key = key;
    		}

    		public String getValue() {
    			return value;
    		}

    		public void setValue(String value) {
    			this.value = value;
    		}

    	}
    	
    	public List<LevelValue> getGenEnum(){
    		List<LevelValue> values=new ArrayList<LevelValue>();
    		Calendar cld = Calendar.getInstance();
    		int year = cld.get(Calendar.YEAR);
    		for(int i=year-2;i<=year;i++)
    		values.add(new LevelValue(""+i, ""+i));
    		
    		return values;
    	}
	

	protected void checkError(){
      
 	  
    }
	
	boolean isMaxRow=false;
    @SuppressWarnings("unchecked")
	public String paging() {
		HttpServletRequest request = this.getServletRequest();
		Integer year=null;
		Integer month=null;
		
		HttpSession session = request.getSession();
		if (session != null) {
				year = (Integer) session.getAttribute(tblid + "_q_year");
				month = (Integer) session.getAttribute(tblid + "_q_month");
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
		initTable(tableFacade, year,month);
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
		
		List<AccountData> dataList=null;
		HttpServletRequest request = this.getServletRequest();
		Integer year=null;
		Integer month=null;
		
		HttpSession session = request.getSession();
		if (session != null) {
				year = (Integer) session.getAttribute(tblid + "_q_year");
				month = (Integer) session.getAttribute(tblid + "_q_month");
		}
		
		try {
			
			result =this.financeManager.getMonthReport(year, month);
			dataList=result.getAccounts();
			
			if(dataList==null)return INPUT;
			this.printExcel(result);
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
	private void initTable(TableFacade tableFacade,  Integer year, Integer month) {
		
		result =this.financeManager.getMonthReport(year, month);
		if(result==null || result.getAccounts()==null) return;
		accs=result.getAccounts();
		AccountData totalsum=new AccountData();
		totalsum.setAccountName("total sum");
		totalsum.setAccountId(Integer.valueOf(0));
		totalsum.setDebit(result.getDebit());
		totalsum.setCredit(result.getCredit());
		accs.add(totalsum);
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
		return "month.xls";
	}
	private void printExcel(MonthReportData report) throws BiffException, IOException, RowsExceededException, WriteException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		
		
		WritableWorkbook workbook = Workbook.createWorkbook(baos);
		String version = Workbook.getVersion();
		System.out.println("version="+version);
		excelInputStream=null;
		List<AccountData> dataList = report.getAccounts();
		// 创建工作表实例
		WritableSheet sheet = workbook.createSheet("sss",0);
		// HSSFSheet sheet = workbook.createSheet("sxtaExcel");
		// 记账凭证 T账号 摘要 借/贷 金额 日期 状态 公司
		
		// 获取样式
		/*SheetSettings settings = sheet.getSettings();
		if(settings!=null){
			sheet.get
		}*/
		String[] fieldNames={"T账号","名称","借金额","贷金额"};
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
				AccountData form = dataList.get(i);
				
				Label label =null;
				
				label=new Label(0,i+1,form.getAccountId()!=null?form.getAccountId().toString():"",labelCellFormat);
				sheet.addCell(label);
				
				label=new Label(1,i+1,form.getAccountName(),labelCellFormat);
				sheet.addCell(label);
				
				
				Number number =null;
				
					number=new Number(2,i+1,form.getDebit()!=null?form.getDebit().doubleValue():0,numberCellFormat);
					sheet.addCell(number);
					number=new Number(3,i+1,form.getCredit()!=null?form.getCredit().doubleValue():0,numberCellFormat);
					sheet.addCell(number);
			}
			AccountData form = new AccountData();
			int kk=dataList.size();
			Label label =null;
			
			label=new Label(0,kk+1,report.getYear()+"/"+(report.getMonth()+1),labelCellFormat);
			sheet.addCell(label);
			
			label=new Label(1,kk+1,"total sum",labelCellFormat);
			sheet.addCell(label);
			
			
			Number number =null;
			
				number=new Number(2,kk+1,report.getDebit()!=null?report.getDebit().doubleValue():0,numberCellFormat);
				sheet.addCell(number);
				number=new Number(3,kk+1,report.getCredit()!=null?report.getCredit().doubleValue():0,numberCellFormat);
				sheet.addCell(number);
				
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

        

      
		public Integer getYear() {
			return year;
		}

		public void setYear(Integer year) {
			this.year = year;
		}

		public Integer getMonth() {
			return month;
		}

		public void setMonth(Integer month) {
			this.month = month;
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
    		tableFacade.setColumnProperties("accountId", "accountName","debit","credit");

    		HtmlTable table = (HtmlTable) tableFacade.getTable();
    		table.getTableRenderer().setWidth("100%");//475px
    		HtmlRow row = table.getRow();
    		row.setFilterable(Boolean.FALSE);
    		MouseRowEvent onmouseout = new MouseRowEvent();
    		    		
    		onmouseout.setStyleClass("even");
    		row.setOnmouseout(onmouseout);
    		
    		HtmlColumn id = row.getColumn("accountId");
    		id.setFilterable(false);
    		id.setTitle("T账号");
    		
    		
    		//id.setSortable(Boolean.FALSE);
    		HtmlColumn genderAction = row.getColumn("accountName");
    		genderAction.setTitle("账号名称");
    		
    		genderAction = row.getColumn("debit");
    		genderAction.setTitle("借方金额");
    		
    		genderAction = row.getColumn("credit");
    		genderAction.setTitle("贷方金额");
    		
    		genderAction.setSortable(Boolean.FALSE);
    		
    		
    		
    		
    		return tableFacade.render();
    	}
        
        
       /* public void setAccountManager(TAccountManagerService manager){
        	this.accountManager=manager;
        }*/
        public void setFinanceManager(FinanceService manager) {
    		this.financeManager = manager;
    	}
       
}
