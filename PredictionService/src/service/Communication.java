package service;


import static spark.Spark.get;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import spark.QueryParamsMap;

interface Communicator{
	
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp);
	public double getTemperatureAtTime(String name, Long timeStamp);
	public double getWeatherAtTime(String name, Long timeStamp);
	public JSONObject getPrediction(String stationsname);
	
}
public class Communication implements Communicator {

	Predictor prediction;
	
	public Communication(){
		prediction = new Predictor(this);
	}
	
	public JSONObject getPrediction(String stationsname){
		return createJsonArrayFromDoubleArray(prediction.predict(stationsname, 5));
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
    	
    	jObject.put("doubles",doubleArray);
    	return jObject;
    }
    
	/**
	 * This method requests the current amount of free Bikes of a specific Station from the StadtRadDBService.
	 * 
	 * @param name of the Station.
	 * 
	 * @return returns amount of free bikes
	 */
    public Integer getFreeBikesOfStation(String stationName){
    	Integer result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/freeBikesOfStation")
			.queryString("station_name", stationName)
			.asString();
    		
    		// stringResponse.getBody().toString() ---- Integer als String?
    		result = Integer.valueOf(stringResponse.getBody().toString());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    
    
	/**
	 * This method requests the amount of free Bikes of a specific Station at a specific Time from the StadtRadDBService.
	 * 
	 * @param name of the Station and TimeStamp.
	 * 
	 * @return returns amount of free bikes
	 */
    public Integer getFreeBikesofStationAtSpecTime(String stationName, String timeStamp){
    	Integer result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/freeBikesofStationAtSpecTime")
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
    
	/**
	 * This method requests the temperature at a specific time from the WeatherService.
	 * 
	 * @param ?.
	 * 
	 * @return returns 
	 */
    public double getTemperatureAtTime(String name, String timeStamp){
    	double result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/temperatureAtTime").asString();
    		
    		result = Double.valueOf(stringResponse.getBody().toString());
		
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }    
    public double getTemperatureAtTime(String name, Long timeStamp){
    	return getTemperatureAtTime(name, timeStamp.toString());
    }
    
    
	/**
	 * This method requests the current Weather from the WeatherService.
	 * 
	 * @param ?.
	 * 
	 * @return returns 
	 */
    public Integer getWeatherAtTime(String name, String timeStamp){
    	Integer result = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get("http://localhost:4567" + "/currentWeather").asString();
    		
    		// result = stringResponse.getBody().toString();
		
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result;
    }
    
    
    public JSONArray testRequest() {
    	JSONArray result = new JSONArray();
    	try {
    		HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:4567/predictionService")
			.queryString("name", "Mark")
			.asJson();
    		result = jsonResponse.getBody().getArray();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return result;
    }



	@Override
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public double getWeatherAtTime(String name, Long timeStamp) {
		// TODO Auto-generated method stub
		return 0;
	}



	
	
	
}
