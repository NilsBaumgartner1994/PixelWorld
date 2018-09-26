package com.gentlemansoftware.pixelworld.helper;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.files.FileHandle;

public class Snippet {

	public static FileHandle[] sortFilesByTimeModified(FileHandle[] files) {
		Arrays.sort(files, new Comparator<FileHandle>() {
			public int compare(FileHandle f1, FileHandle f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}
		});
		
		return files;
	}
}
