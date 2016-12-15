package service;

import org.json.JSONObject;

public interface Communicator{
	
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp);
	public double getTemperatureAtTime(String stationsname, Long timeStamp);
	public JSONObject getPrediction(String stationsname);
	public double getWeatherConditionAtTime(String stationsname, Long timestamp);
	
}