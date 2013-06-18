package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.swp_ucd_2013_eule.view.BenchmarkBar;

public class DrivePointsFragment extends Fragment {
	BenchmarkBar mLevelBar;

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

		((TextView) rootView.findViewById(R.id.txtLevelCur)).setText("135 m²");
		((TextView) rootView.findViewById(R.id.txtLevelNext)).setText("140 m²");
		((TextView) rootView.findViewById(R.id.txtPointsStackNow))
				.setText("80");

		mLevelBar = (BenchmarkBar) rootView.findViewById(R.id.levelBar);
		mLevelBar.setReferenceValue(-1); // No Reference-Indicator
		mLevelBar.setMax(100);
		mLevelBar.setValue(90);
		mLevelBar.setGradientColors(0xFF6cbf1c, 0xFF346802, 0xFF6cbf1c,
				0xFF346802);

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
