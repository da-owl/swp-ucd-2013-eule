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

import com.example.swp_ucd_2013_eule.car_data.CarData;
import com.example.swp_ucd_2013_eule.car_data.CarDataListener;
import com.example.swp_ucd_2013_eule.view.BenchmarkBar;
import com.example.swp_ucd_2013_eule.view.GearIndicator;

public class DriveTechFragment extends Fragment implements CarDataListener {
	private Handler mHandler;
	private Timer mTimer;

	private GearIndicator mGearIndicator;
	private BenchmarkBar mBreakBar;
	private BenchmarkBar mGasBar;

	private int mTestGear = 1;
	private int mTestRPM = 500;
	private int mTestGas = 60;
	private int mTestBreak = 20;
	private int mMode = 0;

	private TextView mFuelConsumptionNow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive_tech,
				container, false);

		mFuelConsumptionNow = (TextView) rootView
				.findViewById(R.id.txtFuelConsumptionNow);
		// ((TextView) rootView.findViewById(R.id.txtFuelConsumptionNow))
		// .setText("7,5 l/100km");
		((TextView) rootView.findViewById(R.id.txtFuelConsumptionCurrentTrip))
				.setText("4,3 l/100km");
		((TextView) rootView.findViewById(R.id.txtFuelConsumptionAll))
				.setText("3,8 l/100km");

		mGearIndicator = (GearIndicator) rootView
				.findViewById(R.id.gearIndicator);

		mGasBar = (BenchmarkBar) rootView.findViewById(R.id.gasPedalBar);
		mGasBar.setValue(mTestGas);

		mBreakBar = (BenchmarkBar) rootView.findViewById(R.id.brakePedalBar);
		mBreakBar.setValue(mTestBreak);
		mBreakBar.setMax(45);
		mBreakBar.setReferenceValue(40);

		CarData.getInstance().subscribeListener(this,
				"InstantaneousValuePerMilage");
		CarData.getInstance().subscribeListener(this,
				"InstantaneousValuePerTime");

		// Test-Only Animation
		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				mTestRPM += 25;
				mGearIndicator.setRPM(mTestRPM);

				boolean gearShift = false;
				if ((mMode == 0 && mTestRPM == 1800)
						|| (mMode == 1 && mTestRPM == 2500)) {
					mTestGear++;
					gearShift = true;
					mTestRPM = 500;
				}
				if (mTestGear > 6) {
					mTestGear = 1;
				}
				if (gearShift) {
					mGearIndicator.setGear(mTestGear);
					mMode = 1 - mMode;
					mGearIndicator.gearShift(mMode == 1);
				}

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
	public void handleCarData(String name, String value) {
		if (name.equals("InstantaneousValuePerMilage")) {
			mFuelConsumptionNow.setText(value + " l/100km");
		}
		if (name.equals("InstantaneousValuePerTime")) {
			mFuelConsumptionNow.setText(value + " l/hour");
		}

	}
}
