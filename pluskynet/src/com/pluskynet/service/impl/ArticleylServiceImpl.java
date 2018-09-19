package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.ArticleylDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.service.ArticleylService;

public class ArticleylServiceImpl implements ArticleylService {

	private ArticleylDao articleDao;

	@Override
	public List<Articleyl> findPageBy() { // �?��做分�?
		return articleDao.getArticles();
	}

	@Override
	public List<Articleyl> getArticles() {
		return articleDao.getArticles();
	}

	public ArticleylDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleylDao articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public List<Articleyl> findPageBy(Articleyl article, int page, int rows, String sidx, String sord) {
		return articleDao.findPageBy(article, page, rows, sidx, sord) ;
	}

	@Override
	public int getCountBy(Articleyl article) {
		return articleDao.getCountBy(article);
	}
	

}
