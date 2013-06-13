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

public class MarketPlantsTextImageAdapter extends BaseAdapter{

	private Context mContext;
	private ForestItem[] mItems;

	public  MarketPlantsTextImageAdapter(Context c) {
	    mContext = c;
	    mItems = makeExamplePlants();
	}

	private ForestItem[] makeExamplePlants() {
		ForestItem fir1 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_fir), "Fir", "This is a fir. It is a tree", 2);
		ForestItem fir2 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_fir), "Fir", "This is a fir. It is a tree", 2);
		ForestItem fir3 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_fir), "Fir", "This is a fir. It is a tree", 2);
		ForestItem fir4 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_fir), "Fir", "This is a fir. It is a tree", 2);
		ForestItem fir5 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_fir), "Fir", "This is a fir. It is a tree", 2);
		ForestItem bush1 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_bush), "Bush", "This is a bush. It is green", 2);
		ForestItem bush2 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_bush), "Bush", "This is a bush. It is green", 2);
		ForestItem bush3 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_bush), "Bush", "This is a bush. It is green", 2);
		ForestItem bush4 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_bush), "Bush", "This is a bush. It is green", 2);
		ForestItem tulip1 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_plants), "Tulip", "This is a flower.", 2);
		ForestItem tulip2 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_plants), "Tulip", "This is a pink flower.", 2);
		ForestItem tulip3 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_plants), "Tulip", "This is a flower.", 2);
		ForestItem tulip4 = new ForestItem( ForestItemType.STANDARD,
				BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.item_plants), "Tulip", "This is a tulip. It is pink.", 2);
		fir1.setPrice(50);
		fir2.setPrice(90);
		fir3.setPrice(150);
		fir4.setPrice(200);
		fir5.setPrice(250);
		bush1.setPrice(290);
		bush2.setPrice(310);
		bush3.setPrice(450);
		bush4.setPrice(450);
		tulip1.setPrice(490);
		tulip2.setPrice(500);
		tulip3.setPrice(510);
		tulip4.setPrice(520);
		ForestItem[] examplePlants = new ForestItem[] {fir1, fir2, fir3, fir4, fir5, 
				bush1, bush2, bush3, bush4, tulip1, tulip2, tulip3, tulip4};
		return examplePlants;
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
	            View v;
	            if(convertView==null){
	                LayoutInflater li = LayoutInflater.from(mContext);
	                v = li.inflate(R.layout.fragment_market_item, null);
	                TextView tv = (TextView)v.findViewById(R.id.icon_text);
	                tv.setText(mItems[position].getPrice() + " Points");
	                ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
	                iv.setImageBitmap(mItems[position].getImage());
	 
	            }
	            else
	            {
	                v = convertView;
	            }
	            return v;
	    }

    


	private Integer[] mThumbIds = {
	        R.drawable.item_plants, R.drawable.item_plants,
	        R.drawable.item_plants, R.drawable.item_plants,
	        R.drawable.item_plants, R.drawable.item_plants,
	};
	
	private String[] mNoPoints = {
			"5 Points", "8 Points",
			"15 Points", "17 Points",
			"25 Points", "50 Points",
	};
	
}
