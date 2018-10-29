package com.pluskynet.service.impl;


import java.util.ArrayList;
import java.util.List;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.dao.SampleDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docsectionandrule;
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
	public DocSectionAndRuleDao docsectionandruledao;

	public void setDocsectionandruledao(DocSectionAndRuleDao docsectionandruledao) {
		this.docsectionandruledao = docsectionandruledao;
	}

	@Override
	public void random(Sample sample,User user) {
		List<Docsectionandrule> doclist = new ArrayList<Docsectionandrule>();
		List<Article01> list = new ArrayList<Article01>();
		JSONArray jsonArray = JSONArray.fromObject(sample.getRule());
		for (int i = 0; i < jsonArray.size(); i++) {
			List<Docsectionandrule> doclists = new ArrayList<Docsectionandrule>();
			List<Article01> articleyl = new ArrayList<Article01>();
			JSONObject jsonObject = new JSONObject().fromObject(jsonArray.get(i));
			String year = jsonObject.getString("year");
			String count = jsonObject.getString("count");
			String trialRound = jsonObject.getString("trialRound");
			String doctype = jsonObject.getString("doctype");
			Cause cause = new Cause();
			cause.setCausename(jsonObject.getString("causet"));
			Cause table = causeDao.selectCause(cause);
			if (jsonObject.has("latitudename")) {
				doclists = docsectionandruledao.getDocsectionList(table,year,Integer.valueOf(count),trialRound,doctype);
				doclist.addAll(doclists);
			}
			articleyl = sampleDao.getListArticle(table.getCausetable(),year,Integer.valueOf(count),trialRound,doctype);
			list.addAll(articleyl);
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
