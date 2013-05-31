package com.example.swp_ucd_2013_eule;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

	private String mSectionNumber;
	private int mNumberOfSections;

	BaseFragment(String section, int sections) {
		mSectionNumber = section;
		mNumberOfSections = sections;
	}

	public String getSectionNumer() {
		return mSectionNumber;
	}

	public int getNumerOfSections() {
		return mNumberOfSections;
	}
	
	public abstract String getPageTitle(int position);
}