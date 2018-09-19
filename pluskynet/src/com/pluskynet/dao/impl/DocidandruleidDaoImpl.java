package com.pluskynet.dao.impl;

import java.util.List;

import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.DocidandruleidDao;
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

}
