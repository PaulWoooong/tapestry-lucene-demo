/*
 * 创建日期 2009-6-17
 * 
 * The  tech Software License v1.0 content.
 * Copyright (c) 2006  
 * All rights reserved
 * For more information on , please
 * see 
 */

package com.samtech.piv.participle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.samtech.database.Rbt2Wordorphrase;
import com.samtech.piv.SmsWordTool;
import com.samtech.piv.Word;

/**
 * @author ympeng
 *
 * 机器人分词实现
 */

public class RobotParticiple implements IParticiple {

    public Word[] participle(String sentence) {
        List<Rbt2Wordorphrase> smwords = SmsWordTool.getWordsFromSentence(sentence);
        List<Word> words=new ArrayList<Word>();
        if(smwords!=null){
            for(int i=0;i<smwords.size();i++){
            	Rbt2Wordorphrase smword = (Rbt2Wordorphrase) smwords.get(i);
                Word word = toWord(smword);
                words.add(word);
               
            }
        }
        return words.toArray(new Word[words.size()]);
    }

    public Word[] participlePivotal(String sentence){
    	List<String> ws = SmsWordTool.getPrivotalWordsFromSentence(sentence);
    	List<Word> words=new ArrayList<Word>();
    	if(ws!=null){
    		for(int i=0;i<ws.size();i++){
    			Word word=new Word();
    			word.setKey(ws.get(i));
    			words.add(word);
    		}
    	}
    	return words.toArray(new Word[words.size()]);
    }
    
    public Word[] participleAndCom(String sentence){
    	List<Rbt2Wordorphrase> smwords = SmsWordTool.getWordsFromSentence(sentence);
    	List<Word> words=new ArrayList<Word>();
    	if(smwords==null || smwords.size()==0) return new Word[0]; 
		List<Rbt2Wordorphrase> phraseFrom = SmsWordTool.getPhraseFrom(smwords);
		
		//先添加关键字组合（a+b)
		if(phraseFrom!=null){
			//组合最多关键字的放置在最前面
			phraseFrom=orderComMaxLength(phraseFrom);
	        for(int i=0;i<phraseFrom.size();i++){
	        	Rbt2Wordorphrase smword = (Rbt2Wordorphrase) phraseFrom.get(i);
	            Word word = toWord(smword);
	            words.add(word);
	           
	        }
		}
       //再添加关键字（A+B组合的关键字，a，b也包含了）
        for(int i=0;i<smwords.size();i++){
        	Rbt2Wordorphrase smword = (Rbt2Wordorphrase) smwords.get(i);
            Word word = toWord(smword);
            words.add(word);
           
        }
        
        return words.toArray(new Word[words.size()]);
    	
    }
    //组合最长的放置在最前面
    private List<Rbt2Wordorphrase> orderComMaxLength(List<Rbt2Wordorphrase> phraseFrom){
    	if(phraseFrom!=null){
    		//看|分割的字符那个最多
    		Comparator<Rbt2Wordorphrase> c =new Comparator<Rbt2Wordorphrase>() {
				public int compare(Rbt2Wordorphrase o1, Rbt2Wordorphrase o2) {
					int l1=o1.getContent().split("\\|").length;
					int l2=o2.getContent().split("\\|").length;
					
					//return l1-l2;//顺序
					return l2-l1;//倒序
				}
			};
			Collections.sort(phraseFrom, c);
    		return phraseFrom;
    	}
    	return null;
    }

	private Word toWord(Rbt2Wordorphrase smword) {
		Word word=new Word();
		word.setSource(smword.getContent());
		
		word.setBusinessList(smword.getBusinessList());
		//FIXME
		/*word.setSignificList(smword.getSignificList());
		if(word.getSingification()!=null){
		    word.setType(word.getSingification().getType().intValue());
		    word.setJavaSrc(word.getSingification().getJavaSrc());
		}*/
		return word;
	}

}
