package com.pluskynet.service.impl;

import java.util.List;
import com.pluskynet.dao.SynonymDao;
import com.pluskynet.domain.Synonymtypetable;
import com.pluskynet.domain.Synonymwordtable;
import com.pluskynet.service.SynonymService;

public class SynonymServiceImpl implements SynonymService {
	private SynonymDao synonymDao;

	public void setSynonymDao(SynonymDao synonymDao) {
		this.synonymDao = synonymDao;
	}

	@Override
	public List<Synonymtypetable> getTypeList() {
		List<Synonymtypetable> list = synonymDao.getTypeList();
		return list;
	}

	@Override
	public List<Synonymwordtable> getSynonym(Synonymtypetable synonymtypetable) {
		List<Synonymwordtable> list = synonymDao.getSynonym(synonymtypetable);
		return list;
	}

	@Override
	public String saveType(Synonymtypetable synonymtypetable) {
		String msg = synonymDao.saveType(synonymtypetable);
		return msg;
	}

	@Override
	public String save(Synonymtypetable synonymtypetable) {
		String msg = synonymDao.save(synonymtypetable);
		return msg;
	}

}
