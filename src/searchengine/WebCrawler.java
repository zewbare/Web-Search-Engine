package searchengine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {
	
	private int count=0;
	private static int i=1;
	private Set<String> links;
	private static final int MAX_DEPTH= 1000;
	
	public WebCrawler() {
		links= new HashSet<>();
	}

	public void crawlPageLinks(String url) {
		if(!links.contains(url)) {
			if(links.add(url)) {
				String fileName= Integer.toString(i++);
				saveUrl(fileName, url);
			}
			try {
				Document document= Jsoup.connect(url).get();
				Elements linksOnPage= document.select("a[href]");
				
				for(Element link: linksOnPage) {
					if(count!= MAX_DEPTH) {
						count++;
						crawlPageLinks(link.attr("abs:href"));
					}
				}
			} catch (IOException e) {
				System.err.println("Error for "+url);
			}
		}
	}
	
	private void saveUrl(String fileName, String urlString) {
		try {
			URL url= new URL(urlString);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			File folder= new File("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\html-files");
			if(!folder.exists())
				folder.mkdir();
			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\D\\MAC\\ACC\\Project\\ACC-Project\\html-files\\" + fileName+".html"));
			String line;
			while((line= br.readLine())!=null)
				bw.write(line);
			br.close();
			bw.close();
			System.out.println("Download Successful");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new WebCrawler().crawlPageLinks("https://google.com");
	}

}
