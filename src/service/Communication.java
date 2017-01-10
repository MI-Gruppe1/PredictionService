package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InvalidAttributeValueException;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Communication implements Communicator {

	Prediction prediction;
	Map<String,Double> latMap = new HashMap<String,Double>();
	Map<String,Double> longMap = new HashMap<String,Double>();
	String WeatherDBServerAdress = "http://localhost:4568";
	String StadtradDBServerAdress = "http://localhost:6000";
	
	public Communication(){
		prediction = new Prediction(this);
	}
	
	public Communication(String weatherDBServerAdress, String stadtradDBServerAdress){
		this.WeatherDBServerAdress = weatherDBServerAdress;
		this.StadtradDBServerAdress = stadtradDBServerAdress;
		prediction = new Prediction(this);
	}
	
	public void updateStations(){
//		List<Map<String, String>> allStations = getAllStations();

		List<Map<String, String>> allStations;
		
//		StringBuffer buffer = new StringBuffer();
//		FileReader in;
//		try {
//			in = new FileReader(new File("src//service//allstations.json"));
//			for (int n;(n = in.read()) != -1;buffer.append((char) n));
//			in.close();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			MailNotification.sendMail(e);
//			System.out.println("allStation failed");
//		}
//		
		
		String s = "[\n" +
				"{\n" +
				"\"name\": \"2397 Alsterdorfer Straﬂe/Fuhlsb¸ttler Straﬂe\",\n" +
				"\"latitude\": 53.62,\n" +
				"\"longitude\": 10.032\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2711 Mannesallee/Veringstraﬂe\",\n" +
				"\"latitude\": 53.514265,\n" +
				"\"longitude\": 9.987298\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2214 Universit‰t / Schl¸terstraﬂe\",\n" +
				"\"latitude\": 53.566972,\n" +
				"\"longitude\": 9.986076\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2215 Allende-Platz/Grindelhof\",\n" +
				"\"latitude\": 53.567845,\n" +
				"\"longitude\": 9.982728\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2212 Universit‰t / Moorweidenstraﬂe\",\n" +
				"\"latitude\": 53.563755,\n" +
				"\"longitude\": 9.987659\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2231 Hallerstraﬂe / Rohtenbaumchaussee\",\n" +
				"\"latitude\": 53.572081,\n" +
				"\"longitude\": 9.988801\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2528 Bahnhof Dammtor S¸d / Marseiller Straﬂe\",\n" +
				"\"latitude\": 53.5602,\n" +
				"\"longitude\": 9.98963\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2216 Bogenstraﬂe / Grindelallee\",\n" +
				"\"latitude\": 53.5712,\n" +
				"\"longitude\": 9.977694\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2512 St.Petersburger Straﬂe/Bei den Kirchhˆfen\",\n" +
				"\"latitude\": 53.559762,\n" +
				"\"longitude\": 9.981839\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2213 Harvestehuder Weg / Alte Rabenstraﬂe\",\n" +
				"\"latitude\": 53.5672,\n" +
				"\"longitude\": 10.0011\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2232 Grindelberg / Bezirksamt Eimsb¸ttel\",\n" +
				"\"latitude\": 53.575408,\n" +
				"\"longitude\": 9.977824\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2221 U-Bahn Schlump\",\n" +
				"\"latitude\": 53.567991,\n" +
				"\"longitude\": 9.969601\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2234 Mittelweg/Sophienterrasse-NDR\",\n" +
				"\"latitude\": 53.576378,\n" +
				"\"longitude\": 9.993122\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2524 G‰nsemarkt / B¸schstraﬂe\",\n" +
				"\"latitude\": 53.556282,\n" +
				"\"longitude\": 9.990091\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2511 Messehallen / Marktstraﬂe\",\n" +
				"\"latitude\": 53.558518,\n" +
				"\"longitude\": 9.975346\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2133 Sternschanze / Eingang D‰nenweg\",\n" +
				"\"latitude\": 53.564227,\n" +
				"\"longitude\": 9.96925\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2233 Innocentiapark/Oberstraﬂe\",\n" +
				"\"latitude\": 53.577248,\n" +
				"\"longitude\": 9.98211\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2525 Caffamacherreihe/Valentinskamp.\",\n" +
				"\"latitude\": 53.555722,\n" +
				"\"longitude\": 9.984209\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2526 Sievekingplatz/Gorch-Fock-Wall\",\n" +
				"\"latitude\": 53.556061,\n" +
				"\"longitude\": 9.979697\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2523 Jungfernstieg / Neuer Jungfernstieg\",\n" +
				"\"latitude\": 53.555,\n" +
				"\"longitude\": 9.9914\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2241 Isestraﬂe / Hoheluftbr¸cke\",\n" +
				"\"latitude\": 53.578,\n" +
				"\"longitude\": 9.97721\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2513 Feldstraﬂe / Marktstraﬂe\",\n" +
				"\"latitude\": 53.557414,\n" +
				"\"longitude\": 9.969178\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2222 Christuskirche/Fruchtallee\",\n" +
				"\"latitude\": 53.5695,\n" +
				"\"longitude\": 9.96309\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2522 Axel-Springer-Platz/Wexstraﬂe\",\n" +
				"\"latitude\": 53.551853,\n" +
				"\"longitude\": 9.985161\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2533 Jungfernstieg / Ballindamm\",\n" +
				"\"latitude\": 53.552244,\n" +
				"\"longitude\": 9.995501\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2624 Enckeplatz / H¸tten\",\n" +
				"\"latitude\": 53.5524,\n" +
				"\"longitude\": 9.97695\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2316 Leinpfad/Fernsicht\",\n" +
				"\"latitude\": 53.580391,\n" +
				"\"longitude\": 9.999404\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2131 Neuer Pferdemarkt / Beim Gr¸nen J‰ger\",\n" +
				"\"latitude\": 53.558869,\n" +
				"\"longitude\": 9.963824\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2132 Schulterblatt/Eifflerstraﬂe\",\n" +
				"\"latitude\": 53.562709,\n" +
				"\"longitude\": 9.96099\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2223 Osterstraﬂe/Bismarckstraﬂe\",\n" +
				"\"latitude\": 53.573183,\n" +
				"\"longitude\": 9.962395\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2623 Groﬂneumarkt/Thielbek\",\n" +
				"\"latitude\": 53.550977,\n" +
				"\"longitude\": 9.980459\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2534 Kleine Rosenstraﬂe / Gerhart-Hauptmann-Platz\",\n" +
				"\"latitude\": 53.5516,\n" +
				"\"longitude\": 9.99843\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2531 Alter Wall/Adolphsbr¸cke\",\n" +
				"\"latitude\": 53.550193,\n" +
				"\"longitude\": 9.99011\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2235 Eppendorfer Baum / Isestraﬂe\",\n" +
				"\"latitude\": 53.5832,\n" +
				"\"longitude\": 9.98441\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2536 Hauptbahnhof West / Glockengieﬂer Wall\",\n" +
				"\"latitude\": 53.553676,\n" +
				"\"longitude\": 10.004553\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2521 Stadthausbr¸cke / Neuer Wall\",\n" +
				"\"latitude\": 53.549672,\n" +
				"\"longitude\": 9.986594\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2636 Mˆnckebergstraﬂe / Rosenstraﬂe\",\n" +
				"\"latitude\": 53.550722,\n" +
				"\"longitude\": 9.99714\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2542 Lange Reihe / Kirchenallee\",\n" +
				"\"latitude\": 53.5552,\n" +
				"\"longitude\": 10.0082\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2541 Hauptbahnhof Ost / Hachmannplatz\",\n" +
				"\"latitude\": 53.554307,\n" +
				"\"longitude\": 10.007652\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2321 Eduard-Rhein-Ufer / Schwanenwik\",\n" +
				"\"latitude\": 53.5677,\n" +
				"\"longitude\": 10.0162\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2243 Eppendorfer Weg/Hoheluftchaussee\",\n" +
				"\"latitude\": 53.581771,\n" +
				"\"longitude\": 9.97172\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2242 Goebenstraﬂe/Eppendorfer Weg\",\n" +
				"\"latitude\": 53.577579,\n" +
				"\"longitude\": 9.963063\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2544 Lange Reihe / Lohm¸hlenpark\",\n" +
				"\"latitude\": 53.5599,\n" +
				"\"longitude\": 10.0149\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2632 Rˆdingsmarkt / Groﬂer Burstah\",\n" +
				"\"latitude\": 53.5483,\n" +
				"\"longitude\": 9.98695\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2612 Millerntorplatz/St.Pauli\",\n" +
				"\"latitude\": 53.551,\n" +
				"\"longitude\": 9.97033\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2637 Jacobikirche / Steinstraﬂe\",\n" +
				"\"latitude\": 53.5503,\n" +
				"\"longitude\": 10.0015\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2639 Mˆnckebergstraﬂe / Steintorwall\",\n" +
				"\"latitude\": 53.5516,\n" +
				"\"longitude\": 10.0054\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2635 Schopenstehl/Alter Fischmarkt\",\n" +
				"\"latitude\": 53.548703,\n" +
				"\"longitude\": 9.997126\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2613 Paulinenplatz/Wohlwillstraﬂe\",\n" +
				"\"latitude\": 53.5542,\n" +
				"\"longitude\": 9.96246\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2622 Michaeliskirche / Krayenkamp\",\n" +
				"\"latitude\": 53.547975,\n" +
				"\"longitude\": 9.97942\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2633 Domstraﬂe/Willy-Brandt-Straﬂe\",\n" +
				"\"latitude\": 53.547486,\n" +
				"\"longitude\": 9.994057\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2324 Hofweg/Am Langenzug\",\n" +
				"\"latitude\": 53.578035,\n" +
				"\"longitude\": 10.013995\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2135 Eimsb¸tteler Straﬂe/Waterloostraﬂe\",\n" +
				"\"latitude\": 53.5662,\n" +
				"\"longitude\": 9.9534\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2644 Steindamm / Stralsunder Straﬂe\",\n" +
				"\"latitude\": 53.554216,\n" +
				"\"longitude\": 10.013306\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2634 Meﬂberg / Willy-Brandt-Straﬂe\",\n" +
				"\"latitude\": 53.547805,\n" +
				"\"longitude\": 9.998774\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2224 Emilienstraﬂe/Fruchtallee\",\n" +
				"\"latitude\": 53.57169,\n" +
				"\"longitude\": 9.953138\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2641 Zentralbibliothek / M¸nzstraﬂe\",\n" +
				"\"latitude\": 53.550332,\n" +
				"\"longitude\": 10.008849\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2638 Steinstraﬂe / Deichtorplatz\",\n" +
				"\"latitude\": 53.548562,\n" +
				"\"longitude\": 10.005325\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2311 Alsterschwimmhalle/Ifflandstraﬂe\",\n" +
				"\"latitude\": 53.560781,\n" +
				"\"longitude\": 10.022148\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2326 Winterhuder Weg/ Zimmerstraﬂe\",\n" +
				"\"latitude\": 53.575308,\n" +
				"\"longitude\": 10.02045\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2646 Lohm¸hlenstraﬂe / Steindamm\",\n" +
				"\"latitude\": 53.5572,\n" +
				"\"longitude\": 10.0199\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2363 Goldbekplatz / Semperstraﬂe\",\n" +
				"\"latitude\": 53.5838,\n" +
				"\"longitude\": 10.01\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2371 Kellinghusenstraﬂe / Loogeplatz\",\n" +
				"\"latitude\": 53.588516,\n" +
				"\"longitude\": 9.991125\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2136 Sophienallee / Sandweg\",\n" +
				"\"latitude\": 53.569769,\n" +
				"\"longitude\": 9.94977\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2631 U-Bahn Baumwall\",\n" +
				"\"latitude\": 53.544279,\n" +
				"\"longitude\": 9.980896\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2621 Landungsbr¸cke/Hafentor\",\n" +
				"\"latitude\": 53.5457,\n" +
				"\"longitude\": 9.9723\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2643 Kurt-Schumacher-Allee / Nagelsweg\",\n" +
				"\"latitude\": 53.5517,\n" +
				"\"longitude\": 10.016\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2672 Auf dem Sande/Kehrwieder-MiniaturWunderland\",\n" +
				"\"latitude\": 53.543731,\n" +
				"\"longitude\": 9.98971\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2225 Heuﬂweg/Wiesenstraﬂe\",\n" +
				"\"latitude\": 53.577796,\n" +
				"\"longitude\": 9.953073\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2611 Bernhard-Nocht-Straﬂe/Zirkusweg\",\n" +
				"\"latitude\": 53.54732,\n" +
				"\"longitude\": 9.965669\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2134 Alsenstraﬂe/D¸ppelstraﬂe\",\n" +
				"\"latitude\": 53.563037,\n" +
				"\"longitude\": 9.948345\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2373 Martinistraﬂe / Haupteingang Klinikum\",\n" +
				"\"latitude\": 53.588918,\n" +
				"\"longitude\": 9.975715\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2372 K¸mmellstraﬂe / Robert-Koch-Straﬂe\",\n" +
				"\"latitude\": 53.590237,\n" +
				"\"longitude\": 9.98673\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2315 Uhlandstraﬂe / Eingang Nord\",\n" +
				"\"latitude\": 53.565,\n" +
				"\"longitude\": 10.0267\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2227 Troplowitzstraﬂe / Beiersdorf  / NXP\",\n" +
				"\"latitude\": 53.582793,\n" +
				"\"longitude\": 9.957594\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2323 Mundsburg / Sch¸rbeker Straﬂe\",\n" +
				"\"latitude\": 53.5696,\n" +
				"\"longitude\": 10.0271\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2642 Amsinckstraﬂe/ Nordkanalbr¸cke\",\n" +
				"\"latitude\": 53.547868,\n" +
				"\"longitude\": 10.012361\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2673 Am Kaiserkai/Groﬂer Grasbrook\",\n" +
				"\"latitude\": 53.541838,\n" +
				"\"longitude\": 9.992856\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2111 Reeperbahn / Kˆnigstraﬂe\",\n" +
				"\"latitude\": 53.549501,\n" +
				"\"longitude\": 9.954934\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2312 L¸becker Straﬂe / Marienkrankenhaus\",\n" +
				"\"latitude\": 53.5597,\n" +
				"\"longitude\": 10.0285\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2645 Berliner Tor / Berlinertordamm\",\n" +
				"\"latitude\": 53.5533,\n" +
				"\"longitude\": 10.0246\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2674 Osakaallee/‹bersee Quartier\",\n" +
				"\"latitude\": 53.541583,\n" +
				"\"longitude\": 9.999731\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2322 Lerchenfeld / Uferstraﬂe\",\n" +
				"\"latitude\": 53.567,\n" +
				"\"longitude\": 10.0318\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2220 Lappenbergsallee / Bei der Apostelkirche\",\n" +
				"\"latitude\": 53.575573,\n" +
				"\"longitude\": 9.944487\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2364 Jarrestraﬂe / Rambatzweg\",\n" +
				"\"latitude\": 53.584,\n" +
				"\"longitude\": 10.0211\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2333 Bartholom‰usstraﬂe/Beim Alten Sch¸tzenhof\",\n" +
				"\"latitude\": 53.575652,\n" +
				"\"longitude\": 10.02957\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2675 Grasbrookpark/‹berseequartier\",\n" +
				"\"latitude\": 53.540282,\n" +
				"\"longitude\": 9.997945\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2671 Unilever/Strandkai\",\n" +
				"\"latitude\": 53.53958,\n" +
				"\"longitude\": 9.99365\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2226 Methfesselstraﬂe/Luruper Weg\",\n" +
				"\"latitude\": 53.58006,\n" +
				"\"longitude\": 9.946786\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2369 Hudtwalckerstraﬂe / Bebelallee\",\n" +
				"\"latitude\": 53.5943,\n" +
				"\"longitude\": 9.99575\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2314 Wartenau / L¸becker Straﬂe\",\n" +
				"\"latitude\": 53.5636,\n" +
				"\"longitude\": 10.0341\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2116 Chemnitzstraﬂe / Max-Brauer-Allee\",\n" +
				"\"latitude\": 53.5553,\n" +
				"\"longitude\": 9.94286\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2374 S¸derfeldstraﬂe/Universit‰tsklinikum Eppendorf\",\n" +
				"\"latitude\": 53.594,\n" +
				"\"longitude\": 9.973\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2115 Groﬂe Bergstraﬂe / Jessenstraﬂe\",\n" +
				"\"latitude\": 53.5512,\n" +
				"\"longitude\": 9.94576\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2112 Fischmarkt/Breite Straﬂe\",\n" +
				"\"latitude\": 53.5462,\n" +
				"\"longitude\": 9.95088\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2551 Eiffestrasse/Normannenweg\",\n" +
				"\"latitude\": 53.552123,\n" +
				"\"longitude\": 10.030364\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2651 Hammerbrook / Sachsenfeld\",\n" +
				"\"latitude\": 53.546011,\n" +
				"\"longitude\": 10.024042\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2313 Landwehr / Ramazan-Avci-Platz\",\n" +
				"\"latitude\": 53.561109,\n" +
				"\"longitude\": 10.037022\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2367 Borgweg/Stadtpark\",\n" +
				"\"latitude\": 53.592022,\n" +
				"\"longitude\": 10.015021\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2332 Holsteinischer Kamp/ Wagnerstraﬂe\",\n" +
				"\"latitude\": 53.574518,\n" +
				"\"longitude\": 10.036608\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2334 Weidestraﬂe/Biedermannplatz\",\n" +
				"\"latitude\": 53.579764,\n" +
				"\"longitude\": 10.034041\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2331 Uferstraﬂe/Wagnerstraﬂe\",\n" +
				"\"latitude\": 53.571078,\n" +
				"\"longitude\": 10.039741\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2113 Kˆnigstraﬂe / Struenseestraﬂe\",\n" +
				"\"latitude\": 53.5471,\n" +
				"\"longitude\": 9.94241\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2383 Bebelallee/Meenkwiese\",\n" +
				"\"latitude\": 53.599203,\n" +
				"\"longitude\": 9.994017\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2336 Schleidenstraﬂe/Osterbekstraﬂe\",\n" +
				"\"latitude\": 53.584582,\n" +
				"\"longitude\": 10.03369\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2381 Planetarium/Hindenburgstraﬂe\",\n" +
				"\"latitude\": 53.59595,\n" +
				"\"longitude\": 10.01365\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2250 Emil-Andresen-Straﬂe / Lohkoppelweg\",\n" +
				"\"latitude\": 53.59285,\n" +
				"\"longitude\": 9.952055\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2121 Bahnhof Altona Ost/Max-Brauer-Allee\",\n" +
				"\"latitude\": 53.552121,\n" +
				"\"longitude\": 9.936005\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2561 Burgstraﬂe/Hammer Landstraﬂe\",\n" +
				"\"latitude\": 53.555693,\n" +
				"\"longitude\": 10.041298\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2411 Wandsbeker Chaussee/Ritterstraﬂe\",\n" +
				"\"latitude\": 53.567126,\n" +
				"\"longitude\": 10.044484\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2122 Bahnhof Altona West / Busbahnhof\",\n" +
				"\"latitude\": 53.5518,\n" +
				"\"longitude\": 9.93387\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2365 Saarlandstraﬂe/Wiesendamm\",\n" +
				"\"latitude\": 53.588408,\n" +
				"\"longitude\": 10.0329\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2335 Dehnhaide/Barmbeker Markt\",\n" +
				"\"latitude\": 53.579981,\n" +
				"\"longitude\": 10.041461\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2366 S¸dring/Stadthallenbr¸cke\",\n" +
				"\"latitude\": 53.591446,\n" +
				"\"longitude\": 10.030327\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2126 Bahrenfelder Straﬂe/Vˆlckersstraﬂe\",\n" +
				"\"latitude\": 53.556897,\n" +
				"\"longitude\": 9.927945\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2114 Ottenser Marktplatz/Museumsstraﬂe\",\n" +
				"\"latitude\": 53.5478,\n" +
				"\"longitude\": 9.93456\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2127 Groﬂe Rainstraﬂe/Ottenser Hauptstraﬂe\",\n" +
				"\"latitude\": 53.552339,\n" +
				"\"longitude\": 9.930344\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2680 Bullerdeich/Stadtreinigung Hamburg\",\n" +
				"\"latitude\": 53.544979,\n" +
				"\"longitude\": 10.03685\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2251 Koppelstraﬂe/Lokstedter Grenzstraﬂe\",\n" +
				"\"latitude\": 53.593587,\n" +
				"\"longitude\": 9.943525\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2337 Lortzingstraﬂe/Friedrichsberger Straﬂe\",\n" +
				"\"latitude\": 53.573588,\n" +
				"\"longitude\": 10.049568\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2351 Wiesendamm/Roggenkamp\",\n" +
				"\"latitude\": 53.586958,\n" +
				"\"longitude\": 10.042993\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2414 Sievekingsallee/Sievekingdamm\",\n" +
				"\"latitude\": 53.560835,\n" +
				"\"longitude\": 10.052589\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2124 Eulenstraﬂe/Groﬂe Brunnenstraﬂe\",\n" +
				"\"latitude\": 53.550816,\n" +
				"\"longitude\": 9.925653\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2412 Hasselbrookstraﬂe/Papenstraﬂe\",\n" +
				"\"latitude\": 53.564805,\n" +
				"\"longitude\": 10.054679\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2125 Fischersallee/Bleickenallee\",\n" +
				"\"latitude\": 53.550966,\n" +
				"\"longitude\": 9.92226\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2382 ‹berseering/Mexikoring\",\n" +
				"\"latitude\": 53.601619,\n" +
				"\"longitude\": 10.024745\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2562 Bei der Hammer Kirche/Hammerpark\",\n" +
				"\"latitude\": 53.556699,\n" +
				"\"longitude\": 10.055075\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2129 Hohenzollernring/Friedensallee\",\n" +
				"\"latitude\": 53.55671,\n" +
				"\"longitude\": 9.918508\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2352 Fuhlsb¸ttler Straﬂe/Hellbrookstraﬂe\",\n" +
				"\"latitude\": 53.59146,\n" +
				"\"longitude\": 10.044508\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2681 Billstraﬂe/Billhorner Deich\",\n" +
				"\"latitude\": 53.540103,\n" +
				"\"longitude\": 10.04287\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2413 Wandsbeker Chaussee/ Bˆrnestraﬂe\",\n" +
				"\"latitude\": 53.57047,\n" +
				"\"longitude\": 10.059528\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2341 Krausestraﬂe/Eilbektal\",\n" +
				"\"latitude\": 53.576238,\n" +
				"\"longitude\": 10.058237\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2682 Rothenburgsort/Marktplatz/Lindleystraﬂe\",\n" +
				"\"latitude\": 53.534288,\n" +
				"\"longitude\": 10.038938\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2686 Osterbrookplatz/ S¸derstraﬂe\",\n" +
				"\"latitude\": 53.546932,\n" +
				"\"longitude\": 10.057613\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2090 Friedensallee/Hegarstraﬂe ( S Bahn Bahrenfeld )\",\n" +
				"\"latitude\": 53.56026,\n" +
				"\"longitude\": 9.908946\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2691 Wilhelmsburger Platz/Zur Schleuse\",\n" +
				"\"latitude\": 53.522794,\n" +
				"\"longitude\": 10.01481\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2391 Fuhlsb¸ttler Straﬂe/Hartzloh\",\n" +
				"\"latitude\": 53.601,\n" +
				"\"longitude\": 10.041\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2151 Neum¸hlen/÷velgˆnne\",\n" +
				"\"latitude\": 53.544302,\n" +
				"\"longitude\": 9.914731\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2431 Schloﬂstrasse/Schloﬂgarten\",\n" +
				"\"latitude\": 53.571664,\n" +
				"\"longitude\": 10.068644\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2563 Horner Weg/Morahtstieg\",\n" +
				"\"latitude\": 53.555503,\n" +
				"\"longitude\": 10.066926\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2432 Wandsbeker Marktstraﬂe/Wandsbeker Marktplatz\",\n" +
				"\"latitude\": 53.572433,\n" +
				"\"longitude\": 10.068955\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2435 Friedrich-Ebert-Damm / Lomerstraﬂe\",\n" +
				"\"latitude\": 53.584919,\n" +
				"\"longitude\": 10.079638\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2085 D¸rerstraﬂe/Beslerplatz- S Bahn Othmarschen\",\n" +
				"\"latitude\": 53.560395,\n" +
				"\"longitude\": 9.887159\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2566 Horner Rennbahn/ Meurer Weg\",\n" +
				"\"latitude\": 53.554193,\n" +
				"\"longitude\": 10.085077\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2433 OTTO Group / Wandsbeker Straﬂe\",\n" +
				"\"latitude\": 53.60026,\n" +
				"\"longitude\": 10.070397\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2712 Barkassenanleger/Mengestraﬂe\",\n" +
				"\"latitude\": 53.499748,\n" +
				"\"longitude\": 9.99647\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2438 Herthastraﬂe / Einkaufszentrum Bramfeld\",\n" +
				"\"latitude\": 53.611878,\n" +
				"\"longitude\": 10.075913\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2714 Berta-Krˆger-Platz/Wilhelm-Strauﬂ-Weg\",\n" +
				"\"latitude\": 53.497933,\n" +
				"\"longitude\": 10.007016\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2713 Neunenfelder Straﬂe/ Kurt-Emmerich-Platz\",\n" +
				"\"latitude\": 53.497232,\n" +
				"\"longitude\": 10.003843\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2801 Kanalplatz/Harburger Schlossstraﬂe\",\n" +
				"\"latitude\": 53.4665,\n" +
				"\"longitude\": 9.984148\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2814 Alter Postweg / Heimfelder Straﬂe\",\n" +
				"\"latitude\": 53.465276,\n" +
				"\"longitude\": 9.963537\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2802 Schellerdamm/Hausnummer22\",\n" +
				"\"latitude\": 53.464282,\n" +
				"\"longitude\": 9.986136\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2812 Harburger Ring/Neue Straﬂe\",\n" +
				"\"latitude\": 53.4606874,\n" +
				"\"longitude\": 9.9775729\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2811 Herbert-Wehner-Platz/Groﬂer Schippsee\",\n" +
				"\"latitude\": 53.460407,\n" +
				"\"longitude\": 9.983319\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2820 TU Harburg/Denickestraﬂe\",\n" +
				"\"latitude\": 53.460622,\n" +
				"\"longitude\": 9.969487\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2813 Harburger Rathausstraﬂe/Deichhausweg\",\n" +
				"\"latitude\": 53.4599586,\n" +
				"\"longitude\": 9.9804731\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2810 Moorstraﬂe/Bahnhof Harburg\",\n" +
				"\"latitude\": 53.457286,\n" +
				"\"longitude\": 9.987994\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2821 Eiﬂendorfer Pferdeweg/ Asklepios-Klinik Harburg\",\n" +
				"\"latitude\": 53.459,\n" +
				"\"longitude\": 9.951\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2830 Maretstraﬂe/Baererstraﬂe\",\n" +
				"\"latitude\": 53.453704,\n" +
				"\"longitude\": 9.982275\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2840 Reeseberg/EBV-Anzengruberstraﬂe\",\n" +
				"\"latitude\": 53.44896,\n" +
				"\"longitude\": 9.990306\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2841 Gotthelfweg/Auﬂenm¸hlenteich\",\n" +
				"\"latitude\": 53.447625,\n" +
				"\"longitude\": 9.980675\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2075 Paul-Ehrlich-Straﬂe/Asklepios Klinik Altona\",\n" +
				"\"latitude\": 53.554433,\n" +
				"\"longitude\": 9.900752\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2950 Hugo-Kirchberg-Straﬂe/Tesa\",\n" +
				"\"latitude\": 53.65429,\n" +
				"\"longitude\": 9.98528\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2399 Langenhorn Markt/Tangstedter Landstraﬂe\",\n" +
				"\"latitude\": 53.6487572,\n" +
				"\"longitude\": 10.0163663\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2362 Weidestraﬂe /Johannes-Prassek-Park\",\n" +
				"\"latitude\": 53.5808087,\n" +
				"\"longitude\": 10.0258479\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2720 Georg-Wilhelm-Straﬂe/Mankiewicz\",\n" +
				"\"latitude\": 53.494,\n" +
				"\"longitude\": 9.99\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2035 Nordalbinger Weg/Paul-Sorge-Straﬂe\",\n" +
				"\"latitude\": 53.6408352,\n" +
				"\"longitude\": 9.9500955\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2218 Randstraﬂe/ Stellingen\",\n" +
				"\"latitude\": 53.590248,\n" +
				"\"longitude\": 9.919356\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2031 Osdorfer Landstraﬂe/ Elbe-Einkaufszentrum\",\n" +
				"\"latitude\": 53.571629,\n" +
				"\"longitude\": 9.862423\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2261 Siemersplatz/Kollaustraﬂe\",\n" +
				"\"latitude\": 53.600072,\n" +
				"\"longitude\": 9.963929\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2377 Hindenburgstraﬂe/Mˆringbogen\",\n" +
				"\"latitude\": 53.606177,\n" +
				"\"longitude\": 10.012225\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2040 Ohnhorststraﬂe/Klein Flottbek\",\n" +
				"\"latitude\": 53.558587,\n" +
				"\"longitude\": 9.86216\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2436 Friedrich-Ebert-Damm/Helbingtwiete\",\n" +
				"\"latitude\": 53.588172,\n" +
				"\"longitude\": 10.095665\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2437 Charlie-Mills-Straﬂe/Friedrich-Ebert-Damm\",\n" +
				"\"latitude\": 53.59145,\n" +
				"\"longitude\": 10.106858\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2441 Rahlstedter Weg/Berner Heerweg\",\n" +
				"\"latitude\": 53.606246,\n" +
				"\"longitude\": 10.12021\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2451 Gr¸ndgensstraﬂe/CÈsar-Klein-Ring\",\n" +
				"\"latitude\": 53.610149,\n" +
				"\"longitude\": 10.058096\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2921 S Allermˆhe/Walter-Rudolphi-Weg\",\n" +
				"\"latitude\": 53.490126,\n" +
				"\"longitude\": 10.158515\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2396 Rˆntgenstraﬂe/Philips\",\n" +
				"\"latitude\": 53.6234809,\n" +
				"\"longitude\": 10.0122088\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2585 Billstedter Platz/÷jendorfer Weg\",\n" +
				"\"latitude\": 53.540096,\n" +
				"\"longitude\": 10.106106\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2912 Bahnhof Bergedorf/Johann-Meyer-Straﬂe\",\n" +
				"\"latitude\": 53.490496,\n" +
				"\"longitude\": 10.206105\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2913 Lohbr¸gger Markt/Ludwig-Rosenberg-Ring\",\n" +
				"\"latitude\": 53.494675,\n" +
				"\"longitude\": 10.206894\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2117 Groﬂe Elbstraﬂe/Van-der-Smissen-Straﬂe\",\n" +
				"\"latitude\": 53.543906,\n" +
				"\"longitude\": 9.94246\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2440 Traberweg/Eckerkoppel\",\n" +
				"\"latitude\": 53.598017,\n" +
				"\"longitude\": 10.101459\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2244 Lˆwenstraﬂe/Eppendorfer Weg\",\n" +
				"\"latitude\": 53.583795,\n" +
				"\"longitude\": 9.977916\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2128 Bleickenallee/Kinderkrankenhaus Altona\",\n" +
				"\"latitude\": 53.5512881,\n" +
				"\"longitude\": 9.9129134\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2228 Osterstraﬂe/Heuﬂweg\",\n" +
				"\"latitude\": 53.5759831,\n" +
				"\"longitude\": 9.9520709\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2353 Habichtstraﬂe/Steilshooper Straﬂe\",\n" +
				"\"latitude\": 53.593726,\n" +
				"\"longitude\": 10.0506397\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2677 Versmannstraﬂe/Am Hannoverschen Bahnhof\",\n" +
				"\"latitude\": 53.540782,\n" +
				"\"longitude\": 10.006168\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2394 Alsterdorf Markt/Evangelische Stiftung\",\n" +
				"\"latitude\": 53.6120909,\n" +
				"\"longitude\": 10.0239462\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2030 Niendorf Markt/Tibarg\",\n" +
				"\"latitude\": 53.6190722,\n" +
				"\"longitude\": 9.9513113\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2219 Langenfelder Damm/M¸ggenkampstraﬂe\",\n" +
				"\"latitude\": 53.580757,\n" +
				"\"longitude\": 9.938418\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2809 Neul‰nder Kamp/Eurofins\",\n" +
				"\"latitude\": 53.4616513,\n" +
				"\"longitude\": 10.0041327\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2229 Grandweg/Veilchenweg\",\n" +
				"\"latitude\": 53.589185,\n" +
				"\"longitude\": 9.964681\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2050 Elbchaussee/Teufelsbr¸ck\",\n" +
				"\"latitude\": 53.547425,\n" +
				"\"longitude\": 9.862013\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2429 Nordschleswiger Straﬂe/Dulsberg-Nord\",\n" +
				"\"latitude\": 53.585813,\n" +
				"\"longitude\": 10.06523\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2376 Djarkartaweg/Sengelmannstraﬂe\",\n" +
				"\"latitude\": 53.609163,\n" +
				"\"longitude\": 10.022685\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2564 Caspar-Voght-Straﬂe/Sievekingsallee\",\n" +
				"\"latitude\": 53.559893,\n" +
				"\"longitude\": 10.061703\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2570 Manshardtstraﬂe/EKZ Manshardtstraﬂe\",\n" +
				"\"latitude\": 53.559215,\n" +
				"\"longitude\": 10.107447\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2580 Riedweg/Vierbergen\",\n" +
				"\"latitude\": 53.547643,\n" +
				"\"longitude\": 10.095515\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2070 DESY/Notkestraﬂe\",\n" +
				"\"latitude\": 53.574537,\n" +
				"\"longitude\": 9.878527\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2119 Thadenstraﬂe/Holstenstraﬂe\",\n" +
				"\"latitude\": 53.555827,\n" +
				"\"longitude\": 9.950708\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2910 Wentorfer Straﬂe/Bezirksamt Bergedorf\",\n" +
				"\"latitude\": 53.487427,\n" +
				"\"longitude\": 10.22\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2911 Vierlandenstraﬂe/Johann-Adolf-Hasse-Platz\",\n" +
				"\"latitude\": 53.488449,\n" +
				"\"longitude\": 10.210913\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2920 S Nettelnburg/Friedrich-Frank-Bogen\",\n" +
				"\"latitude\": 53.488482,\n" +
				"\"longitude\": 10.182199\n" +
				"},\n" +
				"{\n" +
				"\"name\": \"2080 Bahrenfelder Chaussee/Von-Sauer-Straﬂe\",\n" +
				"\"latitude\": 53.5659729,\n" +
				"\"longitude\": 9.9108696\n" +
				"}\n" +
				"]";

		Gson gson = new Gson();
		//JSONArray jArray = new JSONArray(s);
		
		
		// Format fuer das umwandeln jsons in ein Javaobjekt festelegen
		Type type = new TypeToken<List<Map<String, String>>>() {
		}.getType();

		// Aus dem Json ein Javaobjekt erstellen
		allStations = gson.fromJson(s, type);

		
		
		
		
		Map<String, Double> tmpLatMap = new HashMap<String, Double>();
		Map<String, Double> tmpLongMap = new HashMap<String, Double>();
		if(allStations.size()>0){
			for(Map<String,String> station : allStations){
				tmpLatMap.put(station.get("name"), Double.valueOf(station.get("latitude")));
				tmpLongMap.put(station.get("name"), Double.valueOf(station.get("longitude")));
			}
		}
		latMap = tmpLatMap;
		longMap = tmpLongMap;
	}
	
	public JsonArray getPrediction(String stationsname){
		double[] predict = {0,0,0,0,0};
		if(longMap.isEmpty() && latMap.isEmpty()){
			updateStations();
		}
		try {
			predict = prediction.predict(stationsname, 3);
		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			MailNotification.sendMail(e);
		}
		return createJsonArrayFromDoubleArray(predict);
	}


	/**
	 * This method requests the amount of free Bikes of a specific Station at a specific Time from the StadtRadDBService.
	 * 
	 * @param name of the Station and TimeStamp.
	 * 
	 * @return returns amount of free bikes
	 */
	@Override
	public Integer getFreeBikesofStationAtSpecTime(String stationName, Long timeStamp) {
		Integer result = 0;
		Integer tmp = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(StadtradDBServerAdress + "/freeBikesofStationAtSpecTime")
			.queryString("station_name", stationName)
			.queryString("information_timestamp", timeStamp).asString();
    		
    		if((stringResponse != null) && !stringResponse.getBody().isEmpty()){
    				if(stringResponse.getBody().contains("404 Not found")){
    					result = -1;
    				}
    				else if(stringResponse.getBody().contains("400 Bad Request")){
    					result = -1;
    				}
    				else{
    					tmp = Integer.valueOf(stringResponse.getBody().toString());
        				if(tmp >= 0){
        					result = tmp;
        				}
        				else {
        					result = -1;
        				}
    				}
    		}
    		else {
    			result = -1;
    		}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
		}
    	
    	return result;
	}


	@Override
	public double getWeatherConditionAtTime(String stationsname, Long timeStamp) {
		double result = 0;
    	double tmp = 0;
		try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/weatherConditionAtTime")
			.queryString("time", timeStamp)
			.queryString("lon", getLongitude(stationsname))
			.queryString("lat", getLatitude(stationsname)).asString();
   
    		if((stringResponse != null) && (stringResponse.getBody().isEmpty())){
    			if(stringResponse.getBody().contains("404 Not found")){
					result = -1;
				}
				else if(stringResponse.getBody().contains("400 Bad Request")){
					result = -1;
				}
				else{	
    			tmp = Double.valueOf(stringResponse.getBody().toString());
    				if(tmp >= 0){
    					result = tmp;
    				}
    				else {
    					result = -1;
    				}
				}
    		}
    		else {
    			result = -1;
    		}
    	} catch (Exception e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
		}
    	
    	return result;
	}
	
	/**
	 * This method requests the temperature at a specific time from the WeatherService.
	 * 
	 * @param ?.
	 * 
	 * @return returns 
	 */

	@Override
	public double getTemperatureAtTime(String stationsname, Long timeStamp) {
	   	double result = 0;
	   	double tmp = 0;
    	try {
    		HttpResponse<String> stringResponse = Unirest.get(WeatherDBServerAdress + "/temperatureAtTime")
    				.queryString("time", timeStamp)
    				.queryString("lon", getLongitude(stationsname))
    				.queryString("lat", getLatitude(stationsname)).asString();
    		
    		if((stringResponse != null) && !stringResponse.getBody().isEmpty()){
    			if(stringResponse.getBody().contains("404 Not found")){
					result = -1;
				}
				else if(stringResponse.getBody().contains("400 Bad Request")){
					result = -1;
				}
				else{
    			tmp = Double.valueOf(stringResponse.getBody().toString());
				if(tmp >= 0){
					result = tmp;
				}
				else {
					result = -300;
				}
			}
		}
		else {
			result = -1;
		}
    	} catch (Exception  e) {
			// TODO Auto-generated catch block
    		MailNotification.sendMail(e);
    		result = -1;
		}
    	return result;
	}
	
	private double getLongitude(String stationsname){
		return longMap.get(stationsname);
	}
	
	private double getLatitude(String stationsname){
		return  latMap.get(stationsname);
	}

	/**
	 * This method creates a JSONObject out of a array of doubles
	 * 
	 * @param Array of doubles.
	 * 
	 * @return returns JSONObject for array of doubles
	 */
    public static JsonArray createJsonArrayFromDoubleArray(double[] doubleArray){
    	JsonArray jarray = new JsonArray();
    	
    	for(int i = 0; i < doubleArray.length;i++){
    		jarray.add(doubleArray[i]);
    	}
    	return jarray;
    }

	
    private List<Map<String, String>> getAllStations(){
    	Gson gson = new Gson();
    	List<Map<String, String>> itemsList = new ArrayList<Map<String, String>>();
    	HttpResponse<String> response;
		try {
			
			
			response = Unirest.get(StadtradDBServerAdress + "/allStations").asString();
			// Speichern des Jsons aus dem Requestbody
			String json = response.getBody();
			
			if(json!= null && !json.isEmpty()){
				if(json.contains("404 Not found")){
					return itemsList;
				}
				else if(json.contains("400 Bad Request")){
					return itemsList;
				}
				else{
					// Format fuer das umwandeln jsons in ein Javaobjekt festelegen
					Type type = new TypeToken<List<Map<String, String>>>() {
					}.getType();
	
					// Aus dem Json ein Javaobjekt erstellen
					itemsList = gson.fromJson(json, type);
					}
				}
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			MailNotification.sendMail(e);
		}
		return itemsList;	
    }
}

