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

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.FileController;
import com.gentlemansoftware.pixelworld.helper.EasyColor;
import com.gentlemansoftware.pixelworld.materials.MyMaterial;
import com.gentlemansoftware.pixelworld.physics.Position;

public class WorldToPNG {

	private Pixmap _pixmap;
	private int right;
	private int up;
	private int left;
	private int down;
	private MapTile tile;

	private static Map<MyMaterial, Color> mapping = new HashMap<MyMaterial, Color>();

	static {
		mapping.put(MyMaterial.DEBUG, Color.PINK);
		mapping.put(MyMaterial.DIRT, Color.BROWN);
		mapping.put(MyMaterial.ERROR, Color.RED);
		mapping.put(null, EasyColor.TRANSPARENT);
		mapping.put(MyMaterial.GRASS, Color.GREEN);
		mapping.put(MyMaterial.SAND, Color.YELLOW);
		mapping.put(MyMaterial.STONE, Color.GRAY);
		mapping.put(MyMaterial.WATER, Color.BLUE);
	}

	public WorldToPNG(final Chunk c) {
		this(c.getMapTileFromLocalPos(0, 0), 0, 0, Chunk.CHUNKSIZE, Chunk.CHUNKSIZE);
	}

	public WorldToPNG(MapTile tile, int left, int down, int up, int right) {
		this.tile = tile;
		this.left = left;
		this.down = down;
		this.up = up;
		this.right = right;
		initVariables();
	}

	public static Pixmap getPixmap(Chunk c) {
		WorldToPNG toPNG = new WorldToPNG(c);
		toPNG.renderPixmap();
		return toPNG._pixmap;
	}

	public static Pixmap getPixmap(MapTile tile) {
		WorldToPNG toPNG = new WorldToPNG(tile, -Chunk.CHUNKSIZE / 2, -Chunk.CHUNKSIZE / 2, Chunk.CHUNKSIZE / 2,
				Chunk.CHUNKSIZE / 2);
		toPNG.renderPixmap();
		return toPNG._pixmap;
	}

	private void initVariables() {
		_pixmap = new Pixmap(right - left, up - down, Format.RGBA8888);
	}

	public static void saveToImage(TileWorld world) {
		System.out.println("Start Test");

		System.out.println("Start Saving");
		List<WorldToPNG> parts = getArea(world, 49, 2, 48, 2);
		System.out.println("get Pixmaps");
		List<Pixmap> pixmaps = getPixmaps(parts);

		System.out.println("Merge");
		Pixmap merged = merge4PixmapTogether(pixmaps);
		System.out.println("Save Merge");
		savePixmap(merged, "merge");
	}

	public static List<WorldToPNG> getArea(TileWorld world, int xs, int xa, int ys, int ya) {
		List<WorldToPNG> parts = new LinkedList<WorldToPNG>();
		for (int x = xs; x < xs + xa; x++) {
			for (int y = ys; y < ys + ya; y++) {
				WorldToPNG toPNG = new WorldToPNG(world.getChunk(x, y));
				toPNG.renderPixmap();
				parts.add(toPNG);
			}
		}
		return parts;
	}

	public static List<Pixmap> getPixmaps(List<WorldToPNG> parts) {
		List<Pixmap> pixmaps = new LinkedList<Pixmap>();
		for (WorldToPNG part : parts) {
			pixmaps.add(part._pixmap);
		}
		return pixmaps;
	}

	private static Pixmap glue4PixmapsTogether(List<Pixmap> pixmaps) {
		if (!allPixmapsSameSize(pixmaps) || pixmaps.size() != 4) {
			return null;
		}

		int width = pixmaps.get(0).getWidth();
		int height = pixmaps.get(0).getHeight();

		Pixmap result = new Pixmap(width * 2, height * 2, Format.RGBA8888);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				result.drawPixel(x, y, pixmaps.get(0).getPixel(x, y));
				result.drawPixel(x, y + height, pixmaps.get(1).getPixel(x, y));
				result.drawPixel(x + width, y, pixmaps.get(2).getPixel(x, y));
				result.drawPixel(x + width, y + height, pixmaps.get(3).getPixel(x, y));
			}
		}

		return result;
	}

	private static Pixmap merge4PixmapTogether(List<Pixmap> pixmaps) {
		if (!allPixmapsSameSize(pixmaps) || pixmaps.size() != 4) {
			return null;
		}

		int width = pixmaps.get(0).getWidth();
		int height = pixmaps.get(0).getHeight();

		Pixmap result = new Pixmap(width, height, Format.RGBA8888);

		for (int x = 0; x < width; x += 2) {
			for (int y = 0; y < height; y += 2) {
				result.drawPixel(x / 2, y / 2, pixmaps.get(0).getPixel(x, y));
				result.drawPixel(x / 2, y / 2 + height / 2, pixmaps.get(1).getPixel(x, y));
				result.drawPixel(x / 2 + width / 2, y / 2, pixmaps.get(2).getPixel(x, y));
				result.drawPixel(x / 2 + width / 2, y / 2 + height / 2, pixmaps.get(3).getPixel(x, y));
			}
		}

		return result;
	}

	private static boolean allPixmapsSameSize(List<Pixmap> pixmaps) {
		int width = -1;
		int height = -1;

		for (int i = 0; i < pixmaps.size(); i++) {
			Pixmap map = pixmaps.get(i);
			if (map == null) {
				return false;
			}
			if (i != 0) {
				if (width != map.getWidth() || height != map.getHeight()) {
					return false;
				}
			}
			width = map.getWidth();
			height = map.getHeight();
		}
		return true;
	}

	private static void saveChunkToImage(TileWorld world, int x, int y) {
		WorldToPNG toPNG = new WorldToPNG(world.getChunk(x, y));
		System.out.println("Sett up");
		toPNG.saveToImage();
	}

	public void saveToImage() {
		System.out.println("Render Pixmap");
		renderPixmap();
		System.out.println("Save Pixmap");
		savePixmap(_pixmap, tile.chunk.x + "-" + tile.chunk.y);
		dispose();
	}

	public static void savePixmap(Pixmap _pixmap, String name) {
		try {
			FileHandle fh;
			fh = new FileHandle(name + ".png");
			fh.parent().mkdirs();
			PixmapIO.writePNG(fh, _pixmap);
		} catch (Exception e) {
		}
	}

	private Pixmap renderPixmap() {
		int width = _pixmap.getWidth();
		int height = _pixmap.getHeight();

		_pixmap.setColor(Color.alpha(1));
		_pixmap.fillRectangle(0, 0, width, height);

		int gx = tile.getGlobalX();
		int gy = tile.getGlobalY();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (tile != null && tile.chunk != null && tile.chunk.world != null) {
					MapTile t = tile.chunk.world.getMapTileFromGlobalPos(left + x + gx, down + y + gy);
					if (t != null) {
						Color color = mapping.get(t.block.material);
						if(color==null){
							color = EasyColor.PLAYSTATION_TRIANGLE;
						}
						_pixmap.setColor(color);
						_pixmap.drawPixel(x, height - y - 1);

						List<Entity> entitys = new LinkedList<>(t.entitys);

						for (Entity entity : entitys) {
							if (!(entity instanceof Block)) {
								_pixmap.setColor(Color.RED);
								_pixmap.drawPixel(x, height - y - 1);
							}
						}
					}
				}
			}
		}

		return _pixmap;
	}

	public void dispose() {
		_pixmap.dispose();
	}

}
