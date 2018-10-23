package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.User;
import com.pluskynet.test.Bigdatatest;
import com.sun.star.rdf.QueryException;

@SuppressWarnings("all")
public class DocSectionAndRuleDaoImpl extends HibernateDaoSupport implements DocSectionAndRuleDao {
	@Transactional
	public boolean save(Docsectionandrule docsectionandrule, String table) throws QueryException {
		if (docsectionandrule.getSectiontext().indexOf("'") > -1) {
			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\'", "\\\\'"));
		}
		if (docsectionandrule.getSectiontext().indexOf(":") > -1) {
			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\:", "\\\\:"));
		}
//		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
//		String hql = "select * from " + table + " where documentsid = '" + docsectionandrule.getDocumentsid()
//				+ "' and sectionName = '" + docsectionandrule.getSectionname() + "'";
//		List<Docsectionandrule> list = null;
//		list = s.createSQLQuery(hql).addEntity(Docsectionandrule.class).list();
		Session s1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = s1.connection();
		String sql = null;
//		if (list.size() == 0) {
			sql = "insert into " + table + "(ruleid,documentsid,sectionname,sectiontext,title) values ("
					+ docsectionandrule.getRuleid() + ",'" + docsectionandrule.getDocumentsid() + "','"
					+ docsectionandrule.getSectionname() + "',?,'"
					+ docsectionandrule.getTitle() + "')";
//		}
//			else {
//			sql = "update " + table + " set sectiontext = ? where id= "
//					+ list.get(0).getId();
//		}
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, docsectionandrule.getSectiontext());
			stmt.addBatch();
			stmt.executeBatch();
			conn.setAutoCommit(false);
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Docsectionandrule> getDocList() {
		String queryString = "from Docsectionandrule where id in (select max(id) from Docsectionandrule group by id)";
		List<Docsectionandrule> list = this.getHibernateTemplate().find(queryString);
		return list;
	}

	@Override
	public List<Docsectionandrule> getDoc(Docsectionandrule docsectionandrule) {
		String hql = "from Docsectionandrule where documentsid = ?";
		List<Docsectionandrule> list = this.getHibernateTemplate().find(hql, docsectionandrule.getDocumentsid());
		return list;
	}

	@Override
	@Transactional
	public List<Docsectionandrule> getDocLists(String sectionname) {
		String hql = "select * from docsectionandrule where sectionname = '" + sectionname + "' ";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Docsectionandrule> list = s.createSQLQuery(hql).addEntity(Docsectionandrule.class).list();
		return list;
	}

	@Override
	public void saveyl(Docsectionandrule docsectionandrule) {
		this.getHibernateTemplate().save(docsectionandrule);
	}

	@Override
	public void saveyldelete(String sectionname,User user) {
		String sql = "from Docsectionandrule where sectionname = ? and userid = '"+user.getUserid()+"'";
		List<Docsectionandrule> list = this.getHibernateTemplate().find(sql, sectionname);
		if (list.size() > 0) {
			for (int j = 0; j < list.size(); j++) {
				this.getHibernateTemplate().delete(list.get(j));
			}
		}

	}

	@Override
	@Transactional
	public List<Docsectionandrule> listdoc(String doctable, int rows, String sectionname) {
		String sql = "select * from " + doctable + " where sectionname = '" + sectionname
				+ "' and (state = 0 or state is null) limit "+rows+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Docsectionandrule> doclist = session.createSQLQuery(sql).addEntity(Docsectionandrule.class).list();
		String hql = "update " + doctable + " set state = 1 where id = ?";
		Connection connection = session.connection();
		try {
			PreparedStatement stmt = connection.prepareStatement(hql);
			for (int i = 0; i < doclist.size(); i++) {
				stmt.setInt(1, doclist.get(i).getId());
				stmt.addBatch();
				if (i % 100 == 0) {
					stmt.executeBatch();
					connection.setAutoCommit(false);
					connection.commit();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doclist;

	}

	@Override
	@Transactional
	public void update(String doctable, String sectionname) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update " + doctable + " set state = 0 where sectionname = '" + sectionname + "'";
		session.createSQLQuery(sql).executeUpdate();
		session.flush();
		session.clear();
	}

	@Override
	@Transactional
	public Boolean plsave(List<Docsectionandrule> docsectionlist, String doctable) {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "";
		Connection conn = s.connection();
		for (int i = 0; i < docsectionlist.size(); i++) {
			if (docsectionlist.get(i).getSectiontext().indexOf("'") > -1) {
				docsectionlist.get(i).setSectiontext(docsectionlist.get(i).getSectiontext().replaceAll("\\'", "\\\\'"));
			}
			if (docsectionlist.get(i).getSectiontext().indexOf(":") > -1) {
				docsectionlist.get(i).setSectiontext(docsectionlist.get(i).getSectiontext().replaceAll("\\:", "\\\\:"));
			}
			String hql = "select * from " + doctable + " where documentsid = '" + docsectionlist.get(i).getDocumentsid()
					+ "' and sectionName = '" + docsectionlist.get(i).getSectionname() + "'";
			List<Docsectionandrule> list = null;
			list = s.createSQLQuery(hql).addEntity(Docsectionandrule.class).list();
			if (list.size() == 0) {
				sql = "insert into " + doctable + "(ruleid,documentsid,sectionname,sectiontext,title) values ("
						+ docsectionlist.get(i).getRuleid() + ",'" + docsectionlist.get(i).getDocumentsid() + "','"
						+ docsectionlist.get(i).getSectionname() + "', ? ,'" + docsectionlist.get(i).getTitle() + "')";
			} else {
				sql = "update " + doctable + " set sectiontext = ? where id= " + list.get(0).getId();
			}
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, docsectionlist.get(i).getSectiontext());
				stmt.addBatch();
				if (i % 1000 == 0 || i == (docsectionlist.size() - 1)) {
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	// public boolean update(Docsectionandrule docsectionandrule) {
	// String queryString = "update ";
	// this.getHibernateTemplate().bulkUpdate(queryString);
	// return false;
	// }

}
