package service;

import javax.management.InvalidAttributeValueException;

public interface Predictor{
	
	public double[] predict(String address, int numOfSamples) throws InvalidAttributeValueException;
	
}