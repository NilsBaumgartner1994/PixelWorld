package com.gentlemansoftware.pixelworld.searchstrategies;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.gentlemansoftware.pixelworld.game.Main;
import com.gentlemansoftware.pixelworld.physics.Position;
import com.gentlemansoftware.pixelworld.world.MapTile;

public class SearchStrategie {

	public static List<MapTile> getShortestPath(MapTile from, MapTile to) {
		List<SearchNode> erreichbar = new LinkedList<SearchNode>();
		List<MapTile> besterWeg = new LinkedList<MapTile>();
		List<SearchNode> erkundet = new LinkedList<SearchNode>();
		boolean wayFound = false;

		SearchNode start = new SearchNode(from, null, 0);
		erreichbar.add(start);

		while (!erreichbar.isEmpty() && !wayFound) {
			SearchNode expand = chooseBest(erreichbar, to);
			erreichbar.remove(expand);
			erkundet.add(expand);

			// Ziel erreicht
			if (expand.at.equals(to)) {
				wayFound = true;
				while (expand != null) {
					besterWeg.add(expand.at);
					expand = expand.previous;
				}
				Collections.reverse(besterWeg);
				return besterWeg;
			}

			SearchNode left = new SearchNode(expand.at.getMapTileByOffset(-1, 0), expand, expand.cost + 1);
			SearchNode right = new SearchNode(expand.at.getMapTileByOffset(1, 0), expand, expand.cost + 1);
			SearchNode up = new SearchNode(expand.at.getMapTileByOffset(0, 1), expand, expand.cost + 1);
			SearchNode down = new SearchNode(expand.at.getMapTileByOffset(0, -1), expand, expand.cost + 1);

			checkIfExploreableOrExplored(erkundet, erreichbar, left, expand, to);
			checkIfExploreableOrExplored(erkundet, erreichbar, right, expand, to);
			checkIfExploreableOrExplored(erkundet, erreichbar, up, expand, to);
			checkIfExploreableOrExplored(erkundet, erreichbar, down, expand, to);
		}
		return null;
	}

	public static void checkIfExploreableOrExplored(List<SearchNode> erkundet, List<SearchNode> erreichbar,
			SearchNode node, SearchNode expanded, MapTile goal) {
		
		if(!erkundet.contains(node)){
			if(node.at.equals(goal)){
				if(!erreichbar.contains(node)){
					erreichbar.add(node);
				}
			} else {
				if(!node.at.isSolid()){
					if(!erreichbar.contains(node)){
						erreichbar.add(node);
					}
				}
			}
		}

		if (expanded.cost + 1 < node.cost) {
			node.previous = expanded;
			node.cost = expanded.cost + 1;
		}
	}

	public static SearchNode chooseBest(List<SearchNode> nodes, MapTile to) {
		SearchNode best = null;
		int min_cost = Integer.MAX_VALUE;

		for (SearchNode node : nodes) {
			int cost_start_to_node = node.cost;
			int cost_node_to_goal = estimate_distance(node.at, to);
			int total_cost = cost_start_to_node + cost_node_to_goal;

			if (min_cost > total_cost) {
				min_cost = total_cost;
				best = node;
			}

		}

		return best;
	}

	public static int estimate_distance(MapTile from, MapTile to) {
		Position diff = from.getGlobalPosition().subAndSet(to.getGlobalPosition());
		return Math.abs(diff.x) + Math.abs(diff.y);
	}

}
