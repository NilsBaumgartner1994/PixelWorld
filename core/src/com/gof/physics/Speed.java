package com.gof.physics;

import com.gof.game.Main;

public class Speed {
	
	public static int sneak = 1;
	public static int walk = 4;
	public static int run = 16;

	public Position direction;
	int inTicks;
	int ticksPassed;

	public Speed(Position direction, int inTicks, int ticksPassed, boolean minimize) {
		this.direction = direction;
		this.inTicks = inTicks;
		this.ticksPassed = ticksPassed;

		if (minimize) {
			minimize();
		}
	}

	public Speed(Position direction, int inTicks, boolean minimize) {
		this(direction, inTicks, 0, minimize);
	}

	public Speed() {
		this(new Position(), 1, false);
	}

	private void minimize() {
		int xTotal = direction.x * Position.fractionMax_x + direction.xFraction;
		int yTotal = direction.y * Position.fractionMax_y + direction.yFraction;

		int xyggt = ggt(xTotal, yTotal);

		int divisor = ggt(xyggt, inTicks);

		Position newDir = new Position(0, xTotal / divisor, 0, yTotal / divisor);
		this.direction = newDir;
		this.inTicks = inTicks / divisor;
	}

	public Speed cpy() {
		return new Speed(this.direction.cpy(), this.inTicks, this.ticksPassed, false);
	}

	public Position calcStep(int ticksPassed) {
		this.ticksPassed += ticksPassed;
		if (this.ticksPassed >= inTicks) {			
			int amount = this.ticksPassed / inTicks;
			this.ticksPassed %= inTicks;
			return this.direction.cpy().scaleAndSet(amount);
		}
		return new Position(0, 0, 0, 0);
	}

	/**
	 * http://st-page.de/2015/01/java-ggt-groesster-gemeinsamer-teiler/ Der
	 * größte gemeinsame Teiler wird hier berechnet. Die Laufzeit entspricht
	 * maximal der größten Zahl.
	 * 
	 * @param a
	 *            Erste Zahl
	 * @param b
	 *            Zweite Zahl
	 * @return Größter gemeinsame Teiler
	 */
	public static int ggt(int a, int b) {
		// Hier versuche ich Arbeitsaufwand (Rechnenzeit) zu sparen in dem ich
		// mir die kleinste Zahl suche.
		int h = (a > b) ? b : a;
		// Der GGT wird hier berechnet.
		for (int i = h; i > 1; i--) {
			if ((a % i) == 0 && (b % i) == 0) {
				return i;
			}
		}
		// teilerfremde Zahlen haben immer den Teiler 1
		return 1;
	}

}
