package com.example.swp_ucd_2013_eule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MarketAnimalsTextImageAdapter extends BaseAdapter{

	private Context mContext;

	public MarketAnimalsTextImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v;
		if (convertView == null) {
			LayoutInflater li = LayoutInflater.from(mContext);
			v = li.inflate(R.layout.fragment_market_item, null);
			TextView tv = (TextView) v.findViewById(R.id.icon_text);
			tv.setText(mNoPoints[position]);
			ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
			iv.setImageResource(mThumbIds[position]);

		} else {
			v = convertView;
		}
		return v;
	}

	private Integer[] mThumbIds = { R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals, R.drawable.item_animals,
			R.drawable.item_animals,};

	private String[] mNoPoints = { "5 Points", "8 Points", "15 Points",
			"17 Points", "25 Points", "50 Points", "45 Points", "100 Points",
			"500 Points", "520 Points", "598 Points", "650 Points", 
			"650 Points", "780 Points", "999 Points", "1000 Points"};
}