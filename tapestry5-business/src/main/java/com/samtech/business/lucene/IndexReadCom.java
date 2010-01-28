package com.samtech.business.lucene;

import java.util.Date;

import org.apache.lucene.index.IndexReader;

public class IndexReadCom {

	private Date createDate;
	private IndexReader reader;
	
	public IndexReadCom(){
		
	}
	public IndexReadCom(IndexReader r){
		reader=r;
		createDate=new Date();
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public IndexReader getReader() {
		return reader;
	}
	public void setReader(IndexReader reader) {
		this.reader = reader;
	}
}
