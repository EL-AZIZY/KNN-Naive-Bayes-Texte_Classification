package ManipulationData;

import java.io.*;

public class DataLoader {
    public static String removePunctuation(String inputText) {
        // Remplace les caractères '.' et ':' par une chaîne vide
    	String Texte = inputText.replaceAll("[\\[\\]{}@&$#%+=`!?^\"\\_~'.:><)(;*/,|-]", "").toLowerCase();
    	Texte = Texte.replaceAll("[\\x00-\\x1F]","");
    	return Texte;
    }
	 public static String readFileToString(File file) throws IOException {
	        StringBuilder content = new StringBuilder();
//	        System.out.println(file);
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                content.append(line).append("\n");
	            }
	        }
	        return content.toString();
	    }

    public static void processFiles(String mainFolderPath) throws IOException {
        File mainFolder = new File(mainFolderPath);

        if (mainFolder != null) {
                File[] files = mainFolder.listFiles();

                if (files != null) {
                    for (File file : files) {
                        try {
                            String content = readFileToString(file);
//                            System.out.println("avant :"+content);
                            // Supprimer les stop words du contenu du fichier
                            String processed=removePunctuation(content);
                            String processedText = SupprimerStopWords.supprimerStopWords(processed);
                            String processedContent = STM.stemSentence(processedText);
//                            System.out.println("Apres :"+processedContent);

                            // Écrire le contenu traité dans le même fichier
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                                writer.write(processedContent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
 }
    
    public static void main(String[] arg) throws IOException {
    	String path = "Data\\Data_pret";
    	processFiles(path);
    	System.out.println("Done");
    }
}