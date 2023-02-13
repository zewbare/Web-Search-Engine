package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Suggestions {

	public static void suggest(String searchQuery) {
		File file= new File("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\dictionary.txt");
		try {
			BufferedReader br= new BufferedReader(new FileReader(file));
			List<String> dictWords= new ArrayList<>();
			String word;
			while((word= br.readLine())!=null)
				dictWords.add(word);
			int editDistance = Integer.MAX_VALUE, editDistanceFirstWord = Integer.MAX_VALUE, editDistanceSecondWord = Integer.MAX_VALUE;
			int firstSuggestion = 0;
			int secondSuggestion = 0;
			
			for(int i = 0; i < dictWords.size(); i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = minDistance(dictionaryWord, searchQuery);
					if(editDistance < editDistanceFirstWord) {
						editDistanceFirstWord=editDistance;
						firstSuggestion = i;
					}
			}
			
			for(int i = 0; i<dictWords.size();i++){
				String dictionaryWord = dictWords.get(i);
				editDistance = minDistance(dictionaryWord, searchQuery);
				if(editDistance < editDistanceSecondWord) {
					editDistanceSecondWord = editDistance;
					if(i != firstSuggestion) secondSuggestion = i;
				}
			}
			System.out.println("Did you mean: "+dictWords.get(firstSuggestion)+" or "+dictWords.get(secondSuggestion));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static int minDistance(String word1, String word2) {
		int m = word1.length();
        int n = word2.length();
        
        int[][] cost = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++)
            cost[i][0] = i;
        for(int i = 1; i <= n; i++)
            cost[0][i] = i;
        
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(word1.charAt(i) == word2.charAt(j))
                    cost[i + 1][j + 1] = cost[i][j];
                else {
                    int a = cost[i][j];
                    int b = cost[i][j + 1];
                    int c = cost[i + 1][j];
                    cost[i + 1][j + 1] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    cost[i + 1][j + 1]++;
                }
            }
        }
        return cost[m][n];
	}

}
