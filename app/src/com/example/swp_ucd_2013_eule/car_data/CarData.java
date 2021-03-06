package com.example.swp_ucd_2013_eule.car_data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import de.exlap.ConnectionConfiguration;
import de.exlap.DataListener;
import de.exlap.DataObject;
import de.exlap.ExlapClient;
import de.exlap.ExlapException;

/**
 * 
 * @author Marc
 * 
 *         CarData is a listener for the ExlapProxy. It also serves as a
 *         proposer to every class which subscribes for a specific car data. The
 *         connection to the proxy is maintained in a thread which is also
 *         responsible for a scheduled reconnect attempt if the connection was
 *         lost. This class is a singleton.
 * 
 */
public class CarData implements DataListener {

	private ExlapClient mEC;
	private DataListener mEXLAPListener = this;
	private List<String> mSubscribeItems;
	private HashMap<String, List<Handler>> mDataListeners = new HashMap<String, List<Handler>>();
	private volatile Thread mConnectionWatcher;
	private volatile boolean mRun = false;
	private static CarData INSTANCE = new CarData();
	private boolean mRecordTrip = false;

	/**
	 * private constructor used to make the class a singleton
	 */
	private CarData() {

	}

	/**
	 * 
	 * @return the instance of the class
	 */
	public static CarData getInstance() {
		return INSTANCE;
	}

	public void setRecordTrip(boolean state) {
		mRecordTrip = state;
	}

	/**
	 * is called from the ExlapProxy on new data. Extracts the data from the
	 * URL-object and sends the gathered information to all registered
	 * subscribers.
	 */
	public void onData(DataObject dataObject) {

		String objectString = dataObject.toString();

		if (!objectString.contains(", no data elements!")) {
			Log.d("CarData", "Got data!");
			// extracting values from the data
			String[] data = objectString.substring(
					objectString.indexOf("name=")).split(",");
			// key for the values of the data name
			// e.g. "FrontRight"
			String key = data[0].substring(data[0].indexOf("=") + 1).trim();
			// the actual value of the key
			// e.g. 42.6
			String value = data[3].substring(data[3].indexOf("=") + 1,
					data[3].indexOf("]")).trim();
			Log.d("CarData", "Data key: " + key + ", value: " + value);
			if (!value.equals("N.A.")) {
				List<Handler> handlers = mDataListeners.get(key);
				if (handlers != null) {
					Log.d("CarData", "notifying handlers");
					for (Handler handler : handlers) {
						Message msg = handler.obtainMessage();
						Bundle bundleData = new Bundle();
						bundleData.putString(key, value);
						msg.setData(bundleData);
						msg.sendToTarget();
					}
				}
			} else {
				Log.d("CarData", "value N.A. received" + key);
			}
		}

	}

	/**
	 * starts a Thread which will connect(and reconnect) to the given address
	 * and subscribe the given car data at the ExlapProxy.
	 * 
	 * @param address
	 *            Server Address "socket://192.168.0.40:28500"
	 * @param subscribeItems
	 *            A list of Strings containing the identifiers of the car data
	 *            to subscribe
	 */
	public void startService(final String address, List<String> subscribeItems) {
		mRun = true;
		mSubscribeItems = subscribeItems;
		// do not create a new Thread if there is already one running which
		// hasn't been stopped
		if (mConnectionWatcher != null) {
			return;
		}
		// create a new thread for connection and reconnect
		mConnectionWatcher = new Thread(new Runnable() {

			public void run() {
				ConnectionConfiguration config = new ConnectionConfiguration(
						address);
				config.setConnectTimeout(1000);
				mEC = new ExlapClient(config);
				mEC.addDataListener(mEXLAPListener);

				Log.i("CarData.ConnectionWatcher", "started");
				Log.i("CarData.ConnectionWatcher", address);
				while (mRun) {
					Log.d("CarData.ConnectionWatcher", "checking status");
					// check if the EXLAP-Client is connected
					if (!mEC.isConnected() && mRecordTrip) {
						Log.i("CarData.ConnectionWatcher", "not connected");
						// as long as there is no connection try to reconnect to
						// the server

						Log.d("CarData.ConnectionWatcher", "trying to connect");
						mEC.connect();

						// if a connection has been made, subscribe the data
						if (mEC.isConnected()) {
							Log.d("CarData.ConnectionWatcher", "subscribing");
							subscribe();
						} else {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
						}

					} else if (!mRecordTrip && !mEC.isConnected()) {
						Log.i("CarData.ConnectionWatcher",
								"disconnected and no connection needed");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					} else {
						Log.i("CarData.ConnectionWatcher", "connected");
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					}

				}// while end
			}
		});
		// start the thread
		mConnectionWatcher.start();

	}

	/**
	 * goes through the provided list and subscribe the containing
	 * data-identifier
	 */
	private void subscribe() {
		try {
			for (String item : mSubscribeItems) {
				mEC.subscribeObject(item, 100);
			}
		} catch (Exception e) {
			Log.e("CarData", "failed to subscribe listener: " + e.getMessage());
		}
	}

	/**
	 * unsubscribes all previously subscribed data-identifiers
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ExlapException
	 */
	private void unsubscribe() throws IllegalArgumentException, IOException,
			ExlapException {
		// logging?
		for (String item : mSubscribeItems) {
			mEC.unsubscribeObject(item);
		}
	}

	/**
	 * terminates the connection to the ExlapProxy and the connection watcher
	 * thread
	 * 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ExlapException
	 */
	public void endListener() throws IllegalArgumentException, IOException,
			ExlapException {
		Log.d("CarData", "ending listener");
		mRun = false;
		try {
			mConnectionWatcher.join();

		} catch (InterruptedException e) {
			Log.w("CarData", "coudln't end ConectionWatcher: " + e.getMessage());
		}
		Log.d("CarData", "ConnectionWatcher terminated");
		try {
			unsubscribe();
		} catch (Exception e) {
			Log.i("CarData",
					"failed to unsubscribe ConnectionWatcher: "
							+ e.getMessage());
		}
		Log.d("CarData", "listener unsubscribed");
		mEC.shutdown();
		Log.d("CarData", "listener shutdown");
		mConnectionWatcher = null;
		Log.d("CarData", "listener terminated");
	}

	/**
	 * every class can subscribe here for car data if it implements a standard
	 * Android Handler Possible identifiers are: VehicleSpeed, TripOdometer,
	 * RecommendedGear, Odometer, LongitudinalAcceleration, LateralAcceleration,
	 * FuelConsumption, EngineSpeed, CurrentGear
	 * 
	 * @param handler
	 *            which will be notified at a data event
	 * @param identifier
	 *            for the car data to subscribe
	 * @return false if the listener is already known for the given identifier
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

	/**
	 * is called to unsubscribe a previously added Handler
	 * 
	 * @param handler
	 *            which was added to the subscribers
	 * @param identifier
	 *            for the car data to subscribe
	 * @return true if the handler was successful removed
	 */
	public boolean unSubscribeHandler(Handler handler, String identifier) {
		List<Handler> handlerList = mDataListeners.get(identifier);
		if (handlerList != null) {
			return handlerList.remove(handler);
		} else {
			return false;
		}
	}

}
