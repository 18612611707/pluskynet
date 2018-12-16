package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.ReasonnumDao;
import com.pluskynet.domain.Reasonnum;
import com.pluskynet.service.ReasonnumService;

public class ReasonnumServiceImpl implements ReasonnumService {

	private ReasonnumDao reasonnumDao;
	
	public void setReasonnumDao(ReasonnumDao reasonnumDao) {
		this.reasonnumDao = reasonnumDao;
	}

	@Override
	public List<Reasonnum> select() {
		List<Reasonnum> list = reasonnumDao.select();
		return list;
	}

}
