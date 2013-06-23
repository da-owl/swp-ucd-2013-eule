package com.example.swp_ucd_2013_eule.model;

public class MyForest {

	private static MyForest mInstance = null;
	private Forest mForest;
	
	private MyForest(){
		mForest = new Forest();
		mForest.setLevel(17);
		mForest.setPoints(80);
	}
	
	public static MyForest getInstance(){
		if(mInstance==null){
			mInstance = new MyForest();
		}
		return mInstance;
	}
	
	public Forest getForest(){
		return mForest;
	}
	
}
