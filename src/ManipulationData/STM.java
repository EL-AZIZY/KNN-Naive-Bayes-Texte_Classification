package ManipulationData;
import org.tartarus.snowball.ext.englishStemmer;

public class STM {

    public static String stemSentence(String sentence) {
        // Diviser la phrase en mots
        String[] words = sentence.split("\\s+");
        englishStemmer stemmer = new englishStemmer();

        // Effectuer le stemming sur chaque mot
        StringBuilder stemmedSentence = new StringBuilder();
        for (String word : words) {
            stemmer.setCurrent(word);
            stemmer.stem();
            String stemmedWord = stemmer.getCurrent();
            stemmedSentence.append(stemmedWord).append(" ");
        }

        // Retourner la phrase stemmée
        return stemmedSentence.toString().trim();
    }
}
