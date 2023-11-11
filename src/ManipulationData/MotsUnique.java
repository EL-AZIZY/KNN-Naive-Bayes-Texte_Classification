package ManipulationData;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class MotsUnique {

    public static void writeUniqueWordsToFile(String dataFolderPath,String path) {
        Set<String> uniqueWords = new HashSet<>();

        File mainFolder = new File(dataFolderPath);
        if (mainFolder.exists() && mainFolder.isDirectory()) {
            File[] classFolders = mainFolder.listFiles();
            if (classFolders != null) {
                    File[] files = mainFolder.listFiles();
                    if (files != null) {
                        for (File file : files) {
                            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    String[] words = line.split("\\s+");
                                    for (String word : words) {
                                        uniqueWords.add(word.toLowerCase());
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
                }
            } else {
                System.out.println("No class folders found.");
                return;
            }
        } else {
            System.out.println("Invalid data folder path.");
            return;
        }

        // Écrire les mots uniques dans un fichier texte
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String word : uniqueWords) {
                writer.write(word);
                writer.newLine();
            }
            System.out.println("unique mots sont bien charger dans le fichier : "+ path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
