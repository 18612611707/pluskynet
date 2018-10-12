package com.pluskynet.save;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.domain.Docsectionandrule;

public class DocrulePlsave extends HibernateDaoSupport{
	private List<Docsectionandrule> docsectionlist;
	private String doctable;
	
	public Boolean plsave(List<Docsectionandrule> docsectionlist, String doctable) {
				
		return true;
	}
	@Transactional
	public void run(){
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
	}

}
