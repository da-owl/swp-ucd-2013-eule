package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;
import com.example.swp_ucd_2013_eule.view.ForestView;
import com.example.swp_ucd_2013_eule.view.ForestView.UserForestItemListener;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public class OtherForestActivity extends Activity {
	private ForestView mForest;

	private SlideUpContainer mSlideUpContainer;
	private ForestItem mCurItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_forest);

		setTitle(getIntent().getStringExtra("userName") + "'s forest");

		mForest = (ForestView) findViewById(R.id.forest);
		// TODO User other's forest
		mForest.setForest(MyForest.getInstance().getForest());

		// XXX Duplicate code (see MarketCategoryFragment) --> outsource
		mSlideUpContainer = (SlideUpContainer) findViewById(R.id.forestSlideUp);
		mForest.setSlideUpContainer(mSlideUpContainer);
		Button btnClose = (Button) findViewById(R.id.btnSlideUpClose);

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

		Button btnBuy = (Button) findViewById(R.id.btnBuyItem);
		btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyForest.getInstance().buyItem(mCurItem);
				updateCurrentItemView();
			}
		});

		((TextView) findViewById(R.id.txtDrops)).setText("80");

		((TextView) findViewById(R.id.txtForestSize)).setText("85 m²");

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
		pic.setImageBitmap(mCurItem.getImage(this));
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
