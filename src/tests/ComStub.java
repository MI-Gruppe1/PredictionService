package tests;

import com.google.gson.JsonArray;

import service.Communicator;

/**
 * Test Stub for the Communication class returns data given at construction
 * returns one date on each call and goes to the next datapoint
 * 
 * @author Moritz Heindorf
 *
 */

public class ComStub implements Communicator {

	double[] temperatureData;
	double[] precData;
	int[] bikesData;

	int TC;
	int PC;
	int BC;

	public ComStub(double[] WeatherData, double[] PrecData, int[] BikesData) {
		this.temperatureData = WeatherData;
		this.precData = PrecData;
		this.bikesData = BikesData;
		TC = 0;
		PC = 0;
		BC = 0;
	}

	@Override
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp) {
		int result;
		if (stationName != "StationName") {
			BC++;
			return -1;
		}

		if (BC < bikesData.length) {
			result = this.bikesData[BC];
			BC++;
		} else
			result = 0;
		return result;

	}

	@Override
	public double getTemperatureAtTime(String name, Long timeStamp) {
		if (name != "StationName") {
			TC++;
			return -1;
		}
		double result;
		if (TC < temperatureData.length) {
			result = this.temperatureData[TC];
			TC++;
		} else
			result = 0;
		return result;
	}

	@Override
	public double getWeatherConditionAtTime(String name, Long timeStamp) {
		if (name != "StationName") {
			PC++;
			return -1;
		}
		double result;
		if (PC < precData.length) {
			result = this.precData[PC];
			PC++;
		} else
			result = 0;
		return result;
	}

	@Override
	public JsonArray getPrediction(String stationsname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStations() {
		// TODO Auto-generated method stub
		
	}

}