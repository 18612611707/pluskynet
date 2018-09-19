package com.pluskynet.dao.impl;

import java.util.List;

import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.domain.LatitudedocTime;

public class LatitudetimeDaoImpl extends HibernateDaoSupport implements com.pluskynet.dao.LatitudetimeDao {
	private SessionFactory sessionFactory;

	@Override
	public void save(LatitudedocTime latitudedocTime) {
		String sql = "from LatitudedocTime where documentid = ? and latitudename = ?";
		List<LatitudedocKey> list = this.getHibernateTemplate().find(sql,latitudedocTime.getDocumentid(),latitudedocTime.getLatitudename());
		if (list.size()>0) {
			String hql = "update LatitudedocTime set documentid = ? and latitudename = ? and latitudetime =? where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql,latitudedocTime.getDocumentid(),latitudedocTime.getLatitudename(),latitudedocTime.getLatitudetime(),list.get(0).getId());
		}else {
			this.getHibernateTemplate().save(latitudedocTime);
		}
		
	}

}
