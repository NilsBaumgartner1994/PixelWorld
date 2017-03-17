package com.gof.nature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gof.materials.Material;

public class Beet extends Nature implements GrowableInterface {
	
	public static List<String> growstates = new ArrayList<String>();
	
	public final static String READY = "";
	
	static{
		String[] order = {READY};
		growstates = Arrays.asList(order);
	}
	
	public int growstate;
	
	public Beet(){
		setGrowState(0);
	}

	@Override
	public void nextGrowState() {
		setGrowState(getGrowState()+1);
	}

	@Override
	public int getGrowState() {
		return growstate;
	}

	@Override
	public void setGrowState(int pos) {
		growstate=pos%growstates.size();
		this.setTexture(growstates.get(growstate));
	}


}
