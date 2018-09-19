package com.pluskynet.service.impl;

import java.util.List;

import com.pluskynet.dao.ArticleDao;
import com.pluskynet.dao.CauseDao;
import com.pluskynet.domain.Article;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Cause;
import com.pluskynet.service.ArticleService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class ArticleServiceImpl implements ArticleService {

	private ArticleDao articleDao;
	private CauseDao causeDao;

	public void setCauseDao(CauseDao causeDao) {
		this.causeDao = causeDao;
	}

	@Override
	public List<Article> findPageBy() { // �?��做分�?
		return articleDao.getArticles();
	}

	@Override
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	@Override
	public List<Article> findPageBy(Article article, int page, int rows, String sidx, String sord) {
		return articleDao.findPageBy(article, page, rows, sidx, sord);
	}

	@Override
	public int getCountBy(Article article) {
		return articleDao.getCountBy(article);
	}

	@Override
	public int breakArticle(String data, int rows, Object ob) {
		String tables = null;
		String datas = null;
		List<Article> list = null;
		synchronized (ob) {
		list = articleDao.breakArticle(data, rows);	
		}
		if (data.equals("")) {
			tables = "article_decode";
			datas = "2017";
		} else {
			tables = "article" + data + "_decode";
			datas = data;
		}
		for (int i = 0; i < list.size(); i++) {
			// articleDao.articleState(list.get(i).getDocId(), tables, 9);
			
			String decodeData = list.get(i).getDecodeData();
			JSONObject jsonObject = new JSONObject().fromObject(decodeData);
			JSONObject jsonObject2 = new JSONObject().fromObject(jsonObject.getString("dirData"));
			JSONObject jsonObject3 = new JSONObject().fromObject(jsonObject.getString("htmlData"));
			String title = null;
			try {
				title = jsonObject3.getString("Title");
			} catch (Exception e) {
				continue;
			}
			JSONArray jsonArray = new JSONArray().fromObject(jsonObject2.getString("RelateInfo"));
			for (int j = 0; j < jsonArray.size(); j++) {
				JSONObject js = new JSONObject().fromObject(jsonArray.get(j));
				if (js.getString("key").equals("reason")) {
					String value = js.getString("value");
					Cause cause = new Cause();
					cause.setCausename(value);
					String table = causeDao.select(cause);
					if (table == "") {
						continue;
					}
					Article01 article01 = new Article01();
					article01.setDate(data);
					article01.setTitle(title);
					article01.setDecodeData(list.get(i).getDecodeData());
					article01.setDocId(list.get(i).getDocId());
					article01.setStates(0);
					articleDao.articleSave(table, article01);

				}
			}

		}
		if (list.size() > 0) {
			return 1;
		}
		return 0;
	}

}
