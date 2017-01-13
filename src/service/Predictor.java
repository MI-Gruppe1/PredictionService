package service;

import javax.management.InvalidAttributeValueException;
/**
 * 
 * @author Moritz Heindorf
 * Interface for the Prediction class of the Predictionservice
 */
public interface Predictor{
	
	public double[] predict(String address, int numOfSamples) throws InvalidAttributeValueException;
	
}