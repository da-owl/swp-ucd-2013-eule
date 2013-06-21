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
	private int mTileX;
	private int mTileY;
	private float mOffsetX;
	private float mOffsetY;

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

		examples[0].setTile(0, 0);
		examples[0].setOffset(0.5f, 0.5f);
		examples[1].setTile(1, 0);
		examples[1].setOffset(0.5f, 0.5f);
		examples[2].setTile(2, 0);
		examples[2].setOffset(0.5f, 0.5f);
		examples[3].setTile(0, 1);
		examples[3].setOffset(0.5f, 0.5f);
		examples[4].setTile(1, 1);
		examples[4].setOffset(0.5f, 0.5f);
		examples[5].setTile(2, 1);
		examples[5].setOffset(0.5f, 0.5f);

		for (UserForestItem item : examples) {
			item.mItem.incAmount();
		}
		return examples;
	}

	public float getOffsetX() {
		return mOffsetX;
	}

	public float getOffsetY() {
		return mOffsetY;
	}

	/**
	 * 
	 * @param x
	 *            value between 0 and 1
	 * @param y
	 *            value between 0 and 1
	 */
	public void setOffset(float x, float y) {
		mOffsetX = x;
		mOffsetY = y;
	}

	public int getTileX() {
		return mTileX;
	}

	public int getTileY() {
		return mTileY;
	}

	public void setTile(int x, int y) {
		mTileX = x;
		mTileY = y;
	}

	public ForestItem getForestItem() {
		return mItem;
	}
}