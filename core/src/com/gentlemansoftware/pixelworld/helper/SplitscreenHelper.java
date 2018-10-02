package com.gentlemansoftware.pixelworld.helper;

import java.util.LinkedList;
import java.util.List;

public class SplitscreenHelper {
	
	public static boolean startWithHorizontalSplit = false;

	public static List<SplitScreenDimension> getDimensionsForAmountOfPlayers(int amount, int totalWidth,
			int totalHeight) {
		List<SplitScreenDimension> dimensions = new LinkedList<SplitScreenDimension>();
		SplitScreenDimension total = new SplitScreenDimension(0, 0, totalWidth, totalHeight);

		if (amount > 1) {
			List<SplitScreenDimension> nextToSplit = new LinkedList<SplitScreenDimension>();
			nextToSplit.add(total);

			boolean horizontal = startWithHorizontalSplit;
			SplitScreenDimension firstOneSplittet = null;
			for (int i = 1; i < amount; i++) {
				SplitScreenDimension toSplit = nextToSplit.get(0);
				nextToSplit.remove(0);
				List<SplitScreenDimension> splittet = splitDimenstion(toSplit, horizontal);
				if (firstOneSplittet == null || firstOneSplittet == splittet.get(0)) { // alternate
																						// Splitting
					horizontal = !horizontal;
					firstOneSplittet = splittet.get(0);
				}
				nextToSplit.addAll(splittet);
			}
			
			dimensions.addAll(nextToSplit);
		} else {
			dimensions.add(total);
		}

		return dimensions;
	}

	private static List<SplitScreenDimension> splitDimenstion(SplitScreenDimension screen, boolean horizontal) {
		List<SplitScreenDimension> dimensions = new LinkedList<SplitScreenDimension>();

		if (horizontal) {
			SplitScreenDimension upper = new SplitScreenDimension(screen.x, screen.y, screen.width, screen.height / 2);
			SplitScreenDimension lower = new SplitScreenDimension(screen.x, screen.y + screen.height / 2, screen.width,
					screen.height / 2);
			dimensions.add(upper);
			dimensions.add(lower);
		} else {
			SplitScreenDimension left = new SplitScreenDimension(screen.x, screen.y, screen.width / 2, screen.height);
			SplitScreenDimension right = new SplitScreenDimension(screen.x + screen.width / 2, screen.y,
					screen.width / 2, screen.height);
			dimensions.add(left);
			dimensions.add(right);
		}

		return dimensions;
	}

}
