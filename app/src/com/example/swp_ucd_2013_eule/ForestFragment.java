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

public class ForestFragment extends Fragment implements
		OnClickNotHandledListener {
	private ForestView mForest;
	private SlideUpContainer mSlideUpContainer;
	private ForestItem mCurItem;
	private Handler mHandler;
	private Timer mTimer;
	private SlideUpContainerFiller mFiller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);

		Forest forest = MyForest.getInstance().getForest();

		mFiller = new SlideUpContainerFiller(rootView);
		mSlideUpContainer = mFiller.createSlideUp();

		mForest = (ForestView) rootView.findViewById(R.id.forest);
		mForest.setForest(forest);
		mForest.setOnClickNotHandledListener(this);
		((TextView) rootView.findViewById(R.id.txtDrops)).setText(forest
				.getPoints().toString());

		mForest.setForestItemListener(new UserForestItemListener() {
			@Override
			public void onForestItemClicked(UserForestItem item) {
				mCurItem = item.getForestItem();
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
		((TextView) rootView.findViewById(R.id.txtForestSize)).setText(forest
				.getLevel() * 5 + " m²");

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				mForest.animateMoveableItems();
			}
		};
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.sendToTarget();
			}
		}, 0, 2000);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void clickNotHandled() {
		mSlideUpContainer.slideClose();

	}

}
