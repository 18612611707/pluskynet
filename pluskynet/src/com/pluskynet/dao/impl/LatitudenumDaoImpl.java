package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.pluskynet.dao.LatitudenumDao;
import com.pluskynet.domain.Latitudenum;

public class LatitudenumDaoImpl extends HibernateDaoSupport implements LatitudenumDao {
	@Override
	@Transactional
	public List<Latitudenum> countlat(int type) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Connection conn = session.connection();
		List<Latitudenum> list = new ArrayList<Latitudenum>();
		String sql = "";
		if (type == 1) {
			sql = "select latitudeid,latitudename,count(1) as nums,1 as type from latitudedoc_key group by latitudeid,latitudename";
		} else {
			sql = "select latitudeid,latitudename,SUM(nums) as nums,0 as type from (select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule11 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule12 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule13 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule14 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule15 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule16 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule17 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule18 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule19 group by ruleid,sectionname union all select ruleid as latitudeid,sectionname as latitudename,COUNT(1) as nums from Docsectionandrule20 group by ruleid,sectionname) a group by latitudeid,latitudename";
		}
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Latitudenum latitudenum = new Latitudenum();
				latitudenum.setLatitudeid(resultSet.getInt("latitudeid"));
				latitudenum.setLatitudename(resultSet.getString("latitudename"));
				latitudenum.setNums(resultSet.getInt("nums"));
				latitudenum.setType(resultSet.getInt("type"));
				list.add(latitudenum);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			updatelat(list);
			return list;
		}
		return null;
	}
	@Override
	@Transactional
	public boolean updatelat(List<Latitudenum> list) {
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sql = "";
		for (int i = 0; i < list.size(); i++) {
			String hql = "from Latitudenum where latitudeid = " + list.get(i).getLatitudeid() + " and type = "
					+ list.get(i).getType() + "";
			List<Latitudenum> latlist = this.getHibernateTemplate().find(hql);
			if (latlist.size() > 0) {
				sql = "update latitudenum set latitudename = '" + latlist.get(0).getLatitudename() + "',nums = "
						+ latlist.get(0).getNums() + " where id =" + latlist.get(0).getId() + "";
				session.createSQLQuery(sql).executeUpdate();
			} else {
				this.getHibernateTemplate().save(list.get(i));
			}
		}
		return true;
	}

	@Override
	public List<Latitudenum> getnums(int type) {
		String sql = null;
		if (type == -1) {
			sql = "from Latitudenum ";
		} else {
			sql = "from Latitudenum where type = " + type + "";
		}
		List<Latitudenum> list = this.getHibernateTemplate().find(sql);
		return list;
	}

}
