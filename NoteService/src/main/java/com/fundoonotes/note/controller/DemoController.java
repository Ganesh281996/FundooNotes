package com.fundoonotes.note.controller;

import java.io.IOException;
import java.security.Principal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController 
{
	@RequestMapping("/demo")
	public Principal demo(Principal principal) 
	{
		System.out.println(principal);
		return principal;
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
			document = Jsoup.connect("https://mumbaimirror.indiatimes.com/mumbai/mumbai-speaks/articlelist/55817845.cms").get();
			System.out.println(document.hasText());
			System.out.println(document.title());
			System.out.println(document.className());
			System.out.println(document.id());
			System.out.println(document.head());
//			Elements elements = document.getAllElements();
//			for(Element element : elements)
//			{
//				System.out.println(element);
//			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
