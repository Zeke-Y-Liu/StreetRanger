package com.ranger.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

	// try to deduce the app base dir, in case, not able to, return "", current dir
	public static String getBaseDir() { 
		Path currentRelativePath = Paths.get("");
		String currentDir = currentRelativePath.toAbsolutePath().toString();
		if(currentDir.contains("collectorApp")) {
			return currentDir.substring(0,currentDir.lastIndexOf("collectorApp")) + "collectorApp/";
		} else {
			return "";
		}
	}
	
}
