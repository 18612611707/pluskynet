package com.pluskynet.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.SampleDao;
import com.pluskynet.dao.SynonymDao;
import com.pluskynet.domain.Article01;
import com.pluskynet.domain.Articleyl;
import com.pluskynet.domain.Rule;
import com.pluskynet.domain.Sample;
import com.pluskynet.domain.User;
import com.pluskynet.service.SampleService;

public class SampleDaoImpl extends HibernateDaoSupport implements SampleDao{
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Article01> getListArticle(String table, String year, int count,String trialRound,String doctype) {
		String hql = "SELECT * FROM "+table+" WHERE `id`  >= ((SELECT MAX(id) FROM "+table+")-(SELECT MIN(id) FROM "+table+")) * RAND() + (SELECT MIN(id) FROM "+table+") "
				+ "and `date` ='"+year+"' and spcx ='"+trialRound+"' and doctype = '"+doctype+"' limit "+count+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		List<Article01> list = session.createSQLQuery(hql).addEntity(Article01.class).list();
		return list;
	}

	@Override
	public void save(List<Article01> list,User user) {
		for (int i = 0; i < list.size(); i++) {
			Articleyl articleyl = new Articleyl();
			articleyl.setDecodeData(list.get(i).getDecodeData());
			articleyl.setDocId(list.get(i).getDocId());
			articleyl.setTitle(list.get(i).getTitle());
			articleyl.setBelongid(user.getUserid());
			articleyl.setBelonguser(user.getUsername());
			this.getHibernateTemplate().save(articleyl);
		}
	}

	@Override
	@Transactional
	public void saverule(Sample sample,User user) {
		String hql = "delete From Sample where userid = "+user.getUserid()+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(hql).executeUpdate();
		this.getHibernateTemplate().save(sample);
	}

	@Override
	public Sample select(User user) {
		String hql = "from Sample where userid = "+user.getUserid()+"";
		List<Sample> list = this.getHibernateTemplate().find(hql);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}

	@Override
	@Transactional
	public void delete(User user) {
		String hql = "delete From Articleyl where userid = "+user.getUserid()+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(hql).executeUpdate();
		
		
	}


}
