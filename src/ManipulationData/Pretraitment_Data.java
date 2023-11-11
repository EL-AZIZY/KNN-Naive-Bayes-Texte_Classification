package ManipulationData;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class Pretraitment_Data {

    public static void moveFilesToSingleFolder(String sourceFolderPath, String destinationFolderPath) {
        File sourceFolder = new File(sourceFolderPath);
        File destinationFolder = new File(destinationFolderPath);

        // Vérifie si le dossier de destination existe, sinon le crée
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }

        // Liste des dossiers de classe dans le dossier source
        File[] classFolders = sourceFolder.listFiles();

        if (classFolders != null) {
            // Parcourir chaque dossier de classe
            for (File classFolder : classFolders) {
                if (classFolder.isDirectory()) {
                    String className = classFolder.getName();

                    // Liste des fichiers dans le dossier de classe
                    File[] files = classFolder.listFiles();

                    if (files != null) {
                        // Parcourir chaque fichier dans le dossier de classe
                        for (File file : files) {
                            if (file.isFile()) {
                                String fileName = file.getName();
                                String newFileName = fileName + "_" + className;

                                // Déplacer le fichier dans le dossier de destination avec le nouveau nom
                                File newFile = new File(destinationFolder, newFileName);
                                try {
                                    FileUtils.copyFile(file, newFile);
                                    System.out.println("Fichier " + fileName + " déplacé avec succès.");
                                } catch (IOException e) {
                                    System.err.println("Erreur lors du déplacement du fichier " + fileName + ": " + e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Spécifiez le chemin du dossier source contenant les classes et les fichiers
        String sourceFolderPath = "D:\\Master BDSaS\\S3\\Text Mining\\TPs\\20_newsgroups\\20_newsgroups";

        // Spécifiez le chemin du dossier de destination où les fichiers seront déplacés
        String destinationFolderPath = "Data\\Data_pret";

        // Appelez la fonction pour déplacer les fichiers vers le dossier de destination
        moveFilesToSingleFolder(sourceFolderPath, destinationFolderPath);

        System.out.println("Opération terminée.");
    }
}