package Knn_classifeiur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class TF_IDF {
@SuppressWarnings("null")
public static Map<String, Double> Frequences(String text) {
    List<String> listeDeMots = new ArrayList<>();
    String[] mots = text.split("\\s+");

    Collections.addAll(listeDeMots, mots);

    Map<String, Double> FrequenceMap = new HashMap<>();
    int D = listeDeMots.size();

    for (String mot : listeDeMots) {
        Double occurrence = FrequenceMap.getOrDefault(mot, 0.0);
        FrequenceMap.put(mot, occurrence + 1);
    }

    for (String mot : FrequenceMap.keySet()) {
        double frequenceRelative = FrequenceMap.get(mot) / D;
        FrequenceMap.put(mot, frequenceRelative);
    }

    return FrequenceMap;
}
    public static Map<String, String> loadDocumentsFromFolder(String folderPath) {
    	Map<String, String> corpus = new HashMap<>();

        File dossier = new File(folderPath);
        File[] fichiers = dossier.listFiles();

        if (fichiers != null) {
            for (File fichier : fichiers) {
                if (fichier.isFile()) {
                    try {
                    	
                    	String FileName = fichier.getName();

                        String contenu = new String(Files.readAllBytes(fichier.toPath()));
                        corpus.put(FileName, contenu);
                    } catch (IOException e) {
                        System.err.println("Erreur de lecture du fichier : " + e.getMessage());
                    }
                }
            }
        }

        return corpus;
    }

    public static Map<String, Double> calculateIDF(String path) {
    	Map<String, String> corpus = loadDocumentsFromFolder(path);
        int totalDocuments = corpus.size();
        Map<String, Integer> documentFrequency = new HashMap<>();


        for (String document : corpus.values()) {

            String[] mots = document.split("\\s+");

            Set<String> termesUniques = new HashSet<>();
            for (String mot : mots) {
                termesUniques.add(mot);
            }

            for (String termeUnique : termesUniques) {
                documentFrequency.put(termeUnique, documentFrequency.getOrDefault(termeUnique, 0) + 1);
            }
        }


        Map<String, Double> idfValues = new HashMap<>();
        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            String terme = entry.getKey();
            int documentsContenantTerme = entry.getValue();

            double idf = Math.log((double) totalDocuments / (double) documentsContenantTerme);
            idfValues.put(terme, idf);
        }

        return idfValues;
    }
    
	public static Map<String,Map<String,Double>> calculatorTF(String Path) throws FileNotFoundException{
		Map<String,Map<String,Double>>  TF = new HashMap<>();
		File dossier = new File(Path);
	    File[] fichiers = dossier.listFiles();

        for (File fichier : fichiers) {

            if (fichier.isFile()) {
                try {
                	String FileName = fichier.getName();
                    Scanner scannerFichier = new Scanner(fichier);
                    Map<String, Double> motsCompteur = new HashMap<>();
                    int totalMots = 0;


                    while (scannerFichier.hasNext()) {
                        String mot = scannerFichier.next().toLowerCase(); 
                        motsCompteur.put(mot, motsCompteur.getOrDefault(mot, (double) 0) + 1);
                        totalMots++;
                    }
                    scannerFichier.close();

                    Map<String, Double> motsFrequences = new HashMap<>();
                    for (Entry<String, Double> entry : motsCompteur.entrySet()) {
                        String mot = entry.getKey();
                        Double occurrences = entry.getValue();
                        double frequence = (double) occurrences / totalMots;
                        motsFrequences.put(mot, frequence);
                    }


                    TF.put(FileName, motsFrequences);
                } catch (FileNotFoundException e) {
                    System.err.println("Fichier non trouvé : " + e.getMessage());
                }
            }
        }

	        return TF;
	}
	
	 public static Map<String, Map<String, Double>> calculerTFIDF(Map<String, Double> idfValues, Map<String, Map<String, Double>> fichiersMotsFrequences) {
	        Map<String, Map<String, Double>> tfIdfValues = new HashMap<>();


	        for (Map.Entry<String, Map<String, Double>> entry : fichiersMotsFrequences.entrySet()) {
	            String nomFichier = entry.getKey();
	            Map<String, Double> motsFrequences = entry.getValue();
	            Map<String, Double> tfIdfFrequences = new HashMap<>();


	            for (Map.Entry<String, Double> motEntry : motsFrequences.entrySet()) {
	                String mot = motEntry.getKey();
	                double tf = motEntry.getValue();
	                double idf = idfValues.getOrDefault(mot, 0.0); 


	                double tfIdf = tf * idf;
	                tfIdfFrequences.put(mot, tfIdf); 
	            }


	            tfIdfValues.put(nomFichier, tfIdfFrequences);
	        }

	        return tfIdfValues;
	    }
}
