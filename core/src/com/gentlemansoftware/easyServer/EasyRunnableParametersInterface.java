package com.gentlemansoftware.easyServer;

public interface EasyRunnableParametersInterface<E> extends Runnable{

	public void setParam(final E param);
	public E getParam();

}