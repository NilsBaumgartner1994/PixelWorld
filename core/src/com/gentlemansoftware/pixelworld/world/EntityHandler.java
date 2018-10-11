package com.gentlemansoftware.pixelworld.world;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.gentlemansoftware.pixelworld.entitys.Entity;

public class EntityHandler implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3668982728322575248L;
	/**
	 * @return Returns a new UUID for the entity. This UUID is universally
	 *         unique so could potentially also be used for serialisation.
	 */

	private Map<String, Entity> entitymap;

	public EntityHandler() {
		initEntityMap();
	}
	
	public List<Entity> getAllEntitys(){
		return new ArrayList<Entity>(entitymap.values());
	}

	public Entity getEntity(String uuid) {
		return entitymap.get(uuid);
	}

	private void initEntityMap() {
		entitymap = new HashMap<String, Entity>();
	}

	public void registerEntity(Entity e) {
		String uuid = e.getUUID();
		uuid = uuid == null ? createNewUUID() : uuid;
		e.setUUID(uuid);
		entitymap.put(uuid, e);
	}

	public void unregisterEntity(Entity e) {
		entitymap.remove(e.getUUID());
	}

	public String createNewUUID() {
		UUID newID = UUID.randomUUID();
		return newID.toString();
	}
}
