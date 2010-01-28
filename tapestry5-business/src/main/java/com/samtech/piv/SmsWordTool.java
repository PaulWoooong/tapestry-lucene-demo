/*
 * 创建日期 2008-10-21
 * 
 * The  tech Software License v1.0 content.
 * Copyright (c) 2006  
 * All rights reserved
 * For more information on , please
 * see 
 */

package com.samtech.piv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.samtech.business.lucene.IndexReaderFactory;
import com.samtech.database.Rbt2Wordorphrase;

/**
 * @author ympeng
 *  time              auther                   desc
 * 2009/07/01         ympeng               添加缓存时间控制
 */

public class SmsWordTool {

	private static CharNode root = null;
	private static Map EnWordsMap = null;
	private static StringNode phraseRoot=null;
	private static PivotalWordNode privotalWordRoot=null;
    private static long lastUpdateTime=0L;
    private static long interval=-1;//1000*60*60*24L;//-1=一直缓存
    public static final String UpdateSmsWordCmd="$$~~UpdateSmsWord~~$$";//更新词库命令
    public static final String UpdateIndex="$$@@UpdateIndex@@$$";//更新索引
    public static final String ReBuildIndex="$$%%ReBuildIndex%%$$";//重建索引
    public static final String ReOpenIndex="$$%%ReOpenIndex%%$$";//重新打开索引
    public static final String ReUpdateAll="$$%%ReUpdateAll%%$$";//更新词库命令,更新索引,重新打开索引
    public static final String ReBuildAll="$$%%ReBuildAll%%$$";//更新词库命令,重建索引,重新打开索引
    
    

	public static List<Rbt2Wordorphrase> getWordsFromSentence(String sentence) {
        init();
		List<Rbt2Wordorphrase> list1 = WordTool.getWordsFromSentence(sentence, root);
		List<Rbt2Wordorphrase> list2 = WordTool.getEnWordsFromSentence(sentence, EnWordsMap);
		list1.addAll(list2);
		return list1;
	}
	
	/**
	 * 根据提供的Rbt2Wordorphrase集合，来查找按顺序组合的词组
	 * @return
	 */
	public static List<Rbt2Wordorphrase> getPhraseFrom(List<Rbt2Wordorphrase> words){
		return WordTool.findPhraseFromWords(words, phraseRoot);
	}
	/**
	 * 从关键字词库中，分词出关键字
	 * @param sentence
	 * @return
	 */
	public static List<String> getPrivotalWordsFromSentence(String sentence) {
        init();
		List<String> list1 = WordTool.getWordsFromSentence(sentence, privotalWordRoot);
		//找原声词
		List<String> result=new ArrayList<String>();
		if(list1!=null){
			//FIXME
			/*Rbt2SynonymousWordsService synwordService=(Rbt2SynonymousWordsService) BeanFactory.getBean("rbt2SynonymousWordsService");
			Map<String, String> synWords = synwordService.findAllWords(true);
			for (String string : list1) {
				String key = synWords.get(string);
				if(key!=null) result.add(key); else result.add(string);
			}*/
		}
		return result;
	}

    private static  void init(){
        boolean flag=needUpdate();
        /*try {
            if(root==null || flag){
                CharNodeTreeFactory charNodef= (CharNodeTreeFactory) BeanFactory.getBean("charNodeTreeFactory");
                EnWordMapFactory wordMapFactory= (EnWordMapFactory) BeanFactory.getBean("enWordMapFactory");
                root=charNodef.getCharNodeTree();
                SmsWordTool.setEnWordsMap(wordMapFactory.getEnWordMap());
                lastUpdateTime=System.currentTimeMillis();
            }
            if(EnWordsMap==null || flag){
                EnWordMapFactory wordMapFactory= (EnWordMapFactory) BeanFactory.getBean("enWordMapFactory");
                EnWordsMap=wordMapFactory.getEnWordMap();
            }
            if(flag){
            	Rbt2SynonymousWordsService synwordService=(Rbt2SynonymousWordsService) BeanFactory.getBean("rbt2SynonymousWordsService");
            	synwordService.findAllWords(false);
            }

            if(privotalWordRoot==null || flag){
                CharNodeTreeFactory charNodef= (CharNodeTreeFactory) BeanFactory.getBean("charNodeTreeFactory");
                privotalWordRoot=charNodef.getPivotalWordNodeTree();
                lastUpdateTime=System.currentTimeMillis();
            }            
            
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }*/
    }
    public static void UpdateSmsWord(){
        lastUpdateTime=0L;
    }
    public static void UpdateIndex(){
    	//FIXME
    	/*ILuceneIndexService indexService=(ILuceneIndexService) BeanFactory.getBean("luceneIndexService");
    	try {
			indexService.buildIndex(null);
		} catch (Exception e) {
		}*/
    }
    
    public static void ReBuildIndex(){
    	//FIXME
    	/*ILuceneIndexService indexService=(ILuceneIndexService) BeanFactory.getBean("luceneIndexService");
    	try {
			indexService.reBuildIndex(null);
		} catch (Exception e) {
		}*/
    	
    }
    public static void ReOpenIndex(){
    	IndexReaderFactory.getIndexReadPool().reOpen();
    }
    
    public static void ReUpdateAll(){
    	UpdateSmsWord();
    	UpdateIndex();
    	ReOpenIndex();
    }
    
    private static  boolean needUpdate(){
         if(lastUpdateTime==0L){
             return true;
         }
         if((System.currentTimeMillis()-lastUpdateTime)>=interval && interval!=-1L){
             return true;
         }
         return false;
    }
	public static void setRoot(CharNode node) {
		root = node;
	}

	public static CharNode getRoot() {
		return root;
	}

	public static void setEnWordsMap(Map enWordsMap) {
		EnWordsMap = enWordsMap;
	}

	public static StringNode getPhraseRoot() {
		return phraseRoot;
	}

	public static void setPhraseRoot(StringNode phraseRoot) {
		SmsWordTool.phraseRoot = phraseRoot;
	}
    
    

}
