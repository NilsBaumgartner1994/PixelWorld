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
/// Demo by Pablo Nuñez.
/// Last updated January 31, 2014.

package com.gof.world;

import java.util.ArrayList;
import java.util.List;

import com.gof.game.Main;
import com.gof.materials.Grass;
import com.gof.physics.Direction;
import com.gof.worldgenerator.NatureGenerator;

public class Chunk {

	public int x;
	public int y;

	public static final int chunkSize = 1024;
	
	MapTile[][] tiles;

	public void create(int _x, int _y, Amortized2DNoise noise) {

		tiles = new MapTile[chunkSize][chunkSize];

		x = _x;
		y = _y;

		float[][] cell2 = new float[chunkSize][chunkSize];
		for (int i = 0; i < chunkSize; i++) {
			for (int j = 0; j < chunkSize; j++) {
				cell2[i][j] = 0.0f;
			}
		}

		Main.log(getClass(), "Generation: start");
		tiles = noise.Generate2DNoise(this, tiles, cell2, NatureGenerator.octave0, NatureGenerator.octave1, y, x);
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
		return x * chunkSize;
	}

	public int getGloabalPosY() {
		return y * chunkSize;
	}

	private int checkBoundarys(int c) {
		if (c < 0)
			c = 0;
		if (c > chunkSize-1)
			c = chunkSize-1;
		return c;
	}

	public List<MapTile> getMapTilesFromLocalPos(int xs, int ys, int xe, int ye) {
		xs = checkBoundarys(xs);
		xe = checkBoundarys(xe);
		ys = checkBoundarys(ys);
		ye = checkBoundarys(ye);

		int xLeft = xs;
		int xRight = xe;
		if(xLeft>xRight){
			int h = xLeft;
			xLeft = xRight;
			xRight = h;
		}
		
		int yBottom = ys;
		int yTop = ye;
		if(yBottom>yTop){
			int h = yBottom;
			yBottom = yTop;
			yTop = h;
		}
		
		return getMapTilesFromLocalPosRightCoord(xLeft,yTop,xRight,yBottom);
	}
	
	private List<MapTile> getMapTilesFromLocalPosRightCoord(int xLeft, int yTop, int xRight, int yBottom){
		List<MapTile> back = new ArrayList<MapTile>();
		for (int y = yTop; y >= yBottom; y--) {
		for (int x = xLeft; x <= xRight; x++) {
				back.add(tiles[x][y]);
			}
		}
		return back;
	}

	public List<MapTile> getAllMapTiles() {
		return getMapTilesFromLocalPos(0, 0, chunkSize, chunkSize);
	}

	public MapTile getMapTileFromLocalPos(int x, int y) {
		return tiles[x][y];
	}
}
