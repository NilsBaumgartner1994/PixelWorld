package com.gentlemenssoftware.easyServer;

public interface EasyRunnableParameters<E extends Enum<E>> extends Runnable{

	public void setType(final E type);
	public void setParam(final Object param);

}