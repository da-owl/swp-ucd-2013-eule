package com.example.swp_ucd_2013_eule;

import android.app.Activity;
import android.os.Bundle;

import com.example.swp_ucd_2013_eule.view.SocialList;

public class ListActivity extends Activity {

	private SocialList mList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_social_friend_list);

		setTitle(getIntent().getStringExtra("listName") + "-Bestenliste");

		mList = (SocialList) findViewById(R.id.social_ListFriends);
		
		mList.addRow(1, "Marc", 5);
		mList.addRow(2, "Manu", 4);
		mList.addRow(3, "Erik", 4);
		mList.addRow(4, "Konrad", 4);
		mList.addRow(5, "Sammy", 3);
		mList.addRow(6, "Marc", 3);
		mList.addRow(7, "Manu", 3);
		mList.addRow(8, "Erik", 3);
		mList.addRow(9, "Konrad", 2);
		mList.addRow(10, "Sammy", 2);
		mList.addRow(11, "Marc", 2);
		mList.addRow(12, "Manu", 2);
		mList.addRow(13, "Erik", 1);
		mList.addRow(14, "Konrad", 1);
		mList.addRow(15, "Sammy", 1);
		mList.addRow(16, "Marc", 0);
		mList.addRow(17, "Manu", 0);
		mList.addRow(18, "Sammy", 0);

		mList.drawList();

	}
}
