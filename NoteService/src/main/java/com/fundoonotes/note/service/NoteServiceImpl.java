package com.fundoonotes.note.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.ElasticSearchDao;
import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.dto.CreateNoteDTO;
import com.fundoonotes.note.dto.ResponseNoteDTO;
import com.fundoonotes.note.exception.LabelNotFoundException;
import com.fundoonotes.note.exception.NoteNotFoundException;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.note.model.Note;
import com.fundoonotes.note.model.User;
import com.fundoonotes.note.model.WebScrap;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.MapDTOService;
import com.fundoonotes.utility.NoteOperationsService;
import com.fundoonotes.utility.Response;
import com.fundoonotes.utility.WebScrapService;

@Transactional
@Service
@PropertySource(value = "classpath:exception.properties")
public class NoteServiceImpl implements NoteService 
{
	private static final Logger LOGGER = Logger.getLogger(NoteServiceImpl.class.getName());

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/YYYY  hh:mm:ss");

	@Autowired
	Environment environment;
	
	@Autowired
	private NoteDao noteDao;

	@Autowired
	private Userdao userDao;	

	@Autowired
	private LabelDao labelDao;

	@Autowired
	private ElasticSearchDao elasticSearchDao;
	
	@Autowired
	WebScrapService webScrapService; 
	
	@Autowired
	AuthorizeService authorizeService;
	
	@Autowired
	MapDTOService mapDTOService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	NoteOperationsService noteOperationsService; 
	
	@Override
	public Response createDummyUser(User user) 
	{
		userDao.save(user);
		return null;
	}
	
//	@Override
	public ResponseNoteDTO createNot(CreateNoteDTO createNoteDTO, String ownerId) 
	{
		Note note = modelMapper.map(createNoteDTO, Note.class);
		noteOperationsService.setProperties(note, ownerId);
		note = webScrapService.webScrapping(note);
		
		
		note = noteDao.save(note);
		elasticSearchDao.insertNote(note);
		LOGGER.info("Note has been saved");
		
		return modelMapper.map(note, ResponseNoteDTO.class);
	}

	@Override
	public ResponseNoteDTO createNote(CreateNoteDTO createNoteDTO, String ownerId) 
	{
		Note note = mapDTOService.noteDtoToNote(createNoteDTO);
		note = webScrapService.webScrapping(note);
		note.setOwnerId(ownerId);
		note.setCreatedDate(DATE_FORMAT.format(new Date()));
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		note = noteDao.save(note);
		elasticSearchDao.insertNote(note);
		LOGGER.info("Note has been saved");
		
		return modelMapper.map(note, ResponseNoteDTO.class);
	}

	@Override
	public ResponseNoteDTO updateNote(Note note, String ownerId) 
	{
		if(!noteDao.existsById(note.getNoteId()))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, note.getNoteId());
		
		note.setLastUpdatedDate(DATE_FORMAT.format(new Date()));
		note = webScrapService.webScrapping(note);
		note = noteDao.save(note);
		elasticSearchDao.updateNote(note);
		LOGGER.info("Note has been updated");

		return modelMapper.map(note, ResponseNoteDTO.class);
	}

	@Override
	public void deleteNote(String noteId, String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);
		
		noteDao.deleteById(noteId);
		elasticSearchDao.deleteNote(noteId);
		LOGGER.info("Note has been deleted");
	}

	@Override
	public List<ResponseNoteDTO> displayNotes(String ownerId) 
	{
		LOGGER.info("Displaying Notes");
		List<Note> notes = noteDao.findByOwnerId(ownerId);
		return mapDTOService.noteDtoToResponseNoteDto(notes);
	}
	

	@Override
	public List<Map<String, Note>> displayNotesByElasticSearch(String ownerId) 
	{
		return elasticSearchDao.getNoteByOwnerId(ownerId);
	}
	
	@Override
	public List<Map<String, Note>> displayNotesBySearch(String search) 
	{
		return elasticSearchDao.getNotesBySearch(search);
	}

	@Override
	public void pin(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

		Note note = noteDao.findByNoteId(noteId);
		if (note.isPinned())
		{
			note.setPinned(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been UnPinned");
		}
		else
		{
			note.setPinned(true);
			note.setArchieved(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been Pinned");
		}
	}

	@Override
	public void archieve(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException(environment.getProperty("NoteNotFoundException"));
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

		Note note = noteDao.findByNoteId(noteId);
		if (note.isArchieved())
		{
			note.setArchieved(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been removed from Archieve");
		}
		else
		{
			note.setArchieved(true);
			note.setPinned(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been added to Archieve");
		}
	}

	@Override
	public void trash(String noteId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

		Note note = noteDao.findByNoteId(noteId);
		if (note.isInTrash())
		{
			note.setInTrash(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been restored from trash");
		}
		else
		{
			note.setInTrash(true);
			note.setPinned(false);
			note = noteDao.save(note);
			elasticSearchDao.updateNote(note);
			LOGGER.info("Note has been added to trash");
		}
	}

	@Override
	public void label(String noteId, String labelId,String ownerId) 
	{
		if(!noteDao.existsById(noteId))
		{
			throw new NoteNotFoundException("Note with NoteID = "+noteId+" was not Found");
		}
		if(!labelDao.existsById(labelId))
		{
			throw new LabelNotFoundException("Label with LabelID = "+labelId+" was not Found");
		}
		authorizeService.authorizeUserWithNote(ownerId, noteId);

		Note note = noteDao.findByNoteId(noteId);
		Label label = labelDao.findByLabelId(labelId);

		List<String> notes = null;
		List<Label> labels = null;

		if(note.getLabels() == null || label.getNotes() == null )
		{
			labels = new ArrayList<>();
			notes = new ArrayList<>();
		}
		else
		{
			labels = note.getLabels();
			notes = label.getNotes();
		}

		if(labels.contains(label) && notes.contains(labelId))
		{
			labels.remove(label);
			notes.remove(labelId);
			LOGGER.info("Label has been removed from Note");
		}
		else
		{
			labels.add(label);
			notes.add(note.getNoteId());
			LOGGER.info("Label has been added to Note");
		}

		note.setLabels(labels);
		label.setNotes(notes);

		noteDao.save(note);
		elasticSearchDao.updateNote(note);
		labelDao.save(label);
	}

	@Override
	public WebScrap getWebDetails(String url)
	{
		Document document = null;
		try 
		{
			document = Jsoup.connect("https://mumbaimirror.indiatimes.com/mumbai/mumbai-speaks/articlelist/55817845.cms").get();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		WebScrap scrap = new WebScrap();
		scrap.setTitle(document.title());
		String[] urlSplit = document.baseUri().split(".com");
		scrap.setUrl(urlSplit[0]+".com");
		scrap.setImage(urlSplit[0]+".com"+document.getElementsByTag("img").attr("src"));
		return scrap;
	}
}