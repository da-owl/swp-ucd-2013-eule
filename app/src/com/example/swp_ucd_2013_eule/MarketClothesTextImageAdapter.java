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

public class MarketClothesTextImageAdapter extends BaseAdapter{

	private Context mContext;
	private ForestItem[] mItems;

	public MarketClothesTextImageAdapter(Context c) {
		mContext = c;
		mItems = makeExampleClothes();
	}

	private ForestItem[] makeExampleClothes() {

		ForestItem cloth1 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth2 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth3 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth4 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth5 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth6 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth7 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth8 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth9 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		ForestItem cloth10 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_clothes), "T-shirt", "This is a shirt. It is red", 2);
		cloth1.setPrice(2);
		cloth2.setPrice(20);
		cloth3.setPrice(50);
		cloth4.setPrice(86);
		cloth5.setPrice(112);
		cloth6.setPrice(190);
		cloth7.setPrice(210);
		cloth8.setPrice(240);
		cloth9.setPrice(350);
		cloth10.setPrice(400);
		ForestItem[] exampleClothes = new ForestItem[] {cloth1, cloth2, cloth3, cloth4, cloth5, cloth6, cloth7, cloth8, cloth9, cloth10};
		return exampleClothes;
		
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
