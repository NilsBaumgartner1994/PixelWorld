package com.gof.physics;

public class WorldTime {

	public static final int MAXTICKS = 24 * 60;
	
	private int ticks;

	public WorldTime(int ticks) {
		this.ticks = ticks;
	}

	public int getTicks() {
		return this.ticks;
	}
	
	public float getTickPercent(){
		return getTicks()*1f/MAXTICKS*1f;
	}
	
	private final float MINLIGHT = 0.3f;
	
	// 06:00 --> -90°
	// 12:00 --> 0°
	// 18:00 --> 90°
	public int getShaddowAngle(){
		return (int) (360-(360*getTickPercent()+180)%360);
	}
	
	public float getLightIntense(){
		return 0;
//		return (float) ((1-MINLIGHT)*(0.5f+0.5f*Math.sin(2*Math.PI*getTickPercent())));
	}

	public void addTicks(int delta) {
		this.ticks = (this.ticks + delta) % WorldTime.MAXTICKS;
	}

}
