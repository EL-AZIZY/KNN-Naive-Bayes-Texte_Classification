package ManipulationData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DataSplitter {

    public static void splitAndCreateFolders(String dataFolderPath, double splitRatio) {
        File mainFolder = new File(dataFolderPath);
        if (mainFolder.exists() && mainFolder.isDirectory()) {
            File[] files = mainFolder.listFiles();
            File trainFolder = new File("Data", "train/");
            File testFolder = new File("Data", "test/");
            trainFolder.mkdirs();
            testFolder.mkdirs();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        

                        try {
                            double random = Math.random();
                            File destinationFolder = (random < splitRatio) ? trainFolder : testFolder;
                            Files.copy(file.toPath(), new File(destinationFolder, fileName).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Data splitting and folder creation completed.");
            } else {
                System.out.println("No files found in the data folder.");
            }
        } else {
            System.out.println("Invalid data folder path.");
        }
    }

    public static void main(String[] args) {
        // Example usage
        String dataFolderPath = "Data\\Data_pret";
        
        double splitRatio = 0.9; // 80% training, 20% testing
        splitAndCreateFolders(dataFolderPath, splitRatio);
    }
}