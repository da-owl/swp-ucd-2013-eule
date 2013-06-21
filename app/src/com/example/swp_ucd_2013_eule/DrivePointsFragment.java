package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.swp_ucd_2013_eule.car_data.CarDataLogic;
import com.example.swp_ucd_2013_eule.view.BenchmarkBar;
import com.example.swp_ucd_2013_eule.view.ReferenceBar;

public class DrivePointsFragment extends Fragment {
	private BenchmarkBar mLevelBar;
	private ReferenceBar mRefBar;
	private Handler mHandler;
	
	private float mPointProgress;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive_points,
				container, false);

		/*
		 * TextView dummyTextView = (TextView)
		 * rootView.findViewById(R.id.section_label);
		 * dummyTextView.setText("...");
		 */

		((TextView) rootView.findViewById(R.id.txtLevelCur)).setText("35 m²");
		((TextView) rootView.findViewById(R.id.txtLevelNext)).setText("40 m²");
		((TextView) rootView.findViewById(R.id.txtPointsStackNow))
				.setText("80");

		mLevelBar = (BenchmarkBar) rootView.findViewById(R.id.levelBar);
		mLevelBar.setReferenceValue(-1); // No Reference-Indicator
		mLevelBar.setMax(100);
		mLevelBar.setValue(90);
		mLevelBar.setGradientColors(0xFF6cbf1c, 0xFF346802, 0xFF6cbf1c,
				0xFF346802);
		
		mRefBar = (ReferenceBar) rootView.findViewById(R.id.pointReferenceBar);
		mRefBar.setValue(0);
		
		
		
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				Bundle data = msg.getData();

				if (data.containsKey("pointProgress")) {
					mPointProgress = data.getFloat("pointProgress");
					mRefBar.setValue(mPointProgress);
				} 
			}};
		
			CarDataLogic.getInstance().subscribeHandler(mHandler, "pointProgress");
			CarDataLogic.getInstance().subscribeHandler(mHandler, "points");
		

		final ImageView image = (ImageView) rootView
				.findViewById(R.id.imgCombo);
		final Animation animationFadeOut = AnimationUtils.loadAnimation(
				getActivity(), R.anim.fade);
		image.startAnimation(animationFadeOut);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
