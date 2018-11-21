package com.pluskynet.dao.impl;

import java.util.List;

import org.apache.shiro.session.mgt.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.pluskynet.dao.LatitudeKeyDao;
import com.pluskynet.domain.LatitudedocKey;

public class LatitudeKeyDaoImpl extends HibernateDaoSupport implements LatitudeKeyDao {
	private SessionFactory sessionFactory;

	@Override
	public void save(LatitudedocKey latitudedocKey) {
		String sql = "from LatitudedocKey where documentid = ? and latitudename = ? and latitudeid = ? ";
		List<LatitudedocKey> list = this.getHibernateTemplate().find(sql,latitudedocKey.getDocumentid(),latitudedocKey.getLatitudename(),latitudedocKey.getLatitudeid());
		if (list.size()>0) {
			String hql = "update LatitudedocKey set documentid = ? , latitudename = ?,latitudeid = ?  where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql,latitudedocKey.getDocumentid(),latitudedocKey.getLatitudename(),latitudedocKey.getLatitudeid(),list.get(0).getId());
		}else {
			this.getHibernateTemplate().save(latitudedocKey);
		}
	}

}
