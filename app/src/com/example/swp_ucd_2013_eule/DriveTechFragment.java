package com.example.swp_ucd_2013_eule;

import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.car_data.CarData;
import com.example.swp_ucd_2013_eule.view.GearIndicator;
import com.example.swp_ucd_2013_eule.view.ReferenceBar;

public class DriveTechFragment extends Fragment {
	private Handler mHandler;
	private Timer mTimer;

	private GearIndicator mGearIndicator;
	private ReferenceBar mRefBar;

	private int mTestGear = 1;
	private int mTestRPM = 500;
	private int mTestRef = 0;
	private int mMode = 0;
	private int mRefMode = 0;

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

		mRefBar = (ReferenceBar) rootView.findViewById(R.id.referenceBar);
		mRefBar.setValue(mTestRef);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				float value;
				if (data.containsKey("InstantaneousValuePerMilage")) {
					value = Float.parseFloat(data
							.getString("InstantaneousValuePerMilage"));
					mFuelConsumptionNow.setText(String.format("%.1f", value)
							+ " l/100km");
				} else if (data.containsKey("InstantaneousValuePerTime")) {
					value = Float.parseFloat(data
							.getString("InstantaneousValuePerTime"));
					mFuelConsumptionNow.setText(String.format("%.1f", value)
							+ " l/hour");
				} else if (data.containsKey("EngineSpeed")) {
					try {
						mGearIndicator.setRPM(Float.parseFloat(data
								.getString("EngineSpeed")));
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		};

		CarData.getInstance().subscribeHandler(mHandler,
				"InstantaneousValuePerMilage");
		CarData.getInstance().subscribeHandler(mHandler,
				"InstantaneousValuePerTime");
		CarData.getInstance().subscribeHandler(mHandler, "EngineSpeed");

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		mTimer.cancel();
	}

}
