package ManipulationData;

import java.io.BufferedReader;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.File;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
//import java.util.Map.Entry;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;

//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;

import java.util.Set;

//import java.io.OutputStream;
//import java.util.Properties;
//import java.io.FileInputStream;
//import java.io.InputStream;


public class ProbabilitiesCalculator {

	public static Map<String, Double> calculateClassProbabilities(String dataFolderPath) {
	    Map<String, Double> classProbabilities = new HashMap<>();

	    File dataFolder = new File(dataFolderPath);
	    File[] files = dataFolder.listFiles();

	    int totalExamples = 0;

	    // Compter le nombre d'exemples dans chaque classe
	    if (files != null) {
	        for (File file : files) {
	            if (file.isFile()) {
	                String fileName = file.getName();
	                String className = fileName.substring(fileName.lastIndexOf("_") + 1);
	                classProbabilities.put(className, classProbabilities.getOrDefault(className, 0.0) + 1.0);
	                totalExamples++;
	            }
	        }

	        // Calculer les probabilités en divisant par le nombre total d'exemples
	        for (Map.Entry<String, Double> entry : classProbabilities.entrySet()) {
	            entry.setValue(entry.getValue() / totalExamples);
	        }

	    }
		return classProbabilities;
	}
    public static Map<String, Map<String, Double>> calculateWordProbabilities(String uniqueWordsFile, String folder) throws IOException {
        Set<String> uniqueWords = loadUniqueWords(uniqueWordsFile);

        Map<String, Map<String, Integer>> wordCountsByClass = new HashMap<>();

        // Count word occurrences in each class
         File dataFolder = new File(folder);

        for (File file : dataFolder.listFiles()) {
            if (file.isFile()) {
//                String fileName = file.getName().split("_")[0]; // Extract file name
//            	System.out.println(file.getName());
                String className = file.getName().split("_")[1]; // Extract class name

                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                Map<String, Integer> wordCounts = wordCountsByClass.getOrDefault(className, new HashMap<>());

                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        if (uniqueWords.contains(word)) {
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }
                }

                wordCountsByClass.put(className, wordCounts);
                reader.close();
            }
        }

        // Calculate probabilities
        Map<String, Map<String, Double>> wordProbabilitiesByClass = new HashMap<>();
        for (String className : wordCountsByClass.keySet()) {
            Map<String, Integer> wordCounts = wordCountsByClass.get(className);
            double totalWords = wordCounts.values().stream().mapToInt(Integer::intValue).sum();
            Map<String, Double> wordProbabilities = new HashMap<>();

            for (String word : wordCounts.keySet()) {
                double probability = (double) wordCounts.get(word) / totalWords;
                wordProbabilities.put(word, probability);
            }

            wordProbabilitiesByClass.put(className, wordProbabilities);
        }

        return wordProbabilitiesByClass;       
    }
    private static Set<String> loadUniqueWords(String uniqueWordsFile) {
        Set<String> uniqueWords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(uniqueWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                uniqueWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueWords;
    }
//  
//    public static Map<String, Map<String, Double>> loadWordProbabilitiesFromXML(String xmlFilePath) {
//        Map<String, Map<String, Double>> wordProbabilities = new HashMap<>();
//
//        try {
//            File xmlFile = new File(xmlFilePath);
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(xmlFile);
//            doc.getDocumentElement().normalize();
//
//            NodeList classNodes = doc.getElementsByTagName("Class");
//
//            for (int temp = 0; temp < classNodes.getLength(); temp++) {
//                Node classNode = classNodes.item(temp);
//
//                if (classNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element classElement = (Element) classNode;
//                    String className = classElement.getAttribute("name");
//
//                    Map<String, Double> wordProbabilitiesForClass = new HashMap<>();
//                    NodeList wordNodes = classElement.getElementsByTagName("word");
//
//                    for (int i = 0; i < wordNodes.getLength(); i++) {
//                        Node wordNode = wordNodes.item(i);
//
//                        if (wordNode.getNodeType() == Node.ELEMENT_NODE) {
//                            Element wordElement = (Element) wordNode;
//                            String word = wordElement.getAttribute("name");
//                            double probability = Double.parseDouble(wordElement.getTextContent());
//                            wordProbabilitiesForClass.put(word, probability);
//                        }
//                    }
//
//                    wordProbabilities.put(className, wordProbabilitiesForClass);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return wordProbabilities;
//    }
//
    
//    public static void saveWordProbabilitiesToXml(Map<String, Map<String, Double>> wordProbabilities,String xmlFilePath ) {
////    	String xmlFilePath = "Data\\word_probabilities.xml";
//        try (FileWriter fileWriter = new FileWriter(new File(xmlFilePath))) {
//            fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + System.lineSeparator());
//            fileWriter.write("<Probabilite>" + System.lineSeparator());
//
//            for (Entry<String, Map<String, Double>> classEntry : wordProbabilities.entrySet()) {
//                String className = classEntry.getKey();
//                Map<String, Double> probabilities = classEntry.getValue();
//
//                fileWriter.write("    <Class name=\"" + className + "\">" + System.lineSeparator());
//
//                for (Entry<String, Double> wordEntry : probabilities.entrySet()) {
//                    String word = wordEntry.getKey();
//                    Double probability = wordEntry.getValue();
//
//                    fileWriter.write("        <word name=\"" + word + "\">" + probability + "</word>"
//                            + System.lineSeparator());
//                }
//
//                fileWriter.write("    </Class>" + System.lineSeparator());
//            }
//
//            fileWriter.write("</Probabilite>");
//            System.out.println("Les probabilités des mots ont été enregistrées dans le fichier XML : " + xmlFilePath);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    
//    public static void saveClassProba(Map<String, Double> classProba,String filePath) {
////    	String filePath = "Data\\class_probabilities.xml";
//        Properties properties = new Properties();
//        classProba.forEach((key, value) -> properties.setProperty(key, String.valueOf(value)));
//
//        try (OutputStream output = new FileOutputStream(filePath)) {
//            properties.storeToXML(output, "Map Data");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static Map<String, Double> loadClassProba(String filePath) {
//        Map<String, Double> map = new HashMap<>();
//
//        try (InputStream input = new FileInputStream(filePath)) {
//            Properties properties = new Properties();
//            properties.loadFromXML(input);
//
//            properties.forEach((key, value) -> map.put((String) key, Double.parseDouble((String) value)));
//        } catch (Exception e) {
//            e.printStackTrace();}
//		return map;
//		}
//
}