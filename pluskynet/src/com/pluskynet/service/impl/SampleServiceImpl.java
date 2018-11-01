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
		for (int i = 0; i < jsonArray.size(); i++) {
			List<Docsectionandrule01> doclists = new ArrayList<Docsectionandrule01>();
			List<Article01> articleyl = new ArrayList<Article01>();
			JSONObject jsonObject = new JSONObject().fromObject(jsonArray.get(i));
			String year = jsonObject.getString("year");
			int count = jsonObject.getInt("count");
			String trialRound = jsonObject.getString("trialRound");
			String doctype = jsonObject.getString("doctype");
			Cause cause = new Cause();
			cause.setCausename(jsonObject.getString("causet"));
			Cause table = causeDao.selectCause(cause);
			String latitudename = jsonObject.getString("latitudename");
			if (jsonObject.has("latitudename")) {
				if (!jsonObject.getString("latitudename").equals("")) {
					System.out.println(doclists);
					System.out.println(docSectionAndRuleDao);
					System.out.println(table);
					System.out.println(table.getDoctable());
					System.out.println(year);
					System.out.println(count);
					System.out.println(trialRound);
					System.out.println(doctype);
					System.out.println(doclists);
					doclists = docSectionAndRuleDao.getDocsectionList(table,year,count,trialRound,doctype);
					doclist.addAll(doclists);
				}else{
					articleyl = sampleDao.getListArticle(table.getCausetable(),year,Integer.valueOf(count),trialRound,doctype);
					list.addAll(articleyl);
				}
				
			}else{
			articleyl = sampleDao.getListArticle(table.getCausetable(),year,Integer.valueOf(count),trialRound,doctype);
			list.addAll(articleyl);
			}
		}
		if (doclist.size()>0) {
			sampleDao.deleteDoc(user);
			sampleDao.saveDoc(doclist,user);
		}
		if (list.size()>0) {
			sampleDao.delete(user);
			sampleDao.save(list,user);
		}
		sampleDao.saverule(sample,user);
	}

	@Override
	public Sample select(User user) {
		Sample sample = sampleDao.select(user);	
		return sample;
	}


}
