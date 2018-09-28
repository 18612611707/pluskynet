package com.pluskynet.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pluskynet.dao.ArticleylDao;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.DocidAndDoc;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.StatsDoc;
import com.pluskynet.service.DocSectionAndRuleService;
import com.sun.star.rdf.QueryException;

public class DocSectionAndRuleServiceImpl implements DocSectionAndRuleService {
	private DocSectionAndRuleDao docSectionAndRuleDao;
	public void setDocSectionAndRuleDao(DocSectionAndRuleDao docSectionAndRuleDao) {
		this.docSectionAndRuleDao = docSectionAndRuleDao;
	}
	private ArticleylDao articleDao;

	public void setArticleDao(ArticleylDao articleDao) {
		this.articleDao = articleDao;
	}
//	@Override
//	public void save() {
//		List<Article> articles = articleDao.getArticles();
//		Parsing parsing = new Parsing();
//		List<Documentsbasic> documentsbasics = parsing.JosnPar(articles);
//		for (int i = 0; i < documentsbasics.size(); i++) {
//			documentsBasicDao.sava(documentsbasics.get(i));
//			articleDao.updateArticle(documentsbasics.get(i).getDocumentsid());
//		}
//}

	@Override
	public void save(Docsectionandrule docsectionandrule,String table) {
		try {
			docSectionAndRuleDao.save(docsectionandrule,table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(QueryException a){
			
		}
	}

	@Override
	public List<StatsDoc> getDocList() {
		List<Docsectionandrule> list = docSectionAndRuleDao.getDocList();
		List<Articleyl> article = articleDao.getArticles(list);
		List<StatsDoc> listdoc = new ArrayList<StatsDoc>();
		for (int i = 0; i < list.size(); i++) {
			StatsDoc statsDoc = new StatsDoc();
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			statsDoc.setStats("符合");
			docidAndDoc.setDoc(list.get(i).getSectiontext());
			docidAndDoc.setDocid(list.get(i).getDocumentsid());
			docidAndDoc.setTitle(list.get(i).getTitle());
			statsDoc.setDocidAndDoc(docidAndDoc);
			listdoc.add(statsDoc);
		}
		for (int x = 0; x < article.size(); x++) {
			StatsDoc statsDoc = new StatsDoc();
			DocidAndDoc docidAndDoc = new DocidAndDoc();
			statsDoc.setStats("不符合");
			docidAndDoc.setDoc(article.get(x).getData());
			docidAndDoc.setDocid(article.get(x).getDocId());
			docidAndDoc.setTitle(article.get(x).getTitle());
			statsDoc.setDocidAndDoc(docidAndDoc);
			listdoc.add(statsDoc);
		}
		return listdoc;
	}

	@Override
	public List<Docsectionandrule> getDoc(Docsectionandrule docsectionandrule) {
		List<Docsectionandrule> list= docSectionAndRuleDao.getDoc(docsectionandrule);
		return list;
	}

	@Override
	public void update(String doctable, String sectionname) {
		docSectionAndRuleDao.update(doctable, sectionname);
		
	}

	@Override
	public void plsave(List<Docsectionandrule> docsectionlist, String doctable) {
		docSectionAndRuleDao.plsave(docsectionlist,doctable);
		
	}
}
