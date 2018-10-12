package com.gentlemansoftware.pixelworld.entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;

public interface EasyDrawableInterface {

	public Sprite getSprite(Direction cameraDirection);
	public Position getPosition();

}
