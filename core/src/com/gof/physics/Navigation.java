package com.gof.physics;

import java.util.ArrayList;
import java.util.List;

public class Navigation {
	
	List<Position> path;
	
	public Navigation(){
		this.path = new ArrayList<Position>();
	}
	
	public Navigation(List<Position> path){
		this.path = path;
	}
	
	public Position nextDestiny(){
		this.path.remove(0);
		return getActualDestiny();
	}
	
	public boolean hasFinished(){
		return path.isEmpty();
	}
	
	public Position getActualDestiny(){
		return path.get(0);
	}
	
	public void addDestiny(Position destiny, int position){
		path.add(position, destiny);
	}
	
	public void setPath(List<Position> newPath){
		this.path = newPath;
	}
	
	public void removeDestiny(int position){
		this.path.remove(position);
	}
	
	public void removeDestinys(List<Position> path){
		this.path.removeAll(path);
	}
	
	public void removeAllDestiny(Position pos){
		while(this.path.remove(pos)){
			//the pos was removed
		}
	}
	
	public void removeFirstDestiny(Position pos){
		this.path.remove(pos);
	}

}
