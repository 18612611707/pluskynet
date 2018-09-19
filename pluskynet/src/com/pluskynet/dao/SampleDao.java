package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Sample;

public interface SampleDao {

	List<Article01> getListArticle(String table, String year, int count,String trialRound,String doctype);

	void save(List<Article01> list);

	void saverule(Sample sample);

	Sample select();

	void delete();

}
