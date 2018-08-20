package com.gof.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.Entity;
import com.gof.entitys.Human;
import com.gof.game.Main;
import com.gof.physics.WorldTime;
import com.gof.worldgenerator.GeneratorInterface;
import com.gof.worldgenerator.NatureGenerator;

public class TileWorld {

	private static TileWorld instance;
	private GeneratorInterface generator;

	// Number of cells
	public int worldSize = 100;

	public Chunk chunks[][];
	public List<Chunk> activeChunks;

	public WorldTime time;

	public TileWorld() {
		instance = this;
		activeChunks = new ArrayList<Chunk>();
		chunks = new Chunk[worldSize][worldSize];
		setGenerator(new NatureGenerator(this));
		time = new WorldTime(0);
	}
	
	public void activateChunk(Chunk c){
		if(!activeChunks.contains(c)){
			activeChunks.add(c);
		}
	}
	
	public void deactivateChunk(Chunk c){
		activeChunks.remove(c);
	}
	
	public void deactivateAllChunks(){
		for(Chunk[] chunks : chunks){
			for(Chunk c : chunks){
				deactivateChunk(c);
			}
		}
	}
	
	public void updateEntitysBodys(int steps) {
		for(Chunk c : activeChunks){
			for(Entity e : c.entitys){
				e.calcPhysicStep(steps);
			}
		}
	}

	public List<MapTile> getArea(int xs, int ys, int xe, int ye) {

		int cxs = globalPosToChunkPos(xs);
		int cys = globalPosToChunkPos(ys);

		int cxe = globalPosToChunkPos(xe);
		int cye = globalPosToChunkPos(ye);

		int xLeft = cxs;
		int xRight = cxe;
		if (xLeft > xRight) {
			int h = xLeft;
			xLeft = xRight;
			xRight = h;
		}

		int yBottom = cys;
		int yTop = cye;
		if (yBottom > yTop) {
			int h = yBottom;
			yBottom = yTop;
			yTop = h;
		}

		yBottom = checkGloablPosBoundary(yBottom);
		yTop = checkGloablPosBoundary(yTop);
		xLeft = checkGloablPosBoundary(xLeft);
		xRight = checkGloablPosBoundary(xRight);

		List<MapTile> back = new ArrayList<MapTile>();
		for (int y = yTop; y > yBottom - 1; y--) {
			for (int x = xLeft; x < xRight + 1; x++) {

				Chunk ch = getChunk(x, y);
				List<MapTile> chunkBack = ch.getMapTilesFromGlobalPos(xs, ys, xe, ye);
				back.addAll(chunkBack);
			}
		}

		return back;
	}

	public int checkGloablPosBoundary(int gx) {
		if (gx < 0)
			return 0;
		if (gx > worldSize * Chunk.CHUNKSIZE)
			return worldSize * Chunk.CHUNKSIZE;
		return gx;
	}

	public Vector2 checkGlobalPosBoundary(Vector2 v) {
		return new Vector2(checkGloablPosBoundary((int) v.x), checkGloablPosBoundary((int) v.y));
	}

	public MapTile getMapTileFromGlobalPos(int gx, int gy) {
		return getMapTile(getChunkGlobalPos(gx, gy), gx % Chunk.CHUNKSIZE, gy % Chunk.CHUNKSIZE);
	}

	public static int globalPosToChunkPos(int gx) {
		return gx / Chunk.CHUNKSIZE;
	}

	public boolean exsistMapTile(int gx, int gy) {
		return getChunk(globalPosToChunkPos(gx), globalPosToChunkPos(gy)) != null;
	}

	public Chunk getChunkGlobalPos(Vector2 globalV) {
		return getChunk((int) globalV.x, (int) globalV.y);
	}

	public Chunk getChunkGlobalPos(int gx, int gy) {
		return getChunk(globalPosToChunkPos(gx), globalPosToChunkPos(gy));
	}

	public Chunk getChunk(int cx, int cy) {
		if (cx < 0 || cx > worldSize)
			return null;
		if (cy < 0 || cy > worldSize)
			return null;

		Chunk c = chunks[cx][cy];
		if (c == null) {
			generator.generateChunkAt(cx, cy);
		}
		return chunks[cx][cy];
	}

	public Chunk setChunk(Chunk c) {
		return chunks[c.x][c.y] = c;
	}

	public MapTile getMapTile(Chunk c, int tx, int ty) {
		return getChunk(c.x, c.y).getMapTileFromLocalPos(tx, ty);
	}

	public GeneratorInterface getGenerator() {
		return generator;
	}

	public void setGenerator(GeneratorInterface generator) {
		this.generator = generator;
	}

}