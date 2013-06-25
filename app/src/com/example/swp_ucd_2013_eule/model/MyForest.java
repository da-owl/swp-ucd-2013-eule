package com.example.swp_ucd_2013_eule.model;

import com.example.swp_ucd_2013_eule.data.ForestItem;

public class MyForest {

	private static MyForest INSTANCE = new MyForest();
	private Forest mForest;
	private OnItemBoughtListener mListener;

	private MyForest() {
		mForest = new Forest();
		mForest.setLevel(17);
		mForest.setPoints(80);
		mForest.setLevelProgessPoints(89);
		mForest.setPointProgress(90f);
		mForest.setUserforestitems(UserForestItem.getExamples());
	}

	public static MyForest getInstance() {
		return INSTANCE;
	}

	public Forest getForest() {
		return mForest;
	}

	public void buyItem(ForestItem item) {

		item.incAmount();

		int points = mForest.getPoints() - item.getPrice();
		mForest.setPoints(points);
		UserForestItem uItem = new UserForestItem(item);
		uItem.setTile(-1, -1);
		uItem.setOffset(0.5f, 0.5f);

		mForest.addItem(uItem);
		if (mListener != null) {
			mListener.onNewItemBought(uItem);
		}

	}

	public void addOnItemBoughtListener(OnItemBoughtListener listener) {
		mListener = listener;
	}

}
