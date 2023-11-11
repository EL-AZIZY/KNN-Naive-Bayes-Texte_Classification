package Knn_classifeiur;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

import ManipulationData.STM;
import ManipulationData.SupprimerStopWords;



public class KnnClassifieur {

    private static Map<String, Map<String, Double>> data;

    @SuppressWarnings("static-access")
	public KnnClassifieur(Map<String, Map<String, Double>> result) {
        this.data = result;
    }
    public static  String ResultatFinale( String text) {
    	Map<String, Double> query = TF_IDF.Frequences(text);
    	Map<String,Double> fileNames = new HashMap<String, Double>();

        for (String fileName : data.keySet()) {
            Map<String, Double> fileVector = data.get(fileName);
            double similarity = cosines(query, fileVector);
            fileNames.put(fileName, similarity);

        }
        
                
        List<Map.Entry<String, Double>> list = new ArrayList<>(fileNames.entrySet());
        
        Collections.sort(list, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));
        
        List<String> fileNameFinale = new ArrayList<>();

        for (Map.Entry<String, Double> entry : list) {
            fileNameFinale.add(entry.getKey());
        }
        List<String> troisPremiers = fileNameFinale.subList(0, Math.min(fileNameFinale.size(), 3));
        

        
        String mostFrequentClass = troisPremiers.stream()
                .map(fileName -> fileName.split("_")[1])
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return mostFrequentClass;
                
    }
    public String classify(String text) {
        String bestClass = null;
        Map<String, Double> frequence = TF_IDF.Frequences(text);
        double bestSimilarity = Double.MIN_VALUE;

        for (Entry<String, Map<String, Double>> entry : data.entrySet()) {
            String fileName_class = entry.getKey();
            String fileClass = fileName_class.split("_")[1];

            double similarity = cosines(frequence, entry.getValue());

            if (similarity > bestSimilarity) {
                bestSimilarity = similarity;
                bestClass = fileClass;
            }
        }

        return bestClass;
    }

    public static double cosines(Map<String, Double> query, Map<String, Double> fileVector) {
        double dotProduct = 0.0;
        double magnitudeV1 = 0.0;
        double magnitudeV2 = 0.0;

        for (String term : query.keySet()) {
            if (fileVector.containsKey(term)) {
                dotProduct += query.get(term) * fileVector.get(term);
            }
            magnitudeV1 += Math.pow(query.get(term), 2);
        }

        for (String term : fileVector.keySet()) {
            magnitudeV2 += Math.pow(fileVector.get(term), 2);
        }

        if (magnitudeV1 == 0.0 || magnitudeV2 == 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(magnitudeV1) * Math.sqrt(magnitudeV2));
    }

    public static void start(KnnClassifieur Knn) throws Exception {
        System.out.println("Entrez votre texte : ");
        Scanner sc = new Scanner(System.in);

        try {
            String userInput = sc.nextLine();

            if (userInput.isEmpty()) {
                System.out.println("Vous n'avez pas saisi de texte.");
                return;
            }

            String stemmedText = STM.stemSentence(userInput);
            String txt = SupprimerStopWords.supprimerStopWords(stemmedText);
            String Pred = KnnClassifieur.ResultatFinale(txt);

            // Afficher les résultats
            System.out.println("Classifieur dit que ce texte est de classe : " + Pred);
        } finally {
            sc.close();
        }
    }
}
