package com.samtech.common.domain;
import java.io.Serializable;





public class SortColumn implements Serializable,Comparable<SortColumn>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private  String property;
	 private  Order order;
	 private int prior;
	 
	 public SortColumn() {
	
	}
	 public SortColumn(String pro,Order o) {
			this.property=pro;
			this.order=o;
	}
	 
	public String getProperty() {
		return property;
	}
	public Order getOrder() {
		return order;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((order == null) ? 0 : order.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
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
		SortColumn other = (SortColumn) obj;
		if (order == null) {
			if (other.order != null)
				return false;
		} else if (!order.equals(other.order))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
	
	public void setPrior(int prior) {
		this.prior = prior;
	}
	public int getPrior() {
		return prior;
	}
	
	public int compareTo(SortColumn o) {
		return this.prior-o.prior;
		
	}
	 
	 
	    
}
