package com.samtech.database;

import java.io.Serializable;

import javax.persistence.Transient;


/**
 * This is an object that contains data related to the rbt2_business table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rbt2_business"
 */

public  class Rbt2Business  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808423745922048043L;
	public static String REF = "Rbt2Business";
	public static String PROP_BIZ_DES = "BizDes";
	public static String PROP_DELETED = "Deleted";
	public static String PROP_BIZ_GROUP_CD = "BizGroupCd";
	public static String PROP_EXCLUDE_WORDS = "ExcludeWords";
	public static String PROP_INDEXED = "Indexed";
	public static String PROP_OP_ID = "OpId";
	public static String PROP_BIZ_URL = "BizUrl";
	public static String PROP_BIZ_TYPE = "BizType";
	public static String PROP_INCLUDE_WORDS = "IncludeWords";
	public static String PROP_LEV_ID = "LevId";
	public static String PROP_BIZ_NAME = "BizName";
	public static String PROP_BIZ_QUESTION = "BizQuestion";
	public static String PROP_ID = "Id";
	public static String PROP_OP_DATE = "OpDate";
	public static String PROP_BIZ_URL_NAME = "BizUrlName";
	public static String PROP_INDEX_WORDS = "IndexWords";

	
	
	// constructors
	public Rbt2Business () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2Business (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Rbt2Business (
		java.lang.String id,
		java.lang.String bizName,
		java.lang.String bizType,
		java.lang.String indexed,
		java.lang.String deleted) {

		this.setId(id);
		this.setBizName(bizName);
		this.setBizType(bizType);
		this.setIndexed(indexed);
		this.setDeleted(deleted);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String bizName;
	private java.lang.String bizQuestion;
	private java.lang.String bizType;
	private java.lang.String bizDes;
	private java.lang.String bizUrlName;
	private java.lang.String bizUrl;
	private java.lang.String bizGroupCd;
	private java.lang.String levId;
	private java.lang.String indexWords;
	private java.lang.String includeWords;
	private java.lang.String excludeWords;
	private java.lang.String indexed;
	private java.lang.String deleted;
	private java.lang.String opId;
	private java.util.Date opDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid.hex"
     *  column="biz_cd"
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
	 * Return the value associated with the column: biz_name
	 */
	public java.lang.String getBizName () {
		return bizName;
	}

	/**
	 * Set the value related to the column: biz_name
	 * @param bizName the biz_name value
	 */
	public void setBizName (java.lang.String bizName) {
		this.bizName = bizName;
	}



	/**
	 * Return the value associated with the column: biz_question
	 */
	public java.lang.String getBizQuestion () {
		return bizQuestion;
	}

	/**
	 * Set the value related to the column: biz_question
	 * @param bizQuestion the biz_question value
	 */
	public void setBizQuestion (java.lang.String bizQuestion) {
		this.bizQuestion = bizQuestion;
	}



	/**
	 * Return the value associated with the column: biz_type
	 */
	public java.lang.String getBizType () {
		return bizType;
	}

	/**
	 * Set the value related to the column: biz_type
	 * @param bizType the biz_type value
	 */
	public void setBizType (java.lang.String bizType) {
		this.bizType = bizType;
	}



	/**
	 * Return the value associated with the column: biz_des
	 */
	public java.lang.String getBizDes () {
		return bizDes;
	}

	/**
	 * Set the value related to the column: biz_des
	 * @param bizDes the biz_des value
	 */
	public void setBizDes (java.lang.String bizDes) {
		this.bizDes = bizDes;
	}



	/**
	 * Return the value associated with the column: biz_url_name
	 */
	public java.lang.String getBizUrlName () {
		return bizUrlName;
	}

	/**
	 * Set the value related to the column: biz_url_name
	 * @param bizUrlName the biz_url_name value
	 */
	public void setBizUrlName (java.lang.String bizUrlName) {
		this.bizUrlName = bizUrlName;
	}



	/**
	 * Return the value associated with the column: biz_url
	 */
	public java.lang.String getBizUrl () {
		return bizUrl;
	}

	/**
	 * Set the value related to the column: biz_url
	 * @param bizUrl the biz_url value
	 */
	public void setBizUrl (java.lang.String bizUrl) {
		this.bizUrl = bizUrl;
	}



	/**
	 * Return the value associated with the column: biz_group_cd
	 */
	public java.lang.String getBizGroupCd () {
		return bizGroupCd;
	}

	/**
	 * Set the value related to the column: biz_group_cd
	 * @param bizGroupCd the biz_group_cd value
	 */
	public void setBizGroupCd (java.lang.String bizGroupCd) {
		this.bizGroupCd = bizGroupCd;
	}



	/**
	 * Return the value associated with the column: lev_id
	 */
	public java.lang.String getLevId () {
		return levId;
	}

	/**
	 * Set the value related to the column: lev_id
	 * @param levId the lev_id value
	 */
	public void setLevId (java.lang.String levId) {
		this.levId = levId;
	}



	/**
	 * Return the value associated with the column: index_words
	 */
	public java.lang.String getIndexWords () {
		return indexWords;
	}

	/**
	 * Set the value related to the column: index_words
	 * @param indexWords the index_words value
	 */
	public void setIndexWords (java.lang.String indexWords) {
		this.indexWords = indexWords;
	}



	/**
	 * Return the value associated with the column: include_words
	 */
	public java.lang.String getIncludeWords () {
		return includeWords;
	}

	/**
	 * Set the value related to the column: include_words
	 * @param includeWords the include_words value
	 */
	public void setIncludeWords (java.lang.String includeWords) {
		this.includeWords = includeWords;
	}



	/**
	 * Return the value associated with the column: exclude_words
	 */
	public java.lang.String getExcludeWords () {
		return excludeWords;
	}

	/**
	 * Set the value related to the column: exclude_words
	 * @param excludeWords the exclude_words value
	 */
	public void setExcludeWords (java.lang.String excludeWords) {
		this.excludeWords = excludeWords;
	}



	/**
	 * Return the value associated with the column: is_indexed
	 */
	public java.lang.String getIndexed () {
		return indexed;
	}

	/**
	 * Set the value related to the column: is_indexed
	 * @param indexed the is_indexed value
	 */
	public void setIndexed (java.lang.String indexed) {
		this.indexed = indexed;
	}



	/**
	 * Return the value associated with the column: deleted
	 */
	public java.lang.String getDeleted () {
		return deleted;
	}

	/**
	 * Set the value related to the column: deleted
	 * @param deleted the deleted value
	 */
	public void setDeleted (java.lang.String deleted) {
		this.deleted = deleted;
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
		if (!(obj instanceof Rbt2Business)) return false;
		else {
			Rbt2Business rbt2Business = (Rbt2Business) obj;
			if (null == this.getId() || null == rbt2Business.getId()) return false;
			else return (this.getId().equals(rbt2Business.getId()));
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
	
	@Transient
	private float score;
	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public String toString () {
		return super.toString();
	}


}