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
			System.out.println("existing");
			return examples;
		}
		System.out.println("create");

		examples = new UserForestItem[6];
		ForestItem[] availableItems = ForestItem.getExamples(ctx);
		examples[0] = new UserForestItem(availableItems[0]);
		examples[1] = new UserForestItem(availableItems[0]);
		examples[2] = new UserForestItem(availableItems[1]);
		examples[3] = new UserForestItem(availableItems[2]);
		examples[4] = new UserForestItem(availableItems[2]);
		examples[5] = new UserForestItem(availableItems[3]);

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
