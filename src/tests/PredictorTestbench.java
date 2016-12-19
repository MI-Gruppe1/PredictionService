package tests;

import static org.junit.Assert.fail;

import javax.management.InvalidAttributeValueException;

import org.junit.Test;

import service.Prediction;
import service.Predictor;

public class PredictorTestbench {

	@Test // Sanity Test (Does it work at all
	public void T0() {
		// create Test Data Stub
		double temp[] = { 10, 10, 11, 30 };
		double prec[] = { 0.5, 0.5, 0.4, 0 };
		int bikes[] = { 10, 10, 12, 10, 9, 30, 30, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result[0] != 8) {
			System.out.println(result[0]);
			fail("Result unexpected");

		}
		return;
	}

	@Test // test reaction to negative Bikes
	public void T1() {
		// create Test Data Stub
		double temp[] = { 10, 10, 11, 30 };
		double prec[] = { 0.5, 0.5, 0.4, 0 };
		int bikes[] = { -1, 10, 12, 10, 9, 30, 30, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			return;
		}
		fail("System did not throw exception as expected");

	}
	@Test // test reaction to negative Temperature
	public void T2() {
		// create Test Data Stub
		double temp[] = { 10, 10, -11, 11 };
		double prec[] = { 0.5, 0.5, 1, 0.45 };
		int bikes[] = { 10, 10, 12, 12, 9, 11, 15, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			fail("System did throw exception as expected");

		}
		if (result[0] != 7) {
			System.out.println(result[0]);
			fail("Result unexpected");

		}
		return;
	}
	@Test // test reaction to deadly negative Temperature
	public void T3() {
		// create Test Data Stub
		double temp[] = { 10, 10, -11, -3000 };
		double prec[] = { 0.5, 0.5, 0.4, 0.45 };
		int bikes[] = { 10, 10, 12, 12, 9, 11, 15, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			
			return;
		}
		fail("System did not throw exception as expected");
		
	}
	@Test // test reaction to deadly Temperature
	public void T4() {
		// create Test Data Stub
		double temp[] = { 123, 10, -11, -30 };
		double prec[] = { 0.5, 0.5, 0.4, 0.45 };
		int bikes[] = { 10, 10, 12, 12, 9, 11, 15, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			
			return;
		}
		fail("System did not throw exception as expected");
		
	}
	@Test // test reaction to wrong precipitation data
	public void T5() {
		// create Test Data Stub
		double temp[] = { 10, 10, -11, -30 };
		double prec[] = { 1.000000000000001, 0.5, 0.4, 0.45 };// bei noch feinerer auflösung wird es als =1 gewertet
		int bikes[] = { 10, 10, 12, 12, 9, 11, 15, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			
			return;
		}
		fail("System did not throw exception as expected");
		
	}
	@Test // test reaction to wrong precipitation data
	public void T6() {
		// create Test Data Stub
		double temp[] = { 10, 10, -11, -30 };
		double prec[] = { -0.00000000000000001, 0.5, 0.4, 0.45 };// bei noch feinerer auflösung wird es als =1 gewertet
		int bikes[] = { 10, 10, 12, 12, 9, 11, 15, 10, 11, 12, 13, 14 };
		ComStub stub = new ComStub(temp, prec, bikes);
		// create Unit to test
		Predictor UUT = new Prediction(stub);

		// test UUT
		double[] result = null;

		try {
			result = UUT.predict("StationName", 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			
			return;
		}
		fail("System did not throw exception as expected");
		
	}
}
