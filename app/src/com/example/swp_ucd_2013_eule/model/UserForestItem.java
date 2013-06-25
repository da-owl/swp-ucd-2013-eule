package com.example.swp_ucd_2013_eule.model;

import java.util.LinkedList;
import java.util.List;


/**
 * The ForestItems of the current user.
 * 
 * @author MKay
 * 
 */
public class UserForestItem extends Model {
	private Integer item;
	private Integer tileX;
	private Integer tileY;
	private Float offsetX;
	private Float offsetY;
	
	private List<Item> availableItems = new LinkedList<Item>();

//	private static UserForestItem[] examples;
	
	public UserForestItem() {
		
	}

	public UserForestItem(Item item, List<Item> marketItems) {
		this.availableItems.addAll(marketItems);
		this.item = item.getId();
	}

//	public static UserForestItem[] getExamples() {
//		if (examples != null) {
//			Log.d("UserForestItem","item already exists");
//			return examples;
//		}
//		Log.d("UserForestItem","creating item");
//
//		examples = new UserForestItem[6];
//		Item[] availableItems = Item.getExamples();
//		examples[0] = new UserForestItem(availableItems[0]);
//		examples[1] = new UserForestItem(availableItems[0]);
//		examples[2] = new UserForestItem(availableItems[1]);
//		examples[3] = new UserForestItem(availableItems[2]);
//		examples[4] = new UserForestItem(availableItems[2]);
//		examples[5] = new UserForestItem(availableItems[3]);
//
//		examples[0].setTile(0, 0);
//		examples[0].setOffset(0.5f, 0.5f);
//		examples[1].setTile(1, 0);
//		examples[1].setOffset(0.5f, 0.5f);
//		examples[2].setTile(2, 0);
//		examples[2].setOffset(0.5f, 0.5f);
//		examples[3].setTile(0, 1);
//		examples[3].setOffset(0.5f, 0.5f);
//		examples[4].setTile(1, 1);
//		examples[4].setOffset(0.5f, 0.5f);
//		examples[5].setTile(2, 1);
//		examples[5].setOffset(0.5f, 0.5f);
//
//		for (UserForestItem item : examples) {
//			item.mItem.incAmount();
//		}
//		return examples;
//	}

	public Float getOffsetX() {
		return offsetX;
	}

	public Float getOffsetY() {
		return offsetY;
	}	

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	/**
	 * 
	 * @param x
	 *            value between 0 and 1
	 * @param y
	 *            value between 0 and 1
	 */
	public void setOffset(Float x, Float y) {
		offsetX = x;
		offsetY = y;
	}

	public Integer getTileX() {
		return tileX;
	}

	public Integer getTileY() {
		return tileY;
	}

	public void setTileX(Integer tileX) {
		this.tileX = tileX;
	}

	public void setTileY(Integer tileY) {
		this.tileY = tileY;
	}
	
	public void setOffsetX(Double offsetX) {
		this.offsetX = new Float(offsetX.floatValue());
	}

	public void setOffsetY(Double offsetY) {
		this.offsetY = new Float(offsetY.floatValue());
	}

	public void setOffsetX(Float offsetX) {
		this.offsetX = offsetX;
	}

	public void setOffsetY(Float offsetY) {
		this.offsetY = offsetY;
	}

	public void setTile(Integer x, Integer y) {
		tileX = x;
		tileY = y;
	}

	public Item getForestItem() {
		for (Item item: this.availableItems) {
			if(item.getId().equals(this.item)) {
				return item;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "UserForestItem [item=" + item + ", tileX=" + tileX + ", tileY="
				+ tileY + ", offsetX=" + offsetX + ", offsetY=" + offsetY
				+ ", availableItems=" + availableItems + "]";
	}
	
	
}
