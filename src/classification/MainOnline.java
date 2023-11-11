package classification;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ManipulationData.ProbabilitiesCalculator;
public class MainOnline{


    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
    	
    	String Data= "Data\\train";    	
        
    	String uniqueWordsFile = "Data\\unique_words.txt";
        
    	Map<String, Double>ClassProbabilities=ProbabilitiesCalculator.calculateClassProbabilities(Data);
        
    	Map<String, Map<String, Double>> WordProbabilities = ProbabilitiesCalculator.calculateWordProbabilities(uniqueWordsFile,Data);
        
    	NaiveBayesClassifier classifier = new NaiveBayesClassifier(WordProbabilities,ClassProbabilities);
        
    	Scanner scanner = new Scanner(System.in);
        
    	boolean continueProcessing = true;

        while (continueProcessing) {
           
        	NaiveBayesClassifier.start(classifier);
            
        	System.out.println();
                        
        	System.out.println("1 => pour continuer \t 0 pour exit ");

            int input = scanner.nextInt();
            
            if (input == 0) {
            
            	continueProcessing = false;
                
            	System.out.println("Au revoir ! ");
                
            }            
        }
  }
}
