package com.pluskynet.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.ArticleylDao;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.util.PageNoUtil;
@SuppressWarnings("all")
public class ArticleylDaoImpl extends HibernateDaoSupport implements ArticleylDao {
	private SessionFactory sessionFactory;
	
	@Override
	public List<Articleyl> getArticles() {
		String hql = "from Articleyl";
		List<Articleyl> list = this.getHibernateTemplate().find(hql);
//		if (list.size()>0) {
//			return list;
//		}
		return list;
	}

	@Override
	public boolean updateArticle(String docid) {
		Articleyl article = new Articleyl();
		String query = "from Articleyl where docid = ?";
		List<Articleyl> articles = this.getHibernateTemplate().find(query,docid);
		if (articles.size()>0) {
			article = articles.get(0);
		}
		String hql = "update Articleyl set isok = 1 where Id in (?)";
		this.getHibernateTemplate().bulkUpdate(hql,article.getId());	
		return true;
	}
	@Override
	public List<Articleyl> findPageBy(Articleyl article, int page, int rows, String sidx, String sord) {
		String sql = "";
		String sqlAfter = "";
		int toatl = 0;
		toatl = (page - 1) * rows;
		if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
			sql = "from Articleyl where isok <> 1 ORDER BY %s %s limit %s, %s; ";
			sqlAfter = String.format(sql, sidx, sord, toatl, rows);
		} else {
			sql = "from Articleyl where isok <> 1 limit %s, %s;";
			sqlAfter = String.format(sql, toatl, rows);
		}
		int offset = toatl;
		int length = rows;
		List<Articleyl> list =  getListForPage(sqlAfter, offset, length);
		return list;
	}

	/**
	 * 
	 * 使用hql 语句进行操作
	 * 
	 * @param hql
	 *            �?��执行的hql语句
	 * @param offset
	 *            设置�?��位置
	 * @param length
	 *            设置读取数据的记录条�?
	 * @return List 返回�?��要的集合�?
	 */

	public List<Articleyl> getListForPage(final String hql, final int offset, final int length) {
		List<Articleyl> list1 = getHibernateTemplate().executeFind(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List<?> list2 = PageNoUtil.getList(session, hql, offset, length);
				return list2;
			}
		});
		return list1;
	}

	@Override
	public int getCountBy(Articleyl article) {
		String hql = "from Articleyl where isok <> 1";
		List<Articleyl> list = this.getHibernateTemplate().find(hql);
		return list.size();
	}

	@Override
	public List<Articleyl> getArticles(List<Docsectionandrule> list) {
		String hql = "from Articleyl where doc_id not in (";
		for (int i = 0; i < list.size(); i++) {
            if(i==list.size()-1){
                hql+=list.get(i).getDocumentsid()+")";
                break;
            }
            hql+=list.get(i).getDocumentsid()+",";
        }
		List<Articleyl> article = this.getHibernateTemplate().find(hql);
		return article;
	}

	@Override
	public List<Articleyl> getArt(String docid) {
		String query = "from Articleyl where doc_id = ?";
		List<Articleyl> articles = this.getHibernateTemplate().find(query,docid);
		return articles;
	}

}
