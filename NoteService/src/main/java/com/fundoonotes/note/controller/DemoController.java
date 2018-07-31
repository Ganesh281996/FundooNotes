package com.fundoonotes.note.controller;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
//import org.springframework.social.facebook.api.Facebook;
//import org.springframework.social.facebook.api.User;
//import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController 
{
	@GetMapping
	public void get()
	{
		System.out.println("adgadjfhtdjdh");
	}
	
//	@RequestMapping("/demo")
//	public Authentication demo() 
//	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////		System.out.println(authentication);
////		System.out.println(authentication.getDetails());
////		System.out.println(authentication.getName());
////		System.out.println(authentication.getPrincipal());
//		String token = ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue();
//		
//		Facebook facebook = new FacebookTemplate(token);
//		
//		User fbUser = facebook.fetchObject("me", User.class,"first_name","last_name","email"
//				,"gender","birthday");
//		
//		System.out.println(fbUser.getBirthday());
//		System.out.println(fbUser.getEmail());
//		System.out.println(fbUser.getFirstName());
//		System.out.println(fbUser.getGender());
//		System.out.println(fbUser.getId());
//		System.out.println(fbUser.getLastName());
//		System.out.println(fbUser.getLocation());
//		return authentication;
//	}
	
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