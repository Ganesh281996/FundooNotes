package com.fundoonotes.note.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.exception.LabelNotFoundException;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.utility.AuthorizeService;
import com.fundoonotes.utility.JwtTokenService;
import com.fundoonotes.utility.NoteOperationsService;

@Transactional
@Service
@PropertySource(value = "classpath:exception.properties")
public class LabelServiceImpl implements LabelService 
{
	private static final Logger LOGGER = Logger.getLogger(LabelServiceImpl.class.getName());

	@Autowired
	Environment environment;
	
	@Autowired
	LabelDao labelDao;
	
	@Autowired
	AuthorizeService authorizeService;

	@Autowired
	NoteDao noteDao;

	@Autowired
	Userdao userDao;

	@Autowired
	JwtTokenService jwtTokenService;
	
	@Autowired
	NoteOperationsService noteOperationsService; 

	@Override
	public Label create(String labelName,String ownerId) 
	{
		Label label = new Label();
		label.setLabelName(labelName);
		label.setUserId(ownerId);
		label = labelDao.save(label);
		LOGGER.info("Saved Label");
		return label;
	}

	@Override
	public Label update(Label label,String ownerId) 
	{
		if(!labelDao.existsById(label.getLabelId()))
		{
			throw new LabelNotFoundException(environment.getProperty("LabelNotFoundException|"));
		}
		authorizeService.authorizeUserWithLabel(ownerId, label.getLabelId());
		noteOperationsService.updateLabels(label);
		
		label = labelDao.save(label);
		LOGGER.info("Updated Label");
		return label;
	}

	@Override
	public void delete(String labelId,String ownerId) 
	{
		if(!labelDao.existsById(labelId))
		{
			throw new LabelNotFoundException(environment.getProperty("LabelNotFoundException|"));
		}
		authorizeService.authorizeUserWithLabel(ownerId, labelId);
		noteOperationsService.removeLabels(labelId);
		
		labelDao.deleteById(labelId);
		LOGGER.info("Deleted Label");
	}

	@Override
	public List<Label> read(String ownerId) 
	{
		LOGGER.info("Displaying Labels");
		return labelDao.findByUserId(ownerId);
	}
}