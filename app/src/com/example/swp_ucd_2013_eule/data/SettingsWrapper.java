package com.example.swp_ucd_2013_eule.data;

public class SettingsWrapper {
	
	private static SettingsWrapper INSTANCE = new SettingsWrapper();
	private Integer[] levels;
	
	private final int levelCount = 101;
	
	private SettingsWrapper() {
		levels = new Integer[levelCount];
		for(int i = 1; i < levelCount; i++){
			levels[i] = i * 5;
		}		
	}
	
	public static SettingsWrapper getInstance(){
		return INSTANCE;
	}
	
	public int getPointsToNextLevel(int level) {
		if(level <= levelCount){
			return levels[level];
		}
		return 0;
	}

}
