package com.samtech.database;

import java.io.Serializable;


/**
 * This is an object that contains data related to the rbt2_synonymous_words table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="rbt2_synonymous_words"
 */

public class Rbt2SynonymousWords  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3920767143653912828L;
	public static String REF = "Rbt2SynonymousWords";
	public static String PROP_DELETED = "Deleted";
	public static String PROP_MEANING = "Meaning";
	public static String PROP_OP_ID = "OpId";
	public static String PROP_SYN_INDEX = "SynIndex";
	public static String PROP_ID = "Id";
	public static String PROP_WORDS = "Words";
	public static String PROP_OP_DATE = "OpDate";


	// constructors
	public Rbt2SynonymousWords () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public Rbt2SynonymousWords (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public Rbt2SynonymousWords (
		java.lang.String id,
		java.lang.String words,
		java.lang.String meaning,
		java.lang.String synIndex,
		java.lang.String deleted) {

		this.setId(id);
		this.setWords(words);
		this.setMeaning(meaning);
		this.setSynIndex(synIndex);
		this.setDeleted(deleted);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String words;
	private java.lang.String meaning;
	private java.lang.String synIndex;
	private java.lang.String deleted;
	private java.lang.String opId;
	private java.util.Date opDate;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="uuid.hex"
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
	 * Return the value associated with the column: words
	 */
	public java.lang.String getWords () {
		return words;
	}

	/**
	 * Set the value related to the column: words
	 * @param words the words value
	 */
	public void setWords (java.lang.String words) {
		this.words = words;
	}



	/**
	 * Return the value associated with the column: meaning
	 */
	public java.lang.String getMeaning () {
		return meaning;
	}

	/**
	 * Set the value related to the column: meaning
	 * @param meaning the meaning value
	 */
	public void setMeaning (java.lang.String meaning) {
		this.meaning = meaning;
	}



	/**
	 * Return the value associated with the column: syn_index
	 */
	public java.lang.String getSynIndex () {
		return synIndex;
	}

	/**
	 * Set the value related to the column: syn_index
	 * @param synIndex the syn_index value
	 */
	public void setSynIndex (java.lang.String synIndex) {
		this.synIndex = synIndex;
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
		if (!(obj instanceof Rbt2SynonymousWords)) return false;
		else {
			Rbt2SynonymousWords rbt2SynonymousWords = (Rbt2SynonymousWords) obj;
			if (null == this.getId() || null == rbt2SynonymousWords.getId()) return false;
			else return (this.getId().equals(rbt2SynonymousWords.getId()));
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