package com.example.swp_ucd_2013_eule;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.view.BenchmarkBar;
import com.example.swp_ucd_2013_eule.view.GearIndicator;

public class DriveFragment extends BaseFragment {
	private Handler mHandler;
	private Timer mTimer;

	private TextView mDummyTextView;
	private GearIndicator mGearIndicator;
	private BenchmarkBar mBreakBar;
	private BenchmarkBar mGasBar;

	private int mTestGear = 1;
	private int mTestRPM = 1000;
	private int mTestGas = 60;
	private int mTestBreak = 20;

	public DriveFragment() {
		super("section_drive", 2);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive, container,
				false);
		mDummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		
		((TextView) rootView.findViewById(R.id.txtFuelConsumptionNow)).setText("7,5 l/100km");
		((TextView) rootView.findViewById(R.id.txtFuelConsumptionCurrentTrip)).setText("4,3 l/100km");
		((TextView) rootView.findViewById(R.id.txtFuelConsumptionAll)).setText("3,8 l/100km");

		mGearIndicator = (GearIndicator) rootView
				.findViewById(R.id.gearIndicator);

		mGasBar = (BenchmarkBar) rootView.findViewById(R.id.gasPedalBar);
		mGasBar.setValue(mTestGas);

		mBreakBar = (BenchmarkBar) rootView.findViewById(R.id.brakePedalBar);
		mBreakBar.setValue(mTestBreak);
		mBreakBar.setMax(45);
		mBreakBar.setReferenceValue(40);

		mDummyTextView.setText(Integer.toString(getArguments().getInt(
				getSectionNumer())));

		// Test Animation
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				mTestRPM += 25;
				if (mTestRPM > 1800) {
					mTestRPM = 1000;
				}
				mGearIndicator.setRPM(mTestRPM);

				if (mTestRPM == 1800) {
					mTestGear++;
				}
				if (mTestGear > 6) {
					mTestGear = 1;
				}
				mGearIndicator.setGear(mTestGear);

				mTestGas += 1;
				if (mTestGas > 80) {
					mTestGas = 60;
				}
				mGasBar.setValue(mTestGas);

				mTestBreak += 1;
				if (mTestBreak > 39) {
					mTestBreak = 20;
				}
				mBreakBar.setValue(mTestBreak);
			}
		};

		mTimer = new Timer();
		mTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.sendToTarget();
			}
		}, 0, 50);
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
			return getString(R.string.fragment_drive_title_section1);
		case 1:
			return getString(R.string.fragment_drive_title_section2);
		}
		return null;
	}
}
