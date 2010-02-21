package com.samtech.finance.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.CellView;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFeatures;
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
import com.samtech.finance.FinanceRuleException;
import com.samtech.finance.database.AccountStatus;
import com.samtech.finance.database.BalanceDirect;
import com.samtech.finance.domain.Account;
import com.samtech.finance.domain.BalanceItem;
import com.samtech.finance.domain.FinanceForms;
import com.samtech.finance.service.FinanceService;
import com.samtech.finance.service.TAccountManagerService;

public class FinanceFormManagerListAction extends AbstractAction {

	/**
         * 
         */
	private static final long serialVersionUID = 2414729349608270422L;
	private boolean logined = false;
	private String queryFinanceFormId;
	private String bizName;
	private Date startDate, endDate;
	private InputStream pgtableResult;
	private InputStream excelInputStream;

	private static String tblid = "aform_tbl";
	private List<FinanceForms> accs;
	
	private Map<Integer,Account> acc_map=new HashMap<Integer, Account>(20);
	
	private FinanceService financeManager;
	private TAccountManagerService accountManager;
	private boolean showtable;
	// private Short accountStatus;

	public InputStream getExcelInputStream() {
		return excelInputStream;
	}

	public void setExcelInputStream(InputStream excelInputStream) {
		this.excelInputStream = excelInputStream;
	}


	private void setPgInputStream(InputStream in) {
		pgtableResult = in;
	}

	public InputStream getPgInputStream() {

		return pgtableResult;
	}

	public String doQuery() {
		checkError();
		final Map<String, List<String>> fieldErrors = this.getFieldErrors();
		// Collection<String> actionErrors = this.getActionErrors();
		if (fieldErrors != null && !fieldErrors.isEmpty()) {
			return INPUT;
		}
		//String tblid = "aform_tbl";
		HttpServletRequest request = this.getServletRequest();
		TableFacade tableFacade = new TableFacadeImpl(tblid, request);

		// tableFacade.setMaxRows(10);

		HttpSession session = request.getSession();
		if (session != null) {
			session.setAttribute(tblid + "_q_name", this.bizName);
			session.setAttribute(tblid + "_q_id", this.queryFinanceFormId);
			if (startDate != null) {
				session.setAttribute(tblid + "_sdate", startDate);
			}
			if (endDate != null) {
				session.setAttribute(tblid + "_edate", endDate);
			}
			// session.setAttribute(tblid +
			// "_q_status",this.accountStatus);//status = (Short)
			// session.getAttribute(tblid + "_q_status");
		}
		isMaxRow = true;
		String qname = this.bizName;
		String qid = this.queryFinanceFormId;

		tableFacade.setStateAttr("restore");
		tableFacade.setMaxRows(10);
		tableFacade.setMaxRowsIncrements(10, 20, 50);
		initTable(tableFacade, qname, qid, this.startDate, this.endDate);
		if (accs == null || accs.isEmpty()) {
			this.addActionError("查询没有记录！");
		} else {
			final String buildTable = buildTable(tableFacade);
			showtable=true;
			request.setAttribute("user_tbl", buildTable);
		}
		return SUCCESS;
	}

	public String doDelete() {
		if (queryFinanceFormId != null) {

			String qid = this.queryFinanceFormId;
			try {
				this.financeManager.deleteFinanceForm(qid);
				
				this.addActionMessage("删除成功！");
			} catch (FinanceRuleException e) {
				this.addActionError(e.getMessage());
			}
		}
		HttpServletRequest request = this.getServletRequest();
		HttpSession session = request.getSession();
		String qname = null;
		String financeid = null;
		Date sDate = null, eDate = null;

		if (session != null) {
			qname = (String) session.getAttribute(tblid + "_q_name");
			financeid = (String) session.getAttribute(tblid + "_q_id");

			Object o = session.getAttribute(tblid + "_sdate");
			if (o != null)
				sDate = (Date) o;
			o = session.getAttribute(tblid + "_edate");
			if (o != null)
				eDate = (Date) o;
		}
		TableFacade tableFacade = new TableFacadeImpl(tblid, request);

		tableFacade.setMaxRows(10);
		tableFacade.setMaxRowsIncrements(10, 20, 50);
		initTable(tableFacade, qname, financeid, sDate, eDate);
		if (accs == null || accs.isEmpty()) {
			this.addActionError("查询没有记录！");
		} else {
			final String buildTable = buildTable(tableFacade);
			showtable=true;
			request.setAttribute("user_tbl", buildTable);
		}
		return SUCCESS;
	}
	
