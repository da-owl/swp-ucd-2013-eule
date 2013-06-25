package com.example.swp_ucd_2013_eule.test;

import java.util.List;

import com.example.swp_ucd_2013_eule.model.MyMarket;
import com.example.swp_ucd_2013_eule.model.Item;


public class MyMarketTest extends APITest {

	public void testGetAllItems() {
		List<Item> items = MyMarket.getInstance().getItems();
		for (Item item : items) {
			System.out.println(item);
		}
		assertEquals(7, items.size());
	}
}
