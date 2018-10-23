package com.pluskynet.action;
import java.util.List;

import com.pluskynet.domain.Articleyl;
import com.pluskynet.service.ArticleylService;
import com.pluskynet.util.BaseAction;



public class ArticleylAction extends BaseAction {

	private static final long serialVersionUID = -7343124610814497886L;
	private Articleyl article;

	private ArticleylService articleService;
	public ArticleylService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleylService articleService) {
		this.articleService = articleService;
	}

	@Override
	public Articleyl getModel() {
		article = new Articleyl();
		return article;
	}

	/**
	 * 分页查询
	 */
	public void findPageBy() {
		try {
			List<Articleyl> list = articleService.findPageBy(article, this.getPage(),
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
	public void getArticles() {
		try {
			List<Articleyl> list = articleService.getArticles();
			this.outJsonByMsg(list, list.size(), "", "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
