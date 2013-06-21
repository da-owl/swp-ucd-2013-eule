package com.example.swp_ucd_2013_eule;

import com.example.swp_ucd_2013_eule.view.SocialList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SocialTopListFragment extends Fragment {
	
	private SocialList mWorldList;
	private SocialList mLocalList;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_social_top_list,
				container, false);

		mWorldList = (SocialList) rootView.findViewById(R.id.social_toplist_world);
		
		mWorldList.addRow(1, "Ronald", 24);
		mWorldList.addRow(2, "Anakin", 23);
		mWorldList.addRow(3, "Owl", 19);
		mWorldList.addRow(4, "XXX_MEGABOY_XXX", 18);
		mWorldList.addRow(5, "Badass", 18);
		
		
		
		mLocalList = (SocialList) rootView.findViewById(R.id.social_toplist_local);
		
		mLocalList.addRow(1, "Marc", 4);
		mLocalList.addRow(2, "Manu", 3);
		mLocalList.addRow(3, "Erik", 3);
		mLocalList.addRow(4, "Konrad", 2);
		mLocalList.addRow(5, "Sammy", 2);
		
		mWorldList.drawList();
		mLocalList.drawList();

		return rootView;
	}

}
