package com.pluskynet.service.impl;

import java.util.ArrayList;
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
		String table = null;
		synchronized (ob) {
			list = articleDao.breakArticle(data, rows);
		}
		if (data == null || data.equals("")) {
			tables = "article_decode";
			datas = "2017";
		} else {
			tables = "article" + data + "_decode";
			datas = data;
		}
		for (int i = 0; i < list.size(); i++) {
			// articleDao.articleState(list.get(i).getDocId(), tables, 9);
			String court = null;
			String casetype = null;
			String reason = null;
			String trialRound = null;
			String trialDate = null;
			String appellor = null;
			String LegalBase = null;
			String ccourtid = null;
			String caseno = null;
			String courtcities = null;
			String courtprovinces = null;
			String casename = null;
			String doctype = null;
			String decodeData = list.get(i).getDecodeData();
			JSONObject jsonObject = new JSONObject().fromObject(decodeData);
			JSONObject jsonObject2 = new JSONObject().fromObject(jsonObject.getString("dirData"));
			JSONObject jsonObject3 = new JSONObject().fromObject(jsonObject.getString("htmlData"));
			JSONObject jsonObject4 = new JSONObject().fromObject(jsonObject.getString("caseinfo"));
			String title = null;
			try {
				title = jsonObject3.getString("Title");
				if (title.contains("判决书")) {
					doctype = "判决书";
				}else if (title.contains("裁定书")) {
					doctype = "裁定书";
				}else if (title.contains("调解书")) {
					doctype = "调解书";
				}else if (title.contains("决定书")) {
					doctype = "决定书";
				}else if (title.contains("通知书")) {
					doctype = "通知书";
				}else if (title.contains("批复")) {
					doctype = "批复";
				}else if (title.contains("答复")) {
					doctype = "答复";
				}else if (title.contains("函")) {
					doctype = "函";
				}else if (title.contains("令")) {
					doctype = "令";
				}
			} catch (Exception e) {
				continue;
			}
			JSONArray jsonArray = new JSONArray().fromObject(jsonObject2.getString("RelateInfo"));
			for (int j = 0; j < jsonArray.size(); j++) {
				
				JSONObject js = new JSONObject().fromObject(jsonArray.get(j));
				if (js.getString("key").equals("caseType")) {
					casetype = js.getString("value");
				}
				if (js.getString("key").equals("reason")) {
					reason = js.getString("value");
				}
				if (js.getString("key").equals("court")) {
					court = js.getString("value");
				}
				if (js.getString("key").equals("trialRound")) {
					trialRound = js.getString("value");
				}
				if (js.getString("key").equals("trialDate")) {
					trialDate = js.getString("value");
				}
				if (js.getString("key").equals("appellor")) {
					appellor = js.getString("value");
				}
			}
			/*LegalBase = jsonObject2.getString("LegalBase").toString();
			LegalBase =LegalBase.replace("\\'", "\\\\'");*/
			for (int j = 0; j < jsonObject4.size(); j++) {
				ccourtid = jsonObject4.getString("法院ID").toString();
				caseno = jsonObject4.getString("案号").toString();
				courtcities = jsonObject4.getString("法院地市").toString();
				courtprovinces = jsonObject4.getString("法院省份").toString();
				casename = jsonObject4.getString("案件名称").toString();
			}
				Cause cause = new Cause();
				cause.setCausename(reason);
				table = causeDao.select(cause);
				if (table == "") {
					continue;
				}	
				Article01 article01 = new Article01();
				article01.setDate(data);
				article01.setTitle(title);
				article01.setDecodeData(list.get(i).getDecodeData().replace("\\'", "\\\\'"));
				article01.setDocId(list.get(i).getDocId());
				article01.setStates(0);
				article01.setAppellor(appellor);
				article01.setCasename(casename);
				article01.setCaseno(caseno);
				article01.setCasetype(casetype);
				article01.setCcourtid(Integer.parseInt(ccourtid));
				article01.setCourt(court);
				article01.setCourtcities(courtcities);
				article01.setCourtprovinces(courtprovinces);
				article01.setDoctype(doctype);
				article01.setLegalbase(LegalBase);
				article01.setSpcx(trialRound);
				article01.setTrialdate(trialDate);
				article01.setReason(reason);
				articleDao.articleSave(table, article01);
		}
		

		if (list.size() > 0) {
			return 1;
		}
		return 0;
	}

}
