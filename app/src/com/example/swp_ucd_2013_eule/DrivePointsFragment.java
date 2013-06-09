package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swp_ucd_2013_eule.view.BenchmarkBar;

public class DrivePointsFragment extends Fragment {
	BenchmarkBar mLevelBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive_points,
				container, false);

		
		/*
		TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		dummyTextView.setText("...");
		*/
		
		mLevelBar = (BenchmarkBar) rootView.findViewById(R.id.levelBar);
		mLevelBar.setReferenceValue(-1); // No Reference-Indicator

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}
