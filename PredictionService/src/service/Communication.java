package service;


import static spark.Spark.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import spark.QueryParamsMap;

interface Communicator{
	
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp);
	public double getTemperatureAtTime(String stationsname, Long timeStamp);
	public double getWeatherAtTime(String stationsname, Long timeStamp);
	public JSONObject getPrediction(String stationsname);
	public double getWeatherConditionAtTime(String stationsname, Long timestamp);
	
}
public class Communication implements Communicator {

	Predictor prediction;
	Map<String,Double> latMap = new HashMap<String,Double>();
	Map<String,Double> longMap = new HashMap<String,Double>();
	String WeatherDBServerAdress = "http://localhost:4567";
	String StadtradDBServerAdress = "http://localhost:4567";
	
	public Communication(){
		prediction = new Predictor(this);
		
		updateStations();
	}
	
	private void updateStations(){
		List<Map<String, String>> allStations = getAllStations();
		Map<String, Double> tmpLatMap = new HashMap<String, Double>();
		Map<String, Double> tmpLongMap = new HashMap<String, Double>();
		for(Map<String,String> station : allStations){
			
			tmpLatMap.put(station.get("name"), Double.valueOf(station.get("latitude")));
			tmpLongMap.put(station.get("name"), Double.valueOf(station.get("longitude")));
		}
		latMap = tmpLatMap;
		longMap = tmpLongMap;
	}
	
	public JSONObject getPrediction(String stationsname){
		return createJsonArrayFromDoubleArray(prediction.predict(stationsname, 5));
	}



//    
//	/**
//	 * This method requests the current amount of free Bikes of a specific Station from the StadtRadDBService.
//	 * 
//	 * @param name of the Station.
//	 * 
//	 * @return returns amount of free bikes
//	 */
//    public Integer getFreeBikesOfStation(String stationName){
//    	Integer result = 0;
//    	try {
//    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/freeBikesOfStation")
//			.queryString("station_name", stationName)
//			.asString();
//    		
//    		// stringResponse.getBody().toString() ---- Integer als String?
//    		result = Integer.valueOf(stringResponse.getBody().toString());
//		} catch (UnirestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	return result;
//    }
//    
    
    


    
//	/**
//	 * This method requests the current Weather from the WeatherService.
//	 * 
//	 * @param ?.
//	 * 
//	 * @return returns 
//	 */
//    public Integer weatherAtTime(String name, String timeStamp){
//    	Integer result = 0;
//    	try {
//    		time long lat
//    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/currentWeather").asString();
//    		
//    		// result = stringResponse.getBody().toString();
//		
//    	} catch (UnirestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	return result;
//    }
//    
//    
//    public JSONArray testRequest() {
//    	JSONArray result = new JSONArray();
//    	try {
//    		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/predictionService")
//			.queryString("name", "Mark")
//			.asJson();
//    		result = jsonResponse.getBody().getArray();
//		} catch (UnirestException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	return result;
//    }


	/**
	 * This method requests the amount of free Bikes of a specific Station at a specific Time from the StadtRadDBService.
	 * 
	 * @param name of the Station and TimeStamp.
	 * 
	 * @return returns amount of free bikes
	 */
	@Override
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp) {
		Integer result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(StadtradDBServerAdress + "/freeBikesofStationAtSpecTime")
			.queryString("station_name", stationName)
			.queryString("information_timestamp", timeStamp).asString();
    		
    		
    		// stringResponse.getBody().toString() ---- Integer als String?
    		result = Integer.valueOf(stringResponse.getBody().toString());
		
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
	}


	@Override
	public double getWeatherAtTime(String name, Long timeStamp) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getWeatherConditionAtTime(String stationsname, Long timeStamp) {
		double result = 0;
    	
		try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/freeBikesofStationAtSpecTime")
			.queryString("time", timeStamp)
			.queryString("long", getLongitude(stationsname))
			.queryString("lat", getLatitude(stationsname)).asString();
   
    		result = Double.valueOf(stringResponse.getBody().toString());
		
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
	}
	
	/**
	 * This method requests the temperature at a specific time from the WeatherService.
	 * 
	 * @param ?.
	 * 
	 * @return returns 
	 */

	@Override
	public double getTemperatureAtTime(String stationsname, Long timeStamp) {
	   	double result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/temperatureAtTime")
    				.queryString("time", timeStamp)
    				.queryString("long", getLongitude(stationsname))
    				.queryString("lat", getLatitude(stationsname)).asString();
    		
    		result = Double.valueOf(stringResponse.getBody().toString());
		
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
	}
	
	private double getLongitude(String stationsname){
		return longMap.get(stationsname);
	}
	
	private double getLatitude(String stationsname){
		return  latMap.get(stationsname);
	}

	/**
	 * This method creates a JSONObject out of a array of doubles
	 * 
	 * @param Array of doubles.
	 * 
	 * @return returns JSONObject for array of doubles
	 */
    private static JSONObject createJsonArrayFromDoubleArray(double[] doubleArray){
    	JSONObject jObject = new JSONObject();
    	
    	jObject.put("doubles", doubleArray);
    	return jObject;
    }
    
    private List<Map<String, String>> getAllStations(){
    	Gson gson = new Gson();
    	List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
    	HttpResponse<String> response;
		try {
			response = Unirest.get(StadtradDBServerAdress + "/allStations").asString();
			// Speichern des Jsons aus dem Requestbody
			String json = response.getBody();

			// Format fuer das umwandeln jsons in ein Javaobjekt festelegen
			Type type = new TypeToken<List<Map<String, String>>>() {
			}.getType();

			// Aus dem Json ein Javaobjekt erstellen
			itemsList = gson.fromJson(json, type);

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemsList;	
    }
    
}
