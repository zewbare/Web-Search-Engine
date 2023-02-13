package searchengine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLToText {
	
	public static void textFileCreator(File file) {
		
		try {
			Document document = Jsoup.parse(file, "UTF-8");
			String string = document.text();
			String fileNameWithOutExt = file.getName().replaceFirst("[.][^.]+$", "");
			PrintWriter out = new PrintWriter("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\text-files\\" + fileNameWithOutExt + ".txt");
			out.println(string);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void convertFiles() {
		File folder = new File("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\html-files");
		
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				textFileCreator(listOfFiles[i]);
			}
		}
		System.out.println("Text files generated.");
	
	}
	
	public static void main(String[] args) {
		convertFiles();
	}

}
