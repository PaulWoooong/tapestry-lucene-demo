package com.samtech.piv;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.samtech.database.Rbt2Wordorphrase;



public class WordTool {
	
	//该方法试图将一句话分成若干词语，不是尝试找出每一句话包含的词语
	public static List<Rbt2Wordorphrase> getWordsFromSentence(String sentence,CharNode root){
		if(sentence == null || root == null) return null;
		List smWordList = new ArrayList();
		char ch[] = sentence.toCharArray();
		CharNode tmp1 = root;
//		CharNode tmp2 = root;
//        int dep=0;//配对深度
		Rbt2Wordorphrase shorterword = null;//记录一次匹配中的次短词
        int iwordstart = -1;//词语可能的开始,用于回退
		for(int i = 0; i < ch.length; i++){
           /* if(tmp1.getParent()!=null)
                dep++;
            else
                dep=0;*/  //dep
			tmp1 = tmp1.getChild(ch[i]);
			if(tmp1 != null){
				//是节点
				if(iwordstart == -1){
					iwordstart = i;
				}
				if(tmp1.getRbtWord() != null ){	
					//是词语
					if(i < ch.length - 1 && (tmp1.getChild(ch[i+1]))!=null){
						//词不结尾或下一ch在树中
						shorterword = tmp1.getRbtWord();
					}else{
						//确定一词
						smWordList.add(tmp1.getRbtWord());
						tmp1 = root;
						shorterword = null;//清空
						iwordstart = -1;
					}
				}
				/*if(tmp1.getSmWord() != null && (i == ch.length - 1 || (tmp1.getChild(ch[i+1]))==null)){
					smWordList.add(tmp1.getSmWord());
					tmp1 = root;
					shorterword = null;
				}*/
			}else{
				tmp1 = root;
				if(shorterword != null){
					//确定一词
					smWordList.add(shorterword);
					//回退
					if(iwordstart != -1){
						i = iwordstart + shorterword.getContent().length() - 1;
					}
					shorterword = null;//清空
				}else{
					//回退
					if(iwordstart != -1){
						i = iwordstart;
					}
				}
				iwordstart = -1;
				/*i=i-dep;  
                if(i<-1)
                    break;*///dep
			}
		}
        //A关键字+B关键字识别不出bug
        if(shorterword!=null){
            if(!smWordList.contains(shorterword)) smWordList.add(shorterword);
        }
		return smWordList;
	}
	static final Pattern EN_PATTERN = Pattern.compile("[A-Z]+");
	public static List<Rbt2Wordorphrase> getEnWordsFromSentence(String sentence,Map enWordMap){
		String UpperCaseSentence = StringUtils.upperCase(StringUtils.trimToEmpty(sentence));
		Matcher matcher =EN_PATTERN.matcher(UpperCaseSentence);
		List smWordList = new ArrayList();
		while(matcher.find()){
			String key = matcher.group();
			Rbt2Wordorphrase smtmp = (Rbt2Wordorphrase)enWordMap.get(key);
			if(smtmp != null){
				smWordList.add(smtmp);
			}
		}
		return smWordList;
	}
	
