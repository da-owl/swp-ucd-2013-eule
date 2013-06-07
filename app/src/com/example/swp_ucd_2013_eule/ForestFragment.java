package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.view.Forest;

public class ForestFragment extends Fragment {
	private Handler mHandler;
	private Timer mTimer;
	private Forest mForest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_forest, container,
				false);

		mForest = (Forest) rootView.findViewById(R.id.forest);
		((TextView) rootView.findViewById(R.id.txtLevelNumber))
		.setText("17");

		// Test-Only Animation
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

}
