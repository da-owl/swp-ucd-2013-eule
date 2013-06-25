package com.example.swp_ucd_2013_eule.test;

import com.example.swp_ucd_2013_eule.net.ApiClient;

import android.test.AndroidTestCase;

public class APITest extends AndroidTestCase {
	
	public static Integer DEFAULT_STATISTIC_COUNT = 4;
	
	public final Integer DEFAULT_FRIEND_COUNT = 3;
	
	public final Integer DEFAULT_USER_FOREST_ITEM_COUNT = 6;
	
	@Override
	protected void setUp() throws Exception {
		System.out.println("-------------------------------------------------------------------------");
		ApiClient.getInstance().setServer("10.0.2.2:8080");		
		ApiClient.getInstance().setAuthToken("c515f179da3f768d6802709fbd98aa5c8e60d9a1");
		ApiClient.getInstance().setContext(getContext());
		super.setUp();
	}

}
