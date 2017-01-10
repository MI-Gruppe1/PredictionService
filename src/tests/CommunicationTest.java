package tests;

import static org.junit.Assert.assertEquals;
import static spark.Spark.get;

import org.junit.Test;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
	
	@Test
	public void Test(){
		System.out.println("start testing");
		startTestRestApi();
		
		System.out.println("testfreebikes - freebikes");
		
		communication = new Communication(WeatherDBServerAdress,StadtradDBServerAdress);
		System.out.println("created Testsetting");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		testlauf();
		
	}
	
	private void testlauf(){
//		testPrediction();
//		testFreebikesofStationAtSpecTime();
//		testTemperatureAtTime();
//		testWeatherConditionAtTime();
	}
	
	private void all(){
		String stationNameCall = "test1";
		String timeStampCall = "12345";
		String stationName = "test1";
		String timeStamp = "12345";
		
		double temp[] = { 10, 10, 11, 30 };
		double prec[] = { 0.5, 0.5, 0.4, 0 };
		int bikes[] = { 10, 10, 12, 10, 9, 30, 30, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		
		Integer returnFreeBikes = 10;
		//Gson returnAllStations = [{ "name": "2397 Alsterdorfer Straße/Fuhlsbüttler Straße", "latitude": 53.62,"longitude": 10.032 }];
		double returnTemperaturAtSpecificTime = 10.5;
		double returnWeatherConditionAtSpecificTime = 0.5;
		
		
		try {
			HttpResponse<String> stringResponse = Unirest.get("http://localhost:3000" + "/predictionService")
					.queryString("name", stationNameCall).asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void testPrediction(){
		String stationNameCall = "test1";
		String timeStampCall = "12345";
		String stationName = "test1";
		String timeStamp = "12345";
		
		double temp[] = { 10, 10, 11, 30 };
		double prec[] = { 0.5, 0.5, 0.4, 0 };
		int bikes[] = { 10, 10, 12, 10, 9, 30, 30, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		
		Integer returnFreeBikes = 10;
		Gson returnAllStations = new Gson();
		double returnTemperaturAtSpecificTime = 10.5;
		double returnWeatherConditionAtSpecificTime = 0.5;
		
		System.out.println(communication.getPrediction(stationNameCall));
	}
	
	private void testTemperatureAtTime(){
		String timeStampCall = "12345";
		String stationNameCall = "test1";
		
		communication.getTemperatureAtTime(stationNameCall, Long.valueOf(timeStampCall));
	}
	
	private void testWeatherConditionAtTime(){
		communication.getWeatherConditionAtTime(stationNameCall, Long.valueOf(timeStampCall));
	}
	
	public void testFreebikesofStationAtSpecTime(){
		Integer result = 0;
		
		//freebikes null
		String stationNameCall = "test1";
		String timeStampCall = "12345";
		String stationName = "test1";
		String timeStamp = "12345";
		
		
		//freebikes = 0
		returnFreeBikes = 0;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		System.out.println("testfreebikes - freebikes = 0");
		
		//freebikes > 0
		returnFreeBikes = 100;
		result = 100;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		System.out.println("testfreebikes - freebikes > 0");		
		//freebikes -1
		returnFreeBikes = -1;
		result = -1;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		System.out.println("testfreebikes - freebikes = -1");
		
		//freebikes <0
		returnFreeBikes = -100;
		result = -1;
		communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall));
		System.out.println("testfreebikes - freebikes < 0");
		//freebikes null
		returnFreeBikes = null;
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(timeStampCall)));
		
		System.out.println("testfreebikes - freebikes = null");
		//wrong stationname
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime("abc", Long.valueOf(timeStampCall)));
		//wrong time
		assertEquals(result, communication.getFreeBikesofStationAtSpecTime(stationNameCall, Long.valueOf(1)));
		//no response
		
		//wrong datatype
		System.out.println("testfreebikes - done");
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
