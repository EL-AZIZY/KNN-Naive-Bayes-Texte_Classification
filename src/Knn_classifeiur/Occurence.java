package Knn_classifeiur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ManipulationData.DataLoader;
import ManipulationData.STM;
import ManipulationData.SupprimerStopWords;

public class Occurence {

	public static Map<String, Map<String, Map<String, Integer>>> calculerOccurrence(
	        String dossierFichiers) throws Exception {
	    Map<String, Map<String, Map<String, Integer>>> result = new HashMap<>();

//	    Set<String> motsUniques = lireMotsUniques(fichierUniqueWords);

	    File dossier = new File(dossierFichiers);
	    File[] fichiers = dossier.listFiles();
	    
	    if (fichiers != null) {
	        for (File fichier : fichiers) {
	            
	            	
//	            	System.out.println(i++);
	                String[] part = fichier.getName().split("_");
	                String fileName = part[0];
	                String className = part[1];
//	                System.out.println(fichier + " Name " + fileName);

	                if (!result.containsKey(className)) {
	                    result.put(className, new HashMap<>());
	                }
	                BufferedReader reader = new BufferedReader(new FileReader(fichier));
	                StringBuilder contenuBuilder = new StringBuilder();
	                String ligne;
	                while ((ligne = reader.readLine()) != null) {
	                    contenuBuilder.append(ligne).append("\n");
	                }
	                reader.close();
	                String contenu = contenuBuilder.toString();
	                
	                Map<String, Integer> occurrenceMots = calculerOccurrencePourFichier(contenu);

	                result.get(className).put(fileName, occurrenceMots);
	            
	        }
	        System.out.println("Fin caclule Occurence");
	    }

	    return result;
	}

@SuppressWarnings("null")
public static Map<String,Integer> calculerOccurrencePourFichier(String contenu) {
		
	    Map<String, Integer> FrequenceMap = new HashMap<>();
	    String tst=SupprimerStopWords.supprimerStopWords(contenu);
        String txt= STM.stemSentence(tst);
        String text = DataLoader.removePunctuation(txt) ;
        
    	List<String> listeDeMots = Arrays.asList(text.split("\\s+"));
    	
//    	System.out.println(listeDeMots);
    	
//    	int D = listeDeMots.size();

    	 for (String mot : listeDeMots) {
    		 
 	        Integer occurrence = FrequenceMap.getOrDefault(mot, (int) 0);
 	        
 	        FrequenceMap.put(mot, (int) (occurrence + 1));
 	    }
 	    
 	    //Calculer le frequence
         for (String mot : FrequenceMap.keySet()) {
             Integer frequenceRelative = FrequenceMap.get(mot) ;
             FrequenceMap.put(mot, frequenceRelative);	    	
 	    }
	    

	    return FrequenceMap;
	}
//	public static Map<String, Integer> calculerOccurrencePourFichier(String contenu, Set<String> motsUniques)
//		    throws Exception {
//		    String tst=SupprimerStopWords.supprimerStopWords(contenu);
//            String txt= STM.stemSentence(tst);
//            String text = DataLoader.removePunctuation(txt) ;
//		    Map<String, Integer> occurrenceMots = new HashMap<>();
//		    
////		    String[] lignes = text.split("\n");
//		    
////		    for (String ligne : lignes) {
////		    	String motss = t
//		    	String[] mots = text.split("\\s+");
//		        
//		    	for (String mot : mots) {
//		        if (mot !="") {
//		    		mot = " "+mot.replaceAll("[^a-zA-Z]", "").toLowerCase()+" ";
//		    		    
//		    		Integer Occu = occurrenceMots.getOrDefault(mot, 0) + 1;
//		    		occurrenceMots.put(mot,Occu );  		
//		    		
//		        }
//		    	occurrenceMots.remove("  ");
//		    }
//		    return occurrenceMots;
//		}
//
	    public static Set<String> lireMotsUniques(String fichier) throws IOException {
	        Set<String> motsUniques = new HashSet<>();
	        BufferedReader reader = new BufferedReader(new FileReader(fichier));
	        String ligne;

	        while ((ligne = reader.readLine()) != null) {

	        	String[] mots = ligne.split("\\s+");
	            for (String mot : mots) {
	                mot = mot.replaceAll("[^a-zA-Z]", "").toLowerCase();
	                motsUniques.add(mot);
	            }
	        }

	        reader.close();
	        return motsUniques;
	    }
	    public static void main(String[] args) throws Exception {
//	        String dossierFichiers = "Data\\Data_pret";
//	        String fichierUniqueWords = "Data\\unique_words.txt";
//	       
//	        try {
//	            // Calculer les occurrences des mots pour les fichiers dans le dossier spécifié
//	            Map<String, Map<String, Map<String, Integer>>> result = Occurence.calculerOccurrence(dossierFichiers, fichierUniqueWords);
//
//	            // Sauvegarder le résultat dans un fichier
////	            String fichierResultat = "Data\\validation\\resultat.txt";
////	            saveOccurrenceData(result, fichierResultat);
//
//	            System.out.println("Occurrences des mots calculées et enregistrées avec succès.");
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	        Set<String> unique = lireMotsUniques("Data\\unique_words.txt");
	        String URL = "Data\\train\\0000000_alt.atheism";
	        BufferedReader reader = new BufferedReader(new FileReader(URL));
            StringBuilder contenuBuilder = new StringBuilder();
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                contenuBuilder.append(ligne).append("\n");
            }
            reader.close();
            String contenu = contenuBuilder.toString();
            
            Map<String, Integer> occurrenceMots = calculerOccurrencePourFichier(contenu);
            System.out.println(occurrenceMots);
	    }
//
//	    public static void saveOccurrenceData(Map<String, Map<String, Map<String, Integer>>> occurrenceData, String filePath) {
//	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//	            for (Map.Entry<String, Map<String, Map<String, Integer>>> classEntry : occurrenceData.entrySet()) {
//	                String className = classEntry.getKey();
//	                writer.write("Class Name: " + className);
//	                writer.newLine();
//
//	                Map<String, Map<String, Integer>> fileMap = classEntry.getValue();
//	                for (Map.Entry<String, Map<String, Integer>> fileEntry : fileMap.entrySet()) {
//	                    String fileName = fileEntry.getKey();
//	                    writer.write("\tFile Name: " + fileName);
//	                    writer.newLine();
//
//	                    Map<String, Integer> wordOccurrences = fileEntry.getValue();
//	                    for (Map.Entry<String, Integer> wordEntry : wordOccurrences.entrySet()) {
//	                        String word = wordEntry.getKey();
//	                        int occurrence = wordEntry.getValue();
//	                        writer.write("\t\t" + word + " : " + occurrence);
//	                        writer.newLine();
//	                    }
//	                }
//	                writer.newLine();
//	            }
//	            System.out.println("Données d'occurrence enregistrées avec succès dans le fichier : " + filePath);
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
}


