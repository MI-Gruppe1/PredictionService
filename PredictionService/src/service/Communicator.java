package service;

public interface Communicator{
	
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp);
	public double getTemperatureAtTime(String name, Long timeStamp);
	public double getWeatherAtTime(String name, Long timeStamp);
	
}