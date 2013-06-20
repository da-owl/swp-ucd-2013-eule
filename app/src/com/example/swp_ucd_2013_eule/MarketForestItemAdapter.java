package com.example.swp_ucd_2013_eule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.data.ForestItem;

public class MarketForestItemAdapter extends BaseAdapter {
	private Context mContext;
	private ForestItem[] mItems;

	public static MarketForestItemAdapter getExampleClothesAdapter(Context ctx) {
		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx);
		ForestItem[] items = ForestItem.getExamples(ctx);
		adapter.mItems = new ForestItem[] { items[6], items[6], items[6],
				items[6], items[6] };
		return adapter;
	}

	public static MarketForestItemAdapter getExampleAnimalsAdapter(Context ctx) {
		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx);
		ForestItem[] items = ForestItem.getExamples(ctx);
		adapter.mItems = new ForestItem[] { items[5], items[5], items[5],
				items[5], items[5], items[5], items[5], items[5], items[5],
				items[5], items[5], items[5], items[5] };
		return adapter;
	}

	public static MarketForestItemAdapter getExamplePlantsAdapter(Context ctx) {
		MarketForestItemAdapter adapter = new MarketForestItemAdapter(ctx);
		ForestItem[] items = ForestItem.getExamples(ctx);
		adapter.mItems = new ForestItem[] { items[0], items[1], items[2],
				items[4], items[0], items[1], items[2], items[4] };
		return adapter;
	}

	public MarketForestItemAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	public int getCount() {
		return mItems.length;
	}

	@Override
	public ForestItem getItem(int position) {
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
			ForestItem item = mItems[position];

			LayoutInflater li = LayoutInflater.from(mContext);
			v = li.inflate(R.layout.fragment_market_item, null);
			TextView tv = (TextView) v.findViewById(R.id.icon_text);
			tv.setText(item.getPrice() + " Tropfen");
			if(item.getPrice() > 80){
				tv.setTextColor(Color.RED);
			}
			tv = (TextView) v.findViewById(R.id.icon_text2);
			tv.setText(item.getLevel() + " 	Forest size");
			if(item.getLevel() > 17){
				tv.setTextColor(Color.RED);
			}
			ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
			iv.setImageBitmap(item.getImage());
			
			

		} else {
			v = convertView;
		}
		return v;
	}

}
