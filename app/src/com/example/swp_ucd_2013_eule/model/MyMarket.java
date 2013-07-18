package com.example.swp_ucd_2013_eule.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.example.swp_ucd_2013_eule.net.APIException;

/**
 * Wrapper for the market API. Provides methods for retrieving items.
 * @author Erik
 */
public class MyMarket {

	private static MyMarket INSTANCE = new MyMarket();

	private APIModel<Item, Item> mItemAPI;

	private List<Item> items = new LinkedList<Item>();

	private Map<Integer, Item> itemMap = new HashMap<Integer, Item>();
	
	/**
	 * Default constructor. Initializes the corresponding API model and retrieves all items from the backend.
	 */
	private MyMarket() {
		mItemAPI = new APIModel<Item, Item>(Item.class);
		try {
			List<UserForestItem> userItems = MyForest.getInstance().getForest()
					.getUserforestitems();

			items = mItemAPI.getAll(new Item());

			// TODO: dirty, very dirty and very slow "solution" or better hack
			// (erik)
			// is [is only executed once on start up (marc)]
			for (Item item : items) {
				itemMap.put(item.getId(), item);
			}

			for (UserForestItem userForestItem : userItems) {
				int x = userForestItem.getItem();
				itemMap.get(x).incAmount();
				Log.d("MyMarket", "Incremented amount of item with ID " + x
						+ ". New amount is " + itemMap.get(x).getAmount());
			}
		} catch (APIException e) {
			Log.e("MyMarket",
					"Could not retrieve Items! Exception: " + e.getCause());
		}
	}
	
	/**
	 * Returns the current instance.
	 * @return
	 */
	public static MyMarket getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns all items for a given category.
	 * @param category
	 * @return
	 */
	public List<Item> getItems(String category) {
		List<Item> result = new LinkedList<Item>();
		for (Item item : items) {
			if (item.getCategory().equals(category) && item.isStandardItem()) {
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * Returns the item for a given item_id.
	 * @param itemID
	 * @return
	 */
	public Item getItem(int itemID) {
		return itemMap.get(itemID);
	}

}
