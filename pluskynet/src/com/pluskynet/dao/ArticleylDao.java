package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.User;

public interface ArticleylDao {
	List<Articleyl> getArticles(User user);
	boolean updateArticle(String docId);
	
	List<Articleyl> findPageBy(Articleyl article, int page, int rows, String sidx, String sord);

	int getCountBy(Articleyl article);
	List<Articleyl> getArticles(List<Docsectionandrule> list);
	List<Articleyl> getArt(String docid);

}
