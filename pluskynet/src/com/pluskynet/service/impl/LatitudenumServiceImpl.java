package com.pluskynet.service.impl;

import java.util.List;


import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Latitudenum;
import com.pluskynet.service.LatitudenumService;
import com.pluskynet.util.HttpRequest;

public class LatitudenumServiceImpl implements LatitudenumService {

	private LatitudenumDao latitudenumDao;
	
	public void setLatitudenumDao(LatitudenumDao latitudenumDao) {
		this.latitudenumDao = latitudenumDao;
	}

	@Override
	public List<Latitudenum> countlat(int type) {
		List<Latitudenum> list = latitudenumDao.countlat(type);
		/*if (list.size()>0) {
			HttpRequest httpRequest = new HttpRequest();
			httpRequest.sendPost("", list.toString());
		}*/
		return list;
	}

	@Override
	public void updatelat(List<Latitudenum> list) {
		latitudenumDao.updatelat(list);
		
	}

}
