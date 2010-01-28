package com.samtech.database;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rbt2_biz_lev table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rbt2_biz_lev"
 */

public  class Rbt2BizLev  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2538399269537578835L;
	public static String REF = "Rbt2BizLev";
	public static String PROP_NAME = "Name";
	public static String PROP_IS_LEAVE = "IsLeave";
	public static String PROP_OP_ID = "OpId";
	public static String PROP_BIZ_CD = "BizCd";
	public static String PROP_ID = "Id";
	public static String PROP_OP_DATE = "OpDate";


	// constructors
	public Rbt2BizLev () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2BizLev (com.samtech.database.Rbt2BizLevPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Rbt2BizLev (
		com.samtech.database.Rbt2BizLevPK id,
		java.lang.Integer isLeave,
		java.lang.String name) {

		this.setId(id);
		this.setIsLeave(isLeave);
		this.setName(name);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.samtech.database.Rbt2BizLevPK id;

	// fields
	private java.lang.String bizCd;
	private java.lang.Integer isLeave;
	private java.lang.String name;
	private java.util.Date opDate;
	private java.lang.String opId;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.samtech.database.Rbt2BizLevPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.samtech.database.Rbt2BizLevPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: biz_cd
	 */
	public java.lang.String getBizCd () {
		return bizCd;
	}

	/**
	 * Set the value related to the column: biz_cd
	 * @param bizCd the biz_cd value
	 */
	public void setBizCd (java.lang.String bizCd) {
		this.bizCd = bizCd;
	}



	/**
	 * Return the value associated with the column: is_leave
	 */
	public java.lang.Integer getIsLeave () {
		return isLeave;
	}

	/**
	 * Set the value related to the column: is_leave
	 * @param isLeave the is_leave value
	 */
	public void setIsLeave (java.lang.Integer isLeave) {
		this.isLeave = isLeave;
	}



	/**
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}



	/**
	 * Return the value associated with the column: op_date
	 */
	public java.util.Date getOpDate () {
		return opDate;
	}

	/**
	 * Set the value related to the column: op_date
	 * @param opDate the op_date value
	 */
	public void setOpDate (java.util.Date opDate) {
		this.opDate = opDate;
	}



	/**
	 * Return the value associated with the column: op_id
	 */
	public java.lang.String getOpId () {
		return opId;
	}

	/**
	 * Set the value related to the column: op_id
	 * @param opId the op_id value
	 */
	public void setOpId (java.lang.String opId) {
		this.opId = opId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.samtech.database.Rbt2BizLev)) return false;
		else {
			com.samtech.database.Rbt2BizLev rbt2BizLev = (com.samtech.database.Rbt2BizLev) obj;
			if (null == this.getId() || null == rbt2BizLev.getId()) return false;
			else return (this.getId().equals(rbt2BizLev.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}