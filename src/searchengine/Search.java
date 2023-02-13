package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Search {

	public static void searchQuery() {
		Map<String, Integer> hashMap= new HashMap<>();
		Scanner sc= new Scanner(System.in);
		while(true) {
			System.out.println("Plese enter your search: ");
			String searchQuery= sc.nextLine();
			int noOfOccurance=0;
			int noOfFiles=0;
			
			
			
			File my_dir = new File("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\text-files");
			File[] fileArray = my_dir.listFiles();
			int i=0;
			while(i < fileArray.length) {
				noOfOccurance = searchWord(fileArray[i], searchQuery); //this will search the word
				hashMap.put(fileArray[i].getName(), noOfOccurance); 
				if (noOfOccurance != 0) {
					noOfFiles++;
				}
				i++;
			}
			System.out.println("\n Total no. of files '"+searchQuery+"' occurs is "+noOfFiles);
			if(noOfFiles==0) {
				System.out.println("\nPlease wait while we give you suggestions\n");
				Suggestions.suggest(searchQuery); //Suggestions are given using the edit distance
			}
			else
				Rank.rankFiles(hashMap);
			System.out.println("\n\nDo you want to continue?");
			System.out.println("If yes please enter y if 'Yes'");
			System.out.println("If you want to exit hit any key");
			String option = sc.nextLine();
			if(!option.equals("y")) {
				System.out.println("Thanks for using the Web Search Engine");
				break;
				
			}
		}
		
	}

	private static int searchWord(File file, String searchQuery) {
		String data="";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				data = data + line;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		char txt[] = data.toLowerCase().toCharArray();
		char pat[] = searchQuery.toLowerCase().toCharArray();
		int result = boyerMooreSearch(txt, pat);
		if (result != 0) {
			System.out.println("\nThe file that contains all the above words" + file.getName());
			System.out.println("--------------------------------------\n");
		}
		return result;
	}

	private static int boyerMooreSearch(char[] textArray, char[] patternArray) {
		int noOfCharacters=10000;
		int patternLength = patternArray.length; 
	    int textLength = textArray.length; 
	    int count = 0;
	    int badCharArray[] = new int[noOfCharacters]; 
	 
	    int i;
	     for (i = 0; i < noOfCharacters; i++) 
	          badCharArray[i] = -1; 
	     for (i = 0; i < patternLength; i++) 
	          badCharArray[(int) patternArray[i]] = i;
	 
	    int size = 0; 
	    while(size <= (textLength - patternLength)) { 
	        int j = patternLength - 1;
	        while(j >= 0 && patternArray[j] == textArray[size+j]) j--;
	        if (j < 0) { 
	        	System.out.println("The word occurs at position " + size); 
	             count++;
	             size += (size + patternLength < textLength)? patternLength - badCharArray[textArray[size + patternLength]] : 1;
	        } 
	         else size += max(1, j - badCharArray[textArray[size+j]]);
	     } 
	     return count;
	}
	
	static int max (int a, int b) { 
    	if(a > b) return a;
    	return b;
    }

}
