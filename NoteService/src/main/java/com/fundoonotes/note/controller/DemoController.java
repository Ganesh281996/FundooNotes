package com.fundoonotes.note.controller;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController 
{
	@RequestMapping("/demo")
	public Authentication demo() 
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		System.out.println(authentication.getDetails());
		System.out.println(authentication.getName());
		System.out.println(authentication.getPrincipal());
		return authentication;
	}
	
	@RequestMapping("/demo/social")
	public void demoSocial() 
	{
		System.out.println("sucesssssss");
	}
	
	@GetMapping(value = "/scrap")
	public void demoScrap()
	{
		System.out.println("sucesssssss");
		Document document = null;
		try 
		{
			document = Jsoup
			.connect("https://mumbaimirror.indiatimes.com/mumbai/mumbai-speaks/articlelist/55817845.cms").get();
			System.out.println(document.baseUri());
			System.out.println(document.title());
			System.out.println(document.className());
			System.out.println(document.id());
			System.out.println(document.head());
			String a = "ipl_newcont";
			System.out.println(a);
			System.out.println(document.getElementsByClass(a));
			Elements elements = document.getElementsByClass(a);
			for(Element element : elements)
			{
				System.out.println(element.getElementsByTag("img").attr("src"));
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}