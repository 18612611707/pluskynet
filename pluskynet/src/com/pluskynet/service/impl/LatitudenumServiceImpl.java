package com.pluskynet.service.impl;

import java.util.List;


import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.service.LatitudenumService;

public class LatitudenumServiceImpl implements LatitudenumService {

	private LatitudenumDao latitudenumDao;
	
	public void setLatitudenumDao(LatitudenumDao latitudenumDao) {
		this.latitudenumDao = latitudenumDao;
	}

	@Override
	public List<Latitudenum> countlat(int type) {
		List<Latitudenum> list = latitudenumDao.countlat(type);
		return list;
	}

}
