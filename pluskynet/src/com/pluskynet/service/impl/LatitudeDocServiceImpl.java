package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.LatitudeDocDao;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.otherdomain.LatitudeDocList;
import com.pluskynet.service.LatitudeDocService;
@SuppressWarnings("all")
public class LatitudeDocServiceImpl implements LatitudeDocService {
	private LatitudeDocDao latitudeDocDao;
	

	public void setLatitudeDocDao(LatitudeDocDao latitudeDocDao) {
		this.latitudeDocDao = latitudeDocDao;
	}
	private DocSectionAndRuleDao DocSectionAndRuleDao;

	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		DocSectionAndRuleDao = docSectionAndRuleDao;
	}


	@Override
	public int getCountBy(Docsectionandrule latitudedoc,String listLatitudedocs,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String date) {
		int list = latitudeDocDao.getCountBy(latitudedoc,listLatitudedocs, caseno, courtname, judges, parties, law, lawyer, legal, dates, date);
		return list;
	}


	@Override
	public List<LatitudeDocList> findPageBy(Docsectionandrule latitudedoc, int page, int rows,String listLatitudedocs
			,String caseno,String courtname,String judges,String parties,String law,String lawyer,String legal,String dates,String date) {
		return latitudeDocDao.findPageBy(latitudedoc , page , rows,listLatitudedocs, caseno, courtname, judges, parties, law, lawyer, legal, dates, date);
	}


	@Override
	public List<LatitudeDocList> getDoc(Docsectionandrule latitudedoc) {
		return latitudeDocDao.getDoc(latitudedoc);
	}

}
