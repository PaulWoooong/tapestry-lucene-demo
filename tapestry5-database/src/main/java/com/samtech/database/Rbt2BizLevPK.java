package com.samtech.database;

import java.io.Serializable;


public  class Rbt2BizLevPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3552169226811466146L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String id;
	private java.lang.String parentId;


	public Rbt2BizLevPK () {}
	
	public Rbt2BizLevPK (
		java.lang.String id,
		java.lang.String parentId) {

		this.setId(id);
		this.setParentId(parentId);
	}


	/**
	 * Return the value associated with the column: id
	 */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the value related to the column: id
	 * @param id the id value
	 */
	public void setId (java.lang.String id) {
		this.id = id;
	}



	/**
	 * Return the value associated with the column: parent_id
	 */
	public java.lang.String getParentId () {
		return parentId;
	}

	/**
	 * Set the value related to the column: parent_id
	 * @param parentId the parent_id value
	 */
	public void setParentId (java.lang.String parentId) {
		this.parentId = parentId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.samtech.database.Rbt2BizLevPK)) return false;
		else {
			com.samtech.database.Rbt2BizLevPK mObj = (com.samtech.database.Rbt2BizLevPK) obj;
			if (null != this.getId() && null != mObj.getId()) {
				if (!this.getId().equals(mObj.getId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getParentId() && null != mObj.getParentId()) {
				if (!this.getParentId().equals(mObj.getParentId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getId()) {
				sb.append(this.getId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getParentId()) {
				sb.append(this.getParentId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}


}