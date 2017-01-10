package service;

import java.util.Date;

import javax.management.InvalidAttributeValueException;

/**
 * 
 * @author Moritz Heindorf and Harry This class offers one/several methods to
 *         calculate the predicted amount of bikes present at a certain station
 */
public class Prediction implements Predictor {

	private Communicator comm;

	public Prediction(Communicator comm) {
		this.comm = comm;
	}

	
	/**
	 * This method calculates the predicted value of bikes present at a station
	 * in the next hour based on the weather, precipitation and bikes present at
	 * the same time and weekday for the past X weeks at this station.
	 * 
	 * @param address
	 *            The address of the station for which the prediction should be
	 *            made.
	 * @param numOfSamples
	 *            The number of timestamps that are to be used for the
	 *            prediction
	 * @return returns an array of integers where the first entry is the
	 *         predicted value and the following 5 entries are the current
	 *         amount of bikes and the bikes present in the last 4 hours
	 *         respectively
	 */

	public double[] predict(String address, int numOfSamples) throws InvalidAttributeValueException {
		Date now = new Date();
		long time = now.getTime();
		long day = 24 * 60 * 60 * 1000;// one day in milliseconds
		long hour = 60 * 60 * 1000;// one hour in milliseconds
		double result[] = { 0, 0, 0, 0, 0, 0 };

		double tempdif[] = new double[numOfSamples];
		double precdif[] = new double[numOfSamples];
		double availdif[] = new double[numOfSamples];
		// TODO get Inputs for Address

		double bikes[] = new double[1 + 2 * numOfSamples];
		double temperature[] = new double[1 + numOfSamples];
		double precipitation[] = new double[1 + numOfSamples];
		double weight[] = new double[numOfSamples];

		bikes[0] = (int) comm.getFreeBikesofStationAtSpecTime(address, time);
		if (bikes[0] < 0)
			throw new InvalidAttributeValueException("invalid current bikes");
		//System.out.println("bikes[0]:" + bikes[0]);

		for (int i = 1; i <= numOfSamples; i++) {

			bikes[2 * i - 1] = comm.getFreeBikesofStationAtSpecTime(address, (time - i * day));
			bikes[2 * i] = comm.getFreeBikesofStationAtSpecTime(address, (time - i * day - hour));
			if (bikes[2 * i - 1] < 0)
				throw new InvalidAttributeValueException("invalid bike data");
			if (bikes[2 * i - 1] < 0)
				throw new InvalidAttributeValueException("invalid bike data");

			//System.out.println("bikes[" + (2 * i - 1) + "]:" + bikes[2 * i - 1]);
			//System.out.println("bikes[" + (2 * i) + "]:" + bikes[2 * i]);
		}

		for (int i = 0; i < numOfSamples + 1; i++) {
			temperature[i] = comm.getTemperatureAtTime(address, time - i * day);
			precipitation[i] = comm.getWeatherConditionAtTime(address, time - i * day);

			if (temperature[i] < -274 || temperature[i] > 100)
				throw new InvalidAttributeValueException("invalid temperature data");
			if (precipitation[i] < 0 || precipitation[i] > 1)
				throw new InvalidAttributeValueException("invalid precipitation data");

			//System.out.println("tempdata[" + i + "]:" + temperature[i]);
			//System.out.println("precipitation[" + i + "]:" + precipitation[i]);
		}

		// fill result array with the past 5 hours
		for (int i = 1; i < result.length; i++) {
			result[i] = (int) comm.getFreeBikesofStationAtSpecTime(address, time - i * hour);
			if (result[i] < 0)
				throw new InvalidAttributeValueException("invalid bike data for result");
		}

		// weather comparison
		// smaller (absolute) difference gets higher weight in prediction

		// calculate absolute temperature differences
		for (int i = 1; i < temperature.length; i++) {
			tempdif[i - 1] = Math.pow((temperature[0] - temperature[i]), 2);
		}

		int j = findSmallestArrayElement(tempdif);
		tempdif[j] = 10000; // set value to highvalue to enable finding the
							// second smallest element
		weight[j] += 0.22;

		// find second smallest element by running the search again
		j = findSmallestArrayElement(tempdif);
		weight[j] += 0.11;

		// precipitation comparison

		for (int i = 1; i < precipitation.length; i++) {
			precdif[i - 1] = Math.pow((precipitation[0] - precipitation[i]), 2);
		}

		j = findSmallestArrayElement(precdif);
		precdif[j] = 10000; // set value to a highvalue to enable finding the
							// second smallest element
		weight[j] += 0.22;

		// find second smallest element by running the search again
		j = findSmallestArrayElement(precdif);
		weight[j] += 0.11;

		for (int i = 0; i < numOfSamples; i++) {
			availdif[i] = Math.pow((bikes[0] - bikes[i * 2 + 1]), 2);
		}
		j = findSmallestArrayElement(availdif);
		weight[j] += 0.34;

		
		double gradient = 0;
		// calculate gradient based on precalcualted weight
		// current+(gradient) gradient = sum of weights multiplied with
		// gradients
		// since there are no "half" bikes we round down
		for (int i = 1; i <= numOfSamples; i++) {

			gradient += (bikes[i * 2 - 1] - bikes[i * 2]) * weight[i - 1];

		}
		result[0] =  gradient;
		
		return result;
	}

	// returns the position of the smallest array element
	private static int findSmallestArrayElement(double[] array) {
		int result = 0;

		for (int i = 1; i < array.length; i++) {
			if (array[i] < array[result]) {
				result = i;
			}
		}

		return result;
	}

}
