package com.gof.inputs;

public class Button {

	private boolean isPressed;
	private long pressed;
	private long lastCheck;
	private long released;

	private static final long TYPEDTIMEDEFAULT = 500L;
	private long TypedTime;

	public Button() {
		TypedTime = TYPEDTIMEDEFAULT;
		reset();
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean isTyped() {
		if (isPressed() && pressed > lastCheck) {
			updateLastCheck();
			return true;
		}
		return false;
	}

	public void setState(boolean pressed) {
		if (pressed) {
			press();
		} else {
			release();
		}
	}

	public void press() {
		if (!isPressed) {
			isPressed = true;
			pressed = System.currentTimeMillis();
		}
	}

	public void release() {
		if (isPressed) {
			isPressed = false;
			released = System.currentTimeMillis();
		}
	}

	public long timeSinceLastPress() {
		return timeDiff(pressed);
	}

	public float timeSinceLastPressInSeconds() {
		float time = timeSinceLastPress();
		time /= 1000;
		return time;
	}

	public long timeSinceLastRelease() {
		return timeDiff(released);
	}

	public float timeSinceLastReleaseInSeconds() {
		float time = timeSinceLastRelease();
		time /= 1000;
		return time;
	}

	public long timeSinceLastAction() {
		if (isPressed())
			return timeSinceLastPress();
		return timeSinceLastRelease();
	}

	// Helper
	private long timeDiff(long val) {
		return System.currentTimeMillis() - val;
	}

	private void updateLastCheck() {
		lastCheck = System.currentTimeMillis();
	}

	private long timeSinceLastCheck() {
		return System.currentTimeMillis() - lastCheck;
	}

	private void resetTimes() {
		resetPressTime();
		resetReleaseTime();
		resetCheckTime();
	}

	private void resetPressTime() {
		this.pressed = -1;
	}

	private void resetReleaseTime() {
		this.released = -1;
	}

	private void resetCheckTime() {
		this.lastCheck = -1;
	}

	public void reset() {
		isPressed = false;
		resetTimes();
	}

}
