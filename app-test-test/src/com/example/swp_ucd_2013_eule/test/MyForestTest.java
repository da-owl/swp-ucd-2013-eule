package com.example.swp_ucd_2013_eule.test;

import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.model.MyMarket;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.Item;
import com.example.swp_ucd_2013_eule.model.Statistic;
import com.example.swp_ucd_2013_eule.model.UserForestItem;

public class MyForestTest extends APITest {
	
	public void testMyForestSimpleInstanceTest(){
		Forest forest = MyForest.getInstance().getForest();
		assertEquals(MyForest.FOREST_ID, forest.getId());
	}
	
//	public void testMyForestComplexInstanceItemsTest(){
//		Forest forest = MyForest.getInstance().getForest();
//		for (UserForestItem item: forest.getUserforestitems()) {
//			System.out.println(item);
//		}
//		assertEquals(5, forest.getUserforestitems().size());
//	}
//	
//	public void testMyForestComplexInstanceStatsTest(){
//		Forest forest = MyForest.getInstance().getForest();
//		for (Statistic stat: forest.getStatistics()) {
//			System.out.println(stat);
//		}
//		assertEquals(4, forest.getStatistics().size());
//	}
	
	public void testMyForestAddStatistic(){
		Statistic stat = new Statistic();
		
		stat.setConsumption(3.4f);
		stat.setConsumption(7.2f);
		stat.setConsumption(33.4f);
		stat.setConsumption(9.8f);
		
		stat.setDataInterval(1);
		stat.setTripConsumption(300.7f);
		
		stat.setConsumption(9.4f);
		stat.setGainedPoints(23);
		
		boolean state = MyForest.getInstance().addStatistic(stat);
		
		assertTrue(state);
	}
	
	public void testMyForestAddUserForestItem(){
		Item item = MyMarket.getInstance().getItems().get(1);
		UserForestItem userItem = new UserForestItem(item, MyMarket.getInstance().getItems());
		
		userItem.setTile(3, 0);
		userItem.setOffset(0.5f, 0.5f);
		
		// assertNotNull(userItem.getForestItem());
		
		boolean state = MyForest.getInstance().addUserItem(userItem);
		assertTrue(state);
		
	}

}
