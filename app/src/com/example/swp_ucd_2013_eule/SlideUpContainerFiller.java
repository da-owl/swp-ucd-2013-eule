package com.example.swp_ucd_2013_eule;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SlideUpContainerFiller {

	private SlideUpContainer mSlideUpContainer;
	private View rootView;

	public SlideUpContainerFiller(View rootView) {
		this.rootView = rootView;
	}

	public SlideUpContainer createSlideUp() {
		mSlideUpContainer = (SlideUpContainer) rootView
				.findViewById(R.id.forestSlideUp);
		createCloseButton();
//		createBuyButton();
		return mSlideUpContainer;
	}

	public void createCloseButton() {
		Button btnClose = (Button) rootView.findViewById(R.id.btnSlideUpClose);

		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideUpContainer.slideClose();
			}
		});
	}

	public void updateCurrentItemView(ForestItem mCurItem, Context actv) {
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
		pic.setImageBitmap(mCurItem.getImage(actv));
		// set amount
		TextView amount = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_amount);
		amount.setText("Anzahl: " + mCurItem.getAmount());
		// set required points
		TextView reqPnts = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_requirements_points);
		Integer pnts = mCurItem.getPrice();
		reqPnts.setText(pnts.toString());
		// set required forest size
		Integer reqSz = mCurItem.getLevel() * 5;
		TextView reqFrst = (TextView) mSlideUpContainer
				.findViewById(R.id.txt_requirements_level);
		reqFrst.setText(reqSz.toString());
		if (mCurItem.isSpecialItem() || !isObtainable(mCurItem)) {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.INVISIBLE);
		} else {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.VISIBLE);
		}
	}

	public boolean isObtainable(ForestItem mCurItem) {
		if (mCurItem.getPrice() < MyForest.getInstance().getForest()
				.getPoints()
				& mCurItem.getLevel() < MyForest.getInstance().getForest()
						.getLevel()) {
			return true;

		} else {
			return false;
		}
	}
}
