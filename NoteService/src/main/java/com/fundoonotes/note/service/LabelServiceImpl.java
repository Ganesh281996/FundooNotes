package com.fundoonotes.note.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fundoonotes.note.dao.LabelDao;
import com.fundoonotes.note.dao.NoteDao;
import com.fundoonotes.note.dao.Userdao;
import com.fundoonotes.note.model.Label;
import com.fundoonotes.utility.JwtTokenService;

@Transactional
@Service
public class LabelServiceImpl implements LabelService 
{
	private static final Logger LOGGER = Logger.getLogger(LabelServiceImpl.class.getName());

	@Autowired
	LabelDao labelDao;

	@Autowired
	NoteDao noteDao;

	@Autowired
	Userdao userDao;

	@Autowired
	JwtTokenService jwtTokenService;

	@Override
	public Label create(Label label,String ownerId) 
	{
		label.setUserId(ownerId);
		label = labelDao.save(label);
		LOGGER.info("Saved Label");
		return label;
	}

	@Override
	public Label update(Label label,String ownerId) 
	{	
		label = labelDao.save(label);
		LOGGER.info("Updated Label");
		
		return label;
	}

	@Override
	public void delete(String labelId,String ownerId) 
	{
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