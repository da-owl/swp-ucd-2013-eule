package com.example.swp_ucd_2013_eule;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.swp_ucd_2013_eule.car_data.CarData;
import com.example.swp_ucd_2013_eule.view.GearIndicator;
import com.example.swp_ucd_2013_eule.view.ReferenceBar;
import com.example.swp_ucd_2013_eule.view.SlideUpContainer;

/**
 * DriveTechFragment creates a view which visualize the data from the car in an
 * appropriate way. The Gear indicator for example contains also a rev counter
 * which scales logarithmic. There is also a visualization for acceleration,
 * breaking and the current consumption.
 * 
 */
public class DriveTechFragment extends Fragment {
	private Handler mHandler;

	private GearIndicator mGearIndicator;
	private ReferenceBar mRefBar;
	private SlideUpContainer mSlideUp;
	private TextView mInfoText;

	private static int mGear = 0;
	private static float mAcc = 0;
	private static float mRPM = 0;
	private static float mConsumption = 0;

	private TextView mFuelConsumptionNow;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_drive_tech,
				container, false);

		mFuelConsumptionNow = (TextView) rootView
				.findViewById(R.id.txtFuelConsumptionNow);

		mGearIndicator = (GearIndicator) rootView
				.findViewById(R.id.gearIndicator);

		mRefBar = (ReferenceBar) rootView.findViewById(R.id.referenceBar);
		mRefBar.setValue(0);
		mRefBar.setGradientColors(0xFFa3d618, 0xFFdb0f0f, 0xFFa3d618,
				0xFFdb0f0f);

		mHandler = new Handler() {

			public void handleMessage(Message msg) {
				Bundle data = msg.getData();

				if (data.containsKey("InstantaneousValuePerMilage")) {
					try {
						mConsumption = Float.parseFloat(data
								.getString("InstantaneousValuePerMilage"));
					} catch (NumberFormatException e) {
						Log.w("DriveTechFragment",
								"FuelConsumption: " + e.getMessage());
					}
					mFuelConsumptionNow.setText(String.format("%.1f",
							mConsumption) + " l/100km");
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
						Log.w("DriveTechFragment",
								"EngineSpeed: " + e.getMessage());
					}

					mGearIndicator.setRPM(mRPM);
				} else if (data.containsKey("CurrentGear")) {
					int oldGear = mGear;
					try {
						mGear = Integer.parseInt(data.getString("CurrentGear"));
					} catch (NumberFormatException e) {
						Log.w("DriveTechFragment", "Gear: " + e.getMessage());
					}
					if (oldGear < mGear) {
						if (1400 < mRPM && mRPM < 2000) {
							mGearIndicator.gearShift(true);
						} else {
							mGearIndicator.gearShift(false);
						}
					}
					mGearIndicator.setGear(mGear);

				} else if (data.containsKey("LongitudinalAcceleration")) {
					try {
						// value can be -20 to +20, bar goes from -100 to +100
						// therefore use *5 of the value
						// a normal car accelerates with 1.5 and max at 3
						// a normal car breaks with -3 and max at -10
						mAcc = (Float.parseFloat(data
								.getString("LongitudinalAcceleration")) * 5);
						if (mAcc < 0) {
							mAcc *= 2;
						} else {
							mAcc *= 5.5;
						}

					} catch (NumberFormatException e) {
						Log.w("DriveTechFragment",
								"Acceleration: " + e.getMessage());
					}

					mRefBar.setValue(mAcc);

				}
			}
		};

		CarData.getInstance().subscribeHandler(mHandler,
				"InstantaneousValuePerMilage");
		CarData.getInstance().subscribeHandler(mHandler, "EngineSpeed");
		CarData.getInstance().subscribeHandler(mHandler, "CurrentGear");
		CarData.getInstance().subscribeHandler(mHandler,
				"LongitudinalAcceleration");

		// SlideUpContainer
		mInfoText = ((TextView) rootView.findViewById(R.id.txtInfo));
		mSlideUp = ((SlideUpContainer) rootView.findViewById(R.id.infoSlideUp));

		((Button) rootView.findViewById(R.id.btnSlideUpClose))
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mSlideUp.slideClose();
					}
				});

		// Infos
		setupClickableInfo(mFuelConsumptionNow,
				"Der momentane Verbrauch in Litern pro 100 km.");
		setupClickableInfo(rootView.findViewById(R.id.imgFuelConsumptionNow),
				"Der momentane Verbrauch in Litern pro 100 km.");

		setupClickableInfo(
				mGearIndicator,
				"Hier wird der aktuelle Gang und die aktuelle Drehzahl angezeigt. Die Strich-Markierungen stehen für 1000er-Schritte. Der grüne Streifen am äußeren Rand gibt den optimalen Drehzahlbereich zum Schalten an. Beim Schalten leuchtet die Anzeige kurz grün oder rot auf, je nachdem ob sie im optimalen Drehzahlbereich geschaltet haben.");

		setupClickableInfo(
				mRefBar,
				"Diese Anzeige stellt das Beschleunigungs/Brems-Verhalten dar. Der farbige Ausschlag sollte so gering wie möglich sein.");

		return rootView;
	}

	/**
	 * adds an onClickListener to a view to provide an info text containing
	 * helpful information for the user about the clicked data visualizer.
	 * 
	 * @param view to add the listener to
	 * @param text which should be displayed on click
	 */
	private void setupClickableInfo(View view, final String text) {
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showInfo(text);
			}
		});
	}

	/**
	 * is called when the the user clicked on a data visualizer. it opens a SlideUpContainer
	 * containing the text to displayed
	 * @param text which should be display on click
	 */
	private void showInfo(String text) {
		mInfoText.setText(text);
		mSlideUp.slideOpen();
	}

	@Override
	public void onDestroyView() {
		boolean ret;
		ret = CarData.getInstance().unSubscribeHandler(mHandler,
				"InstantaneousValuePerMilage");
		Log.d("DTF UnsubscribeHandler", "" + ret);
		ret = CarData.getInstance().unSubscribeHandler(mHandler, "EngineSpeed");
		Log.d("DTF UnsubscribeHandler", "" + ret);
		ret = CarData.getInstance().unSubscribeHandler(mHandler, "CurrentGear");
		Log.d("DTF UnsubscribeHandler", "" + ret);
		ret = CarData.getInstance().unSubscribeHandler(mHandler,
				"LongitudinalAcceleration");
		Log.d("DTF UnsubscribeHandler", "" + ret);
		super.onDestroyView();
	}

}
