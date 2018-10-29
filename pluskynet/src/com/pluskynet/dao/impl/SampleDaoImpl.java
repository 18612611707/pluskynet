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
import com.pluskynet.domain.Docsectionandrule;
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
	/*
	 * (non-Javadoc)
	 * @see com.pluskynet.dao.SampleDao#save(java.util.List, com.pluskynet.domain.User)
	 * 保存段落样本
	 */
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
		String hql = "delete From Sample where belongid = "+user.getUserid()+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(hql).executeUpdate();
		this.getHibernateTemplate().save(sample);
	}

	@Override
	public Sample select(User user) {
		String hql = "from Sample where belongid = "+user.getUserid()+"";
		List<Sample> list = this.getHibernateTemplate().find(hql);
		if (list.size()>0) {
			return list.get(0);
		}
		return null;
		
	}

	@Override
	@Transactional
	/*
	 * (non-Javadoc)
	 * @see com.pluskynet.dao.SampleDao#delete(com.pluskynet.domain.User)
	 * 删除段落样本
	 */
	public void delete(User user) {
		String hql = "delete From Articleyl where belongid = "+user.getUserid()+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(hql).executeUpdate();
	}

	@Override
	/*
	 * (non-Javadoc)删除段落样本
	 * @see com.pluskynet.dao.SampleDao#deleteDoc(com.pluskynet.domain.User)
	 */
	public void deleteDoc(User user) {
		String hql = "delete From Docsectionandrule where belongid = "+user.getUserid()+"";
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(hql).executeUpdate();
	}

	@Override
	/*
	 * (non-Javadoc)保存段落样本
	 * @see com.pluskynet.dao.SampleDao#saveDoc(java.util.List, com.pluskynet.domain.User)
	 */
	public void saveDoc(List<Docsectionandrule> list, User user) {
		for (int i = 0; i < list.size(); i++) {
			Docsectionandrule docsectionandrule = new Docsectionandrule();
			docsectionandrule.setSectionname(list.get(i).getSectiontext());
			docsectionandrule.setDocumentsid(list.get(i).getDocumentsid());
			docsectionandrule.setSectionname(list.get(i).getSectionname());
			docsectionandrule.setTitle(list.get(i).getTitle());
			docsectionandrule.setBelongid(user.getUserid());
			docsectionandrule.setBelonguser(user.getUsername());
			this.getHibernateTemplate().save(docsectionandrule);
		}
		
	}


}
