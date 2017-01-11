package com.gof.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.gof.worldgenerator.GeneratorInterface;
import com.gof.worldgenerator.NatureGenerator;

public class TileWorld {

	private static TileWorld instance;
	private static GeneratorInterface generator;

	// Number of cells
	public static int worldSize = 100;

	public static Chunk chunks[][];

	public TileWorld() {
		instance = this;
		chunks = new Chunk[worldSize][worldSize];
		setGenerator(new NatureGenerator(this));
	}

	public static TileWorld getInstance() {
		return instance;
	}

	public List<MapTile> getArea(int xs, int ys, int xe, int ye) {

		int cxs = globalPosToChunkPos(xs);
		int cys = globalPosToChunkPos(ys);

		int cxe = globalPosToChunkPos(xe);
		int cye = globalPosToChunkPos(ye);

		int xLeft = cxs;
		int xRight = cxe;
		if(xLeft>xRight){
			int h = xLeft;
			xLeft = xRight;
			xRight = h;
		}
		
		int yBottom = cys;
		int yTop = cye;
		if(yBottom>yTop){
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
	
	public int checkGloablPosBoundary(int gx){
		if(gx<0)return 0;
		if(gx>worldSize*Chunk.chunkSize)return worldSize*Chunk.chunkSize;
		return gx;
	}
	
	public Vector2 checkGlobalPosBoundary(Vector2 v){
		return new Vector2(checkGloablPosBoundary((int)v.x),checkGloablPosBoundary((int)v.y));
	}
		
	public static MapTile getMapTileFromGlobalPos(int gx, int gy){
		return getMapTile(getChunkGlobalPos(gx,gy),gx%Chunk.chunkSize,gy%Chunk.chunkSize);
	}

	public static int globalPosToChunkPos(int gx) {
		return gx / Chunk.chunkSize;
	}

	public boolean exsistMapTile(int gx, int gy) {
		return getChunk(globalPosToChunkPos(gx), globalPosToChunkPos(gy)) != null;
	}
	
	public Chunk getChunkGlobalPos(Vector2 globalV) {
		return getChunk((int)globalV.x, (int)globalV.y);
	}

	public static Chunk getChunkGlobalPos(int gx, int gy) {
		
		return getChunk(globalPosToChunkPos(gx), globalPosToChunkPos(gy));
	}

	public static Chunk getChunk(int cx, int cy) {
		if(cx < 0 || cx > worldSize) return null;
		if(cy < 0 || cy > worldSize) return null;	
		
		Chunk c = chunks[cx][cy];
		if (c==null) {
			generator.generateChunkAt(cx, cy);
		}
		return chunks[cx][cy];
	}

	public Chunk setChunk(Chunk c) {
		return chunks[c.x][c.y] = c;
	}

	public static MapTile getMapTile(Chunk c, int tx, int ty) {
		return getChunk(c.x, c.y).getMapTileFromLocalPos(tx, ty);
	}

	public GeneratorInterface getGenerator() {
		return generator;
	}

	public void setGenerator(GeneratorInterface generator) {
		this.generator = generator;
	}

}