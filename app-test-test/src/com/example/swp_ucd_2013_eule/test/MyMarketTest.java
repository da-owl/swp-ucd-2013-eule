package com.example.swp_ucd_2013_eule.test;

import java.util.List;

import com.example.swp_ucd_2013_eule.model.MyMarket;
import com.example.swp_ucd_2013_eule.model.Item;


public class MyMarketTest extends APITest {

	public void testGetAllItems() {
		MyMarket.getInstance().loadMarket();
		List<Item> items = MyMarket.getInstance().getItems();
		for (Item item : items) {
			System.out.println(item);
		}
		assertEquals(7, items.size());
	}
	
	public void testIsGordonMoveable() {
		MyMarket.getInstance().loadMarket();
		List<Item> items = MyMarket.getInstance().getItems();
		for (Item item : items) {
			if(item.getName().equals("Gordon der Frosch")) {
				assertTrue(item.getMoveable());
			}
		}		
	}
	
	public void testAnimalItems() {
		MyMarket.getInstance().loadMarket();
		List<Item> items = MyMarket.getInstance().getItems(Item.ITEM_CATEGORY_ANIMALS);
		assertEquals(2, items.size());
	}
}
