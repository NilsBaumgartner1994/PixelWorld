package com.gentlemansoftware.pixelworld.physics;

import java.io.Serializable;

import com.gentlemansoftware.pixelworld.profiles.VarHolder;

public class WorldTime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7714975513050895190L;
	public static final int MAXTICKS = 24 * 60;
	public static final float MINLIGHT = 0.3f;
	public static final float MAXDAYSHADDOW = 0.7f;

	private int ticks;
	private int days;
	
	private static float defaultTimeSpeed = 1f;
	public VarHolder<Float> timeSpeed;
	float renderTime = 0;
	public float ticksPerSecond = 60;
	float refreshRate = 1 / ticksPerSecond;

	public WorldTime(int ticks, int days) {
		this.ticks = ticks;
		this.days = days;
		timeSpeed = new VarHolder<Float>(defaultTimeSpeed);
	}

	public WorldTime(int ticks) {
		this(ticks, 0);
	}

	public WorldTime() {
		this(0);
	}

	public int getTicks() {
		return this.ticks;
	}

	public float getMaxTicks() {
		return MAXTICKS * 1f;
	}

	public float getTickPercent() {
		return getTicks() * 1f / MAXTICKS * 1f;
	}

	// 06:00 --> -90°
	// 12:00 --> 0°
	// 18:00 --> 90°
	public int getShaddowAngle() {
		return (int) (360 - (360 * getTickPercent() + 180) % 360);
	}

	public float getShaddowLength() {
		return (1 - getSunHighStand()) + 0.5f;
	}

	public float getLightIntense() {
		return getSunHighStand() * MAXDAYSHADDOW;
	}

	public float getSunHighStand() {
		float tick = getTickPercent();
		float percentDayTime = 0;
		if (tick > 0.25 && tick < 0.75) {
			percentDayTime = 2 * (tick - 0.25f);
			if (percentDayTime > 0.5f) {
				return 2 * (1 - percentDayTime);
			} else {
				return 2 * percentDayTime;
			}
		} else {
			return 0;
		}
	}

	public void addDays(int delta) {
		this.days += delta;
		System.out.println("WorldTime: Days:" + this.days);
	}

	public int getDays() {
		return this.days;
	}

	public int timePassed(float deltaTime) {
		deltaTime *= timeSpeed.getVar();

		renderTime += deltaTime;

		int steps = 0;
		if (renderTime >= refreshRate) {
			steps = (int) (renderTime / refreshRate);
		}
		renderTime %= refreshRate;
		
		return steps;
	}

	public float getTicksPerSecond() {
		return ticksPerSecond;
	}

	public void addTicks(int delta) {
		this.ticks += delta;
		if (this.ticks >= this.getMaxTicks()) {
			this.ticks %= WorldTime.MAXTICKS;
			this.addDays(1);
		}
	}

}