	public String exportExcel(){
		
		List<FinanceForms> dataList=null;
		try {
			HttpServletRequest request = this.getServletRequest();
			String[] parameterValues = request.getParameterValues("id_check");
			if(parameterValues==null || parameterValues.length==0)
				return INPUT;
			dataList=new ArrayList<FinanceForms>(8);
			for (String p : parameterValues) {
				List<FinanceForms> forms = financeManager.findFinanceForms(p, null, null, null);
				if(forms!=null && !forms.isEmpty())
					dataList.addAll(forms);
			}
			if(dataList.isEmpty())return INPUT;
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
	public boolean isShowTable(){
		return showtable;
	}
	protected void checkError() {

		/*
		 * if(StringUtils.isBlank( id) && StringUtils.isBlank( qname) ){
		 * this.addFieldError("queryName", "请输入名字");
		 * this.addFieldError("queryUserId", "请输入登录ID");
		 * 
		 * }
		 */
	}

	boolean isMaxRow = false;

	@SuppressWarnings("unchecked")
	public String paging() {
		HttpServletRequest request = this.getServletRequest();

		// Short status=null;
		String qname = request.getParameter(tblid + "_q_name");
		String financeid = request.getParameter(tblid + "_q_id");

		Date sDate = null, eDate = null;

		HttpSession session = request.getSession();
		if (session != null) {
			qname = (String) session.getAttribute(tblid + "_q_name");
			financeid = (String) session.getAttribute(tblid + "_q_id");

			Object o = session.getAttribute(tblid + "_sdate");
			if (o != null)
				sDate = (Date) o;
			o = session.getAttribute(tblid + "_edate");
			if (o != null)
				eDate = (Date) o;
		}
		Map parameterMap = request.getParameterMap();
		String maxrowkey = tblid + "_" + Action.MAX_ROWS.toParam();
		String pagekey = tblid + "_" + Action.PAGE.toParam();
		String sortkey = tblid + "_" + Action.SORT.toParam();

		if (parameterMap.containsKey(maxrowkey)) {
			Object object = parameterMap.get(maxrowkey);
			if (object != null) {
				if (object.getClass().isArray()) {
					String[] pvalues = (String[]) object;
					if (!("null".equalsIgnoreCase(pvalues[0].toString()) || ""
							.equals(pvalues[0])))
						isMaxRow = true;
				} else if (!("null".equalsIgnoreCase(object.toString()) || ""
						.equals(object.toString())))
					isMaxRow = true;
			}
			if (!isMaxRow)
				request.setAttribute(maxrowkey, 10);
			object = parameterMap.get(pagekey);

		}

		TableFacade tableFacade = new TableFacadeImpl(tblid, request);

		tableFacade.setStateAttr("restore");

		initTable(tableFacade, qname, financeid, sDate, eDate);
		final String buildTable = buildTable(tableFacade);
		showtable=true;
		ByteArrayInputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(buildTable.getBytes("UTF8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		this.setPgInputStream(inputStream);
		return "pgresult";
	}

	@SuppressWarnings("unchecked")
	private void initTable(TableFacade tableFacade, String bizName,
			String financeformId, Date startDate, Date endDate) {
		if (StringUtils.isBlank(bizName))
			bizName = null;
		if (StringUtils.isBlank(financeformId))
			financeformId = null;
		accs = financeManager.findFinanceForms(financeformId, bizName,
				startDate, endDate);
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

	public boolean isLogined() {
		return logined;
	}

	public String getQueryFinanceFormId() {
		return queryFinanceFormId;
	}

	public void setQueryFinanceFormId(String q) {
		this.queryFinanceFormId = q;
	}

	public String getQueryName() {
		return bizName;
	}

	public void setQueryName(String username) {
		this.bizName = username;
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

	@Override
	public void prepare() throws Exception {

		super.prepare();
		IUser loginUser = this.getLoginUser();
		if (loginUser != null)
			this.logined = true;
	}

	private String buildTable(TableFacade tableFacade) {
		final HttpServletRequest request = this.getServletRequest();
		tableFacade.setColumnProperties("id", "businessId", "bizDate",
				"amount", "operator");

		HtmlTable table = (HtmlTable) tableFacade.getTable();
		table.getTableRenderer().setWidth("100%");// 475px
		HtmlRow row = table.getRow();
		row.setFilterable(Boolean.FALSE);
		MouseRowEvent onmouseout = new MouseRowEvent();
		onmouseout.setStyleClass("odd");
		MouseRowEvent onmouseover = new MouseRowEvent();
		onmouseout.setStyleClass("even");
		row.setOnmouseout(onmouseout);

		HtmlColumn id = row.getColumn("id");
		id.setFilterable(false);
		id.setTitle("凭证号");
		id.getCellRenderer().setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new BasicCellEditor().getValue(item, property,
						rowcount);
				HtmlBuilder html = new HtmlBuilder();

				if (value != null) {
					html.input().type("checkbox").name("id_check").value(
							value.toString()).close();
					html.append(value);
				} else
					html.append("-");
				return html.toString();
			}
		});
		// id.setSortable(Boolean.FALSE);
		HtmlColumn firstName = row.getColumn("businessId");
		firstName.setTitle("原始单号");

		HtmlColumn genderAction = row.getColumn("bizDate");
		genderAction.setTitle("记账日期");
		genderAction.setSortable(Boolean.FALSE);
		final Format datefm = new SimpleDateFormat("yyyy-MM-dd");
		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new BasicCellEditor().getValue(item, property,
						rowcount);
				HtmlBuilder html = new HtmlBuilder();

				if (value != null) {
					html.append(datefm.format(value));
				} else
					html.append("-");
				return html.toString();
			}
		});

		genderAction = row.getColumn("amount");
		genderAction.setTitle("金额");
		genderAction.setSortable(Boolean.FALSE);
		genderAction.getCellRenderer().setCellEditor(new CellEditor() {
			public Object getValue(Object item, String property, int rowcount) {
				Object value = new BasicCellEditor().getValue(item, property,
						rowcount);
				HtmlBuilder html = new HtmlBuilder();

				if (value != null && value instanceof Number) {
					if (((Number) value).intValue() > 0)
						html.append(value);
					else
						html.append("0");
				}
				return html.toString();
			}
		});

		HtmlColumn operatorAction = row.getColumn("operator");
		operatorAction.setTitle("操作");
		operatorAction.setSortable(Boolean.FALSE);

		operatorAction.getCellRenderer().setCellEditor(new CellEditor() {

			public Object getValue(Object item, String property, int rowcount) {

				HtmlBuilder html = new HtmlBuilder();
				Object id = ItemUtils.getItemValue(item, "id");
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
					/*
					 * html.img().src(request.getContextPath() +
					 * "/images/pencil.png") .border("none").end();
					 */
					html.append("删除");
					html.aEnd();
					html.append("&#160;");
					// <a href="javascript:void(0)"
					// href1="<s:property value="#url"/>" class="ymPrompt"
					// title="修改用户">修改</a>
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
				} else
					html.append("-");
				return html.toString();

			}
		});

