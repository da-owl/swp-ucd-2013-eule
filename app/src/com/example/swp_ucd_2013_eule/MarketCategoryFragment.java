package com.example.swp_ucd_2013_eule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.view.Level;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public abstract class MarketCategoryFragment extends Fragment {
	private SlideUpContainer mSlideUpContainer;
	private MarketForestItemAdapter mAdapter;
	private ForestItem mCurItem;
	protected Forest mForest;
	private SlideUpContainerFiller mFiller;

	public abstract MarketForestItemAdapter getMarketForestItemAdapter(
			Context ctx);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mForest = MyForest.getInstance().getForest();

		View rootView = inflater.inflate(R.layout.fragment_market_grid,
				container, false);

		GridView gridView = (GridView) rootView.findViewById(R.id.gridItems);
		mAdapter = getMarketForestItemAdapter(rootView.getContext());
		gridView.setAdapter(mAdapter);

		((TextView) rootView.findViewById(R.id.txtPointNumberHeader))
				.setText(mForest.getPoints().toString());
		((TextView) rootView.findViewById(R.id.txtWoodHeader)).setText(mForest
				.getLevel() * 5 + " m²");

		mFiller = new SlideUpContainerFiller(rootView);
		mSlideUpContainer = mFiller.createSlideUp();
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurItem = (ForestItem) mAdapter.getItem(position);
				mFiller.updateCurrentItemView(mCurItem, getActivity());
				mSlideUpContainer.slideOpen();
			}
		});

		Button btnBuy = (Button) rootView.findViewById(R.id.btnBuyItem);
		btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyForest.getInstance().buyItem(mCurItem);
				mFiller.updateCurrentItemView(mCurItem, getActivity());
				Toast conf = Toast.makeText(getActivity(),
						"One " + mCurItem.getName() + " has been bought",
						Toast.LENGTH_SHORT);
				conf.show();
			}
		});

		return rootView;
	}

}