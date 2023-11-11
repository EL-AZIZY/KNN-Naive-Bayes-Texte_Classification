package classification;

import java.io.IOException;
//import java.util.Map;

import ManipulationData.DataLoader;
import ManipulationData.MotsUnique;

public class MainOffline {
	public static void main(String[] args) throws IOException {
        String mainFolderPath = "Data\\train";

        String Path = "Data\\unique_words.txt";
        
        DataLoader.processFiles(mainFolderPath);
        
        MotsUnique.writeUniqueWordsToFile(mainFolderPath,Path);
        
        System.out.println("fin Process Files");
        
        System.out.println("Start  Classification");
    }
}
