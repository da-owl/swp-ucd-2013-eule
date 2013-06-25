package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
	private SlideUpContainerFiller mFiller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_forest);

		setTitle(getIntent().getStringExtra("userName") + "'s forest");

		mForest = (ForestView) findViewById(R.id.forest);
		// TODO User other's forest
		mForest.setForest(MyForest.getInstance().getForest());

		mFiller = new SlideUpContainerFiller(getWindow().getDecorView().getRootView());
		mSlideUpContainer = mFiller.createSlideUp();
		mForest.setSlideUpContainer(mSlideUpContainer);
	
		mForest.setForestItemListener(new UserForestItemListener() {
			@Override
			public void onForestItemClicked(UserForestItem item) {
				mCurItem = item.getForestItem();
				mFiller.updateCurrentItemView(mCurItem, OtherForestActivity.this);
				mSlideUpContainer.slideOpen();
			}
		});


		Button btnBuy = (Button) findViewById(R.id.btnBuyItem);
		btnBuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyForest.getInstance().buyItem(mCurItem);
				mFiller.updateCurrentItemView(mCurItem, OtherForestActivity.this);
				Toast conf = Toast.makeText(OtherForestActivity.this,
						"One " + mCurItem.getName() + " has been bought",
						Toast.LENGTH_SHORT);
				conf.show();
			}
		});

		((TextView) findViewById(R.id.txtDrops)).setText("80");

		((TextView) findViewById(R.id.txtForestSize)).setText("85 m²");

	}

}
