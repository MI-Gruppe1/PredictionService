package service;

import com.google.gson.JsonArray;

public interface Communicator{
	
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp);
	public double getTemperatureAtTime(String stationsname, Long timeStamp);
	public JsonArray getPrediction(String stationsname);
	public double getWeatherConditionAtTime(String stationsname, Long timestamp);
	
}