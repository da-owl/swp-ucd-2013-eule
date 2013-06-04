package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swp_ucd_2013_eule.view.Forest;

public class ForestFragment extends BaseFragment {
	private Handler mHandler;
	private Timer mTimer;
	private Forest mForest;

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
		mForest = (Forest) rootView.findViewById(R.id.forest);

		// Test Animation
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				mForest.moveItems();
			}
		};
		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.sendToTarget();
			}
		}, 0, 2000);

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTimer.cancel();
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
