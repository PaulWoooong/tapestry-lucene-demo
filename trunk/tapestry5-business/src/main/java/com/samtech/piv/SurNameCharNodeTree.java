package com.samtech.piv;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.samtech.database.Rbt2Wordorphrase;

public class SurNameCharNodeTree{
	
    private static Log log=LogFactory.getLog(SurNameCharNodeTree.class);
    private static CharNode charnode=null;
    
    public  synchronized static CharNode getCharNodeTree(){
        if(charnode==null){
            charnode=init();
        }
        return charnode;
    }
	private static CharNode init(){
		CharNode charNodeRoot = new CharNode();
        try {
            InputStream nstream = ChinaNameTool.class
                    .getResourceAsStream("surname.txt");
            List list = IOUtils.readLines(nstream);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    String line = (String) list.get(i);
                    if (line != null) {
                        String[] sur = line.split(" ");
                        if (sur != null) {
                            for (int j = 0; j < sur.length; j++) {
                                if (sur[j] != null && sur[j].trim().length() > 0){
                                	Rbt2Wordorphrase smWord=new Rbt2Wordorphrase();
                                    smWord.setContent(sur[j].trim());
                                    addWordToTree(charNodeRoot, smWord);
                                }
                            }
                        }
                    }
                }
            }
            nstream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e);
        } 
		return charNodeRoot;
	}
    
    
    private static void addWordToTree(CharNode root,Rbt2Wordorphrase smWord){
        if(root == null) return; 
        CharNode tmpNode = root;
        String word = smWord.getContent();
        for(int j = 0; j < word.length(); j++ ){
            char tmp = word.charAt(j);
            CharNode child = null;
            if(j == word.length() - 1){
                child = tmpNode.addChild(tmp,smWord);
            }else{
                child = tmpNode.addChild(tmp);
            }
            tmpNode = child;
        }
    }

	
	
}
