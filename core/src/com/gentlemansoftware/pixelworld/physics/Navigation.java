package com.gentlemansoftware.pixelworld.physics;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Navigation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3738207424213463129L;
	private List<Position> path;
	
	public Navigation(){
		path = new LinkedList<Position>();
	}

	public Navigation(List<Position> path) {
		this.path = path;
	}

	public Navigation(final Position path) {
		this(new LinkedList<Position>() {
			{
				add(path);
			}
		});
	}

	public void arrivedAtDestiny() {
		if (!this.path.isEmpty()) {
			this.path.remove(0);
		}
	}

	public boolean hasFinished() {
		return path.isEmpty();
	}

	public Position getActualDestiny() {
		if (hasFinished()) {
			return null;
		}
		return path.get(0);
	}

	public void addDestiny(Position destiny) {
		path.add(destiny);
	}

	public void addDestiny(Position destiny, int position) {
		path.add(position, destiny);
	}

	public void setPath(List<Position> newPath) {
		if (newPath == null) {
			newPath = new LinkedList<Position>();
		}
		this.path = newPath;
	}

	public void setPath(Position singleTarget) {
		List<Position> newPath = new LinkedList<Position>();
		newPath.add(singleTarget);
		setPath(newPath);
	}

	public void setSecondDestiny(Position pos) {
		if (hasFinished()) {
			addDestiny(pos);
		} else {
			Position first = this.path.get(0);
			if(pos.equals(first)) return;
			removeAllDestinys();
			addDestiny(first);
			addDestiny(pos);
		}
	}

	public void removeDestiny(int position) {
		this.path.remove(position);
	}

	public void removeAllDestinys() {
		this.path = new LinkedList<Position>();
	}

	public void removeDestinys(List<Position> path) {
		this.path.removeAll(path);
	}

	public void removeAllDestiny(Position pos) {
		while (this.path.remove(pos)) {
			// the pos was removed
		}
	}

	public void removeFirstDestiny(Position pos) {
		this.path.remove(pos);
	}

}
