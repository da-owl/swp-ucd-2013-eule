package com.example.swp_ucd_2013_eule.data;

public class SettingsWrapper {
	
	private Integer[] levels;
	
	private final int levelCount = 10;
	
	public SettingsWrapper() {
		levels = new Integer[levelCount];
		for(int i = 0; i <= levelCount; i++){
			levels[i] = i * 100;
		}		
	}
	
	public int getPointsToNextLevel(int level) {
		if(level <= levelCount){
			return levels[level];
		}
		return 0;
	}

}
