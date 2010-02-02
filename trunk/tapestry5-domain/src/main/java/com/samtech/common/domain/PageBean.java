package com.samtech.common.domain;

import java.io.Serializable;

public class PageBean implements Serializable {

	private static final long serialVersionUID = 68993129400642767L;
	
	private String 	sortColumn;//更改的排序字段
	private String 	sortOrder;//更改的排序order
	private Integer pageNo;//更改的页码
	private Integer pageSize;//更改的页数
	private Integer initialPageNo;//初始化的页数
	private Integer initialPageSize;//初始化的pageSize
	private String 	initialSortColumn;//初始化的排序
	private String 	initialSortOrder;//初始化的排序
	private Integer pagesDisplayed;//中间显示的页数


	
	public Integer getInitialPageNo() {
		return initialPageNo;
	}

	public void setInitialPageNo(Integer initialPageNo) {
		this.initialPageNo = initialPageNo;
	}

	public Integer getInitialPageSize() {
		return initialPageSize;
	}

	public void setInitialPageSize(Integer initialPageSize) {
		this.initialPageSize = initialPageSize;
	}

	public String getInitialSortColumn() {
		return initialSortColumn;
	}

	public void setInitialSortColumn(String initialSortColumn) {
		this.initialSortColumn = initialSortColumn;
	}

	public String getInitialSortOrder() {
		return initialSortOrder;
	}

	public void setInitialSortOrder(String initialSortOrder) {
		this.initialSortOrder = initialSortOrder;
	}

	public PageBean() {
		setInitialPageNo(new Integer(1));
		setInitialPageSize(new Integer(20));
		setInitialSortColumn(null);
		setInitialSortOrder(null);
		setPagesDisplayed(new Integer(10));
	}
	

	 public void initializePageNo(){
		setPageNo(getInitialPageNo());
	}
	 public void initialPageSize(){
		 setPageSize(getInitialPageSize());
	 }
	 
	 public void initializeSortColumn(){	
		 setSortColumn(getInitialSortColumn());
		 setSortOrder(getInitialSortOrder());
	 }
	 public void initialize(){	
		 initializePageNo();
		 initialPageSize();
		 initializeSortColumn();
	 }
	



	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPagesDisplayed() {
		return pagesDisplayed;
	}

	public void setPagesDisplayed(Integer pagesDisplayed) {
		this.pagesDisplayed = pagesDisplayed;
	}
	
	
}

