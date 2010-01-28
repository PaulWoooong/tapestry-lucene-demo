package com.samtech.database;

import java.io.Serializable;


public  class Rbt2WordphraseSignificationPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6134010331535824700L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String significationId;
	private java.lang.String wordorphraseId;


	public Rbt2WordphraseSignificationPK () {}
	
	public Rbt2WordphraseSignificationPK (
		java.lang.String significationId,
		java.lang.String wordorphraseId) {

		this.setSignificationId(significationId);
		this.setWordorphraseId(wordorphraseId);
	}


	/**
	 * Return the value associated with the column: signification_id
	 */
	public java.lang.String getSignificationId () {
		return significationId;
	}

	/**
	 * Set the value related to the column: signification_id
	 * @param significationId the signification_id value
	 */
	public void setSignificationId (java.lang.String significationId) {
		this.significationId = significationId;
	}



	/**
	 * Return the value associated with the column: wordorphrase_id
	 */
	public java.lang.String getWordorphraseId () {
		return wordorphraseId;
	}

	/**
	 * Set the value related to the column: wordorphrase_id
	 * @param wordorphraseId the wordorphrase_id value
	 */
	public void setWordorphraseId (java.lang.String wordorphraseId) {
		this.wordorphraseId = wordorphraseId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof Rbt2WordphraseSignificationPK)) return false;
		else {
			Rbt2WordphraseSignificationPK mObj = (Rbt2WordphraseSignificationPK) obj;
			if (null != this.getSignificationId() && null != mObj.getSignificationId()) {
				if (!this.getSignificationId().equals(mObj.getSignificationId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getWordorphraseId() && null != mObj.getWordorphraseId()) {
				if (!this.getWordorphraseId().equals(mObj.getWordorphraseId())) {
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
			if (null != this.getSignificationId()) {
				sb.append(this.getSignificationId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getWordorphraseId()) {
				sb.append(this.getWordorphraseId().hashCode());
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