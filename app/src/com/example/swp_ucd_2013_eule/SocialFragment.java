package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SocialFragment extends BaseFragment {

	public SocialFragment(){
		super("section_number",2);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_social,
				container, false);
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				getSectionNumer())));
		return rootView;
	}

	@Override
	public String getPageTitle(int position) {
		switch (position) {
		case 0:
			return getString(R.string.fragment_social_title_section1);
		case 1:
			return getString(R.string.fragment_social_title_section2);
		}
		return null;
	}
    
}
