package com.example.swp_ucd_2013_eule.test;

import org.json.JSONObject;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.Serializer;

public class ForestModelSerializationTest extends APITest {

	public void testIdSerialization() {
		try {
			Forest forest = new Forest();
			forest.setId(1);
			JSONObject json = new Serializer<Forest>().serialize(forest);
			assertEquals(forest.getId(), (Integer)json.get("id"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void testIdDeserialization() {
		try {
			JSONObject json = new JSONObject();
			Forest forest = new Forest();			
			json.put("id", 1);			
			forest = new Serializer<Forest>().deserialize(forest, json);
			assertEquals((Integer)json.get("id"), forest.getId());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void testPointsSerialization() {
		try {
			Forest forest = new Forest();
			forest.setPoints(1);
			JSONObject json = new Serializer<Forest>().serialize(forest);
			assertEquals(forest.getPoints(), (Integer)json.get("points"));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public void testPointsDeserialization() {
		try {
			JSONObject json = new JSONObject();
			Forest forest = new Forest();			
			json.put("points", 1);
			forest = new Serializer<Forest>().deserialize(forest, json);
			assertEquals((Integer)json.get("points"), forest.getPoints());
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
