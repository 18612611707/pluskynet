package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Else;
import org.apache.struts2.components.Select;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.ArticleDao;
import com.pluskynet.domain.Article;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.test.Articletest;
import com.pluskynet.test.Bigdatatest;
import com.pluskynet.util.PageNoUtil;
import com.sun.star.beans.GetDirectPropertyTolerantResult;

@SuppressWarnings("all")
public class ArticleDaoImpl extends HibernateDaoSupport implements ArticleDao {
	private SessionFactory sessionFactory;
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<Article> getArticles() {
		String hql = "from Article";
		List<Article> list = this.getHibernateTemplate().find(hql);
		// if (list.size()>0) {
		// return list;
		// }
		return list;
	}

	@Override
	public boolean updateArticle(String docid) {
		Article article = new Article();
		String query = "from Article where docid = ?";
		List<Article> articles = this.getHibernateTemplate().find(query, docid);
		if (articles.size() > 0) {
			article = articles.get(0);
		}
		String hql = "update Article set isok = 1 where Id in (?)";
		this.getHibernateTemplate().bulkUpdate(hql, article.getId());
		return true;
	}

	@Override
	public List<Article> findPageBy(Article article, int page, int rows, String sidx, String sord) {
		String sql = "";
		String sqlAfter = "";
		int toatl = 0;
		toatl = (page - 1) * rows;
		if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
			sql = "from Article where isok <> 1 ORDER BY %s %s limit %s, %s; ";
			sqlAfter = String.format(sql, sidx, sord, toatl, rows);
		} else {
			sql = "from Article where isok <> 1 limit %s, %s;";
			sqlAfter = String.format(sql, toatl, rows);
		}
		int offset = toatl;
		int length = rows;
		List<Article> list = getListForPage(sqlAfter, offset, length);
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

