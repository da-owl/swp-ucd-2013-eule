package com.example.swp_ucd_2013_eule.model;

public class MyForest {

	private static MyForest INSTANCE = new MyForest();
	private Forest mForest;

	private MyForest() {
		mForest = new Forest();
		mForest.setLevel(17);
		mForest.setPoints(80);
	}

	public static MyForest getInstance() {
		return INSTANCE;
	}

	public Forest getForest() {
		return mForest;
	}

}
