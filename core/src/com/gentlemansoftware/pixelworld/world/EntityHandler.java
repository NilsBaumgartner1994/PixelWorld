package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.gentlemansoftware.pixelworld.entitys.Entity;
import com.gentlemansoftware.pixelworld.game.Main;

public class EntityHandler implements Serializable {
	/**
	 * @return Returns a new UUID for the entity. This UUID is universally
	 *         unique so could potentially also be used for serialisation.
	 */

	private Map<UUID, Entity> entitymap;

	public EntityHandler() {
		initEntityMap();
	}

	public Entity getEntity(UUID uuid) {
		return entitymap.get(uuid);
	}

	private void initEntityMap() {
		entitymap = new HashMap<UUID, Entity>();
	}

	public void registerEntity(Entity e) {
		UUID uuid = e.getUUID();
		uuid = uuid == null ? createNewUUID() : uuid;
		e.setUUID(uuid);
		entitymap.put(uuid, e);
	}

	public void unregisterEntity(Entity e) {
		entitymap.remove(e.getUUID());
	}

	public UUID createNewUUID() {
		UUID newID = UUID.randomUUID();
		return newID;
	}
}
