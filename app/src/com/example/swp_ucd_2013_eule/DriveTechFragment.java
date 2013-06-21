package com.example.swp_ucd_2013_eule;

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

	private GearIndicator mGearIndicator;
	private ReferenceBar mRefBar;
	
	private int mGear = 0;
	private float mAcc = 0;
	private float mRPM = 0;
	private float mConsumption = 0;

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
		mRefBar.setValue(0);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				Bundle data = msg.getData();

				if (data.containsKey("InstantaneousValuePerMilage")) {
					try{
						mConsumption = Float.parseFloat(data.getString("InstantaneousValuePerMilage"));
					}catch(NumberFormatException e){
						System.out.println(e.getMessage());
					}
					mFuelConsumptionNow.setText(String.format("%.1f", mConsumption)+ " l/100km");
				} /*
				 * else if (data.containsKey("InstantaneousValuePerTime")) {
				 * value = Float.parseFloat(data
				 * .getString("InstantaneousValuePerTime"));
				 * mFuelConsumptionNow.setText(String.format("%.1f", value) +
				 * " l/hour"); }
				 */else if (data.containsKey("EngineSpeed")) {
					 try {
						 mRPM = Float.parseFloat(data.getString("EngineSpeed"));
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
					 mGearIndicator.setRPM(mRPM);
				} else if (data.containsKey("CurrentGear")) {
					int oldGear = mGear;
					try {
						mGear = Integer.parseInt(data.getString("CurrentGear"));
					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
					if(oldGear < mGear){
						if(1600<mRPM && mRPM<2000){
							mGearIndicator.gearShift(true);
						}else{
							mGearIndicator.gearShift(false);
						}
					}
					mGearIndicator.setGear(mGear);

				} else if(data.containsKey("LongitudinalAcceleration")){
					try {
						// value can be -20 to +20, bar goes from -100 to +100 therefore use *5 of the value
						// additional use *6 to get better results for the indicator
						mAcc = (Float.parseFloat(data.getString("LongitudinalAcceleration")))*30f;

					} catch (NumberFormatException e) {
						System.out.println(e.getMessage());
					}
					
					mRefBar.setValue(mAcc);
					
				}
			}
		};

		CarData.getInstance().subscribeHandler(mHandler,"InstantaneousValuePerMilage");
		CarData.getInstance().subscribeHandler(mHandler, "EngineSpeed");
		CarData.getInstance().subscribeHandler(mHandler, "CurrentGear");
		CarData.getInstance().subscribeHandler(mHandler, "LongitudinalAcceleration");

		return rootView;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
