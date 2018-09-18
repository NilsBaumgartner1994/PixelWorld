package com.gentlemansoftware.pixelworld.worldgenerator;

import java.util.Random;

import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class NatureGenerator implements GeneratorInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7778561222591949339L;

	TileWorld world;

	public static boolean debug_generation = false;

	// Altering will make it look more like continents or islands.
	public static float seaLevel = -0.2f;

	// How much sand in the shore is created
	public static float sandAmount = 0.3f;
	public static Random random;

	// Alter this value for different results
	public static int octave0 = 2;
	public static int octave1 = 6;
	public static Amortized2DNoise noise;

	public NatureGenerator() {
		random = new Random();
		noise = new Amortized2DNoise(Amortized2DNoise.CELLSIZE2D);
	}
	
	@Override
	public void setTileWorld(TileWorld world){
		this.world = world;
	}

	@Override
	public void generateChunkAt(int cx, int cy) {
		Main.log(getClass(), "Chunk(" + cx + "|" + cy + "): generating");
		Chunk c = new Chunk(this.world);
		c.create(cx, cy, noise);
		world.setChunk(c);
		c.spawnAllBlocks();
		Main.log(getClass(), "Chunk(" + cx + "|" + cy + "): ready");
	}

}