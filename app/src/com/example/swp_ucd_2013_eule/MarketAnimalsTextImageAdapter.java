package com.example.swp_ucd_2013_eule;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.data.ForestItem.ForestItemType;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MarketAnimalsTextImageAdapter extends BaseAdapter{

	private Context mContext;
	private ForestItem[] mItems;

	public MarketAnimalsTextImageAdapter(Context c) {
		mContext = c;
		mItems = makeExampleAnimals();
	}

	private ForestItem[] makeExampleAnimals() {
		ForestItem bird1 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird1", "This is bird1. It is Brown", 2);
		ForestItem bird2 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird2", "This is bird2. It is also Brown", 1);
		ForestItem bird3 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird3", "This is bird1. Brown", 3);
		ForestItem bird4 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird4", "This is bird4. It is Brown", 5);
		ForestItem bird5 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird5", "This is bird5. It is Brown", 2);
		ForestItem bird6 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_animals), "Bird6", "This is bird6. It is the last bird", 2);
		bird1.setPrice(5);
		bird2.setPrice(15);
		bird3.setPrice(40);
		bird4.setPrice(100);
		bird5.setPrice(115);
		bird6.setPrice(205);
		ForestItem[] exampleAnimals = new ForestItem[] {bird1, bird2, bird3, bird4, bird5, bird6};
		return exampleAnimals;
	}

	public int getCount() {
		return mItems.length;
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
			tv.setText(mItems[position].getPrice() + " Points");
			ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
			iv.setImageBitmap(mItems[position].getImage());

		} else {
			v = convertView;
		}
		return v;
	}

	
}
