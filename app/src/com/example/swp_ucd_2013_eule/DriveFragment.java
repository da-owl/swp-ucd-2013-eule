package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.view.BenchmarkBar;

public class DriveFragment extends BaseFragment {

	public DriveFragment() {
		super("section_number", 2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive, container,
				false);
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		BenchmarkBar breakBar = (BenchmarkBar) rootView
				.findViewById(R.id.brakePedalBar);
		breakBar.setValue(35);
		breakBar.setMax(45);
		breakBar.setReferenceValue(40);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				getSectionNumer())));
		return rootView;
	}

	@Override
	public String getPageTitle(int position) {
		switch (position) {
		case 0:
			return getString(R.string.fragment_drive_title_section1);
		case 1:
			return getString(R.string.fragment_drive_title_section2);
		}
		return null;
	}
}
