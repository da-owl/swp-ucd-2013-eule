package com.example.swp_ucd_2013_eule;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.data.ForestItem;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;
import com.example.swp_ucd_2013_eule.view.ForestView;
import com.example.swp_ucd_2013_eule.view.ForestView.UserForestItemListener;
import com.example.swp_ucd_2013_eule.view.OnClickNotHandledListener;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

public class ItemBehaviour implements OnClickNotHandledListener {

	private Forest mForest;

	private TextView mTxtDrops;
	private TextView mTxtForestSize;
	private View mRootView;
	private ForestView mForestView;
	private SlideUpContainer mSlideUpContainer;
	private ForestItem mCurItem;

	public ItemBehaviour(View rootView) {
		mRootView = rootView;

		mSlideUpContainer = (SlideUpContainer) mRootView
				.findViewById(R.id.forestSlideUp);

		createCloseButton();
		createBuyButton();
	}

	public void attachForest(Forest forest) {
		mForest = forest;
		updatePoints();
	}

	public void attachPointTextViews(TextView txtDrops, TextView txtForestSize) {
		mTxtDrops = txtDrops;
		mTxtForestSize = txtForestSize;
		updatePoints();
	}

	public void attachForestView() {
		mForestView = (ForestView) mRootView.findViewById(R.id.forest);
		mForestView.setOnClickNotHandledListener(this);
		mForestView.setForestItemListener(new UserForestItemListener() {
			@Override
			public void onForestItemClicked(UserForestItem item) {
				showItem(item.getForestItem());
			}
		});
		mForestView.setForest(mForest);
	}

	private void createBuyButton() {
		Button btnBuy = (Button) mRootView.findViewById(R.id.btnBuyItem);
		btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyForest.getInstance().buyItem(mCurItem);
				updateSlideUpItemView();
				updatePoints();
				Toast conf = Toast.makeText(mRootView.getContext(), "Das Item "
						+ mCurItem.getName() + " wurde \"gekauft\"!",
						Toast.LENGTH_SHORT);
				conf.show();
			}
		});
	}

	private void createCloseButton() {
		Button btnClose = (Button) mRootView.findViewById(R.id.btnSlideUpClose);

		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideUpContainer.slideClose();
			}
		});
	}

	private void updateSlideUpItemView() {
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
		pic.setImageBitmap(mCurItem.getImage(mRootView.getContext()));
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
		if (mCurItem.isSpecialItem()
				|| !MyForest.getInstance().isObtainable(mCurItem)) {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.INVISIBLE);
		} else {
			mSlideUpContainer.findViewById(R.id.btnBuyItem).setVisibility(
					View.VISIBLE);
		}
	}

	private void updatePoints() {
		if (mTxtDrops != null) {
			mTxtDrops.setText(mForest.getPoints().toString());
		}

		if (mTxtForestSize != null) {
			mTxtForestSize.setText(mForest.getLevel() * 5 + " m²");
		}
	}

	public void showItem(ForestItem item) {
		mCurItem = item;
		updateSlideUpItemView();
		mSlideUpContainer.slideOpen();
	}

	@Override
	public void clickNotHandled() {
		mSlideUpContainer.slideClose();
	}
}
