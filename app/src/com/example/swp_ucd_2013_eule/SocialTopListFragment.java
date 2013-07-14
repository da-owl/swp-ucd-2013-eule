package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swp_ucd_2013_eule.view.SocialList;

/**
 * SocialTopListFragment initializes a view containing two SocialLists. The
 * first one contains the five best player in the world and the second one the
 * five best inside the user's local area.
 * 
 */
public class SocialTopListFragment extends Fragment {

	private SocialList mWorldList;
	private SocialList mLocalList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_social_top_list,
				container, false);

		mWorldList = (SocialList) rootView
				.findViewById(R.id.social_toplist_world);

		mWorldList.addRow(1, "Ronald", 24);
		mWorldList.addRow(2, "Anakin", 23);
		mWorldList.addRow(3, "Owl", 19);
		mWorldList.addRow(4, "MEGABOY", 18);
		mWorldList.addRow(5, "Badass", 18);

		mLocalList = (SocialList) rootView
				.findViewById(R.id.social_toplist_local);

		mLocalList.addRow(1, "Marc", 5);
		mLocalList.addRow(2, "Manu", 4);
		mLocalList.addRow(3, "Erik", 4);
		mLocalList.addRow(4, "Konrad", 2);
		mLocalList.addRow(5, "Sammy", 2);

		mWorldList.setlistName("Welt");
		mLocalList.setlistName("Lokale");
		mWorldList.drawList();
		mLocalList.drawList();

		return rootView;
	}

}
