package Knn_classifeiur;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;



public class Main {
	public static void main(String[] args) throws Exception {
		 String dossierFichiers = "Data\\train";

		 Map<String, Double> IDF = TF_IDF.calculateIDF(dossierFichiers);
		 Map<String,Map<String,Double>> TF = TF_IDF.calculatorTF(dossierFichiers);
		 Map<String, Map<String, Double>> result =TF_IDF.calculerTFIDF(IDF,TF);
		 KnnClassifieur KNN = new KnnClassifieur(result);
		
	     System.out.println("------------Test--------------");
		
		
		boolean T = true;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		while (T == true) {
			KnnClassifieur.start(KNN);
			
			System.out.println("1 => pour contunuer \t 0 pour exit ");
			
			int i = sc.nextInt();
			if(i == 0) {
				T =false;
				System.out.println("A bien tot ! ");
			}
			
		}
    }
}

