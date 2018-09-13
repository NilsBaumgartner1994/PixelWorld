package com.gof.profiles;

import java.io.Serializable;

public class VarHolder<T extends Serializable> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7594759790940482935L;
	
	public T value;
	public String name;
	
	public VarHolder(T value, String name){
		setVar(value);
		setName(name);
	}
	
	public VarHolder(T value){
		this(value,"");
	}
	
	public VarHolder(){
		this(null,"");
	}
	
	public void setVar(T value){
		this.value = value;
	}
	
	public T getVar(){
		return this.value;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	
}