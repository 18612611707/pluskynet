package com.pluskynet.service;

import java.util.List;

import com.pluskynet.domain.Article;

public interface ArticleService {

	public List<Article> findPageBy();

	/**
	 * �?��
	 * @return
	 */
	public List<Article> getArticles();

	/**
	 * @param article
	 * @param page
	 * @param rows
	 * @param sidx
	 * @param sord
	 * @return 分页查询
	 */
	public List<Article> findPageBy(Article article, int page, int rows, String sidx, String sord);

	/**
	 * @param article
	 * @return 查询总记录数
	 */
	public int getCountBy(Article article);
	/*
	 * 拆分文书
	 */
	public int breakArticle(String data,int rows,Object ob);
}
