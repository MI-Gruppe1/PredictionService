package tests;

import static org.junit.Assert.assertEquals;
import static spark.Spark.get;

import org.junit.Test;

import com.google.gson.Gson;

import service.Communication;
import service.Communicator;
import service.PredictionService;


public class CommunicationTest {
	String WeatherDBServerAdress = "http://localhost:4567";
	String StadtradDBServerAdress = "http://localhost:4567";
	Communicator communication;
	
	Integer returnFreeBikes;
	Gson returnAllStations = new Gson();
	double returnTemperaturAtSpecificTime;
	double returnWeatherConditionAtSpecificTime;
	
	String stationNameCall = "";
	String timeStampCall = "";
	String stationName = "";
	String timeStamp = "";
	
	public void Test(){
		startTestRestApi();
		communication = new Communication(WeatherDBServerAdress,StadtradDBServerAdress);
		testlauf();
	}
	
	private void testlauf(){
		testPrediction();
		testFreebikesofStationAtSpecTime();
		testTemperatureAtTime();
		testWeatherConditionAtTime();
	}
	
	private void testPrediction(){
		
		
		communication.getPrediction(stationNameCall);
	}
	
	private void testTemperatureAtTime(){
		communication.getTemperatureAtTime(stationNameCall, Long.valueOf(timeStampCall));
	}
	
	private void testWeatherConditionAtTime(){
		communication.getWeatherConditionAtTime(stationNameCall, Long.valueOf(timeStampCall));
	}
	
	@Test
	public void testFreebikesofStationAtSpecTime(){
		Integer result = 1;
		
		//freebikes null
		String stationNameCall = "test1";
		String timeStampCall = "12345";
		String stationName = "test1";
		String timeStamp = "12345";
		
		
		//freebikes = 0
		returnFreeBikes = 0;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		
		//freebikes > 0
		returnFreeBikes = 100;
		result = 100;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		
		//freebikes -1
		returnFreeBikes = -1;
		result = -1;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		
		//freebikes <0
		returnFreeBikes = -100;
		result = -1;
		communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall));
		
		//freebikes null
		returnFreeBikes = null;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		
		//wrong stationname

		//wrong time
		
		//no response
		
		//wrong datatype
	}
	
	
	
//	private double[] requestPrediction(String stationName, String timeStamp) throws UnirestException{
//		double[] result = {0,0,0,0,0};
//		HttpResponse<JsonNode> gsonResponse = Unirest.get(StadtradDBServerAdress + "/freeBikesofStationAtSpecTime")
//				.queryString("station_name", stationName)
//				.queryString("information_timestamp", timeStamp).asJson();
//		for(int i = 0; i<=4 ;i++){
//			result[i] = gsonResponse.getBody().getArray().getDouble(i);
//		}
//		return result;
//	}
	
	
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
