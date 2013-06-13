package com.example.swp_ucd_2013_eule.data;

import android.content.Context;

/**
 * The ForestItems of the current user.
 * 
 * @author MKay
 * 
 */
public class UserForestItem {
	private ForestItem mItem;
	private int mX;
	private int mY;

	private static UserForestItem[] examples;

	public UserForestItem(ForestItem item) {
		mItem = item;
	}

	public static UserForestItem[] getExamples(Context ctx) {
		if (examples != null) {
			return examples;
		}

		ForestItem[] availableItems = ForestItem.getExamples(ctx);
		UserForestItem fir1 = new UserForestItem(availableItems[0]);
		UserForestItem fir2 = new UserForestItem(availableItems[0]);
		UserForestItem tree = new UserForestItem(availableItems[1]);
		UserForestItem bush1 = new UserForestItem(availableItems[2]);
		UserForestItem bush2 = new UserForestItem(availableItems[2]);
		UserForestItem gordon = new UserForestItem(availableItems[3]);

		examples = new UserForestItem[] { fir1, fir2, tree, bush1, bush2,
				gordon };

		for (UserForestItem item : examples) {
			item.mItem.incAmount();
		}
		return examples;
	}

	public int getX() {
		return mX;
	}

	public int getY() {
		return mY;
	}

	public void setCoordinates(int x, int y) {
		this.mX = x;
		this.mY = y;
	}

	public ForestItem getForestItem() {
		return mItem;
	}
}
