package classification;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import ManipulationData.DataLoader;
import ManipulationData.STM;
import ManipulationData.SupprimerStopWords;

public class NaiveBayesClassifier {

    private static  Map<String, Map<String, Double>> wordProbabilities;
    private static Map<String, Double> classProbabilities;

    public NaiveBayesClassifier(Map<String, Map<String, Double>> WordProbabilities,Map<String, Double> ClassProbabilities) throws IOException {
        // Charger les probabilités des mots à partir du fichier XML
        NaiveBayesClassifier.wordProbabilities = WordProbabilities;
        NaiveBayesClassifier.classProbabilities = ClassProbabilities;

    }
   

    public  String classify(String text) {
        String[] words = text.split("\\s+");
        double maxProbability = Double.NEGATIVE_INFINITY;
        String predictedClass = null;

        for (Map.Entry<String, Map<String, Double>> classEntry : wordProbabilities.entrySet()) {
            String className = classEntry.getKey();
//            System.out.println(className);
            Map<String, Double> probabilities = classEntry.getValue();
            double classProbability = classProbabilities.get(className);
//            System.out.println(classProbability);
            
            for (String word : words) {
                if (probabilities.containsKey(word)) {
                    // Utilisez le log des probabilités pour éviter les underflows avec de petits nombres
                    classProbability += probabilities.get(word);
                } 
            }

            if (classProbability > maxProbability) {
                maxProbability = classProbability;
                predictedClass = className;
                }
       }
        
        return predictedClass;
   }
    public static void start(NaiveBayesClassifier classifier) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        System.out.println("Entrez votre texte :");
        String textToClassify = scanner.nextLine();

        if (textToClassify.isEmpty()) {
            System.out.println("Vous n'avez pas saisi de texte.");
        }
        String processed=DataLoader.removePunctuation(textToClassify);
        String processedText = SupprimerStopWords.supprimerStopWords(processed);
        String processedContent = STM.stemSentence(processedText);
        String ClassPredit = classifier.classify(processedContent);
		
		System.out.println("Mon model dis que c'est : "+ClassPredit);
    }
}


