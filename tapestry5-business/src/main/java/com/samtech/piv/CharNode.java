package com.samtech.piv;

import java.util.HashMap;
import java.util.Map;

import com.samtech.database.Rbt2Wordorphrase;



public class CharNode {
	private Rbt2Wordorphrase smWord; 
	private Map nodes; //判断是否叶子,由于只涉及读，不考虑线程安全问题
	private char nodeVal;
	private CharNode parent;
	
	private void setRbtWord(Rbt2Wordorphrase smWord){
		this.smWord = smWord;
	}
	private void setParent(CharNode charNode) {
		this.parent = charNode;
	}
	private void setNodeVal(char nodeVal){
		this.nodeVal = nodeVal;
	}
	private void setNodes(Map nodes){
		this.nodes = nodes;
	}
	
	/*public CharNode(char nodeVal,int type,CharNode parent){
		this.nodeVal = nodeVal;
		this.type = type;
		setParent(parent);
	}*/
	/*public CharNode(char nodeVal,CharNode parent){
		this.nodeVal = nodeVal;
		setParent(parent);
	}*/
	public CharNode(char nodeVal){
		setNodeVal(nodeVal);
	}
	
	public CharNode(char nodeVal,Rbt2Wordorphrase smWord){
		setNodeVal(nodeVal);
		setRbtWord(smWord);
	}
	
	public CharNode(){
		
	}
	public boolean isRoot(){
		if(parent == null){
			return true;
		}
		return false;
	} 
	public boolean isLeaf(){
		if(nodes == null || nodes.isEmpty()){
			return true;
		}
		return false;
	}
	
	public CharNode addChild(char ch){
		
		if(getNodes() == null){
			if(isRoot()){
				setNodes(new HashMap(200));
			}else{
				setNodes(new HashMap(10));
			}
		}
		Character key = new Character(ch);
		CharNode childNode = null;
		if(!nodes.containsKey(key)){
			childNode = new CharNode(ch);
			nodes.put(key, childNode);
			childNode.setParent(this);
		}else{
			childNode = (CharNode)nodes.get(key);
		}
		return childNode;
	}
	
	public CharNode addChild(char ch,Rbt2Wordorphrase smWord){
		if(getNodes() == null){
			if(isRoot()){
				setNodes(new HashMap(200));
			}else{
				setNodes(new HashMap(10));
			}
		}
		Character character = new Character(ch);
		CharNode childNode = null;
		if(!nodes.containsKey(character)){
			childNode = new CharNode(ch,smWord);
			nodes.put(character, childNode);
			childNode.setParent(this);
		}else{
			childNode = (CharNode)nodes.get(character);
			if(childNode.getRbtWord() == null){
				childNode.setRbtWord(smWord);
			}
		}
		return childNode;
		
	}
	
	public CharNode getChild(char childval){
		if(nodes == null){
			return null;
		}else{
			return (CharNode)nodes.get(new Character(childval));
		}
	}
	
	public Map getNodes() {
		return nodes;
	}
	public char getNodeVal() {
		return nodeVal;
	}
	public CharNode getParent() {
		return parent;
	}
	public Rbt2Wordorphrase getRbtWord(){
		return smWord;
	}
	
}