	/**
	 * 根据提供的RbtWord集合，来查找按顺序组合的词组
	 * @return
	 */
	public static List<Rbt2Wordorphrase> findPhraseFromWords(List<Rbt2Wordorphrase> words,StringNode root){
		if(words==null || root ==null) return null;
		String key=null;
		List<Rbt2Wordorphrase> ls=new ArrayList<Rbt2Wordorphrase>();
		StringNode childNode=null;
		for(int i=0;i<words.size();i++){
			key=words.get(i).getContent();
			childNode= root.getChild(key);
			if(childNode!=null){
				List<Rbt2Wordorphrase> subList =words.subList(i, words.size());
				if(childNode.getSmWord()!=null){
					ls.add(childNode.getSmWord());
					if(findChilds(subList, childNode)!=null){
						ls.addAll(findPhraseFromWords(subList, childNode));
					}
				} else {
					ls.addAll(findPhraseFromWords(subList, childNode));
				}
			}
			
		}
		return ls;
		
	}
	/**
	 * 查找节点中，包含的所有在words中的数据
	 * @param words
	 * @param root
	 * @return
	 */
	private static List<Rbt2Wordorphrase> findChilds(List<Rbt2Wordorphrase> words,StringNode root){
		if(words==null || root ==null) return null;
		List<Rbt2Wordorphrase> rb=null;
		for(Rbt2Wordorphrase w :words){
			if(root.getChild(w.getContent())!=null){
				if(rb==null) rb=new ArrayList<Rbt2Wordorphrase>();
				rb.add(w);
			}
		}
		return rb;
	}
	
	public static List<String> getWordsFromSentence(String sentence,PivotalWordNode root){
		if(sentence == null || root == null) return null;
		List smWordList = new ArrayList();
		char ch[] = sentence.toCharArray();
		PivotalWordNode tmp1 = root;
		String shorterword = null;//记录一次匹配中的次短词
        int iwordstart = -1;//词语可能的开始,用于回退
		for(int i = 0; i < ch.length; i++){
           /* if(tmp1.getParent()!=null)
                dep++;
            else
                dep=0;*/  //dep
			tmp1 = tmp1.getChild(ch[i]);
			if(tmp1 != null){
				//是节点
				if(iwordstart == -1){
					iwordstart = i;
				}
				if(tmp1.getRbtWord() != null ){	
					//是词语
					if(i < ch.length - 1 && (tmp1.getChild(ch[i+1]))!=null){
						//词不结尾或下一ch在树中
						shorterword = tmp1.getRbtWord();
					}else{
						//确定一词
						smWordList.add(tmp1.getRbtWord());
						tmp1 = root;
						shorterword = null;//清空
						iwordstart = -1;
					}
				}
				/*if(tmp1.getSmWord() != null && (i == ch.length - 1 || (tmp1.getChild(ch[i+1]))==null)){
					smWordList.add(tmp1.getSmWord());
					tmp1 = root;
					shorterword = null;
				}*/
			}else{
				tmp1 = root;
				if(shorterword != null){
					//确定一词
					smWordList.add(shorterword);
					//回退
					if(iwordstart != -1){
						i = iwordstart + shorterword.length() - 1;
					}
					shorterword = null;//清空
				}else{
					//回退
					if(iwordstart != -1){
						i = iwordstart;
					}
				}
				iwordstart = -1;
				/*i=i-dep;  
                if(i<-1)
                    break;*///dep
			}
		}
        //A关键字+B关键字识别不出bug
        if(shorterword!=null){
            if(!smWordList.contains(shorterword)) smWordList.add(shorterword);
        }
		return smWordList;
	}
	public static void main(String[] args) throws Exception {
		CharNodeTreeFactory charNodeTreeFactory = new CharNodeTreeFactory();
		//charNodeTreeFactory.setSmWordDao( new RbtWordDao());
		CharNode root = charNodeTreeFactory.getCharNodeTree();
		List list = WordTool.getWordsFromSentence("我想知道明天从广州桂林到北京的票价", root);
		for(int i = 0; i < list.size();i++){
			System.out.println(((Rbt2Wordorphrase)list.get(i)).getContent());
		}
		String pattern = "[^a-zA-Z]+";
		String patternEn = "[A-Z]+";
		String sen = "我想   知CA N道   明天从广州桂林到OOP	北京的票价";
		/*String[] strs = sen.split(pattern);
		System.out.println(strs.length);
		for(int i = 0; i< strs.length;i++){
			System.out.println(strs[i]);
		}*/
		Matcher matcher =EN_PATTERN.matcher(sen);
		while(matcher.find()){
			System.out.println(matcher.group());
		}
	}
}
