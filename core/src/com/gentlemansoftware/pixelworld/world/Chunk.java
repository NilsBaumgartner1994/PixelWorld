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

package com.gentlemansoftware.pixelworld.world;

import java.util.ArrayList;
import java.util.List;

import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Body;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.worldgenerator.Amortized2DNoise;
import com.gentlemansoftware.pixelworld.worldgenerator.NatureGenerator;

public class Chunk extends SaveAndLoadable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1265662013911180482L;
	public int x;
	public int y;

//	public static final int CHUNKSIZE = 1024;
	public static final int CHUNKSIZE = 32;

	MapTile[][] tiles;
	List<Entity> entitys;
	
	public transient TileWorld world;
	
	public Chunk(){
		
	}
	
	public void setTransients(TileWorld world){
		this.world = world;
		for(MapTile[] t1 : tiles){
			for(MapTile t : t1){
				t.setTransients(this);
			}
		}
		for(Entity e : entitys){
			e.setTransient(world);
		}
	}
	
	public String toString(){
		return "["+this.x+"|"+this.y+"]";
	}
	
	public Chunk(TileWorld world, int cx, int cy){
		this.world = world;
		this.x = cx;
		this.y = cy;
		tiles = new MapTile[CHUNKSIZE][CHUNKSIZE];
		for(int x = 0; x<CHUNKSIZE; x++){
			for(int y = 0; y<CHUNKSIZE; y++){
				tiles[x][y]= new MapTile(this,x,y);
			}
		}
		entitys = new ArrayList<Entity>();
	}
	
	public final static String ENDINGNAME = "chunk";
	public final static String ENDING = "."+ENDINGNAME;
	
	public void save(TileWorld world){
		System.out.println("Saving: "+x+"-"+y+ENDING);
		saveToInternal(TileWorld.WORLDS+world.name+"/"+x+"-"+y+ENDING);
		System.out.println("Saved to: "+TileWorld.WORLDS+world.name+"/"+x+"-"+y+ENDING);
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
