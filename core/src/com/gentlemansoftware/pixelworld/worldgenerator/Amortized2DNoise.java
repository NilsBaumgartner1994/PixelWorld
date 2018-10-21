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

package com.gentlemansoftware.pixelworld.worldgenerator;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.entitys.TallGrass;
import com.gentlemansoftware.pixelworld.entitys.Tree;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.world.Block;
import com.gentlemansoftware.pixelworld.world.Chunk;
import com.gentlemansoftware.pixelworld.world.MapTile;

public class Amortized2DNoise {
	float[] uax, vax, ubx, vbx, uay, vay, uby, vby; // /< Amortized noise
													// tables.
	float[] spline; // /< Spline array.
	float[][] workspace; // /< Temporary workspace.
	int size; // /< Size of workspace.

	public static int CELLSIZEMULT = 32;
	public static int CELLSIZE2D = Chunk.CHUNKSIZE * CELLSIZEMULT;

	Pixmap pixmap;

	public ArrayList<Texture> textures = new ArrayList<Texture>();

	public float lerp(float t, float a, float b) {
		return (a + t * (b - a));
	}

	public Amortized2DNoise(int n) {
		uax = new float[n + 1]; // /< X coordinate of u used to compute a in the
								// Perlin noise algorithm.
		vax = new float[n + 1]; // /< X coordinate of v used to compute a in the
								// Perlin noise algorithm.
		ubx = new float[n + 1]; // /< X coordinate of u used to compute b in the
								// Perlin noise algorithm.
		vbx = new float[n + 1]; // /< X coordinate of v used to compute b in the
								// Perlin noise algorithm.
		uay = new float[n + 1]; // /< Y coordinate of u used to compute a in the
								// Perlin noise algorithm.
		uby = new float[n + 1]; // /< Y coordinate of v used to compute a in the
								// Perlin noise algorithm.
		vay = new float[n + 1]; // /< Y coordinate of u used to compute b in the
								// Perlin noise algorithm.
		vby = new float[n + 1]; // /< Y coordinate of v used to compute b in the
								// Perlin noise algorithm.

		spline = new float[n + 1];

		workspace = new float[n + 1][n + 1];
	}

	// / Fill amortized noise table bottom up.
	// / \param t Amortized noise table.
	// / \param s Initial value.
	// / \param n Granularity.
	void FillUp(float[] t, float s, int n) {
		float d = s / n;
		t[0] = 0.0f;
		for (int i = 1; i < n; i++)
			t[i] = t[i - 1] + d;
	} // FillUp

	// / Fill amortized noise table top down.
	// / \param t Amortized noise table.
	// / \param s Initial value.
	// / \param n Granularity.
	void FillDn(float[] t, float s, int n) {
		float d = -s / n;
		t[n - 1] = d;
		for (int i = n - 2; i >= 0; i--)
			t[i] = t[i + 1] + d;
	}
	
	public int h(int x) {
		long xl = (long) x;
		return (int) (1664525L * xl * xl + 1013904223L); // constants from the
															// book
															// "Numerical
															// Recipes"
	} // h1

	// / A 2D hash function.
	// / This is the standard method for making a 2D hash function out of a 1D
	// hash function.
	// / \param x X coordinate of value to be hashed.
	// / \param y Y coordinate of value to be hashed.
	// / \return Hash of (x, y).

	public int h(int x, int y) {
		return h(h(x) + y);
	} // h

	// / Initialize the amortized noise tables
	// / as described in Ian Parberry's paper "Amortized Noise" (google it).
	// / \param x0 x coordinate of top left corner of cell.
	// / \param y0 y coordinate of top left corner of cell.
	// / \param n Granularity.
	void initEdgeTables(int x0, int y0, int n) {
		// compute gradients at corner points
		int b0 = h(x0, y0);
		int b1 = h(x0, y0 + 1);
		int b2 = h(x0 + 1, y0);
		int b3 = h(x0 + 1, y0 + 1);

		// fill inferred gradient tables from corner gradients
		FillUp(uax, (float) Math.cos((float) b0), n);
		FillDn(vax, (float) Math.cos((float) b1), n);
		FillUp(ubx, (float) Math.cos((float) b2), n);
		FillDn(vbx, (float) Math.cos((float) b3), n);
		FillUp(uay, (float) Math.sin((float) b0), n);
		FillUp(vay, (float) Math.sin((float) b1), n);
		FillDn(uby, (float) Math.sin((float) b2), n);
		FillDn(vby, (float) Math.sin((float) b3), n);
	} // initEdgeTables

	// / Initialize the spline table
	// / as described in Ian Parberry's paper "Amortized Noise" (google it).
	// / \param n Granularity.

	void initSplineTable(int n) {
		for (int i = 0; i < n; i++) { // for each table entry
			float t = (float) i / n; // offset between grid points
			spline[i] = t * t * t * (10.0f + 3.0f * t * (2.0f * t - 5.0f)); // quintic
																			// spline
		} // for
	} // initSplineTable

