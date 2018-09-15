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

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.gof.materials.MyMaterial;

public class WorldToPNG {

	private Pixmap _pixmap;
	private int width;
	private int height;
	private Chunk chunk;

	private static Map<MyMaterial, Color> mapping = new HashMap<MyMaterial, Color>();

	static {
		mapping.put(MyMaterial.DEBUG, Color.PINK);
		mapping.put(MyMaterial.DIRT, Color.BROWN);
		mapping.put(MyMaterial.ERROR, Color.RED);
		mapping.put(MyMaterial.GRASS, Color.GREEN);
		mapping.put(MyMaterial.SAND, Color.YELLOW);
		mapping.put(MyMaterial.STONE, Color.GRAY);
		mapping.put(MyMaterial.WATER, Color.BLUE);
	}

	public WorldToPNG(final Chunk c) {
		this.chunk = c;
		initVariables();
	}

	private void initVariables() {
		this.width = this.height = Chunk.CHUNKSIZE;
		_pixmap = new Pixmap(this.width, this.height, Format.RGBA8888);
	}

	public static void saveToImage(TileWorld world) {
		WorldToPNG toPNG = new WorldToPNG(world.getChunk(50, 49));
		toPNG.saveToImage();
	}

	public void saveToImage() {
		renderPixmap();
		savePixmap();
		dispose();
	}

	public void savePixmap() {
		try {
			FileHandle fh;
			do {
				fh = new FileHandle("dsfefs.png");
			} while (fh.exists());
			PixmapIO.writePNG(fh, _pixmap);
		} catch (Exception e) {
		}
	}

	private Pixmap renderPixmap() {
		_pixmap.setColor(Color.alpha(1));
		_pixmap.fillRectangle(0, 0, width, height);

		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				Color color = mapping.get(this.chunk.getMapTileFromLocalPos(x, y).material);
				_pixmap.setColor(color);
				_pixmap.drawPixel(x, y);
			}
		}

		return _pixmap;
	}

	public void dispose() {
		_pixmap.dispose();
	}

}
