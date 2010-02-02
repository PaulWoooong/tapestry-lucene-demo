package com.samtech.finance.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="biz_account_rule")
public class BusinessAccountRule implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1680260483117452675L;
	@Id
	Integer id;
	@Column(name="business_type",length=10)
	String bizType;
	@Column(name="account_id")
	Integer accountId;
	@Enumerated(EnumType.STRING)
	BalanceDirect direct;
	@Column(name="create_date")
	@Temporal(TemporalType.TIMESTAMP)
	Date createDate;
	
}
