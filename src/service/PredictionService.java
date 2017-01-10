package service;

import static spark.Spark.get;

import javax.management.InvalidAttributeValueException;

public class PredictionService {

	public static void main(String[] args) {
		Communicator communicator = new Communication();
		// Port fuer diesen Service setzen
		spark.Spark.port(3000);
		
		// defines REST Api for predictionService
				get("/predictionService", (req, res) -> {
						String name = req.queryParams("name");
						System.out.println("Stationsname: " + name);
		        		return communicator.getPrediction(name);
				});
	}
}