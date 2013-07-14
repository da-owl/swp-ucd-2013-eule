package com.example.swp_ucd_2013_eule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.Item;
import com.example.swp_ucd_2013_eule.model.MyForest;

/**
 * MarketCategoryFragment is the base class for fragments showing items of one
 * category inside the market.
 * 
 */
public abstract class MarketCategoryFragment extends Fragment {
	protected Forest mForest;

	private MarketForestItemAdapter mAdapter;
	private ItemBehaviour mItemBehav;

	/**
	 * Returns the MarketForestItemAdapter that contains the items which will be
	 * shown in this fragment.
	 * 
	 * @param ctx
	 * @return
	 */
	public abstract MarketForestItemAdapter getMarketForestItemAdapter(
			Context ctx);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_market_grid,
				container, false);

		mForest = MyForest.getInstance().getForest();
		mItemBehav = new ItemBehaviour(rootView);
		mItemBehav.attachForest(mForest);
		mItemBehav.attachPointTextViews(
				(TextView) rootView.findViewById(R.id.txtPointNumberHeader),
				(TextView) rootView.findViewById(R.id.txtWoodHeader));

		GridView gridView = (GridView) rootView.findViewById(R.id.gridItems);
		mAdapter = getMarketForestItemAdapter(rootView.getContext());
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Item item = (Item) mAdapter.getItem(position);
				mItemBehav.showItem(item);
			}
		});

		return rootView;
	}

}