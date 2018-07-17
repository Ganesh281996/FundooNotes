package com.fundoonotes.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ElasticConfiguration 
{
	@Bean
	public RestHighLevelClient restHighLevelClient()
	{
		HttpHost httpHost = new HttpHost("localhost", 9200, "http");
		RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
		return new RestHighLevelClient(restClientBuilder);
	}
	
	@Bean
	public ObjectMapper objectMapper()
	{
		return new ObjectMapper();
	}
}