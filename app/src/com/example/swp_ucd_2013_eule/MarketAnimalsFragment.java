package com.example.swp_ucd_2013_eule;

import android.content.Context;

/**
 * MarketAnimalsFragment initiates a view containing the information about the 
 * animal tab in the market.
 */
public class MarketAnimalsFragment extends MarketCategoryFragment {

	@Override
	public MarketForestItemAdapter getMarketForestItemAdapter(Context ctx) {
		return MarketForestItemAdapter.getExampleAnimalsAdapter(ctx, mForest);
	}

}