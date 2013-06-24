package com.example.swp_ucd_2013_eule.model;

import android.util.Log;

import com.example.swp_ucd_2013_eule.data.ForestItem;

/**
 * The ForestItems of the current user.
 * 
 * @author MKay
 * 
 */
public class UserForestItem extends Model {
	private ForestItem mItem;
	private int tileX;
	private int tileY;
	private float offsetX;
	private float offsetY;

	private static UserForestItem[] examples;

	public UserForestItem(ForestItem item) {
		mItem = item;
	}

	public static UserForestItem[] getExamples() {
		if (examples != null) {
			Log.d("UserForestItem","item already exists");
			return examples;
		}
		Log.d("UserForestItem","creating item");

		examples = new UserForestItem[6];
		ForestItem[] availableItems = ForestItem.getExamples();
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
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	/**
	 * 
	 * @param x
	 *            value between 0 and 1
	 * @param y
	 *            value between 0 and 1
	 */
	public void setOffset(float x, float y) {
		offsetX = x;
		offsetY = y;
	}

	public int getTileX() {
		return tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTile(int x, int y) {
		tileX = x;
		tileY = y;
	}

	public ForestItem getForestItem() {
		return mItem;
	}
}
