package com.samtech.piv.participle;

import com.samtech.piv.Word;



public interface IParticiple {
	/**
	 * 用于分词短信业务
	 * @param sentence
	 * @return
	 */
	public Word[] participle(String sentence);
	/**
	 * 分词 ，包括关键字组合
	 * @param sentence
	 * @return
	 */
	public Word[] participleAndCom(String sentence);
	
	/**
	 * 关键字分词
	 * @param sentence
	 * @return
	 */
	public Word[] participlePivotal(String sentence);
}
