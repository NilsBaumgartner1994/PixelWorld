package com.gentlemansoftware.pixelworld.helper;

public class ArrayHelper {

	public static int[] stringArrToIntArr(String[] arr) {
		int[] intarr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intarr[i] = Integer.parseInt(arr[i]);
		}

		return intarr;
	}

}
