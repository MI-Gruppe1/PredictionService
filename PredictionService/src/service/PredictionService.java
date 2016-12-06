package service;

import static spark.Spark.get;

public class PredictionService {

	public static void main(String[] args) {
		Communicator communicator = new Communication();
		// TODO Auto-generated method stub
		// defines REST Api for predictionService
				get("/predictionService", (req, res) -> {
		        	String name = req.queryParams("name");
		        	return communicator.getPrediction(name);
		        });
	}

}