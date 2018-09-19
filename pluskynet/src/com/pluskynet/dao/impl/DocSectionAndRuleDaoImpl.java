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
import com.pluskynet.test.Bigdatatest;

@SuppressWarnings("all")
public class DocSectionAndRuleDaoImpl extends HibernateDaoSupport implements DocSectionAndRuleDao {
	@Transactional
	public boolean save(Docsectionandrule docsectionandrule, String table) {
		if (docsectionandrule.getSectiontext().indexOf("'") > -1) {
			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\'", "\\\\'"));
		}
		if(docsectionandrule.getSectiontext().indexOf(":") > -1){
			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\:", "\\\\:"));
		}
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "select * from " + table + " where documentsid = '" + docsectionandrule.getDocumentsid()
				+ "' and sectionName = '" + docsectionandrule.getSectionname() + "' and sectiontext = '"+docsectionandrule.getSectiontext()+"'";
		List<Docsectionandrule> list = s.createSQLQuery(hql).addEntity(Docsectionandrule.class).list();
		if (list.size() == 0) {
			Session s1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String sql = "insert into " + table + "(ruleid,documentsid,sectionname,sectiontext,title) values ("
					+ docsectionandrule.getRuleid() + ",'" + docsectionandrule.getDocumentsid() + "','"
					+ docsectionandrule.getSectionname() + "','" + docsectionandrule.getSectiontext() + "','"
					+ docsectionandrule.getTitle() + "')";
			Query query = s1.createSQLQuery(sql);
			query.executeUpdate();
			s1.flush();
			s1.clear();
		} else {
			Session s2 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
			String sectiontext = docsectionandrule.getSectiontext();
			String querysql = "update " + table + " set sectiontext = '" + sectiontext + "' where id= " +list.get(0).getId();
			s2.createSQLQuery(querysql).executeUpdate();
			s2.flush();
			s2.clear();
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
		String hql = "select * from docsectionandrule where sectionname = '" + sectionname + "' limit 0,300;";
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Docsectionandrule> list = s.createSQLQuery(hql).addEntity(Docsectionandrule.class).list();
		return list;
	}

	@Override
	public void saveyl(Docsectionandrule docsectionandrule) {
		this.getHibernateTemplate().save(docsectionandrule);
	}

	@Override
	public void saveyldelete(String sectionname) {
		String sql = "from Docsectionandrule where sectionname = ?";
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
		String sql = "select * from " + doctable + " where sectionname = '" + sectionname + "' and state = 0 limit 10000";
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
	public void update(String doctable,String sectionname) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "update " + doctable + " set state = 0 where sectionname = '"+sectionname+"'";
		session.createSQLQuery(sql).executeUpdate();
		session.flush();
		session.clear();
	}

	// public boolean update(Docsectionandrule docsectionandrule) {
	// String queryString = "update ";
	// this.getHibernateTemplate().bulkUpdate(queryString);
	// return false;
	// }

}
