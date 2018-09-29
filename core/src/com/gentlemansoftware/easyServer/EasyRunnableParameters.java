package com.gentlemansoftware.easyServer;

public class EasyRunnableParameters<E extends Enum<E>> implements EasyRunnableParametersInterface<E>{

	private Object param;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParam(Object param) {
		// TODO Auto-generated method stub
		this.param = param;
	}

	@Override
	public Object getParam() {
		return param;
	}


}