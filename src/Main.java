import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import ManipulationData.ProbabilitiesCalculator;
import Metrics.CroseValidation;
import classification.NaiveBayesClassifier;
import Knn_classifeiur.KnnClassifieur;
import Knn_classifeiur.Occurence;
import Knn_classifeiur.TF_IDF;
public class Main {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {   
		
		while(true) {
			String Data= "Data\\train";    	
			String fichierUniqueWords = "Data\\unique_words.txt";

			System.out.println("\n\n\t\t\tChoisit une option : \n");
			System.out.println("Test Naive Bayes : 1 \t  KNN : 2 \t Validation Croiser : 3 \n");
			System.out.println("\t\t\t0 pour quiter ");

	 
			int choix = new Scanner(System.in).nextInt();
			
			if (choix == 1) {
		    	Map<String, Double>ClassProbabilities=ProbabilitiesCalculator.calculateClassProbabilities(Data);
		        
		    	Map<String, Map<String, Double>> WordProbabilities = ProbabilitiesCalculator.calculateWordProbabilities(fichierUniqueWords,Data);
		        
		    	NaiveBayesClassifier classifier = new NaiveBayesClassifier(WordProbabilities,ClassProbabilities);
				
		    	NaiveBayesClassifier.start(classifier);	
			}
			
			if (choix == 2) {
				
				Map<String, Double> IDF = TF_IDF.calculateIDF(Data);
				 Map<String,Map<String,Double>> TF = TF_IDF.calculatorTF(Data);
				 Map<String, Map<String, Double>> result =TF_IDF.calculerTFIDF(IDF,TF);
				 KnnClassifieur KNN = new KnnClassifieur(result);
								
				KnnClassifieur.start(KNN);
			}
			
			if (choix == 3) {
				CroseValidation.Start();
			}
			
			if (choix == 0) {
				System.out.println("A bien tot :)");
				break;
			}
			
			
		}
		
		
	}


}