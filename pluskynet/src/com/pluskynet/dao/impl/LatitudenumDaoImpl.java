package com.pluskynet.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Latitudenum;
import com.sun.org.apache.bcel.internal.generic.LALOAD;

public class LatitudenumDaoImpl extends HibernateDaoSupport implements LatitudenumDao {
	@Override
	@Transactional
	public List<Latitudenum> countlat(int type) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "";
		if (type == 1) {
			sql = "select '' as id,latitudeid,latitudename,count(1) as nums,1 as type from latitudedoc_key group by latitudeid,latitudename";
		} else {
			sql = "select '' as id,latitudeid,latitudename,SUM(nums) as nums,0 as type from (select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule11 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule12 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule13 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule14 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule15 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule16 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule17 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule18 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule19 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule20 group by ruleid,sectionname) a group by latitudeid,latitudename";
		}
		List<Latitudenum> list = session.createSQLQuery(sql).addEntity(Latitudenum.class).list();
		if (list.size()>0) {
			updatelat(list);
			return list;
		}
		return null;
	}

	@Transactional
	private void updatelat(List<Latitudenum> list) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "";
		for (int i = 0; i < list.size(); i++) {
			String hql = "from Latitudenum where latitudeid = "+list.get(i).getLatitudeid()+" and type = "+list.get(i).getType()+"";
			List<Latitudenum> latlist = this.getHibernateTemplate().find(hql);
			if (latlist.size()>0) {
				sql = "update latitudenum set latitudename = '"+latlist.get(0).getLatitudename()+"',nums = "+latlist.get(0).getNums()+" where id ="+latlist.get(0).getId()+"";
				session.createSQLQuery(sql).executeUpdate();
			}else{
				this.getHibernateTemplate().save(list.get(i));
			}
		}
	}

}
