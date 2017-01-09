package com.gof.entitys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Inventory {
	
	public static final int MAXITEMSLOTS = 8;
	
	List<Item> items;

	public Inventory(){
		items = new ArrayList<Item>();
		Item[] init = new Item[MAXITEMSLOTS];
		items.addAll(Arrays.asList(init));
	}
	
}
