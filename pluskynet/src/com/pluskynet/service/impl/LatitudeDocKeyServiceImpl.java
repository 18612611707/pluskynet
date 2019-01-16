package com.pluskynet.service.impl;

import com.pluskynet.dao.LatitudeKeyDao;
import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.service.LatitudeDocKeyService;


public class LatitudeDocKeyServiceImpl implements LatitudeDocKeyService {
	private LatitudeKeyDao latitudeKeyDao;
	

	public void setLatitudeKeyDao(LatitudeKeyDao latitudeKeyDao) {
		this.latitudeKeyDao = latitudeKeyDao;
	}

	@Override
	public void save(LatitudedocKey latitudekey) {
		latitudeKeyDao.save(latitudekey);
		
	}

	@Override
	public void delete(LatitudedocKey latitudedockey) {
		latitudeKeyDao.delete(latitudedockey);
		
	}

}
