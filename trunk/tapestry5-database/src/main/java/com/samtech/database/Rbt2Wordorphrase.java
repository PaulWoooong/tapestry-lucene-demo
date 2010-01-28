package com.samtech.database;

import java.io.Serializable;
import java.util.List;

import com.samtech.database.Rbt2Business;

//import com.footmarktech.robot.database.Rbt2Signification;

/**
 * This is an object that contains data related to the rbt2_wordorphrase table.
 * Do not modify this class because it will be overwritten if the configuration
 * file related to this class is modified.
 * 
 * @hibernate.class table="rbt2_wordorphrase"
 */

public class Rbt2Wordorphrase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2740337763220559060L;
	public static String REF = "Rbt2Wordorphrase";
	public static String PROP_LANG = "Lang";
	public static String PROP_OP_ID = "OpId";
	public static String PROP_TYPE = "Type";
	public static String PROP_ID = "Id";
	public static String PROP_CONTENT = "Content";
	public static String PROP_OP_DATE = "OpDate";

	static public String LANG_ZH = "zh";
	static public String LANG_EN = "en";
	static public String TYPE_WORD = "word";
	static public String TYPE_PHRASE = "phrase";

	// constructors
	public Rbt2Wordorphrase() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2Wordorphrase(java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String content;
	private java.lang.String type;
	private java.lang.String lang;
	private java.lang.String opId;
	private java.util.Date opDate;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="uuid.hex" column="id"
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param id
	 *            the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: content
	 */
	public java.lang.String getContent() {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * 
	 * @param content
	 *            the content value
	 */
	public void setContent(java.lang.String content) {
		this.content = content;
	}

	/**
	 * Return the value associated with the column: type
	 */
	public java.lang.String getType() {
		return type;
	}

	/**
	 * Set the value related to the column: type
	 * 
	 * @param type
	 *            the type value
	 */
	public void setType(java.lang.String type) {
		this.type = type;
	}

	/**
	 * Return the value associated with the column: lang
	 */
	public java.lang.String getLang() {
		return lang;
	}

	/**
	 * Set the value related to the column: lang
	 * 
	 * @param lang
	 *            the lang value
	 */
	public void setLang(java.lang.String lang) {
		this.lang = lang;
	}

	/**
	 * Return the value associated with the column: op_id
	 */
	public java.lang.String getOpId() {
		return opId;
	}

	/**
	 * Set the value related to the column: op_id
	 * 
	 * @param opId
	 *            the op_id value
	 */
	public void setOpId(java.lang.String opId) {
		this.opId = opId;
	}

	/**
	 * Return the value associated with the column: op_date
	 */
	public java.util.Date getOpDate() {
		return opDate;
	}

	/**
	 * Set the value related to the column: op_date
	 * 
	 * @param opDate
	 *            the op_date value
	 */
	public void setOpDate(java.util.Date opDate) {
		this.opDate = opDate;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof Rbt2Wordorphrase))
			return false;
		else {
			Rbt2Wordorphrase rbt2Wordorphrase = (Rbt2Wordorphrase) obj;
			if (null == this.getId() || null == rbt2Wordorphrase.getId())
				return false;
			else
				return (this.getId().equals(rbt2Wordorphrase.getId()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString() {
		return super.toString();
	}

	/** 业务集合 */
	List<Rbt2Business> businessList;

	/** signifcation集合 */
	// List<Rbt2Signification> significList;

	public boolean isEnglish() {
		return LANG_EN.equalsIgnoreCase(getLang()) ? true : false;
	}

	public List<Rbt2Business> getBusinessList() {
		return businessList;
	}

	public void setBusinessList(List<Rbt2Business> businessList) {
		this.businessList = businessList;
	}

	/*
	 * public List<Rbt2Signification> getSignificList() { return significList; }
	 * 
	 * public void setSignificList(List<Rbt2Signification> significList) {
	 * this.significList = significList; }
	 */

}