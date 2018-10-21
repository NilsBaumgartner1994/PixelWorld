package com.gentlemansoftware.pixelworld.helper;

import com.badlogic.gdx.Gdx;

public class MemoryHelper {

	public static long maxMemory = 0;
	public static long minMemory = 0;
	public static long avgMemory = 0;

	private static long[] frameMemory = new long[600];
	private static int pos = 0;
	private static long frameTotalMemorys = 0;

	public static void calcMemory() {
		long mem = Gdx.app.getJavaHeap() + Gdx.app.getNativeHeap();
		long old = frameMemory[pos];
		frameMemory[pos] = mem;
		pos = (pos + 1) % frameMemory.length;
		frameTotalMemorys = frameTotalMemorys - old + mem;
		avgMemory = frameTotalMemorys/frameMemory.length;
		minMemory = Long.MAX_VALUE;
		maxMemory = Long.MIN_VALUE;
		for(long l : frameMemory){
			minMemory = l<minMemory ? l : minMemory;
			maxMemory = l>maxMemory ? l : maxMemory;
		}
	}

	public static String getMaxMemory() {
		return humanReadableByteCount(maxMemory, true);
	}

	public static String getMinMemory() {
		return humanReadableByteCount(minMemory, true);
	}

	public static String getAvgMemory() {
		return humanReadableByteCount(avgMemory, true);
	}

	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
