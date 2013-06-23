package com.example.swp_ucd_2013_eule.test;

import java.util.List;

import com.example.swp_ucd_2013_eule.model.APIModel;
import com.example.swp_ucd_2013_eule.model.APIModelFactory;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;
import com.example.swp_ucd_2013_eule.net.APIException;

public class ForestAPIModelTest extends APITest {
	
	private final Integer FOREST_ID = 1;
	
	private final Integer DEFAULT_LEVEL = 1;
	
	private final Integer DEFAULT_POINTS = 99;
	
	private final Integer DEFAULT_FRIEND_COUNT = 3;
	
	private final Integer DEFAULT_USER_FOREST_ITEM_COUNT = 2;
	
	public void testForestGetId() {
		try {
			APIModel<Forest, Forest> forestAPI = APIModelFactory.getForestAPI(getContext());
			Forest forest = forestAPI.get(new Forest(), FOREST_ID);
			assertEquals(Integer.valueOf(FOREST_ID), forest.getId());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}	
	}
	
	public void testForestGetPoints() {
		try {
			APIModel<Forest, Forest> forestAPI = APIModelFactory.getForestAPI(getContext());
			Forest forest = forestAPI.get(new Forest(), FOREST_ID);
			assertEquals(Integer.valueOf(DEFAULT_POINTS), forest.getPoints());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}	
	}
	
	public void testForestGetLevel() {
		try {
			APIModel<Forest, Forest> forestAPI = APIModelFactory.getForestAPI(getContext());
			Forest forest = forestAPI.get(new Forest(), FOREST_ID);
			assertEquals(Integer.valueOf(DEFAULT_LEVEL), forest.getLevel());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}	
	}
	
	public void testForestSave() {
		try {
			APIModel<Forest, Forest> forestAPI = APIModelFactory.getForestAPI(getContext());
			Forest forest = new Forest();
			forest.setId(FOREST_ID);
			forest.setUser(2);
			forest.setLevel(1);
			forest.setPoints(DEFAULT_POINTS);
			forest = forestAPI.save(forest);
			assertEquals(Integer.valueOf(1), forest.getLevel());
		} catch (APIException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}
	}
	
	public void testForestFriendAdd() {
		try {
			APIModel<Forest, Forest> forestAPI = APIModelFactory.getForestAPI(getContext());
			Forest parent = new Forest();
			parent.setId(FOREST_ID);
			parent.setUser(2);
			parent.setLevel(1);
			parent.setPoints(DEFAULT_POINTS);
			
			Forest forest = new Forest();
			forest.setId(2);
			
			forest = forestAPI.addToParent(forest, parent, "friends");
			assertEquals(Integer.valueOf(2), forest.getId());
		} catch (APIException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}
	}
	
	public void testForestFriendList() {
		try {
			APIModel<Forest, Forest> forestAPI = new APIModel<Forest, Forest>(Forest.class, getContext());
			Forest parent = new Forest();
			parent.setId(FOREST_ID);
			
			List<Forest> friends = forestAPI.getAllByParent(parent, new Forest(), "friends");
			parent.setFriends(friends);
			
			assertEquals(DEFAULT_FRIEND_COUNT, Integer.valueOf(parent.getFriends().size()));
		} catch (APIException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getCause().getMessage());
		}
		
	}
	
	public void testForestUserForestItemList() {
		try {
			APIModel<UserForestItem, Forest> userForestItemAPI = new APIModel<UserForestItem, Forest>(UserForestItem.class, getContext());
			Forest parent = new Forest();
			parent.setId(FOREST_ID);
			
			List<UserForestItem> userForestItems = userForestItemAPI.getAllByParent(parent, new UserForestItem(null), "userforestitems");
			parent.setUserforestitems(userForestItems);

			assertEquals(DEFAULT_USER_FOREST_ITEM_COUNT, Integer.valueOf(parent.getUserforestitems().size()));
		} catch (APIException e) {
			fail(e.getMessage());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}
}
