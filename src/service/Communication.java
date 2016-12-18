package service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InvalidAttributeValueException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Communication implements Communicator {

	Prediction prediction;
	Map<String,Double> latMap = new HashMap<String,Double>();
	Map<String,Double> longMap = new HashMap<String,Double>();
	String WeatherDBServerAdress = "http://WeatherDBService:4568";
	String StadtradDBServerAdress = "http://stadtraddbservice:6000";
	
	public Communication(){
		prediction = new Prediction(this);
	}
	
	public Communication(String weatherDBServerAdress, String stadtradDBServerAdress){
		this.WeatherDBServerAdress = weatherDBServerAdress;
		this.StadtradDBServerAdress = stadtradDBServerAdress;
		prediction = new Prediction(this);
		
		updateStations();
	}
	
	private void updateStations(){
		List<Map<String, String>> allStations = getAllStations();
		Map<String, Double> tmpLatMap = new HashMap<String, Double>();
		Map<String, Double> tmpLongMap = new HashMap<String, Double>();
		if(allStations.size()>0){
			for(Map<String,String> station : allStations){
				tmpLatMap.put(station.get("name"), Double.valueOf(station.get("latitude")));
				tmpLongMap.put(station.get("name"), Double.valueOf(station.get("longitude")));
			}			
		}
		latMap = tmpLatMap;
		longMap = tmpLongMap;
	}
	
	public JsonArray getPrediction(String stationsname){
		double[] predict = {0,0,0,0,0};
		if(longMap.isEmpty() && latMap.isEmpty()){
			updateStations();
		}
		try {
			predict = prediction.predict(stationsname, 5);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			MailNotification.sendMail(e);
		}
		return createJsonArrayFromDoubleArray(predict);
	}


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
		Integer tmp = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(StadtradDBServerAdress + "/freeBikesofStationAtSpecTime")
			.queryString("station_name", stationName)
			.queryString("information_timestamp", timeStamp).asString();
    		
    		if(!stringResponse.equals(null) && !stringResponse.getBody().isEmpty()){
    				tmp = Integer.valueOf(stringResponse.getBody().toString());
    				if(tmp >= 0){
    					result = tmp;
    				}
    				else {
    					result = -1;
    				}
    		}
    		else {
    			result = -1;
    		}
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
		}
    	
    	return result;
	}


	@Override
	public double getWeatherConditionAtTime(String stationsname, Long timeStamp) {
		double result = 0;
    	double tmp = 0;
		try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/weatherConditionAtTime")
			.queryString("time", timeStamp)
			.queryString("long", getLongitude(stationsname))
			.queryString("lat", getLatitude(stationsname)).asString();
   
    		if(!stringResponse.equals(null) && !stringResponse.getBody().isEmpty()){
    				tmp = Double.valueOf(stringResponse.getBody().toString());
    				if(tmp >= 0){
    					result = tmp;
    				}
    				else {
    					result = -1;
    				}
    		}
    		else {
    			result = -1;
    		}
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
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
	   	double tmp = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/temperatureAtTime")
    				.queryString("time", timeStamp)
    				.queryString("long", getLongitude(stationsname))
    				.queryString("lat", getLatitude(stationsname)).asString();
    		
    		if(!stringResponse.equals(null) && !stringResponse.getBody().isEmpty()){
				tmp = Double.valueOf(stringResponse.getBody().toString());
				if(tmp >= 0){
					result = tmp;
				}
				else {
					result = -1;
				}
		}
		else {
			result = -1;
		}
    	} catch (UnirestException e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
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
    public static JsonArray createJsonArrayFromDoubleArray(double[] doubleArray){
    	JsonArray jarray = new JsonArray();
    	
    	for(int i = 0; i < doubleArray.length;i++){
    		jarray.add(doubleArray[i]);
    	}
    	return jarray;
    }

	
//    private static JSONObject createJsonArrayFromDoubleArray(double[] doubleArray){
//    	JSONObject jObject = new JSONObject();
//    	
//    	jObject.put("doubles", doubleArray);
//    	return jObject;
//    }
    
    private List<Map<String, String>> getAllStations(){
    	Gson gson = new Gson();
    	List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
    	HttpResponse<String> response;
		try {
			response = Unirest.get(StadtradDBServerAdress + "/allStations").asString();
			// Speichern des Jsons aus dem Requestbody
			String json = response.getBody();
			
			if(!json.isEmpty()){
				// Format fuer das umwandeln jsons in ein Javaobjekt festelegen
				Type type = new TypeToken<List<Map<String, String>>>() {
				}.getType();

				// Aus dem Json ein Javaobjekt erstellen
				itemsList = gson.fromJson(json, type);
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			MailNotification.sendMail(e);
		}
		return itemsList;	
    }
    
}


