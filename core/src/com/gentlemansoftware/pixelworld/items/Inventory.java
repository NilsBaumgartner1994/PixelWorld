package com.gentlemansoftware.pixelworld.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.gentlemansoftware.pixelworld.game.Main;

public class Inventory {

	public static final int MAXITEMSLOTS = 8;

	List<AbstractItem> items;
	public int activSlot;

	public Inventory() {
		activSlot = 0;
		items = new ArrayList<AbstractItem>();
		Item[] init = new Item[MAXITEMSLOTS];
		items.addAll(Arrays.asList(init));
	}

	private void setItem(AbstractItem item, int position) {
		if (validPosition(position)) {
			items.set(position, item);
		}
	}
	
	public AbstractItem getItem(int position){
		if (validPosition(position)) {
			return items.get(position);
		}
		return null;
	}
	
	public boolean isActivSlot(int slot){
		return getActivSlot()==slot;
	}
	
	public int getActivSlot(){
		return this.activSlot;
	}
	
	public AbstractItem getActivItem(){
		return getItem(getActivSlot());
	}
	
	public void setActivSlot(int slot){
		this.activSlot = slot%MAXITEMSLOTS;
		if(this.activSlot<0){
			setActivSlot(MAXITEMSLOTS+this.activSlot);
		}
	}
	
	private boolean validPosition(int position){
		return (position < items.size() && position >= 0);
	}

	public void addItem(AbstractItem item) {
		setItem(item,getFirstEmptySlot());
	}
	
	private int getFirstEmptySlot(){
		int pos = 0;
		for(AbstractItem item: items){
			if(item==null){
				return pos;
			}
			pos++;
		}
		return -1; 
	}

}
