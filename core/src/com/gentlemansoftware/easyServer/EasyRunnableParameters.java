package com.gentlemansoftware.easyServer;

public class EasyRunnableParameters<E> implements EasyRunnableParametersInterface<E>{

	private E param;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParam(E param) {
		// TODO Auto-generated method stub
		this.param = param;
	}

	@Override
	public E getParam() {
		return param;
	}


}