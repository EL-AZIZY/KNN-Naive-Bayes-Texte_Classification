package Metrics;

import java.util.ArrayList;

public class Evaluation {
	
	  
	
	
	
	public static double calculateF1Score(ArrayList<String> actualValues, ArrayList<String> predictedValues) {
        if (actualValues.size() != predictedValues.size()) {
            throw new IllegalArgumentException("Les listes doivent avoir la même taille.");
        }

        int truePositives = 0;
        int falsePositives = 0;
        int falseNegatives = 0;

        for (int i = 0; i < actualValues.size(); i++) {
            String actual = actualValues.get(i);
            String predicted = predictedValues.get(i);

            if (actual.equals(predicted)) {
                truePositives++;
            } else {
                if (actualValues.contains(predicted)) {
                    falsePositives++;
                } else {
                    falseNegatives++;
                }
            }
        }

        if (truePositives == 0) {
            return 0.0; // Avoid division by zero
        }

        double precision = (double) truePositives / (truePositives + falsePositives);
        double recall = (double) truePositives / (truePositives + falseNegatives);

        return 2.0 * (precision * recall) / (precision + recall);
    }

	

	
	
	
}