	// / Compute a single point of a single octave of Perlin noise. This is
	// similar
	// / to Perlin's noise2 function except that it substitutes table lookups
	// for floating point
	// / multiplication as described in Ian Parberry's paper "Amortized Noise"
	// (google it).
	// / \param i x coordinate of point.
	// / \param j y coordinate of point.
	// / \return Noise value at (x, y).

	float getNoise(int i, int j) {
		float u = uax[j] + uay[i];
		float v = vax[j] + vay[i];
		float a = lerp(spline[j], u, v);
		u = ubx[j] + uby[i];
		v = vbx[j] + vby[i];
		float b = lerp(spline[j], u, v);
		return lerp(spline[i], a, b);
	} // getNoise

	// / Get a single octave of noise into a subcell.
	// / This differs from CAmortized2DNoise::addNoise in that it copies the
	// noise
	// / to the cell instead of adding it in.
	// / \param n Granularity.
	// / \param i0 x offset of this subcell in cell.
	// / \param j0 y offset of this subcell in cell.
	// / \param cell Noise cell.

	void getNoise(int n, int i0, int j0, float[][] cell) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				cell[i0 + i][j0 + j] = getNoise(i, j); // this is the only line
														// that differs from
														// addNoise
	} // getNoise

	// / Add a single octave of noise into a subcell.
	// / This differs from CAmortized2DNoise::getNoise in that it adds the noise
	// / to the cell instead of copying it there.
	// / \param n Granularity.
	// / \param i0 x offset of this subcell in cell.
	// / \param j0 y offset of this subcell in cell.
	// / \param scale Noise is to be rescaled by this factor.
	// / \param cell Noise cell.

	void addNoise(int n, int i0, int j0, float scale, float[][] cell) {
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				cell[i0 + i][j0 + j] += scale * getNoise(i, j); // this is the
																// only line
																// that differs
																// from getNoise
	} // addNoise

	// / Generate a cell of 1/f amortized noise with persistence 0.5 and
	// lacunarity 2.0.
	// / See Ian Parberry's paper "Amortized Noise" (google it).
	// / \param x x coordinate of top left corner of cell.
	// / \param y y coordinate of top left corner of cell.
	// / \param m0 First octave.
	// / \param m1 Last octave.
	// / \param n Granularity.
	// / \param cell Cell to put generated noise into.
	// / \return Multiply noise by this to get into the range -1..1

	float generate(int x, int y, int m0, int m1, int n, float[][] cell) {
		int r = 1; // Side length of cell divided by side length of subcell.

		// Skip over unwanted octaves.
		for (int i = 1; i < m0; i++) {
			n /= 2;
			r += r;
		} // for

		if (n < 2)
			return 1.0f; // fail and bail - should not happen

		// Generate first octave directly into cell.
		// We could add all octaves into the cell directly if we zero out the
		// cell before we begin.
		// However, that is a nontrivial overhead that Perlin noise does not
		// have, and we can avoid
		// it too by putting in the first octave and adding in the rest.

		initSplineTable(n); // initialize the spline table to cells of size n
		for (int i0 = 0; i0 < r; i0++)
			for (int j0 = 0; j0 < r; j0++) { // for each subcell
				initEdgeTables(x + i0, y + j0, n); // initialize the amortized
													// noise tables
				getNoise(n, i0 * n, j0 * n, cell); // generate noise directly
													// into cell
			} // for

		float scale = 1.0f; // scale factor

		// Generate the other octaves and add them into cell. See previous
		// comment.
		for (int k = m0; k < m1 && n >= 2; k++) { // for each octave after the
													// first
			n /= 2;
			r += r;
			x += x;
			y += y;
			scale *= 0.5f; // rescale for next octave
			initSplineTable(n); // initialize the spline table to cells of size
								// n
			for (int i0 = 0; i0 < r; i0++)
				for (int j0 = 0; j0 < r; j0++) { // for each subcell
					initEdgeTables(x + i0, y + j0, n); // initialize the
														// amortized noise
														// tables
					addNoise(n, i0 * n, j0 * n, scale, cell); // generate noise
																// directly into
																// cell
				} // for
		} // for each octave

		// Compute 1/magnitude and return it.
		// A single octave of Perlin noise returns a value of magnitude at most
		// 1/sqrt(2).
		// Adding magnitudes over all scaled octaves gives a total magnitude of
		// (1 + 0.5 + 0.25 +...+ scale)/sqrt(2). This is equal to (2 -
		// scale)/sqrt(2)
		// (using the standard formula for the sum of a geometric progression).
		// 1/magnitude is therefore sqrt(2)/(2-scale).

		return 1.4142f / (2.0f - scale); // multiply this by cell values to
											// bring them to [-1,1]
	} // generate

	public static Color colorConv(int r, int g, int b, float a) {
		return new Color((float) r / 255, (float) g / 255, (float) b / 255, a);
	}

	private int chunkPosToRowCol(int chunkPos) {
		chunkPos *= Chunk.CHUNKSIZE;

		if (chunkPos < 0 && chunkPos % CELLSIZE2D != 0) {
			return chunkPos / CELLSIZE2D - 1;
		}
		return chunkPos / CELLSIZE2D;
	}

	private int chunkPosInCellArray(int chunkPos) {
		chunkPos = chunkPos * Chunk.CHUNKSIZE % CELLSIZE2D;
		if (chunkPos < 0) {
			chunkPos += CELLSIZE2D;
		}
		return chunkPos;
	}

	public void Generate2DNoise(Chunk c) {
		int octave0 = NatureGenerator.octave0;
		int octave1 = NatureGenerator.octave1;
		int nCol = chunkPosToRowCol(c.x);
		int nRow = chunkPosToRowCol(c.y);

		for (int i = 1; i < octave0; i++) {
			nCol = nCol * 2;
			nRow = nRow * 2;
		}

		float[][] cell = new float[CELLSIZE2D][CELLSIZE2D];
		for (int i = 0; i < cell.length; i++) {
			for (int j = 0; j < cell[i].length; j++) {
				cell[i][j] = 0.0f;
			}
		}

		generate(nCol, nRow, octave0, octave1, CELLSIZE2D, cell);
		float seaLevel = NatureGenerator.seaLevel;
		float sandAmount = NatureGenerator.sandAmount;

		int cxStart = chunkPosInCellArray(c.x);
		int cyStart = chunkPosInCellArray(c.y);

		for (int cy = 0; cy < Chunk.CHUNKSIZE; cy++) {
			// Main.log(getClass(), "Chunk Row: " + cy);
			for (int cx = 0; cx < Chunk.CHUNKSIZE; cx++) {
				MapTile t = c.getMapTileFromLocalPos(cx, cy);
				float cellValue = cell[cxStart + cx][cyStart + cy];

				Block b = null;
				if (cellValue < seaLevel) {
					b = new Block(t, MyMaterial.WATER);
				}
				if (cellValue >= seaLevel && cellValue <= seaLevel + sandAmount) {
					b = new Block(t, MyMaterial.SAND);
				}
				if (cellValue > seaLevel + 0.1f && cellValue <= seaLevel + 0.4f) {
					b = new Block(t, MyMaterial.GRASS);
				}
				if (cellValue > seaLevel + 0.4f) {
					b = new Block(t, MyMaterial.STONE);
				}
				t.setBlock(b);
			}
		}
		
		for (int i = 0; i < Chunk.CHUNKSIZE * Chunk.CHUNKSIZE / 400; i++) {
			randomGrass(c);
		}
		
		for (int i = 0; i < Chunk.CHUNKSIZE * Chunk.CHUNKSIZE / 20; i++) {
			randomTree(c);
		}

		// return tiles;
	}

	private void randomGrass(Chunk c) {
		Random rand = NatureGenerator.random;
		int x = rand.nextInt(Chunk.CHUNKSIZE);
		int y = rand.nextInt(Chunk.CHUNKSIZE);
		for (int j = 0; j < 10; j++) {
			int xx = x + rand.nextInt(15) - rand.nextInt(15);
			int yy = y + rand.nextInt(15) - rand.nextInt(15);
			if (xx >= 0 && yy >= 0 && xx < Chunk.CHUNKSIZE && yy < Chunk.CHUNKSIZE) {
				MapTile tile = c.getMapTileFromLocalPos(xx, yy);
				if (tile.b.m.equals(MyMaterial.GRASS)) {
					TallGrass grass = new TallGrass(c.world, tile.getGlobalPosition().addAndSet(0, 0, 0, 0, 1, 0));
					grass.spawn();
				}
			}
		}
	}

	private void randomTree(Chunk c) {
		Random rand = NatureGenerator.random;
		int x = rand.nextInt(Chunk.CHUNKSIZE);
		int y = rand.nextInt(Chunk.CHUNKSIZE);
		for (int j = 0; j < 10; j++) {
			int xx = x + rand.nextInt(15) - rand.nextInt(15);
			int yy = y + rand.nextInt(15) - rand.nextInt(15);
			if (xx >= 0 && yy >= 0 && xx < Chunk.CHUNKSIZE && yy < Chunk.CHUNKSIZE) {
				MapTile tile = c.getMapTileFromLocalPos(xx, yy);
				if (tile.b.m.equals(MyMaterial.GRASS)) {
					Tree tree = new Tree(c.world, tile.getGlobalPosition().addAndSet(0, 0, 0, 0, 1, 0));
					tree.spawn();
				}
			}
		}
	}

	public void dispose() {
		uax = null;
		vax = null;
		ubx = null;
		vbx = null;
		uay = null;
		vay = null;
		uby = null;
		vby = null; // /< Amortized noise
		spline = null;
		workspace = null;
	}

}
