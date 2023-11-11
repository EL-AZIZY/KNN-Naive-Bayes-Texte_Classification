package Metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import classification.NaiveBayesClassifier;
import Knn_classifeiur.KnnClassifieur;
import Knn_classifeiur.Occurence;
import ManipulationData.ProbabilitiesCalculator;
import Knn_classifeiur.TF_IDF;


public class CroseValidation {
	
	public static String chemin = "Data\\train";
	public static String destination = "Data\\validation";
	public static String UniqueMots = "Data\\validation\\UniqueMots.txt";
	public static int NbrFolder = 5;
	
	
	@SuppressWarnings("resource")
	public static void Start() throws Exception {
		System.out.println("Donne moi le chemin de dossier : (Data\\Data_pret)");
		chemin = new Scanner(System.in).nextLine();
		
		System.out.println("Donne moi le nom du model NB ou KNN");
		String Model = new Scanner(System.in).nextLine();
		
		System.out.println("Donne moi le nombre de folder");
		NbrFolder = new Scanner(System.in).nextInt();
		System.out.println("Chargement...");

//		Map<String, ArrayList<String>> ClassFilePath = upload.ClassFilesPathe(chemin);
		List<Path> Folder =  splitDataIntoKFolds(chemin,destination);

        ArrayList<String> Actuell = new ArrayList<String>();
        ArrayList<String> Predit = new ArrayList<String>();
		double F1Score = 0;
		

			File Test = Folder.get(0).toFile();
			File[] Fichies = Test.listFiles();
			String Train = Folder.get(1).toString();
			if(Model.equals("NB")) {
		    
				Map<String, Map<String, Double>> Prob = ProbabilitiesCalculator.calculateWordProbabilities(UniqueMots,Train);
		    	
				Map<String, Double>ClassProbabilities=ProbabilitiesCalculator.calculateClassProbabilities(Folder.get(1).toString());
		    	
				NaiveBayesClassifier classifieur = new NaiveBayesClassifier(Prob,ClassProbabilities);
		        
				for (File TestF : Fichies) {
	            
					BufferedReader reader = new BufferedReader(new FileReader(TestF));
	                
					StringBuilder contenuBuilder = new StringBuilder();
	                
					String ligne;
	                
					while ((ligne = reader.readLine()) != null) {
	                    contenuBuilder.append(ligne).append("\n");
	                }
	                reader.close();
	                String contenu = contenuBuilder.toString(); 
		        	String ClassPredit = classifieur.classify(contenu);
		        	String ClassReel = TestF.getName().split("_")[1];
		        	
		        	Actuell.add(ClassReel);
		        	Predit.add(ClassPredit);
		        }
		        
		        // F1 Score
		        
		        double F1 = Evaluation.calculateF1Score(Actuell, Predit);
		        F1Score = F1;
			
			
			
			}else if (Model.equals("KNN")) {
				
				Map<String, Double> IDF = TF_IDF.calculateIDF(chemin);
				 Map<String,Map<String,Double>> TF = TF_IDF.calculatorTF(chemin);
				 Map<String, Map<String, Double>> result =TF_IDF.calculerTFIDF(IDF,TF);
				 KnnClassifieur KNN = new KnnClassifieur(result);
				 for (File TestF : Fichies) {
			            
						BufferedReader reader = new BufferedReader(new FileReader(TestF));
		                
						StringBuilder contenuBuilder = new StringBuilder();
		                
						String ligne;
		                
						while ((ligne = reader.readLine()) != null) {
		                    contenuBuilder.append(ligne).append("\n");
		                }
		                reader.close();
		                String contenu = contenuBuilder.toString(); 
			        	String ClassPredit = KNN.classify(contenu);
			        	
			        	String ClassReel = TestF.getName().split("_")[1];
			        	System.out.println(ClassPredit +"  Real class est :" +ClassReel);
			        	
			        	Actuell.add(ClassReel);
			        	Predit.add(ClassPredit);
			        }
			        
			        // F1 Score
			        
			        double F1 = Evaluation.calculateF1Score(Actuell, Predit);
			        F1Score=F1;
			}
		
		//Affichage de resultat
		System.out.println();
		System.out.println("Le Moyenne de F1 Score : "+F1Score*100);
	
	}
	
	
	
    public static Map<Integer, ArrayList<String>> splitCrossValidation(Map<String, ArrayList<String>> dataMap, int numFolds) {
        Map<Integer, ArrayList<String>> crossValidationData = new HashMap<>();

        // Initialize random number generator
        Random rand = new Random();

        for (int i = 0; i < numFolds; i++) {
            crossValidationData.put(i, new ArrayList<>());
        }

        for (Map.Entry<String, ArrayList<String>> entry : dataMap.entrySet()) {
            ArrayList<String> files = entry.getValue();

            for (String file : files) {
                int foldIndex = rand.nextInt(numFolds); // Randomly assign to a fold
                crossValidationData.get(foldIndex).add(file);
            }
        }

        return crossValidationData;
    }
	
    
    public static List<Path> splitDataIntoKFolds( String sourceFolderPath, String destinationParentFolderPath) throws IOException {
        List<Path> folderPaths = new ArrayList<>();

        File sourceFolder = new File(sourceFolderPath);
        File[] files = sourceFolder.listFiles();

        if (files != null) {
            // Mélanger les fichiers de manière aléatoire
            List<File> shuffledFiles = Arrays.asList(files);
            Collections.shuffle(shuffledFiles);

            // Créez le dossier de test
            File testFolder = new File(destinationParentFolderPath, "test folder");
            testFolder.mkdirs();

            // Calculer la taille du dossier de test (1/5 des fichiers)
            int testSize = shuffledFiles.size() / NbrFolder;

            // Copiez les fichiers dans le dossier de test
            for (int i = 0; i < testSize; i++) {
                File file = shuffledFiles.get(i);
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get(testFolder.getPath(), file.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Créez le dossier d'entraînement
            File trainFolder = new File(destinationParentFolderPath, "train folder");
            trainFolder.mkdirs();

            // Copiez les fichiers restants dans le dossier d'entraînement
            for (int i = testSize; i < shuffledFiles.size(); i++) {
                File file = shuffledFiles.get(i);
                Path sourcePath = file.toPath();
                Path destinationPath = Paths.get(trainFolder.getPath(), file.getName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }

            folderPaths.add(Paths.get(testFolder.getPath()));  
            folderPaths.add(Paths.get(trainFolder.getPath())); 
        }

        return folderPaths;
    }
}
