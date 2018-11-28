package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.DocSectionAndRuleDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Cause;
import com.pluskynet.domain.Docsectionandrule;
import com.pluskynet.domain.Docsectionandrule01;
import com.pluskynet.domain.User;
import com.pluskynet.test.Bigdatatest;
import com.sun.star.rdf.QueryException;

@SuppressWarnings("all")
public class DocSectionAndRuleDaoImpl extends HibernateDaoSupport implements DocSectionAndRuleDao {
	@Transactional
	public boolean save(Docsectionandrule docsectionandrule, String table) throws QueryException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (docsectionandrule.getSectiontext().indexOf("'") > -1) {
//			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\'", "\\\\'"));
//		}
//		if (docsectionandrule.getSectiontext().indexOf(":") > -1) {
//			docsectionandrule.setSectiontext(docsectionandrule.getSectiontext().replaceAll("\\:", "\\\\:"));
//		}
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String hql = "select * from " + table + " where documentsid = '" + docsectionandrule.getDocumentsid()
				+ "' and sectionName = '" + docsectionandrule.getSectionname() + "' and ruleid = "+docsectionandrule.getRuleid()+"";
		List<Docsectionandrule01> list = null;
		list = s.createSQLQuery(hql).addEntity(Docsectionandrule01.class).list();
		Session s1 = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = s1.connection();
		String sql = null;
		if (list.size() == 0) {
			sql = "insert into " + table + "(ruleid,documentsid,sectionname,sectiontext,title,createtime) values ("
					+ docsectionandrule.getRuleid() + ",'" + docsectionandrule.getDocumentsid() + "','"
					+ docsectionandrule.getSectionname() + "',?,'"
					+ docsectionandrule.getTitle() + "','"+df.format(new Date())+"')";
		}
			else {
			sql = "update " + table + " set ruleid= "
					+ docsectionandrule.getRuleid() + ",documentsid='" + docsectionandrule.getDocumentsid() + "',sectionname = '"
					+ docsectionandrule.getSectionname() + "',title='"
					+ docsectionandrule.getTitle() + "' ,sectiontext = ?,updatetime = '"+df.format(new Date())+"' where id= "
					+ list.get(0).getId();
		}
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
	public List<Docsectionandrule> getDocLists(User user) {
		String hql = "select * from docsectionandrule where belonguser = '"+user.getUsername()+"' and belongid = '"+user.getUserid()+"' ";
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
	public List<Docsectionandrule01> listdoc(String doctable, int rows,int state) {
		String sql = null;
		if (state == 0) {
			sql = "select * from " + doctable + " where (state = 0 or state is null) limit "+rows+"";
		}else {
			sql = "select * from " + doctable + " where state = "+state+" limit "+rows+"";
		}
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Docsectionandrule01> doclist = session.createSQLQuery(sql).addEntity(Docsectionandrule01.class).list();
		String hql = null;
		if (state != 3) {
			hql = "update " + doctable + " set state = 3 where id = ?";
		}else{
			hql = "update " + doctable + " set state = 5 where id = ?";
		}
		
		Connection connection = session.connection();
		try {
			PreparedStatement stmt = connection.prepareStatement(hql);
			for (int i = 0; i < doclist.size(); i++) {
				stmt.setInt(1, doclist.get(i).getId());
				stmt.addBatch();
				if (i % 100 == 0 || i==doclist.size()-1) {
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

	@Override
	@Transactional
	public List<Docsectionandrule01> getDocsectionList(Cause doctable, String year, int count, String trialRound,
			String doctype , Integer sectionname, Integer latitudename) {
//		String hql = "select * from "+doctable.getDoctable()+" a left join "+doctable.getCausetable()+" b on a.documentsid = b.doc_id where b.spcx='"+trialRound+"' and b.doctype='"+doctype+"' and b.date = '"+year+"' LIMIT "+count+";";
//		String hql ="SELECT * FROM "+doctable.getCausetable()+" WHERE id >= ((SELECT MAX(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"')-(SELECT MIN(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"')) * RAND() + (SELECT MIN(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"') and  spcx='"+trialRound+"' and doctype='"+doctype+"' and date = '"+year+"' LIMIT "+count+" ;"; 
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
//		List<Article01> lists = session.createSQLQuery(hql).addEntity(Article01.class).list();
		String sql = "select * from "+doctable.getDoctable()+" a right join ( SELECT doc_id FROM "+doctable.getCausetable()+" WHERE id >= ((SELECT MAX(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"')-(SELECT MIN(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"')) * RAND() + (SELECT MIN(id) FROM "+doctable.getCausetable()+" t1 WHERE  t1.spcx='"+trialRound+"' and t1.doctype='"+doctype+"' and t1.date = '"+year+"') and  spcx='"+trialRound+"' and doctype='"+doctype+"' and date = '"+year+"' LIMIT "+count+") b on a.documentsid = b.doc_id where ruleid = "+sectionname+"";
//		for (int i = 0; i < lists.size(); i++) {
//			if (i==lists.size()-1) {
//				sql = sql + "'"+ lists.get(i).getDocId() +"') and ruleid = "+sectionname+"";
//			}else {
//				sql = sql + "'"+ lists.get(i).getDocId() +"',";
//			}
//		}
//		if (lists.size()>0) {
			List<Docsectionandrule01> list = session.createSQLQuery(sql).addEntity(Docsectionandrule01.class).list();
			return list;
//		}else {
//			return null;
//		}
		
	}

	// public boolean update(Docsectionandrule docsectionandrule) {
	// String queryString = "update ";
	// this.getHibernateTemplate().bulkUpdate(queryString);
	// return false;
	// }

}
