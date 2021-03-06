package com.fundoonotes.utility;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
		Set<WebScrap> webScraps = genericWebScrap(note.getBody());
		webScraps.addAll(genericWebScrap(note.getTitle()));
		note.setWebScraps(webScraps);
		return note;
	}

	public Set<WebScrap> genericWebScrap(String data)
	{
		Set<String> urls = getUrls(data);
		Set<WebScrap> webScraps = new HashSet<>();
		for(String url : urls)
		{
			webScraps.add(getWebScraps(url));
		}
		return webScraps;
	}

	public Set<String> getUrls(String body)
	{
		Set<String> urls = new HashSet<>();
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