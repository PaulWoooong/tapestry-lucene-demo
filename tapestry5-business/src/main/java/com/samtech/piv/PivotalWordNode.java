package com.samtech.piv;

import java.util.HashMap;
import java.util.Map;


public class PivotalWordNode  {
	private String smWord; 
	private Map nodes; //判断是否叶子,由于只涉及读，不考虑线程安全问题
	private char nodeVal;
	private PivotalWordNode parent;
	
	private void setRbtWord(String smWord){
		this.smWord = smWord;
	}
	private void setParent(PivotalWordNode charNode) {
		this.parent = charNode;
	}
	private void setNodeVal(char nodeVal){
		this.nodeVal = nodeVal;
	}
	private void setNodes(Map nodes){
		this.nodes = nodes;
	}
	
	public PivotalWordNode(char nodeVal){
		setNodeVal(nodeVal);
	}
	
	public PivotalWordNode(char nodeVal,String smWord){
		setNodeVal(nodeVal);
		setRbtWord(smWord);
	}
	
	public PivotalWordNode(){
		
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
	
	public PivotalWordNode addChild(char ch){
		
		if(getNodes() == null){
			if(isRoot()){
				setNodes(new HashMap(200));
			}else{
				setNodes(new HashMap(10));
			}
		}
		Character key = new Character(ch);
		PivotalWordNode childNode = null;
		if(!nodes.containsKey(key)){
			childNode = new PivotalWordNode(ch);
			nodes.put(key, childNode);
			childNode.setParent(this);
		}else{
			childNode = (PivotalWordNode)nodes.get(key);
		}
		return childNode;
	}
	
	public PivotalWordNode addChild(char ch,String smWord){
		if(getNodes() == null){
			if(isRoot()){
				setNodes(new HashMap(200));
			}else{
				setNodes(new HashMap(10));
			}
		}
		Character character = new Character(ch);
		PivotalWordNode childNode = null;
		if(!nodes.containsKey(character)){
			childNode = new PivotalWordNode(ch,smWord);
			nodes.put(character, childNode);
			childNode.setParent(this);
		}else{
			childNode = (PivotalWordNode)nodes.get(character);
			if(childNode.getRbtWord() == null){
				childNode.setRbtWord(smWord);
			}
		}
		return childNode;
		
	}
	
	public PivotalWordNode getChild(char childval){
		if(nodes == null){
			return null;
		}else{
			return (PivotalWordNode)nodes.get(new Character(childval));
		}
	}
	
	public Map getNodes() {
		return nodes;
	}
	public char getNodeVal() {
		return nodeVal;
	}
	public PivotalWordNode getParent() {
		return parent;
	}
	public String getRbtWord(){
		return smWord;
	}
}
