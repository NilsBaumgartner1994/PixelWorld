package com.gentlemansoftware.pixelworld.entitys;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gentlemansoftware.pixelworld.physics.Direction;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.physics.Speed;
import com.gentlemansoftware.pixelworld.physics.WorldTime;
import com.gentlemansoftware.pixelworld.profiles.User;
import com.gentlemansoftware.pixelworld.sound.EasySounds;
import com.gentlemansoftware.pixelworld.world.MapTile;
import com.gentlemansoftware.pixelworld.world.TileWorld;

public class Bat extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6290502169526685385L;

	public Bat() {

	}

	public Bat(TileWorld world, Position globalPos) {
		super(world, globalPos, EntityHostileType.ANIMAL);
		this.resetInputVariables();
		setNextRandomGoal();
	}

	@Override
	public void updateLogic() {
		if (this.nav.hasFinished()) { // find next random Position to move
			setNextRandomGoal();
		}
	}

	private void setNextRandomGoal() {
		MapTile nextGoal = getNextGoal();
		while (nextGoal.isSolid()) {
			nextGoal = getNextGoal();
		}

		this.nav.setPath(nextGoal.getGlobalPosition().addAndSet(0, 0, 0, 0, 1, 0));
	}

	private MapTile getNextGoal() {
		MapTile tile = this.getMapTile();
		Random r = new Random();
		int min = 3;
		int max = 10;
		int distance = min + r.nextInt(max + 1);

		boolean inXDir = r.nextBoolean();
		int xDir = inXDir ? 1 : 0;
		int yDir = inXDir ? 0 : 1;
		int positive = r.nextBoolean() ? 1 : -1;

		int xAdd = xDir * positive * distance;
		int yAdd = yDir * positive * distance;

		return this.world.getMapTileFromGlobalPos(tile.getGlobalX() + xAdd, tile.getGlobalY() + yAdd);
	}

	public void resetInputVariables() {
		this.speed = Speed.sneak;
	}

	int lastTick = 0;
	int lastDay = 0;
	int maxDistance = 10;

	@Override
	public void playSoundForUser(Position playerPos, User user) {
		Position distancePos = this.getPosition().distance(playerPos);
		float distance = distancePos.length();

		if (this.world != null) {
			if (lastDay < this.world.time.getDays()) {
				lastDay = this.world.time.getDays();
				lastTick -= WorldTime.MAXTICKS;
			}
			if (lastTick
					+ this.world.time.getTicksPerSecond()
							* (BatSpriteAnimations.sittingAnimationTime - 0.1f) < this.world.time.getTicks()
					&& BatSpriteAnimations.getAnimationTime(this.world.time) >= 0.75f) {
				if (distance <= maxDistance) {
					float volume = 1f - (distance / maxDistance);
					user.soundManager.playSound(EasySounds.BAT_WING_FLAP, volume);
					lastTick = this.world.time.getTicks();
				}
			}
		}
	}

	@Override
	public Sprite getSprite(Direction camdir) {
		if(this.world==null){
			System.out.println("World is null...");
		}
		
		return new Sprite(BatSpriteAnimations.getTexture(getMotionState(), this.world.time));
	}

}