	public List getListForPage(final String hql, final int offset, final int length) {
		List list1 = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list2 = PageNoUtil.getList(session, hql, offset, length);
				return list2;
			}
		});
		return list1;
	}

	@Override
	public int getCountBy(Article article) {
		String hql = "from Article where isok <> 1";
		List<Article> list = this.getHibernateTemplate().find(hql);
		return list.size();
	}

	@Override
	public List<Article> getArticles(List<Docsectionandrule> list) {
		String hql = "from Article where doc_id not in (";
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				hql += list.get(i).getDocumentsid() + ")";
				break;
			}
			hql += list.get(i).getDocumentsid() + ",";
		}
		List<Article> article = this.getHibernateTemplate().find(hql);
		return article;
	}

	@Override
	public List<Article> getArt(String docid) {
		String query = "from Article where doc_id = ?";
		List<Article> articles = this.getHibernateTemplate().find(query, docid);
		return articles;
	}

	@Override
	@Transactional
	public List<Article> breakArticle(String data, int rows) {
		Article article = new Article();
		article.setState(7);
		String table = null;
		if (data==null || data.equals("")) {
			table = "article_decode";
		} else {
			table = "article" + data + "_decode";
		}
		String hql = "select * from " + table + " where state = " + article.getState() + " limit " + rows + "";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Article> list = s.createSQLQuery(hql).addEntity(Article.class).list();
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update " + table + " set state = 9 where doc_id = ?";
		Connection conn = session.connection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);

			for (int i = 0; i < list.size(); i++) {
				
				stmt.setString(1, list.get(i).getDocId());
				stmt.addBatch();
				if (i % 100 == 0) {
					System.out.println(list.get(i).getDocId());
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				}
				if (i==(list.size()-1) && i % 100 != 0) {
					System.out.println(list.get(i).getDocId());
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Transactional
	public void articleSave(String table, Article01 article01) {
		String data = article01.getDate();
		String tables = null;
		if (data==null || data.equals("")) {
			tables = "article_decode";
			data = "2017";
		} else {
			tables = "article" + data + "_decode";
		}
		String sqlString = "select * from " + table + " where doc_id='" + article01.getDocId() + "'";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Article01> list = s.createSQLQuery(sqlString).addEntity(Article01.class).list();
		s.flush();
		s.clear();
		if (list.size() < 1) {
			String hql = "insert into " + table + "(doc_id,date,decode_data,states,title) Select doc_id,'" + data
					+ "',decode_data,0,'" + article01.getTitle() + "' from " + tables + " where doc_id='"
					+ article01.getDocId() + "'";
			Session s1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			Query query = s1.createSQLQuery(hql);
			query.executeUpdate();
			s1.flush();
			s1.clear();
			// tran.commit();

		}
	}

	/*
	 * 获取所有未挖掘的文书1：全部，2是剩余(non-Javadoc)
	 * 
	 * @see com.pluskynet.dao.ArticleDao#getArticle01List(java.lang.String, int)
	 */
	@Transactional
	public List<Article01> getArticle01List(String table, int allorre, int rows) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String hql = null;
		if (allorre == 1) {
			hql = "select * from " + table + " where id>=(select id from "+table+" where states = 3 order by id limit 0,1) and states=3 order by id limit " + rows;
		} else if (allorre == 0) {
			hql = "select * from " + table + " where id>=(select id from "+table+" where states = 3 order by id limit 0,1) and states=3 order by id limit " + rows;
		} else if (allorre == 3) {
			hql = "select * from " + table + " where id>=(select id from "+table+" where states = 3 order by id limit 0,1) and states=3 order by id limit " + rows;
		}
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		List<Article01> list = s.createSQLQuery(hql).addEntity(Article01.class).list();
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update " + table + " set states = 0 where doc_id = ?";
		Connection conn = session.connection();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				stmt.setString(1, list.get(i).getDocId());
				stmt.addBatch();
				if (i % 1000 == 0) {
					System.out.println(list.get(i).getDocId());
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				}
				if (i==(list.size()-1) && i % 1000 != 0) {
					System.out.println(list.get(i).getDocId());
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				}
			}
			session.flush();
			session.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	@Transactional
	public void updateArticleState(String docid, String table, int states) {
		String sql = "update " + table + " set states = " + states + " where doc_id = '" + docid + "'";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		s.createSQLQuery(sql).executeUpdate();
		s.flush();
		s.clear();
	}

	@Transactional
	public void articleState(String table, int states) {
		String sql = null;
		if (states == 3) {
			sql = "update " + table + " set states = 0 where states = 3";
		} else {
			sql = "update " + table + " set states = 0";
		}
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		s.createSQLQuery(sql).executeUpdate();
		s.flush();
		s.clear();
	}

	@Override
	@Transactional
	public void delete(int a) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		int i = 0;
		do {
			String sql = "select id from (select doc_id,count(*) as count,MAX(id) as id from article04 group by doc_id having count = "
					+ a + ") a limit 10000;";
			PreparedStatement statement;
			try {
				statement = conn.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				i = 0;
				String hql = "delete from article04 where id = ?";
				PreparedStatement stmt = conn.prepareStatement(hql);
				while (resultSet.next()) {
					i++;
					stmt.setInt(1, resultSet.getInt("id"));
					stmt.addBatch();
					if (i % 100 == 0) {
						stmt.executeBatch();
						conn.setAutoCommit(false);
						conn.commit();
					}
				}
//				System.out.println(i);
			} catch (SQLException e) {
				System.out.println("出错了");
				e.printStackTrace();
			}
			session.flush();
			session.clear();

		} while (i > 0);
		// try {
		// String hql = "delete from article04 where id = ?";
		// PreparedStatement stmt = conn.prepareStatement(hql);
		// for (int i = 0; i < list.size(); i++) {
		// String a = list.get(i);
		// System.out.println(a);
		//// stmt.setInt(i, a);
		// stmt.addBatch();
		// if (i % 100 == 0) {
		// stmt.executeBatch();
		// conn.setAutoCommit(false);
		// conn.commit();
		// }
		// if(i==list.size()-1 && i%100!=0){
		// stmt.executeBatch();
		// conn.setAutoCommit(false);
		// conn.commit();
		// }
		//
		// }
		// session.flush();
		// session.clear();
		// } catch (Exception e) {
		// System.out.println("出错了");
		// }
	}
}
