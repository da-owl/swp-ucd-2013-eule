package com.example.swp_ucd_2013_eule.model;

import java.util.List;

import android.util.Log;
import com.example.swp_ucd_2013_eule.net.APIException;

public class MyForest {

	private static MyForest INSTANCE = new MyForest();
	private Forest mForest;
	private OnItemBoughtListener mListener;
	
	public final static Integer FOREST_ID = 1;
	
	private APIModel<Forest, Forest> mForestAPI;
	private APIModel<UserForestItem, Forest> mUserItemAPI;
	private APIModel<Statistic, Forest> mStatAPI;

	private MyForest() {
		mForestAPI = new APIModel<Forest, Forest>(Forest.class);
		mUserItemAPI = new APIModel<UserForestItem, Forest>(UserForestItem.class);
		mStatAPI = new APIModel<Statistic, Forest>(Statistic.class);
		
		try {
			mForest = new Forest(1);
			
			
			Log.d("MyForest", mForest.toString());
			mForest = mForestAPI.get(mForest);
			
			List<UserForestItem> items = mUserItemAPI.getAllByParent(mForest, new UserForestItem(), "userforestitems");
			List<Statistic> stats = mStatAPI.getAllByParent(mForest, new Statistic(), "statistics");
			
			mForest.setUserforestitems(items);
			mForest.setStatistics(stats);
		} catch (APIException e) {
			Log.e("MyForest", "Could not retrieve Forest!");
		}
		
		
//		mForest.setLevel(17);
//		mForest.setPoints(80);
//		mForest.setLevelProgessPoints(89);
//		mForest.setPointProgress(90f);
	}

	public static MyForest getInstance() {
		return INSTANCE;
	}

	public Forest getForest() {
		return mForest;
	}

	public boolean isObtainable(Item item) {
		if (item.getPrice() < mForest.getPoints()
				& item.getLevel() < mForest.getLevel()) {
			return true;
		}
		return false;
	}

	public boolean buyItem(Item item) {
		try {
			item.incAmount();

			int points = mForest.getPoints() - item.getPrice();
			mForest.setPoints(points);
			UserForestItem uItem = new UserForestItem(item, MyMarket.getInstance().getItems());
			uItem.setTile(-1, -1);
			uItem.setOffset(0.5f, 0.5f);
			
			mForest.addItem(uItem);
			
			mUserItemAPI.save(uItem);
			mUserItemAPI.addToParent(uItem, mForest, "userforestitems");
			
			if (mListener != null) {
				mListener.onNewItemBought(uItem);
			}
		
			return true;
		} catch (APIException e) {
			return false;
		}
	}
	
	public boolean addBoughtItem(UserForestItem userItem) {
		// TODO: dublicate code here?
//		try {
//			userItem.
//			int points = mForest.getPoints() - item.getPrice();
//			mForest.setPoints(points);
//			UserForestItem uItem = new UserForestItem(item, MyMarket.getInstance().getItems());
//			uItem.setTile(-1, -1);
//			uItem.setOffset(0.5f, 0.5f);
//			
//			mForest.addItem(uItem);
//			
//			mUserItemAPI.save(uItem);
//			mUserItemAPI.addToParent(uItem, mForest, "userforestitems");
//			
//			if (mListener != null) {
//				mListener.onNewItemBought(uItem);
//			}
//		
//			return true;
//		} catch (APIException e) {
//			return false;
//		}
		return false;
	}

	public void addOnItemBoughtListener(OnItemBoughtListener listener) {
		mListener = listener;
	}
	
	
	public boolean saveForest() {
		try {
			mForestAPI.save(mForest);
			return true;
		} catch (APIException e) {
			return false;
		}
		
	}
	
	public boolean addStatistic(Statistic stat) {
		try {
			mStatAPI.save(stat);
			mStatAPI.addToParent(stat, mForest, "statistics");
			return true;
		} catch (APIException e) {
			Log.e("MyUser", "API failure. Could not add statistic!");
			return false;
		}
		
	}
	
	public boolean addUserItem(UserForestItem item) {
		try {
			mUserItemAPI.save(item);
			mUserItemAPI.addToParent(item, mForest, "userforestitems");
			return true;
		} catch (APIException e) {
			Log.e("MyUser", "API failure. Could not add useritem!");
			return false;
		}
		
	}

}
