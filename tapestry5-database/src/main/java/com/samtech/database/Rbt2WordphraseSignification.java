package com.samtech.database;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rbt2_wordphrase_signification table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rbt2_wordphrase_signification"
 */

public  class Rbt2WordphraseSignification  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5345219997477073439L;
	public static String REF = "Rbt2WordphraseSignification";
	public static String PROP_SEQ_NUM = "SeqNum";
	public static String PROP_OP_ID = "OpId";
	public static String PROP_ID = "Id";
	public static String PROP_OP_DATE = "OpDate";


	// constructors
	public Rbt2WordphraseSignification () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2WordphraseSignification (Rbt2WordphraseSignificationPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Rbt2WordphraseSignification (
		Rbt2WordphraseSignificationPK id,
		java.lang.Integer seqNum) {

		this.setId(id);
		this.setSeqNum(seqNum);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private Rbt2WordphraseSignificationPK id;

	// fields
	private java.lang.Integer seqNum;
	private java.lang.String opId;
	private java.util.Date opDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public Rbt2WordphraseSignificationPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (Rbt2WordphraseSignificationPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: seq_num
	 */
	public java.lang.Integer getSeqNum () {
		return seqNum;
	}

	/**
	 * Set the value related to the column: seq_num
	 * @param seqNum the seq_num value
	 */
	public void setSeqNum (java.lang.Integer seqNum) {
		this.seqNum = seqNum;
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
		if (!(obj instanceof Rbt2WordphraseSignification)) return false;
		else {
			Rbt2WordphraseSignification rbt2WordphraseSignification = (Rbt2WordphraseSignification) obj;
			if (null == this.getId() || null == rbt2WordphraseSignification.getId()) return false;
			else return (this.getId().equals(rbt2WordphraseSignification.getId()));
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