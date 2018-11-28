package com.pluskynet.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.SampleDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Docsectionandrule01;
import com.pluskynet.domain.Sample;
import com.pluskynet.domain.User;
import com.pluskynet.service.SampleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SampleServiceImpl implements SampleService{
	private CauseDao causeDao;

	public void setCauseDao(CauseDao causeDao) {
		this.causeDao = causeDao;
	}
	private SampleDao sampleDao;

	public void setSampleDao(SampleDao sampleDao) {
		this.sampleDao = sampleDao;
	}
	
	private DocSectionAndRuleDao docSectionAndRuleDao;
	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		this.docSectionAndRuleDao = docSectionAndRuleDao;
	}


	@Override
	public void random(Sample sample,User user) {
		List<Docsectionandrule01> doclist = new ArrayList<Docsectionandrule01>();
		List<Article01> list = new ArrayList<Article01>();
		if (sample.getRule()==null || sample.getRule().equals("[]")) {
			return;
		}
		JSONArray jsonArray = JSONArray.fromObject(sample.getRule());
		String sectionname = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			List<Docsectionandrule01> doclists = new ArrayList<Docsectionandrule01>();
			List<Article01> articleyl = new ArrayList<Article01>();
			JSONObject jsonObject = new JSONObject().fromObject(jsonArray.get(i));
			String year = jsonObject.getString("year");
			int count = jsonObject.getInt("count");
			String trialRound = jsonObject.getString("trialRound");
			String doctype = jsonObject.getString("doctype");
			sectionname = jsonObject.getString("sectionname");
			Cause cause = new Cause();
			cause.setCausename(jsonObject.getString("causet"));
			Cause table = causeDao.selectCause(cause);
			String latitudename = jsonObject.getString("latitudename");
			int latitudeid =-1;
			if (!latitudename.equals("")) {
				latitudeid = Integer.valueOf(latitudename);
			}
			if (jsonObject.has("sectionname")) {
				if (!jsonObject.getString("sectionname").equals("")) {
					if (i==0) {
						sampleDao.deleteDoc(user);
					}
					doclists = docSectionAndRuleDao.getDocsectionList(table,year,count,trialRound,doctype,Integer.valueOf(sectionname),latitudeid);
					sampleDao.saveDoc(doclists,user);
				}else{
					if (i==0) {
						sampleDao.delete(user);
					}
					articleyl = sampleDao.getListArticle(table.getCausetable(),year,Integer.valueOf(count),trialRound,doctype,user);
					sampleDao.save(articleyl, user);
				}
			}else{
			articleyl = sampleDao.getListArticle(table.getCausetable(),year,Integer.valueOf(count),trialRound,doctype,user);
			sampleDao.save(articleyl, user);
			}
		}
		sampleDao.saverule(sample,user);
	}

	@Override
	public Sample select(User user) {
		Sample sample = sampleDao.select(user);	
		return sample;
	}


}
