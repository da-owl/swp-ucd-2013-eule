package com.example.swp_ucd_2013_eule;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.Item;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.model.UserForestItem;
import com.example.swp_ucd_2013_eule.view.ForestView;
import com.example.swp_ucd_2013_eule.view.ForestView.UserForestItemListener;
import com.example.swp_ucd_2013_eule.view.OnClickNotHandledListener;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

/**
 * The ItemBehaviour class encapsulates item-specific behaviour that is used in
 * multiple views like OtherForestActivity, ForestFragment and
 * MarketCategoryFragment.
 * 
 * For the main part it provides the functionality to open/close a
 * SlideUpContainer which shows the item details, a buy button and a box-close
 * button.
 * 
 * A Forest(View) can be attached to automatically open that SlideUpContainer if
 * an item in the ForestView is clicked. Moreover TextViews for drops and forest
 * size can be attached to adjust it's text values after an item was bought.
 * 
 */
public class ItemBehaviour implements OnClickNotHandledListener {

	private Forest mForest;

	private TextView mTxtDrops;
	private TextView mTxtForestSize;
	private View mRootView;
	private ForestView mForestView;
	private SlideUpContainer mSlideUpContainer;
	private Item mCurItem;

	/**
	 * Constructor
	 * 
	 * @param rootView
	 */
	public ItemBehaviour(View rootView) {
		mRootView = rootView;

		mSlideUpContainer = (SlideUpContainer) mRootView
				.findViewById(R.id.forestSlideUp);

		createCloseButton();
		createBuyButton();
	}

	/**
	 * Attach a Forest-object.
	 * 
	 * Its drops/forest size values are used to show in TextViews which may be
	 * attached using attachPointTextViews. Moreover the Forest-object is used
	 * by attachForestView to show that forest.
	 * 
	 * @param forest
	 */
	public void attachForest(Forest forest) {
		mForest = forest;
		updatePoints();
	}

	/**
	 * Set the TextViews which are used to show the drops/forest size values of
	 * the attached Forest-object.
	 * 
	 * @param txtDrops
	 * @param txtForestSize
	 */
	public void attachPointTextViews(TextView txtDrops, TextView txtForestSize) {
		mTxtDrops = txtDrops;
		mTxtForestSize = txtForestSize;
		updatePoints();
	}

	/**
	 * Attach the ForestView R.id.forest of the root view so that it shows the
	 * attached Forest-object. Moreover setup onClick-Listeners for the Items
	 * inside the forest so that the SlideUpContainer will open if an item is
	 * clicked.
	 */
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

	/**
	 * Setup the behaviour of the buy-button.
	 */
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

	/**
	 * Setup the behaviour of the close-button.
	 */
	private void createCloseButton() {
		Button btnClose = (Button) mRootView.findViewById(R.id.btnSlideUpClose);

		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSlideUpContainer.slideClose();
			}
		});
	}

	/**
	 * Update the TextViews inside the SlideUpContainer to show the current
	 * item's values.
	 */
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

	/**
	 * Update the attached TextViews (if available) to show the current forest's
	 * drops and forest size.
	 */
	private void updatePoints() {
		if (mTxtDrops != null) {
			mTxtDrops.setText(mForest.getPoints().toString());
		}

		if (mTxtForestSize != null) {
			mTxtForestSize.setText(mForest.getLevel() * 5 + " m²");
		}
	}

	/**
	 * Setup the SlideUpContainer to show the specified item-data and the open
	 * the container.
	 * 
	 * @param item
	 */
	public void showItem(Item item) {
		mCurItem = item;
		updateSlideUpItemView();
		mSlideUpContainer.slideOpen();
	}

	/**
	 * If a click was not handled by the ForestView, then this method is called.
	 * This method closes the SlideUpContainer.
	 */
	@Override
	public void clickNotHandled() {
		mSlideUpContainer.slideClose();
	}
}
