//package com.fundoonotes.configuration;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.config.AbstractFactoryBean;
//
//public class RestHighLevelClientConfiguration extends AbstractFactoryBean<RestHighLevelClient>
//{
//	@Override
//	public Class<RestHighLevelClient> getObjectType() 
//	{
//		return RestHighLevelClient.class;
//	}
//
//	@Override
//	protected RestHighLevelClient createInstance() throws Exception 
//	{
//		return clientBuilder();
//	}
//	
//	private RestHighLevelClient clientBuilder()
//	{
//		HttpHost httpHost = new HttpHost("localhost", 9200, "http");
//		RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
//		return new RestHighLevelClient(restClientBuilder);
//	}
//}