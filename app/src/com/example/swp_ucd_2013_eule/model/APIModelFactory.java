package com.example.swp_ucd_2013_eule.model;

public class APIModelFactory {
	
	public static APIModel<Forest, Forest> getForestAPI(){
		return new APIModel<Forest, Forest>(Forest.class);
	}

}
