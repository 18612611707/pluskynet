package com.pluskynet.dao;

import java.util.List;

import com.pluskynet.domain.Article;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Docsectionandrule;

public interface ArticleDao {
	List<Article> getArticles();
	boolean updateArticle(String docId);
	
	List<Article> findPageBy(Article article, int page, int rows, String sidx, String sord);

	int getCountBy(Article article);
	List<Article> getArticles(List<Docsectionandrule> list);
	List<Article> getArt(String docid);
	List<Article> breakArticle(String data,int rows);
	void articleSave(String table, Article01 article01);
	List<Article01> getArticle01List(String tbale,int allorre,int rows);
	void updateArticleState(String docid,String table,int states);
	void articleState(String table,int states);
	void delete(int i);
}
