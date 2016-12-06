package service;


import java.util.ArrayList;
import java.util.Date;


/**
 * 
 * @author Moritz Heindorf and Harry This class offers one/several methods to
 *         calculate the predicted amount of bikes present at a certain station
 */

public class Predictor {

	private Communicator comm;

	public Predictor(Communicator comm) {

		this.comm = comm;

	}

	/**
	 * This method calculates the predicted value of bikes present at a station
	 * in the next hour based on the weather, precipitation and bikes present at
	 * the same time and weekday for the past two weeks at this station.
	 * 
	 * @param address
	 *            The address of the station for which the prediction should be
	 *            made.
	 * 
	 * @return returns an array of integers where the first entry is the
	 *         predicted value and the following 5 entries are the current
	 *         amount of bikes and the bikes present in the last 4 hours
	 *         respectively
	 */
	private double[] predict(String address) {
		Date now = new Date();
		long time = now.getTime();
		long day = 24 * 60 * 60 * 1000;// one day in milliseconds
		long hour = 60 * 60 * 1000;// one hour in milliseconds
		double result[] = { 0, 0, 0, 0, 0, 0 };
		double weightA = 0;
		double weightB = 0;

		// TODO get Inputs for Address

		double bikes[] = { 0, 0, 0, 0, 0 };
		double temperature[] = { 0, 0, 0 };
		double precipitation[] = { 0, 0, 0 };

		bikes[0] = comm.getFreeBikesofStationAtSpecTime(address, time);
		bikes[1] = comm.getFreeBikesofStationAtSpecTime(address, (time - day));
		bikes[2] = comm.getFreeBikesofStationAtSpecTime(address, (time - day - hour));
		bikes[3] = comm.getFreeBikesofStationAtSpecTime(address, (time - 2 * day));
		bikes[4] = comm.getFreeBikesofStationAtSpecTime(address, (time - 2 * day - hour));

		temperature[0] = comm.getTemperatureAtTime(address, time);
		temperature[1] = comm.getTemperatureAtTime(address, (time - day));
		temperature[2] = comm.getTemperatureAtTime(address, (time - 2 * day));

		precipitation[0] = comm.getWeatherAtTime(address, time);
		precipitation[1] = comm.getWeatherAtTime(address, (time - day));
		precipitation[2] = comm.getWeatherAtTime(address, (time - 2 * day));

		for (int i = 1; i < 6; i++) {
			result[i] = comm.getFreeBikesofStationAtSpecTime(address, time - i * hour);
		}

		// weather comparison
		// smaller (absolute) difference gets higher weight in prediction
		if (Math.pow((temperature[0] - temperature[1]), 2) <= Math.pow((temperature[0] - temperature[2]), 2)) {
			weightA += 0.33;
		} else {
			weightB += 0.33;
		}
		// precipitation comparison
		// smaller difference leads to higher weight for the dataset
		if (Math.pow((precipitation[0] - precipitation[1]), 2) <= Math.pow((precipitation[0] - precipitation[2]), 2)) {
			weightA += 0.33;
		} else {
			weightB += 0.33;
		}
		// bike availability comparison
		// smaller differences in availability leads to higher weight for the
		// dataset in the calculation
		if (Math.pow((bikes[0] - bikes[1]), 2) <= Math.pow((bikes[0] - bikes[3]), 2)) {
			weightA += 0.33;
		} else {
			weightB += 0.33;
		}
		// calculate gradient based on precalcualted weight
		// current+(gradient) gradient = sum of weights multiplied with
		// gradients
		// since there are no "half" bikes we roud down
		result[0] = (int) bikes[0] + (weightA * (bikes[1] - bikes[2]) + weightB * (bikes[3] - bikes[4]));

		return result;
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

	public double[] predict(String address, int numOfSamples) {
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

		bikes[0] = comm.getFreeBikesofStationAtSpecTime(address, time);

		for (int i = 1; i < numOfSamples; i++) {

			bikes[2 * i - 1] = comm.getFreeBikesofStationAtSpecTime(address, (time - i * day));
			bikes[2 * i] = comm.getFreeBikesofStationAtSpecTime(address, (time - i * day - hour));

		}

		for (int i = 0; i < numOfSamples + 1; i++) {
			temperature[i] = comm.getTemperatureAtTime(address, time - i * day);
			precipitation[i] = comm.getWeatherAtTime(address, time - i * day);
		}

		// fill result array with the past 5 hours
		for (int i = 1; i < result.length; i++) {
			result[i] = comm.getFreeBikesofStationAtSpecTime(address, time - i * hour);
		}

		// TODO anpassen an samplesize

		// weather comparison
		// smaller (absolute) difference gets higher weight in prediction

		// calculate absolute temperature differences
		for (int i = 1; i < temperature.length; i++) {
			tempdif[i - 1] = Math.pow((temperature[0] - temperature[i]), 2);
		}

		int j = findSmallestArrayElement(tempdif);
		tempdif[j] = 10000; // set value to highvalue to enable finding the
							// second smallest element
		weight[j] *= 0.22;

		// find second smallest element by running the search again
		j = findSmallestArrayElement(tempdif);
		weight[j] *= 0.11;

		// precipitation comparison

		for (int i = 1; i < precipitation.length; i++) {
			precdif[i - 1] = Math.pow((precipitation[0] - precipitation[i]), 2);
		}

		j = findSmallestArrayElement(precdif);
		precdif[j] = 10000; // set value to a highvalue to enable finding the
							// second smallest element
		weight[j] *= 0.22;

		// find second smallest element by running the search again
		j = findSmallestArrayElement(precdif);
		weight[j] *= 0.11;

		for (int i = 0; i < numOfSamples; i++) {
			availdif[i] = Math.pow((bikes[0] - bikes[i * 2 + 1]), 2);
		}
		j = findSmallestArrayElement(availdif);
		weight[j] *= 0.34;

		double gradient = 0;
		// calculate gradient based on precalcualted weight
		// current+(gradient) gradient = sum of weights multiplied with
		// gradients
		// since there are no "half" bikes we round down
		for (int i = 1; i < numOfSamples; i++) {

			gradient += (bikes[i * 2 - 1] - bikes[i * 2]) * weight[i - 1];

		}
		result[0] = bikes[0] + gradient;

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
