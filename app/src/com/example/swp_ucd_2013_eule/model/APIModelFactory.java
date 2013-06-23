package com.example.swp_ucd_2013_eule.model;

import android.content.Context;

public class APIModelFactory {
	
	public static APIModel<Forest, Forest> getForestAPI(Context ctx){
		return new APIModel<Forest, Forest>(Forest.class, ctx);
	}

}
