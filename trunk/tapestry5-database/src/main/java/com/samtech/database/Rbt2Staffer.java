package com.samtech.database;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rbt2_staffer table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rbt2_staffer"
 */

public  class Rbt2Staffer  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4793744395342819261L;
	public static String REF = "Rbt2Staffer";
	public static String PROP_IP = "Ip";
	public static String PROP_END_DATE = "EndDate";
	public static String PROP_E_MAIL = "EMail";
	public static String PROP_PASSWORD = "Password";
	public static String PROP_MOBILE = "Mobile";
	public static String PROP_STAFFER_NAME = "StafferName";
	public static String PROP_ID = "Id";
	public static String PROP_TELPHONE = "Telphone";
	public static String PROP_DEPARTMENT_ID = "DepartmentId";
	public static String PROP_OP_DATE = "OpDate";


	// constructors
	public Rbt2Staffer () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2Staffer (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Rbt2Staffer (
		java.lang.String id,
		java.lang.String stafferName) {

		this.setId(id);
		this.setStafferName(stafferName);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String stafferName;
	private java.lang.String departmentId;
	private java.lang.String password;
	private java.lang.String telphone;
	private java.lang.String mobile;
	private java.lang.String eMail;
	private java.util.Date endDate;
	private java.lang.String ip;
	private java.util.Date opDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: staffer_name
	 */
	public java.lang.String getStafferName () {
		return stafferName;
	}

	/**
	 * Set the value related to the column: staffer_name
	 * @param stafferName the staffer_name value
	 */
	public void setStafferName (java.lang.String stafferName) {
		this.stafferName = stafferName;
	}



	/**
	 * Return the value associated with the column: department_id
	 */
	public java.lang.String getDepartmentId () {
		return departmentId;
	}

	/**
	 * Set the value related to the column: department_id
	 * @param departmentId the department_id value
	 */
	public void setDepartmentId (java.lang.String departmentId) {
		this.departmentId = departmentId;
	}



	/**
	 * Return the value associated with the column: password
	 */
	public java.lang.String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: password
	 * @param password the password value
	 */
	public void setPassword (java.lang.String password) {
		this.password = password;
	}



	/**
	 * Return the value associated with the column: telphone
	 */
	public java.lang.String getTelphone () {
		return telphone;
	}

	/**
	 * Set the value related to the column: telphone
	 * @param telphone the telphone value
	 */
	public void setTelphone (java.lang.String telphone) {
		this.telphone = telphone;
	}



	/**
	 * Return the value associated with the column: mobile
	 */
	public java.lang.String getMobile () {
		return mobile;
	}

	/**
	 * Set the value related to the column: mobile
	 * @param mobile the mobile value
	 */
	public void setMobile (java.lang.String mobile) {
		this.mobile = mobile;
	}



	/**
	 * Return the value associated with the column: e_mail
	 */
	public java.lang.String getEMail () {
		return eMail;
	}

	/**
	 * Set the value related to the column: e_mail
	 * @param eMail the e_mail value
	 */
	public void setEMail (java.lang.String eMail) {
		this.eMail = eMail;
	}



	/**
	 * Return the value associated with the column: end_date
	 */
	public java.util.Date getEndDate () {
		return endDate;
	}

	/**
	 * Set the value related to the column: end_date
	 * @param endDate the end_date value
	 */
	public void setEndDate (java.util.Date endDate) {
		this.endDate = endDate;
	}



	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
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




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Rbt2Staffer)) return false;
		else {
			Rbt2Staffer rbt2Staffer = (Rbt2Staffer) obj;
			if (null == this.getId() || null == rbt2Staffer.getId()) return false;
			else return (this.getId().equals(rbt2Staffer.getId()));
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