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

	public float getTickPercent() {
		return getTicks() * 1f / MAXTICKS * 1f;
	}

	private final float MINLIGHT = 0.3f;

	// 06:00 --> -90°
	// 12:00 --> 0°
	// 18:00 --> 90°
	public int getShaddowAngle() {
		return (int) (360 - (360 * getTickPercent() + 180) % 360);
	}
	
	public float getShaddowLength(){
		return (1-getSunHighStand())+0.5f;
	}
	
	public static final float MAXDAYSHADDOW = 0.7f;

	public float getLightIntense() {
		return getSunHighStand()*MAXDAYSHADDOW;
	}
	
	public float getSunHighStand() {
		float tick = getTickPercent();
		float percentDayTime = 0;
		if (tick > 0.25 && tick < 0.75) {
			percentDayTime = 2 * (tick - 0.25f);
			if (percentDayTime > 0.5f) {
				return 2 * (1 - percentDayTime);
			} else {
				return 2*percentDayTime;
			}
		} else {
			return 0;
		}
	}

	public void addTicks(int delta) {
		this.ticks = (this.ticks + delta) % WorldTime.MAXTICKS;
	}

}
