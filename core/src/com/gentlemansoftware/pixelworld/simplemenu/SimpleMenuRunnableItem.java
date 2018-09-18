package com.gentlemansoftware.pixelworld.simplemenu;

public class SimpleMenuRunnableItem extends SimpleMenuNameItem{
	
	Runnable r;
	
	public SimpleMenuRunnableItem(String title, SimpleMenuNameTypes type, Runnable r) {
		super(title,type);
		this.r = r;
	}

	@Override
	public void select() {
		// TODO Auto-generated method stub
		r.run();
	}

}
