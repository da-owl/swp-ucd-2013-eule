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

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.view.Level;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public abstract class MarketCategoryFragment extends Fragment {
	private SlideUpContainer mSlideUpContainer;
	private MarketForestItemAdapter mAdapter;
	private ForestItem mCurItem;

	public abstract MarketForestItemAdapter getMarketForestItemAdapter(
			Context ctx);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_market_grid,
				container, false);

		GridView gridView = (GridView) rootView.findViewById(R.id.gridItems);
		mAdapter = getMarketForestItemAdapter(rootView.getContext());
		gridView.setAdapter(mAdapter);

		((TextView) rootView.findViewById(R.id.txtPointNumberHeader))
				.setText("80");
		((Level) rootView.findViewById(R.id.level)).setLevel(17);

		// XXX Duplicate code (see ForestFragment) --> outsource
		mSlideUpContainer = (SlideUpContainer) rootView
				.findViewById(R.id.forestSlideUp);
		Button btnClose = (Button) rootView.findViewById(R.id.btnSlideUpClose);

		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideUpContainer.slideClose();
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurItem = (ForestItem) mAdapter.getItem(position);
				updateCurrentItemView();
				mSlideUpContainer.slideOpen();
			}
		});

		Button btnBuy = (Button) rootView.findViewById(R.id.btnBuyItem);
		btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCurItem.incAmount();
				updateCurrentItemView();
			}
		});

		return rootView;
	}

	// XXX Duplicate code (see ForestFragment) --> outsource
	private void updateCurrentItemView() {
		// set label
		TextView name = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_label);
		name.setText("Detailansicht " + mCurItem.getName());
		// set description
		TextView des = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_details);
		des.setText(mCurItem.getDescription());
		// set Picture
		ImageView pic = (ImageView) mSlideUpContainer
				.findViewById(R.id.imgItem);
		pic.setImageBitmap(mCurItem.getImage());
		// set amount
		TextView amount = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_amount);
		amount.setText("Anzahl: " + mCurItem.getAmount());
	}

}