package com.gof.physics;

import com.badlogic.gdx.math.Vector2;
import com.gof.world.MapTile;
import com.gof.world.TileWorld;

public class Position implements Comparable<Position> {

	public int x;
	public int y;
	public int xFraction;
	public int yFraction;

	public static final int fractionMax_x = MapTile.tileWidth;
	public static final int fractionMax_y = MapTile.tileHeight;

	public Position(int x, int xFraction, int y, int yFraction) {
		set(x, xFraction, y, yFraction);
	}

	public Position set(int x, int xFraction, int y, int yFraction) {
		this.x = x;
		this.xFraction = xFraction;
		this.y = y;
		this.yFraction = yFraction;
		return calcOverflow();
	}

	public Position(int x, int y) {
		this(x, 0, y, 0);
	}
	
	public Position(){
		this(0,0);
	}

	public Position calcOverflow() {
		if(xFraction<0){
			xFraction+=fractionMax_x;
			this.x--;
		}
		if(yFraction<0){
			yFraction+=fractionMax_y;
			this.y--;
		}
		
		this.x = this.x + xFraction / fractionMax_x;
		this.y = this.y + yFraction / fractionMax_y;
		this.xFraction = this.xFraction % fractionMax_x;
		this.yFraction = this.yFraction % fractionMax_y;
		return this;
	}
	
	public Position getPosition(){
		return cpy();
	}

	public Position cpy() {
		return new Position(this.x, this.xFraction, this.y, this.yFraction);
	}

	public Position addAndSet(int x, int xFraction, int y, int yFraction) {
		return set(this.x + x, this.xFraction + xFraction, this.y + y, this.yFraction + yFraction);
	}

	public Position addAndSet(Position p) {
		return addAndSet(p.x, p.xFraction, p.y, p.yFraction);
	}
	
	public Position subAndSet(Position p){
		return addAndSet(p.scaleAndSet(-1));
	}

	public Position scaleAndSet(int scale_x, int scale_y) {
		return set((this.x * scale_x), (this.xFraction * scale_x), (this.y * scale_x), (this.yFraction * scale_x));
	}

	public Position scaleAndSet(int scale) {
		return scaleAndSet(scale, scale);
	}

	public Position scaleAndSet(Position p) {
		return scaleAndSet(p.x, p.y);
	}

	public Position scaleAndSet(float scale_x, float scale_y) {
		return set((int) (this.x * scale_x), (int) (this.xFraction * scale_x), (int) (this.y * scale_x),
				(int) (this.yFraction * scale_x));
	}

	public Position scaleAndSet(float scale) {
		return scaleAndSet(scale);
	}

	public Position distance(Position other) {
		Position me = this.cpy();
		Position ot = other.cpy();
		return me.addAndSet(ot.scaleAndSet(-1));
	}

	public float heightCompareLength() {
		return this.x + this.y + fractionLength();
	}
	
	public float lengthX(){
		return this.x + fractionLengthX();
	}
	
	public float lengthY(){
		return this.y + fractionLengthY();
	}
	
	public float length() {
		return Math.abs(lengthX())+Math.abs(lengthY());
	}

	public Position rotate(float degree) {
		int divisor = getFractionDivisor();
		int x_total = this.x * divisor + getFractionXComparable();
		int y_total = this.y * divisor + getFractionYComparable();

		/*
		 * Normal Vector rotation https://en.wikipedia.org/wiki/Rotation_matrix
		 */

		int x_total_new = (int) (Math.cos(Math.toRadians(degree)) * x_total
				- Math.sin(Math.toRadians(degree)) * y_total);
		int y_total_new = (int) (Math.sin(Math.toRadians(degree)) * x_total
				+ Math.cos(Math.toRadians(degree)) * y_total);

		int x_new = x_total_new / divisor;
		int y_new = y_total_new / divisor;

		int xFraction_new = (x_total_new - x_new * divisor) / fractionMax_y;
		int yFraction_new = (y_total_new - y_new * divisor) / fractionMax_x;

		set(x_new, xFraction_new, y_new, yFraction_new);
		return this;
	}

	private int getFractionDivisor() {
		int divisor = fractionMax_x * fractionMax_y;
		return (divisor == 0) ? 1 : divisor;
	}

	private int getFractionXComparable() {
		return this.xFraction * fractionMax_y;
	}

	private int getFractionYComparable() {
		return this.yFraction * fractionMax_x;
	}
	
	private float fractionLengthX(){
		int xComp = getFractionXComparable();
		int comp = getFractionDivisor();
		return (1.0f * xComp) / comp;
	}
	
	private float fractionLengthY(){
		int yComp = getFractionYComparable();
		int comp = getFractionDivisor();
		return (1.0f * yComp) / comp;
	}

	private float fractionLength() {
		return fractionLengthX() + fractionLengthY();
	}
	
	public static Position getPositionDirectionFromVector(Vector2 v) {
		float a = v.angle();

		if (a >= 45 && a <= 180 - 45)
			return Direction.NORTH;
		if (a > 180 - 45 && a < 180 + 45)
			return Direction.WEST;
		if (a >= 180 + 45 && a <= 360 - 45)
			return Direction.SOUTH;
		return Direction.EAST;
	}

	@Override
	public int compareTo(Position o) {
		float me = heightCompareLength();
		float other = o.heightCompareLength();

		if (me > other) {
			return -1;
		}
		if (me < other) {
			return 1;
		}

		// both bodys same vertical height
		if (this.x < o.x) {
			return 1;
		}
		if (this.x > o.x) {
			return -1;
		}

		if (this.xFraction < o.xFraction) {
			return 1;
		}
		if (this.xFraction > o.xFraction) {
			return -1;
		}

		return 0;
	}
	
	public String toString(){
		return "X: "+this.x+","+this.xFraction+" | Y: "+this.y+","+this.yFraction;
	}

}
