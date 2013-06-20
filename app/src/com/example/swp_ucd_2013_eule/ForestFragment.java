package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.data.UserForestItem;
import com.example.swp_ucd_2013_eule.view.Forest;
import com.example.swp_ucd_2013_eule.view.Forest.UserForestItemListener;
import com.example.swp_ucd_2013_eule.view.Level;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public class ForestFragment extends Fragment {
	private Handler mHandler;
	private Timer mTimer;
	private Forest mForest;
	private SlideUpContainer mSlideUpContainer;
	private ForestItem mCurItem;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);

		// XXX Duplicate code (see MarketCategoryFragment) --> outsource
		mSlideUpContainer = (SlideUpContainer) rootView
				.findViewById(R.id.forestSlideUp);
		Button btnClose = (Button) rootView.findViewById(R.id.btnSlideUpClose);

		mForest = (Forest) rootView.findViewById(R.id.forest);
		mForest.setSlideUpContainer(mSlideUpContainer);
		((TextView) rootView.findViewById(R.id.txtDrops)).setText("80");

		mForest.setForestItemListener(new UserForestItemListener() {
			@Override
			public void onForestItemClicked(UserForestItem item) {
				mCurItem = item.getForestItem();
				updateCurrentItemView();
				mSlideUpContainer.slideOpen();
			}
		});
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideUpContainer.slideClose();
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

		((TextView) rootView.findViewById(R.id.txtForestSize))
				.setText("135 m²");

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	// XXX Duplicate code (see MarketCategoryFragment) --> outsource
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
		if (mCurItem.isSpecialItem()) {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.INVISIBLE);
		} else {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.VISIBLE);
		}
	}
}
