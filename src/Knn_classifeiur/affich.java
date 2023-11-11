package Knn_classifeiur;

import java.io.FileNotFoundException;
import java.util.Map;

public class affich {
	 public static void printMapOfMaps(Map<String, Map<String, Double>> mapOfMaps) {
	        for (Map.Entry<String, Map<String, Double>> outerEntry : mapOfMaps.entrySet()) {
	            System.out.println("Key (Outer Map): " + outerEntry.getKey());

	            Map<String, Double> innerMap = outerEntry.getValue();

	            for (Map.Entry<String, Double> innerEntry : innerMap.entrySet()) {
	                System.out.println("  Key (Inner Map): " + innerEntry.getKey() + ", Value: " + innerEntry.getValue());
	            }
	        }
	    }
	  public static void main(String[] args) throws FileNotFoundException {
	        // Exemple d'utilisation
		  String path = "Data\\train";
	        Map<String, Map<String, Double>> exampleMap = TF_IDF.calculatorTF(path);
	        printMapOfMaps(exampleMap);
	    }
}
