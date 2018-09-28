package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.shiro.session.mgt.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.DocidandruleidDao;
import com.pluskynet.domain.Article;
import com.pluskynet.domain.Batchdata;
import com.pluskynet.domain.Docidandruleid;

public class DocidandruleidDaoImpl extends HibernateDaoSupport implements DocidandruleidDao {
	private SessionFactory sessionFactory;

	@Override
	public void save(Docidandruleid docidandruleid) {
		String sql = "from Docidandruleid where docid = ? and ruleid = ?";
		List<Docidandruleid> list = this.getHibernateTemplate().find(sql,docidandruleid.getDocid(),docidandruleid.getRuleid());
		if (list.size()==0) {
			this.getHibernateTemplate().save(docidandruleid);
		}
		
	}

	@Override
	@Transactional
	public void plsave(List<Docidandruleid> docidlist) {
		Session s = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = null;
		Connection conn = s.connection();
		for (int i = 0; i < docidlist.size(); i++) {
			String hql = "select * from docidandruleid where docid = '"+docidlist.get(i).getDocid()+"' and ruleid = '"+docidlist.get(i).getRuleid()+"'";
			List<Docidandruleid> list = s.createSQLQuery(hql).addEntity(Docidandruleid.class).list();
			if (list.size()==0) {
				sql = "insert into docidandruleid (docid,ruleid) values ('"+docidlist.get(i).getDocid()+"','"+docidlist.get(i).getRuleid()+"')";
			}
			if(sql != null && i%1000==0){
				try {
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.executeBatch();
					conn.setAutoCommit(false);
					conn.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else if(i==docidlist.size()-1 && i%1000!=0){
					try {
						PreparedStatement stmt = conn.prepareStatement(sql);
						stmt.executeBatch();
						conn.setAutoCommit(false);
						conn.commit();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
	}
}
