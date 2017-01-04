package com.gof.helper;

public class ArrayHelper {

	public static boolean[][] rotateCounter(boolean[][] arr) {
		/* W and H are already swapped */
		int w = arr.length;
		int h = arr[0].length;
		boolean[][] ret = new boolean[h][w];
		for (int i = 0; i < h; ++i) {
			for (int j = 0; j < w; ++j) {
				ret[i][j] = arr[j][h - i - 1];
			}
		}
		return ret;
	}

}
