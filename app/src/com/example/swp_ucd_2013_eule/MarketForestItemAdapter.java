package com.example.swp_ucd_2013_eule;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.model.MyMarket;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.Item;

public class MarketForestItemAdapter extends BaseAdapter {
	private Context mContext;
	private Forest mForest;
	private Item[] mItems;

	public static MarketForestItemAdapter getExampleClothesAdapter(Context ctx,
			Forest forest) {

		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx,
				forest);
		List<Item> items = MyMarket.getInstance().getItems();
		// TODO remove harcoded indices-usage
		adapter.mItems = new Item[] { items.get(6) };
		return adapter;
	}

	public static MarketForestItemAdapter getExampleAnimalsAdapter(Context ctx,
			Forest forest) {
		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx,
				forest);

		List<Item> items = MyMarket.getInstance().getItems();
		// TODO remove harcoded indices-usage
		adapter.mItems = new Item[] { items.get(5) };

		return adapter;
	}

	public static MarketForestItemAdapter getExamplePlantsAdapter(Context ctx,
			Forest forest) {
		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx,
				forest);

		List<Item> items = MyMarket.getInstance().getItems();
		// TODO remove harcoded indices-usage
		adapter.mItems = new Item[] { items.get(0), items.get(1), items.get(3),
				items.get(4) };

		return adapter;
	}

	public MarketForestItemAdapter(Context ctx, Forest forest) {
		mContext = ctx;
		mForest = forest;
	}

	@Override
	public int getCount() {
		return mItems.length;
	}

	@Override
	public Item getItem(int position) {
		return mItems[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v;
		if (convertView == null) {
			Item item = mItems[position];

			LayoutInflater li = LayoutInflater.from(mContext);
			v = li.inflate(R.layout.fragment_market_item, null);
			TextView tv = (TextView) v.findViewById(R.id.icon_text);
			tv.setText(item.getPrice() + " Tropfen");
			if (item.getPrice() > mForest.getPoints()) {
				tv.setTextColor(Color.RED);
			}
			tv = (TextView) v.findViewById(R.id.icon_text2);
			tv.setText((item.getLevel() * 5) + " m²");
			if (item.getLevel() > mForest.getLevel()) {
				tv.setTextColor(Color.RED);
			}
			ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
			iv.setImageBitmap(item.getImage(mContext));

		} else {
			v = convertView;
		}
		return v;
	}

}
