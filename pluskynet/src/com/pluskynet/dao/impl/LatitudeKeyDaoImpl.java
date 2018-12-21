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
		String sql = "from LatitudedocKey where documentid = ? and latitudeid = ? ";
		List<LatitudedocKey> list = this.getHibernateTemplate().find(sql,latitudedocKey.getDocumentid(),latitudedocKey.getLatitudeid());
		if (list.size()>0) {
			String hql = "update LatitudedocKey set documentid = ? , latitudename = ?,latitudeid = ?,sectionid = ? ,updatatime = ? ,location = ?  where id = ?";
			this.getHibernateTemplate().bulkUpdate(hql,latitudedocKey.getDocumentid(),latitudedocKey.getLatitudename(),latitudedocKey.getLatitudeid()
					,latitudedocKey.getSectionid(),latitudedocKey.getUpdatatime(),latitudedocKey.getLocation(),list.get(0).getId());
		}else {
			this.getHibernateTemplate().save(latitudedocKey);
		}
	}

	@Override
	public void delete(LatitudedocKey latitudedocKey) {
		String sql = "from LatitudedocKey where documentid = ? and latitudeid = ? ";
		List<LatitudedocKey> list = this.getHibernateTemplate().find(sql,latitudedocKey.getDocumentid(),latitudedocKey.getLatitudeid());
		for (int i = 0; i < list.size(); i++) {
			this.getHibernateTemplate().delete(list.get(i));
		}
		
	}

}