		return tableFacade.render();
	}

	

	private String getExcelFileName() {
		return "financeforms.xls";
	}

	private void printExcel(List<FinanceForms> dataList) throws BiffException, IOException, RowsExceededException, WriteException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = this.getClass().getResourceAsStream("/financeTemplate.xls");
		Workbook workbook1 = Workbook.getWorkbook(is);
		Sheet source_sht = workbook1.getSheet(0);
		WritableWorkbook workbook = Workbook.createWorkbook(baos);
		String version = Workbook.getVersion();
		System.out.println("version="+version);
		excelInputStream=null;
		// 创建工作簿实例
		//d 亿 n分  o 亿 y分     
		// 创建工作表实例
		WritableSheet sheet = workbook.importSheet("sss",0,source_sht);
		// HSSFSheet sheet = workbook.createSheet("sxtaExcel");
		// 设置列宽
		
		// 获取样式
		/*SheetSettings settings = sheet.getSettings();
		if(settings!=null){
			sheet.get
		}*/
		
		if (dataList != null && !dataList.isEmpty()) {
			Format fm=new SimpleDateFormat("yyyy年MM月dd日");
			int rows=15;//skip 1 row
			// 给excel填充数据
			boolean isMerged=false;
			Range[] orgin_mergedCells = sheet.getMergedCells();
			if(orgin_mergedCells!=null && orgin_mergedCells.length>0){
				isMerged=true;
			}
			for (int i = 0; i < dataList.size(); i++) {
				// 将dataList里面的数据取出来
				FinanceForms form = dataList.get(i);
				if(i<(dataList.size()-1)){
					Range[] mergedCells=sheet.getMergedCells();
					Cell cell=null;
					int column=0,row=0;
					CellFormat cellFormat=null;
					WritableCellFeatures features=null;
					CellType type=null;
					CellView paramCellView=null;
					for(int k=0;k<15;k++){
						paramCellView = sheet.getRowView(i*17+k);
						//Cell[] cells = sheet.getRow(i*17+k);
						//Cell[] targets = sheet.getRow((i+1)*17+k+1);
						sheet.setRowView((i+1)*17+k, paramCellView);
						for(int kk=0;kk<30 ;kk++){
							cell=sheet.getCell(kk, i*17+k);
							if(cell==null)continue;
							type = cell.getType();
							column = cell.getColumn();
							row = cell.getRow();
							if(isMerged && inMergedRange(mergedCells,column,row))continue;
							cellFormat= cell.getCellFormat();
							if(row>5 && row<10){
								if(column>3 && column<18){
									if(cellFormat!=null){
										BorderLineStyle border = cellFormat.getBorder(Border.LEFT);
										StringBuffer buf=new StringBuffer(20);
										buf.append("col="+column+";row="+row+"\n");
										buf.append(" left style:").append(border!=null?border.getValue():"");
										border = cellFormat.getBorder(Border.RIGHT);
										buf.append(" right style:").append(border!=null?border.getValue():"");
										border = cellFormat.getBorder(Border.TOP);
										buf.append("top style:").append(border!=null?border.getValue():"");
										border = cellFormat.getBorder(Border.BOTTOM);
										buf.append(" bottom style:").append(border!=null?border.getValue():"");
										buf.append("");
										System.out.println(buf.toString());
									}else{
										System.out.println("col="+column+";row="+row+"\n"+" cellformat is null.");
									}
								}
							}
							//type= cell.getType();
							WritableCell cell2 = sheet.getWritableCell( column,row);
							features=cell2.getWritableCellFeatures();
							WritableCell target = cell2.copyTo(column,(i+1)*17+k);
							if(cellFormat!=null)
							target.setCellFormat(cellFormat);
							if(features!=null)
								target.setCellFeatures(features);
							sheet.addCell(target);
						}
					}
					copyMergedCells(sheet,mergedCells,i*17,(i+1)*17-1,17);
				}
				WritableCell cell = sheet.getWritableCell(1, i*17+1);//i*17
				System.out.println("col="+cell.getColumn()+" ;row="+cell.getRow());
				String contents = cell.getContents();
				System.out.println("contents"+contents);
				CellFormat cellFormat = cell.getCellFormat();
				
				Label label =null;
				if(cellFormat!=null)
					label=new Label(1,i*17+1,fm.format(form.getBizDate()),cellFormat);
				else
					label=new Label(1,i*17+1,fm.format(form.getBizDate()));
				
				sheet.addCell(label);
				cell = sheet.getWritableCell(0, i*17+4);
				cellFormat = cell.getCellFormat();
				CellFeatures cellFeatures = cell.getCellFeatures();
				if(cellFormat!=null)
					label=new Label(0,i*17+4,form.getContext(),cellFormat);
				else
					label=new Label(0,i*17+4,form.getContext());
				sheet.addCell(label);
				int jj=i*17+4;
				List<BalanceItem> items = form.getDebits();
				if(items!=null && !items.isEmpty()){
					writebalance(sheet,items,jj);
					jj+=items.size();
				}
				items = form.getCredits();
				if(items!=null && !items.isEmpty()){
					writebalance(sheet,items,jj);
				}
				int j=0;
				
					char c='d';
					char c1='n';
					j=3+c1-c;
				int k=0;
					 c='d';
					 c1='y';
					k=3+c1-c;
				
				if(j>0){
					BigDecimal amount = form.getAmount();
					String a = amount.toString();
					if(!(a.indexOf(".")>0)){
						a=a+".00";
					}
					writeAmount(sheet,a,j,i*17+13);
				}
				if(k>0){
					BigDecimal amount = form.getAmount();
					String a = amount.toString();
					if(!(a.indexOf(".")>0)){
						a=a+".00";
					}
					writeAmount(sheet,a,k,i*17+13);
				}
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

	


	
	private void copyMergedCells(WritableSheet sheet, Range[] mergedCells,
			int startrow, int endrow,int step) throws RowsExceededException, WriteException {
		int col1=0;
		int col2=0;
		int row1=0;
		int row2=0;
		Cell top=null, bottom=null;
		for(int i=0;i<mergedCells.length;i++){
			Range range = mergedCells[i];
			top= range.getTopLeft();
			bottom= range.getBottomRight();
			if(top.getRow()>=startrow && bottom.getRow()<endrow){
				col1=top.getColumn();
				row1=top.getRow()+step;
				col2=bottom.getColumn();
				row2=bottom.getRow()+step;
				Range r = sheet.mergeCells(col1, row1, col2, row2);
				WritableCell cell2 = sheet.getWritableCell( top.getColumn(),top.getRow());
				WritableCell target = cell2.copyTo(col1,row1);
				CellFormat cellFormat = cell2.getCellFormat();
				if(cellFormat!=null)
				target.setCellFormat(cellFormat);
				sheet.addCell(target);
			}
		}
		
	}

	private boolean inMergedRange(Range[] mergedCells, int column, int row) {
		Range range=null;
		for(int j=0;j<mergedCells.length;j++){
			 range= mergedCells[j];
			 Cell t = range.getTopLeft();
			 Cell b = range.getBottomRight();
			 boolean in=false;
			 if(row>=t.getRow() && column>=t.getColumn())in=true;
			 else in=false;
			 if(row<=t.getRow() && column<=t.getColumn())in=true && in;
			 else in=false;
			 if( in)return in;
		}
		return false;
	}

	//d 亿 n分  o 亿 y分   start startrow  
	private void writebalance(WritableSheet sheet, List<BalanceItem> items,
			int startrow) throws RowsExceededException, WriteException {
		CellFormat cellFormat =null;
		BalanceItem item =null;
		Label label=null;
		for(int k=0;k<items.size();k++){
			item= items.get(k);
			Cell cell = sheet.getCell(1, startrow+k);
			cellFormat =cell.getCellFormat();
			String disp=item.getFinanceId().toString();
			if(acc_map.containsKey(item.getFinanceId())){
				Account account = acc_map.get(item.getFinanceId());
				disp=account.getId().toString()+" "+account.getName();
			}else{
				Account account = this.accountManager.getAccountById(item.getFinanceId());
				if(account!=null){
					acc_map.put(account.getId(), account);
					disp=account.getId().toString()+" "+account.getName();
				}
			}
			if(cellFormat!=null)
				label=new Label(1,startrow+k,disp,cellFormat);
			else
				label=new Label(1,startrow+k,disp);
			sheet.addCell(label);
			BalanceDirect direct = item.getDirect();
			int j=0;
			if(direct.equals(BalanceDirect.DEBIT)){
				char c='d';
				char c1='n';
				j=3+c1-c;
			}else{
				char c='d';
				char c1='y';
				j=3+c1-c;
			}
			if(j>0){
				BigDecimal amount = item.getAmount();
				String a = amount.toString();
				if(!(a.indexOf(".")>0)){
					a=a+".00";
				}
				int row=startrow+k;
				writeAmount(sheet, a ,j,  row);
			}
		}
		
	}

	private void writeAmount(WritableSheet sheet, String a, int col,  int row) throws RowsExceededException, WriteException {
		Label label;
		int len = a.length();
		for(int i=len-1;i>=0;i--){
			String pr = a.substring(i, i+1);
			if(pr.equalsIgnoreCase("."))continue;
			Cell cell = sheet.getCell(col, row);
			CellFormat cellFormat = cell.getCellFormat();
			if(cellFormat!=null)
				label=new Label(col,row,pr,cellFormat);
			else
				label=new Label(col,row,pr);
			sheet.addCell(label);
			col--;
		}
	}

	public void setFinanceManager(FinanceService manager) {
		this.financeManager = manager;
	}

	 public void setAccountManager(TAccountManagerService manager){
     	this.accountManager=manager;
     }
}
