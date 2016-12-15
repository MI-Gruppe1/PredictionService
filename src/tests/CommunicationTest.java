package tests;

import static spark.Spark.get;

import org.junit.Test;

import com.google.gson.Gson;

import service.Communication;
import service.Communicator;


public class CommunicationTest {
	String WeatherDBServerAdress = "http://localhost:4567";
	String StadtradDBServerAdress = "http://localhost:4567";
	Communicator communicator;
	
	int returnFreeBikes;
	Gson returnAllStations;
	double returnTemperaturAtSpecificTime;
	double returnWeatherConditionAtSpecificTime;
	
	@Test
	public void Test(){
		startTestRestApi();
		communicator = new Communication(WeatherDBServerAdress,StadtradDBServerAdress);
		
	}
	
	private void startTestRestApi(){
		// defines REST Api for predictionService
		get("/temperatureAtTime", (req, res) -> {
			Long.valueOf(req.queryParams("time"));
			Double.parseDouble(req.queryParams("lon"));
			Double.parseDouble(req.queryParams("lat"));
			
			return returnTemperaturAtSpecificTime;
        });
		
		// defines REST Api for predictionService
		get("/weatherConditionAtTime", (req, res) -> {
			Long.valueOf(req.queryParams("time"));
			Double.parseDouble(req.queryParams("lon"));
			Double.parseDouble(req.queryParams("lat"));
			
			return returnWeatherConditionAtSpecificTime;
        });
		
		// defines REST Api for predictionService
		get("/freeBikesofStationAtSpecTime", (req, res) -> {
			String Stationname = req.queryParams("station_name");
			String timeStamp = req.queryParams("information_timestamp");
			
			return returnFreeBikes;
        });
		
		// defines REST Api for predictionService
		get("/allStations", (req, res) -> {
			String Stationname = req.queryParams("station_name");
			
        	return returnAllStations;
        });
	}
}
