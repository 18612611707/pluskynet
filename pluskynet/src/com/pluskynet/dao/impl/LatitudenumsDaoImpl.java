package com.pluskynet.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.pluskynet.dao.LatitudenumsDao;
import com.pluskynet.domain.Latitudenum;

public class LatitudenumsDaoImpl extends HibernateDaoSupport implements LatitudenumsDao {

	@Override
	public List<Latitudenum> getnums(int type) {
		String sql = "from Latitudenum where type = "+type+"";
		List<Latitudenum> list = this.getHibernateTemplate().find(sql);
		return list;
	}

}
