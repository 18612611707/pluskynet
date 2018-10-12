package com.pluskynet.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pluskynet.dao.DocRuleDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.LatitudeauditDao;
import com.pluskynet.domain.Docrule;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Latitudeaudit;
import com.pluskynet.otherdomain.TreeDocrule;
import com.pluskynet.otherdomain.Treelatitude;
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
		List<Docrule> friList = docRuleDao.getDcoSectionList();
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < friList.size(); i++) {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("ruleid", friList.get(i).getRuleid());
			treeMap.put("fid", friList.get(i).getFid());
			treeMap.put("sectionname", friList.get(i).getSectionName());
			treeMap.put("children", treeList(friList.get(i).getRuleid()));
			list.add(treeMap);
		}
		return list;
	}
	
	public List<TreeDocrule> treeList(int latitudeid) {
		List<TreeDocrule> nextSubSet = new ArrayList<TreeDocrule>();
		TreeDocrule voteTree = new TreeDocrule();
		voteTree.setRuleid(latitudeid);
		nextSubSet = docRuleDao.getNextSubSet(voteTree);
		return nextSubSet;
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

	@Override
	public List<Map> getSecNameShow(String sectionname) {
		List<Docrule> list = docRuleDao.getSecNameShow(sectionname);
		List<Map> lists = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> treeMap = new HashMap<String, Object>();
			treeMap.put("ruleid", list.get(i).getRuleid());
			treeMap.put("fid", list.get(i).getFid());
			treeMap.put("sectionname", list.get(i).getSectionName());
			treeMap.put("children", treeList(list.get(i).getRuleid()));
			lists.add(treeMap);
		}
		return lists;
	}

	@Override
	public List<Docrule> getRuleShow(Integer ruleid, String causeo, String causet, String spcx, String doctype) {
		List<Docrule> list = docRuleDao.getRuleShow(ruleid,causeo,causet,spcx,doctype);
		return list;
	}

}
