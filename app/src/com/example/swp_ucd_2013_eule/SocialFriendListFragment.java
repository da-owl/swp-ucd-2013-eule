package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swp_ucd_2013_eule.view.SocialList;

public class SocialFriendListFragment extends Fragment {
	
	private SocialList mList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_social_friend_list,
				container, false);
		
		
		mList = (SocialList) rootView.findViewById(R.id.social_ListFriends);
		
		mList.addRow(1, "Marc", 20);
		mList.addRow(2, "Manu", 15);
		mList.addRow(3, "Erik", 13);
		mList.addRow(4, "Konrad", 11);
		mList.addRow(5, "Sammy", 10);
		mList.addRow(6, "Marc", 20);
		mList.addRow(7, "Manu", 15);
		mList.addRow(8, "Erik", 13);
		mList.addRow(9, "Konrad", 11);
		mList.addRow(10, "Sammy", 10);
		mList.addRow(11, "Marc", 20);
		mList.addRow(12, "Manu", 15);
		mList.addRow(13, "Erik", 13);
		mList.addRow(14, "Konrad", 11);
		mList.addRow(15, "Sammy", 10);
		mList.addRow(16, "Marc", 20);
		mList.addRow(17, "Manu", 15);
		mList.addRow(18, "Erik", 13);
		mList.addRow(19, "Konrad", 11);
		mList.addRow(20, "Sammy", 10);
		

		mList.drawList();
		
		return rootView;
		
	}

}
