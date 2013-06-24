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

	public void addBoughtItem(UserForestItem item) {
		ForestItem itemBought = item.getForestItem();
		for(UserForestItem uItem : mForest.getUserforestitems()){
			ForestItem itemInList = uItem.getForestItem();
			if(itemInList.getName().equals(itemBought.getName())){
				itemInList.setAmount(itemBought.getAmount());
			}
		}
		mForest.addItem(item);
		if(mListener!=null){
			mListener.onNewItemBought(item);
		}
		
	}
	
	public void addOnItemBoughtListener(OnItemBoughtListener listener){
		mListener = listener;
	}
	

}
