package com.example.swp_ucd_2013_eule.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Marc
 * 
 */
public class Statistic extends Model {


	private Integer gainedPoints = 0;

	private LinkedList<Float> consumptions = new LinkedList<Float>();

	private Integer dataInterval;

	private Float tripConsumption;
	
	public Statistic(){
		
	}
	
	public Statistic(Integer id) {
		this.id = id;
	}

	public Statistic(Integer id, Integer interval) {
		this.id = id;
		dataInterval = interval;
	}

	public void setDataInterval(Integer interval) {
		dataInterval = interval;
	}


	public void addGainedPoint() {
		gainedPoints++;
	}
	
	public void removeGainedPoint(){
		gainedPoints--;
	}

	public Integer getGainedPoints() {
		return gainedPoints;
	}
	
	public void setConsumptions(String consList) {
		List<String> cons = Arrays.asList(consList.split(","));
		for (String string : cons) {
			consumptions.add(Float.valueOf(string));
		}
	}
	
	public String getConsumptions() {
		String ret = "";
		for (Float value : this.consumptions) {
			ret += value + ",";
		}
		return ret;
	}

	public void setConsumption(Float consumption) {
		consumptions.add(consumption);
	}	

	public Float getTripConsumption() {
		return tripConsumption;
	}

	public void setTripConsumption(Float tripConsumption) {
		this.tripConsumption = tripConsumption;
	}
	
	public void setTripConsumption(Double tripConsumption) {
		this.tripConsumption = new Float(tripConsumption.floatValue());
	}
	
	

	public Integer getDataInterval() {
		return dataInterval;
	}

	public void setGainedPoints(Integer gainedPoints) {
		this.gainedPoints = gainedPoints;
	}

	public Float calculateTripConsumption() {
		int values = consumptions.size();
		for (Float f : consumptions) {
			tripConsumption += f;
		}
		tripConsumption /= values;
		return tripConsumption;
	}

	@Override
	public String toString() {
		return "Statistic [gainedPoints=" + gainedPoints + ", consumptions="
				+ consumptions + ", dataInterval=" + dataInterval
				+ ", tripConsumption=" + tripConsumption + "]";
	}
	
	

}
