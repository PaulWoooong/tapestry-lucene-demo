package com.samtech.piv;

import java.util.HashMap;
import java.util.Map;

import com.samtech.database.Rbt2Wordorphrase;


public class StringNode  {

	private Rbt2Wordorphrase smWord;
	private Map<String,StringNode> nodes;
	private StringNode parent;
	private String nodeVal;
    
	public StringNode() {
	}
	public StringNode(String nodeVal){
		setNodeVal(nodeVal);
	}
	public StringNode(String nodeVal,Rbt2Wordorphrase smWord){
		setNodeVal(nodeVal);
		setSmWord(smWord);
	}
	public boolean isRoot(){
		if(parent == null){
			return true;
		}
		return false;
	} 
	
	public StringNode addChild(String key) {
		return addChild(key,null);
	}
	public StringNode addChild(String key,Rbt2Wordorphrase smWord){
		if(getNodes() == null){
			if(isRoot()){
				setNodes(new HashMap<String,StringNode>(200));
			}else{
				setNodes(new HashMap<String,StringNode>(10));
			}
		}
		StringNode childNode = null;
		if(!getNodes().containsKey(key)){
			childNode = new StringNode(key,smWord);
			getNodes().put(key, childNode);
			childNode.setParent(this);
		}else{
			childNode = (StringNode)getNodes().get(key);
			if(childNode.getSmWord() == null){
				childNode.setSmWord(smWord);
			}
		}
		return childNode;
		
	}
	public StringNode getChild(String nodeValue) {
		if(getNodes() == null){
			return null;
		}else{
			return getNodes().get(nodeValue);
		}
	}

	public Rbt2Wordorphrase getSmWord() {
		return smWord;
	}

	public void setSmWord(Rbt2Wordorphrase smWord) {
		this.smWord = smWord;
	}


	public StringNode getParent() {
		return parent;
	}

	public void setParent(StringNode parent) {
		this.parent = parent;
	}

	public String getNodeVal() {
		return nodeVal;
	}

	public void setNodeVal(String nodeVal) {
		this.nodeVal = nodeVal;
	}
	public void setNodes(Map<String,StringNode> nodes) {
		this.nodes = nodes;
	}
	public Map<String,StringNode> getNodes() {
		return nodes;
	}

}
