package com.fundoonotes.note.dao;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundoonotes.note.model.Note;

@Repository
public class ElasticSearchDao 
{
	private static final String INDEX = "elasticdatabase";
	
	private static final String TYPE = "note";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	public void insertNote(Note note)
	{
		Map<Note, String> map = objectMapper.convertValue(note, Map.class);
		
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getOwnerId()).source(map);
		
		try 
		{
			restHighLevelClient.index(indexRequest);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> getNoteByOwnerId(String ownerId)
	{
		GetRequest request = new GetRequest(INDEX, TYPE, ownerId);
		GetResponse response = null;
		try 
		{
			response = restHighLevelClient.get(request);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return response.getSourceAsMap();
	}
}