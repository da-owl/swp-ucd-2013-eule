package com.example.swp_ucd_2013_eule;

import android.content.Context;

public class MarketAnimalsFragment extends MarketCategoryFragment {

	@Override
	public MarketForestItemAdapter getMarketForestItemAdapter(Context ctx) {
		return MarketForestItemAdapter.getExampleAnimalsAdapter(ctx);
	}

}