package com.gentlemansoftware.pixelworld.helper;

import java.util.LinkedList;
import java.util.List;

public class SplitscreenHelper {
	
	public static boolean startWithHorizontalSplit = false;

	public static List<Rectangle> getDimensionsForAmountOfPlayers(int amount, int totalWidth,
			int totalHeight) {
		List<Rectangle> dimensions = new LinkedList<Rectangle>();
		Rectangle total = new Rectangle(0, 0, totalWidth, totalHeight);

		if (amount > 1) {
			List<Rectangle> nextToSplit = new LinkedList<Rectangle>();
			nextToSplit.add(total);

			boolean horizontal = startWithHorizontalSplit;
			Rectangle firstOneSplittet = null;
			for (int i = 1; i < amount; i++) {
				Rectangle toSplit = nextToSplit.get(0);
				nextToSplit.remove(0);
				List<Rectangle> splittet = splitDimenstion(toSplit, horizontal);
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

	private static List<Rectangle> splitDimenstion(Rectangle screen, boolean horizontal) {
		List<Rectangle> dimensions = new LinkedList<Rectangle>();

		if (horizontal) {
			Rectangle upper = new Rectangle(screen.x, screen.y, screen.width, screen.height / 2);
			Rectangle lower = new Rectangle(screen.x, screen.y + screen.height / 2, screen.width,
					screen.height / 2);
			dimensions.add(upper);
			dimensions.add(lower);
		} else {
			Rectangle left = new Rectangle(screen.x, screen.y, screen.width / 2, screen.height);
			Rectangle right = new Rectangle(screen.x + screen.width / 2, screen.y,
					screen.width / 2, screen.height);
			dimensions.add(left);
			dimensions.add(right);
		}

		return dimensions;
	}

}
