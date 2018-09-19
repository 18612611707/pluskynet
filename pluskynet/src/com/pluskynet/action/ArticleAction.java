package com.pluskynet.action;
import java.util.List;

import com.pluskynet.domain.Article;
import com.pluskynet.service.ArticleService;
import com.pluskynet.util.BaseAction;



public class ArticleAction extends BaseAction {

	private static final long serialVersionUID = -7343124610814497886L;
	private Article article;

	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Article getModel() {
		article = new Article();
		return article;
	}

	/**
	 * 分页查询
	 */
	public void findPageBy() {
		try {
			List<Article> list = articleService.findPageBy(article, this.getPage(),
					this.getRows(), this.getSidx(), this.getSord());
			int totalSize = articleService.getCountBy(article);
			this.outJsonByMsg(list, totalSize, "", "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在页面上进行分页
	 */
	public List<Article> getArticles() {
		List<Article> list = articleService.getArticles();
//		try {
//			List<Article> list = articleService.getArticles();
//			this.outJsonByMsg(list, list.size(), "", "yyyy-MM-dd HH:mm:ss");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return list;
	}
	
	
	public int BreakArticle(String data,int rows,Object ob){
		int i = articleService.breakArticle(data,rows,ob);
		return i;
	}

}
