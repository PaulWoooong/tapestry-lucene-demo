package com.samtech.common.domain;

public class QueryParam implements java.io.Serializable {
	private static final long serialVersionUID = 68993129400642767L;
	// 分页参数bean
	private PageBean pageBean;
	// 查询开关
	private boolean query;
	// 要删除的Id
	private String deleteIds;
	//queryBean的JSON String
	private String queryBeanJson;

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public QueryParam() {
		this.setPageBean(new PageBean());
	}

	public boolean isQuery() {
		return query;
	}

	public void setQuery(boolean query) {
		this.query = query;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

	public String getQueryBeanJson() {
		return queryBeanJson;
	}

	public void setQueryBeanJson(String queryBeanJson) {
		this.queryBeanJson = queryBeanJson;
	}
	
	
	

}
