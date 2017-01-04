package com.gof.Inputs;

public class Button {
	
	private boolean isPressed;
	private long pressed;
	private long released;
	
	public Button(){
		isPressed = false;
		resetTimes();
	}
	
	public boolean isPressed(){
		return isPressed;
	}
	
	public void press(){
		isPressed = true;
		pressed = System.currentTimeMillis();
	}
	
	public void release(){
		isPressed = false;
		released = System.currentTimeMillis();
	}
	
	public long timeSinceLastPress(){
		return timeDiff(pressed);
	}
	
	public float timeSinceLastPressInSeconds(){
		float time = timeSinceLastPress();
		time/=1000;
		return time;
	}
	
	public long timeSinceLastRelease(){
		return timeDiff(released);
	}
	
	public float timeSinceLastReleaseInSeconds(){
		float time = timeSinceLastRelease();
		time/=1000;
		return time;
	}
	
	public long timeSinceLastAction(){
		if(isPressed()) return timeSinceLastPress();
		return timeSinceLastRelease();
	}
	
	
	//Helper
	private long timeDiff(long val){
		return System.currentTimeMillis()-val;
	}
	
	private void resetTimes(){
		resetPressTime();
		resetReleaseTime();
	}
	
	private void resetPressTime(){
		this.pressed=-1;
	}
	
	private void resetReleaseTime(){
		this.released=-1;
	}
	
	
	
	
}
