package com.gentlemansoftware.pixelworld.entitys;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.game.ResourceLoader;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class Tree extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5731904582913542561L;

	public Tree(TileWorld world, Position startPos) {
		super(world, startPos, EntityHostileType.FRIENDLY);
	}

	@Override
	public Sprite getSprite(Direction camdir) {
		return new Sprite(ResourceLoader.getInstance().getNature("tree"));
	}


}
