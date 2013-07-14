package com.example.swp_ucd_2013_eule;

import android.content.Context;

/**
 * MarketClothesFragment initiates a view containing the information about the
 * animal tab in the market.
 */
public class MarketClothesFragment extends MarketCategoryFragment {

	@Override
	public MarketForestItemAdapter getMarketForestItemAdapter(Context ctx) {
		return MarketForestItemAdapter.getExampleClothesAdapter(ctx, mForest);
	}

}