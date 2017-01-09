package com.gof.entitys;

import com.gof.materials.Material;

public class Item {
	
	public static final int maxItemStackAmount = 16;
	
	int amount;
	Material material;
	
	public Item(Material m, int amount){
		this.material = m;
		
		this.amount = amount>maxItemStackAmount ? maxItemStackAmount : amount;
	}
	
	public Item(Material m){
		this(m,1);
	}
	
	
	
}
