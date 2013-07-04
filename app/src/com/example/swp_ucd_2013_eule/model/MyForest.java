package com.example.swp_ucd_2013_eule.model;

import java.util.List;

import android.util.Log;

import com.example.swp_ucd_2013_eule.data.SettingsWrapper;
import com.example.swp_ucd_2013_eule.net.APIException;

public class MyForest {

	private static MyForest INSTANCE = new MyForest();
	private Forest mForest;
	private OnItemBoughtListener mListener;

	private APIModel<Forest, Forest> mForestAPI;
	private APIModel<UserForestItem, Forest> mUserItemAPI;
	private APIModel<Statistic, Forest> mStatAPI;
	
	public final static Integer FOREST_ID = 1;

	private MyForest() {
		// mForest.setLevel(17);
		// mForest.setPoints(80);
		// mForest.setLevelProgessPoints(89);
		// mForest.setPointProgress(90f);
		
	}

	public static MyForest getInstance() {
		return INSTANCE;
	}

	public Forest loadForest() {
		mForestAPI = new APIModel<Forest, Forest>(Forest.class);
		mUserItemAPI = new APIModel<UserForestItem, Forest>(
				UserForestItem.class);
		mStatAPI = new APIModel<Statistic, Forest>(Statistic.class);
		try {
			MyMarket.getInstance().loadMarket();
			List<Item> items = MyMarket.getInstance().getItems();

			Forest forest = new Forest(SettingsWrapper.getInstance()
					.getCurrentForestId());
			mForest = mForestAPI.get(forest);

			List<UserForestItem> userItems = mUserItemAPI.getAllByParent(
					mForest, new UserForestItem(), "userforestitems");

			// TODO workaround. Should be done by api/serializier???
			for (UserForestItem ui : userItems) {
				ui.setMartketItems(items);
			}

			List<Statistic> stats = mStatAPI.getAllByParent(mForest,
					new Statistic(), "statistics");

			mForest.setUserforestitems(userItems);
			mForest.setStatistics(stats);
		} catch (APIException e) {
			Log.e("MyUser", "API failure. Failed to load forest!");
		}

		return mForest;
	}

	public Forest getForest() {
		return mForest;
	}

	public boolean isObtainable(Item item) {
		if (item.getPrice() <= mForest.getPoints()
				& item.getLevel() <= mForest.getLevel()) {
			return true;
		}
		return false;
	}

	public boolean buyItem(Item item) {
		try {
			item.incAmount();

			int points = mForest.getPoints() - item.getPrice();
			mForest.setPoints(points);
			UserForestItem uItem = new UserForestItem(item, MyMarket
					.getInstance().getItems());
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
			Log.e("MyForest", "API failure. Could not add statistic!");
			return false;
		}

	}

	public boolean addUserItem(UserForestItem item) {
		try {
			mUserItemAPI.save(item);
			mUserItemAPI.addToParent(item, mForest, "userforestitems");
			return true;
		} catch (APIException e) {
			Log.e("MyForest", "API failure. Could not add useritem!");
			return false;
		}

	}
	
	public boolean updateUserForestItemPosition(UserForestItem item) {
		try {
			mUserItemAPI.save(item);
			return true;
		} catch (APIException e) {
			Log.e("MyForest", "API failure. Could not update useritem position!");
			return false;
		}
	}

}
