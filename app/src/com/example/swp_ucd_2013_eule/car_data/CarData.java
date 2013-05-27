package com.example.swp_ucd_2013_eule.car_data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.exlap.DataListener;
import de.exlap.DataObject;
import de.exlap.ExlapClient;
import de.exlap.ExlapException;

/**
 * 
 * @author Marc
 * 
 * CarData provides a listener for the EXLAP-Proxy which stores
 * the subscribed data in a HashMap. It also auto-reconnect
 * to the given EXLAP-Proxy address if the connection is lost.
 *
 */
public class CarData implements DataListener {

	private ExlapClient mEC;
	private DataListener mDataListener = this;
	private List<String> mSubscribeItems;
	private HashMap<String, HashMap<String, String>> mReceivedData = new HashMap<String, HashMap<String, String>>();
	private Thread mConnectionWatcher;
	private boolean mRun = false;
	private long mLastRun = System.currentTimeMillis();


	/**
	 * Receives and stores the subscribed data it which is a url object wrapped into the 
	 * DataObject.
	 */
	public void onData(DataObject dataObject) {

		// logging?
		String objectString = dataObject.toString();
		
		if (!objectString.contains(", no data elements!")) {
			//getting the name of the received data 
			int fst = objectString.indexOf("url=") + 4;
			int snd = objectString.indexOf("[E");
			String dataName = objectString.substring(fst, snd).trim();
			//looking if data with this name was already in the HashMap otherwise create a new HashMap
			// e.g. "WheelSpeed"
			HashMap<String, String> map = mReceivedData.get(dataName);
			if (map == null) {
				map = new HashMap<String, String>();
			}
			//extracting values from the data
			String[] data = objectString.substring(
					objectString.indexOf("name=")).split(",");
			//key for the values of the data name
			//e.g. "FrontRight"
			String key = data[0].substring(data[0].indexOf("=") + 1).trim();
			//the actual value of the key
			//e.g. 42.6
			String value = data[3].substring(data[3].indexOf("=") + 1,
					data[3].indexOf("]")).trim();
			// storing values under the name of the data
			map.put(key, value);
			mReceivedData.put(dataName, map);
		}
		// calling a method to print the content of the HashMap for testing purposes
		TESTONLYPRINTHASHMAP();

	}

	/**
	 * starts a Thread which will connect(and reconnect) to the given address and subscribe to all data which
	 * is provided in the list. 
	 * @param address Server Address "socket://192.168.0.40:28500"
	 * @param subscribeItems A list of Strings containing the names of data to subscribe
	 */
	public void startService(final String address, List<String> subscribeItems) {
		mRun=true;
		mSubscribeItems = subscribeItems;
		// do not create a new Thread if theres allready one running which hasn't been stopped
		if(mConnectionWatcher!=null){
			return;
		}
		// create a new thread for connection and reconnect
		mConnectionWatcher = new Thread(new Runnable() {
			
			public void run() {
				mEC = new ExlapClient(address);
				mEC.addDataListener(mDataListener);
				
				while (mRun) {
					// check if the EXLAP-Client is connected
					if (!mEC.isConnected()) {
						// if the connection is lost, reset the HashMap 
						mReceivedData.clear();
						//logging?
						//as long as there is no connection try to reconnect to the server
						while (!mEC.isConnected()) {
							mEC.connect();
							// TODO: abbruch Bedingung (4 mal versuchen oder so??)
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
							}
						}
						// if a connection has been made, subscribe the data
						subscribe();
					}

				}
			}
		});
		//start the thread
		mConnectionWatcher.start();

	}
	
	/**
	 * goes through the provided list and subscribe the containing data-identifier
	 */
	private void subscribe() {
		try {
			// logging?
			for (String item : mSubscribeItems) {
				mEC.subscribeObject(item, 100);
			}
		} catch (Exception e) {
			//logging?
			System.out.println(e.getMessage());
		}
	}
	/**
	 * unsubscribes all previously subscribed data-identifier
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
	 * terminates the connection and the connection watcher thread
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws ExlapException
	 */
	public void endListener() throws IllegalArgumentException, IOException,
			ExlapException {
		mRun = false;
		try {
			mConnectionWatcher.join();
		} catch (InterruptedException e) {
			//logging?
		}
		//logging?
		unsubscribe();
		mEC.shutdown();
		mConnectionWatcher = null;
		//logging?
	}
		
	/**
	 * returns a HashMap with the latest received data for a given data identifier of null.
	 * @param identifier of the data
	 * @return HashMap<String,String> or null
	 */
	public HashMap<String,String> getDataHashMap(String identifier){
		return mReceivedData.get(identifier);
	}

	/**
	 * prints the content of the HashMap every 10 seconds
	 */
	private void TESTONLYPRINTHASHMAP() {
		if (System.currentTimeMillis() - mLastRun > (10000)) {
			//getting through data-identifiers
			for (String key : mReceivedData.keySet()) {
				HashMap<String, String> values = mReceivedData.get(key);
				System.out.println("Key: " + key);
				//getting through the data names and data values
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
