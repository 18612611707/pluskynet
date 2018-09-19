package com.pluskynet.dao.impl;

import java.util.List;

import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.LatitudewordDao;
import com.pluskynet.domain.LatitudedocKey;
import com.pluskynet.domain.LatitudedocWord;

public class LatitudewordDaoImpl extends HibernateDaoSupport implements LatitudewordDao {
	private SessionFactory sessionFactory;

	@Override
	public void save(LatitudedocWord latitudedocWord) {
		String sql = "from LatitudedocWord where documentid = ? and latitudename = ?";
		List<LatitudedocWord> list = this.getHibernateTemplate().find(sql,latitudedocWord.getDocumentid(),latitudedocWord.getLatitudename());
		if (list.size()>0) {
			String hql = "update LatitudedocWord set documentid = '"+latitudedocWord.getDocumentid()+"' and latitudename = '"+latitudedocWord.getLatitudename()+"' and latitudetext = ? where id = "+list.get(0).getId();
			this.getHibernateTemplate().bulkUpdate(hql,latitudedocWord.getLatitudetext());
		}else {
			this.getHibernateTemplate().save(latitudedocWord);
		}
		
	}

}
