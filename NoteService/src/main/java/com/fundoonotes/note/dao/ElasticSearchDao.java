package com.fundoonotes.note.dao;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundoonotes.note.model.Note;

@Repository
public class ElasticSearchDao 
{
	private static String INDEX = "elastic";
	
	private static String TYPE = "note";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	public void insertNote(Note note)
	{
		Map<Note, String> map = objectMapper.convertValue(note, Map.class);
		
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, note.getNoteId()).source(map);
		
		try 
		{
			restHighLevelClient.index(indexRequest);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void deleteNote(String noteId)
	{
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, noteId);
		try 
		{
			restHighLevelClient.delete(deleteRequest);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Map<String, Note>> getNoteByOwnerId(String ownerId)
	{
		SearchRequest searchRequest = searchRequest(INDEX, TYPE, "ownerId", ownerId);
		SearchResponse searchResponse = null;
		List<Map<String, Note>> notes = new LinkedList<>();
		try 
		{
			searchResponse = restHighLevelClient.search(searchRequest);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		SearchHit[] searchHits = searchResponse.getHits().getHits();
		for(SearchHit searchHit : searchHits)
		{
			notes.add(objectMapper.convertValue(searchHit.getSourceAsMap(), Map.class));
		}
		return notes;
	}
	
	public List<Map<String, Note>> getNotesBySearch(String search)
	{
		SearchRequest searchRequest = searchRequest(INDEX, TYPE, "noteId", search);
		SearchResponse searchResponse = null;
		List<Map<String, Note>> notes = new LinkedList<>();
		try 
		{
			searchResponse = restHighLevelClient.search(searchRequest);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		 SearchHit[] searchHits = searchResponse.getHits().getHits();
		for(SearchHit searchHit : searchHits)
		{
			notes.add(objectMapper.convertValue(searchHit.getSourceAsMap(), Map.class));
		}
		return notes;
	}
	
	private SearchRequest searchRequest(String index, String type, String field, String search) 
	{
	    SearchRequest searchRequest = new SearchRequest(index);
	    searchRequest.types(type);
	    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	    searchSourceBuilder.query(QueryBuilders.matchPhrasePrefixQuery(field, search));
	    searchRequest.source(searchSourceBuilder);
	    return searchRequest;
	}
}