package com.gof.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.gof.entitys.Entity;
import com.gof.entitys.Human;
import com.gof.game.Main;
import com.gof.game.SaveAndLoadable;
import com.gof.physics.WorldTime;
import com.gof.worldgenerator.GeneratorInterface;
import com.gof.worldgenerator.NatureGenerator;

public class TileWorld extends SaveAndLoadable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828667796302397997L;

	private GeneratorInterface generator;

	String name;

	// Number of cells
	public int worldSize = 100;

	public Chunk chunks[][];
	public List<Chunk> activeChunks;
	public Map<Integer, Entity> entitys = new HashMap<Integer, Entity>();

	public WorldTime time;

	public TileWorld(String name) {
		this(name, null, new WorldTime(0), null, new NatureGenerator());
	}

	public TileWorld(String name, List<Chunk> generatedChunks, WorldTime time, Map<Integer, Entity> entitys,
			NatureGenerator generator) {
		this.name = name;
		activeChunks = new ArrayList<Chunk>();
		chunks = new Chunk[worldSize][worldSize];
		for(int i=0; i<worldSize;i++){
			for(int j=0; j<worldSize;j++){
				chunks[i][j] = new Chunk();
			}
		}
		
		if (generatedChunks != null) {
			for (Chunk chunk : generatedChunks) {
				setChunk(chunk);
			}
		}
		this.time = time;
		setEntityMap(entitys);
		setGenerator(generator);
	}

	public void setEntityMap(Map<Integer, Entity> entitys) {
		if (entitys == null) {
			entitys = new HashMap<Integer, Entity>();
		}
		this.entitys = entitys;
	}

	public static final String DATA = "data/";
	public static final String WORLDS = DATA + "worlds/";
	public static final String ENDING = ".world";

	public void save() {
		for (Chunk[] chunks : chunks) {
			for (Chunk c : chunks) {
				if (c != null) {
					if (c.isGenerated()) {
						c.save(this);
					}
				}
			}
		}
	}

	public static TileWorld load(String name) {
		return loadFromExternal(WORLDS + name + ENDING, TileWorld.class);
	}

	public void activateChunk(Chunk c) {
		if (!activeChunks.contains(c)) {
			activeChunks.add(c);
		}
	}

	public void deactivateChunk(Chunk c) {
		activeChunks.remove(c);
	}

	public void deactivateAllChunks() {
		for (Chunk[] chunks : chunks) {
			for (Chunk c : chunks) {
				deactivateChunk(c);
			}
		}
	}

	public void updateEntitysBodys(int steps) {
		for (Chunk c : activeChunks) {
			for (Entity e : c.entitys) {
				e.updateLogic();
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
		} else {
			if (!c.isGenerated()) {
				generator.generateChunkAt(cx, cy);
			}
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
		if (generator == null) {
			generator = new NatureGenerator();
		}
		this.generator = generator;
		this.generator.setTileWorld(this);
	}

}