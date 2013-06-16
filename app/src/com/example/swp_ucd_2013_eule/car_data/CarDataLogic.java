package com.example.swp_ucd_2013_eule.car_data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarDataLogic implements CarDataListener {
	private static CarDataLogic CarDataLogicInstance = null;
	private volatile int mShifts = 0; // positiv gut, negativ schlecht
	private int mMaxRPM = 0; // innerhalb der ermittelten Zeit
	private volatile int mShiftCount = 0;
	private ArrayList<Float> mCurrentConsumptions = new ArrayList<Float>();
	private ArrayList<Float> mCurrentSpeed = new ArrayList<Float>();
	private volatile boolean mFastAcceleration = false;
	private volatile boolean mHardBreaking = false;
	private int mInterval = 150;
	private volatile float mCurPoints=0;
	// for now static
	private float mCity = 5.8f;
	private float mCountry = 4.2f;
	private float mMotorWay = 4.7f;
	private int mGears = 6;
	private float m5percent =0.1f;
	private float m10percent =0.2f;
	private float m15percent =0.3f;
	private float m20percent =0.4f;
	

	private CarDataLogic() {
		CarData instance = CarData.getInstance();

		instance.subscribeListener(this, "InstantaneousValuePerMilage");
		// instance.subscribeListener(this, "EngineSpeed");
		// instance.subscribeListener(this, "CurrentGear");
		// instance.subscribeListener(this, "RecommendedGear");
		instance.subscribeListener(this, "VehicleSpeed");
	}

	public static void init() {
		if (CarDataLogicInstance == null) {
			CarDataLogicInstance = new CarDataLogic();
		}
	}

	@Override
	public void handleCarData(String name, String value) {
		if (name.equals("InstantaneousValuePerMilage")) {
			mCurrentConsumptions.add(Float.valueOf(value));
		}
		if (name.equals("VehicleSpeed")) {
			mCurrentSpeed.add(Float.valueOf(value));
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
		 * Penaltyflag checken für Beschleunigung/Verzögerung größer als GW
		 * errechneten Durchschnitsverbrauch für diese Fahrt in Klasse einordnen
		 * 20% drunter 10% drunter 0 10% drüber 20%drüber etc Schaltpunkte
		 * bewerten Drehzahl Überschreitungen bewerten
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
		Thread thread = new CalculationThread(listConsum, listSpeed);
		thread.start();

	}

	private class CalculationThread extends Thread {
		private List<Float> mConsumptions;
		private List<Float> mSpeedList;
		private float mConsumption = 0f;
		private float mSpeed = 0f;
		private float mReferenceConsumption;

		public CalculationThread(List<Float> consumps, List<Float> speed) {
			mConsumptions = consumps;
			mSpeedList = speed;
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
			float percent = Math.abs(delta)/mReferenceConsumption;
			float factor = m20percent;
			if(percent < 0.05f){
				factor = m5percent;
			}else if(percent <0.1f){
				factor = m10percent;
			}else if(percent < 0.15f){
				factor = m15percent;
			}
			
			if(delta > 0f){
				//+Points
				mCurPoints += 1*factor;
			}else{
				//-Points
				mCurPoints -= 1*factor;
			}
			
			System.out.println("curent points: "+mCurPoints);
			
		}

	}

}
