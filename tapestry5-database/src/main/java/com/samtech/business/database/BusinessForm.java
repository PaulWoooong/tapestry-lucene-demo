package com.samtech.business.database;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="business_form")
public class BusinessForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 814775873798012282L;
	@Id
	@Column(length=30)
	@GeneratedValue(strategy=GenerationType.TABLE)
	private String id;
	/**
	 * {@link BusinessType}
	 */
	@Column(name="business_type",length=10,nullable=false)
	private String bizType;
	@Column(length=500,nullable=false)
	private String content;
	
	@Column(scale=2)
	private BigDecimal amount;
	@Enumerated(EnumType.STRING)
	private BizStatus status=BizStatus.Order;
	/**
	 * 0-挂账,1 finance confirm 完成记账
	 */
	@Column(name="finance_status")
	private Integer financeStatus;
	@Column(name="create_date",nullable=false)
	private Date createDate;
	@Column(name="endDate")
	private Date endDate;
	@Column(length=200)
	private String remark; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BizStatus getStatus() {
		return status;
	}
	public void setStatus(BizStatus status) {
		this.status = status;
	}
	public Integer getFinanceStatus() {
		return financeStatus;
	}
	public void setFinanceStatus(Integer financeStatus) {
		this.financeStatus = financeStatus;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date insertDate) {
		this.createDate = insertDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessForm other = (BusinessForm) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
