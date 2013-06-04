package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swp_ucd_2013_eule.view.Forest;

public class ForestFragment extends BaseFragment {

	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public ForestFragment() {
		super("section_forest", 2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);
		/*
		 * TextView dummyTextView = (TextView) rootView
		 * .findViewById(R.id.section_label);
		 * dummyTextView.setText(Integer.toString(getArguments().getInt(
		 * getSectionNumer())));
		 */
		Forest forest = (Forest) rootView.findViewById(R.id.forest);
		return rootView;
	}

	@Override
	public String getPageTitle(int position) {
		switch (position) {
		case 0:
			return getString(R.string.fragment_forest_title_section1);
		case 1:
			return getString(R.string.fragment_forest_title_section2);
		}
		return null;
	}

}
