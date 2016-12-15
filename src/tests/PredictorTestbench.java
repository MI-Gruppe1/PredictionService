package tests;

import static org.junit.Assert.fail;

import javax.management.InvalidAttributeValueException;

import org.junit.Test;

import service.Prediction;
import service.Predictor;

public class PredictorTestbench {

	@Test
	public void test() {
		//create Test Data Stub
		double temp[] = {10,10,11,30};
		double prec[] = {0.5,0.5,0.4,0};
		int bikes[] = {10,10,12,10,9,30,30};
		ComStub stub = new ComStub(temp,prec,bikes);
		//create Unit to test
		Predictor UUT = new Prediction(stub);		
		
		//test UUT 
		double[] result = null;
		
		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result[0]!=8){
			System.out.println(result[0]);
			fail("Not yet implemented");
			
		}
		return;
		}

}
