package com.pluskynet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.pluskynet.dao.BatchStatisticalDao;
import com.pluskynet.domain.Statisticalnum;

public class BatchStatisticalDaoImpl extends HibernateDaoSupport implements BatchStatisticalDao {

	@Override
	@Transactional
	public List<Statisticalnum> docStatistical() {
		List<Map> maplist = new ArrayList<Map>();
		List<Statisticalnum> list = new ArrayList<Statisticalnum>();
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sumdoc = "select states,SUM(nums) as docsum from (select states,count(1) as nums from article11 group by states union all select states,count(1) as nums from article12 group by states union all select states,count(1) as nums from article13 group by states union all select states,count(1) as nums from article14 group by states union all select states,count(1) as nums from article15 group by states union all select states,count(1) as nums from article16 group by states union all select states,count(1) as nums from article17 group by states union all select states,count(1) as nums from article18 group by states union all select states,count(1) as nums from article19 group by states union all select states,count(1) as nums from article20 group by states) a group by states ";
		Connection conn = session.connection();
		String auditdoc = "select batchstats from latitudeaudit where latitudetype = 0 and casetype = 1 limit 1;";
		int batchstats = -1;
		try {
			PreparedStatement statement = conn.prepareStatement(sumdoc);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map map = new HashMap();
				map.put(resultSet.getInt("states"), resultSet.getInt("docsum"));
				maplist.add(map);
			}
			PreparedStatement statement1 = conn.prepareStatement(auditdoc);
			ResultSet resultSet1 = statement1.executeQuery();
			while (resultSet1.next()) {
				batchstats = resultSet1.getInt("batchstats");
			}
			for (int i = 0; i < maplist.size(); i++) {
				Iterator<Integer> iter = maplist.get(i).keySet().iterator();
				while (iter.hasNext()) {
					Integer key = iter.next();
					int value = (int) maplist.get(i).get(key);
					if (!key.equals(batchstats)) {
						Statisticalnum statisticalnum = new Statisticalnum();
						statisticalnum.setName("docnum");
						if (list.size() > 0) {
							for (int j = 0; j < list.size(); j++) {
								if (list.get(i).getName().equals("docnum")) {
									list.get(i).setNums(list.get(i).getNums() + value);
								}else{
									statisticalnum.setNums(value);
									list.add(statisticalnum);
								}
							}
						} else {
							statisticalnum.setNums(value);
							list.add(statisticalnum);
						}
					}
					Statisticalnum statisticalnum = new Statisticalnum();
					statisticalnum.setName("docnums");
					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							if (list.get(i).getName().equals("docnums")) {
								list.get(i).setNums(list.get(i).getNums() + value);
							}else{
								statisticalnum.setNums(value);
								list.add(statisticalnum);
							}
						}
					} else {
						statisticalnum.setNums(value);
						list.add(statisticalnum);
					}
					
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	@Transactional
	public List<Statisticalnum> laStatistical() {
		List<Map> maplist = new ArrayList<Map>();
		List<Statisticalnum> list = new ArrayList<Statisticalnum>();
		Session session = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
		String sumdoc = "select state,SUM(nums)as lasum from (select state,count(1) as nums from Docsectionandrule11 GROUP BY state union all select state,count(1) as nums from Docsectionandrule12 GROUP BY state union all select state,count(1) as nums from Docsectionandrule13 GROUP BY state union all select state,count(1) as nums from Docsectionandrule14 GROUP BY state union all select state,count(1) as nums from Docsectionandrule15 GROUP BY state union all select state,count(1) as nums from Docsectionandrule16 GROUP BY state union all select state,count(1) as nums from Docsectionandrule17 GROUP BY state union all select state,count(1) as nums from Docsectionandrule18 GROUP BY state union all select state,count(1) as nums from Docsectionandrule19 GROUP BY state union all select state,count(1) as nums from Docsectionandrule20 GROUP BY state) a GROUP BY state";
		Connection conn = session.connection();
		String auditdoc = "select batchstats from latitudeaudit where latitudetype = 1 and casetype = 1 limit 1;";
		int batchstats = -1;
		try {
			PreparedStatement statement = conn.prepareStatement(sumdoc);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Map map = new HashMap();
				map.put(resultSet.getInt("state"), resultSet.getInt("lasum"));
				maplist.add(map);
			}
			PreparedStatement statement1 = conn.prepareStatement(auditdoc);
			ResultSet resultSet1 = statement1.executeQuery();
			while (resultSet1.next()) {
				batchstats = resultSet1.getInt("batchstats");
			}
			for (int i = 0; i < maplist.size(); i++) {
				Iterator<Integer> iter = maplist.get(i).keySet().iterator();
				while (iter.hasNext()) {
					Integer key = iter.next();
					int value = (int) maplist.get(i).get(key);
					if (!key.equals(batchstats)) {
						Statisticalnum statisticalnum = new Statisticalnum();
						statisticalnum.setName("lanum");
						if (list.size() > 0) {
							for (int j = 0; j < list.size(); j++) {
								if (list.get(i).getName().equals("lanum")) {
									list.get(i).setNums(list.get(i).getNums() + value);
								}
							}
						} else {
							statisticalnum.setNums(value);
						}
						list.add(statisticalnum);
					}
					Statisticalnum statisticalnum = new Statisticalnum();
					statisticalnum.setName("lanums");
					if (list.size() > 0) {
						for (int j = 0; j < list.size(); j++) {
							if (list.get(i).getName().equals("lanums")) {
								list.get(i).setNums(list.get(i).getNums() + value);
							}else{
								statisticalnum.setNums(value);
							}
						}
					} else {
						statisticalnum.setNums(value);
					}
					list.add(statisticalnum);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
