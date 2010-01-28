package com.samtech.piv;

public interface INode<T> {
	public INode<T> addChild(T child);
	
    public INode<T> getChild(T nodeValue);
}