package com.gentlemansoftware.easyServer;

public interface EasyRunnableParametersInterface<E extends Enum<E>> extends Runnable{

	public void setParam(final Object param);
	public Object getParam();

}