package com.gof.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.Entity;
import com.gof.helper.ArrayHelper;
import com.gof.materials.Material;
import com.gof.nature.Nature;
import com.gof.physics.Direction;
import com.gof.physics.Position;

public class MapTile extends Entity {

	public final static int tileWidth = 120;
	public final static int tileHeight = 60;

	public int direction;
	private boolean solid;

	public Material material;
	public Nature nature;

	public Chunk chunk;

	public MapTile(Chunk c, int x, int y, boolean solid, Material m) {
		super(x, y);
		this.chunk = c;
		setSolid(solid);
		this.material = m;
	}

	private MapTile getOffset(int xi, int yi) {
		xi = (int) (this.getPosition().x + xi);
		yi = (int) (this.getPosition().y + yi);

		if (xi < 0 || xi > Chunk.chunkSize || yi < 0 || yi > Chunk.chunkSize) {
			return null;
		}
		return chunk.getMapTileFromLocalPos(xi, yi);
	}

	public int getRotation() {
		return direction * 90;
	}

	public int getGlobalX() {
		return (int) (chunk.x * Chunk.chunkSize + this.getPosition().x);
	}

	public int getGlobalY() {
		return (int) (chunk.y * Chunk.chunkSize + this.getPosition().y);
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public Sprite getMaterialSprite() {
		return new Sprite(material.getTexture());
	}

	public Sprite getNatureTexture() {
		if (nature == null) {
			return null;
		}
		return new Sprite(nature.getTexture());
	}

	public boolean isSolid() {
		return this.solid;
	}

	public void setDirection(int dir) {
		this.direction = dir;
	}

	public MapTile getTileInDirection(Position direction) {
		return getOffset(direction.x, direction.y);
	}

	public MapTile getLeft() {
		return getTileInDirection(Direction.WEST);
	}

	public MapTile getRight() {
		return getTileInDirection(Direction.EAST);
	}

	public MapTile getAbouve() {
		return getTileInDirection(Direction.NORTH);
	}

	public MapTile getUnder() {
		return getTileInDirection(Direction.SOUTH);
	}

	public List<MapTile> getNeumann() {
		List<MapTile> back = new ArrayList<MapTile>();
		back.add(getLeft());
		back.add(getRight());
		back.add(getAbouve());
		back.add(getUnder());
		return back;
	}

	public List<MapTile> getMoore() {
		List<MapTile> back = getNeumann();
		back.add(getOffset(-1, -1));
		back.add(getOffset(-1, 1));
		back.add(getOffset(1, -1));
		back.add(getOffset(1, 1));
		return back;
	}

	// Bordering

	// private boolean isSameMaterial(boolean[][] sameMaterial) {
	// boolean back = true;
	// int off = sameMaterial.length / 2;
	// for (int x = 0; x < sameMaterial.length; x++) {
	// for (int y = 0; y < sameMaterial[x].length; y++) {
	// if (getOffset(x - off, y - off).material.isSame(material) !=
	// sameMaterial[x][y]) {
	// back = false;
	// }
	// }
	// }
	// return back;
	// }
	//
	// private boolean isSameMaterial(boolean[][]... checks) {
	// for (boolean[][] check : checks) {
	// if (isSameMaterial(check))
	// return true;
	// }
	// return false;
	// }

	// 2 5 8
	// 1 4 7
	// 0 3 6

	// private List<boolean[][]> getInnerCorner() {
	// boolean[][] all = { { false, true, true }, { true, true, true }, { true,
	// true, true } };
	//
	// List<boolean[][]> back = new ArrayList<boolean[][]>();
	// back.add(all);
	//
	// return back;
	// }
	//
	// private boolean isInnerCorner(int d) {
	// List<boolean[][]> checks = getInnerCorner();
	// for (int i = 0; i < d; i++) {
	// for (int x = 0; x < checks.size(); x++) {
	// checks.set(x, ArrayHelper.rotateCounter(checks.get(x)));
	// }
	// }
	// return isSameMaterial(checks.get(0));
	// }
	//
	// public boolean isInnerCornerNorth() {
	// return isInnerCorner(Direction.NORTH);
	// }
	//
	// public boolean isInnerCornerEast() {
	// return isInnerCorner(Direction.EAST);
	// }
	//
	// public boolean isInnerCornerSouth() {
	// return isInnerCorner(Direction.SOUTH);
	// }
	//
	// public boolean isInnerCornerWest() {
	// return isInnerCorner(Direction.WEST);
	// }
	//
	// private List<boolean[][]> getOuterCornerTopRight() {
	// boolean[][] three = { { false, false, true }, { false, true, true }, {
	// true, true, true } };
	// boolean[][] up = { { false, false, false }, { false, true, true }, {
	// true, true, true } };
	// boolean[][] down = { { false, false, true }, { false, true, true }, {
	// false, true, true } };
	// boolean[][] all = { { false, false, false }, { false, true, true }, {
	// false, true, true } };
	//
	// List<boolean[][]> back = new ArrayList<boolean[][]>();
	// back.add(three);
	// back.add(all);
	// back.add(up);
	// back.add(down);
	//
	// return back;
	// }
	//
	// private boolean isOuterCorner(int d) {
	// List<boolean[][]> checks = getOuterCornerTopRight();
	// for (int i = 0; i < d; i++) {
	// for (int x = 0; x < checks.size(); x++) {
	// checks.set(x, ArrayHelper.rotateCounter(checks.get(x)));
	// }
	// }
	// return isSameMaterial(checks.get(0), checks.get(1), checks.get(2),
	// checks.get(3));
	// }
	//
	// public boolean isOuterCornerNorth() {
	// return isOuterCorner(Direction.NORTH);
	// }
	//
	// public boolean isOuterCornerSouth() {
	// return isOuterCorner(Direction.SOUTH);
	// }
	//
	// public boolean isOuterCornerEast() {
	// return isOuterCorner(Direction.EAST);
	// }
	//
	// public boolean isOuterCornerWest() {
	// return isOuterCorner(Direction.WEST);
	// }
	//
	// private List<boolean[][]> getStraightBorderSouth() {
	// boolean[][] all = { { false, true, true }, { false, true, true, }, {
	// false, true, true } };
	// boolean[][] left = { { false, true, true }, { false, true, true }, {
	// true, true, true } };
	// boolean[][] right = { { true, true, true }, { false, true, true }, {
	// false, true, true } };
	// boolean[][] middle = { { true, true, true }, { false, true, true }, {
	// true, true, true } };
	//
	// List<boolean[][]> back = new ArrayList<boolean[][]>();
	// back.add(all);
	// back.add(left);
	// back.add(right);
	// back.add(middle);
	// return back;
	// }
	//
	// private boolean checkStraightBorder(int d) {
	// List<boolean[][]> checks = getStraightBorderSouth();
	// for (int i = 0; i < d; i++) {
	// for (int x = 0; x < checks.size(); x++) {
	// checks.set(x, ArrayHelper.rotateCounter(checks.get(x)));
	// }
	// }
	// return isSameMaterial(checks.get(0), checks.get(1), checks.get(2),
	// checks.get(3));
	// }
	//
	// public boolean isStraightBorderNorth() {
	// return checkStraightBorder(Direction.NORTH);
	// }
	//
	// public boolean isStraightBorderEast() {
	// return checkStraightBorder(Direction.EAST);
	// }
	//
	// public boolean isStraightBorderSouth() {
	// return checkStraightBorder(Direction.SOUTH);
	// }
	//
	// public boolean isStraightBorderWest() {
	// return checkStraightBorder(Direction.WEST);
	// }

}