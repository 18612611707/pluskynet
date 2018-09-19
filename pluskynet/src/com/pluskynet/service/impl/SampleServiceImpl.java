package com.pluskynet.service.impl;


import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import com.pluskynet.dao.CauseDao;
import com.pluskynet.dao.SampleDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Rule;
import com.pluskynet.domain.Sample;
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

	@Override
	public void random(Sample sample) {
		List<Article01> list = new ArrayList<Article01>();
		JSONArray jsonArray = JSONArray.fromObject(sample.getRule());
		for (int i = 0; i < jsonArray.size(); i++) {
			List<Article01> articleyl = new ArrayList<Article01>();
			JSONObject jsonObject = new JSONObject().fromObject(jsonArray.get(i));
			String year = jsonObject.getString("year");
			String count = jsonObject.getString("count");
			String trialRound = jsonObject.getString("trialRound");
			String doctype = jsonObject.getString("doctype");
			Cause cause = new Cause();
			cause.setCausename(jsonObject.getString("causet"));
			String table = causeDao.select(cause);
			articleyl = sampleDao.getListArticle(table,year,Integer.valueOf(count),trialRound,doctype);
			list.addAll(articleyl);
		}
		sampleDao.delete();
		sampleDao.save(list);
		sampleDao.saverule(sample);
		
	}

	@Override
	public Sample select() {
		Sample sample = sampleDao.select();	
		return sample;
	}


}
