package com.pluskynet.service.impl;

import java.util.List;
import java.util.Map;

import com.pluskynet.dao.DocRuleDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.service.DocRuleService;
@SuppressWarnings("all")
public class DocRuleServiceImpl implements DocRuleService {
	private LatitudeauditDao latitudeauditDao;
	public void setLatitudeauditDao(LatitudeauditDao latitudeauditDao) {
		this.latitudeauditDao = latitudeauditDao;
	}

	private DocRuleDao docRuleDao;
	
	public void setDocRuleDao(DocRuleDao docRuleDao) {
		this.docRuleDao = docRuleDao;
	}
	public DocSectionAndRuleDao docSectionAndRuleDao;
	

	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		this.docSectionAndRuleDao = docSectionAndRuleDao;
	}

	@Override
	public String save(Docrule docrule) {
		String msg = docRuleDao.save(docrule);
		return msg;
	}

	@Override
	public String update(Docrule docrule) {
		String msg = docRuleDao.update(docrule);
		if (msg.equals("成功")) {
			Map<?, ?> map = docRuleDao.getDcoSection(docrule);
			String sectionname = map.get("sectionName").toString();
			docrule.setSectionName(sectionname);
			Latitudeaudit latitudeaudit = new Latitudeaudit();
			latitudeaudit.setRule(docrule.getRule());
			latitudeaudit.setLatitudename(sectionname);
			latitudeaudit.setLatitudeid(docrule.getRuleid());
			latitudeaudit.setLatitudetype(0);
			latitudeauditDao.update(latitudeaudit);
		}
		return msg;
	}

	@Override
	public List<Map> getDcoSectionList() {
		List<Map> jsonArray = docRuleDao.getDcoSectionList();
		return jsonArray;
	}

	@Override
	public Map<?, ?> getDcoSection(Docrule docrule) {
		Map<?, ?> map = docRuleDao.getDcoSection(docrule);
		return map;
	}

	@Override
	public void saveyldelete(String sectionname) {
		docSectionAndRuleDao.saveyldelete(sectionname);
		
	}

	@Override
	public void saveyl(Docsectionandrule docsectionandrule) {
		docSectionAndRuleDao.saveyl(docsectionandrule);		
	}

}
