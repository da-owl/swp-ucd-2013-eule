package com.example.swp_ucd_2013_eule.car_data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.exlap.DataListener;
import de.exlap.DataObject;
import de.exlap.ExlapClient;
import de.exlap.ExlapException;

public class CarData implements DataListener {

	private ExlapClient mEC;
	private DataListener mDataListener = this;
	private List<String> mSubscribeItems;
	private HashMap<String, HashMap<String, String>> mReceivedData = new HashMap<String, HashMap<String, String>>();
	private Thread mConnectionWatcher;
	private boolean mRun = false;

	public void onData(DataObject dataObject) {

		// logging?
		String objectString = dataObject.toString();
		if (!objectString.contains(", no data elements!")) {
			int fst = objectString.indexOf("url=") + 4;
			int snd = objectString.indexOf("[E");
			String dataName = objectString.substring(fst, snd).trim();
			HashMap<String, String> map = mReceivedData.get(dataName);
			String[] data = objectString.substring(
					objectString.indexOf("name=")).split(",");
			if (map == null) {
				map = new HashMap<String, String>();
			}
			String key = data[0].substring(data[0].indexOf("=") + 1).trim();
			String value = data[3].substring(data[3].indexOf("=") + 1,
					data[3].indexOf("]")).trim();
			map.put(key, value);
			mReceivedData.put(dataName, map);
		}

		TESTONLYPRINTHASHMAP();

	}

	public void startService(final String address, List<String> subscribeItems) {
		mRun=true;
		mSubscribeItems = subscribeItems;

		if(mConnectionWatcher!=null){
			return;
		}
		mConnectionWatcher = new Thread(new Runnable() {
			public void run() {
				mEC = new ExlapClient(address);
				mEC.addDataListener(mDataListener);
				while (mRun) {
					if (!mEC.isConnected()) {
						mReceivedData.clear();
						System.out.println("trying to connect");
						while (!mEC.isConnected()) {
							mEC.connect();
							// TODO: abbruch Bedingung (4 mal versuchen oder so)
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
							}
						}
						subscribe();
					}

				}
			}
		});
		mConnectionWatcher.start();

	}

	private void subscribe() {
		try {
			// logging?
			for (String item : mSubscribeItems) {
				mEC.subscribeObject(item, 100);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void unsubscribe() throws IllegalArgumentException, IOException,
			ExlapException {
		// logging?
		for (String item : mSubscribeItems) {
			mEC.unsubscribeObject(item);
		}
	}

	public void endListener() throws IllegalArgumentException, IOException,
			ExlapException {
		mRun = false;
		try {
			mConnectionWatcher.join();
		} catch (InterruptedException e) {
		}
		unsubscribe();
		mEC.shutdown();
		mConnectionWatcher = null;
	}

	private long mLastRun = System.currentTimeMillis();

	private void TESTONLYPRINTHASHMAP() {
		if (System.currentTimeMillis() - mLastRun > (10000)) {
			for (String key : mReceivedData.keySet()) {
				HashMap<String, String> values = mReceivedData.get(key);
				System.out.println("Key: " + key);
				for (String item : values.keySet()) {
					System.out.println("\t item: " + item + ", value: "
							+ values.get(item));
				}
				System.out.println("------");
				mLastRun = System.currentTimeMillis();
			}
		}
	}

}
