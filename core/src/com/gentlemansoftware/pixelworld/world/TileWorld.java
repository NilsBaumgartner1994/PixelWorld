package com.gentlemansoftware.pixelworld.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.SaveAndLoadable;
import com.gentlemansoftware.pixelworld.physics.WorldTime;
import com.gentlemansoftware.pixelworld.worldgenerator.GeneratorInterface;
import com.gentlemansoftware.pixelworld.worldgenerator.NatureGenerator;

public class TileWorld extends SaveAndLoadable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828667796302397997L;

	private GeneratorInterface generator;

	String name;

	public Map<String, Chunk> chunks;

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
		chunks = new HashMap<String, Chunk>();

		setGeneratedChunks(generatedChunks);
		this.time = time;
		setEntityMap(entitys);
		setGenerator(generator);
	}
	
	public void setGeneratedChunks(List<Chunk> generatedChunks){
		if (generatedChunks != null) {
			for (Chunk chunk : generatedChunks) {
				setChunk(chunk);
			}
		}
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
		for (Chunk c : chunks.values()) {
			if (c != null) {
				if (c.isGenerated()) {
					System.out.println("Save Chunk: " + c.x + ":" + c.y);
					c.save(this);
				}
			}
		}
	}

	public static TileWorld load(String name) {
		TileWorld world = new TileWorld(name, null, new WorldTime(0), null, null);

		List<Chunk> chunks = new LinkedList<Chunk>();

		FileHandle dirHandle;

		dirHandle = Gdx.files.internal("./"+WORLDS+name+"/");

		System.out.println("Show me All Files");
		for (FileHandle entry : dirHandle.list()) {
			if(entry.extension().equals(Chunk.ENDINGNAME)){
				chunks.add(Chunk.loadFromInternal(entry.path(), Chunk.class));
			}
		}
		
		world.setGeneratedChunks(chunks);

		// return loadFromExternal(WORLDS + name + ENDING, TileWorld.class);
		return world;
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
		for (Chunk c : chunks.values()) {
			deactivateChunk(c);
		}
	}

	public void updateEntitysBodys(int steps) {
		for (Chunk c : activeChunks) {
			List<Entity> copiedEntitys = new LinkedList<Entity>(c.entitys);
			for (Entity e : copiedEntitys) {
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

		// make left lower than right
		int xLeft = cxs > cxe ? cxe : cxs;
		int xRight = cxs > cxe ? cxs : cxe;

		// make bottom lower than top
		int yBottom = cys > cye ? cye : cys;
		int yTop = cys > cye ? cys : cye;

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
		// if (gx < 0)
		// return 0;
		// if (gx > worldSize * Chunk.CHUNKSIZE)
		// return worldSize * Chunk.CHUNKSIZE;
		return gx;
	}

	public Vector2 checkGlobalPosBoundary(Vector2 v) {
		return new Vector2(checkGloablPosBoundary((int) v.x), checkGloablPosBoundary((int) v.y));
	}

	public MapTile getMapTileFromGlobalPos(int gx, int gy) {
		int clx = gx;
		int cly = gy;

		clx = gx % Chunk.CHUNKSIZE;
		cly = gy % Chunk.CHUNKSIZE;

		if (clx < 0) {
			clx = Chunk.CHUNKSIZE + clx;
		}
		if (cly < 0) {
			cly = Chunk.CHUNKSIZE + cly;
		}

		return getMapTile(getChunkGlobalPos(gx, gy), clx, cly);
	}

	public static int globalPosToChunkPos(int gx) {
		if (gx < 0 && gx % Chunk.CHUNKSIZE != 0) {
			return gx / Chunk.CHUNKSIZE - 1;
		}
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
		// if (cx < 0 || cx > worldSize)
		// return null;
		// if (cy < 0 || cy > worldSize)
		// return null;

		Chunk c = chunks.get(cx + "-" + cy);
		if (c == null || !c.isGenerated()) {
			generator.generateChunkAt(cx, cy);
		}

		return chunks.get(cx + "-" + cy);
	}

	public Chunk setChunk(Chunk c) {
		return chunks.put(c.x + "-" + c.y, c);
	}

	public MapTile getMapTile(Chunk c, int tx, int ty) {
		if (c == null)
			return null;
		Chunk generatedChunk = getChunk(c.x, c.y);
		if (generatedChunk == null)
			return null;
		return generatedChunk.getMapTileFromLocalPos(tx, ty);
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