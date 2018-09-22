package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class UserVariableProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3207593096419342158L;
	
	private List<VarHolder<? extends Serializable>> vars;
	String name;
	
	public UserVariableProfile(String name){
		initList();
	}
	
	private void initList(){
		vars = new LinkedList<VarHolder<?>>();
	}
	
	public void addVar(VarHolder<? extends Serializable> var){
		vars.add(var);
	}
	
	public List<VarHolder<? extends Serializable>> getVars(){
		return vars;
	}

	public String getName() {
		return name;
	}
	
	
}
