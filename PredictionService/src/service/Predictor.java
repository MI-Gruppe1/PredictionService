package service;

public class Predictor {

	
	/**
	 * 
	 * 
	 * @return
	 */
	public double[] predict(String address){
		double result[] = {0,0,0,0,0,0};
		double weightA = 0;
		double weightB = 0;
		
		//get Inputs for Address
		double bikes[] = {0,0,0,0,0};		
		double temperature[] = {0,0,0};
		double precipitation[] = {0,0,0};
		
		
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
		
		
		
		
		return result;//KEK enough for the demo
	}
	
}
