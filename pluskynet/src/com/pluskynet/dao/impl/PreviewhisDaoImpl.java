package com.pluskynet.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.PreviewhisDao;
import com.pluskynet.domain.Previewhis;

public class PreviewhisDaoImpl extends HibernateDaoSupport implements PreviewhisDao {

	@Override
	@Transactional
	public List<Previewhis> select(String starttime,String endtime) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "select * from previewhis where createtime between '"+starttime+"' and '"+endtime+"'";
		List<Previewhis> list = session.createSQLQuery(sql).addEntity(Previewhis.class).list();
		return list;
	}

	@Override
	public void save(Previewhis previewhis) {
		this.getHibernateTemplate().save(previewhis);
	}

}
