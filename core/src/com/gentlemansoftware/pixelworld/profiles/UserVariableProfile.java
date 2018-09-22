package com.gentlemansoftware.pixelworld.profiles;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public interface UserVariableProfile {
	
	public List<VarHolder<? extends Serializable>> getVars();
	public String getName();
	
	
}
