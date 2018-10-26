package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.ArticleylDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.User;
import com.pluskynet.service.ArticleylService;

import javassist.expr.NewArray;

public class ArticleylServiceImpl implements ArticleylService {

	private ArticleylDao articleDao;
	User user = new User(); 
	@Override
	public List<Articleyl> findPageBy() { // �?��做分�?
		return articleDao.getArticles(user);
	}
	
	@Override
	public List<Articleyl> getArticles() {
		return articleDao.getArticles(user);
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
