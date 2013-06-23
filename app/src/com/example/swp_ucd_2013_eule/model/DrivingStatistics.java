package com.example.swp_ucd_2013_eule.model;

import java.util.LinkedList;

/**
 * 
 * @author Marc
 * 
 */
public class DrivingStatistics extends Model {


	private Integer mGainedPoints=0;

	private LinkedList<Float> mConsumptions = new LinkedList<Float>();

	private Integer mDataInterval;

	private Float mTripConsumption;

	public DrivingStatistics(Integer id) {
		this.id = id;
	}

	public DrivingStatistics(Integer id, Integer interval) {
		this.id = id;
		mDataInterval = interval;
	}

	public void setDataInterval(Integer interval) {
		mDataInterval = interval;
	}


	public void addGainedPoint() {
		mGainedPoints++;
	}
	
	public void removeGainedPoint(){
		mGainedPoints--;
	}

	public Integer getGainedPoints() {
		return mGainedPoints;
	}

	public void setConsumption(Float consumption) {
		mConsumptions.add(consumption);
	}

	public Float calculateTripConsumption() {
		int values = mConsumptions.size();
		for (Float f : mConsumptions) {
			mTripConsumption += f;
		}
		mTripConsumption /= values;
		return mTripConsumption;
	}

}
