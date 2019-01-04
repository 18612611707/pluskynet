package com.pluskynet.service.impl;

import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.domain.Docidandruleid;
import com.pluskynet.service.DocidandruleService;

public class DocidandruleServiceImpl implements DocidandruleService {
	private DocidandruleidDao docidandruleidDao;

	public void setDocidandruleidDao(DocidandruleidDao docidandruleidDao) {
		this.docidandruleidDao = docidandruleidDao;
	}

	@Override
	public void save(Docidandruleid docidandruleid) {
		docidandruleidDao.save(docidandruleid);
	}

	@Override
	public void delete(Docidandruleid docidandruleid) {
		docidandruleidDao.delete(docidandruleid);
	}

}
