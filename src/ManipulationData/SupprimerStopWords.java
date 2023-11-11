package ManipulationData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SupprimerStopWords {
    // Ensemble pour stocker les mots vides (stop words)
    private static HashSet<String> stopWords = new HashSet<>();
    private static String filePath="Data\\english.txt";

    // Méthode pour charger les stop words à partir d'un fichier
    private static void loadStopWords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour supprimer les stop words du texte
    public static String supprimerStopWords(String text) {
        // Assurez-vous d'avoir chargé les stop words avant d'utiliser cette méthode
    	loadStopWords();
        if (stopWords.isEmpty()) {
            System.err.println("Erreur : Stop words non chargés. Veuillez appeler loadStopWords() d'abord.");
            return text;
        }

        // Supprimez les stop words du texte
        String[] words = text.split("\\s+");
        StringBuilder processedText = new StringBuilder();

        for (String word : words) {
            // Vérifiez si le mot n'est pas un stop word avant de l'ajouter au texte traité
            if (!stopWords.contains(word.toLowerCase())) {
                processedText.append(word).append(" ");
            }
        }

        return processedText.toString().trim();
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de la fonction supprimerStopWords
        String inputText = "The quick brown fox jumps over the lazy dog";
        String processedText = supprimerStopWords(inputText);
        System.out.println("Texte traité : " + processedText);
    }
}
