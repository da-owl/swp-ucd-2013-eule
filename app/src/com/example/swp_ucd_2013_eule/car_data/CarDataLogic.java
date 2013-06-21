package com.example.swp_ucd_2013_eule.car_data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class CarDataLogic extends Handler {

	private static CarDataLogic CarDataLogicInstance = null;
	private volatile int mShifts = 0; // positiv gut, negativ schlecht
	private int mMaxRPM = 0; // innerhalb der ermittelten Zeit
	private volatile int mShiftCount = 0;
	private ArrayList<Float> mCurrentConsumptions = new ArrayList<Float>();
	private ArrayList<Float> mCurrentSpeed = new ArrayList<Float>();
	private volatile boolean mFastAcceleration = false;
	private volatile boolean mHardBreaking = false;
	private int mInterval = 150;
	private volatile float mCurPoints = 0;
	private HashMap<String, List<Handler>> mDataListeners = new HashMap<String, List<Handler>>();
	private float mPointsScaleFactor = 2;
	private float mCurrentRPM = 0;
	private int[] mRPMExceeding = { 0, 0, 0, 0 };
	private int mCurGear;
	private int mGoodShifts = 0;

	// for now static
	private float mCity = 7.5f;
	private float mCountry = 5.8f;
	private float mMotorWay = 5.2f;
	private int mGears = 6;
	private float m5percent = 0.1f;
	private float m10percent = 0.2f;
	private float m15percent = 0.3f;
	private float m20percent = 0.4f;

	private CarDataLogic() {
		CarData instance = CarData.getInstance();

		instance.subscribeHandler(this, "InstantaneousValuePerMilage");
		instance.subscribeHandler(this, "EngineSpeed");
		instance.subscribeHandler(this, "CurrentGear");
		// instance.subscribeHandler(this, "RecommendedGear");
		instance.subscribeHandler(this, "VehicleSpeed");
	}

	public static CarDataLogic getInstance() {
		if (CarDataLogicInstance == null) {
			CarDataLogicInstance = new CarDataLogic();
		}
		return CarDataLogicInstance;
	}

	public void handleMessage(Message msg) {
		Bundle data = msg.getData();

		if (data.containsKey("InstantaneousValuePerMilage")) {
			mCurrentConsumptions.add(Float.valueOf(data
					.getString("InstantaneousValuePerMilage")));
			System.out.println("Verbrauch: "
					+ data.getString("InstantaneousValuePerMilage"));
		} else if (data.containsKey("VehicleSpeed")) {
			mCurrentSpeed.add(Float.valueOf(data.getString("VehicleSpeed")));
			System.out.println("Geschwindigkeit: "
					+ data.getString("VehicleSpeed"));
		} else if (data.containsKey("EngineSpeed")) {
			System.out.println("RPM: "
					+ data.getString(data.getString("EngineSpeed")));
			
				try {
					mCurrentRPM = Float.parseFloat(data
							.getString("EngineSpeed"));
				} catch (NumberFormatException e) {
					System.out.println(e.getMessage());
				}
			
			if (mCurrentRPM > 4000) {
				mRPMExceeding[3]++;
			} else if (mCurrentRPM > 3000) {
				mRPMExceeding[2]++;
			} else if (mCurrentRPM > 2000) {
				mRPMExceeding[1]++;
			} else {
				mRPMExceeding[0]++;
			}

		} else if (data.containsKey("CurrentGear")) {
			System.out.println("Gang: " + data.getString("CurrentGear"));
			int oldGear = mCurGear;
			try {
				mCurGear = Integer.parseInt(data.getString("CurrentGear"));
			} catch (NumberFormatException e) {
				System.out.println(e.getMessage());
			}
			if (oldGear < mCurGear) {
				if (1600 < mCurrentRPM && mCurrentRPM < 2000) {
					mGoodShifts++;
				} else {
					mGoodShifts--;
				}
			}

		}

		if (mCurrentConsumptions.size() >= mInterval
				&& mCurrentSpeed.size() >= mInterval) {
			calculatePoints();
		}

	}

	private void calculatePoints() {
		/*
		 * Bewertung anhand der angegebenen Durchschnittsverbrauchwerte z.b.
		 * Golf 7 1.4TSI ACT S=5.8 /L=4.2 /A=4.7 6 Gang Schaltgetriebe
		 * Zeiteinheit definieren: 30 Sekunden ~ 150 Messwerte (alle 200ms)
		 * Durchschnitt des Momentanverbrauchs (ist schon auf l/100km normiert)
		 * Durchschnitt der Fahrgeschwindigt => Berechnen ob Stadt/Land/Autobahn
		 * Penaltyflag checken f�r Beschleunigung/Verz�gerung gr��er als GW
		 * errechneten Durchschnitsverbrauch f�r diese Fahrt in Klasse einordnen
		 * 20% drunter 10% drunter 0 10% dr�ber 20%dr�ber etc Schaltpunkte
		 * bewerten Drehzahl �berschreitungen bewerten
		 */
		ArrayList<Float> listConsum = new ArrayList<Float>();
		ArrayList<Float> listSpeed = new ArrayList<Float>();
		synchronized (mCurrentConsumptions) {
			listConsum.addAll(mCurrentConsumptions);
			mCurrentConsumptions.clear();
		}
		synchronized (mCurrentSpeed) {
			listSpeed.addAll(mCurrentSpeed);
			mCurrentConsumptions.clear();
		}
		int[] rpm = new int[] { mRPMExceeding[0], mRPMExceeding[1],
				mRPMExceeding[2], mRPMExceeding[3] };
		mRPMExceeding = new int[] { 0, 0, 0, 0 };
		Thread thread = new CalculationThread(listConsum, listSpeed, rpm,
				mGoodShifts);
		thread.start();
		mGoodShifts = 0;

	}

	/**
	 * 
	 * @param handler
	 *            which will be notified at a data event
	 * @param identifier
	 *            for the data to subscribe
	 * @return false if the listener is allready known for the given identifier
	 */
	public boolean subscribeHandler(Handler handler, String identifier) {
		List<Handler> list = mDataListeners.get(identifier);
		if (list == null) {
			list = new ArrayList<Handler>();
		} else if (list.contains(handler)) {
			return false;
		}
		list.add(handler);
		mDataListeners.put(identifier, list);
		return true;

	}

	private class CalculationThread extends Thread {
		private List<Float> mConsumptions;
		private List<Float> mSpeedList;
		private float mConsumption = 0f;
		private float mSpeed = 0f;
		private float mReferenceConsumption;
		private int[] mRPM;
		private int mShifts;

		public CalculationThread(List<Float> consumps, List<Float> speed,
				int[] rpm, int goodShifts) {
			mConsumptions = consumps;
			mSpeedList = speed;
			mRPM = rpm;
			mShifts = goodShifts;
		}

		@Override
		public void run() {
			for (int i = 0; i < mInterval; i++) {
				mSpeed += mSpeedList.get(i);
				mConsumption += mConsumptions.get(i);
			}
			mConsumption = mConsumption / mInterval;
			mSpeed = mSpeed / mInterval;
			if (mSpeed < 60) {
				mReferenceConsumption = mCity;
			} else if (mSpeed < 90) {
				mReferenceConsumption = mCountry;
			} else {
				mReferenceConsumption = mMotorWay;
			}

			float delta = mReferenceConsumption - mConsumption;
			float percent = Math.abs(delta) / mReferenceConsumption;
			float factor = m20percent;
			if (percent < 0.05f) {
				factor = m5percent;
			} else if (percent < 0.1f) {
				factor = m10percent;
			} else if (percent < 0.15f) {
				factor = m15percent;
			}

			if (delta > 0f) {
				// +Points
				mCurPoints += 1 * factor;
			} else {
				// -Points
				mCurPoints -= 1 * factor;
			}

			mCurPoints += ((float) mRPM[0] / mInterval) * 2;

			mCurPoints -= ((float) mRPM[1] / mInterval) * 1;

			mCurPoints -= ((float) mRPM[2] / mInterval) * 4;

			mCurPoints -= ((float) mRPM[3] / mInterval) * 8;

			if (mShifts < 0) {
				mCurPoints += (mShifts * 0.1);
			} else {
				mCurPoints += (mShifts * 0.2);
			}

			List<Handler> handlers = mDataListeners.get("pointProgress");
			if (handlers != null) {
				for (Handler handler : handlers) {
					Message msg = handler.obtainMessage();
					Bundle bundleData = new Bundle();
					bundleData.putFloat("pointProgress", mCurPoints
							* mPointsScaleFactor);
					msg.setData(bundleData);
					msg.sendToTarget();
				}
			}

			System.out.println("curent points: " + mCurPoints
					* mPointsScaleFactor);

		}

	}

}
