package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Sample;
import com.pluskynet.domain.User;

public interface SampleDao {

	List<Article01> getListArticle(String table, String year, int count,String trialRound,String doctype);

	void save(List<Article01> list,User user);

	void saverule(Sample sample,User user);

	Sample select(User user);

	void delete(User user);

}
