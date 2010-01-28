package com.samtech.piv;
import java.util.List;

//import com.footmarktech.robot.business.IRobotWordService;
import com.samtech.database.Rbt2Wordorphrase;



public class CharNodeTreeFactory {
	
	//private IRobotWordService robotService; 
	
	public CharNode getCharNodeTree() throws Exception{
		CharNode charNodeRoot = new CharNode();
		//FIXME
		/*List list = getRobotService().findAllZhWords();
		
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Rbt2Wordorphrase smWord = (Rbt2Wordorphrase)list.get(i);
				addWordToTree(charNodeRoot, smWord);
			}
		}*/
		return charNodeRoot;
	}

	public StringNode getStringNodeTree() throws Exception{
		StringNode charNodeRoot = new StringNode();
		//FIXME
		/*List list = getRobotService().findAllPhrase();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				Rbt2Wordorphrase smWord = (Rbt2Wordorphrase)list.get(i);
				addWordToStringNodeTree(charNodeRoot, smWord);
			}
		}*/
		return charNodeRoot;
	}
	
	public PivotalWordNode getPivotalWordNodeTree() throws Exception{
		PivotalWordNode root = new PivotalWordNode();
		//FIXME
		/*List<String> list=getRobotService().findAllPivotalWords();
		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				addWordToPivotalWordTree(root, list.get(i));
			}
		}*/
		return root;

	}	

	
	/*public IRobotWordService getRobotService() {
        return robotService;
    }



    public void setRobotService(IRobotWordService robotService) {
        this.robotService = robotService;
    }*/



    private void addWordToTree(CharNode root,Rbt2Wordorphrase smWord){
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
    
    private void addWordToPivotalWordTree(PivotalWordNode root,String word){
		if(root == null) return; 
		PivotalWordNode tmpNode = root;
		for(int j = 0; j < word.length(); j++ ){
			char tmp = word.charAt(j);
			PivotalWordNode child = null;
			if(j == word.length() - 1){
				child = tmpNode.addChild(tmp,word);
			}else{
				child = tmpNode.addChild(tmp);
			}
			tmpNode = child;
		}
	}
    
    private void addWordToStringNodeTree(StringNode root,Rbt2Wordorphrase smWord){
		if(root == null) return; 
		StringNode tmpNode = root;
		String[] words = smWord.getContent().split("\\|");
		for(int j = 0; j < words.length; j++ ){
			String tmp = words[j];
			StringNode child = null;
			if(j == words.length - 1){
				child = tmpNode.addChild(tmp,smWord);
			}else{
				child = tmpNode.addChild(tmp);
			}
			tmpNode = child;
		}
	}
	
	
	
	
	
}
