package service;
/**
 * 
 * @author Moritz Heindorf
 * This class offers one/several methods to calculate the predictes amount of bikes present at a certain station
 */


public class Predictor {

	
	/**
	 * This method calculates the predicted value of bikes present at a station in the next hour based on the weather, 
	 * precipitation and bikes present at the same time and weekday for the past two weeks at this station.
	 * 
	 * @param address The address of the station for which the prediction should be made.
	 * 
	 * @return returns an array of integers where the first entry is the predicted value and the following 5 entries are the current amount of bikes and the bikes present in the last 4 hors respectively
	 */
	public double[] predict(String address){
		double result[] = {0,0,0,0,0,0};
		double weightA = 0;
		double weightB = 0;
		
		//TODO get Inputs for Address
		double bikes[] = {0,0,0,0,0};		
		double temperature[] = {0,0,0};
		double precipitation[] = {0,0,0};
		//TODO can i access the databank services from here or do i have to call someone else?
		
		
		//TODO fill the result array with data from the past 5 hours
		
		
		//weather comparison
		//smaller (absolute) difference gets higher weight in prediction 
		if(Math.pow((temperature[0]-temperature[1]),2)<=Math.pow((temperature[0]-temperature[2]),2)){
			weightA+=0.33;
		}else{
			weightB+=0.33;
		}
		//precipitation comparison
		//smaller difference leads to higher weight for the dataset
		if(Math.pow((precipitation[0]-precipitation[1]),2)<=Math.pow((precipitation[0]-precipitation[2]),2)){
			weightA+=0.33;
		}else{
			weightB+=0.33;
		}
		//bike availability comparison
		//smaller differences in availability leads to higher weight for the dataset in the calculation
		if(Math.pow((bikes[0]-bikes[1]),2)<=Math.pow((bikes[0]-bikes[3]),2)){
			weightA+=0.33;
		}else{
			weightB+=0.33;
		}
		//calculate gradient based on precalcualted weight
		//current+(gradient) gradient = sum of weights multiplied with gradients
		//since there are no "half" bikes we roud down
		result[0]=(int) bikes[0]+(weightA*(bikes[1]-bikes[2])+weightB*(bikes[3]-bikes[4]));
		
		
		
		
		return result;
	}
	
	/**
	 * This method calculates the predicted value of bikes present at a station in the next hour based on the weather, 
	 * precipitation and bikes present at the same time and weekday for the past X weeks at this station.
	 * @param address The address of the station for which the prediction should be made.
	 * @param numOfSamples The number of timestamps that are to be used for the prediction
	 * @return
	 */
	
	public double[] predict(String address, int numOfSamples){
		double result[] = {0,0,0,0,0,0};
		
		//TODO IMPLEMENT THIS
		
		
		return result;
	}
}
