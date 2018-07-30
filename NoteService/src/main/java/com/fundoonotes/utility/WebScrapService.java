package com.fundoonotes.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.WebScrap;

@Service
public class WebScrapService 
{	
	public Note webScrapping(Note note)
	{
		List<WebScrap> webScraps = genericWebScrap(note.getBody());
		webScraps.addAll(genericWebScrap(note.getTitle()));
		note.setWebScrapes(webScraps);
		return note;
	}
	
	public List<WebScrap> genericWebScrap(String data)
	{
		List<String> urls = getUrls(data);
		List<WebScrap> webScraps = new ArrayList<>();
		for(String url : urls)
		{
			webScraps.add(getWebScraps(url));
		}
		return webScraps;
	}
		
	public List<String> getUrls(String body)
	{
		List<String> urls = new ArrayList<>();
		String[] checkForUrls = body.split(" ");
		
		for(int i=0 ; i<checkForUrls.length ; i++)
		{
			if(checkForUrls[i].startsWith("http"))
			{
				urls.add(checkForUrls[i]);
			}
		}
		return urls;
	}
	
	public WebScrap getWebScraps(String url)
	{
		WebScrap webScrap = new WebScrap();
		try 
		{
			Document document = Jsoup.connect(url).get();
			webScrap.setTitle(document.title());
			String[] urlSplit = document.baseUri().split(".com");
			webScrap.setUrl(urlSplit[0]+".com");
			webScrap.setImage(urlSplit[0]+".com"+document.getElementsByTag("img").attr("src"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return webScrap;
	}
}