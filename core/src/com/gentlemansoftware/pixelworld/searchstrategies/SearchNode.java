package com.gentlemansoftware.pixelworld.searchstrategies;

import com.gentlemansoftware.pixelworld.world.MapTile;

public class SearchNode{

	MapTile at;
	SearchNode previous;
	int cost;

	public SearchNode(MapTile at, SearchNode previous, int cost) {
		this.at = at;
		this.previous = previous;
		this.cost = cost;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SearchNode) {
			SearchNode on = (SearchNode) o;
			return at.equals(on.at);
		}
		return false;
	}

}
