/// Copyright Ian Parberry, September 2013.
///
/// This file is made available under the GNU All-Permissive License.
///
/// Copying and distribution of this file, with or without modification,
/// are permitted in any medium without royalty provided the copyright
/// notice and this notice are preserved.  This file is offered as-is,
/// without any warranty.
///
/// Created by Ian Parberry, September 2013.
/// Demo by Pablo Nu�ez.
/// Last updated January 31, 2014.

package com.gof.world;

import java.util.ArrayList;
import java.util.List;

import com.gof.entitys.Entity;
import com.gof.game.Main;
import com.gof.game.SaveAndLoadable;
import com.gof.materials.MyMaterial;
import com.gof.physics.Body;
import com.gof.physics.Direction;
import com.gof.worldgenerator.Amortized2DNoise;
import com.gof.worldgenerator.NatureGenerator;

public class Chunk extends SaveAndLoadable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1265662013911180482L;
	public int x;
	public int y;

//	public static final int CHUNKSIZE = 1024;
	public static final int CHUNKSIZE = 32;
	private boolean generated;

	MapTile[][] tiles;
	List<Entity> entitys;
	
	public Chunk(){
		generated = false;
	}
	
	public void save(TileWorld world){
		System.out.println("Saving: "+x+"-"+y+".chunk");
		saveToInternal(TileWorld.WORLDS+world.name+"/"+x+"-"+y+".chunk");
		System.out.println("Saved to: "+TileWorld.WORLDS+world.name+"/"+x+"-"+y+".chunk");
	}
	
	public boolean isGenerated(){
		return this.generated;
	}

	public void create(int _x, int _y, Amortized2DNoise noise) {

		tiles = new MapTile[CHUNKSIZE][CHUNKSIZE];
		entitys = new ArrayList<Entity>();

		x = _x;
		y = _y;

		float[][] cell2 = new float[CHUNKSIZE][CHUNKSIZE];
		for (int i = 0; i < CHUNKSIZE; i++) {
			for (int j = 0; j < CHUNKSIZE; j++) {
				cell2[i][j] = 0.0f;
			}
		}

		Main.log(getClass(), "Generation: start");
		tiles = noise.Generate2DNoise(this, tiles, cell2, NatureGenerator.octave0, NatureGenerator.octave1, y, x);
		this.generated = true;
	}

	public void registerEntity(Entity body) {
		if (!entitys.contains(body)) {
			entitys.add(body);
		}
	}

	public void unregisterEntity(Body body) {
		entitys.remove(body);
	}

	public List<MapTile> getMapTilesFromGlobalPos(int gxs, int gys, int gxe, int gye) {
		int gx = getGloabalPosX();
		int gy = getGloabalPosY();

		gxs -= gx;
		gxe -= gx;
		gys -= gy;
		gye -= gy;

		return getMapTilesFromLocalPos(gxs, gys, gxe, gye);
	}

	public int getGloabalPosX() {
		return x * CHUNKSIZE;
	}

	public int getGloabalPosY() {
		return y * CHUNKSIZE;
	}

	private int checkBoundarys(int c) {
		if (c < 0)
			c = 0;
		if (c > CHUNKSIZE - 1)
			c = CHUNKSIZE - 1;
		return c;
	}

	public List<MapTile> getMapTilesFromLocalPos(int xs, int ys, int xe, int ye) {
		xs = checkBoundarys(xs);
		xe = checkBoundarys(xe);
		ys = checkBoundarys(ys);
		ye = checkBoundarys(ye);

		int xLeft = xs;
		int xRight = xe;
		if (xLeft > xRight) {
			int h = xLeft;
			xLeft = xRight;
			xRight = h;
		}

		int yBottom = ys;
		int yTop = ye;
		if (yBottom > yTop) {
			int h = yBottom;
			yBottom = yTop;
			yTop = h;
		}

		return getMapTilesFromLocalPosRightCoord(xLeft, yTop, xRight, yBottom);
	}

	private List<MapTile> getMapTilesFromLocalPosRightCoord(int xLeft, int yTop, int xRight, int yBottom) {
		List<MapTile> back = new ArrayList<MapTile>();
		for (int y = yTop; y >= yBottom; y--) {
			for (int x = xLeft; x <= xRight; x++) {
				back.add(tiles[x][y]);
			}
		}
		return back;
	}

	public List<MapTile> getAllMapTiles() {
		return getMapTilesFromLocalPos(0, 0, CHUNKSIZE, CHUNKSIZE);
	}

	public MapTile getMapTileFromLocalPos(int x, int y) {
		if (x < 0 || x >= CHUNKSIZE || y < 0 || y >= CHUNKSIZE) {
			return null;
		}
		return tiles[x][y];
	}
}
