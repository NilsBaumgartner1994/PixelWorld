package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.graphics.Pixmap;
import com.gentlemansoftware.pixelworld.physics.Direction;

public class PixmapHelper {

	public static Pixmap rotate(Pixmap pixmap, Direction dir) {
		int width = pixmap.getWidth();
		int height = pixmap.getHeight();
		
		if(dir == Direction.SOUTH){
			Pixmap rotated = new Pixmap(width,height,pixmap.getFormat());
			for(int x=0;x<width;x++){
				for(int y=0;y<width;y++){
					rotated.drawPixel(width-x-1, height-y-1, pixmap.getPixel(x, y));
				}
			}
			return rotated;
		}
		if(dir == Direction.EAST){
			Pixmap rotated = new Pixmap(height,width,pixmap.getFormat());
			for(int x=0;x<width;x++){
				for(int y=0;y<width;y++){
					rotated.drawPixel(height-y-1,width-x-1, pixmap.getPixel(x, y));
				}
			}
			return rotated;
		}
		if(dir == Direction.WEST){
			Pixmap rotated = new Pixmap(height,width,pixmap.getFormat());
			for(int x=0;x<width;x++){
				for(int y=0;y<width;y++){
					rotated.drawPixel(y, x, pixmap.getPixel(x, y));
				}
			}
			return rotated;
		}
		
		return pixmap;
	}

}
