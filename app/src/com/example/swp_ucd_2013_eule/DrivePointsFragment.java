package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.swp_ucd_2013_eule.car_data.CarData;
import com.example.swp_ucd_2013_eule.car_data.CarDataLogic;
import com.example.swp_ucd_2013_eule.data.SettingsWrapper;
import com.example.swp_ucd_2013_eule.model.Forest;
import com.example.swp_ucd_2013_eule.model.MyForest;
import com.example.swp_ucd_2013_eule.view.BenchmarkBar;
import com.example.swp_ucd_2013_eule.view.ReferenceBar;

public class DrivePointsFragment extends Fragment {
	private BenchmarkBar mLevelBar;
	private ReferenceBar mRefBar;
	private Handler mHandler;
	private Forest mForest = MyForest.getInstance().getForest();
	private SettingsWrapper mSettings = SettingsWrapper.getInstance();

	private TextView mTxtLevelCur;
	private TextView mTxtLevelNext;
	private TextView mTxtPointsStackNow;

	private static float mPointProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive_points,
				container, false);

		ToggleButton button = (ToggleButton) rootView
				.findViewById(R.id.tglBtTrip);
		button.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton toggleButton,
					boolean isChecked) {
				CarData.getInstance().setRecordTrip(isChecked);
				CarDataLogic.getInstance().setTripStartStop(isChecked);
			}
		});

		mPointProgress = mForest.getPointProgress();
		mTxtLevelCur = (TextView) rootView.findViewById(R.id.txtLevelCur);
		mTxtLevelNext = (TextView) rootView.findViewById(R.id.txtLevelNext);
		mTxtPointsStackNow = (TextView) rootView
				.findViewById(R.id.txtPointsStackNow);

		int level = mForest.getLevel();
		int barMax = mSettings.getPointsToNextLevel(level + 1);
		int curLvlPrgPoints = mForest.getLevelProgessPoints();

		mTxtLevelCur.setText(level * 5 + " m²");
		mTxtLevelNext.setText((level + 1) * 5 + " m²");
		mTxtPointsStackNow.setText(String.valueOf(mForest.getPoints()));

		mLevelBar = (BenchmarkBar) rootView.findViewById(R.id.levelBar);
		mLevelBar.setReferenceValue(-1); // No Reference-Indicator
		mLevelBar.setMax(barMax);
		mLevelBar.setValue(curLvlPrgPoints);
		mLevelBar.setGradientColors(0xFF6cbf1c, 0xFF346802, 0xFF6cbf1c,
				0xFF346802);

		mRefBar = (ReferenceBar) rootView.findViewById(R.id.pointReferenceBar);
		mRefBar.setValue(mPointProgress);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				if (data.containsKey("pointProgress")) {
					mPointProgress = data.getFloat("pointProgress");
					mRefBar.setValue(mPointProgress);
				} else if (data.containsKey("viewChanged")) {
					int level = mForest.getLevel();
					int barMax = mSettings.getPointsToNextLevel(level + 1);
					int curLvlPrgPoints = mForest.getLevelProgessPoints();

					mTxtLevelCur.setText(level * 5 + " m²");
					mTxtLevelNext.setText((level + 1) * 5 + " m²");
					mTxtPointsStackNow.setText(String.valueOf(mForest
							.getPoints()));

					mLevelBar.setMax(barMax);
					mLevelBar.setValue(curLvlPrgPoints);

				}

			}
		};

		CarDataLogic.getInstance().subscribeHandler(mHandler, "pointProgress");
		CarDataLogic.getInstance().subscribeHandler(mHandler, "viewChanged");

		final ImageView image = (ImageView) rootView
				.findViewById(R.id.imgCombo);
		final Animation animationFadeOut = AnimationUtils.loadAnimation(
				getActivity(), R.anim.fade);
		image.startAnimation(animationFadeOut);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		boolean ret;
		ret = CarDataLogic.getInstance().unSubscribeHandler(mHandler,
				"pointProgress");
		Log.d("DVP UnsubscribeHandler", "" + ret);
		ret = CarDataLogic.getInstance().unSubscribeHandler(mHandler,
				"viewChanged");
		Log.d("DVP UnsubscribeHandler", "" + ret);
		super.onDestroyView();
	}
}
